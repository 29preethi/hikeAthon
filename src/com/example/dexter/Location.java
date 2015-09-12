package com.example.dexter;

public class Location {

	double lat;
	
	double lng;
	
	public Location(double d, double e) {
		// TODO Auto-generated constructor stub
		this.lat = d;
		
		this.lng = e;
	}
	
	public String toString() {
		
		return lat+","+lng;
		
	}

	
}
