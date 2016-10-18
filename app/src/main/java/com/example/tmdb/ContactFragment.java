package com.example.tmdb;

import android.os.Bundle;
import android.preference.PreferenceFragment;

public class ContactFragment extends PreferenceFragment {
@Override
public void onCreate(Bundle savedInstanceState) {
	
	super.onCreate(savedInstanceState);
	addPreferencesFromResource(R.xml.preference);
}

}
