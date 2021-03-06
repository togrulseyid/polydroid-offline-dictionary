package com.togrul.pd.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

import com.togrul.polydroidofflinedictionary.R;

public class SettingsActivity extends ActionBarActivity implements
		ActionBar.TabListener {

	private ActionBar actionBar;
	private Fragment fragment;
	private Tab tab1, tab2, tab3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		setContentView(R.layout.activity_settings);

		actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		tab1 = actionBar.newTab().setTabListener(this);
		tab2 = actionBar.newTab().setTabListener(this);
		tab3 = actionBar.newTab().setTabListener(this);

		tab1.setIcon(getResources().getDrawable(R.drawable.icon_style_config));
		tab2.setIcon(getResources()
				.getDrawable(R.drawable.icon_download_config));
		tab3.setIcon(getResources().getDrawable(R.drawable.icon_aboutus_config));

		actionBar.addTab(tab1);
		actionBar.addTab(tab2);
		actionBar.addTab(tab3);

		Intent i = getIntent();
		int tabToOpen = i.getIntExtra("FirstTab", -1);
		if (tabToOpen == 4) {
			getSupportActionBar().selectTab(tab2);
		} else if (tabToOpen == 2) {
			getSupportActionBar().selectTab(tab3);
		}
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction arg1) {
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction arg1) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction fragmentTransaction) {

		switch (tab.getPosition()) {
		case 0: // ThemeActivity
			fragment = ThemeActivity.instance();
			break;

		case 1: // DownloadActivity
			fragment = DownloadActivity.instance();
			break;

		case 2: // AboutUsActivity
			fragment = AboutUsActivity.instance();
			break;

		default: // ThemeActivity
			fragment = ThemeActivity.instance();
			break;
		}

		try {
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.settings_content_frame, fragment).commit();
		} catch (Exception tabError) {
			tabError.printStackTrace();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		// clear tab view on exit;
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayHomeAsUpEnabled(false);
		actionBar.setDisplayUseLogoEnabled(false);
		actionBar.removeAllTabs();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
		}
		return super.onOptionsItemSelected(item);
	}
}