package com.togrul.pd.settings;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.togrul.polydroidofflinedictionary.R;

public class AboutUsActivity extends Fragment {

	Button moreAboutMe;

	public static Fragment instance() {
		AboutUsActivity aboutUsActivity = new AboutUsActivity();
		return aboutUsActivity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = getActivity().getLayoutInflater().inflate(
				R.layout.fragment_settings_tab_aboutus, null);

		moreAboutMe = (Button) view
				.findViewById(R.id.textViewFragmentSettingsTabAboutUsMoreAboutMe);

		moreAboutMe.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
						.parse(getResources().getString(
								R.string.fragment_settings_tab_about_web_site)));
				startActivity(browserIntent);
			}
		});

		return view;
	}

}