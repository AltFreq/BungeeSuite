package com.minecraftdimensions.bungeesuite;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.minecraftdimensions.bungeesuite.configs.MainConfig;
import com.minecraftdimensions.bungeesuite.listeners.PlayerListener;
import com.minecraftdimensions.bungeesuite.managers.AnnouncementManager;
import com.minecraftdimensions.bungeesuite.managers.ChatManager;
import com.minecraftdimensions.bungeesuite.managers.DatabaseTableManager;
import com.minecraftdimensions.bungeesuite.managers.PrefixSuffixManager;
import com.minecraftdimensions.bungeesuite.managers.SQLManager;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeeSuite extends Plugin {
	public static BungeeSuite instance;
	public static ProxyServer proxy;

	public void onEnable() {
		instance = this;
		proxy = ProxyServer.getInstance();
		initialiseManagers();
		registerListeners();
	}

	private void initialiseManagers() {
		if(SQLManager.initialiseConnections()){
		DatabaseTableManager.createDefaultTables();
		AnnouncementManager.loadAnnouncements();
		ChatManager.loadChannels();
		PrefixSuffixManager.loadPrefixes();
		PrefixSuffixManager.loadSuffixes();
		}else{
			setupSQL();
		}
	}
	
	void registerListeners() {
		proxy.getPluginManager().registerListener(this, new PlayerListener());
	}
	
	private void setupSQL() {
		System.out.println(ChatColor.GREEN+"--------"+ChatColor.GOLD+"Welcome to BungeeSuite SQL setup"+ChatColor.GREEN+"--------");
		System.out.println(ChatColor.DARK_RED+"Enter your databases URL/IP:"); 
		try{
		    BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		    String s = bufferRead.readLine();
		    MainConfig.config.setString("Database.IRL", s);
		    MainConfig.irl = s;
			System.out.println(ChatColor.GREEN+"URL set to: "+ s); 
			System.out.println(ChatColor.DARK_RED+"Enter your database name:"); 
		    s = bufferRead.readLine();
		    MainConfig.config.setString("Database.Database", s);
		    MainConfig.database = s;
		    System.out.println(ChatColor.GREEN+"Database name set to: "+ s); 
			System.out.println(ChatColor.DARK_RED+"Enter your databases port number (default 3306):"); 
		    s = bufferRead.readLine();
		    MainConfig.config.setString("Database.Port", s);
		    MainConfig.port = s;
		    System.out.println(ChatColor.GREEN+"Database port set to: "+ s); 
			System.out.println(ChatColor.DARK_RED+"Enter your database username:"); 
		    s = bufferRead.readLine();
		    MainConfig.config.setString("Database.Username", s);
		    MainConfig.username = s;
		    System.out.println(ChatColor.GREEN+"Database username set to: "+ s); 
			System.out.println(ChatColor.DARK_RED+"Enter your database password:"); 
		    s = bufferRead.readLine();
		    MainConfig.config.setString("Database.Password", s);
		    MainConfig.password = s;
		    System.out.println(ChatColor.GREEN+"Database password set to: "+ s); 
		    System.out.println(ChatColor.GREEN+"--------"+ChatColor.GOLD+"SQL setup complete"+ChatColor.GREEN+"--------");
		    System.out.println(ChatColor.GREEN+"Connecting...");
		    initialiseManagers();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
	}

	public void onDisable() {
		SQLManager.closeConnections();
	}
}
