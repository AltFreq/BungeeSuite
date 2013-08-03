package com.minecraftdimensions.bungeesuite.configs;

import java.io.File;

import com.minecraftdimensions.bungeesuite.configlibrary.Config;

public class BansConfig {
		private static String configpath = File.separator+"plugins"+File.separator+"BungeeSuite"+File.separator+"bans.yml";
		public static Config bansConfig = new Config(configpath);
		public static boolean bans = bansConfig.getBoolean("Bans.Enabled", true);
		public static boolean broadcastBans = bansConfig.getBoolean("Bans.BroadcastBans", true);
		public static boolean detectAltAccs = bansConfig.getBoolean("Bans.DetectAltAccounts", true);
		public static boolean showAltAccountsIfBanned = bansConfig.getBoolean(
				"Bans.ShowAltAccountsOnlyIfBanned", false);
}
