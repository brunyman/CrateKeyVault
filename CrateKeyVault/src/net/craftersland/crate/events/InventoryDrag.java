package net.craftersland.crate.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;

import net.craftersland.crate.CKV;

public class InventoryDrag implements Listener {
	
	private CKV pl;
	
	public InventoryDrag(CKV pl) {
		this.pl = pl;
	}
	
	@EventHandler
	public void onInvInteract(InventoryDragEvent event) {
		if (event.getView().getTitle().matches(pl.getConfigHandler().getStringWithColor("Settings.VaultTitle")) == true || pl.getVaultHandler().getCheckingVaultList().contains((Player) event.getWhoClicked())) {
			event.setCancelled(true);
		}
	}

}
