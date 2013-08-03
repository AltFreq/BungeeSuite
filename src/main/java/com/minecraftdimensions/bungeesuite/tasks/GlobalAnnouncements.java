package com.minecraftdimensions.bungeesuite.tasks;

import java.util.ArrayList;
import java.util.Collection;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class GlobalAnnouncements implements Runnable  {

	ArrayList<String> list = new ArrayList<String>();
	int count = 0;

	public void addAnnouncement(String message) {
		list.add(colorize(message));
	}

	public void run() {
		if (list.isEmpty()) {
			return;
		}
		Collection<ProxiedPlayer> players = ProxyServer.getInstance().getPlayers();
		if (players.isEmpty()) {
			return;
		}
		for(ProxiedPlayer player: players){
			player.sendMessage(list.get(count));
		}
		count++;
		if((count+1)>list.size()){
			count=0;
		}
	}
	public String colorize(String input) {
		return ChatColor.translateAlternateColorCodes('&', input);
	}
}
