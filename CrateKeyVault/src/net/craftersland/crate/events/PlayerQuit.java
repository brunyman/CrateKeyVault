package net.craftersland.crate.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.InventoryView;

import net.craftersland.crate.CKV;

public class PlayerQuit implements Listener {
	
	private CKV pl;
	
	public PlayerQuit(CKV pl) {
		this.pl = pl;
	}
	
	@EventHandler
	public void onPlayerDisconnect(final PlayerQuitEvent event) {
		if (event.getPlayer().getOpenInventory() != null) {
			InventoryView iv = event.getPlayer().getOpenInventory();
			if (iv.getTitle().matches(pl.getConfigHandler().getStringWithColor("Settings.VaultTitle"))) {
				pl.getVaultHandler().updateCrateOnDisconnect(event.getPlayer(), iv.getTopInventory());
			}
		}
		pl.getVaultHandler().removeFromCheckingVaultList(event.getPlayer());
		Bukkit.getScheduler().runTaskAsynchronously(pl, new Runnable() {

			@Override
			public void run() {
				pl.getVaultHandler().saveDataOnDisconnect(event.getPlayer());
				pl.getVaultHandler().unloadData(event.getPlayer());
			}
			
		});
	}

}
