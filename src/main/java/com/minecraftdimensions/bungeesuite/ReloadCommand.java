package com.minecraftdimensions.bungeesuite;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.scheduler.ScheduledTask;

public class ReloadCommand extends Command {

	BungeeSuite plugin;
	
	public ReloadCommand(BungeeSuite plugin) {
		super("bsreload");
		this.plugin = plugin;

	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(!sender.hasPermission("bungeesuite.reload")){
			plugin.utils.sendMessage(sender.getName(), "NO_PERMISSION");
			return;
		}
		plugin.getConfig();
		plugin.createDefaultMessages();
		plugin.createDatabaseConnection();
		plugin.motdMessage = plugin.utils.colorize(plugin.motdMessage);
		for(ScheduledTask data:plugin.announcementTasks){
			plugin.proxy.getScheduler().cancel(data);	
		}
		plugin.announcementTasks.clear();
		plugin.getAnnouncements();
		sender.sendMessage("BungeeSuite reloaded");

	}

}
