package com.minecraftdimensions.bungeesuite.configs;

import java.io.File;

import com.minecraftdimensions.bungeesuite.configlibrary.Config;

public class BansConfig {
		private static String configpath = File.separator+"plugins"+File.separator+"BungeeSuite"+File.separator+"bans.yml";
		public static Config bansConfig = new Config(configpath);
		public static boolean bans = bansConfig.getBoolean("Bans.Enabled", true);
		public static boolean broadcastBans = bansConfig.getBoolean("Bans.BroadcastBans", true);
		public static boolean broadcastKicks = bansConfig.getBoolean("Bans.BroadcastKicks", true);
		public static boolean detectAltAccs = bansConfig.getBoolean("Bans.DetectAltAccounts", true);
		public static boolean showAltAccountsIfBanned = bansConfig.getBoolean(
				"Bans.ShowAltAccountsOnlyIfBanned", false);
		
		
		public static void reloadBans(){
			bansConfig = new Config(configpath);
			bans = bansConfig.getBoolean("Bans.Enabled", true);
			broadcastBans= bansConfig.getBoolean("Bans.BroadcastBans", true);
			broadcastKicks = bansConfig.getBoolean("Bans.BroadcastKicks", true);
			detectAltAccs = bansConfig.getBoolean("Bans.DetectAltAccounts", true);
			showAltAccountsIfBanned = bansConfig.getBoolean("Bans.ShowAltAccountsOnlyIfBanned", false);
			
		}
}
