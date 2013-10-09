package com.minecraftdimensions.bungeesuite.managers;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;

import com.minecraftdimensions.bungeesuite.BungeeSuite;
import com.minecraftdimensions.bungeesuite.configs.SpawnConfig;
import com.minecraftdimensions.bungeesuite.objects.Location;
import com.minecraftdimensions.bungeesuite.objects.BSPlayer;
import com.minecraftdimensions.bungeesuite.objects.Messages;
import com.minecraftdimensions.bungeesuite.tasks.SendPluginMessage;

public class SpawnManager {
	public static Location NewPlayerSpawn;
	public static Location ProxySpawn;
	public static String OUTGOING_CHANNEL = "BungeeSuiteSpawn";
	public static ArrayList<ProxiedPlayer> newPlayers = new ArrayList<>();
	
	public static void loadSpawns() throws SQLException{
		@SuppressWarnings("unused")
		boolean newspawn = SpawnConfig.newspawn;
		ResultSet res =SQLManager.sqlQuery("SELECT * FROM BungeeSpawns WHERE spawnname='ProxySpawn'");
		while( res.next() ){
			ProxySpawn = new Location(res.getString("server"), res.getString("world"), res.getDouble("x"), res.getDouble("y"), res.getDouble("z"), res.getFloat("yaw"), res.getFloat("pitch"));
		}
		res.close();
		res =SQLManager.sqlQuery("SELECT * FROM BungeeSpawns WHERE spawnname='NewPlayerSpawn'");
		while( res.next() ){
			NewPlayerSpawn = new Location(res.getString("server"), res.getString("world"), res.getDouble("x"), res.getDouble("y"), res.getDouble("z"), res.getFloat("yaw"), res.getFloat("pitch"));
		}
		res.close();
	}
	
	public static boolean doesProxySpawnExist(){
		return ProxySpawn!=null;
	}
	
	public static boolean doesNewPlayerSpawnExist(){
		return NewPlayerSpawn!=null;
	}

	public static void sendPlayerToProxySpawn(BSPlayer player, boolean silent) {
		if(!doesProxySpawnExist()){
			player.sendMessage(Messages.SPAWN_DOES_NOT_EXIST);
			return;
		}
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream( b );
        try {
            out.writeUTF( "TeleportToLocation" );
            out.writeUTF( player.getName() );
            out.writeUTF( ProxySpawn.getWorld() );
            out.writeDouble( ProxySpawn.getX() );
            out.writeDouble( ProxySpawn.getY() );
            out.writeDouble( ProxySpawn.getZ() );
            out.writeFloat( ProxySpawn.getYaw() );
            out.writeFloat( ProxySpawn.getPitch() );
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        sendPluginMessageTaskSpawns( ProxySpawn.getServer(), b );
        if ( !player.getServer().getInfo().equals( ProxySpawn.getServer() ) ) {
            player.getProxiedPlayer().connect( ProxySpawn.getServer() );
        }
	}
	
	

	public static void sendPlayerToNewPlayerSpawn(BSPlayer player, boolean silent) {
		if(!doesNewPlayerSpawnExist()){
			player.sendMessage(Messages.SPAWN_DOES_NOT_EXIST);
			return;
		}
		if(player==null){
			return;
		}
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream( b );
        try {
            out.writeUTF( "TeleportToLocation" );
            out.writeUTF( player.getName() );
            out.writeUTF( NewPlayerSpawn.getWorld() );
            out.writeDouble( NewPlayerSpawn.getX() );
            out.writeDouble( NewPlayerSpawn.getY() );
            out.writeDouble( NewPlayerSpawn.getZ() );
            out.writeFloat( NewPlayerSpawn.getYaw() );
            out.writeFloat( NewPlayerSpawn.getPitch() );
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        sendPluginMessageTaskSpawns( NewPlayerSpawn.getServer(), b );
        if ( !player.getServer().getInfo().equals( NewPlayerSpawn.getServer() ) ) {
            player.getProxiedPlayer().connect( NewPlayerSpawn.getServer() );
        }
	}

	
	public static void sendSpawns(Server s) throws SQLException {
		ResultSet res =SQLManager.sqlQuery("SELECT * FROM BungeeSpawns WHERE server='"+s.getInfo().getName()+"' AND NOT (spawnname = 'NewPlayerSpawn' OR spawnname = 'ProxySpawn')");
		while(res.next()){
			 ByteArrayOutputStream b = new ByteArrayOutputStream();
		        DataOutputStream out = new DataOutputStream( b );
		        try {
		            out.writeUTF( "SendSpawn" );
		            out.writeUTF( res.getString("spawnname"));
		            out.writeUTF( res.getString("world"));
		            out.writeDouble( res.getDouble("x") );
		            out.writeDouble( res.getDouble("y") );
		            out.writeDouble( res.getDouble("z") );
		            out.writeFloat( res.getFloat("yaw") );
		            out.writeFloat( res.getFloat("pitch") );
		        } catch ( IOException e ) {
		            e.printStackTrace();
		        }
		        sendPluginMessageTaskSpawns( s.getInfo(), b );
		}
		
	}
	
	public static void sendSpawn(String name, Location l){
		 ByteArrayOutputStream b = new ByteArrayOutputStream();
	        DataOutputStream out = new DataOutputStream( b );
	        try {
	            out.writeUTF( "SendSpawn" );
	            out.writeUTF( name);
	            out.writeUTF( l.getWorld());
	            out.writeDouble( l.getX() );
	            out.writeDouble( l.getY() );
	            out.writeDouble( l.getZ() );
	            out.writeFloat( l.getYaw() );
	            out.writeFloat( l.getPitch() );
	        } catch ( IOException e ) {
	            e.printStackTrace();
	        }
	        sendPluginMessageTaskSpawns( l.getServer(), b );
	}

	public static void setServerSpawn(BSPlayer p, Location l, boolean exists) throws SQLException {
		if(exists){
			SQLManager.standardQuery("UPDATE BungeeSpawns SET server = '"+l.getServer().getName()+"', world = '"+l.getWorld()+"', x = "+l.getX()+", y ="+l.getY()+", z = "+l.getZ()+", yaw = "+l.getYaw()+", pitch = "+l.getPitch()+" WHERE spawnname ='server' AND server ='"+l.getServer().getName()+"'");
			p.sendMessage(Messages.SPAWN_UPDATED);
		}else{
			SQLManager.standardQuery("INSERT INTO BungeeSpawns VALUES('server','"+l.getServer().getName()+"', '"+l.getWorld()+"', "+l.getX()+", "+l.getY()+",  "+l.getZ()+", "+l.getYaw()+",  "+l.getPitch()+")");
			p.sendMessage(Messages.SPAWN_SET);
		}
		sendSpawn("server",l);
	}

	public static void setWorldSpawn(BSPlayer p, Location l, boolean exists) throws SQLException {
		if(exists){
			SQLManager.standardQuery("UPDATE BungeeSpawns SET server = '"+l.getServer().getName()+"', world = '"+l.getWorld()+"', x = "+l.getX()+", y ="+l.getY()+", z = "+l.getZ()+", yaw = "+l.getYaw()+", pitch = "+l.getPitch()+" WHERE spawnname ='"+l.getWorld()+"' AND server ='"+l.getServer().getName()+"'");
			p.sendMessage(Messages.SPAWN_UPDATED);
		}else{
			SQLManager.standardQuery("INSERT INTO BungeeSpawns VALUES('"+l.getWorld()+"','"+l.getServer().getName()+"', '"+l.getWorld()+"', "+l.getX()+", "+l.getY()+",  "+l.getZ()+", "+l.getYaw()+",  "+l.getPitch()+")");
			p.sendMessage(Messages.SPAWN_SET);
		}
		sendSpawn(l.getWorld(),l);
	}

	public static void setNewPlayerSpawn(BSPlayer p, Location l) throws SQLException {
		
		if(NewPlayerSpawn!=null){
			SQLManager.standardQuery("UPDATE BungeeSpawns SET server = '"+l.getServer().getName()+"', world = '"+l.getWorld()+"', x = "+l.getX()+", y ="+l.getY()+", z = "+l.getZ()+", yaw = "+l.getYaw()+", pitch = "+l.getPitch()+" WHERE spawnname ='NewPlayerSpawn'");
			p.sendMessage(Messages.SPAWN_UPDATED);
		}else{
			SQLManager.standardQuery("INSERT INTO BungeeSpawns VALUES('NewPlayerSpawn','"+l.getServer().getName()+"', '"+l.getWorld()+"', "+l.getX()+", "+l.getY()+",  "+l.getZ()+", "+l.getYaw()+",  "+l.getPitch()+")");
			p.sendMessage(Messages.SPAWN_SET);
		}
		NewPlayerSpawn = l;
	}
	
	public static void setProxySpawn(BSPlayer p, Location l) throws SQLException {
		if(ProxySpawn!=null){
			SQLManager.standardQuery("UPDATE BungeeSpawns SET server = '"+l.getServer().getName()+"', world = '"+l.getWorld()+"', x = "+l.getX()+", y ="+l.getY()+", z = "+l.getZ()+", yaw = "+l.getYaw()+", pitch = "+l.getPitch()+" WHERE spawnname ='ProxySpawn'");
			p.sendMessage(Messages.SPAWN_UPDATED);
		}else{
			SQLManager.standardQuery("INSERT INTO BungeeSpawns VALUES('ProxySpawn','"+l.getServer().getName()+"', '"+l.getWorld()+"', "+l.getX()+", "+l.getY()+",  "+l.getZ()+", "+l.getYaw()+",  "+l.getPitch()+")");
			p.sendMessage(Messages.SPAWN_SET);
		}
		ProxySpawn = l;
	}
	
    public static void sendPluginMessageTaskSpawns( ServerInfo server, ByteArrayOutputStream b ) {
        BungeeSuite.proxy.getScheduler().runAsync( BungeeSuite.instance, new SendPluginMessage( OUTGOING_CHANNEL, server, b ) );
    }

}
