package com.minecraftdimensions.bungeesuite;

import java.sql.SQLException;

public class NewPlayerSpawner implements Runnable {
	
	  private final String name;
	private BungeeSuite plugin;
	    
	    public NewPlayerSpawner(String name, BungeeSuite plugin) {
	        this.name = name;
	        this.plugin = plugin;
	    }

	    public void run() {
	    	try {
				plugin.utils.sendPlayerToSpawn(name, "newplayerspawn");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }


}
