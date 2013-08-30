package com.minecraftdimensions.bungeesuite.listeners;

import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.sql.SQLException;

import com.minecraftdimensions.bungeesuite.managers.HomesManager;

public class HomesMessageListener implements Listener {

    @EventHandler
    public void receivePluginMessage( PluginMessageEvent event ) throws IOException, SQLException {
        if ( event.isCancelled() ) {
            return;
        }
        if ( !event.getTag().equalsIgnoreCase( "BSHomes" ) ) {
            return;
        }
        event.setCancelled( true );

        DataInputStream in = new DataInputStream( new ByteArrayInputStream( event.getData() ) );

        String task = in.readUTF();
        
        if(task.equals("DeleteHome")){
        	HomesManager.removeHome(in.readUTF(), in.readUTF());
        }
        else if(task.equals("SendPlayerHome")){
        	HomesManager.sendPlayerToHome(in.readUTF(), in.readUTF(),in.readBoolean(), in.readBoolean());
        }
        else if(task.equals("SetPlayersHome")){
        	HomesManager.createNewHome(in.readUTF(),in.readInt(),in.readInt(), in.readUTF(), ((Server)event.getSender()).getInfo().getName(), in.readUTF(), in.readDouble(), in.readDouble(), in.readDouble(), in.readFloat(), in.readFloat());
        }
        else if (task.equals("GetHomesList")){
        	HomesManager.listPlayersHomes(in.readUTF(), in.readBoolean(), in.readBoolean());
        }
        in.close();
        
    }
}