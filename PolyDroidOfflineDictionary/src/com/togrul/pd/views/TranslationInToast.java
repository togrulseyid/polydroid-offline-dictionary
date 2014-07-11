package com.togrul.pd.views;

import android.app.Activity;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

import com.togrul.pd.DataBaseHelper;
import com.togrul.polydroidofflinedictionary.R;

public class TranslationInToast extends Activity{
	
	private static View view ;
	private static long id ;
	private static String spinnerText ;
	private static DataBaseHelper mySQLiteAdapter ;
	private static Dialog dialog;
	private WebView webView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		dialog = new Dialog(TranslationInToast.this);
		dialog.setContentView(R.layout.word);
		dialog.setTitle("PolyDroid Offline Dictionary");
		dialog.setCancelable(true);
		
		webView = (WebView) dialog.findViewById(R.id.WebView);
		try {
			Cursor c = mySQLiteAdapter.query(((TextView) view).getText().toString(), spinnerText, id);
			c.moveToFirst();
			String s = c.getString(c.getColumnIndex("_to"));
			//Translated word
			s = "<html>" +
				"<head><meta charset=\"utf-8\" />" +
				"<style type=\"text/css\"> body{ background-color: #F5F5DC; font-size:18px;}</style>" +
				"</head>" + 
				"<body>" +
				Html.fromHtml(s) +
				"</body>" +
				"</html>";

			webView.setWebChromeClient(new WebChromeClient());
			webView.setWebViewClient(new WebViewClient(){
				public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
	        		//do something on error
					webView.loadUrl("file:///android_asset/html/myerrorpage.html");
	        	}
			}); 
		
			webView.loadData(s, "text/html","UTF-8");
		} catch (Exception e) {
			Log.d("WebView", e.toString());
		}finally{
			mySQLiteAdapter.close();
		}

		ImageButton button = (ImageButton) dialog.findViewById(R.id.exit);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.cancel();
				finish();
			}
		});
		dialog.show();
	}
	
	public void getData(View v, long i,DataBaseHelper m, String s,Dialog d)
	{
			dialog =d;
			view=v;
			id=i;
			mySQLiteAdapter =m;
			spinnerText =s;
	}

}
