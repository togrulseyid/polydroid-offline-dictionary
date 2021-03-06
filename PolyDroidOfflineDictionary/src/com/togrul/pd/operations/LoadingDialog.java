package com.togrul.pd.operations;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.togrul.polydroidofflinedictionary.R;

public class LoadingDialog extends DialogFragment {

	public static final int LOADING_DIALOG_STYLE_DETERMINATE = 0;
	public static final int LOADING_DIALOG_STYLE_INDETERMINATE = 1;
	private int style = 0;
	private ProgressBar progressBar;
	private ProgressBar progressBarIndeterminate;
	private TextView textViewTitle;
	
	public LoadingDialog(int style) {
		this.style = style;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		setStyle(STYLE_NO_TITLE,  R.style.Polydroid_Dialog);

		LayoutInflater inflater = getActivity().getLayoutInflater();
		View dialoglayout = inflater.inflate(R.layout.dialog_loading, null);
		progressBar = (ProgressBar) dialoglayout.findViewById(R.id.progressBarDialogProgress);
		progressBarIndeterminate = (ProgressBar) dialoglayout.findViewById(R.id.progressBarDialogIndeterminateProgress);
		textViewTitle = (TextView) dialoglayout.findViewById(R.id.textViewDialogProgressTitle);
		
		textViewTitle.setVisibility(View.VISIBLE);
		if(style == 0) {
			progressBar.setVisibility(View.VISIBLE);
		} else {
			progressBarIndeterminate.setVisibility(View.VISIBLE);
		}
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setView(dialoglayout);
		builder.setCancelable(false);
		setCancelable(false);

		return builder.create();

	}

	public void setProgress(int progress) {
		progressBar.setProgress(progress);
	}
	
	public void setProgressTitle(String title) {
		textViewTitle.setText(title);
	}

}