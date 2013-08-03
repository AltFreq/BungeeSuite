package com.minecraftdimensions.bungeesuite.configs;

import java.io.File;

import com.minecraftdimensions.bungeesuite.configlibrary.Config;

public class Channels {
	 private static String configpath = File.separator+"plugins"+File.separator+"BungeeSuite"+File.separator+"channels.yml";
	 public static Config channelsConfig = new Config(configpath);
}
