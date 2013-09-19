package com.minecraftdimensions.bungeesuite.objects;

import com.minecraftdimensions.bungeesuite.configlibrary.Config;
import net.md_5.bungee.api.ChatColor;

public class Messages {
   
	static String configpath = "/plugins/BungeeSuite/Messages.yml";
    static Config m = new Config( configpath );
    public static String PLAYER_CONNECT_PROXY = colorize( m.getString( "PLAYER_CONNECT_PROXY", "{player}&e has joined the server!" ) );
    public static String PLAYER_DISCONNECT_PROXY = colorize( m.getString( "PLAYER_DISCONNECT_PROXY", "{player}&e has left the server!" ) );
    public static String PLAYER_DOES_NOT_EXIST = colorize( m.getString( "PLAYER_DOES_NOT_EXIST", "&c" + "That player does not exist" ) );
    public static String PLAYER_LOAD = colorize( m.getString( "PLAYER_LOAD", "Loadded player &9{player}" ) );
    public static String PLAYER_UNLOAD = colorize( m.getString( "PLAYER_UNLOAD", "Unloaded player &c{player}" ) );
    public static String PLAYER_NOT_ONLINE = colorize( m.getString( "PLAYER_NOT_ONLINE", "&c" + "That player is not online" ) );
    public static String NO_PLAYERS_ONLINE = colorize( m.getString( "NO_PLAYERS_ONLINE", "&c" + "There must be players online to use this command" ) );
    public static String NO_PERMISSION = colorize( m.getString( "NO_PERMISSION", "&c" + "You do not have permission to use that command" ) );
    public static String PLAYER_COMMAND_ONLY = colorize( m.getString( "PLAYER_COMMAND_ONLY", "&c" + "This command can only be used by a player" ) );
    public static String SERVER_DOEST_NOT_EXIST = colorize( m.getString( "SERVER_DOEST_NOT_EXIST", "&c" + "That server does not exist" ) );
    public static String WORLD_NOT_EXIST = colorize( m.getString( "WORLD_NOT_EXIST", "&c" + "That world does not exist" ) );
    // teleport specific messages
    public static String ALL_PLAYERS_TELEPORTED = colorize( m.getString( "ALL_PLAYERS_TELEPORTED", "&2" + "All players have been teleported to {player}" ) );
    public static String TELEPORTED_TO_PLAYER = colorize( m.getString( "TELEPORTED_TO_PLAYER", "&2" + "You have been teleported to {player}" ) );
    public static String PLAYER_TELEPORT_PENDING = colorize( m.getString( "PLAYER_TELEPORT_PENDING", "&c" + "You already have a teleport pending" ) );
    public static String PLAYER_TELEPORT_PENDING_OTHER = colorize( m.getString( "PLAYER_TELEPORT_PENDING_OTHER", "&c" + "That player already has a teleport pending" ) );
    public static String PLAYER_TELEPORTED_TO_YOU = colorize( m.getString( "PLAYER_TELEPORTED_TO_YOU", "&2" + "{player} has teleported to you" ) );
    public static String PLAYER_TELEPORTED = colorize( m.getString( "PLAYER_TELEPORTED", "&2" + "{player} has teleported to {target}" ) );
    public static String PLAYER_REQUESTS_TO_TELEPORT_TO_YOU = colorize( m.getString( "PLAYER_REQUESTS_TO_TELEPORT_TO_YOU", "&2" + "{player} has requested to teleport to you. Type /tpaccept to allow" ) );
    public static String PLAYER_REQUESTS_YOU_TELEPORT_TO_THEM = colorize( m.getString( "PLAYER_REQUESTS_YOU_TELEPORT_TO_THEM", "&2" + "{player} has requested you teleport to them. Type /tpaccept to allow" ) );
    public static String TELEPORT_DENIED = colorize( m.getString( "TELEPORT_DENIED", "&c" + "You denied {player}'s teleport request" ) );
    public static String TELEPORT_REQUEST_DENIED = colorize( m.getString( "TELEPORT_REQUEST_DENIED", "&c" + "{player} denied your teleport request" ) );
    public static String NO_TELEPORTS = colorize( m.getString( "NO_TELEPORTS", "&c" + "You do not have any pending teleports" ) );
    public static String TELEPORT_REQUEST_SENT = colorize( m.getString( "TELEPORT_REQUEST_SENT", "&2" + "Your request has been sent" ) );
    public static String TPA_REQUEST_TIMED_OUT = colorize( m.getString( "TPA_REQUEST_TIMED_OUT", "&c" + "Your request to teleport to {player} has timed out" ) );
    public static String TP_REQUEST_OTHER_TIMED_OUT = colorize( m.getString( "TPA_REQUEST_OTHER_TIMED_OUT", "&c" + "{player}'s teleport request has timed out" ) );
    public static String TPAHERE_REQUEST_TIMED_OUT = colorize( m.getString( "TPAHERE_REQUEST_TIMED_OUT", "&c" + "Your request to have {player} teleport to you has timed out" ) );
    public static String NO_BACK_TP = colorize( m.getString( "NO_BACK_TP", "&c" + "You do not have anywhere to go back to" ) );
    public static String SENT_BACK = colorize( m.getString( "SENT_BACK", "&2" + "You have been sent back" ) );
    public static String DEATH_BACK = colorize( m.getString( "SENT_BACK", "&c" + "Type /back to go back to your death point" ) );
    public static String TELEPORT_TOGGLE_ON = colorize( m.getString( "TELEPORT_TOGGLE_ON", "&2" + "Telports have been toggled on" ) );
    public static String TELEPORT_TOGGLE_OFF = colorize( m.getString( "TELEPORT_TOGGLE_OFF", "&c" + "Telports have been toggled off" ) );
    public static String TELEPORT_UNABLE = colorize( m.getString( "TELEPORT_UNABLE", "&c" + "You are unable to teleport to this player" ) );
    // warp specific messages
    public static String WARP_CREATED = colorize( m.getString( "WARP_CREATED", "&2" + "Successfully created a warp" ) );
    public static String WARP_UPDATED = colorize( m.getString( "WARP_UPDATED", "&2" + "Successfully updated the warp" ) );
    public static String WARP_DELETED = colorize( m.getString( "WARP_DELETED", "&2" + "Successfully deleted the warp" ) );
    public static String WARP_ALREADY_EXISTS = colorize( m.getString( "WARP_ALREADY_EXISTS", "&c" + "Warp already exists" ) );
    public static String PLAYER_WARPED = colorize( m.getString( "PLAYER_WARPED", "&7" + "You have been warped to {warp}" ) );
    public static String PLAYER_WARPED_OTHER = colorize( m.getString( "PLAYER_WARPED_OTHER", "&7" + "{player} has been warped to {warp}" ) );
    public static String WARP_DOES_NOT_EXIST = colorize( m.getString( "WARP_DOES_NOT_EXIST", "&c" + "That warp does not exist" ) );
    public static String WARP_NO_PERMISSION = colorize( m.getString( "WARP_NO_PERMISSION", "&c" + "You do not have permission to use that warp" ) );
    public static String WARP_SERVER = colorize( m.getString( "WARP_SERVER", "&c" + "Warp not on the same server" ) );

    // portal specific messages
    public static String PORTAL_NO_PERMISSION = colorize( m.getString( "PORTAL_NO_PERMISSION", "&c" + "You do not have permission to enter this portal" ) );
    public static String PORTAL_CREATED = colorize( m.getString( "PORTAL_CREATED", "&2" + "You have successfully created a portal" ) );
    public static String PORTAL_UPDATED = colorize( m.getString( "PORTAL_UPDATED", "&2" + "You have successfully updated the portal" ) );
    public static String PORTAL_DELETED = colorize( m.getString( "PORTAL_DELETED", "&c" + "Portal deleted" ) );
    public static String PORTAL_FILLTYPE = colorize( m.getString( "PORTAL_FILLTYPE", "&c" + "That filltype does not exist" ) );
    public static String PORTAL_WRONG_TYPE = colorize( m.getString( "PORTAL_WRONG_TYPE", "&c" + "That type of portal does not exist, please use warp or server" ) );
    public static String PORTAL_ALREADY_EXISTS = colorize( m.getString( "PORTAL_ALREADY_EXISTS", "&c" + "That portal already exists" ) );
    public static String PORTAL_DESTINATION_NOT_EXIST = colorize( m.getString( "PORTAL_DESTINATION_NOT_EXIST", "&c" + "That portal destination does not exist" ) );
    public static String PORTAL_DOES_NOT_EXIST = colorize( m.getString( "PORTAL_DOES_NOT_EXIST", "&c" + "That portal does not exist" ) );
    // Spawn messages
    public static String SPAWN_DOES_NOT_EXIST = colorize( m.getString( "SPAWN_DOES_NOT_EXIST", "&c" + "The spawn point has not been set yet" ) );
    public static String SPAWN_UPDATED = colorize( m.getString( "SPAWN_UPDATED", "&2" + "Spawn point updated" ) );
    public static String SPAWN_SET = colorize( m.getString( "SPAWN_SET", "&2" + "Spawn point set" ) );
    public static String TELEPORTED_TO_SPAWN = colorize( m.getString( "TELEPORTED_TO_SPAWN", "&2" + "You have been teleported to spawn" ) );
    // ban messages
    public static String KICK_PLAYER_MESSAGE = colorize( m.getString( "KICK_PLAYER_MESSAGE", "&c" + "You have been kicked for: {message}, by {sender}" ) );
    public static String KICK_PLAYER_BROADCAST = colorize( m.getString( "KICK_PLAYER_BROADCAST", "&c" + "{player} has been kicked for {message}, by {sender}!" ) );
    public static String SAME_IP = colorize( m.getString( "SAME_IP", "&c" + "{player} has the same ip as&2 {list} &cat ip: {ip}" ) );
    public static String PLAYER_ALREADY_BANNED = colorize( m.getString( "PLAYER_ALREADY_BANNED", "&c" + "That player is already banned" ) );
    public static String PLAYER_NOT_BANNED = colorize( m.getString( "PLAYER_NOT_BANNED", "&c" + "That player is not banned" ) );
    public static String IPBAN_PLAYER = colorize( m.getString( "IPBAN_PLAYER", "&c" + "You have been IP banned for: {message}, by {sender}" ) );
    public static String IPBAN_PLAYER_BROADCAST = colorize( m.getString( "IPBAN_PLAYER_BROADCAST", "&c" + "{player} has been ip banned for: {message}, by {sender}" ) );
    public static String DEFAULT_BAN_REASON = colorize( m.getString( "DEFAULT_BAN_REASON", "Breaking server rules" ) );
    public static String DEFAULT_KICK_MESSAGE = colorize( m.getString( "DEFAULT_KICK_MESSAGE", "&cBreaking server rules" ) );
    public static String BAN_PLAYER_MESSAGE = colorize( m.getString( "BAN_PLAYER_MESSAGE", "&c" + "You have been banned for: {message}, by {sender}" ) );
    public static String BAN_PLAYER_BROADCAST = colorize( m.getString( "BAN_PLAYER_BROADCAST", "&c" + "{player} has been banned for: {message}, by {sender}" ) );
    public static String TEMP_BAN_BROADCAST = colorize( m.getString( "TEMP_BAN_BROADCAST", "&c" + "{player} has been temporarily banned for {message} until {time}, by {sender}!" ) );
    public static String TEMP_BAN_MESSAGE = colorize( m.getString( "TEMP_BAN_MESSAGE", "&c" + "You have been temporarily until {time} for {message}, by {sender}!" ) );
    public static String PLAYER_UNBANNED = colorize( m.getString( "PLAYER_UNBANNED", "&2" + "{player} has been unbanned by {sender}!" ) );
    public static String PLAYER_BANNED_STILL_TEMP_MESSAGE = colorize( m.getString( "PLAYER_BANNED_STILL_TEMP_MESSAGE", "&c" + "You are still temporarily banned for: {message}, {date}" ) );
    public static String PLAYER_BANNED_STILL_TEMP = colorize( m.getString( "PLAYER_BANNED_STILL_TEMP", "&c" + "You are still temporarily banned for another: {date}" ) );
    public static String PLAYER_BANNED_STILL_MESSAGE = colorize( m.getString( "PLAYER_BANNED_STILL_MESSAGE", "&c" + "You have been banned for life for: {message}" ) );
    public static String PLAYER_BANNED_STILL = colorize( m.getString( "PLAYER_BANNED_STILL", "&c" + "You have been banned for life!" ) );
    public static String CHECK_PLAYER_BAN = colorize( m.getString( "CHECK_PLAYER_BAN", "&c" + "{name} &4was banned for reason: &c{message} &4time left for ban &c{time} !" ) );
    // chat
    public static String NEW_PLAYER_BROADCAST = colorize( m.getString( "NEW_PLAYER_BROADCAST", "&d Welcome {player} the newest member of the server!" ) );
    public static String CHANNEL_DEFAULT_GLOBAL = m.getString( "CHANNEL_DEFAULT_GLOBAL", "&c[{channel}]&e[{server}]{prefix}&f{player}&f{suffix}&f: &f{message}" );
    public static String CHANNEL_DEFAULT_SERVER = m.getString( "CHANNEL_DEFAULT_SERVER", "&e[{server}]{prefix}&f{player}&f{suffix}&f: &7{message}" );
    public static String CHANNEL_DEFAULT_LOCAL = m.getString( "CHANNEL_DEFAULT_LOCAL", "&9[Local]{prefix}&f{player}&f{suffix}&f: &7{message}" );
    public static String CHANNEL_DEFAULT_ADMIN = m.getString( "CHANNEL_DEFAULT_ADMIN", "&9[Admin]{player}:{message}" );
    public static String CHANNEL_DEFAULT_FACTION = m.getString( "CHANNEL_DEFAULT_FACTION", "&a{factions_roleprefix}{factions_title}{player}:&r {message}" );
    public static String CHANNEL_DEFAULT_FACTION_ALLY = m.getString( "CHANNEL_DEFAULT_FACTION_ALLY", "&d{factions_roleprefix}{factions_name}{player}:&r {message}" );
    public static String PRIVATE_MESSAGE_OTHER_PLAYER = colorize( m.getString( "PRIVATE_MESSAGE_OTHER_PLAYER", "&7" + "[" + "&3" + "me" + "&7" + "->" + "&6" + "{player}" + "&7" + "] {message}" ) );
    public static String PRIVATE_MESSAGE_RECEIVE = colorize( m.getString( "PRIVATE_MESSAGE_RECEIVE", "&7" + "[" + "&b" + "{player}" + "&7" + "->" + "&6" + "me" + "&7" + "] {message}" ) );
    public static String PRIVATE_MESSAGE_SPY = colorize( m.getString( "PRIVATE_MESSAGE_SPY", "&7" + "[" + "&b" + "{sender}" + "&7" + "->" + "&6" + "{player}" + "&7" + "] {message}" ) );
    public static String MUTE_ALL_ENABLED = colorize( m.getString( "MUTE_ALL_ENABLED", "&c" + "All players have been muted by {sender}" ) );
    public static String MUTE_ALL_DISABLED = colorize( m.getString( "MUTE_ALL_DISABLED", "&2" + "All players have been unmuted by {sender}" ) );
    public static String PLAYER_MUTED = colorize( m.getString( "PLAYER_MUTED", "&2" + "{player} has been muted" ) );
    public static String PLAYER_UNMUTED = colorize( m.getString( "PLAYER_UNMUTED", "&c" + "{player} has been unmuted" ) );
    public static String MUTED = colorize( m.getString( "MUTED", "&c" + "You have been muted" ) );
    public static String UNMUTED = colorize( m.getString( "UNMUTED", "&2" + "You have been unmuted" ) );
    public static String PLAYER_NOT_MUTE = colorize( m.getString( "PLAYER_NOT_MUTE", "&c" + "That player is not muted" ) );
    public static String NO_ONE_TO_REPLY = colorize( m.getString( "NO_ONE_TO_REPLY", "&c" + "You have no one to reply to" ) );
    public static String CHANNEL_DOES_NOT_EXIST = colorize( m.getString( "CHANNEL_DOES_NOT_EXIST", "&c" + "That channel does not exist" ) );
    public static String CHANNEL_NOT_A_MEMBER = colorize( m.getString( "CHANNEL_NOT_A_MEMBER", "&c" + "You are not allowed to join this channel" ) );
    public static String CHANNEL_TOGGLE = colorize( m.getString( "CHANNEL_TOGGLE", "&2" + "You are now talking in the channel {channel}" ) );
    public static String CHANNEL_UNTOGGLABLE = colorize( m.getString( "CHANNEL_UNTOGGLABLE", "&c" + "You are unable to toggle to the channel {channel}" ) );
    public static String FACTION_TOGGLE = colorize( m.getString( "FACTION_TOGGLE", "&e" + "Faction only chat mode" ) );
    public static String FACTION_ALLY_TOGGLE = colorize( m.getString( "FACTION_ALLY_TOGGLE", "&e" + "Ally only chat mode" ) );
    public static String FACTION_OFF_TOGGLE = colorize( m.getString( "FACTION_OFF_TOGGLE", "&e" + "Public chat mode" ) );
    public static String FACTION_NONE = colorize( m.getString( "FACTION_NONE", "&c" + "You do not have a faction" ) );
    public static String GLOBAL_MUTE = colorize( m.getString( "GLOBAL_MUTE", "&c" + "All players are currently muted" ) );
    public static String NICKNAMED_PLAYER = colorize( m.getString( "NICKNAMED_PLAYER", "&2" + "You have set {player}'s name to {name}" ) );
    public static String NICKNAME_CHANGED = colorize( m.getString( "NICKNAME_CHANGED", "&2" + "Your nickname has been change to {name}" ) );
    public static String NICKNAME_TOO_LONG = colorize( m.getString( "NICKNAME_TOO_LONG", "&c" + "That nickname is too long!" ) );
    public static String NICKNAME_TAKEN = colorize( m.getString( "NICKNAME_TAKEN", "&c" + "That nickname is already taken by a player!" ) );
    public static String NICKNAME_REMOVED_PLAYER = colorize( m.getString( "NICKNAME_REMOVED_PLAYER", "&c" + "You have removed {player}'s nickname!" ) );
    public static String NICKNAME_REMOVED = colorize( m.getString( "NICKNAME_REMOVED", "&c" + "Your nickname has been removed!" ) );
    public static String PLAYER_IGNORED = colorize( m.getString( "PLAYER_IGNORED", "&2" + "{player} has been ignored" ) );
    public static String PLAYER_UNIGNORED = colorize( m.getString( "PLAYER_UNIGNORED", "&2" + "{player} has been unignored" ) );
    public static String PLAYER_IGNORING = colorize( m.getString( "PLAYER_IGNORING", "&c" + "{player} is ignoring you" ) );
    public static String PLAYER_NOT_IGNORED = colorize( m.getString( "PLAYER_NOT_IGNORED", "&c" + "{player} is not ignored" ) );
    public static String CHATSPY_ENABLED = colorize( m.getString( "CHATSPY_ENABLED", "&2" + "ChatSpy enabled" ) );
    public static String CHATSPY_DISABLED = colorize( m.getString( "CHATSPY_DISABLED", "&c" + "ChatSpy disabled" ) );
    public static String CLEANCHAT_ENABLED = colorize( m.getString( "CLEANCHAT_ENABLED", "&2" + "CleanChat enabled" ) );
    public static String CLEANCHAT_DISABLED = colorize( m.getString( "CLEANCHAT_DISABLED", "&c" + "CleanChat disabled" ) );
    public static String AFK_DISPLAY = colorize( m.getString( "AFK_DISPLAY", "&5" + "[AFK]&r" ) );
    public static String PLAYER_AFK = colorize( m.getString( "PLAYER_AFK", "&6" + "{player} &6is now afk" ) );
    public static String PLAYER_NOT_AFK = colorize( m.getString( "PLAYER_NOT_AFK", "&7" + "{player} &7is no longer afk" ) );
    // home messages
    public static String HOME_NO_PERMISSION = colorize( m.getString( "HOME_NO_PERMISSION", "&c" + "You do not have permission to set a home here" ) );
    public static String HOME_NOT_SET = colorize( m.getString( "HOME_NOT_SET", "&c" + "You do not have a home set in this world, returned to spawn" ) );
    public static String SENT_HOME = colorize( m.getString( "SENT_HOME", "&2" + "You have been sent home" ) );
    public static String HOME_NOT_EXIST = colorize( m.getString( "HOME_NOT_EXIST", "&c" + "Your home has not been set" ) );
    public static String NO_HOMES_ALLOWED_SERVER = colorize( m.getString( "NO_HOMES_ALLOWED_SERVER", "&c" + "Your are not able to set anymore homes on this server" ) );
    public static String NO_HOMES_ALLOWED_GLOBAL = colorize( m.getString( "NO_HOMES_ALLOWED_GLOBAL", "&c" + "Your are not able to set anymore homes globally" ) );
    public static String NO_HOMES = colorize( m.getString( "NO_HOMES", "&c" + "You do not have any set homes" ) );

    public static String HOME_UPDATED = colorize( m.getString( "HOME_UPDATED", "&2" + "Your home has been updated" ) );
    public static String HOME_SET = colorize( m.getString( "HOME_SET", "&2" + "Your home has been set" ) );
    public static String HOME_DOES_NOT_EXIST = colorize( m.getString( "HOME_DOES_NOT_EXIST", "&c" + "That home does not exist" ) );
    public static String HOME_DELETED = colorize( m.getString( "HOME_DELETED", "&c" + "Your home has been deleted" ) );

    public static String colorize( String input ) {
        return ChatColor.translateAlternateColorCodes( '&', input );
    }

    public class NO_MORE_HOMES_GLOABAL {}
}
