package com.togrul.polydroidofflinedictionary.settings;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.togrul.polydroidofflinedictionary.R;
import com.togrul.polydroidofflinedictionary.SpinnerAdapter;
import com.togrul.polydroidofflinedictionary.download.Download;
import com.togrul.polydroidofflinedictionary.download.XMLfunctions;

public class DownloadActivity extends Fragment{
	private ListView lv;
	private Intent i;
	private Activity activity; 
	private boolean isDownload = false;
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) { 
		View view = getActivity().getLayoutInflater().inflate(R.layout.listplaceholder, null);
		activity = getActivity();

	 	if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}
		

	 	ArrayList<DatabaseModel> databaseModels = new ArrayList<DatabaseModel>();

		String xml = XMLfunctions.getXML();
		Document doc = XMLfunctions.XMLfromString(xml);

		int numResults = XMLfunctions.numResults(doc);

		if ((numResults <= 0)) {
			 Toast.makeText(activity, "No Internet Conection! Please try again later", Toast.LENGTH_LONG).show();
		}

		NodeList nodes = doc.getElementsByTagName("database");

		for (int i = 0; i < nodes.getLength(); i++) {
			DatabaseModel map = new DatabaseModel();

			Element e = (Element) nodes.item(i);

			if(new SpinnerAdapter(activity).isDownloaded(Integer.valueOf(XMLfunctions.getValue(e, "id")))==1){
				isDownload = true;
			}else{
				isDownload = false;
			}
			
			map.setId(XMLfunctions.getValue(e, "id"));
			map.setSize(XMLfunctions.getValue(e, "size"));
			map.setName(XMLfunctions.getValue(e, "name"));
			map.setDbname(XMLfunctions.getValue(e, "dbname"));
			map.setDownload(new SpinnerAdapter(activity).isDownloaded(Integer.valueOf(XMLfunctions.getValue(e, "id")))+ "");
			map.setIsdownload(isDownload);
			databaseModels.add(map);
		}
		
//		SimpleAdapter adapter = new SimpleAdapter(activity, mylist, R.layout.download_listview,
//				new String[] { "name", "size","isdownload" }, new int[] { R.id.item_title, R.id.item_subtitle,R.id.is_item_downloaded_title });
		SimpleAdapter adapter = new SimpleAdapter(activity, databaseModels);

		lv = (ListView) view.findViewById(R.id.listViewDatabaseList);
		lv.setTextFilterEnabled(true);
		lv.setAdapter(adapter);

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				TextView txt = (TextView) view.findViewById(R.id.is_item_downloaded_title);
				DatabaseModel o = (DatabaseModel) lv.getItemAtPosition(position);
				if (Integer.valueOf(o.getDownload()) == 1) {
					txt.setText("Downloaded");
					txt.setTextColor(Color.GREEN);
					i = new Intent(activity, Download.class);
					i.putExtra("id", o.getId());
					i.putExtra("size", o.getSize());
					i.putExtra("name", o.getName());
					i.putExtra("dbname", o.getDbname());
					i.putExtra("isDownload", o.getDownload());
					startActivity(i);
				} else {
					txt.setText("Download");
					txt.setTextColor(Color.RED);
					i = new Intent(activity, Download.class);
					i.putExtra("id", o.getId());
					i.putExtra("size", o.getSize());
					i.putExtra("name", o.getName());
					i.putExtra("dbname", o.getDbname());
					i.putExtra("isDownload", o.getDownload());
					startActivity(i);
				}
			}
		});
		adapter.notifyDataSetChanged();
		
	 	
		return view;
	}
		

//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		// Handle the back button
//		if (keyCode == KeyEvent.KEYCODE_BACK) {
//			// Ask the user if they want to quit
//			new AlertDialog.Builder(activity)
//					.setIcon(android.R.drawable.ic_dialog_alert)
//					.setTitle(R.string.d_quit)
//					.setMessage(R.string.d_really_quit)
//					.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
//								public void onClick(DialogInterface dialog, int which) {
//									System.exit(-1);
//									activity.finish();
//								}
//							})
//					.setNegativeButton(R.string.d_no, new DialogInterface.OnClickListener() {
//						public void onClick(DialogInterface dialog, int which) {
//							launchIntent();
//							activity.finish();
//						}
//					}).show();
//			return true;
//		} else {
//			return super.onKeyDown(keyCode, event);
//		}
//	}


	public static Fragment instance() {
		DownloadActivity downloadActivity = new DownloadActivity();		
		return downloadActivity;
	}

	
	
//	private class ListDownloadAsynTask extends AsyncTask<Params, Progress, Result>{
//		
//	}
	

}