package com.minecraftdimensions.bungeesuite.objects;

public class Region {
	
	Location max;
	Location min;
	
	public Region (Location max, Location min){
		this.max = max;
		this.min = min;
	}
	
	public Region(String serialised){
		String locations []= serialised.split("~!~");
		max = new Location(locations[0]);
		min = new Location(locations[1]);
	}
	
	public Location getMax(){
		return max;	
	}
	
	public Location getMin(){
		return min;
	}
	
	public String serialise(){
		return max.serialise()+"~!~"+min.serialise();
	}

}
