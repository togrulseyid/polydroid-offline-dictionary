package com.togrul.pd;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Environment;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.togrul.pd.adapters.PolyDroidSpinner;
import com.togrul.polydroidofflinedictionary.R;

public class SpinnerAdapter {
	private static final File sdcard = Environment
			.getExternalStorageDirectory();
	private static final String dbfile = sdcard.getAbsolutePath()
			+ File.separator + "PolyDroid" + File.separator + "database.db";
	private static final String dbPath = sdcard.getAbsolutePath()
			+ File.separator + "PolyDroid" + File.separator;
	private Spinner spinner;
	private Activity activity;
	private android.database.sqlite.SQLiteDatabase spinnerDB;

	public SpinnerAdapter(Spinner spinner, Activity context) {
		this.spinner = spinner;
		this.activity = context;

		createFileSystem();
		createDatabase();
	}

	public SpinnerAdapter(Activity context) {
		this.activity = context;

		createFileSystem();
		createDatabase();
	}

	protected void arrayAdapter() {

		int counter = 0;
		try {
			List<String> database_array_list = new ArrayList<String>();
			database_array_list.clear();

			try {
				Cursor cursor = getAccounts();
				int accountnameIndex = cursor.getColumnIndexOrThrow("dbname");
				if (cursor.moveToFirst()) {
					do {
						database_array_list.add(counter,
								cursor.getString(accountnameIndex));
						counter++;
					} while (cursor.moveToNext());
				}
			} catch (Exception e) {
			}

			if (database_array_list.size() == 0) {
				database_array_list.add("Add Language From settings Menu");
			}

			// This is the array adapter, it takes the context of the activity
			// as a first parameter, the type of list view as a second parameter
			// and your array as a third parameter

			ArrayAdapter<String> arrayAdapter = new PolyDroidSpinner(activity,R.layout.simple_spinner_dropdown_item, database_array_list);
					
					
//					new ArrayAdapter<String>(
//					context, android.R.layout.simple_spinner_item,
//					database_array_list);
			arrayAdapter
					.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
			spinner.setAdapter(arrayAdapter);
		} catch (Exception e) {
		}

	}

	public void close() {
		spinnerDB.close();
	}

	private Cursor getAccounts() {

		spinnerDB = android.database.sqlite.SQLiteDatabase
				.openOrCreateDatabase(dbfile, null);

		String KEY_ROWID = "id";
		String KEY_TITLE = "dbname";
		String KEY_CONTEXT = "context";
		Cursor c = spinnerDB.query("database", new String[] { KEY_ROWID,
				KEY_TITLE, KEY_CONTEXT }, null, null, null, null, KEY_ROWID);
		return c;

	}

	public void addSpinnerItem(String id, String database, String name) {
		spinnerDB = android.database.sqlite.SQLiteDatabase
				.openOrCreateDatabase(dbfile, null);
		spinnerDB.execSQL("INSERT INTO database VALUES(" + id + ",'" + database
				+ "','" + name + "')");
		spinnerDB.close();
	}

	public void deleteSpinnerItem(String id) {

		spinnerDB = android.database.sqlite.SQLiteDatabase
				.openOrCreateDatabase(dbfile, null);
		// spinnerDB.execSQL("INSERT INTO database VALUES(" + id + ",'" +
		// database+ "','" + name + "')");
		spinnerDB.delete("database", "id", new String[] { id });
		spinnerDB.close();

	}

	public boolean selectSpinnerDB() {

		int count;
		this.createDatabase();
		Cursor c = spinnerDB.rawQuery("SELECT count(id) FROM database", null);
		c.moveToFirst();
		count = c.getInt(0);
		c.close();
		spinnerDB.close();
		// Log.i("Counter",count+"");
		if (count == 1)
			return true;
		return false;

	}

	public int selectCountSpinnerDB() {
		spinnerDB = android.database.sqlite.SQLiteDatabase
				.openOrCreateDatabase(dbfile, null);
		Cursor c;
		int count = 0;

		try {
			c = spinnerDB.rawQuery("SELECT count(*) FROM database", null);
			c.moveToFirst();
			count = c.getInt(0);
			// Log.d("selectCountSpinnerDB1-idc",count+"");
			c.close();
			spinnerDB.close();
		} catch (Exception e) {
			// Log.d("ExCount",count+"");
		}

		return count;
	}

	public boolean isDownloaded(String id) throws SQLException {

		boolean downloaded = false;
		spinnerDB = android.database.sqlite.SQLiteDatabase
				.openOrCreateDatabase(dbfile, null);
		Cursor c = spinnerDB.rawQuery(
				"SELECT count(id) FROM database WHERE id=" + id, null);
		c.moveToFirst();
		int count = c.getInt(0);

		if (count == 1) {
			downloaded = true;
		}
		c.close();
		spinnerDB.close();

		return downloaded;
	}

	private void createDatabase() {
		try {
			createFileSystem();
			spinnerDB = android.database.sqlite.SQLiteDatabase
					.openOrCreateDatabase(dbfile, null);
			String sql = "CREATE TABLE 'database' ("
					+ " 'id' INTEGER NOT NULL," + " 'context' TEXT NOT NULL,"
					+ " 'dbname' TEXT NOT NULL )";

			if (isTableExists("database", spinnerDB) == false) {
				spinnerDB.execSQL(sql);
			}

			spinnerDB.close();
		} catch (Exception e) {
			// Log.d("Error3",e+"");
		}
	}

	private void createFileSystem() {
		File databaseDirectory = new File(dbPath);
		if (databaseDirectory.isDirectory() == false)
			databaseDirectory.mkdirs();
	}

	public boolean isTableExists(String tableName,
			android.database.sqlite.SQLiteDatabase spinnerDB) {
		spinnerDB = android.database.sqlite.SQLiteDatabase
				.openOrCreateDatabase(dbfile, null);
		Cursor cursor = spinnerDB.rawQuery(
				"select DISTINCT tbl_name from sqlite_master where tbl_name = '"
						+ tableName + "'", null);
		if (cursor != null) {
			if (cursor.getCount() > 0) {
				cursor.close();
				spinnerDB.close();
				return true;
			}
		}
		cursor.close();
		spinnerDB.close();
		return false;
	}

	public String dbnameFromTable(String name) {
		spinnerDB = android.database.sqlite.SQLiteDatabase
				.openOrCreateDatabase(dbfile, null);
		if (name == null || name.equals(""))
			return name = "azen.polydroid";
		try {
			Cursor c = spinnerDB.rawQuery(
					"SELECT context FROM database WHERE dbname='" + name
							+ "' ;", null);
			c.moveToFirst();
			name = c.getString(0);
			c.close();
			spinnerDB.close();
		} catch (Exception e) {
		}
		return name;
	}
}
