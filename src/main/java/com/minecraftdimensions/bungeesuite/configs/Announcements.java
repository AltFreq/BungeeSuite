package com.minecraftdimensions.bungeesuite.configs;


import java.io.File;

import com.minecraftdimensions.bungeesuite.configlibrary.Config;

public class Announcements {
	private static String configpath = File.separator+"plugins"+File.separator+"BungeeSuite"+File.separator+"announcements.yml";
	public static Config announcements = new Config(configpath);
	public static boolean announcer = announcements.getBoolean("Announcements.enabled", true);

}
