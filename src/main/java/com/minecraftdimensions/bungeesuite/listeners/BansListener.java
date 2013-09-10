package com.minecraftdimensions.bungeesuite.listeners;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.minecraftdimensions.bungeesuite.managers.BansManager;
import com.minecraftdimensions.bungeesuite.managers.LoggingManager;
import com.minecraftdimensions.bungeesuite.objects.Ban;
import com.minecraftdimensions.bungeesuite.objects.Messages;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
public class BansListener implements Listener {

	@EventHandler
	public void banCheck(LoginEvent e) throws SQLException {
		if(BansManager.isPlayerBanned(e.getConnection().getName())){
			Ban b =BansManager.getBanInfo(e.getConnection().getName());
			if(b.getType().equals("tempban")){
				if(BansManager.checkTempBan(b)){
					e.setCancelled(true);
					SimpleDateFormat sdf = new SimpleDateFormat();
					Date then = b.getBannedUntil();
					Date now = new Date();
					long timeDiff = then.getTime()-now.getTime();
					long hours = timeDiff / (60 * 60 *1000);
					long mins = timeDiff / (60 * 1000) % 60; 
					sdf.applyPattern("dd MMM yyyy HH:mm:ss z");
					e.setCancelReason(Messages.TEMP_BAN_MESSAGE.replace("{sender}", b.getBannedBy()).replace("{time}", sdf.format(then) + " ("+hours+":"+mins+" hours)").replace("{message}", b.getReasaon()));
					LoggingManager.log(ChatColor.RED+e.getConnection().getName()+"'s connection refused due to being banned!");
					return;
				}
			}
			else{
				e.setCancelled(true);
				SimpleDateFormat sdf = new SimpleDateFormat();
				sdf.applyPattern("dd MMM yyyy HH:mm:ss z");
				e.setCancelReason(Messages.BAN_PLAYER_MESSAGE.replace("{sender}", b.getBannedBy()).replace("{message}", b.getReasaon()));
				LoggingManager.log(ChatColor.RED+e.getConnection().getName()+"'s connection refused due to being banned!");
				return;
			}
		}
	}
}
