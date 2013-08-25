package com.minecraftdimensions.bungeesuite.socket;

import java.net.*;
import java.util.logging.Logger;
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
public class ServerThread extends Thread {

	private Logger jdkLogger = Logger.getLogger(this.getClass().getName());
    private Socket socket = null;
    private int port;

    public ServerThread(Socket socket) {
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
    		
    		String task  = in.readUTF();
    		
    		if(task.equals("PlayersTeleportBackLocation")){
    			TeleportManager.setPlayersTeleportBackLocation(PlayerManager.getPlayer(in.readUTF()), new Location(getServer(new InetSocketAddress(socket.getInetAddress(), port)), in.readUTF(), in.readDouble(), in.readDouble(), in.readDouble()));
    			return;
    		}
    		
    		
    		data.close();
    		in.close();

        } catch (IOException e) {
        	jdkLogger.severe("run() error: " + e.toString());
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
