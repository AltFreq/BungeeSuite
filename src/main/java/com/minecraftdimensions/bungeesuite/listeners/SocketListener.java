package com.minecraftdimensions.bungeesuite.listeners;

import java.net.*;
import java.io.*;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;

import com.minecraftdimensions.bungeesuite.managers.PlayerManager;
import com.minecraftdimensions.bungeesuite.managers.TeleportManager;
import com.minecraftdimensions.bungeesuite.objects.Location;
/*
 * Very simple socket server example. That responds to a single object with
 * another object. The
 */
public class SocketListener extends Thread {

    private Socket socket = null;
    private int port;

    public SocketListener(Socket socket) {
        this.socket = socket;
    }
//this shit confuses me!
    public void run() {
        try {
    		DataInputStream in = new DataInputStream(socket.getInputStream());
    		
    		port = in.readInt();
    		int length = in.readInt();
    		byte[] message = new byte[length];
    		in.readFully(message, 0, message.length);
    		DataInputStream data = new DataInputStream(new ByteArrayInputStream(message));
    		
    		String task  = data.readUTF();
    		
    		if(task.equals("PlayersTeleportBackLocation")){
    			TeleportManager.setPlayersTeleportBackLocation(PlayerManager.getPlayer(data.readUTF()), new Location(getServer(new InetSocketAddress(socket.getInetAddress(), port)), data.readUTF(), data.readDouble(), data.readDouble(), data.readDouble()));
    		}
    		
    		
    		data.close();
    		in.close();

        } catch (IOException e) {
        	e.printStackTrace();
        }
    }
    
	private ServerInfo getServer(InetSocketAddress inetSocketAddress) {
		for(ServerInfo s: ProxyServer.getInstance().getServers().values()){
			if(s.getAddress().equals(inetSocketAddress)){
				return s;
			}
		}
		return null;
	}
}
