package com.minecraftdimensions.bungeesuite.listeners;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import com.minecraftdimensions.bungeesuite.BungeeSuite;
import com.minecraftdimensions.bungeesuite.managers.PlayerManager;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
public class PlayerListener implements Listener {

	@EventHandler
	public void playerLogin(PostLoginEvent e) throws SQLException {
		PlayerManager.loadPlayer(e.getPlayer());
	}

	@EventHandler
	public void playerLogout(final PlayerDisconnectEvent e) {
		BungeeSuite.proxy.getScheduler().schedule(BungeeSuite.instance, new Runnable(){

			@Override
			public void run() {
				if(ProxyServer.getInstance().getPlayer(e.getPlayer().getName())==null){
					PlayerManager.unloadPlayer(e.getPlayer().getName());
				}
			}
			
		}, 5, TimeUnit.SECONDS);
	}
}
