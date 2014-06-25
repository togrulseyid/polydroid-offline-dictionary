package com.togrul.polydroidofflinedictionary.download;

import com.togrul.polydroidofflinedictionary.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class NoInternetConnection extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.no_internet_connextion);
		ImageView image = (ImageView) findViewById(R.id.image_verkkovirhe);

		image.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo info = cm.getActiveNetworkInfo();
				if (info == null) {
					Intent intent = new Intent(getApplicationContext(),com.togrul.polydroidofflinedictionary.settings.DownloadActivity.class); 
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent);
					finish();
				}
			}
		});
	}
}