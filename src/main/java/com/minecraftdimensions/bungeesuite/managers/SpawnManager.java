package com.minecraftdimensions.bungeesuite.managers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import com.minecraftdimensions.bungeesuite.objects.Location;
import com.minecraftdimensions.bungeesuite.objects.Messages;
import com.minecraftdimensions.bungeesuite.objects.Spawn;
import com.minecraftdimensions.bungeesuite.objects.BSPlayer;

public class SpawnManager {
		static HashMap<String,Spawn> spawns;
	
	public static void loadSpawnLocations() throws SQLException{
		spawns = new HashMap<String,Spawn>();
		ResultSet res = SQLManager.sqlQuery("SELECT * FROM BungeeSpawns");
		while (res.next()){
			createSpawn(res.getString("spawnname"), new Location(res.getString("server"), res.getString("world"), res.getDouble("x"), res.getDouble("y"), res.getDouble("z"), res.getFloat("yaw"), res.getFloat("pitch")));
		}
		res.close();
	}
	
	public static void createSpawn(String name, Location loc){
		Spawn s = new Spawn(name, loc);
		spawns.put(name, s);
	}
	
	public static void setSpawn(BSPlayer sender, String name, Location loc) throws SQLException{
		Spawn s;
		if(doesSpawnExist(name)){
			s =spawns.get(name);
			s.setLocation(loc);
			SQLManager.standardQuery("UPDATE BungeeSpawns SET server='" + loc.getServer()
					+ "', world='" + loc.getWorld() + "', x=" + loc.getX() + ", y=" + loc.getY() + ", z="
					+ loc.getZ() + ", yaw=" + loc.getYaw() + ", pitch= " + loc.getPitch()
					+ " WHERE spawnname='" + name + "'");
			sender.sendMessage(Messages.SPAWN_UPDATED);
		}else{
			s = new Spawn(name, loc);
			spawns.put(name, s);
			SQLManager.standardQuery("INSERT INTO BungeeSpawns VALUES ('"+name+"', '"+loc.getServer()+"','"+loc.getWorld()+"',"+loc.getX()+","+loc.getY()+","+loc.getZ()+","+loc.getYaw()+","+loc.getPitch()+")");
			sender.sendMessage(Messages.SPAWN_SET);
		}
	}
	
	public static Spawn getGlobalSpawn(){
		return spawns.get("spawn");
	}
	
	public Spawn getSpawn(String name){
		return spawns.get(name);
	}
	
	public Spawn getNewSpawn(String server,String world){
		return spawns.get(server+"-"+world);
	}
	
	public static Spawn getNewPlayerSpawn(){
		if(doesNewPlayerSpawnExist()){
			return spawns.get("newplayerspawn");
		}else if(doesGlobalSpawnExist()){
			return getGlobalSpawn();
		}else{
			LoggingManager.log(Messages.SPAWN_DOES_NOT_EXIST);
			return null;
		}
	}
	
	public static boolean doesSpawnExist(String name){
		return spawns.containsKey(name);
	}
	
	public static boolean doesWorldSpawnExist(String server, String world){
		return spawns.containsKey(server+"-"+world);
	}
	
	public static boolean doesNewPlayerSpawnExist(){
		return spawns.containsKey("newplayerspawn");
	}
	
	public static boolean doesGlobalSpawnExist(){
		return spawns.containsKey("spawn");
	}
	
	public static void sendPlayerToSpawn(BSPlayer player, Spawn spawn, boolean silent){
		player.teleportPlayerToLocation(spawn.getLocation());
		if(!silent){
			player.sendMessage(Messages.TELEPORTED_TO_SPAWN);
		}
	}
	
	public static void sendPlayerToSpawn(String player, Spawn spawn, boolean silent){
		BSPlayer p = PlayerManager.getPlayer(player);
		p.teleportPlayerToLocation(spawn.getLocation());
		if(!silent){
			p.sendMessage(Messages.TELEPORTED_TO_SPAWN);
		}
	}
}
