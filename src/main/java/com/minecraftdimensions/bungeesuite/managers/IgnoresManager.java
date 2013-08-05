package com.minecraftdimensions.bungeesuite.managers;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.md_5.bungee.api.connection.Server;

import com.minecraftdimensions.bungeesuite.objects.BSPlayer;
import com.minecraftdimensions.bungeesuite.objects.Messages;

public class IgnoresManager {

	public static void LoadPlayersIgnores(BSPlayer p) throws SQLException{
		ResultSet res = SQLManager.sqlQuery("SELECT ignoring FROM BungeeChatIgnores WHERE player ='"+p.getName()+"'");
		while(res.next()){
			p.addIgnore(res.getString("ignoring"));
		}
		res.close();
	}
	
	public static void addIgnore(BSPlayer p, String ignore) throws SQLException{
		if(PlayerManager.playerExists(ignore)){
			if(!p.isIgnoring(ignore)){
		p.addIgnore(ignore);
		SQLManager.standardQuery("INSERT INTO BungeeChatIgnores VALUES ('"+p.getName()+"', '"+ignore+"')");
		p.sendMessage(Messages.PLAYER_IGNORED);
			}else{
				removeIgnore(p, ignore);
			}
		}else{
			p.sendMessage(Messages.PLAYER_DOES_NOT_EXIST);
		}
		sendPlayersIgnores(p, p.getServer());
		
	}
	
	public static void removeIgnore(BSPlayer p, String ignore) throws SQLException{
		if(p.isIgnoring(ignore)){
			p.removeIgnore(ignore);
			SQLManager.standardQuery("DELETE FROM BungeeChatIgnores WHERE player='"+p.getName()+"' AND ignoring ='"+ignore+"'");
			p.sendMessage(Messages.PLAYER_UNIGNORED);
		}else{
			p.sendMessage(Messages.PLAYER_NOT_IGNORED);
		}
		sendPlayersIgnores(p, p.getServer());
	}
	
	public static void sendPlayersIgnores(BSPlayer p, Server s){
		String ignores = "";
		for(String str:p.getIgnores()){
			ignores+=str+"%";
		}
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try {
			out.writeUTF("SendPlayersIgnores");
			out.writeUTF(p.getName());
			out.writeUTF(ignores);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ChatManager.sendPluginMessageTaskChat(s.getInfo(), b);
	}
	
	public static boolean playerHasIgnores(BSPlayer p){
		return p.hasIgnores();
	}
	
}
