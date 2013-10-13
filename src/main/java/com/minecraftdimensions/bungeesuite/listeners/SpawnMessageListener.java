package com.minecraftdimensions.bungeesuite.listeners;

import com.minecraftdimensions.bungeesuite.managers.LoggingManager;
import com.minecraftdimensions.bungeesuite.managers.PlayerManager;
import com.minecraftdimensions.bungeesuite.managers.SpawnManager;
import com.minecraftdimensions.bungeesuite.objects.Location;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.sql.SQLException;

public class SpawnMessageListener implements Listener {

    @EventHandler
    public void receivePluginMessage( PluginMessageEvent event ) throws IOException, SQLException {
        if ( event.isCancelled() ) {
            return;
        }
        if ( !event.getTag().equalsIgnoreCase( "BSSpawns" ) ) {
            return;
        }
        event.setCancelled( true );

        DataInputStream in = new DataInputStream( new ByteArrayInputStream( event.getData() ) );

        String task = in.readUTF();
        Server s = ( Server ) event.getSender();

        if ( task.equals( "SendToProxySpawn" ) ) {
            SpawnManager.sendPlayerToProxySpawn( PlayerManager.getPlayer( in.readUTF() ), in.readBoolean() );
        } else if ( task.equals( "GetSpawns" ) ) {
            SpawnManager.sendSpawns( s );
        } else if ( task.equals( "SetServerSpawn" ) ) {
            SpawnManager.setServerSpawn( PlayerManager.getPlayer( in.readUTF() ), new Location( s.getInfo().getName(), in.readUTF(), in.readDouble(), in.readDouble(), in.readDouble(), in.readFloat(), in.readFloat() ), in.readBoolean() );
        } else if ( task.equals( "SetWorldSpawn" ) ) {
            SpawnManager.setWorldSpawn( PlayerManager.getPlayer( in.readUTF() ), new Location( s.getInfo().getName(), in.readUTF(), in.readDouble(), in.readDouble(), in.readDouble(), in.readFloat(), in.readFloat() ), in.readBoolean() );
        } else if ( task.equals( "SetNewPlayerSpawn" ) ) {
            SpawnManager.setNewPlayerSpawn( PlayerManager.getPlayer( in.readUTF() ), new Location( s.getInfo().getName(), in.readUTF(), in.readDouble(), in.readDouble(), in.readDouble(), in.readFloat(), in.readFloat() ) );
        } else if ( task.equals( "SetProxySpawn" ) ) {
            SpawnManager.setProxySpawn( PlayerManager.getPlayer( in.readUTF() ), new Location( s.getInfo().getName(), in.readUTF(), in.readDouble(), in.readDouble(), in.readDouble(), in.readFloat(), in.readFloat() ) );
        } else if ( task.equals( "SendVersion" ) ) {
            LoggingManager.log( in.readUTF() );
        }

        in.close();

    }

}
