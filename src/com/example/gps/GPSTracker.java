package com.example.gps;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

public class GPSTracker implements LocationListener {
	private final Context mContext;
	boolean canGetLocation 		=	false;
	boolean isGPSEnabled		=	false;
	boolean isNetworkEnabled	=	false;

	protected LocationManager locationManager;

	Location location;
	double latitude; // latitude
    double longitude; // longitude

	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1;	//1 meter(s)
    private static final long MIN_TIME_BW_UPDATES = 1000*60*1;		//1 minute(s)

	public GPSTracker(Context context) {
		this.mContext = context;
		this.getLocation();
	}

	public Location getLocation() {
		try {
			locationManager = (LocationManager)mContext.getSystemService(Context.LOCATION_SERVICE);

			isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

			isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

			if(!isGPSEnabled && !isNetworkEnabled) {
				Log.e("LALA", "POSITIONING IS NOT ENABLED");
			}
			else {
				this.canGetLocation = true;

				if(isNetworkEnabled) {
					locationManager.requestLocationUpdates(
                            			LocationManager.NETWORK_PROVIDER,
                            			MIN_TIME_BW_UPDATES,
                            			MIN_DISTANCE_CHANGE_FOR_UPDATES,
                            			this
                            		);

                    Log.e("LALA", "Get coordinates by Network");

                    if(locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if(location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
				}

				if(isGPSEnabled) {
					if(location == null) {
						locationManager.requestLocationUpdates(
											LocationManager.GPS_PROVIDER,
											MIN_TIME_BW_UPDATES,
											MIN_DISTANCE_CHANGE_FOR_UPDATES,
											this
										);

						Log.e("LALA", "GPS Enabled");

						if(locationManager != null) {
							location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

	                        if(location != null) {
	                        	latitude = location.getLatitude();
	                            longitude = location.getLongitude();

	                            Log.e("LALA", "LAT: "+latitude+", LONG: "+longitude);
	                        }
	                        else Log.e("LALA", "CAN'T GET LOCATION OBJECT");
	                    }
					}
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		return location;
	}

	public double getLatitude() {
		if(location != null) latitude = location.getLatitude();

        return latitude;
	}

	public double getLongitude() {
		if(location != null) longitude = location.getLongitude();

		return longitude;
	}

	public boolean canGetLocation() {
		return this.canGetLocation;
	}

	public void showSettingsAlert(){
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        // Setting Dialog Title
		alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
		alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            	dialog.cancel();
            }
        });

        alertDialog.show();
    }

	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
	}
}
