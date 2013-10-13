package com.minecraftdimensions.bungeesuite.listeners;

import com.minecraftdimensions.bungeesuite.managers.LoggingManager;
import com.minecraftdimensions.bungeesuite.managers.PlayerManager;
import com.minecraftdimensions.bungeesuite.managers.PortalManager;
import com.minecraftdimensions.bungeesuite.objects.BSPlayer;
import com.minecraftdimensions.bungeesuite.objects.Location;
import com.minecraftdimensions.bungeesuite.objects.Messages;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.sql.SQLException;

public class PortalsMessageListener implements Listener {

    @EventHandler
    public void receivePluginMessage( PluginMessageEvent event ) throws IOException, SQLException {
        if ( event.isCancelled() ) {
            return;
        }
        if ( !( event.getSender() instanceof Server ) )
            return;
        if ( !event.getTag().equalsIgnoreCase( "BSPortals" ) ) {
            return;
        }
        event.setCancelled( true );

        DataInputStream in = new DataInputStream( new ByteArrayInputStream( event.getData() ) );

        String task = in.readUTF();
        Server s = ( Server ) event.getSender();
        if ( task.equals( "TeleportPlayer" ) ) {
            PortalManager.teleportPlayer( PlayerManager.getPlayer( in.readUTF() ), in.readUTF(), in.readUTF(), in.readBoolean() );
        } else if ( task.equals( "ListPortals" ) ) {
            PortalManager.listPortals( PlayerManager.getPlayer( in.readUTF() ) );
        } else if ( task.equals( "DeletePortal" ) ) {
            PortalManager.deletePortal( PlayerManager.getPlayer( in.readUTF() ), in.readUTF() );
        } else if ( task.equals( "SetPortal" ) ) {
            BSPlayer sender = PlayerManager.getPlayer( in.readUTF() );
            boolean selection = in.readBoolean();
            if ( !selection ) {
                sender.sendMessage( Messages.NO_SELECTION_MADE );
            } else {
                PortalManager.setPortal( sender, in.readUTF(), in.readUTF(), in.readUTF(), in.readUTF(), new Location( s.getInfo(), in.readUTF(), in.readDouble(), in.readDouble(), in.readDouble() ), new Location( s.getInfo(), in.readUTF(), in.readDouble(), in.readDouble(), in.readDouble() ) );
            }
        } else if ( task.equals( "RequestPortals" ) ) {
            PortalManager.getPortals( s.getInfo() );
        } else if ( task.equals( "SendVersion" ) ) {
            LoggingManager.log( in.readUTF() );
        }

        in.close();

    }

}
