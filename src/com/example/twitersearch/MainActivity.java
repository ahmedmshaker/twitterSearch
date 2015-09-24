package com.example.twitersearch;

import java.util.Arrays;

import android.net.Uri;
import android.os.Bundle;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class MainActivity extends Activity {
	private SharedPreferences savedsearch;
    private	Button saved;
	private AutoCompleteTextView auto;
	private EditText edits;
	private TableLayout layouts;
	private Button clears;
	public final static String[] locatin={"google",
		"java",
		"microsoft",
		"sun",
		"appele",
		"sumsong",
		"dell",
		"toshipa",
		"zamalek","ahly"
		,"Dwight D. Eisenhower",
		"John F. Kennedy",
		"Lyndon B. Johnson",
		"Richard Nixon",
		"Gerald Ford",
		"Jimmy Carter",
		"Ronald Reagan",
		"George H. W. Bush",
		"Bill Clinton",
		"George W. Bush",
		"Barack Obama"
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ArrayAdapter<CharSequence> adapter=new ArrayAdapter<CharSequence>(getApplicationContext(),android.R.layout.simple_expandable_list_item_1, locatin);
		savedsearch=getSharedPreferences("savedsearched", MODE_PRIVATE);
		layouts=(TableLayout) findViewById(R.id.table2);
		auto=(AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
		auto.setThreshold(1);
		auto.setAdapter(adapter);
		edits=(EditText) findViewById(R.id.editText1);
		saved=(Button) findViewById(R.id.button1);
	    clears=(Button) findViewById(R.id.button2);
	 
	    saved.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if((auto.getText().length()>0)&&(edits.getText().length()>0)){
				makeGui(auto.getText().toString(), edits.getText().toString());
				auto.setText("");
				edits.setText("");
			}else{
				AlertDialog.Builder bui=new AlertDialog.Builder(MainActivity.this);
				bui.setTitle(R.string.titesave);
				bui.setPositiveButton(R.string.oks,null);
			//	bui.setNegativeButton(R.string.cancels, null);
				bui.setMessage(R.string.meeagesave);
				AlertDialog errord=bui.create();
				errord.show();
				Toast.makeText(getApplicationContext(), "errer entered", Toast.LENGTH_LONG).show();
			}
		}
	}); 
	    clears.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				AlertDialog.Builder clearalert=new AlertDialog.Builder(MainActivity.this);
				clearalert.setTitle(R.string.title);
				clearalert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						clearbutton();
						SharedPreferences.Editor prefg=savedsearch.edit();
						prefg.clear();
						prefg.apply(); 
					}
				});
				clearalert.setMessage(R.string.masseage);
				clearalert.setCancelable(true);
				clearalert.setNegativeButton(R.string.cancels, null);
				AlertDialog aler=clearalert.create();
				aler.show();
				
			}
		});
	    refershbutton(null);

	}
	private void refershbutton(String object) {
		// TODO Auto-generated method stub
		String[] tagedsaved=savedsearch.getAll().keySet().toArray(new String[0]);
		Arrays.sort(tagedsaved,String.CASE_INSENSITIVE_ORDER);
		if(object!=null){
			makeGui(object,Arrays.binarySearch(tagedsaved, object));
		}else{
			for(int i=0;i<tagedsaved.length;++i){
				makeGui(tagedsaved[i],i);
			}
		}
		
	}
	private void makeGui(String object, int binarySearch) {
		// TODO Auto-generated method stub
		LayoutInflater iflater=(LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		View v=iflater.inflate(R.layout.tow_button, null);
		Button searchbutton=(Button) v.findViewById(R.id.btn1);
		searchbutton.setText(object);
		searchbutton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String textbutton=((Button)arg0).getText().toString();
				String quarys=savedsearch.getString(textbutton, null);
				String searchedbut=getString(R.string.searchedbtn)+quarys;
				Intent i=new Intent(Intent.ACTION_VIEW,Uri.parse(searchedbut));
				startActivity(i);
			}
		});
		Button editbutton=(Button) v.findViewById(R.id.btn2);
		editbutton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				TableRow tblr=(TableRow) arg0.getParent();
				Button tagedsearched=(Button) tblr.findViewById(R.id.btn1);
				String dg=tagedsearched.getText().toString();
				edits.setText(dg);
				String ahmed=savedsearch.getString(dg, null);
				auto.setText(ahmed);
			}
		});
		layouts.addView(v,binarySearch);
	}
	void makeGui(String object,String tag){
		String orig=savedsearch.getString(tag, null);
		SharedPreferences.Editor pref=savedsearch.edit();
		pref.putString(tag, object);
		pref.apply();
		if(orig==null){
			refershbutton(tag);
		}
	}
	

private void clearbutton(){
	layouts.removeAllViews();
}
}

