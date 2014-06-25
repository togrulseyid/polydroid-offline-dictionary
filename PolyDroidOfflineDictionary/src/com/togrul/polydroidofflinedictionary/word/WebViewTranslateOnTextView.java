package com.togrul.polydroidofflinedictionary.word;

import com.togrul.polydroidofflinedictionary.DataBaseHelper;
import com.togrul.polydroidofflinedictionary.R;
import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

public class WebViewTranslateOnTextView extends Activity {
	private static View view;
	private static long id;
	private static String spinnerText;
	private static DataBaseHelper mySQLiteAdapter;
	private static WebView webView;
	private static String sizeOfText = "font-size:18px;";
	private static String RGB = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.word2);
		
		LoadPreference();
		try {
			webView = (WebView) findViewById(R.id.WebView2);
			Cursor c = mySQLiteAdapter.query(((TextView) view).getText().toString(), spinnerText, id);
			c.moveToFirst();
			String s = c.getString(c.getColumnIndex("_to"));
			
			    
			    
			s = 	"<!DOCTYPE HTML><html><head>"
					+ "<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">"
					+ "<title>Translate</title>"
					+ " <style type=\"text/css\">"
					//+ " @font-face {  font-family: 'myface';  src: url('file:///android_asset/fonts//ARIAL.TTF');	} "
					+ " body{" + RGB
					+ 	" background-color: #F5F5DC; " + sizeOfText
					//+ 	" font-family: 'myface', arial;"
					+ 	" margin:30px 30px 0 30px;"
					+ " }</style>" 
					+ "</head>"
					+ " <body><center><font color='black'>" + c.getString(c.getColumnIndex("_from"))+"<hr size='2' color='black'></font></center>"
					+ 	s 
					+ "</body>" 
					+ "</html>";
			webView.loadData(s, "text/html; charset=utf-8", "utf-8");//"text/html", "UTF-8");
			//Log.d("HTML",s);
		} catch (Exception e) {
		} finally {
			mySQLiteAdapter.close();
		}
	}

	public void getData(View v, long i, DataBaseHelper m, String s) {
		view = v;
		id = i;
		mySQLiteAdapter = m;
		spinnerText = s;
	}

	private void LoadPreference() {
		SharedPreferences sp = getSharedPreferences("styleSharedPreferences", 0);
		sizeOfText = "font-size:"
				+ (Integer.valueOf(sp.getString("SeekBar", "6")) + 12) + "px;";
		RGB = sp.getString("RGB", "");
		// Log.d("RGB",RGB);
	}

}
