package com.minecraftdimensions.bungeesuite.managers;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import net.md_5.bungee.api.ProxyServer;

import com.minecraftdimensions.bungeesuite.BungeeSuite;
import com.minecraftdimensions.bungeesuite.objects.BSPlayer;
import com.minecraftdimensions.bungeesuite.objects.Location;
import com.minecraftdimensions.bungeesuite.objects.Messages;

public class TeleportManager {
	public static HashMap<BSPlayer, BSPlayer> pendingTeleportsTPA; // Player ----teleported---> player
	public static HashMap<BSPlayer, BSPlayer> pendingTeleportsTPAHere; // Player ----teleported---> player
	public static HashMap<BSPlayer, Location> backLocations;

	public static void initialise(){
		pendingTeleportsTPA = new HashMap<BSPlayer,BSPlayer>();
		pendingTeleportsTPAHere = new HashMap<BSPlayer,BSPlayer>();
	}
	
	public static void requestToTeleportToPlayer(String player, String target){
		final BSPlayer bp = PlayerManager.getPlayer(player);
		final BSPlayer bt = PlayerManager.getSimilarPlayer(target);
		if(playerHasPendingTeleport(bp)){
			bp.sendMessage(Messages.PLAYER_TELEPORT_PENDING);
			return;
		}
		if(bt==null){
			bp.sendMessage(Messages.PLAYER_NOT_ONLINE);
			return;
		}
		if(!playerIsAcceptingTeleports(bt)){
			bp.sendMessage(Messages.TELEPORT_UNABLE);
			return;
		}
		if(playerHasPendingTeleport(bt)){
			bp.sendMessage(Messages.PLAYER_TELEPORT_PENDING_OTHER);
			return;
		}
		pendingTeleportsTPA.put(bp, bt);
		bp.sendMessage(Messages.TELEPORT_REQUEST_SENT);
		bt.sendMessage(Messages.PLAYER_REQUESTS_TO_TELEPORT_TO_YOU.replace("{player}", bp.getDisplayingName()));
		ProxyServer.getInstance().getScheduler().schedule(BungeeSuite.instance, new Runnable() {
					@Override
					public void run() {
						if (pendingTeleportsTPA.containsKey(bt)) {
							if(bp.isOnline()){
							bp.sendMessage(Messages.TPA_REQUEST_TIMED_OUT.replace("{player}", bt.getDisplayingName()));
							}
							pendingTeleportsTPA.remove(bt);
							if(bt.isOnline()){
								bt.sendMessage(Messages.TP_REQUEST_OTHER_TIMED_OUT.replace("{player}", bp.getDisplayingName()));
							}
						}
					}
				}, 10, TimeUnit.SECONDS);
	}
	
	public static void requestPlayerTeleportToYou(String player, String target){
		final BSPlayer bp = PlayerManager.getPlayer(player);
		final BSPlayer bt = PlayerManager.getSimilarPlayer(target);
		if(playerHasPendingTeleport(bp)){
			bp.sendMessage(Messages.PLAYER_TELEPORT_PENDING);
			return;
		}
		if(bt==null){
			bp.sendMessage(Messages.PLAYER_NOT_ONLINE);
			return;
		}
		if(!playerIsAcceptingTeleports(bt)){
			bp.sendMessage(Messages.TELEPORT_UNABLE);
			return;
		}
		if(playerHasPendingTeleport(bt)){
			bp.sendMessage(Messages.PLAYER_TELEPORT_PENDING_OTHER);
			return;
		}
		pendingTeleportsTPAHere.put(bt, bp);
		bp.sendMessage(Messages.TELEPORT_REQUEST_SENT);
		bt.sendMessage(Messages.PLAYER_REQUESTS_YOU_TELEPORT_TO_THEM.replace("{player}",bp.getDisplayingName()));
		ProxyServer.getInstance().getScheduler().schedule(BungeeSuite.instance, new Runnable() {
					@Override
					public void run() {
						if (pendingTeleportsTPAHere.containsKey(bt)) {
							if(bp.isOnline()){
							bp.sendMessage(Messages.TPAHERE_REQUEST_TIMED_OUT.replace("{player}", bt.getDisplayingName()));
							}
							pendingTeleportsTPAHere.remove(bt);
							if(bt.isOnline()){
								bt.sendMessage(Messages.TP_REQUEST_OTHER_TIMED_OUT.replace("{player}", bp.getDisplayingName()));
							}
						}
					}
				}, 10, TimeUnit.SECONDS);
	}
	
	public static void acceptTeleportRequest(BSPlayer player){
		if(pendingTeleportsTPA.containsKey(player)){
			BSPlayer target = pendingTeleportsTPA.get(player);
			player.sendMessage(Messages.TELEPORTED_TO_PLAYER.replace("{player}", target.getDisplayingName()));
			target.sendMessage(Messages.PLAYER_TELEPORTED_TO_YOU.replace("{player}", player.getDisplayingName()));
			teleportPlayerToPlayer(player,target);
			pendingTeleportsTPA.remove(player);
		}else if(pendingTeleportsTPAHere.containsKey(player)){
			BSPlayer target = pendingTeleportsTPA.get(player);
			player.sendMessage(Messages.TELEPORTED_TO_PLAYER.replace("{player}", target.getDisplayingName()));
			target.sendMessage(Messages.PLAYER_TELEPORTED_TO_YOU.replace("{player}", player.getDisplayingName()));
			teleportPlayerToPlayer(player,target);
			pendingTeleportsTPAHere.remove(player);
		}else{
			player.sendMessage(Messages.NO_TELEPORTS);
		}
	}
	
	public static void denyTeleportRequest(BSPlayer player){
		if(pendingTeleportsTPA.containsKey(player)){
			BSPlayer target = pendingTeleportsTPA.get(player);
			player.sendMessage(Messages.TELEPORT_REQUEST_DENIED.replace("{player}", target.getDisplayingName()));
			target.sendMessage(Messages.TELEPORT_DENIED.replace("{player}", player.getDisplayingName()));
			pendingTeleportsTPA.remove(player);
		}else if(pendingTeleportsTPAHere.containsKey(player)){
			BSPlayer target = pendingTeleportsTPA.get(player);
			player.sendMessage(Messages.TELEPORT_REQUEST_DENIED.replace("{player}", target.getDisplayingName()));
			target.sendMessage(Messages.TELEPORT_DENIED.replace("{player}", player.getDisplayingName()));
			pendingTeleportsTPAHere.remove(player);
		}else{
			player.sendMessage(Messages.NO_TELEPORTS);
		}
	}

	public static boolean playerHasPendingTeleport(BSPlayer player){
		return pendingTeleportsTPA.containsKey(player) || pendingTeleportsTPAHere.containsKey(player);
	}
	
	public static boolean playerIsAcceptingTeleports(BSPlayer player){
		return player.acceptingTeleports();
	}
	
	public static boolean playerHasDeathBackLocation(BSPlayer player){
		return player.hasDeathBackLocation();
	}
	public static boolean playerHasTeleportBackLocation(BSPlayer player){
		return player.hasTeleportBackLocation();
	}
	
	public static void setPlayersDeathBackLocation(BSPlayer player, Location loc){
		player.setDeathBackLocation(loc);
	}
	public static void setPlayersTeleportBackLocation(BSPlayer player, Location loc){
		player.setTeleportBackLocation(loc);
	}
	
	public static void sendPlayerToTeleportBack(BSPlayer player){
		player.teleportPlayerToLocation(player.getTeleportBackLocation());
	}
	
	public static void sendPlayerToDeathBack(BSPlayer player){
		player.teleportPlayerToLocation(player.getDeathBackLocation());
	}
	
	
	public static void sendPlayerToLastBack(BSPlayer player){
		player.teleportPlayerToLocation(player.getLastBackLocation());
	}
	
	public static void togglePlayersTeleports(BSPlayer player){
		if(player.acceptingTeleports()){
			player.setAcceptingTeleports(false);
			player.sendMessage(Messages.TELEPORT_TOGGLE_OFF);
		}else{
			player.setAcceptingTeleports(true);
			player.sendMessage(Messages.TELEPORT_TOGGLE_ON);
		}
	}
	
	public static void teleportPlayerToPlayer(BSPlayer player, BSPlayer target){
		player.teleportPlayerToPlayer(target);
	}

}


