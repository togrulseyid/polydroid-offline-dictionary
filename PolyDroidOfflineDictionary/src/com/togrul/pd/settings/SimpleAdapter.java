package com.togrul.pd.settings;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.togrul.pd.models.DatabaseModel;
import com.togrul.polydroidofflinedictionary.R;

public class SimpleAdapter extends BaseAdapter {
	
	private Activity activity;
	private ArrayList<DatabaseModel> databaseModels;
	private ViewHolder viewHolder;
	private DatabaseModel model;
	
	public SimpleAdapter(Activity activity, ArrayList<DatabaseModel> databaseModels) {
		this.activity = activity;
		this.databaseModels = databaseModels;
	}
	
	@Override
	public int getCount() {
		return databaseModels.size();
	}

	@Override
	public DatabaseModel getItem(int position) {
		return databaseModels.get(position);
	}

	@Override
	public long getItemId(int id) {
		return id;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup viewGroup) {

		View view = convertView;
		if (convertView == null) {
			view = activity.getLayoutInflater().inflate(
					R.layout.fragment_settings_tab_database_list_items, viewGroup, false);
			viewHolder = new ViewHolder();

			viewHolder.name = (TextView) view.findViewById(R.id.item_title);
			viewHolder.size = (TextView) view.findViewById(R.id.item_subtitle);
			viewHolder.isdownload = (TextView) view
					.findViewById(R.id.is_item_downloaded_title);

			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}

		model = getItem(position);

		viewHolder.name.setText(model.getName());
		viewHolder.size.setText(model.getSize());
		
		if(model.getIsDownload()) {
			viewHolder.isdownload.setText("Downloaded");
		} else {
			viewHolder.isdownload.setText("Download");			
		}
		 
		if (model.getIsDownload()) {
			viewHolder.isdownload.setText("Downloaded");
			viewHolder.isdownload.setTextColor(Color.GREEN);
		} else {
			viewHolder.isdownload.setText("Download");
			viewHolder.isdownload.setTextColor(Color.RED);
		}

		return view;
	}

	private class ViewHolder {
		public TextView name;
		public TextView size;
		public TextView isdownload;
	}
	
}
