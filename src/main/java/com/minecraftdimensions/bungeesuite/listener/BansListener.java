package com.minecraftdimensions.bungeesuite.listener;
import java.sql.SQLException;

import com.minecraftdimensions.bungeesuite.BungeeSuite;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
public class BansListener implements Listener {

	BungeeSuite plugin;

	public BansListener(BungeeSuite bungeeSuite) {
		this.plugin = bungeeSuite;
	}


	@EventHandler
	public void playerLogin(LoginEvent e) throws SQLException {
		
	}
}
