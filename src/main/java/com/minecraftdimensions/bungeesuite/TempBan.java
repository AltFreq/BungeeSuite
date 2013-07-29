package com.minecraftdimensions.bungeesuite;

import java.sql.SQLException;

public class TempBan implements Runnable {

	BungeeSuite plugin;
	String player;
	String sender;
	public TempBan(BungeeSuite plugin, String player, String sender) {
		this.plugin = plugin;
	}

	@Override
	public void run() {
		if(plugin.tempMutes.contains(player)){
			try {
				plugin.utils.unMutePlayer(sender, player);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			plugin.tempMutes.remove(player);
		}

	}

}
