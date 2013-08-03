package com.minecraftdimensions.bungeesuite.managers;

import java.util.logging.Logger;

import com.minecraftdimensions.bungeesuite.configs.ChatConfig;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;

public class LoggingManager {
	static ProxyServer proxy = ProxyServer.getInstance();
	static Logger log = proxy.getLogger();

	public static void log(String message) {
		if(ChatConfig.stripChat){
			message = ChatColor.stripColor(message);
			message=message.replaceAll("&[0-9 a-f k-o r]", "");
		}
		log.info(message);
	}

}
