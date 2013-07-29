package com.minecraftdimensions.bungeesuite.listener;

import java.sql.SQLException;
import java.util.Calendar;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.event.EventHandler;

import com.minecraftdimensions.bungeesuite.BungeeSuite;

public class PlayerBanCheckDelay implements Runnable {
	ProxiedPlayer player;
	BungeeSuite plugin;

	public PlayerBanCheckDelay(ProxiedPlayer player, BungeeSuite plugin) {
		this.player = player;
		this.plugin = plugin;
	}

	@EventHandler
	public void run() {
		if (plugin.bans) {
			String name = player.getName();
			String ip = player.getAddress().getAddress().toString();
			ip = ip.substring(1, ip.length());
			System.out.println(ip);
			if (plugin.utils.playerIsBanned(name)
					|| plugin.utils.ipIsBanned(ip)) {
				String reason = plugin.utils.getBanReason(name);
				if (plugin.utils.playerBanIsTemp(name)) {
					Calendar bannedTill = plugin.utils.playerBanTempDate(name);
					Calendar now = Calendar.getInstance();
					if (now.after(bannedTill)) {
						try {
							plugin.utils.removeTempBan(name);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return;
					}
					double hours = ((((bannedTill.getTimeInMillis() - now
							.getTimeInMillis()) / 1000) / 60) / 60);
					if (reason == null || reason.equals("")) {
						String message = plugin
								.getMessage("PLAYER_BANNED_STILL_TEMP");
						message = message.replace("%date", hours + "(Hours)");
						player.disconnect(message);
					} else {
						String message = plugin
								.getMessage("PLAYER_BANNED_STILL_TEMP_MESSAGE");
						message = message.replace("%date", hours + "(Hours)");
						message = message.replace("%msg", reason);
						player.disconnect(message);
					}
				} else {
					if (reason == null || reason.equals("")) {
						String message = plugin
								.getMessage("PLAYER_BANNED_STILL");
						player.disconnect(message);
					} else {
						String message = plugin
								.getMessage("PLAYER_BANNED_STILL_MESSAGE");
						message = message.replace("%msg", reason);
						player.disconnect(message);
					}
				}
				plugin.getProxy().getLogger()
						.info(name + " tried to connect but is banned!");
				return;
			}
		}

	}
}
