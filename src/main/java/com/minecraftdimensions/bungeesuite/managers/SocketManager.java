package com.minecraftdimensions.bungeesuite.managers;

import java.net.*;
import java.util.logging.Logger;
import java.io.*;

import com.minecraftdimensions.bungeesuite.listeners.SocketListener;

/*
 * Very simple socket server example. That responds to a single object with
 * another object. Could be used as the basis for something more complex, but
 * this illistrates the basics of TCP/IP communication.
 * 
 * A Client will call a Server with a message. The Server will respond with a message.
 * In this simplist implementation the messages can be any serializable object.
 * 
 * To setup a server:
 *  1) Create your handler, a class that implements the simple SimpleSocketHandler 
 *     interface.
 *  2) Call the static SimpleSocketServer.startServer() method with a port and an 
 *     instance of your SimpleSocketHandler defined above.
 *     
 * To call the server from a client:
 *  1) Call the static Client.send() method specifying the server host, port, and
 *     message. This returns your response.
 */
public class SocketManager extends Thread {
    
	public static int DEFAULT_PORT = 14455;
	
    public static SocketManager startServer() {
    	return startServer(SocketManager.DEFAULT_PORT);
    }
    public static synchronized SocketManager startServer(int port) {
        SocketManager simpleSocketServer = new SocketManager(port);
        simpleSocketServer.start();
        while (!SocketManager.isServerRunning()) {}
        return simpleSocketServer;
    }

	private Logger jdkLogger = Logger.getLogger(this.getClass().getName());
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
        jdkLogger.info("Stopped");
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
