package com.minecraftdimensions.bungeesuite.managers;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.SimpleTimeZone;

import com.minecraftdimensions.bungeesuite.Utilities;
import com.minecraftdimensions.bungeesuite.configs.BansConfig;
import com.minecraftdimensions.bungeesuite.objects.BSPlayer;
import com.minecraftdimensions.bungeesuite.objects.Ban;
import com.minecraftdimensions.bungeesuite.objects.Messages;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class BansManager {

	public static boolean isPlayerBanned(String player) {
		return SQLManager
				.existanceQuery("SELECT player FROM BungeeBans WHERE player='"
						+ player + "'");
	}

	public static void banPlayer(String player, String bannedBy, String reason) throws SQLException{
		if(reason.equals("")){
			reason = Messages.DEFAULT_BAN_REASON;
		}
		SQLManager.standardQuery("INSERT INTO BungeeBans (player,banned_by,reason,type,banned_on) VALUES ('"+player+"','"+bannedBy+"','"+reason+"','ban',NOW())");
		if(PlayerManager.isPlayerOnline(player)){
			disconnectPlayer(player,Messages.BAN_PLAYER_MESSAGE.replace("{message}", reason).replace("{sender}", bannedBy));
		}
		if(BansConfig.broadcastBans){
			PlayerManager.sendBroadcast(Messages.BAN_PLAYER_BROADCAST.replace("{player}", player).replace("{message}", reason).replace("{sender}", bannedBy));
		}else{
			PlayerManager.sendMessageToPlayer(bannedBy, Messages.BAN_PLAYER_BROADCAST.replace("{player}", player).replace("{message}", reason).replace("{sender}", bannedBy));
		}
	}

	public static void tempBanPlayer(String player, String bannedBy,
			String reason, Date bannedUntil) {

	}
	
	public static String getPlayersIP(String player) throws SQLException{
		return PlayerManager.getPlayersIP(player);
	}

	public static void unbanPlayer(String sender,String player) throws SQLException {
		SQLManager.standardQuery("DELETE FROM BungeeBans WHERE player ='"+player+"'");
		if(BansConfig.broadcastBans){
			PlayerManager.sendBroadcast(Messages.PLAYER_UNBANNED.replace("{player}", player).replace("{sender}", sender));
		}else{
			PlayerManager.sendMessageToPlayer(sender,Messages.PLAYER_UNBANNED.replace("{player}", player).replace("{sender}", sender));
		}
	}

	public static void banIP(String bannedBy, String player, String reason) throws SQLException {
		if(reason==null){
			reason = Messages.DEFAULT_BAN_REASON;
		}
		ArrayList<String> accounts = null;
		if(Utilities.isIPAddress(player)){
			accounts = PlayerManager.getPlayersAltAccountsByIP(player);
		}else{
			accounts = PlayerManager.getPlayersAltAccounts(player);
		}
		for(String a: accounts){
			if(!isPlayerBanned(a)){
				SQLManager.standardQuery("INSERT INTO BungeeBans VALUES ('"+a+"', '"+bannedBy+"', '"+reason+"', 'ipban', NOW(), NULL)");
			}
			if(PlayerManager.isPlayerOnline(a)){
				disconnectPlayer(player,Messages.IPBAN_PLAYER.replace("{message}", reason).replace("{sender}", bannedBy));
			}
		}
		if(BansConfig.broadcastBans){
			PlayerManager.sendBroadcast(Messages.IPBAN_PLAYER_BROADCAST.replace("{player}", player).replace("{message}", reason).replace("{sender}", bannedBy));
		}else{
			PlayerManager.sendMessageToPlayer(bannedBy, Messages.IPBAN_PLAYER_BROADCAST.replace("{player}", player).replace("{message}", reason).replace("{sender}", bannedBy));
		}
			

	}

	public static void unbanIP(String player, String sender) throws SQLException {
		if(!Utilities.isIPAddress(player)){
		player=PlayerManager.getPlayersIP(player);
		}
		SQLManager.standardQuery("DELETE FROM BungeeBans B INNER JOIN BungeePlayers P ON B.player = P.playername WHERE ipadress ='"
				+ player + "'");
		if(BansConfig.broadcastBans){
			PlayerManager.sendBroadcast(Messages.PLAYER_UNBANNED.replace("{player}", player).replace("{sender}", sender));
		}else{
			PlayerManager.sendMessageToPlayer(sender,Messages.PLAYER_UNBANNED.replace("{player}", player).replace("{sender}", sender));
		}
	}
	
	public boolean isPlayerStillBanned(String player) throws SQLException{
		return getBanInfo(player).stillBanned();
	}

	public static void kickAll(String sender, String message) {
		if(message.equals("")){
			message = Messages.DEFAULT_KICK_MESSAGE;
		}else{
			message = Messages.KICK_PLAYER_MESSAGE.replace("{message}", message).replace("{sender}", sender);
		}
		for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
			disconnectPlayer(p, message);
		}
	}

	public static Ban getBanInfo(String player) throws SQLException {
		Ban b = null;
		ResultSet res =SQLManager.sqlQuery("SELECT * FROM BungeeBans WHERE player = '"+player+"'");
		while (res.next()){
			b = new Ban(res.getString("player"), res.getString("bannedBy"), res.getString("reason"), res.getString("type"), res.getDate("banned_on"), res.getDate("banned_until"));
		}
		res.close();
		return b;
	}
	
	public static void checkPlayersBan(String sender, String player) throws SQLException{
		BSPlayer p = PlayerManager.getPlayer(sender);
		Ban b = getBanInfo(player);
		if(b==null){
			p.sendMessage(Messages.PLAYER_NOT_BANNED);
			return;
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat();
			sdf.setTimeZone(new SimpleTimeZone(0, "GMT"));
			sdf.applyPattern("dd MMM yyyy HH:mm:ss z");
			p.sendMessage(ChatColor.DARK_AQUA+"--------"+ChatColor.DARK_RED+"Ban Info"+ChatColor.DARK_AQUA+"--------");
			p.sendMessage(ChatColor.RED+"Player: "+ChatColor.AQUA+b.getPlayer());
			p.sendMessage(ChatColor.RED+"Ban type: "+ChatColor.AQUA+b.getType());
			p.sendMessage(ChatColor.RED+"Banned by: "+ChatColor.AQUA+b.getBannedBy());
			p.sendMessage(ChatColor.RED+"Ban reason: "+ChatColor.AQUA+b.getReasaon());
			p.sendMessage(ChatColor.RED+"Bannned on: "+ChatColor.AQUA+sdf.format(b.getBannedOn()));
			if(b.getBannedUntil()==null){
				p.sendMessage(ChatColor.RED+"Bannned until: "+ChatColor.AQUA+"-Forever-");
			}else{
				p.sendMessage(ChatColor.RED+"Bannned until: "+ChatColor.AQUA+sdf.format(b.getBannedUntil()));
			}
		}
		
	}

	public static void kickPlayer(String player, String sender, String reason) {
		if(reason.equals("")){
			reason = Messages.DEFAULT_KICK_MESSAGE;
		}
		player = PlayerManager.getSimilarPlayer(player).getName();
		if(PlayerManager.isPlayerOnline(player)){
			disconnectPlayer(player,Messages.KICK_PLAYER_MESSAGE.replace("{message}", reason).replace("{sender}", sender));
			if(BansConfig.broadcastKicks){
				PlayerManager.sendBroadcast(Messages.KICK_PLAYER_BROADCAST.replace("{message}", reason).replace("{player}", player).replace("{sender}", sender));
			}
		}
	}

	public static void disconnectPlayer(ProxiedPlayer player, String message) {
		player.disconnect(message);
	}

	public static void disconnectPlayer(String player, String message) {
		ProxyServer.getInstance().getPlayer(player).disconnect(message);
	}
	public static void disconnectPlayer(BSPlayer player, String message) {
		player.getProxiedPlayer().disconnect(message);
	}

	public ArrayList<String> getAltAccounts(String player) throws SQLException {
		return PlayerManager.getPlayersAltAccounts(player);
	}
	
	public ArrayList<String> getBannedAltAccounts(String player) throws SQLException {
		ArrayList<String> accounts = getAltAccounts(player);
		boolean check = false;
		for(String p :getAltAccounts(player)){
			if(isPlayerBanned(p)){
				check = true;
				String newPlayer = ChatColor.DARK_RED+"[Banned] "+p;
				p = newPlayer;
			}
		}
		if(check){
			return accounts;
		}else{
			return null;
		}
	}

	public static void reloadBans(String sender) {
		PlayerManager.getPlayer(sender).sendMessage("Bans Reloaded");
		BansConfig.reloadBans();
	}
}
