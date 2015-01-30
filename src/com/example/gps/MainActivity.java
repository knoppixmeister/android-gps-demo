package com.example.gps;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void getGPS(View v) {
		GPSTracker gps = new GPSTracker(MainActivity.this);

		if(gps.canGetLocation()) {
			double latitude = gps.getLatitude();
			double longitude = gps.getLongitude();

			Toast.makeText(
						getApplicationContext(),
						"Your Location is - \nLat: "+latitude+"\nLong: "+longitude,
						Toast.LENGTH_SHORT
					).show();
		}
		else gps.showSettingsAlert();
	}
}
