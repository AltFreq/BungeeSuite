package com.minecraftdimensions.bungeesuite.objects;

import com.minecraftdimensions.bungeesuite.managers.WarpManager;

public class Portal {
	String name;
	String server;
	String fillType;
	boolean target; //false portal, true warp
	String targetName;
	Region region;
	
	
	public Portal(String name, String server, String fillType, boolean target, String targetName, Region region){
		this.name = name;
		this.server = server;
		this.fillType = fillType;
		this.target = target;
		this.targetName = targetName;
		this.region = region;
	}
	
	public Portal(String serialised){
		String data [] = serialised.split("~$~");
		name = data[0];
		server = data[1];
		fillType = data[2];
		target = Boolean.parseBoolean(data[3]);
		targetName = data[3];
		region = new Region(data[4]);
	}
	
	public void sendPlayerToDestination(BSPlayer player){
		if(target){
		WarpManager.getWarp(targetName).getLocation().teleportPlayerToLocation(player);
		}else{
			player.sendToServer(targetName);
		}
	}
	
	public String getName(){
		return name;
	}
	
	public String serialise(){
		return name+"~$~"+server+"~$~"+fillType+"~$~"+target+"~$~"+targetName+"~$~"+region.serialise();
	}

	public void setRegion(Region region) {
		this.region = region;	
	}

	public String getServer() {
		return server;
	}
	public String getWorld(){
		return region.max.getWorld();
	}

	public String getFillType() {
		return fillType;
	}
}
