package com.minecraftdimensions.bungeesuite.managers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import com.minecraftdimensions.bungeesuite.objects.Location;
import com.minecraftdimensions.bungeesuite.objects.Messages;
import com.minecraftdimensions.bungeesuite.objects.Warp;
import com.minecraftdimensions.bungeesuite.objects.BSPlayer;

public class WarpManager {
		static HashMap<String,Warp> warps;
	
	public static void loadWarpLocations() throws SQLException{
		warps = new HashMap<String,Warp>();
		ResultSet res = SQLManager.sqlQuery("SELECT * FROM BungeeWarps");
		while (res.next()){
			createWarp(res.getString("warpname"), new Location(res.getString("server"), res.getString("world"), res.getDouble("x"), res.getDouble("y"), res.getDouble("z"), res.getFloat("yaw"), res.getFloat("pitch")),res.getBoolean("private"));
		}
		res.close();
	}
	
	public static void createWarp(String name, Location loc, boolean hidden){
		Warp s = new Warp(name, loc, hidden);
		warps.put(name, s);
	}
	
	public static void setWarp(BSPlayer sender, String name, Location loc, boolean hidden) throws SQLException{
		Warp w;
		if(doesWarpExist(name)){
			w =warps.get(name);
			w.setLocation(loc);
			SQLManager.standardQuery("UPDATE BungeeWarps SET server='" + loc.getServer()
					+ "', world='" + loc.getWorld() + "', x=" + loc.getX() + ", y=" + loc.getY() + ", z="
					+ loc.getZ() + ", yaw=" + loc.getYaw() + ", pitch= " + loc.getPitch()
					+ " WHERE spawnname='" + name + "'");
			sender.sendMessage(Messages.WARP_UPDATED);
		}else{
			w = new Warp(name, loc, hidden);
			warps.put(name, w);
			SQLManager.standardQuery("INSERT INTO BungeeWarps VALUES ('"+name+"', '"+loc.getServer()+"','"+loc.getWorld()+"',"+loc.getX()+","+loc.getY()+","+loc.getZ()+","+loc.getYaw()+","+loc.getPitch()+", "+hidden+")");
			sender.sendMessage(Messages.WARP_CREATED);
		}
	}
	
	public static void deleteWarp(String warp, BSPlayer sender) throws SQLException{
		warps.remove(warp);
		SQLManager.standardQuery("DELETE FROM BungeeWarps WHERE warpsname='"+warp+"'");
		sender.sendMessage(Messages.WARP_DELETED);
	}
	
	public static Warp getWarp(String name){
		return warps.get(name);
	}
	
	public static Warp getSimilarWarp(String name){
		if(warps.containsKey(name)){
			return warps.get(name);
		}
		for(String warp: warps.keySet()){
			if(warp.toLowerCase().equals(name)){
				return warps.get(warp);
			}
		}
		return null;
	}
	
	public static boolean doesWarpExist(String name){
		return warps.containsKey(name);
	}

	public static HashMap<Boolean,String> getWarpsList(){
		HashMap<Boolean,String> list = new HashMap<Boolean,String>();
		for(Warp w: warps.values()){
			if(w.isHidden()){
				list.put(true, w.getName());
			}else{
				list.put(false, w.getName());
			}
		}
		return list;
	}

	
	public static void sendPlayerToWarp(String player, Warp warp, boolean silent){
		BSPlayer p = PlayerManager.getPlayer(player);
//		p.teleportPlayerToLocation(warp.getLocation());
		if(!silent){
			p.sendMessage(Messages.PLAYER_WARPED.replace("{warp}", warp.getName()));
		}
	}
}

