package com.minecraftdimensions.bungeesuite.listeners;


import java.sql.SQLException;

import com.minecraftdimensions.bungeesuite.configs.ChatConfig;
import com.minecraftdimensions.bungeesuite.managers.ChatManager;
import com.minecraftdimensions.bungeesuite.managers.IgnoresManager;
import com.minecraftdimensions.bungeesuite.managers.PlayerManager;
import com.minecraftdimensions.bungeesuite.objects.BSPlayer;
import com.minecraftdimensions.bungeesuite.objects.Messages;

import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
public class ChatListener implements Listener {
	@EventHandler
	public void playerLogin(ServerConnectedEvent e) throws SQLException {
		ChatManager.loadPlayersChannels(e.getPlayer(), e.getServer());
		ChatManager.sendPlayer(e.getPlayer().getName(),e.getServer());
		IgnoresManager.sendPlayersIgnores(PlayerManager.getPlayer(e.getPlayer()), e.getServer());
		BSPlayer p = PlayerManager.getPlayer(e.getPlayer());
		p.updateDisplayName();
		if(ChatConfig.broadcastProxyConnectionMessages){
			PlayerManager.sendBroadcast(Messages.PLAYER_CONNECT_PROXY.replace("{player}", p.getDisplayingName()));
		}
	}
	
	@EventHandler
	public void playerLogin(ChatEvent e) throws SQLException {
		if(e.isCommand()){
			return;
		}
		BSPlayer p = PlayerManager.getPlayer(e.getSender().toString());
		if(ChatManager.MuteAll){
			p.sendMessage(Messages.MUTED);
			e.setCancelled(true);
		}
		if(p.isMuted()){
			p.sendMessage(Messages.MUTED);
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void playerLogout(PlayerDisconnectEvent e) throws SQLException {
		BSPlayer p = PlayerManager.getPlayer(e.getPlayer());
		if(ChatConfig.broadcastProxyConnectionMessages){
			PlayerManager.sendBroadcast(Messages.PLAYER_DISCONNECT_PROXY.replace("{player}", p.getDisplayingName()));
		}
	}
	
}
