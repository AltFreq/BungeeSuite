package com.minecraftdimensions.bungeesuite.commands;

import com.minecraftdimensions.bungeesuite.managers.PlayerManager;
import com.minecraftdimensions.bungeesuite.objects.Messages;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class WhoIsCommand extends Command {

	public WhoIsCommand() {
		super("whois", "who", "lookup");
	}

	@Override
	public void execute(CommandSender arg0, String[] arg1) {
		if(arg0.hasPermission("bungeesuite.whois")){
			if(arg1.length>0){
				if(PlayerManager.playerExists(arg1[0])){
					PlayerManager.getPlayerInformation(arg0, arg1[0]);
				}else{
					arg0.sendMessage(Messages.PLAYER_DOES_NOT_EXIST);
				}
			}else{
				arg0.sendMessage("/whois (player/nick)");
			}
		}else{
			arg0.sendMessage(Messages.NO_PERMISSION);
		}
	}

}
