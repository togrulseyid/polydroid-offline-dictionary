package com.togrul.polydroidofflinedictionary.settings;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.togrul.polydroidofflinedictionary.R;


public class AboutUsActivity extends Fragment {
	
//	private TextView aboutUsTextView, PDName;
//	private ScrollView scrollView;
//	private ImageView PDimageView;
	
	public static Fragment instance() {
		AboutUsActivity aboutUsActivity = new AboutUsActivity();
		return aboutUsActivity;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_settings_tab_aboutus, null);
//
//		aboutUsTextView = (TextView) view.findViewById(R.id.aboutUsTextView);
//		scrollView = (ScrollView) view.findViewById(R.id.scrollView);
//		PDimageView = (ImageView) view.findViewById(R.id.PDimageView);
//		
//		PDName = (TextView) view.findViewById(R.id.PDName);
//		scrollView.setOnTouchListener(new OnTouchListener() {
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				aboutUsTextView.setHeight(30);				
//				return false;
//			}
//		});
//		
//		
//		PDimageView.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				aboutUsTextView.setHeight(250);
//			}
//		});
//		
//		PDName.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//				aboutUsTextView.setHeight(250);
//			}
//		});

		return view;
	}
	
}