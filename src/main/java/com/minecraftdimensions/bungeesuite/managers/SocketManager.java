package com.minecraftdimensions.bungeesuite.managers;

import com.minecraftdimensions.bungeesuite.configs.MainConfig;
import com.minecraftdimensions.bungeesuite.listeners.SocketListener;
import net.md_5.bungee.api.ChatColor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class SocketManager extends Thread {

    public static int DEFAULT_PORT = MainConfig.BackupSocketPort;

    public static SocketManager startServer() {
        return startServer( SocketManager.DEFAULT_PORT );
    }

    public static synchronized SocketManager startServer( int port ) {
        LoggingManager.log( ChatColor.GREEN + "Starting socket on " + port );
        SocketManager simpleSocketServer = new SocketManager( port );
        simpleSocketServer.start();
        LoggingManager.log( ChatColor.GREEN + "Listener started" );
        while ( !SocketManager.isServerRunning() ) {
        }
        LoggingManager.log( ChatColor.GREEN + "Did it pause here?" );
        return simpleSocketServer;
    }

    private int port;
    private static ServerSocket serverSocket = null;
    private static boolean bRunning = false;

    public SocketManager( int port ) {
        super( "SimpleSock" );
        this.port = port;
    }

    public void run() {

        try {
            serverSocket = new ServerSocket( port );
            bRunning = true;
            while ( true ) {
                Socket s = serverSocket.accept();
                new SocketListener( s ).start();
            }
        } catch ( IOException e ) {
            if ( serverSocket != null && serverSocket.isClosed() )
                ; //Ignore if closed by stopServer() call
            else
                e.printStackTrace();
        } finally {
            serverSocket = null;
            bRunning = false;
        }
    }

    public static void stopServer() {
        try {
            if ( serverSocket != null )
                serverSocket.close();
        } catch ( IOException e ) {
        }
    }

    public static boolean isServerRunning() {
        return bRunning;
    }
}
