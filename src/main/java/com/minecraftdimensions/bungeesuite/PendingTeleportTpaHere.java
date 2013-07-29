package com.minecraftdimensions.bungeesuite;

public class PendingTeleportTpaHere implements Runnable {

	private final String player;
	BungeeSuite plugin;

	public PendingTeleportTpaHere(BungeeSuite plugin, String player) {
		this.player = player;
		this.plugin = plugin;
	}

	@Override
	public void run() {
		if (plugin.pendingTeleportsTPAHere.containsKey(player)) {
			String msg = plugin.getMessage("TPAHERE_REQUEST_TIMED_OUT");
			msg = msg.replace("{player}", player);
			plugin.utils.sendMessage(plugin.pendingTeleportsTPAHere.get(player),msg);
			plugin.pendingTeleportsTPAHere.remove(player);
		}

	}

}
