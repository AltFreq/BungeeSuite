package com.minecraftdimensions.bungeesuite;

import java.util.ArrayList;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class ServerAnnouncements implements Runnable {

	ArrayList<String> list = new ArrayList<String>();
	int count = 0;
	ServerInfo server;

	public ServerAnnouncements(ServerInfo server) {
		this.server = server;
	}

	public void addAnnouncement(String message) {
		list.add(colorize(message));
	}

	public void run() {
		if (list.isEmpty()) {
			return;
		}
		if (server.getPlayers().isEmpty()) {
			return;
		}
		for(ProxiedPlayer player: server.getPlayers()){
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
