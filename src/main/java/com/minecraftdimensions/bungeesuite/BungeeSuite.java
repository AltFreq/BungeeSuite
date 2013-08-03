package com.minecraftdimensions.bungeesuite;

import com.minecraftdimensions.bungeesuite.managers.AnnouncementManager;
import com.minecraftdimensions.bungeesuite.managers.ChannelManager;
import com.minecraftdimensions.bungeesuite.managers.DatabaseTableManager;
import com.minecraftdimensions.bungeesuite.managers.PrefixSuffixManager;
import com.minecraftdimensions.bungeesuite.managers.SQLManager;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeeSuite extends Plugin {
	public static BungeeSuite instance;

	public void onEnable() {
		instance = this;
		initialiseManagers();
		registerListeners();
	}

	private void initialiseManagers() {
		if(SQLManager.initialiseConnections()){
		DatabaseTableManager.createDefaultTables();
		AnnouncementManager.loadAnnouncements();
		ChannelManager.loadChannels();
		PrefixSuffixManager.loadPrefixes();
		PrefixSuffixManager.loadSuffixes();
		
		}
	}
	
	void registerListeners() {
		
	}

	public void onDisable() {
		SQLManager.closeConnections();
	}
}
