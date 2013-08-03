package com.minecraftdimensions.bungeesuite.managers;

import java.util.ArrayList;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;

import com.minecraftdimensions.bungeesuite.configlibrary.Config;
import com.minecraftdimensions.bungeesuite.configs.Channels;
import com.minecraftdimensions.bungeesuite.objects.Channel;
import com.minecraftdimensions.bungeesuite.objects.Messages;

public class ChatManager {
	public static ArrayList<Channel> channels = new ArrayList<Channel>();
	
	public static void loadChannels(){
		LoggingManager.log(ChatColor.GOLD+"Loading channels");
		Config chan=Channels.channelsConfig;
		String server =ProxyServer.getInstance().getConsole().getName();
		//Load Global
		loadChannel(server, "Global", chan.getString("Channels.Global", Messages.CHANNEL_DEFAULT_GLOBAL));
		//Load Admin Channel
		loadChannel(server, "Admin", chan.getString("Channels.Admin",Messages.CHANNEL_DEFAULT_ADMIN));
		//Load Faction Channel
		loadChannel(server, "Faction", chan.getString("Channels.Faction",Messages.CHANNEL_DEFAULT_FACTION));
		//Load Faction Ally Channel
		loadChannel(server, "FactionAlly", chan.getString("Channels.FactionAlly",Messages.CHANNEL_DEFAULT_FACTION_ALLY));
		//Load Server Channels
		for(String servername: ProxyServer.getInstance().getServers().keySet())	{	
			loadChannel(server, servername, chan.getString("Channels.Servers."+servername+".Server",Messages.CHANNEL_DEFAULT_SERVER));
			loadChannel(server, servername+" Local", chan.getString("Channels.Servers."+servername+".Local",Messages.CHANNEL_DEFAULT_LOCAL));
			chan.getString("Channels.Servers."+servername+".Shortname", servername.substring(0,1));
			chan.getBoolean("Channels.Servers."+servername+".ForceChannel", false);
			chan.getString("Channels.Servers."+servername+".ForcedChannel", "Server");
		}
		//Load Custom Channels
		for(String custom:chan.getSubNodes("Channels.Custom Channels")){
			loadChannel(custom, chan.getString("Channels.Custom Channels."+custom+".Owner", null), chan.getString("Channels.Custom Channels."+custom+".Format", null));
		}
		LoggingManager.log(ChatColor.GOLD+"Channels loaded - "+ChatColor.DARK_GREEN+channels.size());
	}
	public static void loadChannel(String owner, String name, String format){
		Channel c = new Channel(name,format,owner,false);
		channels.add(c);
	}
	
	public void createNewCustomChannel(String owner, String name, String format){
		Config chan=Channels.channelsConfig;
		Channel c = new Channel(name,chan.getString("Channels.Custom Channels."+name+".Format", format),chan.getString("Channels.Custom Channels."+name+".Owner", owner),false);
		channels.add(c);
		LoggingManager.log("Created "+name+" channel");
	}
	
	public static boolean channelExists(String name){
		for(Channel c: channels){
			if(c.getName().equals(name)){
				return true;
			}
		}
		return false;
	}
	
	public static Channel getChannel(String name){
		for(Channel chan: channels){
			if(chan.getName().equals(name)){
				return chan;
			}
		}
		return null;
	}
	public static Channel getSimilarChannel(String name){
		for(Channel chan: channels){
			if(chan.getName().contains(name)){
				return chan;
			}
		}
		return null;
	}
	
}
