package com.minecraftdimensions.bungeesuite;

public class PlayerInfo {
	String playername;
	String channel;
	boolean mute;
	String nickname;
	boolean cleanchatting;
	
	PlayerInfo(String name, String channel,boolean mute, String nickname, boolean cleanchat){
		this.playername= name;
		this.channel = channel;
		this.mute = mute;
		this.nickname = nickname;
		this.cleanchatting = cleanchat;
	}
}
