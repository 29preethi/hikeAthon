package com.example.dexter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ParseAPI {
	
	private String API_KEY ="AIzaSyAUXTwdRh5o4h893JcfMbEEOCuy0Qwq1Ro";
	
	private Location start_location;
	
	public void parseAPI(String origin, String destination) {
		
		//origin = "HSR Layout,Bangalore";
		//destination = "Silk Board,Bangalore";
		start_location = new Location(Double.parseDouble(origin.split(",")[0]), Double.parseDouble(origin.split(",")[1]));
		
		try {
			origin = URLEncoder.encode(origin, "UTF-8");
		} catch (UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		
		try {
			destination = URLEncoder.encode(destination, "UTF-8");
		} catch (UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		
		URL url = null;
		try {
			url = new URL("https://maps.googleapis.com/maps/api/directions/json?origin="+origin+"&destination="+destination+"&mode=transit&alternate=true&key="+API_KEY);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//APi call
		System.out.println("url is "+url);

		HttpURLConnection urlConnection = null;
		
		InputStream inStream = null;
		try {
		    
		    urlConnection = (HttpURLConnection) url.openConnection();
		    
		    urlConnection.connect();
		    
		    JsonParser jp = new JsonParser (); //from gson
			
		    JsonObject root = (JsonObject) jp.parse(new InputStreamReader((InputStream) urlConnection.getContent()));
		    
		    System.out.println("Extracted string is "+root);
		    
		    parseJSONResponse(root.toString());
		        
		} catch (Exception e) {
		    System.out.println(e);
		} finally {
		    if (inStream != null) {
		        try {
		            // this will close the bReader as well
		            inStream.close();
		        } catch (IOException ignored) {
		        }
		    }
		    if (urlConnection != null) {
		        urlConnection.disconnect();
		    }
		}
	}
		
	

	public void parseJSONResponse(String response) {

		List<Double> step_dist ;
		
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(response);
		
		// routesArray contains ALL routes
		JSONArray routesArray = jsonObject.getJSONArray("routes");
		
		//get each route
		for(int i = 0; i < routesArray.length(); i++ ) {
			
			step_dist = new ArrayList<Double>();
		
			JSONObject route = routesArray.getJSONObject(i);
		
			JSONArray legs = route.getJSONArray("legs");
			
			// Grab first leg
			JSONObject leg = legs.getJSONObject(0);
			
			JSONArray steps = leg.getJSONArray("steps");
			
			System.out.println("length of steps of "+i+" "+steps.length());
			for(int j = 0; j < steps.length(); j++) {
				
				JSONObject step = steps.getJSONObject(j);
				
				int distance =  (int) step.getJSONObject("distance").get("value");
				
				System.out.println("Distance is "+distance+": "+start_location.lat+" "+start_location.lng);
				
				step_dist.add((double) distance);
				
			}
			
			AltRoutes.altroutes.add(new Route(start_location,step_dist));
		}
			
					
			
		
		
		}catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
