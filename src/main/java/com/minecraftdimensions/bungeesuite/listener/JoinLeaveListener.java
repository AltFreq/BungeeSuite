package com.minecraftdimensions.bungeesuite.listener;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import com.minecraftdimensions.bungeesuite.BungeeSuite;
import com.minecraftdimensions.bungeesuite.NewPlayerSpawner;


import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
public class JoinLeaveListener implements Listener {

	BungeeSuite plugin;

	public JoinLeaveListener(BungeeSuite bungeeSuite) {
		this.plugin = bungeeSuite;
	}

	@EventHandler
	public void playerLogMessage(ChatEvent e) {
		if (e.getMessage().endsWith("joined the game.")) {
			e.setCancelled(true);
		} else if (e.getMessage().endsWith("left the game.")) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void playerLogin(PostLoginEvent e) throws SQLException {

		if (plugin.motd) {
			String msg = plugin.motdMessage;
			msg = msg.replace("{player}", e.getPlayer().getDisplayName());
			e.getPlayer().sendMessage(plugin.utils.colorize(msg));
		}
		String name = e.getPlayer().getName();
		if (!plugin.utils.playerExists(name)) {
			String ip = e.getPlayer().getAddress().getAddress().toString();
			plugin.utils.createPlayer(name, ip.substring(1, ip.length()));
			if (plugin.newspawn) {
				plugin.getProxy()
						.getScheduler()
						.schedule(plugin, new NewPlayerSpawner(name, plugin),
								plugin.DELAY_TIME, TimeUnit.MILLISECONDS);
			}
		}else{
			String ip = e.getPlayer().getAddress().getAddress().toString();
			plugin.utils.updatePlayer(name, ip.substring(1, ip.length()));
		}
	}
	@EventHandler
	public void playerLogin(LoginEvent e) throws SQLException {
		if (plugin.bans) {
			String name = e.getConnection().getName();
			String ip = e.getConnection().getAddress().getAddress().toString();
			ip = ip.substring(1,ip.length());
			if (plugin.utils.playerIsBanned(name) || plugin.utils.ipIsBanned(ip)) {
				String reason = plugin.utils.getBanReason(name);
				if (plugin.utils.playerBanIsTemp(name)) {
					Calendar bannedTill = plugin.utils.playerBanTempDate(name);
					Calendar now = Calendar.getInstance();
					if (now.after(bannedTill)) {
						plugin.utils.removeTempBan(name);
						return;
					}
					double hours = ((((bannedTill.getTimeInMillis() - now
							.getTimeInMillis()) / 1000) / 60) / 60);
					if (reason == null || reason.equals("")) {
						String message = plugin
								.getMessage("PLAYER_BANNED_STILL_TEMP");
						message = message.replace("%date", hours + "(Hours)");
						e.setCancelReason(message);
					} else {
						String message = plugin
								.getMessage("PLAYER_BANNED_STILL_TEMP_MESSAGE");
						message = message.replace("%date", hours + "(Hours)");
						message = message.replace("%msg", reason);
						e.setCancelReason(message);
					}
				} else {
					if (reason == null || reason.equals("")) {
						String message = plugin
								.getMessage("PLAYER_BANNED_STILL");
						e.setCancelReason(message);
					} else {
						String message = plugin
								.getMessage("PLAYER_BANNED_STILL_MESSAGE");
						message = message.replace("%msg", reason);
						e.setCancelReason(message);
					}
				}
				plugin.getProxy().getLogger()
						.info(name + " tried to connect but is banned!");
				e.setCancelled(true);
				return;
			}
			if(plugin.detectAltAccs){;
				plugin.utils.getSimilarIps(e.getConnection().getName(),ip);
			}
		}
		System.out.println("Player "+e.getConnection().getName()+ " has logged in");
	}

	@EventHandler
	public void playerLogout(PlayerDisconnectEvent e) {
		String player = e.getPlayer().getName();
		if(plugin.pendingTeleportsTPA.containsKey(player)){
			plugin.pendingTeleportsTPA.remove(player);
		}
		if(plugin.pendingTeleportsTPAHere.containsKey(player)){
			plugin.pendingTeleportsTPAHere.remove(player);
		}
		if(plugin.backLocations.containsKey(player)){
			plugin.backLocations.remove(player);
		}
		if (plugin.replyMessages.containsKey(player)) {
			plugin.replyMessages.remove(player);
		}
		if (plugin.playersIgnores.containsKey(player)) {
			plugin.playersIgnores.remove(player);
		}
		if (plugin.chatspies.contains(player)) {
			plugin.chatspies.remove(player);
		}
		if (plugin.playerdata.containsKey(player)) {
			plugin.playerdata.remove(player);
		}
		if(plugin.cleanChatters.contains(e.getPlayer())){
			plugin.cleanChatters.remove(e.getPlayer());
		}
		if(plugin.denyTeleport.contains(player)){
			plugin.denyTeleport.remove(player);
		}

	}
}
