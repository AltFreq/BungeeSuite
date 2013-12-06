package com.minecraftdimensions.bungeesuite.objects;


public class ServerData {
    String serverName;
    String shortName;
    boolean forceChannel;
    String forcedChannel;
    int localDistance;
    boolean connectionMessages;
    boolean usingFactionChannels;


    public ServerData( String name, String shortName, boolean force, String channel, int localDistance, boolean connectionMessages ) {
        this.serverName = name;
        this.shortName = shortName;
        this.forceChannel = force;
        this.forcedChannel = channel;
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
}
