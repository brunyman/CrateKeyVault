package net.craftersland.crate.events;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import net.craftersland.crate.CKV;

public class InventoryInteract implements Listener {
	
	private CKV pl;
	
	public InventoryInteract(CKV pl) {
		this.pl = pl;
	}
	
	@EventHandler
	public void onInvInteract(InventoryClickEvent event) {
		if (event.getClickedInventory() != null) {
			if (event.getWhoClicked().getGameMode() != GameMode.CREATIVE) {
				if (event.getView().getTitle().matches(pl.getConfigHandler().getStringWithColor("Settings.VaultTitle")) == true) {
					Player p = (Player) event.getWhoClicked();
					
					if (event.isShiftClick() == true) {
						if (pl.getVaultHandler().isCrateKey(event.getCurrentItem()) == false) {
							event.setCancelled(true);
							pl.getSoundHandler().sendFailedSound(p);
							p.sendMessage(pl.getConfigHandler().getStringWithColor("ChatMessages.CrateKeyOnly"));
						}
					} else if (event.getCursor().getType() != Material.AIR && pl.getVaultHandler().isCrateKey(event.getCursor()) == false) {
						event.setCancelled(true);
						pl.getSoundHandler().sendFailedSound(p);
						p.sendMessage(pl.getConfigHandler().getStringWithColor("ChatMessages.CrateKeyOnly"));
					}
					/*if (event.getClickedInventory().getName().matches(pl.getConfigHandler().getStringWithColor("Settings.VaultTitle")) == true) {
						if (event.getCursor().getType() != Material.AIR && pl.getVaultHandler().isCrateKey(event.getCursor()) == false) {
							event.setCancelled(true);
							pl.getSoundHandler().sendFailedSound(p);
							p.sendMessage(pl.getConfigHandler().getStringWithColor("ChatMessages.CrateKeyOnly"));
						}
					} else {
						if (event.isShiftClick() == true) {
							if (pl.getVaultHandler().isCrateKey(event.getCurrentItem()) == false) {
								event.setCancelled(true);
								pl.getSoundHandler().sendFailedSound(p);
								p.sendMessage(pl.getConfigHandler().getStringWithColor("ChatMessages.CrateKeyOnly"));
							}
						}
					}*/
				}
			} 
		}
	}

}
