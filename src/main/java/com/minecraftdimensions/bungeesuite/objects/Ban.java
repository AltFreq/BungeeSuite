package com.minecraftdimensions.bungeesuite.objects;

import java.sql.Date;

public class Ban {
	private String player;
	private String bannedBy;
	private String reason;
	private String type;
	private Date bannedOn;
	private Date bannedUntil;
	
	public Ban (String player, String bannedBy, String reason, String type, Date bannedOn, Date bannedUntil){
		this.player=player;
		this.bannedBy = bannedBy;
		this.reason = reason;
		this.type = type;
		this.bannedOn = bannedOn;
		this.bannedUntil = bannedUntil;
	}

	public String getPlayer(){
		return player;
	}
	public String getBannedBy(){
		return bannedBy;
	}
	public String getReasaon(){
		return reason;
	}
	public String getType(){
		return type;
	}
	public Date getBannedOn(){
		return bannedOn;
	}
	public Date getBannedUntil(){
		return bannedUntil;
	}
	public boolean stillBanned(){
		if(bannedUntil==null){
			return true;
		}
		return bannedUntil.compareTo(bannedOn)<0;
	}
}
