package com.example.dexter;

import java.util.List;

public class Route {
	
	
	List<Double> steps;
	
	Location start_location;
	
	public Route(Location start_location, List<Double> step_dist) {
		// TODO Auto-generated constructor stub
		this.steps = step_dist;
		
		this.start_location = start_location;
	}

}
