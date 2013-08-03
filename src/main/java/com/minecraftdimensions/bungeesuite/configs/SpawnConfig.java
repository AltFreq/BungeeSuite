package com.minecraftdimensions.bungeesuite.configs;

import java.io.File;

import com.minecraftdimensions.bungeesuite.configlibrary.Config;

public class SpawnConfig {
	private static String configpath = File.separator+"plugins"+File.separator+"BungeeSuite"+File.separator+"spawns.yml";
	public static Config spawnConfig = new Config(configpath);
	public static boolean newspawn = spawnConfig.getBoolean("Spawn new players at newspawn",
			false);
	
}
