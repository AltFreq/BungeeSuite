package com.minecraftdimensions.bungeesuite.objects;

public class Warp {
	private Location loc;
	private String name;
	private boolean hidden;
	
	public Warp(String name, Location loc, boolean hidden){
		this.name=name;
		this.loc = loc;
		this.hidden = hidden;
	}
	
	public void setLocation(Location loc){
		this.loc = loc;
	}
	
	public Location getLocation(){
		return loc;
	}
	
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	
	public boolean isHidden(){
		return hidden;
	}
	
	public void setHidden(boolean hidden){
		this.hidden = hidden;
	}
	
	
}
