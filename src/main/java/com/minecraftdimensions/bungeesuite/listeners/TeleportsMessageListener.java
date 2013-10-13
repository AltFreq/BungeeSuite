package com.minecraftdimensions.bungeesuite.listeners;

import com.minecraftdimensions.bungeesuite.managers.LoggingManager;
import com.minecraftdimensions.bungeesuite.managers.PlayerManager;
import com.minecraftdimensions.bungeesuite.managers.TeleportManager;
import com.minecraftdimensions.bungeesuite.objects.Location;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.sql.SQLException;

public class TeleportsMessageListener implements Listener {

    @EventHandler
    public void receivePluginMessage( PluginMessageEvent event ) throws IOException, SQLException {
        if ( event.isCancelled() ) {
            return;
        }
        if ( !event.getTag().equalsIgnoreCase( "BSTeleports" ) ) {
            return;
        }

        DataInputStream in = new DataInputStream( new ByteArrayInputStream( event.getData() ) );
        String task = in.readUTF();

        if ( task.equals( "TpAccept" ) ) {
            TeleportManager.acceptTeleportRequest( PlayerManager.getPlayer( in.readUTF() ) );
            return;
        }

        if ( task.equals( "TeleportToLocation" ) ) {
            String sender = in.readUTF();
            String loc = in.readUTF();
            String locs[] = loc.split( "~!~" );
            TeleportManager.teleportPlayerToLocation( PlayerManager.getPlayer( sender ), new Location( ( ( Server ) event.getSender() ).getInfo(), locs[0], Double.parseDouble( locs[1] ), Double.parseDouble( locs[2] ), Double.parseDouble( locs[3] ) ) );
            return;
        }

        if ( task.equals( "PlayersTeleportBackLocation" ) ) {
            TeleportManager.setPlayersTeleportBackLocation( PlayerManager.getPlayer( in.readUTF() ), new Location( ( ( Server ) event.getSender() ).getInfo(), in.readUTF(), in.readDouble(), in.readDouble(), in.readDouble() ) );
            return;
        }

        if ( task.equals( "PlayersDeathBackLocation" ) ) {
            TeleportManager.setPlayersDeathBackLocation( PlayerManager.getPlayer( in.readUTF() ), new Location( ( ( Server ) event.getSender() ).getInfo(), in.readUTF(), in.readDouble(), in.readDouble(), in.readDouble() ) );
            return;
        }

        if ( task.equals( "TeleportToPlayer" ) ) {
            TeleportManager.teleportPlayerToPlayer( in.readUTF(), in.readUTF(), in.readUTF(), in.readBoolean(), in.readBoolean() );
            return;
        }

        if ( task.equals( "TpaHereRequest" ) ) {
            TeleportManager.requestPlayerTeleportToYou( in.readUTF(), in.readUTF() );
            return;
        }

        if ( task.equals( "TpaRequest" ) ) {
            TeleportManager.requestToTeleportToPlayer( in.readUTF(), in.readUTF() );
            return;
        }

        if ( task.equals( "TpDeny" ) ) {
            TeleportManager.denyTeleportRequest( PlayerManager.getPlayer( in.readUTF() ) );
            return;
        }

        if ( task.equals( "TpAll" ) ) {
            TeleportManager.tpAll( in.readUTF(), in.readUTF() );
            return;
        }

        if ( task.equals( "SendPlayerBack" ) ) {
            TeleportManager.sendPlayerToLastBack( PlayerManager.getPlayer( in.readUTF() ), in.readBoolean(), in.readBoolean() );
            return;
        }

        if ( task.equals( "ToggleTeleports" ) ) {
            TeleportManager.togglePlayersTeleports( PlayerManager.getPlayer( in.readUTF() ) );
            return;
        }

        if ( task.equals( "SendVersion" ) ) {
            LoggingManager.log( in.readUTF() );
            return;
        }
    }

}
