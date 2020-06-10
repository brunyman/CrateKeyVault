package net.craftersland.crate.events;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import net.craftersland.crate.CKV;

public class PlayerEntityInteract implements Listener {
	
	private CKV pl;
	
	public PlayerEntityInteract(CKV pl) {
		this.pl = pl;
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEntityEvent event) {
		if (event.getRightClicked().getType() == EntityType.ITEM_FRAME) {
			boolean hasKeyInHand = false;
			if (pl.getVaultHandler().isCrateKey(event.getPlayer().getInventory().getItemInMainHand()) == true) {
				hasKeyInHand = true;
			} else if (pl.getVaultHandler().isCrateKey(event.getPlayer().getInventory().getItemInOffHand()) == true) {
				hasKeyInHand = true;
			}
			if (hasKeyInHand == true) {
				event.setCancelled(true);
			}
		}
	}

}
