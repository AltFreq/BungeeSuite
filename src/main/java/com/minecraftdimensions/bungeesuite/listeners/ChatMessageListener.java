package com.minecraftdimensions.bungeesuite.listeners;

import com.minecraftdimensions.bungeesuite.configs.ChatConfig;
import com.minecraftdimensions.bungeesuite.managers.*;
import com.minecraftdimensions.bungeesuite.objects.BSPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.sql.SQLException;
import net.md_5.bungee.api.ChatColor;

public class ChatMessageListener implements Listener {

    @EventHandler
    public void receivePluginMessage( PluginMessageEvent event ) throws IOException, SQLException {
        if ( event.isCancelled() ) {
            return;
        }
        if ( !( event.getSender() instanceof Server ) )
            return;
        if ( !event.getTag().equalsIgnoreCase( "BSChat" ) ) {
            return;
        }
        event.setCancelled( true );
        Server s = ( Server ) event.getSender();
        DataInputStream in = new DataInputStream( new ByteArrayInputStream( event.getData() ) );
        String task = in.readUTF();
        if ( task.equals( "LogChat" ) ) {
            String message = in.readUTF();
            if ( ChatConfig.logChat ) {
                LoggingManager.log( message );
            }
            PlayerManager.sendMessageToSpies( s, message );

            return;
        }
        if ( task.equals( "GlobalChat" ) ) {
            String sender = in.readUTF();
            String message = in.readUTF();
            ChatManager.sendGlobalChat( sender, message, s );
            return;
        }
        if ( task.equals("RealName") ) {
            String name = in.readUTF();
            String nick = in.readUTF();
            BSPlayer p = PlayerManager.getSimilarNickPlayer(nick);
            if(p == null)
                PlayerManager.sendMessageToPlayer(name, ChatColor.GRAY + nick + ChatColor.RESET + ChatColor.GRAY + " was not found!");
            else
                PlayerManager.sendMessageToPlayer(name, ChatColor.GRAY + p.getNickname() + ChatColor.RESET + ChatColor.GRAY + " is " + p.getName());
            return;
        }
        if ( task.equals( "GetServerChannels" ) ) {
            ChatManager.sendServerData( s.getInfo() );
            ChatManager.sendDefaultChannelsToServer( s.getInfo() );
            PrefixSuffixManager.sendPrefixAndSuffixToServer( s.getInfo() );
            return;
        }
        if ( task.equals( "GetFactionChannels" ) ) {
            ChatManager.sendFactionChannelsToServer( s.getInfo() );
            return;
        }
        if ( task.equals( "GetTownyChannels" ) ) {
            ChatManager.sendTownyChannelsToServer( s.getInfo() );
            return;
        }
        if ( task.equals( "AdminChat" ) ) {
            String message = in.readUTF();
            ChatManager.sendAdminChat( message, ( Server ) event.getSender() );
            return;
        }
        if ( task.equals( "GetPlayer" ) ) {
            String player = in.readUTF();
            ChatManager.sendPlayer( player, s, true );
            IgnoresManager.sendPlayersIgnores( PlayerManager.getPlayer( player ), s );
            return;
        }
        if ( task.equals( "AFKPlayer" ) ) {
            ChatManager.setPlayerAFK( in.readUTF(), in.readBoolean(), in.readBoolean() );
            return;
        }
        if ( task.equals( "ReplyToPlayer" ) ) {
            ChatManager.replyToPlayer( in.readUTF(), in.readUTF() );
            return;
        }
        if ( task.equals( "PrivateMessage" ) ) {
            BSPlayer p = PlayerManager.getPlayer( in.readUTF() );
            PlayerManager.sendPrivateMessageToPlayer( p, in.readUTF(), in.readUTF() );
            return;
        }
        if ( task.equals( "SetChatSpy" ) ) {
            ChatManager.setChatSpy( in.readUTF() );
            return;
        }
        if ( task.equals( "IgnorePlayer" ) ) {
            BSPlayer p = PlayerManager.getPlayer( in.readUTF() );
            IgnoresManager.addIgnore( p, in.readUTF() );
            return;
        }
        if ( task.equals( "UnIgnorePlayer" ) ) {
            BSPlayer p = PlayerManager.getPlayer( in.readUTF() );
            IgnoresManager.removeIgnore( p, in.readUTF() );
            return;
        }
        if ( task.equals( "MuteAll" ) ) {
            ChatManager.muteAll( in.readUTF() );
            return;
        }
        if ( task.equals( "MutePlayer" ) ) {
            ChatManager.MutePlayer( in.readUTF(), in.readUTF(), in.readBoolean() );
            return;
        }
        if ( task.equals( "NickNamePlayer" ) ) {
            ChatManager.nickNamePlayer( in.readUTF(), in.readUTF(), in.readUTF(), in.readBoolean() );
            return;
        }
        if ( task.equals( "TempMutePlayer" ) ) {
            ChatManager.tempMutePlayer( in.readUTF(), in.readUTF(), in.readInt() );
            return;
        }
        if ( task.equals( "ReloadChat" ) ) {
            ChatManager.reloadChat( in.readUTF() );
            return;
        }
        if ( task.equals( "TogglePlayersChannel" ) ) {
            ChatManager.togglePlayersChannel( in.readUTF(), in.readBoolean(), in.readBoolean(), in.readBoolean(), in.readBoolean() );
            return;
        }
        if ( task.equals( "TogglePlayersFactionsChannel" ) ) {
            ChatManager.togglePlayersFactionsChannel( in.readUTF(), in.readBoolean() );
            return;
        }
        if ( task.equals( "ToggleToPlayersFactionChannel" ) ) {
            ChatManager.toggleToPlayersFactionChannel( in.readUTF(), in.readUTF(), in.readBoolean() );
            return;
        }
        if ( task.equals( "TogglePlayersTownyChannel" ) ) {
            ChatManager.togglePlayersTownyChannel( in.readUTF(), in.readBoolean(), in.readBoolean() );
            return;
        }
        if ( task.equals( "ToggleToPlayersTownyChannel" ) ) {
            ChatManager.toggleToPlayersTownyChannel( in.readUTF(), in.readUTF(), in.readBoolean(), in.readBoolean() );
            return;
        }
        if ( task.equals( "TogglePlayerToChannel" ) ) {
            ChatManager.togglePlayerToChannel( in.readUTF(), in.readUTF(), in.readBoolean(), in.readBoolean(), in.readBoolean(), in.readBoolean() );
            return;
        }
        if ( task.equals( "GetChannelInfo" ) ) {
            ChatManager.sendPlayerChannelInformation( in.readUTF(), in.readUTF(), in.readBoolean() );
            return;
        }
        if ( task.equals( "SetChannelFormat" ) ) {
            ChatManager.setChannelsFormat( in.readUTF(), in.readUTF(), in.readBoolean() );
            return;
        }
        if ( task.equals( "SendVersion" ) ) {
            LoggingManager.log( in.readUTF() );
            return;
        }
    }

}
