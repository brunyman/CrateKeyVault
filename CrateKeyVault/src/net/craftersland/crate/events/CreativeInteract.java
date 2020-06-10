package net.craftersland.crate.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCreativeEvent;

import net.craftersland.crate.CKV;

public class CreativeInteract implements Listener {
	
	private CKV pl;
	
	public CreativeInteract(CKV pl) {
		this.pl = pl;
	}
	
	@EventHandler
	public void onInteract(InventoryCreativeEvent event) {
		boolean isCrateKey = false;
		if (event.getCurrentItem() != null && pl.getVaultHandler().isCrateKey(event.getCurrentItem()) == true) {
			isCrateKey = true;
		} else if (pl.getVaultHandler().isCrateKey(event.getCursor()) == true) {
			isCrateKey = true;
			event.setCursor(null);
			Player p = (Player) event.getWhoClicked();
			p.updateInventory();
		}
		if (isCrateKey == true) {
			event.setCancelled(true);
		}
	}

}
