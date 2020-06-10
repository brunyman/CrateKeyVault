package net.craftersland.crate.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import net.craftersland.crate.CKV;

public class PlayerQuit implements Listener {
	
	private CKV pl;
	
	public PlayerQuit(CKV pl) {
		this.pl = pl;
	}
	
	@EventHandler
	public void onPlayerDisconnect(final PlayerQuitEvent event) {
		Bukkit.getScheduler().runTaskAsynchronously(pl, new Runnable() {

			@Override
			public void run() {
				pl.getVaultHandler().unloadData(event.getPlayer());
			}
			
		});
	}

}
