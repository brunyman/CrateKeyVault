package net.craftersland.crate.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import net.craftersland.crate.CKV;

public class InventoryClose implements Listener {
	
	private CKV pl;
	
	public InventoryClose(CKV pl) {
		this.pl = pl;
	}
	
	@EventHandler
	public void onInvClose(final InventoryCloseEvent event) {
		if (event.getView().getTitle().matches(pl.getConfigHandler().getStringWithColor("Settings.VaultTitle")) == true) {
			Bukkit.getScheduler().runTaskAsynchronously(pl, new Runnable() {

				@Override
				public void run() {
					for (ItemStack is : event.getInventory().getContents().clone()) {
						if (is != null && pl.getVaultHandler().isCrateKey(is) == false) {
							event.getInventory().remove(is);
							event.getPlayer().getInventory().addItem(is);
						}
					}
					pl.getVaultHandler().saveData((Player) event.getPlayer(), event.getInventory());
				}
				
			});
		}
	}

}
