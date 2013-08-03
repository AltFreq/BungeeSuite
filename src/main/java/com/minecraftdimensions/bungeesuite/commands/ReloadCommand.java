package com.minecraftdimensions.bungeesuite.commands;

import com.minecraftdimensions.bungeesuite.managers.PlayerManager;
import com.minecraftdimensions.bungeesuite.objects.Messages;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class ReloadCommand extends Command {

	public ReloadCommand() {
		super("bsreload");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(!sender.hasPermission("bungeesuite.reload")){
			PlayerManager.getPlayer(sender).sendMessage(Messages.NO_PERMISSION);
			return;
		}

	}

}
