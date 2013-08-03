package com.minecraftdimensions.bungeesuite.configs;

import java.io.File;

import com.minecraftdimensions.bungeesuite.configlibrary.Config;

public class ChatConfig {

		private static String configpath = File.separator+"plugins"+File.separator+"BungeeSuite"+File.separator+"chat.yml";
		
		 public static Config c = new Config(configpath);
		 public static boolean logChat = c.getBoolean("Log chat to console", true);
		 public static boolean stripChat = c.getBoolean("Strip color from log", true);
		 public static boolean chatSpySeesAll = c
		 .getBoolean("ChatSpy sees all chat", false);
		 public static int nickNameLimit = c.getInt("NicknameLengthLimit", 16);
//		 public static boolean CleanTab = c.getBoolean("CleanTab", false);
//		 public static String cleanChatRegex = c.getString("CleanChatRegex",
//		 "([&][klmno])");
		 public static String globalChatRegex = c.getString("GlobalChatRegex",
		 "\\{(factions_.*?)\\}");
		 public static int localChatRange = c.getInt("Channels.LocalChatRadius", 100);
		 public static String defaultChannel = c.getString("DefaultChannel", "Global");
		 public static String adminColor = c.getString("AdminChatColor", "&f");
		 public static boolean usingPrefix = c.getBoolean("Prefix.Enabled", false);
		 public static boolean usingSuffix = c.getBoolean("Suffix.Enabled", false);

		 

}
