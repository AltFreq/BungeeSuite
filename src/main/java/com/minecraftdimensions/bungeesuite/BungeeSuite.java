package com.minecraftdimensions.bungeesuite;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.minecraftdimensions.bungeesuite.config.Config;
import com.minecraftdimensions.bungeesuite.database.SQL;
import com.minecraftdimensions.bungeesuite.listener.JoinLeaveListener;
import com.minecraftdimensions.bungeesuite.listener.PluginMessageListener;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.scheduler.ScheduledTask;

public class BungeeSuite extends Plugin {
	ProxyServer proxy;
	public Utilities utils;
	/* MySQL Information */
	public String dbtype, irl, database, port, username, password;
	public int threads;
	public SQL sql;
	/* Configs */
	public Config config;
	HashMap<String, String> messages = new HashMap<String, String>();
	private Config messagesConfig;
	public long DELAY_TIME;

	/* Teleports */
	public HashMap<String, String> pendingTeleportsTPA = new HashMap<String, String>();
	public HashMap<String, String> pendingTeleportsTPAHere = new HashMap<String, String>();
	public HashMap<String, String> backLocations = new HashMap<String, String>();
	public ArrayList<String> denyTeleport = new ArrayList<String>();
	/* ConnectionMessages */
	public boolean newPlayerBroadcast;
	public String newPlayerBroadcastMessage;

	/* MOTD */
	public boolean motd;
	public String motdMessage;

	/* Spawn */
	public boolean newspawn;
	public Config spawnConfig;
	public boolean toGlobalSpawnOnDeath;

	/* bans */
	public boolean broadcastBans;
	public boolean bans = false;
	public boolean firstLogin = true;
	public boolean bansLoaded;
	public Config bansConfig;
	public boolean detectAltAccs;
	public boolean showAltAccountsIfBanned;

	/* chat */
	public String globalChatRegex;
	public boolean usingSuffix;
	public boolean usingPrefix;
	public HashMap<String, String> prefixes = new HashMap<String,String>();
	public HashMap<String, String> suffixes = new HashMap<String,String>();
	public int nickNameLimit = 16;
	public boolean prefaceNicks;
	public Config chatConfig;
	public HashMap<String, String> channelFormats = new HashMap<String, String>();
	public boolean channelsCreated;
	public boolean muteAll;
	public String defaultChannel;
	public ArrayList<String> tempMutes = new ArrayList<String>();
	public ArrayList<ProxiedPlayer> cleanChatters = new ArrayList<ProxiedPlayer>();
	public HashMap<String, String> replyMessages = new HashMap<String, String>(); // remove
																					// on
																					// dc
	public HashMap<String, ArrayList<String>> playersIgnores = new HashMap<String, ArrayList<String>>(); // remove
																											// on
																											// dc
	public ArrayList<String> chatspies = new ArrayList<String>();// remove on dc
	public HashMap<String, PlayerInfo> playerdata = new HashMap<String, PlayerInfo>(); // remove
																						// on
																						// dc
	public HashMap<String, String> forcedChannels = new HashMap<String, String>();
	public boolean chatSpySeesAll;
	public int localChatRange;
	public HashMap<String, String> shortForm = new HashMap<String, String>();
	public boolean CleanTab = false;
	public String adminColor;
	public String cleanChatRegex;
	public boolean logChat;
	// homes
	public boolean homesLoaded;
	public Config homeConfig;
	public ArrayList<String> homeGroupsList = new ArrayList<String>();
	HashMap<String, HashMap<String, Integer>> homeLimits = new HashMap<String, HashMap<String, Integer>>();
	// announcements
	public Config announcements;
	public boolean announcer;
	public ArrayList<ScheduledTask> announcementTasks = new ArrayList<ScheduledTask>();
	public boolean adminChannel;
	public boolean WorldSpawnCommandisSpawn;


	public void onEnable() {
		
		proxy = ProxyServer.getInstance();
		getConfig();
		createDatabaseConnection();
		utils = new Utilities(this);
		this.motdMessage = utils.colorize(motdMessage);
		registerListeners();
		try {
			createBaseTables();
			populateServerInfo();
			createDefaultMessages();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		getAnnouncements();
	}

	public void onDisable() {
		for(String data:this.tempMutes){
			try {
				this.utils.unMutePlayer(this.proxy.getConsole().getName(), data);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	void getConfig() {
		String configpath = "/plugins/BungeeSuite/config.yml";
		config = new Config(configpath);

		/* MySQL Information */
		dbtype = config.getString("Database.Type", "MySQL");
		irl = config.getString("Database.IRL", "localhost");
		database = config.getString("Database.Database", "minecraft");
		port = config.getString("Database.Port", "3306");
		username = config.getString("Database.Username", "username");
		password = config.getString("Database.Password", "password");
		threads = config.getInt("Database.Threads", 5);

		DELAY_TIME = Long.parseLong(config.getString("TeleportDelay", "100"));
		motd = config.getBoolean("MOTD.Enabled", true);
		motdMessage = config.getString("MOTD.message", "&6"
				+ "Welcome to the server, {player}" + "&6" + "!");
		newPlayerBroadcast = config.getBoolean("NewPlayer.BroadCast", true);
		newPlayerBroadcastMessage = config.getString(
				"NewPlayer.Broadcast Message", "&d"
						+ "Welcome {player} the newest member of Dimensions");

	}

	void getAnnouncements() {
		String configpath = "/plugins/BungeeSuite/announcements.yml";
		announcements = new Config(configpath);
		announcer = announcements.getBoolean("Announcements.enabled", true);
		List<String> check = announcements.getSubNodes("Announcements");
		// set defaults
		if (!check.contains("Global")) {
			announcements.setInt("Announcements.Global.Interval", 300);
			List<String> l = new ArrayList<String>();
			l.add("&4Welcome to the server!");
			l.add("&aDon't forget to check out our website");
			announcements.setListString("Announcements.Global.Messages", l);
		}
		for (String server : proxy.getServers().keySet()) {
			if (!check.contains(server)) {
				announcements.setInt("Announcements." + server + ".Interval",
						150);
				List<String> l = new ArrayList<String>();
				l.add("&4Welcome to the " + server + " server!");
				l.add("&aDon't forget to check out our website");
				announcements.setListString("Announcements." + server
						+ ".Messages", l);
			}
		}
		// load announcements
		if (announcer) {
			List<String> global = announcements.getListString(
					"Announcements.Global.Messages", new ArrayList<String>());
			if (!global.isEmpty()) {
				int interval = announcements.getInt(
						"Announcements.Global.Interval", 0);
				if (interval > 0) {
					GlobalAnnouncements g = new GlobalAnnouncements();
					for (String messages : global) {
						g.addAnnouncement(messages);
					}
					ScheduledTask t = getProxy().getScheduler().schedule(this,
							g, interval, interval, TimeUnit.SECONDS);
					announcementTasks.add(t);
				}
			}
			for (String server : proxy.getServers().keySet()) {
				List<String> servermes = announcements.getListString(
						"Announcements." + server + ".Messages",
						new ArrayList<String>());
				if (!servermes.isEmpty()) {
					int interval = announcements.getInt("Announcements."
							+ server + ".Interval", 0);
					if (interval > 0) {
						ServerAnnouncements s = new ServerAnnouncements(proxy.getServerInfo(server));
						for (String messages : servermes) {
							s.addAnnouncement(messages);
						}
						ScheduledTask t = getProxy().getScheduler().schedule(this,
								s, interval, interval, TimeUnit.SECONDS);
						announcementTasks.add(t);
					}
				}
			}
		}
	}

	void registerListeners() {
		proxy.registerChannel("BungeeSuite");
		proxy.getPluginManager().registerListener(this,
				new PluginMessageListener(this));
		proxy.getPluginManager().registerListener(this,
				new JoinLeaveListener(this));
		proxy.getPluginManager().registerCommand(this, new ReloadCommand(this));
	}

	void createDatabaseConnection() {
		sql = new SQL(irl, port, database, username, password,threads, this);
	}

	private void createBaseTables() throws SQLException {
		utils.createTable(
				"BungeePlayers",
				"CREATE TABLE BungeePlayers (playername VARCHAR(50), lastonline DATE NOT NULL, ipaddress VARCHAR(50), PRIMARY KEY (playername))");
		utils.createTable("BungeeServers",
				"CREATE TABLE BungeeServers (servername VARCHAR(50), PRIMARY KEY (servername))");
		utils.createTable(
				"BungeeMessages",
				"CREATE TABLE BungeeMessages (message VARCHAR(100), string VARCHAR(250), PRIMARY KEY (message))");
		sql.standardQuery("ALTER TABLE BungeePlayers MODIFY ipaddress varchar(50)");
	}

	private void populateServerInfo() throws SQLException {
		for (String server : proxy.getServers().keySet()) {
			utils.addServer(server);
		}
	}

	void createDefaultMessages() {// TODO send only
																// group when
																// needed
																// general +
																// specific
		// general messages
		String configpath = "/plugins/BungeeSuite/Messages.yml";
		messagesConfig = new Config(configpath);
		Config m = messagesConfig;
		messages.put("PLAYER_DOES_NOT_EXIST",
				utils.colorize(m.getString("PLAYER_DOES_NOT_EXIST", "&c"
						+ "That player does not exist")));
		messages.put("PLAYER_NOT_ONLINE",
				utils.colorize(m.getString("PLAYER_NOT_ONLINE", "&c"
						+ "That player is not online")));
		messages.put("NO_PLAYERS_ONLINE",
				utils.colorize(m.getString("NO_PLAYERS_ONLINE", "&c"
						+ "There must be players online to use this command")));
		messages.put("NO_PERMISSION",
				utils.colorize(m.getString("NO_PERMISSION", "&c"
						+ "You do not have permission to use that command")));
		messages.put("PLAYER_COMMAND_ONLY",
				utils.colorize(m.getString("PLAYER_COMMAND_ONLY", "&c"
						+ "This command can only be used by a player")));
		messages.put("SERVER_DOEST_NOT_EXIST",
				utils.colorize(m.getString("SERVER_DOEST_NOT_EXIST", "&c"
						+ "That server does not exist")));
		messages.put("WORLD_NOT_EXIST",
				utils.colorize(m.getString("WORLD_NOT_EXIST", "&c"
						+ "That world does not exist")));
		// teleport specific messages
		messages.put("TELEPORTED_TO_PLAYER",
				utils.colorize(m.getString("TELEPORTED_TO_PLAYER", "&2"
						+ "You have been teleported to {player}")));
		messages.put("PLAYER_TELEPORT_PENDING",
				utils.colorize(m.getString("PLAYER_TELEPORT_PENDING", "&c"
						+ "You already have a teleport pending")));
		messages.put("PLAYER_TELEPORT_PENDING_OTHER",
				utils.colorize(m.getString("PLAYER_TELEPORT_PENDING_OTHER",
						"&c" + "That player already has a teleport pending")));
		messages.put("PLAYER_TELEPORTED_TO_YOU",
				utils.colorize(m.getString("PLAYER_TELEPORTED_TO_YOU", "&2"
						+ "{player} has teleported to you")));
		messages.put(
				"PLAYER_REQUESTS_TO_TELEPORT_TO_YOU",
				utils.colorize(m
						.getString(
								"PLAYER_REQUESTS_TO_TELEPORT_TO_YOU",
								"&2"
										+ "{player} has requested to teleport to you. Type /tpaccept to allow")));
		messages.put(
				"PLAYER_REQUESTS_YOU_TELEPORT_TO_THEM",
				utils.colorize(m
						.getString(
								"PLAYER_REQUESTS_YOU_TELEPORT_TO_THEM",
								"&2"
										+ "{player} has requested you teleport to them. Type /tpaccept to allow")));
		messages.put("TELEPORT_DENIED",
				utils.colorize(m.getString("TELEPORT_DENIED", "&c"
						+ "You denied the request")));
		messages.put("TELEPORT_REQUEST_DENIED",
				utils.colorize(m.getString("TELEPORT_REQUEST_DENIED", "&c"
						+ "Your teleport request was denied")));
		messages.put("NO_TELEPORTS", utils.colorize(m.getString("NO_TELEPORTS",
				"&c" + "You do not have anyone to teleport to")));
		messages.put("TELEPORT_REQUEST_SENT",
				utils.colorize(m.getString("TELEPORT_REQUEST_SENT", "&2"
						+ "Your request has been sent")));
		messages.put("TPA_REQUEST_TIMED_OUT",
				utils.colorize(m.getString("TPA_REQUEST_TIMED_OUT", "&c"
						+ "Your request to teleport to {player} has timed out")));
		messages.put(
				"TPAHERE_REQUEST_TIMED_OUT",
				utils.colorize(m
						.getString(
								"TPAHERE_REQUEST_TIMED_OUT",
								"&c"
										+ "Your request to have {player} teleport to you has timed out")));
		messages.put("NO_BACK_TP", utils.colorize(m.getString("NO_BACK_TP",
				"&c" + "You do not have anywhere to go back to")));
		messages.put("SENT_BACK", utils.colorize(m.getString("SENT_BACK", "&2"
				+ "You have been sent back")));
		messages.put("DEATH_BACK", utils.colorize(m.getString("SENT_BACK", "&c"
				+ "Type /back to go back to your death point")));
		messages.put("TELEPORT_TOGGLE_ON", utils.colorize(m.getString("TELEPORT_TOGGLE_ON", "&2"
				+ "Telports have been toggled on")));
		messages.put("TELEPORT_TOGGLE_OFF", utils.colorize(m.getString("TELEPORT_TOGGLE_OFF", "&c"
				+ "Telports have been toggled off")));
		messages.put("TELEPORT_UNABLE", utils.colorize(m.getString("TELEPORT_UNABLE", "&c"
				+ "You are unable to teleport to this player")));
		// warp specific messages
		messages.put("WARP_CREATED", utils.colorize(m.getString("WARP_CREATED",
				"&2" + "Successfully created a warp")));
		messages.put("WARP_DELETED", utils.colorize(m.getString("WARP_DELETED",
				"&2" + "Successfully deleted the warp")));
		messages.put("WARP_ALREADY_EXISTS",
				utils.colorize(m.getString("WARP_ALREADY_EXISTS", "&c"
						+ "Warp already exists")));
		messages.put("PLAYER_WARPED",
				utils.colorize(m.getString("PLAYER_WARPED", "&7"
						+ "You have been warped")));
		messages.put("WARP_DOES_NOT_EXIST",
				utils.colorize(m.getString("WARP_DOES_NOT_EXIST", "&c"
						+ "That warp does not exist")));
		messages.put("WARP_NO_PERMISSION",
				utils.colorize(m.getString("WARP_NO_PERMISSION", "&c"
						+ "You do not have permission to use that warp")));
		// portal specific messages
		messages.put("PORTAL_NO_PERMISSION",
				utils.colorize(m.getString("PORTAL_NO_PERMISSION", "&c"
						+ "You do not have permission to enter this portal")));
		messages.put("PORTAL_CREATED",
				utils.colorize(m.getString("PORTAL_CREATED", "&2"
						+ "You have successfully created a portal")));
		messages.put("PORTAL_DELETED",
				utils.colorize(m.getString("PORTAL_DELETED", "&c"
						+ "Portal deleted")));
		messages.put("PORTAL_FILLTYPE",
				utils.colorize(m.getString("PORTAL_FILLTYPE", "&c"
						+ "That filltype does not exist")));
		messages.put(
				"PORTAL_WRONG_TYPE",
				utils.colorize(m
						.getString(
								"PORTAL_WRONG_TYPE",
								"&c"
										+ "That type of portal does not exist, please use warp or server")));
		messages.put("PORTAL_ALREADY_EXISTS",
				utils.colorize(m.getString("PORTAL_ALREADY_EXISTS", "&c"
						+ "That portal already exists")));
		messages.put("PORTAL_DESTINATION_NOT_EXIST",
				utils.colorize(m.getString("PORTAL_DESTINATION_NOT_EXIST", "&c"
						+ "That portal destination does not exist")));
		messages.put("PORTAL_DOES_NOT_EXIST",
				utils.colorize(m.getString("PORTAL_DOES_NOT_EXIST", "&c"
						+ "That portal does not exist")));
		// Spawn messages
		messages.put("SPAWN_DOES_NOT_EXIST",
				utils.colorize(m.getString("SPAWN_DOES_NOT_EXIST", "&c"
						+ "The spawn point has not been set yet")));
		messages.put("SPAWN_SET", utils.colorize(m.getString("SPAWN_SET", "&2"
				+ "Spawn point set")));
		// ban messages
		messages.put("KICK_PLAYER", utils.colorize(m.getString("KICK_PLAYER",
				"&c" + "You have been kicked!")));
		messages.put("KICK_PLAYER_MESSAGE",
				utils.colorize(m.getString("KICK_PLAYER_MESSAGE", "&c"
						+ "You have been kicked for: {message}")));
		messages.put("KICK_PLAYER_BROADCAST",
				utils.colorize(m.getString("KICK_PLAYER_BROADCAST", "&c"
						+ "{player} has been kicked!")));
		messages.put("KICK_PLAYER_BROADCAST_MESSAGE_PREFIX", utils.colorize(m
				.getString("KICK_PLAYER_BROADCAST_MESSAGE_PREFIX", "&c"
						+ "{player} has been kicked for: {message}")));
		messages.put("BAN_PLAYER", utils.colorize(m.getString("BAN_PLAYER",
				"&c" + "You have been banned!")));
		messages.put("SAME_IP", utils.colorize(m.getString("SAME_IP", "&c"
				+ "{player} has the same ip as&2 {list} &cat ip: {ip}")));
		messages.put("PLAYER_ALREADY_BANNED",
				utils.colorize(m.getString("PLAYER_ALREADY_BANNED", "&c"
						+ "That player is already banned")));
		messages.put("PLAYER_NOT_BANNED",
				utils.colorize(m.getString("PLAYER_NOT_BANNED", "&c"
						+ "That player is not banned")));
		messages.put("IPBAN_PLAYER", utils.colorize(m.getString("BAN_PLAYER",
				"&c" + "You have been banned!")));
		messages.put("IPBAN_PLAYER_MESSAGE_PREFIX",
				utils.colorize(m.getString("BAN_PLAYER_MESSAGE_PREFIX", "&c"
						+ "You have been IP banned for: {message}")));
		messages.put("IPBAN_PLAYER_BROADCAST",
				utils.colorize(m.getString("BAN_PLAYER_BROADCAST", "&c"
						+ "{ip} has been ip banned")));
		messages.put("IPBAN_PLAYER_BROADCAST_MESSAGE_PREFIX", utils.colorize(m
				.getString("BAN_PLAYER_BROADCAST_MESSAGE_PREFIX", "&c"
						+ "{player} has been ip banned for: {message}")));
		messages.put("BAN_PLAYER_MESSAGE_PREFIX",
				utils.colorize(m.getString("BAN_PLAYER_MESSAGE_PREFIX", "&c"
						+ "You have been banned for: {message}")));
		messages.put("BAN_PLAYER_BROADCAST",
				utils.colorize(m.getString("BAN_PLAYER_BROADCAST", "&c"
						+ "{player} has been banned")));
		messages.put("BAN_PLAYER_BROADCAST_MESSAGE_PREFIX", utils.colorize(m
				.getString("BAN_PLAYER_BROADCAST_MESSAGE_PREFIX", "&c"
						+ "{player} has been banned for: {message}")));
		messages.put("TEMP_BAN", utils.colorize(m.getString("TEMP_BAN", "&c"
				+ "You have been temporarily banned for {time}")));
		messages.put("TEMP_BAN_BROADCAST",
				utils.colorize(m.getString("TEMP_BAN_BROADCAST", "&c"
						+ "{player} has been temporarily banned for {time}!")));
		messages.put(
				"TEMP_BAN_MESSAGE",
				utils.colorize(m
						.getString(
								"TEMP_BAN_MESSAGE",
								"&c"
										+ "You have been temporarily banned for {time} for {message}!")));
		messages.put(
				"TEMP_BAN_BROADCAST_MESSAGE",
				utils.colorize(m
						.getString(
								"TEMP_BAN_BROADCAST_MESSAGE",
								"&c"
										+ "{player} has been temporarily banned for {time} for {message}")));
		messages.put("PLAYER_UNBANNED",
				utils.colorize(m.getString("PLAYER_UNBANNED", "&2"
						+ "{player} has been unbanned!")));
		messages.put(
				"PLAYER_BANNED_STILL_TEMP_MESSAGE",
				utils.colorize(m
						.getString(
								"PLAYER_BANNED_STILL_TEMP_MESSAGE",
								"&c"
										+ "You are still temporarily banned for: {message}, {date}")));
		messages.put(
				"PLAYER_BANNED_STILL_TEMP",
				utils.colorize(m
						.getString(
								"PLAYER_BANNED_STILL_TEMP",
								"&c"
										+ "You are still temporarily banned for another: {date}")));
		messages.put("PLAYER_BANNED_STILL_MESSAGE",
				utils.colorize(m.getString("PLAYER_BANNED_STILL_MESSAGE", "&c"
						+ "You have been banned for life for: {message}")));
		messages.put("PLAYER_BANNED_STILL",
				utils.colorize(m.getString("PLAYER_BANNED_STILL", "&c"
						+ "You have been banned for life!")));
		messages.put("CHECK_PLAYER_BAN",
				utils.colorize(m.getString("CHECK_PLAYER_BAN", "&c"
						+ "{name} &4was banned for reason: &c{message} &4time left for ban &c{time} !")));
		// chat
		messages.put(
				"CHANNEL_DEFAULT_GLOBAL",
				m
						.getString("CHANNEL_DEFAULT_GLOBAL",
								"&c[{channel}]&e[{server}]{prefix}&f{player}&f{suffix}&f: &f{message}"));
		messages.put("CHANNEL_DEFAULT_SERVER", m.getString(
				"CHANNEL_DEFAULT_SERVER",
				"&e[{server}]{prefix}&f{player}&f{suffix}&f: &7{message}"));
		messages.put("CHANNEL_DEFAULT_LOCAL", m.getString(
				"CHANNEL_DEFAULT_LOCAL",
				"&9[Local]{prefix}&f{player}&f{suffix}&f: &7{message}"));
		messages.put(
				"CHANNEL_DEFAULT_ADMIN",
				m
						.getString("CHANNEL_DEFAULT_ADMIN",
								"&9[Admin]{player}:{message}"));
		messages.put("CHANNEL_DEFAULT_FACTION", m.getString(
				"CHANNEL_DEFAULT_FACTION",
				"&a{factions_roleprefix}{factions_title}{player}:&r {message}"));
		messages.put("CHANNEL_DEFAULT_FACTION_ALLY", m.getString(
				"CHANNEL_DEFAULT_FACTION_ALLY",
				"&d{factions_roleprefix}{factions_name}{player}:&r {message}"));
		messages.put("PRIVATE_MESSAGE_OTHER_PLAYER",
				utils.colorize(m.getString("PRIVATE_MESSAGE_OTHER_PLAYER", "&7"
						+ "[" + "&3" + "me" + "&7" + "->" + "&6" + "{player}"
						+ "&7" + "] {message}")));
		messages.put("PRIVATE_MESSAGE_RECEIVE",
				utils.colorize(m.getString("PRIVATE_MESSAGE_RECEIVE", "&7"
						+ "[" + "&b" + "{player}" + "&7" + "->" + "&6" + "me"
						+ "&7" + "] {message}")));
		messages.put("PRIVATE_MESSAGE_SPY",
				utils.colorize(m.getString("PRIVATE_MESSAGE_SPY", "&7" + "["
						+ "&b" + "{sender}" + "&7" + "->" + "&6" + "{player}"
						+ "&7" + "] {message}")));
		messages.put("MUTE_ALL_ENABLED",
				utils.colorize(m.getString("MUTE_ALL_ENABLED", "&2"
						+ "Server mute enabled")));
		messages.put("MUTE_ALL_DISABLED",
				utils.colorize(m.getString("MUTE_ALL_DISABLED", "&c"
						+ "Server mute disabled")));
		messages.put("PLAYER_MUTED", utils.colorize(m.getString("PLAYER_MUTED",
				"&2" + "Player has been muted")));
		messages.put("PLAYER_UNMUTED",
				utils.colorize(m.getString("PLAYER_UNMUTED", "&c"
						+ "Player has been unmuted")));
		messages.put("MUTED", utils.colorize(m.getString("MUTED", "&c"
				+ "You have been muted")));
		messages.put("UNMUTED", utils.colorize(m.getString("UNMUTED", "&2"
				+ "You have been unmuted")));
		messages.put("PLAYER_NOT_MUTE",
				utils.colorize(m.getString("PLAYER_NOT_MUTE", "&c"
						+ "That player is not muted")));
		messages.put("NO_ONE_TO_REPLY",
				utils.colorize(m.getString("NO_ONE_TO_REPLY", "&c"
						+ "You have no one to reply to")));
		messages.put("CHANNEL_DOES_NOT_EXIST",
				utils.colorize(m.getString("CHANNEL_DOES_NOT_EXIST", "&c"
						+ "That channel does not exist")));
		messages.put("CHANNEL_NOT_A_MEMBER",
				utils.colorize(m.getString("CHANNEL_NOT_A_MEMBER", "&c"
						+ "You are not allowed to join this channel")));
		messages.put("CHANNEL_TOGGLE",
				utils.colorize(m.getString("CHANNEL_TOGGLE", "&2"
						+ "You are now talking in the {channel} channel")));
		messages.put("FACTION_TOGGLE",
				utils.colorize(m.getString("FACTION_TOGGLE", "&e"
						+ "Faction only chat mode")));
		messages.put("FACTION_ALLY_TOGGLE",
				utils.colorize(m.getString("FACTION_ALLY_TOGGLE", "&e"
						+ "Ally only chat mode")));
		messages.put("FACTION_OFF_TOGGLE",
				utils.colorize(m.getString("FACTION_OFF_TOGGLE", "&e"
						+ "Public chat mode")));
		messages.put("GLOBAL_MUTE", utils.colorize(m.getString("GLOBAL_MUTE",
				"&c" + "All players are currently muted")));
		messages.put("NICKNAMED_PLAYER",
				utils.colorize(m.getString("NICKNAMED_PLAYER", "&2"
						+ "You have set {player} ''s name to {name}")));
		messages.put("NICKNAME_CHANGED",
				utils.colorize(m.getString("NICKNAME_CHANGED", "&2"
						+ "Your nickname has been change to {name}")));
		messages.put("NICKNAME_TOO_LONG",
				utils.colorize(m.getString("NICKNAME_TOO_LONG", "&c"
						+ "That nickname is too long!")));
		messages.put("NICKNAME_TAKEN",
				utils.colorize(m.getString("NICKNAME_TAKEN", "&c"
						+ "That nickname is already taken by a player!")));
		messages.put("PLAYER_IGNORED",
				utils.colorize(m.getString("PLAYER_IGNORED", "&2"
						+ "{player} has been ignored")));
		messages.put("PLAYER_UNIGNORED",
				utils.colorize(m.getString("PLAYER_UNIGNORED", "&2"
						+ "{player} has been unignored")));
		messages.put("PLAYER_IGNORING",
				utils.colorize(m.getString("PLAYER_IGNORING", "&c"
						+ "{player} is ignoring you")));
		messages.put("PLAYER_ALREADY_IGNORED",
				utils.colorize(m.getString("PLAYER_ALREADY_IGNORED", "&c"
						+ "{player} is already ignored")));
		messages.put("PLAYER_NOT_IGNORED",
				utils.colorize(m.getString("PLAYER_NOT_IGNORED", "&c"
						+ "{player} is not ignored")));
		messages.put("CHATSPY_ENABLED",
				utils.colorize(m.getString("CHATSPY_ENABLED", "&2"
						+ "ChatSpy enabled")));
		messages.put("CHATSPY_DISABLED",
				utils.colorize(m.getString("CHATSPY_DISABLED", "&c"
						+ "ChatSpy disabled")));
		messages.put("CLEANCHAT_ENABLED",
				utils.colorize(m.getString("CLEANCHAT_ENABLED", "&2"
						+ "CleanChat enabled")));
		messages.put("CLEANCHAT_DISABLED",
				utils.colorize(m.getString("CLEANCHAT_DISABLED", "&c"
						+ "CleanChat disabled")));
		// home messages
		messages.put("HOME_NO_PERMISSION",
				utils.colorize(m.getString("HOME_NO_PERMISSION", "&c"
						+ "You do not have permission to set a home here")));
		messages.put(
				"HOME_NOT_SET",
				utils.colorize(m
						.getString(
								"HOME_NOT_SET",
								"&c"
										+ "You do not have a home set in this world, returned to spawn")));
		messages.put("SENT_HOME", utils.colorize(m.getString("SENT_HOME", "&2"
				+ "You have been sent home")));
		messages.put("HOME_NOT_EXIST",
				utils.colorize(m.getString("HOME_NOT_EXIST", "&c"
						+ "Your home has not been set")));
		messages.put("NO_HOMES_ALLOWED",
				utils.colorize(m.getString("NO_HOMES_ALLOWED", "&c"
						+ "Your are not able to set home in this server")));
		messages.put(
				"NO_MORE_HOMES",
				utils.colorize(m
						.getString(
								"NO_MORE_HOMES",
								"&c"
										+ "Your are not able to set any more homes in this server")));
		messages.put("HOME_UPDATED", utils.colorize(m.getString("HOME_UPDATED",
				"&2" + "Your home has been updated")));
		messages.put("HOME_SET", utils.colorize(m.getString("HOME_SET", "&2"
				+ "Your home has been set")));
		messages.put("HOME_DOES_NOT_EXIST",
				utils.colorize(m.getString("HOME_DOES_NOT_EXIST", "&c"
						+ "That home does not exist")));
		messages.put("HOME_DELETED", utils.colorize(m.getString("HOME_DELETED",
				"&c" + "Your home has been deleted")));
	}

	public String getMessage(String message) {
		String string = this.messages.get(message);
		return string;
	}
}
