package net.craftersland.crate.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.craftersland.crate.CKV;

public class PlayerJoin implements Listener {
	
	private CKV pl;
	
	public PlayerJoin(CKV pl) {
		this.pl = pl;
	}
	
	@EventHandler
	public void onPlayerLogin(final PlayerJoinEvent event) {
		Bukkit.getScheduler().runTaskAsynchronously(pl, new Runnable() {

			@Override
			public void run() {
				pl.getVaultHandler().loadData(event.getPlayer());
			}
			
		});
	}

}
