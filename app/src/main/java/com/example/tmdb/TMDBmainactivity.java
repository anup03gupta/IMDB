package com.example.tmdb;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class TMDBmainactivity<MainActivity> extends Activity {

	// URL to get contacts JSON
	private static String url = "https://api.themoviedb.org/3/movie/upcoming?api_key=dfddeb5ab1078893dcef34ef5e62bcfb";
	// JSON Node names
	int loader = R.drawable.ic_launcher;
	static String TAG_RESULTS = "results";
	static String TAG_BACKDROP_PATH = "backdrop_path";
	static String TAG_ID = "id";
	static String TAG_NAME = "title";
	static String TAG_LANGUAGE = "original_language";
	static String TAG_OVERVIEW = "overview";
	static String TAG_POSTER = "poster_path";
	static String TAG_POPULARITY = "vote_average";
	static String TAG_RELEASE = "release_date";
	static String urlb = "http://image.tmdb.org/t/p/w500/";

	ListView listview;
	CustomAdapter adapter;
	ImageView image;
	// contacts JSONArray
	JSONArray results = null;
	// Hashmap for ListView
	ArrayList<HashMap<String, String>> resultList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tmdbmainactivity);
		
		if (check()){
		listview = (ListView) findViewById(R.id.listview);
		resultList = new ArrayList<HashMap<String, String>>();
		new GetResults(this).execute();
		}
		else
			Toast.makeText(this, "Connect to network", Toast.LENGTH_LONG).show();
	}

	private class GetResults extends AsyncTask<Void, Void, Void> {

		private ProgressDialog pDialog;
		private Context context;

		public GetResults(Context context) {

			this.context = context;
			pDialog = new ProgressDialog(TMDBmainactivity.this);
			pDialog.setTitle("Downloading...");
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(false);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {

			ServiceHandler sh = new ServiceHandler();
			String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
			image = (ImageView) findViewById(R.id.imageView1);

			Log.d("Response: ", "> " + jsonStr);
			if (jsonStr != null) {
				try {

					JSONObject jsonObj = new JSONObject(jsonStr);
					results = jsonObj.getJSONArray(TAG_RESULTS);
					for (int i = 0; i < results.length(); i++) {
						JSONObject c = results.getJSONObject(i);
						String id = c.getString(TAG_ID);
						String name = c.getString(TAG_NAME);
						String language = c.getString(TAG_LANGUAGE);
						String overview = c.getString(TAG_OVERVIEW);
						String popularity = c.getString(TAG_POPULARITY);
						String bckdrp = c.getString(TAG_BACKDROP_PATH);
						String release = "Release Date:"+c.getString(TAG_RELEASE);
						String poster = c.getString(TAG_POSTER);
						String p = url + bckdrp;
						popularity="Rating:"+popularity+"/10";
						Log.d("url", p);

						HashMap<String, String> result = new HashMap<String, String>();
						result.put(TAG_ID, id);
						result.put(TAG_RELEASE, release);
						result.put(TAG_NAME, name);
						result.put(TAG_OVERVIEW, overview);
						result.put(TAG_POPULARITY, popularity);
						result.put(TAG_BACKDROP_PATH, bckdrp);
						resultList.add(result);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				Log.e("ServiceHandler", "Couldn't get any data from the url");
			}
			return null;
		}

		

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (pDialog.isShowing())
				pDialog.dismiss();

			adapter = new CustomAdapter(TMDBmainactivity.this, resultList);
			listview.setAdapter(adapter);
		}
	}
	public boolean check(){
		
		ConnectivityManager connectivityManager = (ConnectivityManager) 
				this.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = 
				connectivityManager.getActiveNetworkInfo(); 
				if 
				(networkInfo!=null){
				 return true;
				}
				else
					return false;
		
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tmdbmainactivity, menu);
		return true;
	}

	@Override
	protected void onStart() {

		super.onStart();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		switch (id) {

		case R.id.about:

			Intent settingIntent = new Intent(TMDBmainactivity.this,
					AboutActivity.class);
			startActivity(settingIntent);
			break;
		case R.id.contact:
			Intent settingIntent1 = new Intent(TMDBmainactivity.this,
					ContactUsActivity.class);
			startActivity(settingIntent1);
			break;
		case R.id.search:
			Intent settingintent = new Intent(TMDBmainactivity.this,
					SearchbarActivity.class);
			startActivity(settingintent);
			break;
		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}
}
