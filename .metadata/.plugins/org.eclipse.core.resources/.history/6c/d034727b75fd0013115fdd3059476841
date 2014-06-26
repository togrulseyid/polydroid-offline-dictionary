package com.togrul.polydroidofflinedictionary;

import java.util.Observable;
import java.util.Observer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.togrul.polydroidofflinedictionary.word.WebViewTranslateOnTextView;

/**
 * @author Togrul Seyidov
 * @version 1.0.0
 * @see <a href="http://www.polydroid.info">PolyDroid.info</a>
 * @since <b>What the hell? ))</b>
 * */

public class PolyDroidActivity extends ActionBarActivity implements Observer {
	
	private String database;
	private String spinnerText;
	private Dialog dialog;
	private ListView listContent;
	private Spinner spinner;
	private EditText editText;
	private ImageButton SearchButton;
	private Button button1;
	private Button button2;
	private Button button3;
	private Button button4;
	private Button button5;
	private Button button6;
	private Button button7;
	private SpinnerAdapter spinnerAdapter;
	private SimpleCursorAdapter cursorAdapter;
	private DataBaseHelper databaseHelper;
	private Typeface EditTextface;
	private Typeface face;
	private String sizeOfText = "font-size:18px;";
	private String RGB;
	private Intent i;

	/*
	 * onCreate method it start first and main operations are here
	 */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_polydroid);

		spinner = (Spinner) findViewById(R.id.languages);
		SearchButton = (ImageButton) findViewById(R.id.searchbutton);
		editText = (EditText) findViewById(R.id.searchtext);
		listContent = (ListView) findViewById(R.id.contentlist);
		
		button1 = (Button) findViewById(R.id.herif1);
		button2 = (Button) findViewById(R.id.herif2);
		button3 = (Button) findViewById(R.id.herif3);
		button4 = (Button) findViewById(R.id.herif4);
		button5 = (Button) findViewById(R.id.herif5);
		button6 = (Button) findViewById(R.id.herif6);
		button7 = (Button) findViewById(R.id.herif7);

		spinnerAdapter = new SpinnerAdapter(spinner, getApplicationContext());
		if (spinnerAdapter.selectCountSpinnerDB() == 0) {
			Intent i = new Intent(this, com.togrul.polydroidofflinedictionary.settings.SettingsActivity.class);
			i.putExtra("FirstTab", 4);
			startActivity(i);
			finish();
		}

		spinnerAdapter.arrayAdapter();
		spinnerAdapter.close(); // // //
		spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
		//LoadPreference();
		spinner.setSaveEnabled(true);
		
		
		EditTextface = Typeface.createFromAsset(getAssets(), "fonts/ARIAL.TTF");
		editText.setTypeface(EditTextface);
		face = Typeface.createFromAsset(getAssets(), "fonts/ARIAL.TTF");
		button1.setTypeface(face);
		button2.setTypeface(face);
		button3.setTypeface(face);
		button4.setTypeface(face);
		button5.setTypeface(face);
		button6.setTypeface(face);
		button7.setTypeface(face);
		
		buttonAction(button1, getString(R.string.herif1));
		buttonAction(button2, getString(R.string.herif2));
		buttonAction(button3, getString(R.string.herif3));
		buttonAction(button4, getString(R.string.herif4));
		buttonAction(button5, getString(R.string.herif5));
		buttonAction(button6, getString(R.string.herif6));
		buttonAction(button7, getString(R.string.herif7));

		database = spinnerAdapter.dbnameFromTable(spinnerString(spinner));
		databaseHelper = new DataBaseHelper(getApplicationContext(), database);
		databaseHelper.observe();
		spinnerAdapter.close(); // // //
		databaseHelper.close(); // // //
		

		
		editText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				searchIt();
			}
		});

		SearchButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
				try {
					searchIt();
				} catch (Exception e) {
				}
				databaseHelper.close();
				spinnerAdapter.close(); // // //
			}
		});

		
//		Log.d("testA", spinnerString(spinner));
	}

	@Override
	protected void onPause() {
		super.onPause();
	}
	
	
	public class MyOnItemSelectedListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
			spinnerText = spinnerString(spinner);
			//SavePreferens("languages", spinnerText);
			BeingWatched watch = new BeingWatched();
			watch.Send(database);
		}

		public void onNothingSelected(AdapterView<?> parent) {
			// Do nothing.
		}
	}

	
	private int LoadstyleSharedPreferences() {
		SharedPreferences sp = getSharedPreferences("styleSharedPreferences", 0);
		sizeOfText = "font-size:"+ (Integer.valueOf(sp.getString("SeekBar", "0")) + 12) + "px;";
		RGB = sp.getString("RGB", "color:rgb(172,173,131);");
		return Integer.valueOf(sp.getString("RadioButton", "0"));
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// Handle the back button
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// Ask the user if they want to quit
			new AlertDialog.Builder(this)
					.setIcon(android.R.drawable.ic_dialog_alert)
					.setTitle(R.string.quit)
					.setMessage(R.string.really_quit)
					.setPositiveButton(R.string.yes,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,	int which) {
									System.exit(-1);
									finish();
								}
							}).setNegativeButton(R.string.no, null).show();
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_ENTER) {
			//Log.d("KEY",KeyEvent.KEYCODE_ENTER+"");
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
			try {
				searchIt();
			} catch (Exception e) {
				//Log.d("KEY",KeyEvent.KEYCODE_ENTER+"");
			}
			
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}

	}

	public void searchIt(String s) {
		// search = s;
		searchAlgorithm(spinnerString(spinner));
	}

	public void searchIt() {
		database = spinnerAdapter.dbnameFromTable((String) spinner.getSelectedItem());
		databaseHelper = new DataBaseHelper(getApplicationContext(),database);
		searchAlgorithm(spinnerString(spinner));
		databaseHelper.close();
		spinnerAdapter.close();
	}

	/*
	 * This method returns Spinners selection
	 */
	public String spinnerString(Spinner spinner) {
		Log.d("Eroor","Burdadi");
		Log.d("Spinnerrrr",spinner.getSelectedItem().toString());
		return spinner.getSelectedItem().toString();
	}

	/*
	 * This method returns EditText value
	 */
	public String editTextString(EditText editText) {
		return editText.getText().toString();
	}

	/*
	 * This is the Core Search Algorithm
	 */

	@SuppressWarnings({ "deprecation"})
	public void searchAlgorithm(String Text) {
		
		//SavePreferens("languages", Text);
		// Checks that word is clean
		if (!checkText(editText)) {
			Toast.makeText(getApplicationContext(), "There is no such word in our Database", Toast.LENGTH_LONG).show();
		} else
			try {
				
				Cursor cursor = databaseHelper.queryLike(editTextString(editText));
				String[] from = new String[] { DataBaseHelper.TABLE_FROM };
				int[] to = new int[] { R.id.text };
				cursorAdapter = new SimpleCursorAdapter(getApplicationContext(), R.layout.row, cursor, from, to);
				listContent.setAdapter(cursorAdapter);
				listContent.setTextFilterEnabled(true);
				listContent.setTranscriptMode(ListView.CHOICE_MODE_NONE);
				
				
				listContent.setOnItemLongClickListener(new OnItemLongClickListener() {
							@Override
							public boolean onItemLongClick(AdapterView<?> arg0,	View arg1, int arg2, long arg3) {
								Toast.makeText(getApplicationContext(), "OnItemLongClickListener",Toast.LENGTH_SHORT).show();
								return false;
							}
						});
				
				listContent.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						//view.setBackgroundResource(R.color.Coral);						 
						database = spinnerAdapter.dbnameFromTable((String) spinner.getSelectedItem());
						spinnerAdapter.close(); // // //
						databaseHelper = new DataBaseHelper(getApplicationContext(), database);
//#39abdf
						//setLayoutParams(new AbsListView.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
						switch (LoadstyleSharedPreferences()) {
						case 0:
							WebViewTranslateOnTextView(view, id);
							break;
						case 1:
							WebViewTranslateOnToast(view, id);
							break;
						case 2:
							WebViewTranslateOnTextView(view, id);
							break;
						default:
							WebViewTranslateOnTextView(view, id);
							break;
						}
					}
				});
				
				
				listContent.setOnScrollListener(new OnScrollListener() {
					
					@Override
					public void onScrollStateChanged(AbsListView view, int scrollState) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
						 System.out.println("\n"+"first"+ firstVisibleItem+"visible"+visibleItemCount+"total"+ totalItemCount);
					}
				});

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				spinnerAdapter.close();
				databaseHelper.close();
			}

	}

	@Override
	public void update(Observable observable, Object data) {
		// Log.d("DataOfObserver", data+"");
	}

	public void buttonAction(Button button, final String letter) {
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				editText.append(letter);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_polydroid, menu);
		return true;
	}
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.exit:
			System.exit(-1);
			finish();
			return false;

//		case R.id.info:
//			LayoutInflater inflater = getLayoutInflater();
//			View view = inflater.inflate(R.layout.info,	(ViewGroup) findViewById(R.id.relativeLayout1));
//			Toast toast = new Toast(this);
//			toast.setView(view);
//			toast.setGravity(Gravity.LEFT, 0, 0);
//			toast.setDuration(Toast.LENGTH_LONG);
//			toast.show();
//			return true;
			
		case R.id.menu_item_info:
			i = new Intent(getApplicationContext(), com.togrul.polydroidofflinedictionary.settings.SettingsActivity.class);
			i.putExtra("FirstTab", 2);
			startActivity(i);
			return true;

		case R.id.menu_item_settings:
			i = new Intent(getApplicationContext(),
					com.togrul.polydroidofflinedictionary.settings.SettingsActivity.class);
			startActivity(i);
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/*
	 * version 1.0 Pop Up WebView that shows translated word. This is first
	 * method
	 */
	public void WebViewTranslateOnToast(View view, long id) {
		dialog = new Dialog(PolyDroidActivity.this);
		dialog.setContentView(R.layout.word);
		dialog.setTitle("PolyDroid Offline Dictionary");
		dialog.setCancelable(true);

		WebView webView = (WebView) dialog.findViewById(R.id.WebView);
		try {
			Cursor c = databaseHelper.query(((TextView) view).getText().toString(), spinnerText, id);
			c.moveToFirst();
			String s = c.getString(c.getColumnIndex("_to"));
			// Translated word
		    
		    
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
		} catch (Exception e) {
			// Log.d("WebView", e.toString());
		} finally {
			databaseHelper.close();
		}

		ImageButton button = (ImageButton) dialog.findViewById(R.id.exit);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.cancel();
			}
		});
		dialog.show();
	}

	/*
	 * version 1.0
	 */

	public void WebViewTranslateOnTextView(View view, long id) {
		WebViewTranslateOnTextView txt = new WebViewTranslateOnTextView();
		// txt.getData(view, id,mySQLiteAdapter, spinnerText);
		txt.getData(view, id, databaseHelper, spinnerText);
		Intent i = new Intent(getApplicationContext(), WebViewTranslateOnTextView.class);
		startActivity(i);
	}

	/*
	 * Checker checks EditText value that it will not be a word that there is no
	 * such table Like 'Ğ', 'ğ', 'ı', ' ' , '\t' and etc.
	 */
	public boolean checkText(EditText text) {
		boolean safe = false;

		if (text.equals("Ğ") || text.equals("ğ") || text.equals("ı")
				|| text.equals(" "))
			safe = false;
		else
			safe = true;

		return safe;
	}
}

/**
 * This is the class being observed.
 * @version 1.0.0
 */
class BeingWatched extends Observable {
	public static String s;

	String Send(String s) {
		// Log.d("Observable",s);
		return BeingWatched.s = s;
	}

	void Send() {
		setChanged();
		notifyObservers(new String[] { BeingWatched.s });
	}
}