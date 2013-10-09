package com.minecraftdimensions.bungeesuite.managers;

import java.net.*;
import java.io.*;

import com.minecraftdimensions.bungeesuite.configs.MainConfig;
import com.minecraftdimensions.bungeesuite.listeners.SocketListener;


public class SocketManager extends Thread {
    
	public static int DEFAULT_PORT = MainConfig.BackupSocketPort;
	
    public static SocketManager startServer() {
    	return startServer(SocketManager.DEFAULT_PORT);
    }
    public static synchronized SocketManager startServer(int port) {
        SocketManager simpleSocketServer = new SocketManager(port);
        simpleSocketServer.start();
        while (!SocketManager.isServerRunning()) {}
        return simpleSocketServer;
    }
    private int port;
    private static ServerSocket serverSocket = null;
    private static boolean bRunning = false;
    
    public SocketManager(int port) {
    	super("SimpleSock");
        this.port = port;
    }

    public void run() {

        try {
            serverSocket = new ServerSocket(port);
            bRunning = true;
            while (true) {
                Socket s = serverSocket.accept(); 
                new SocketListener(s).start();
            }
        } 
        catch (IOException e) {
            if (serverSocket != null && serverSocket.isClosed())
            	; //Ignore if closed by stopServer() call
            else
            	e.printStackTrace();
        }
        finally {
        	serverSocket = null;
        	bRunning = false;
        }
    }
    public static void stopServer() {
        try {
            if (serverSocket != null)
                serverSocket.close();
        } catch (IOException e) {
        }
    }
    public static boolean isServerRunning() {
    	return bRunning;
    }
}
