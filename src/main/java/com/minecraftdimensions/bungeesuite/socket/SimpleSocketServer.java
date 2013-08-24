package com.minecraftdimensions.bungeesuite.socket;

import java.net.*;
import java.util.logging.Logger;
import java.io.*;

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
public class SimpleSocketServer extends Thread {
    
	public static int DEFAULT_PORT = 14455;
	
    public static SimpleSocketServer startServer() {
    	return startServer(SimpleSocketServer.DEFAULT_PORT);
    }
    public static synchronized SimpleSocketServer startServer(int port) {
        SimpleSocketServer simpleSocketServer = new SimpleSocketServer(port);
        simpleSocketServer.start();
        while (!simpleSocketServer.isServerRunning()) {}
        return simpleSocketServer;
    }

	private Logger jdkLogger = Logger.getLogger(this.getClass().getName());
    private int port;
    private ServerSocket serverSocket = null;
    private boolean bRunning = false;
    
    public SimpleSocketServer(int port) {
    	super("SimpleSock");
        this.port = port;
    }

    public void run() {

        try {
            serverSocket = new ServerSocket(port);
            bRunning = true;
            while (true) {
                Socket s = serverSocket.accept(); 
                new ServerThread(s).start();
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
    public void stopServer() {
    	jdkLogger.info("Server: Stopping server...");
        try {
            if (serverSocket != null)
                serverSocket.close();
        } catch (IOException e) {
        	jdkLogger.severe("stopServer() error: " + e.toString());
        }
    }
    public boolean isServerRunning() {
    	return bRunning;
    }
}
