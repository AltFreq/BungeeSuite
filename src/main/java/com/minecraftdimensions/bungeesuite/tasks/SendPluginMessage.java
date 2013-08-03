package com.minecraftdimensions.bungeesuite.tasks;

import java.io.ByteArrayOutputStream;

import net.md_5.bungee.api.config.ServerInfo;

public class SendPluginMessage implements Runnable {
	
	  private final String channel;
	    private final ByteArrayOutputStream bytes;
	    private final ServerInfo server;
	    
	    public SendPluginMessage(String channel, ServerInfo server, ByteArrayOutputStream bytes) {
	        this.channel = channel;
	        this.bytes = bytes;
	        this.server = server;
	    }

	    public void run() {
	       server.sendData(channel, bytes.toByteArray());
	    }


}
