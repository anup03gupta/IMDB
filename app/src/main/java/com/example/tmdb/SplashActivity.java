package com.example.tmdb;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;

public class SplashActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		Thread splash_screen = new Thread() {
			public void run() {
				try {
					sleep(5000);
				}

				catch (Exception e) {
					e.printStackTrace();
				} finally {
					startActivity(new Intent(getApplicationContext(),
							TMDBmainactivity.class));
					finish();
				}
			}
		};
		splash_screen.start();
	}

}
