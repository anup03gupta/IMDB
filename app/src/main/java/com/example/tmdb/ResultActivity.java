package com.example.tmdb;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.ListView;

public class ResultActivity extends Activity {

	int loader = R.drawable.ic_launcher;
	static String TAG_RESULTS = "results";
	static String TAG_BACKDROP_PATH = "backdrop_path";
	static String TAG_ID = "id";
	static String TAG_NAME = "title";
	static String TAG_LANGUAGE = "original_language";
	static String TAG_OVERVIEW = "overview";
	static String TAG_POSTER = "poster_path";
	static String TAG_POPULARITY = "popularity";
	static String TAG_RELEASE = "release_date";
	static String urlb = "http://image.tmdb.org/t/p/w500/";
	String url; 
	JSONArray results = null;
	ListView listview;
	SearchAdapter adapter;
	ArrayList<HashMap<String, String>> resultList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		
		resultList = new ArrayList<HashMap<String, String>>();
		listview = (ListView) findViewById(R.id.listView1);
		url = getIntent().getStringExtra("com.example.searchbar.input");
		new GetResults(this).execute();
	}
	private class GetResults extends AsyncTask<Void, Void, Void> {

		private ProgressDialog pDialog;
		private Context context;

		public GetResults(Context context) {

			this.context = context;
			pDialog = new ProgressDialog(ResultActivity.this);
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
			//image = (ImageView) findViewById(R.id.imageView1);

			Log.d("Response: ", "> " + jsonStr);
			if (jsonStr != null) {
				try {

					JSONObject jsonObj = new JSONObject(jsonStr);
					results = jsonObj.getJSONArray(TAG_RESULTS);
					for (int i = 0; i < results.length(); i++) {
						JSONObject c = results.getJSONObject(i);
						String id = c.getString(TAG_ID);
						String name = c.getString(TAG_NAME);
						String overview = c.getString(TAG_OVERVIEW);
						String popularity = c.getString(TAG_POPULARITY);
						String bckdrp = c.getString(TAG_BACKDROP_PATH);
						String release = c.getString(TAG_RELEASE);
						String poster = c.getString(TAG_POSTER);
						String p = url + bckdrp;
						
						
						HashMap<String, String> result = new HashMap<String, String>();
						result.put(TAG_ID, id);
						result.put(TAG_RELEASE, release);
						result.put(TAG_NAME, name);
						result.put(TAG_OVERVIEW, overview);
						result.put(TAG_POPULARITY, popularity);
						result.put(TAG_POSTER, poster);
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
			
			adapter = new SearchAdapter(ResultActivity.this, resultList);
			listview.setAdapter(adapter);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.result, menu);
		return true;
	}

}
