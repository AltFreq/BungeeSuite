package com.minecraftdimensions.bungeesuite.commands;

import com.minecraftdimensions.bungeesuite.configs.Announcements;
import com.minecraftdimensions.bungeesuite.configs.MainConfig;
import com.minecraftdimensions.bungeesuite.objects.Messages;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ReloadCommand extends Command {

    public ReloadCommand() {
        super( "bsreload" );
    }

    @Override
    public void execute( CommandSender sender, String[] args ) {
        if ( !( sender.hasPermission( "bungeesuite.reload" ) || sender.hasPermission( "bungeesuite.admin" ) ) ) {
            if ( sender instanceof ProxiedPlayer ) {
                ProxiedPlayer p = ( ProxiedPlayer ) sender;
                p.chat( "/bsreload" );
            }
        } else {
            Messages.reloadMessages();
            MainConfig.reloadConfig();
            Announcements.reloadAnnouncements();
            sender.sendMessage( "config.yml, announcements.yml and messages.yml reloaded!" );
        }

    }

}
