package com.minecraftdimensions.bungeesuite.managers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import com.minecraftdimensions.bungeesuite.BungeeSuite;
import com.minecraftdimensions.bungeesuite.configs.MainConfig;
import com.minecraftdimensions.bungeesuite.objects.BSPlayer;
import com.minecraftdimensions.bungeesuite.objects.Messages;

public class PlayerManager {

	private static HashMap<String, BSPlayer> onlinePlayers = new HashMap<String, BSPlayer>();
	static ProxyServer proxy = ProxyServer.getInstance();
	static BungeeSuite plugin = BungeeSuite.instance;

	public static boolean playerExists(String player) {
		return SQLManager.existanceQuery("SELECT playername FROM BungeePlayers WHERE playername = '"+player+"'");
	}

	public static void loadPlayer(ProxiedPlayer player) throws SQLException {
		String nickname = null;
		String channel = null;
		boolean muted = false;
		boolean chatspying = false;
		boolean cleanchat = false;
		boolean dnd = false;
		boolean tps = true;
		
		if(playerExists(player.getName())){
		ResultSet res = SQLManager
				.sqlQuery("SELECT playername,nickname,channel,muted,chat_spying,clean_chat,dnd,tps FROM BungeePlayers WHERE playername = '"
						+ player + "'");
		while (res.next()) {
			nickname = res.getString("nickname");
			channel = res.getString("channel");
			muted = res.getBoolean("muted");
			chatspying = res.getBoolean("chat_spying");
			cleanchat = res.getBoolean("clean_chat");
			dnd = res.getBoolean("dnd");
			tps = res.getBoolean("tps");
		}
		res.close();
		BSPlayer bsplayer = new BSPlayer(player.getName(), nickname, channel, muted, chatspying, cleanchat, dnd, tps);
			addPlayer(bsplayer);
		} else {
			createNewPlayer(player);
		}
	}

	private static void createNewPlayer(ProxiedPlayer player) throws SQLException {
		String ip = player.getAddress().getAddress().toString();
		SQLManager.standardQuery("INSERT INTO BungeePlayers (playername,lastonline,ipaddress) VALUES ('"
				+ player.getName() + "', NOW(), '" + ip.substring(1, ip.length()) + "')");
		BSPlayer bsplayer = new BSPlayer(player.getName(),null,null,false,false,false,false,true);
		if (MainConfig.newPlayerBroadcast) {
			sendBroadcast(Messages.NEW_PLAYER_BROADCAST.replace("{player}",
					player.getName()));
		}
		addPlayer(bsplayer);
		
	}

	private static void addPlayer(BSPlayer player){
		onlinePlayers.put(player.getName(), player);
		LoggingManager.log(Messages.PLAYER_LOAD.replace("{player}", player.getName()));
	}
	
	public static void unloadPlayer(String player) {
		onlinePlayers.remove(player);
		LoggingManager.log(Messages.PLAYER_UNLOAD.replace("{player}", player));
	}

	public static BSPlayer getPlayer(String player) {
		return onlinePlayers.get(player);
	}
	
	public static BSPlayer getSimilarPlayer(String player){
		for(String p: onlinePlayers.keySet()){
			if(p.toLowerCase().contains(player.toLowerCase())){
				return onlinePlayers.get(p);
			}
		}
		return null;
	}
	
	public static void sendPrivateMessageToPlayer(BSPlayer from, BSPlayer to, String message){
		from.sendMessageToPlayer(to, message);
	}
	
	public static void sendMessageToPlayer(String player, String message){
		if(player.equals("CONSOLE")){
			ProxyServer.getInstance().getConsole().sendMessage(message);
		}else{
			getPlayer(player).sendMessage(message);
		}
	}

	public static String getPlayersIP(String player) throws SQLException{
		BSPlayer p = getSimilarPlayer(player);
		String ip = null;
		if(p==null){
			ResultSet res = SQLManager.sqlQuery("SELECT ipaddress FROM BungeePlayers WHERE player = '"+player+"'");
			while(res.next()){
				ip = res.getString("ipaddress");
			}
		}else{
			ip = p.getProxiedPlayer().getAddress().getAddress().toString();
		}
		return ip;
	}
	
	public static void sendBroadcast(String message) {
		for (ProxiedPlayer p : proxy.getPlayers()) {
			for (String line : message.split("\n")) {
				p.sendMessage(line);
			}
		}
		LoggingManager.log(message);
	}
	
	public static boolean isPlayerOnline(String player){
		return onlinePlayers.containsKey(player);
	}
	
	public static ArrayList<String> getPlayersAltAccounts(String player) throws SQLException{
		ArrayList<String>accounts = new ArrayList<String>();
		ResultSet res =SQLManager.sqlQuery("SELECT playername from BungeePlayers WHERE ipaddress = (SELECT ipaddress FROM BungeePlayers WHERE playername = '"+player+"')");
		while(res.next()){
			accounts.add(res.getString("playername"));
		}
		return accounts;
	}
	public static ArrayList<String> getPlayersAltAccountsByIP(String ip) throws SQLException{
		ArrayList<String>accounts = new ArrayList<String>();
		ResultSet res =SQLManager.sqlQuery("SELECT playername from BungeePlayers WHERE ipaddress = '"+ip+"'");
		while(res.next()){
			accounts.add(res.getString("playername"));
		}
		return accounts;
	}

	public static BSPlayer getPlayer(CommandSender sender) {
			return onlinePlayers.get(sender.getName());
	}
}
