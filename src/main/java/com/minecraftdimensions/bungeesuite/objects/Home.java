package com.minecraftdimensions.bungeesuite.objects;


public class Home {
    public String owner;
    public String name;
    public Location loc;

    public Home( String owner, String name, Location loc ) {
        this.owner = owner;
        this.name = name;
        this.loc = loc;
    }

    public void setLoc( Location loc ) {
        this.loc = loc;
    }
}
