package com.togrul.pd.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.togrul.pd.views.arabic.ArabicUtilities;
import com.togrul.polydroidofflinedictionary.R;

public class TranslationInTextViewArabicCorrector extends ActionBarActivity {

	private String to;
	private String from;
	private WebView webView;
	private String sizeOfText = "font-size:18px;";
	private String RGB = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		setContentView(R.layout.translation_in_text_view);

		Intent intent = getIntent();

		from = intent.getStringExtra("from");
		to = intent.getStringExtra("to");

		LoadPreference();
		try {
			webView = (WebView) findViewById(R.id.WebView2);

			to = "<!DOCTYPE HTML>"
					+ "<html>"
					+ "<head>"
					+ "<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">"
					+ "<title>Translate</title>"
					+ " <style type=\"text/css\">"
					+ " @font-face {  font-family: 'myface';  src: url('file:///android_asset/fonts//ARIAL.TTF');	} "
					+ " body{"
					+ 	RGB
					+ " background-color: #F5F5DC; "
					+ 	sizeOfText
					+ " font-family: 'myface', serif;"
					+ " margin:30px 0 0 30px;" + " }</style>" 
					+ " </head>"
					+ " <body>"
					+ "<center>"
					+ "<font color='black'>" + Html.fromHtml(from) + " <hr size='2' color='black'></font>"
					+ "</center>" 
					+ Html.fromHtml(to)
					+ " </body>" 
					+ "</html>";
			

			webView.setWebChromeClient(new WebChromeClient());
			webView.setWebViewClient(new WebViewClient(){
				public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
	        		//do something on error
					webView.loadUrl("file:///android_asset/html/myerrorpage.html");
	        	}
			}); 
		
			webView.loadData(ArabicUtilities.reshape(to), "text/html; charset=utf-8", "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void LoadPreference() {
		SharedPreferences sp = getSharedPreferences("styleSharedPreferences", 0);
		sizeOfText = "font-size:"
				+ (Integer.valueOf(sp.getString("SeekBar", "6")) + 12) + "px;";
		RGB = sp.getString("RGB", "");
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;

		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}
}