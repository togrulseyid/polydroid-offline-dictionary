package com.togrul.polydroidofflinedictionary.download;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import com.togrul.polydroidofflinedictionary.R;
import com.togrul.polydroidofflinedictionary.SpinnerAdapter;
import com.togrul.polydroidofflinedictionary.settings.DownloadActivity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

/**
 * @author togrul
 * @version 1.0.0
 * @see <a href="http://polydroid.info/polydroid/database.xml">Downloads</a>
 * */

public class Download extends Activity {
	private static File sdcard = Environment.getExternalStorageDirectory();
	private static String dbPath = sdcard.getAbsolutePath() + File.separator+"PolyDroid" + File.separator;
	private static final String dbfile = sdcard.getAbsolutePath()+ File.separator + "PolyDroid" + File.separator + "database.db";

	public static long total = 0, lenghtOfFile;
	public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
	private Button startBtn;
	private ProgressDialog mProgressDialog;
	private String database, name, id;
	private Intent i;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.download);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		Intent myIntent = getIntent(); // gets the previously created intent
		database = myIntent.getStringExtra("dbname");
		name = myIntent.getStringExtra("name");
		id = myIntent.getStringExtra("id");
		lenghtOfFile = Integer.valueOf(myIntent.getStringExtra("size"));

		startBtn = (Button) findViewById(R.id.startBtn);
		
		if(new SpinnerAdapter(getApplicationContext()).isDownloaded(Integer.valueOf(id))!=0){
			startBtn.setText("Delete");
			Toast.makeText(getApplicationContext(), "Toast made", Toast.LENGTH_LONG).show();
			startBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					File file = new File(dbPath+database);
					boolean x = file.delete();
					if(x){
//						Log.d("Log","DDDDD");
						deleteSpinnerItem(id);
					}					
				}
			});
			
		}else{
			startBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					startDownload("http://polydroid.info/polydroid/encrypted/"+ database);
				}
			});
		}
	}
	
	public void deleteSpinnerItem(String id) {
		SQLiteDatabase spinnerDB = android.database.sqlite.SQLiteDatabase.openOrCreateDatabase(dbfile, null);
		spinnerDB.delete("database", " id="+id, null);
		spinnerDB.close();
		i = new Intent(Download.this, DownloadActivity.class);
		startActivity(i);
		finish();

	}

	private void startDownload(String url) {
		new DownloadFileAsync().execute(url);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_DOWNLOAD_PROGRESS:
			mProgressDialog = new ProgressDialog(this);
			mProgressDialog.setMessage("Downloading file..");
			mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			mProgressDialog.setCancelable(false);
			mProgressDialog.show();
			return mProgressDialog;
		default:
			return null;
		}
	}

	class DownloadFileAsync extends AsyncTask<String, String, String> {

		@SuppressWarnings("deprecation")
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showDialog(DIALOG_DOWNLOAD_PROGRESS);
			mProgressDialog.setProgress(0);
		}

		@Override
		protected String doInBackground(String... aurl) {
			int count;

			try {
				URL url = new URL(aurl[0]);
				URLConnection conexion = url.openConnection();
				conexion.connect();

				// lenghtOfFile = conexion.getContentLength();
				// Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);

				InputStream input = new BufferedInputStream(url.openStream());
				OutputStream output = new FileOutputStream(dbPath + database);

				byte data[] = new byte[1024];

				while ((count = input.read(data)) != -1) {
					total += count;
					output.write(data, 0, count);
					if (total != lenghtOfFile) {
						publishProgress(""
								+ (int) ((total * 100) / lenghtOfFile));
					}
				}

				output.flush();
				output.close();
				input.close();
			} catch (Exception e) {
			} finally {
				// Log.d("database",database);
				total = 0;
				SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext());
				adapter.addSpinnerItem(id, database, name);
				
				i = new Intent(Download.this, DownloadActivity.class);
				startActivity(i);
				finish();

			}
			return null;

		}

		protected void onProgressUpdate(String... progress) {
			// Log.d("ANDRO_ASYNC", progress[0]);
			mProgressDialog.setProgress(Integer.parseInt(progress[0]));
		}

		@SuppressWarnings("deprecation")
		@Override
		protected void onPostExecute(String unused) {
			dismissDialog(DIALOG_DOWNLOAD_PROGRESS);
		}
	}
}