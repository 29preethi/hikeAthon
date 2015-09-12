package com.example.dexter;

import android.app.Activity;
import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;


public class Tracker extends Activity implements LocationListener{

	LocationManager locationManager;
	
	Button select;
	
	Location start_location;
	
	TextView origin;
	
	TextView dest;
	
	final double DIST_THRESHOLD = 200;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_tracker);

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

		StrictMode.setThreadPolicy(policy); 

		String destination = getIntent().getStringExtra("destination");
		
		System.out.println("dest is "+destination);
		
		dest = (TextView)findViewById(R.id.dest);
		
		dest.setText(destination);

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

		android.location.Location current = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER) ;
		
		start_location = new Location(current.getLatitude(), current.getLongitude());
		
		System.out.println("Start location is "+start_location.lat+" , "+start_location.lng);
		
		ParseAPI parseAPI = new ParseAPI();
		
		parseAPI.parseAPI(start_location.toString(), destination);
		
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1000, this);
		

	}

	
	@Override
	public void onProviderDisabled(String provider) {
		Log.d("Latitude","disable");
	}

	@Override
	public void onProviderEnabled(String provider) {
		Log.d("Latitude","enable");
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		Log.d("Latitude","status");
	}

	@Override
	public void onLocationChanged(android.location.Location location) {
		// TODO Auto-generated method stub

		origin = (TextView) findViewById(R.id.origin);
		
		origin.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
		
		for(Route r : AltRoutes.altroutes){
						
			double current_dist = getDistance(r.start_location.lat, r.start_location.lng, location.getLatitude(), location.getLongitude());
			
			double min = Double.MAX_VALUE;
			
			for(double step : r.steps) {
				
				if((current_dist-step) < min)
					min = current_dist-step;
				
			}
			
			if(min > DIST_THRESHOLD ) {
			
				sendSMS("9538729218","My current location is "+location.getLatitude()+","+location.getLongitude()+" deviated more than 200m from intended path");
			
			}
		}
		
	}
	
	private double getDistance(double lat1, double lon1, double lat2, double lon2) {
	      double theta = lon1 - lon2;
	      double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
	      dist = Math.acos(dist);
	      dist = rad2deg(dist);
	      dist = dist * 60 * 1.1515;
	       return (dist);
	    }

	   private double deg2rad(double deg) {
	      return (deg * Math.PI / 180.0);
	    }
	   private double rad2deg(double rad) {
	      return (rad * 180.0 / Math.PI);
	    }

	private void sendSMS(String phoneNumber, String message)
	{
		SmsManager sms = SmsManager.getDefault();
		System.out.println(phoneNumber+" "+message);
		sms.sendTextMessage(phoneNumber, null, message, null, null);
	}


}

