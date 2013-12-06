package com.minecraftdimensions.bungeesuite.objects;


public class ServerData {
    String serverName;
    String shortName;
    boolean forceChannel;
    String forcedChannel;
    int localDistance;
    boolean connectionMessages;

    boolean usingFactionChannels;
    boolean usingTowny;


    public ServerData( String name, String shortName, boolean force, String channel, int localDistance, boolean connectionMessages ) {
        this.serverName = name;
        this.shortName = shortName;
        this.forceChannel = force;
        if ( channel.equalsIgnoreCase( "server" ) ) {
            this.forcedChannel = serverName;
        } else if ( channel.equalsIgnoreCase( "global" ) ) {
            this.forcedChannel = "Global";
        } else if ( channel.equalsIgnoreCase( "local" ) ) {
            this.forcedChannel = serverName + " Local";
        } else {
            this.forcedChannel = channel;
        }
        this.localDistance = localDistance;
        this.connectionMessages = connectionMessages;
    }

    public String getServerName() {
        return serverName;
    }

    public String getServerShortName() {
        return shortName;
    }

    public String getForcedChannel() {
        return forcedChannel;
    }

    public boolean forcingChannel() {
        return forceChannel;
    }

    public boolean usingFactions() {
        return usingFactionChannels;
    }

    public void useFactions() {
        this.usingFactionChannels = true;
    }

    public int getLocalDistance() {
        return localDistance;
    }

    public boolean usingConnectionMessages() {
        return connectionMessages;
    }

    public boolean usingTowny() {
        return usingTowny;
    }

    public void useTowny() {
        this.usingTowny = true;
    }
}
