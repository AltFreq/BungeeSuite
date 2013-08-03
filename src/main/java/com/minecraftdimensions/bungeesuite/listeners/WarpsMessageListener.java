package com.minecraftdimensions.bungeesuite.listeners;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.sql.SQLException;

import com.minecraftdimensions.bungeesuite.BungeeSuite;

import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class WarpsMessageListener implements Listener {
	
	@EventHandler
	public void receivePluginMessage(PluginMessageEvent event)
			throws IOException, SQLException {
		if (!event.getTag().equalsIgnoreCase("BSChat")) {
			return;
		}

		DataInputStream in = new DataInputStream(new ByteArrayInputStream(
				event.getData()));
		String task = in.readUTF();
		
	}

}
