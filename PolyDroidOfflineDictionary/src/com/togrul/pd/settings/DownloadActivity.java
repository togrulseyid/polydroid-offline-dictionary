package com.togrul.pd.settings;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.togrul.pd.SpinnerAdapter;
import com.togrul.pd.models.DatabaseModel;
import com.togrul.pd.operations.LoadingDialog;
import com.togrul.pd.operations.XMLfunctions;
import com.togrul.polydroidofflinedictionary.R;

public class DownloadActivity extends Fragment {

	private static final File sdcard = Environment
			.getExternalStorageDirectory();
	private static final String dbPath = sdcard.getAbsolutePath()
			+ File.separator + "PolyDroid" + File.separator;
	private static final String dbfile = sdcard.getAbsolutePath()
			+ File.separator + "PolyDroid" + File.separator + "database.db";
	private long total = 0, lenghtOfFile;
	private ListView listView;
	private TextView textViewNoInternetConnection;
	private Activity activity;
	private DatabaseModel databaseModel;
	private ArrayList<DatabaseModel> databaseModels;
	private Document doc;
	private NodeList nodes;
	private SimpleAdapter adapter;
	private TextView textViewIsDownloaded;

	public static Fragment instance() {
		DownloadActivity downloadActivity = new DownloadActivity();
		return downloadActivity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = getActivity().getLayoutInflater().inflate(
				R.layout.fragment_settings_tab_database_list, null);
		activity = getActivity();

		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

		listView = (ListView) view.findViewById(R.id.listViewDatabaseList);
		textViewNoInternetConnection = (TextView) view
				.findViewById(R.id.textViewFragmentSettingsNoInternetConnection);
		databaseModels = new ArrayList<DatabaseModel>();
		adapter = new SimpleAdapter(activity, databaseModels);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new PDOnClickListener());

		new DownloadList().execute();

		return view;
	}

	private class PDOnClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> adapterView, View view,
				int position, long id) {

			textViewIsDownloaded = (TextView) view
					.findViewById(R.id.is_item_downloaded_title);
			databaseModel = (DatabaseModel) listView
					.getItemAtPosition(position);
			if (databaseModel.getIsDownload()) {
				textViewIsDownloaded.setText("Downloaded");
				textViewIsDownloaded.setTextColor(Color.GREEN);
			} else {
				textViewIsDownloaded.setText("Download");
				textViewIsDownloaded.setTextColor(Color.RED);
			}

			DownloadActivity.this.lenghtOfFile = Integer.valueOf(databaseModel
					.getSize());
			if (databaseModel.getIsDownload()) {

				new AlertDialog.Builder(activity)
						.setIcon(android.R.drawable.ic_dialog_alert)
						.setTitle(R.string.confirm_dialog_delete_title)
						.setMessage(R.string.confirm_dialog_delete_body)
						.setPositiveButton(R.string.yes,
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO: start Delete
										File file = new File(dbPath
												+ databaseModel.getDbName());
										boolean x = file.delete();
										if (x) {
											deleteSpinnerItem(databaseModel
													.getId());
											databaseModel.setIsDownload(false);
											adapter.notifyDataSetChanged();
										}
									}

								}).setNegativeButton(R.string.no, null).show();

			} else {
				// TODO: start download
				new DownloadFileAsync(getActivity(), databaseModel.getDbName(),
						databaseModel.getId())
						.execute("http://polydroid.info/polydroid/encrypted/"
								+ databaseModel.getDbName());
			}
		}
	}

	private class DownloadList extends AsyncTask<Void, String, Integer> {

		private LoadingDialog dialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			if (dialog == null) {
				dialog = new LoadingDialog(
						LoadingDialog.LOADING_DIALOG_STYLE_INDETERMINATE);
				dialog.show(getFragmentManager(), "");
			}
		}

		@Override
		protected void onProgressUpdate(String... values) {
			super.onProgressUpdate(values);

			dialog.setProgressTitle("Downloading...");
		}

		@Override
		protected Integer doInBackground(Void... params) {
			publishProgress("Downloading");
			String xml = getXML();
			doc = XMLfunctions.XMLfromString(xml);

			int numResults = XMLfunctions.numResults(doc);

			nodes = doc.getElementsByTagName("database");

			for (int i = 0; i < nodes.getLength(); i++) {

				DatabaseModel model = new DatabaseModel();
				Element e = (Element) nodes.item(i);
				model.setId(XMLfunctions.getValue(e, "id"));
				model.setSize(XMLfunctions.getValue(e, "size"));
				model.setName(XMLfunctions.getValue(e, "name"));
				model.setDbname(XMLfunctions.getValue(e, "dbname"));
				model.setIsDownload(new SpinnerAdapter(activity)
						.isDownloaded(XMLfunctions.getValue(e, "id")));
				databaseModels.add(model);

			}

			return numResults;
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);

			if ((result <= 0)) { // no internet connection
				textViewNoInternetConnection.setVisibility(View.VISIBLE);
			}

			if (dialog != null) {
				dialog.dismiss();
			}
			adapter.notifyDataSetChanged();
		}

		public String getXML() {
			String line = null;
			try {
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(
						"http://polydroid.info/polydroid/database.xml");
				HttpResponse httpResponse = httpClient.execute(httpPost);
				HttpEntity httpEntity = httpResponse.getEntity();
				line = EntityUtils.toString(httpEntity, "UTF-8");

			} catch (UnsupportedEncodingException e) {
				line = "<results status=\"error\"><msg>Can't connect to server</msg></results>";
			} catch (MalformedURLException e) {
				line = "<results status=\"error\"><msg>Can't connect to server</msg></results>";
			} catch (IOException e) {
				line = "<results status=\"error\"><msg>Can't connect to server</msg></results>";
			}

			return line;
		}

	}

	private class DownloadFileAsync extends AsyncTask<String, Integer, Void> {

		private LoadingDialog dialog;
		private Activity activity;
		private String dbName;
		private String id;

		public DownloadFileAsync(Activity activity, String dbName, String id) {
			this.activity = activity;
			this.dbName = dbName;
			this.id = id;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			if (dialog == null) {
				dialog = new LoadingDialog(
						LoadingDialog.LOADING_DIALOG_STYLE_DETERMINATE);
				dialog.show(getFragmentManager(), "");
			}

		}

		@Override
		protected Void doInBackground(String... urls) {

			int count;
			try {
				URL url = new URL(urls[0]);
				URLConnection conexion = url.openConnection();
				conexion.connect();

				InputStream input = new BufferedInputStream(url.openStream());
				OutputStream output = new FileOutputStream(dbPath + dbName);

				byte data[] = new byte[1024];

				while ((count = input.read(data)) != -1) {

					total += count;
					output.write(data, 0, count);

					if (total != lenghtOfFile) {
						// Log.d("testP","progress: " + (int) ((total * 100) /
						// lenghtOfFile));
						Log.d("testP", "total: " + total + "\n lenghtOfFile: "
								+ lenghtOfFile);
						publishProgress((int) ((total * 100) / lenghtOfFile));
					}

				}

				output.flush();
				output.close();
				input.close();
				databaseModel.setIsDownload(true);

			} catch (Exception e) {
			} finally {
				total = 0;
				SpinnerAdapter sAdapter = new SpinnerAdapter(activity);
				sAdapter.addSpinnerItem(id, dbName, databaseModel.getName());
			}

			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
			dialog.setProgress(values[0]);

			dialog.setProgressTitle(values[0] + "%");
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			if (dialog != null) {
				dialog.dismiss();
			}
			adapter.notifyDataSetChanged();
		}
	}

	public void deleteSpinnerItem(String id) {
		SQLiteDatabase spinnerDB = android.database.sqlite.SQLiteDatabase
				.openOrCreateDatabase(dbfile, null);
		spinnerDB.delete("database", " id=" + id, null);
		spinnerDB.close();
	}
}