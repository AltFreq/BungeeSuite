package com.minecraftdimensions.bungeesuite.objects;


import com.minecraftdimensions.bungeesuite.BungeeSuite;
import net.md_5.bungee.api.config.ServerInfo;

public class Portal {
    String name;
    String server;
    String fillType;
    String type;
    String dest;
    Location max;
    Location min;


    public Portal( String name, String server, String fillType, String type, String dest, Location max, Location min ) {
        this.name = name;
        this.server = server;
        this.fillType = fillType;
        this.type = type;
        this.dest = dest;
        this.max = max;
        this.min = min;
    }

    public ServerInfo getServer() {
        return BungeeSuite.proxy.getServerInfo( server );
    }

    public String getName() {
        return name;
    }

    public String getFillType() {
        return fillType;
    }

    public String getType() {
        return type;
    }

    public String getDest() {
        return dest;
    }

    public Location getMax() {
        return max;
    }

    public Location getMin() {
        return min;
    }
}
