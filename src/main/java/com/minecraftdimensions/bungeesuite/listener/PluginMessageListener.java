package com.minecraftdimensions.bungeesuite.listener;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.sql.SQLException;

import com.minecraftdimensions.bungeesuite.BungeeSuite;

import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PluginMessageListener implements Listener {
	BungeeSuite plugin;

	public PluginMessageListener(BungeeSuite bungeeSuite) {
		plugin = bungeeSuite;
	}
	
	@EventHandler
	public void receivePluginMessage(PluginMessageEvent event)
			throws IOException, SQLException {
		if (!event.getTag().equalsIgnoreCase("BungeeSuite")) {
			return;
		}

		DataInputStream in = new DataInputStream(new ByteArrayInputStream(
				event.getData()));
		String channel = in.readUTF();
		if (channel.equalsIgnoreCase("SendGlobalMessage")) {
			String sender = in.readUTF();
			String format = in.readUTF();
			plugin.utils.sendGlobalMessage(((Server)event.getSender()).getInfo(),sender, format);
			return;
		}
		if (channel.equalsIgnoreCase("SendCleanGlobalMessage")) {
			String sender = in.readUTF();
			String format = in.readUTF();
			plugin.utils.sendCleanGlobal(((Server)event.getSender()).getInfo(),sender, format);
			return;
		}
		if (channel.equalsIgnoreCase("SendChat")) {
			String format = in.readUTF();
			plugin.utils.sendSpyMessageAndLog(((Server)event.getSender()).getInfo(), format);
			return;
		}
		if (channel.equalsIgnoreCase("GetServerMessage")) {
			String sender = in.readUTF();
			String message = in.readUTF();
			plugin.utils.sendMessage(sender, message);
			return;
		}
		if (channel.equalsIgnoreCase("PlayersBackLocation")) {
			String sender = in.readUTF();
			String location = ((Server)event.getSender()).getInfo().getName()+"~";
			location+= in.readDouble()+"~";
			location+=in.readDouble()+"~";
			location+=in.readDouble()+"~";
			location+=in.readUTF();
			plugin.backLocations.put(sender, location);
			return;
		}
		if (channel.equalsIgnoreCase("ReloadChat")) {
			String sender = in.readUTF();
			plugin.utils.reloadChat(sender);
			return;
		}
		if (channel.equalsIgnoreCase("GetPlayersInfo")) {
			String player = in.readUTF();
			plugin.utils.getPlayersInfo(player);
			return;
		}
		if (channel.equalsIgnoreCase("ReplyToPlayer")) {
			String sender = in.readUTF();
			String message = in.readUTF();
			plugin.utils.replyToPlayer(sender, message);
			return;
		}
		if (channel.equalsIgnoreCase("SendPrivateMessage")) {
			String sender = in.readUTF();
			String player = in.readUTF();
			String message = in.readUTF();
			plugin.utils.sendPrivateMessage(sender, player,message);
			return;
		}
		if (channel.equalsIgnoreCase("MutePlayer")) {
			String sender = in.readUTF();
			String player = in.readUTF();
			plugin.utils.mutePlayer(sender, player);
			return;
		}
		if (channel.equalsIgnoreCase("NicknamePlayer")) {
			String sender = in.readUTF();
			String player = in.readUTF();
			String nickname = in.readUTF();
			plugin.utils.nicknamePlayer(sender, player, nickname);
			return;
		}
		if (channel.equalsIgnoreCase("IgnorePlayer")) {
			String sender = in.readUTF();
			String player = in.readUTF();
			plugin.utils.ignorePlayer(sender,player);
			return;
		}
		if (channel.equalsIgnoreCase("UnIgnorePlayer")) {
			String sender = in.readUTF();
			String player = in.readUTF();
			plugin.utils.unIgnorePlayer(sender,player);
			return;
		}
		if (channel.equalsIgnoreCase("ToggleToChannel")) {
			String sender = in.readUTF();
			String channelToToggle = in.readUTF();
			plugin.utils.toggleToChannel(sender, channelToToggle);
			return;
		}
		if (channel.equalsIgnoreCase("ChatSpy")) {
			String sender = in.readUTF();
			plugin.utils.chatSpy(sender);
			return;
		}
		if (channel.equalsIgnoreCase("CleanChat")) {
			String sender = in.readUTF();
			plugin.utils.cleanPlayersChat(sender);
			return;
		}
		if (channel.equalsIgnoreCase("GetPlayersNextChannel")) {
			String sender = in.readUTF();
			plugin.utils.getPlayersNextChannel(sender);
			return;
		}
		if (channel.equalsIgnoreCase("ChannelChange")) {
			String sender = in.readUTF();
			String nextChannel = in.readUTF();
			plugin.utils.setPlayersChannel(sender, nextChannel);
			return;
		}
		if (channel.equalsIgnoreCase("UnMuteAll")) {
			String sender = in.readUTF();
			plugin.utils.unMuteAll(sender);
			return;
		}
		if (channel.equalsIgnoreCase("UnMutePlayer")) {
			String sender = in.readUTF();
			String player = in.readUTF();
			plugin.utils.unMutePlayer(sender, player);
			return;
		}
		if (channel.equalsIgnoreCase("MuteAll")) {
			String sender = in.readUTF();
			plugin.utils.muteAll(sender);
			return;
		}
		if (channel.equalsIgnoreCase("CreateChatConfig")) {
			plugin.utils.createChatConfig();
			return;
		}
		if (channel.equalsIgnoreCase("GetChannelFormats")) {
			plugin.utils.getChannelFormats(((Server)event.getSender()).getInfo().getName());
			return;
		}
		if (channel.equalsIgnoreCase("GetLocalRadius")) {
			String server = ((Server)event.getSender()).getInfo().getName();
			plugin.utils.sendLocalChannelRadius(server);
			return;
		}
		if (channel.equalsIgnoreCase("NicknamePermission")) {
			String player = in.readUTF();
			String nickname = in.readUTF();
			plugin.utils.nicknamePermission(player, nickname);
			return;
		}
		if (channel.equalsIgnoreCase("TeleportToPlayerRequest")) {
			String player = in.readUTF();
			String target = in.readUTF();
			plugin.utils.TeleportToPlayer(player, target);
			return;
		}
		if (channel.equalsIgnoreCase("TpAll")) {
			String player = in.readUTF();
			String targetPlayer = in.readUTF();
			plugin.utils.TpAll(player, targetPlayer);
			return;
		}
		if (channel.equalsIgnoreCase("TpaRequest")) {
			String player = in.readUTF();
			String targetPlayer = in.readUTF();
			plugin.utils.tpaRequest(player, targetPlayer);
			return;
		}
		if (channel.equalsIgnoreCase("TpaHereRequest")) {
			String player = in.readUTF();
			String targetPlayer = in.readUTF();
			plugin.utils.tpaHereRequest(player, targetPlayer);
			return;
		}
		if (channel.equalsIgnoreCase("TpAccept")) {
			String player = in.readUTF();
			plugin.utils.tpAccept(player);
			return;
		}
		if (channel.equalsIgnoreCase("TpDeny")) {
			String player = in.readUTF();
			plugin.utils.tpDeny(player);
			return;
		}
		if (channel.equalsIgnoreCase("CreateTable")) {
			String name = in.readUTF();
			String query = in.readUTF();
			plugin.utils.createTable(name, query);
			return;
		}
		if (channel.equalsIgnoreCase("WarpPlayerB")) {
			String sender = in.readUTF();
			String player = in.readUTF();
			String warpName= in.readUTF();
			boolean paccess = in.readBoolean();
			plugin.utils.warpPlayer(sender,player,warpName, paccess);
			return;
		}
		if (channel.equalsIgnoreCase("WarpList")) {
			String name = in.readUTF();
			boolean permission = in.readBoolean();
			plugin.utils.getWarpsList(name, permission);
			return;
		}
		if (channel.equalsIgnoreCase("DeleteWarp")) {
			String sender = in.readUTF();
			String name = in.readUTF();
			plugin.utils.deleteWarp(sender, name);
			return;
		}
		if (channel.equalsIgnoreCase("CreateWarp")) {
			String sender = in.readUTF();
			String name = in.readUTF();
			String world = in.readUTF();
			double x = in.readDouble();
			double y = in.readDouble();
			double z = in.readDouble();
			float yaw = in.readFloat();
			float pitch = in.readFloat();
			boolean hidden = in.readBoolean();
			plugin.utils.createWarp(sender,name,((Server)event.getSender()).getInfo().getName(),world, x, y, z, yaw, pitch, hidden);
			return;
		}
		if (channel.equalsIgnoreCase("GetForcedServerChannel")) {
			plugin.utils.getForcedServerChannel(((Server)event.getSender()).getInfo().getName());
			return;
		}
		if (channel.equalsIgnoreCase("PortalWarpPlayer")) {
			String sender = in.readUTF();
			String player = in.readUTF();
			String warpName= in.readUTF();
			plugin.utils.warpPlayerSilent(sender,player,warpName);
			return;
		}
		if (channel.equalsIgnoreCase("GetPortals")) {
			plugin.utils.getPortals(((Server)event.getSender()).getInfo().getName());
			return;
		}
		if (channel.equalsIgnoreCase("CreatePortal")) {
			String sender = in.readUTF();
			String name = in.readUTF();
			String type = in.readUTF();
			String dest = in.readUTF();
			String world = in.readUTF();
			String filltype = in.readUTF();
			int xmax = in.readInt();
			int xmin = in.readInt();
			int ymax = in.readInt();
			int ymin = in.readInt();
			int zmax = in.readInt();
			int zmin = in.readInt();
			plugin.utils.createPortal(sender,name,type,dest,world,filltype,xmax,xmin,ymax,ymin,zmax,zmin);
			return;
		}
		if (channel.equalsIgnoreCase("DeletePortal")) {
			String sender = in.readUTF();
			String portal = in.readUTF();
			plugin.utils.deletePortal(sender,portal);
			return;
		}
		if (channel.equalsIgnoreCase("ListPortals")) {
			String sender = in.readUTF();
			plugin.utils.listPortals(sender);
			return;
		}
		if (channel.equalsIgnoreCase("SendPlayerToSpawn")) {
			String sender = in.readUTF();
			plugin.utils.sendPlayerToSpawn(sender, "spawn");
			return;
		}
		if (channel.equalsIgnoreCase("SetSpawn")) {
			String sender = in.readUTF();
			String type = in.readUTF();
			String world = in.readUTF();
			double x = in.readDouble();
			double y = in.readDouble();
			double z = in.readDouble();
			float yaw = in.readFloat();
			float pitch = in.readFloat();
			plugin.utils.setSpawn(sender,type,((Server)event.getSender()).getInfo().getName(),world,x,y,z,yaw,pitch);
			return;
		}
		if(channel.equalsIgnoreCase("BanPlayer")){
			String sender = in.readUTF();
			String player = in.readUTF();
			String msg = in.readUTF();
			plugin.utils.banPlayer(sender,player,msg);
			return;
		}
		if(channel.equalsIgnoreCase("IPBanPlayer")){
			String sender = in.readUTF();
			String player = in.readUTF();
			String msg = in.readUTF();
			plugin.utils.ipBanPlayer(sender,player,msg);
			return;
		}
		if(channel.equalsIgnoreCase("UnIPBanPlayer")){
			String sender = in.readUTF();
			String player = in.readUTF();
			String msg = in.readUTF();
			plugin.utils.unIpBanPlayer(sender,player,msg);
			return;
		}
		if(channel.equalsIgnoreCase("CreateBansConfig")){
			plugin.utils.createBanConfig();
			return;
		}
		if(channel.equalsIgnoreCase("ReloadBans")){
			String sender = in.readUTF();
			plugin.utils.reloadBans(sender);
			return;
		}
		if(channel.equalsIgnoreCase("KickAll")){
			String msg = in.readUTF();
			plugin.utils.kickAll(msg);
			return;
		}
		if(channel.equalsIgnoreCase("KickPlayer")){
			String sender = in.readUTF();
			String player = in.readUTF();
			String msg = in.readUTF();
			plugin.utils.kickPlayer(sender,player,msg);
			return;
		}
		if(channel.equalsIgnoreCase("TempBanPlayer")){
			String sender = in.readUTF();
			String player = in.readUTF();
			int minute = in.readInt();
			int hour = in.readInt();
			int day = in.readInt();
			String message= in.readUTF();
			plugin.utils.tempBanPlayer(sender,player, minute, hour, day, message);
			return;
		}
		if(channel.equalsIgnoreCase("UnbanPlayer")){
			String sender = in.readUTF();
			String player = in.readUTF();
			plugin.utils.unbanPlayer(sender,player);
			return;
		}
		if(channel.equalsIgnoreCase("AddColumns")){
			String table = in.readUTF();
			String query = in.readUTF();
			plugin.utils.addColumns(table,query);
			return;
		}
		if(channel.equalsIgnoreCase("SendPlayerBack")){
			String sender = in.readUTF();
			plugin.utils.sendPlayerBack(sender);
			return;
		}
		if(channel.equalsIgnoreCase("WhoIsPlayer")){
			String sender = in.readUTF();
			String player = in.readUTF();
			plugin.utils.whoIsPlayer(sender, player);
			return;
		}
		if(channel.equalsIgnoreCase("CreateHomeConfig")){
			plugin.utils.createHomeConfig();
			return;
		}
		if(channel.equalsIgnoreCase("SetPlayersHome")){
			String player = in.readUTF();
			String permissions = in.readUTF();
			String location = in.readUTF();
			String home = in.readUTF();
			plugin.utils.setPlayersHome(player,permissions,location,home,((Server)event.getSender()).getInfo().getName());
			return;
		}
		if(channel.equalsIgnoreCase("SetPlayersHomeImport")){
			String player = in.readUTF();
			String location = in.readUTF();
			String home = in.readUTF();
			plugin.utils.setPlayersHome(player,location,home,((Server)event.getSender()).getInfo().getName());
			return;
		}
		if(channel.equalsIgnoreCase("GetPlayersHome")){
			String player = in.readUTF();
			plugin.utils.sendPlayersHome(player, ((Server)event.getSender()).getInfo().getName());
			return;
		}
		if(channel.equalsIgnoreCase("DeletePlayersHome")){
			String player = in.readUTF();
			String home = in.readUTF();
			plugin.utils.deletePlayersHome(player,home, ((Server)event.getSender()).getInfo().getName());
			return;
		}
		if(channel.equalsIgnoreCase("ListPlayersHomes")){
			String player = in.readUTF();
			plugin.utils.listPlayersHomes(player);
			return;
		}
		if(channel.equalsIgnoreCase("SendPlayerHome")){
			String player = in.readUTF();
			String homename = in.readUTF();
			plugin.utils.sendPlayerToHome(player, homename, ((Server)event.getSender()).getInfo().getName());
			return;
		}
		if(channel.equalsIgnoreCase("GetHomesGroupList")){
			plugin.utils.sendHomeGroups(((Server)event.getSender()).getInfo().getName());
			return;
		}
		if(channel.equalsIgnoreCase("ReloadHomes")){
			String player = in.readUTF();
			plugin.utils.reloadHomes(player);
			return;
		}
		if (channel.equalsIgnoreCase("TempMute")) {
			String sender = in.readUTF();
			String player = in.readUTF();
			int time = in.readInt();
			plugin.utils.tempMutePlayer(sender, player, time);
			return;
		}
		if (channel.equalsIgnoreCase("CheckPlayerBans")) {
			String sender = in.readUTF();
			String player = in.readUTF();
			plugin.utils.checkPlayerBans(sender,player);
			return;
		}
		if (channel.equalsIgnoreCase("GetWorldSpawns")) {
			plugin.utils.sendWorldSpawns(((Server)event.getSender()).getInfo());
			return;
		}
		if (channel.equalsIgnoreCase("CreateSpawnConfig")) {
			plugin.utils.createSpawnConfig();
			return;
		}
		if (channel.equalsIgnoreCase("ToggleTeleports")) {
			String player = in.readUTF();
			plugin.utils.toggleTeleports(player);
			return;
		}
	}

}
