package com.minecraftdimensions.bungeesuite.objects;

import com.minecraftdimensions.bungeesuite.managers.PlayerManager;

import net.md_5.bungee.api.config.ServerInfo;

public class Home {
	private String owner;
	private String name;
	private Location loc;

	public String getOwner(){
		return owner;
	}
	public void setOwner(String owner){
		this.owner = owner;
	}
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name=name;
	}
	public ServerInfo getServer(){
		return loc.getServer();
	}
	public void setServer(String server){
		loc.setServer(server);
	}
	public String getWorld(){
		return loc.getWorld();
	}
	public void setWorld(String world){
		loc.setWorld(world);
	}
	public double getX(){
		return loc.getX();
	}
	public double getY(){
		return loc.getY();
	}
	public double getZ(){
		return loc.getZ();
	}
	public void setX(double x){
		loc.setX(x);
	}
	public void setY(double y){
		loc.setY(y);
	}
	public void setZ(double z){
		loc.setZ(z);
	}
	public float getYaw(){
		return loc.getYaw();
	}
	public float getPitch(){
		return loc.getPitch();
	}
	public void setYaw(float yaw){
		loc.setYaw(yaw);
	}
	public void setPitch(float pitch){
		loc.setPitch(pitch);
	}
	public String getLocationString(){
		return loc.serialise();
	}
	public void teleportPlayerToHome(String player){
		PlayerManager.getSimilarPlayer(player).teleportPlayerToLocation(loc);
	}
	public Location getLocation() {
		return loc;
	}
}
