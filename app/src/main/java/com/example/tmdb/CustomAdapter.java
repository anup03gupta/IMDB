package com.example.tmdb;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {

	private Context context;
	ArrayList<HashMap<String, String>> data;
	private LayoutInflater inflater;

	public CustomAdapter(Context context,
			ArrayList<HashMap<String, String>> arraylist) {
		this.data = arraylist;
		this.context = context;
		/*
		 * if (!ModelSingletone.create().isEmpty()){ this.data =
		 * ModelSingletone.create().getDatasource(); }
		 */

		inflater = (LayoutInflater) this.context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	@Override
	public int getCount() {

		if (null != data) {

			return data.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int i) {
		return null;
	}

	@Override
	public long getItemId(int i) {
		return 0;
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {

		if (view == null) {
			view = inflater.inflate(R.layout.custom_row, null);
		}

		TextView overviewTV = (TextView) view.findViewById(R.id.overview);
		TextView popularityTV = (TextView) view.findViewById(R.id.popularity);
		TextView titleTV = (TextView) view.findViewById(R.id.name);
		TextView releaseTV = (TextView) view.findViewById(R.id.release);
		ImageView imageViewMovie = (ImageView) view
				.findViewById(R.id.imageView1);

		// -- Set Value --

		overviewTV.setText(data.get(i).get(TMDBmainactivity.TAG_OVERVIEW));
		popularityTV.setText(data.get(i).get(TMDBmainactivity.TAG_POPULARITY));
		titleTV.setText(data.get(i).get(TMDBmainactivity.TAG_NAME));
		releaseTV.setText(data.get(i).get(TMDBmainactivity.TAG_RELEASE));
		String imagePath = TMDBmainactivity.urlb
				+ data.get(i).get(TMDBmainactivity.TAG_BACKDROP_PATH);
		new ImageDownloadTask(imageViewMovie, context).execute(imagePath);
		Log.d("url2", imagePath);

		return view;
	}

	class ImageDownloadTask extends AsyncTask<String, Void, Bitmap> {

		private Context context;
		private ImageView imageView;
		private static final String GET = "GET";
		private int timeoutMillis = 1000 * 60;
		private int connectionTimeOut = 1000 * 30;

		ImageDownloadTask(ImageView imageView, Context context) {
			this.context = context;
			this.imageView = imageView;
		}

		@Override
		protected Bitmap doInBackground(String... strings) {

			try {

				URL url = new URL(strings[0]);
				Log.d("url3", strings[0]);
				HttpURLConnection httpURLConnection = (HttpURLConnection) url
						.openConnection();

				httpURLConnection.setRequestMethod(GET);
				httpURLConnection.setReadTimeout(timeoutMillis);
				httpURLConnection.setConnectTimeout(connectionTimeOut);
				httpURLConnection.setDoInput(true);

				httpURLConnection.connect();
				int responseCode = httpURLConnection.getResponseCode();
				if (responseCode == HttpURLConnection.HTTP_OK) {

					InputStream inputStream = httpURLConnection
							.getInputStream();
					if (null != inputStream) {
						Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
						return bitmap;
					}

				}

			} catch (MalformedURLException e) {

			} catch (IOException e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Bitmap bitmap) {
			super.onPostExecute(bitmap);

			if (null != bitmap) {
				this.imageView.setImageBitmap(bitmap);
			}
		}
	}
}