package com.togrul.pd.adapters;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.togrul.polydroidofflinedictionary.R;

public class PolyDroidSpinner extends ArrayAdapter<String> {

	private List<String> objects;
	private Activity activity;

	public PolyDroidSpinner(Activity activity, int resource,
			List<String> objects) {
		super(activity, resource, objects);
		this.objects = objects;
		this.activity = activity;
	}

	@Override
	public String getItem(int position) {
		return objects.get(position);
	}

	@Override
	public int getCount() {
		return objects.size();
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View row = activity.getLayoutInflater().inflate(
				R.layout.simple_spinner_selected_item, parent, false);
		TextView make = (TextView) row.findViewById(android.R.id.text1);
		make.setText(getItem(position));
		return row;
	}

	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		View row = activity.getLayoutInflater().inflate(
				R.layout.simple_spinner_dropdown_item, parent, false);
		TextView make = (TextView) row.findViewById(android.R.id.text1);
		make.setText(getItem(position));
		return row;//
	}

}