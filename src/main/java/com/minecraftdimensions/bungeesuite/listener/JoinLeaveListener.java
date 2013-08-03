package com.minecraftdimensions.bungeesuite.listener;
import java.sql.SQLException;

import com.minecraftdimensions.bungeesuite.BungeeSuite;
import com.minecraftdimensions.bungeesuite.managers.PlayerManager;

import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
public class JoinLeaveListener implements Listener {

	BungeeSuite plugin;

	public JoinLeaveListener(BungeeSuite bungeeSuite) {
		this.plugin = bungeeSuite;
	}

	@EventHandler
	public void playerLogin(PostLoginEvent e) throws SQLException {
		PlayerManager.loadPlayer(e.getPlayer());
	}

	@EventHandler
	public void playerLogout(PlayerDisconnectEvent e) {
		PlayerManager.unloadPlayer(e.getPlayer().getName());
	}
}
