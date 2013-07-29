package com.minecraftdimensions.bungeesuite;

public class PendingTeleportTpa implements Runnable {

	private final String targetPlayer;
	BungeeSuite plugin;

	public PendingTeleportTpa(BungeeSuite plugin, String targetPlayer) {
		this.targetPlayer = targetPlayer;
		this.plugin = plugin;
	}

	@Override
	public void run() {
		if (plugin.pendingTeleportsTPA.containsKey(targetPlayer)) {
			String msg = plugin.getMessage("TPA_REQUEST_TIMED_OUT");
			msg = msg.replace("{player}", targetPlayer);
			plugin.utils.sendMessage(plugin.pendingTeleportsTPA.get(targetPlayer),msg);
			plugin.pendingTeleportsTPA.remove(targetPlayer);
		}

	}

}
