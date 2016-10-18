package com.example.tmdb;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SearchbarActivity extends Activity {
	private Button searchButton;
	public String search_url;
	EditText editText;
	String search;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_searchbar);
		
		getFragmentManager().beginTransaction().replace(R.id.search,new HomeSearchFragment())
		.commit();	
		editText = (EditText) findViewById(R.id.editText1);
		search= editText.getText().toString();
		searchButton=(Button) findViewById(R.id.search_button);
		searchButton.setOnClickListener(new OnClickListener() {
		@Override
				public void onClick(View v) {
						search = editText.getText().toString();
						if(search.equals("")||search.equals(" ")|| search.equals("  ")|| search.equals("   ")|| search.equals("    ")){
							Toast.makeText(SearchbarActivity.this, "Enter movie name", Toast.LENGTH_LONG).show();
						}
						else{
						search=replacespace(search);
						search_url = "http://api.themoviedb.org/3/search/movie?query="+search+"&api_key=dfddeb5ab1078893dcef34ef5e62bcfb";
						
						Intent intent= new Intent(SearchbarActivity.this,ResultActivity.class);
						intent.putExtra("com.example.searchbar.input",search_url);
						startActivity(intent);
						}
					}
				});
			}
			
	
		
				
	

	
	

	protected String replacespace(String s) {
		// TODO Auto-generated method stub
		int l = s.length();
		char c;
		String nu="";
		for(int i=0; i<l; i++)
		{
			c=s.charAt(i);
			if(c==' ')
				nu=nu+"%20";
			else
				nu+=c;
		}
		return nu;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.searchbar, menu);
		return true;
	}

}
