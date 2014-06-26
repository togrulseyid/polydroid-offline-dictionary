package com.togrul.polydroidofflinedictionary.settings;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.togrul.polydroidofflinedictionary.R;


public class ThemeActivity extends Fragment {

	private TextView textSize, RGB;
	private SeekBar seekBar, seekBarR, seekBarG, seekBarB;
	private int ColorR, ColorG, ColorB, progress;
	private String RGBColor;
	private RadioButton NewTab, PopUp, HalfTab;
	private Button Reset;
	private Activity activity;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_settings_tab_preference, null);
		activity = getActivity();
		textSize = (TextView) view.findViewById(R.id.size);
		RGB = (TextView) view.findViewById(R.id.RGB);
		Reset = (Button) view.findViewById(R.id.buttonReset);

		seekBar = (SeekBar) view.findViewById(R.id.seekBar);

		seekBarR = (SeekBar) view.findViewById(R.id.seekBarR);
		seekBarG = (SeekBar) view.findViewById(R.id.seekBarG);
		seekBarB = (SeekBar) view.findViewById(R.id.seekBarB);

		NewTab = (RadioButton) view.findViewById(R.id.radio0);
		PopUp = (RadioButton) view.findViewById(R.id.radio1);
		HalfTab = (RadioButton) view.findViewById(R.id.radio2);
		
		seekBarR.setOnSeekBarChangeListener(SeekBarListener);
		seekBarG.setOnSeekBarChangeListener(SeekBarListener);
		seekBarB.setOnSeekBarChangeListener(SeekBarListener);

		LoadPreference();

		NewTab.setOnClickListener(myOptionOnClickListener);
		PopUp.setOnClickListener(myOptionOnClickListener);
		HalfTab.setOnClickListener(myOptionOnClickListener);

		seekBar.setSaveEnabled(true);
		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			public void onProgressChanged(SeekBar seekBar, int ps,
					boolean fromUser) {
				// ps = ps + 12;
				if (ps % 2 != 0)
					ps = ps + 1;
				progress = ps + 12;
				textSize.setText("Text size: " + (progress));
			}

			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			public void onStopTrackingTouch(SeekBar seekBar) {
				SavePreferens("SeekBar", (progress - 12) + "");
			}
		});

		Reset.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				SavePreferens("RGB", "color:rgb(172,173,131);");
				SavePreferens("R", 172 + "");
				SavePreferens("G", 173 + "");
				SavePreferens("B", 131 + "");
				SavePreferens("RadioButton", 0 + "");
				NewTab.setChecked(true);
				seekBarR.setProgress(172);
				seekBarG.setProgress(173);
				seekBarB.setProgress(131);
				seekBar.setProgress(6);
				SavePreferens("SeekBar", 6 + "");
				RGB.setBackgroundColor(Color.rgb(172,173,131));
				RGB.setText("RGB(172,173,131)");
			}
		});
		return view;
	}

	SeekBar.OnSeekBarChangeListener SeekBarListener = new SeekBar.OnSeekBarChangeListener() {
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			switch (seekBar.getId()) {
			case R.id.seekBarR:
				ColorR = progress;
				break;
			case R.id.seekBarG:
				ColorG = progress;
				break;
			case R.id.seekBarB:
				ColorB = progress;
				break;

			default:
				break;
			}
			// bgcolor="rgb(255,0,0)";
			RGBColor = "color:rgb(" + ColorR + "," + ColorG + "," + ColorB+ ");";
			
			RGB.setBackgroundColor(Color.rgb(ColorR, ColorG, ColorB));
			RGB.setText("RGB(" + ColorR + "," + ColorG + "," + ColorB + ")");
		}

		public void onStartTrackingTouch(SeekBar seekBar) {
		}

		public void onStopTrackingTouch(SeekBar seekBar) {
			SavePreferens("RGB", RGBColor);
			SavePreferens("R", ColorR + "");
			SavePreferens("G", ColorG + "");
			SavePreferens("B", ColorB + "");
		}

	};

	RadioButton.OnClickListener myOptionOnClickListener = new RadioButton.OnClickListener() {
		@Override
		public void onClick(View arg0) {
			if (NewTab.isChecked()) {
				SavePreferens("RadioButton", 0 + "");
			} else if (PopUp.isChecked()) {
				SavePreferens("RadioButton", 1 + "");
			} else if (HalfTab.isChecked()) {
				SavePreferens("RadioButton", 2 + "");
			}
		}
	};

	private void LoadPreference() {
		SharedPreferences sp = activity.getSharedPreferences("styleSharedPreferences", 0);
		int prg = Integer.valueOf(sp.getString("SeekBar", "6"));
		seekBar.setProgress(prg);
		textSize.setText("Text size:" + (prg + 12));
		// #003366 : 0, 51, 102
		int ColorR = Integer.valueOf(sp.getString("R", "245"));
		int ColorG = Integer.valueOf(sp.getString("G", "245"));
		int ColorB = Integer.valueOf(sp.getString("B", "220"));

		seekBarR.setProgress(ColorR);
		seekBarG.setProgress(ColorG);
		seekBarB.setProgress(ColorB);
		RGB.setBackgroundColor(Color.rgb(ColorR, ColorG, ColorB));
		RGB.setText("RGB(" + ColorR + ", " + ColorG + ", " + ColorB + ")");

		switch (Integer.valueOf(sp.getString("RadioButton", "0"))) {
		case 0:
			NewTab.setChecked(true);
			break;
		case 1:
			PopUp.setChecked(true);
			break;
		case 2:
			HalfTab.setChecked(true);
			break;
		default:
			break;
		}
	}

	private void SavePreferens(String key, String l) {
		SharedPreferences sp = activity.getSharedPreferences("styleSharedPreferences", 0);
		Editor edit = sp.edit();
		edit.putString(key, l);
		edit.commit();
	}

	public static Fragment instance() {
		ThemeActivity themeActivity = new ThemeActivity();
		return themeActivity;
	}

}