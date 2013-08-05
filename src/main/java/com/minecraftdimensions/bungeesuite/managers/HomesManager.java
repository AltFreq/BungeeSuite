package com.minecraftdimensions.bungeesuite.managers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import net.md_5.bungee.api.ChatColor;

import com.minecraftdimensions.bungeesuite.objects.Home;
import com.minecraftdimensions.bungeesuite.objects.Messages;
import com.minecraftdimensions.bungeesuite.objects.BSPlayer;

public class HomesManager {
	public static ArrayList<String> homeGroupsList = new ArrayList<String>();
	public static HashMap<String, HashMap<String, Integer>> homeLimits = new HashMap<String, HashMap<String, Integer>>();
	
	
	public static void createNewHome(String name,String owner, String server,String world, double x, double y, double z, float yaw, float pitch) throws SQLException{
		Home h = new Home();
		h.setName(name);
		h.setOwner(owner);
		h.setServer(server);
		h.setWorld(world);
		h.setX(x);
		h.setY(y);
		h.setZ(z);
		h.setYaw(yaw);
		h.setPitch(pitch);
		SQLManager.standardQuery("INSERT INTO BungeeHomes (player,home_name,server,world,x,y,z,yaw,pitch) VALUES('"+owner+"','"+name+"','"+server+"','"+world+"',"+x+","+y+","+z+","+yaw+","+pitch+",)");
		PlayerManager.getPlayer(owner).addHome(h);
	}
	
	public static int getPlayersHomesCount(String player, String server){
		return PlayerManager.getPlayer(player).getHomesCountOnServer(server);
	}
	
	public static void removeHome(String player,String home){
		PlayerManager.getPlayer(player).removeHome(home);
	}
	
	public static boolean canPlayerSetHome(String player, String home, String server,int maxHomesAllowed){
		if(playersHomeExists(player,home)){
			return true;
		}else if(maxHomesAllowed>getPlayersHomesCount(player,server)){
			return true;
		}else{
			return false;
		}
	}
	
	public static void listPlayersHomes(String player) throws SQLException {
		BSPlayer p = PlayerManager.getPlayer(player);
		HashMap<String,ArrayList<String>> homes = p.getHomesList();

		p.sendMessage(ChatColor.AQUA + "Your homes:");
		for (String server : homes.keySet()) {
			String message = ChatColor.GOLD + server + ": " + ChatColor.WHITE;
			for (String home : homes.get(server)) {
				message += " " + home + ",";
			}
			p.sendMessage(message.substring(0, server.length() - 1));
		}
	}
	
	public static void addHome(String player,Home home){
		PlayerManager.getPlayer(player).addHome(home);
	}
	
	public static boolean playersHomeExists(String player, String home){
		return PlayerManager.getPlayer(player).homeExists(home);
	}
	
	public static void loadPlayersHomes(String player) throws SQLException{
		ResultSet res = SQLManager.sqlQuery("SELECT * FROM BungeeHomes WHERE player = '"+player+"'");
		while(res.next()){
			Home h = new Home();
			h.setName(res.getString("home_name"));
			h.setOwner(player);
			h.setServer(res.getString("server"));
			h.setWorld(res.getString("world"));
			h.setX(res.getDouble("x"));
			h.setY(res.getDouble("y"));
			h.setZ(res.getDouble("z"));
			h.setYaw(res.getFloat("yaw"));
			h.setPitch(res.getFloat("pitch"));
			addHome(player, h);
		}
		res.close();
	}
	
	public static void sendPlayerToHome(String player,String home){
		BSPlayer p = PlayerManager.getPlayer(player);
		Home h = p.getHomeSimilar(home);
		if(h!=null){
			p.sendMessage(Messages.SENT_HOME);
			p.teleportPlayerToLocation(h.getLocation());
		}else{
			p.sendMessage(Messages.HOME_DOES_NOT_EXIST);
		}
	}
}
	
