package net.craftersland.crate.events;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import net.craftersland.crate.CKV;

public class PlayerInteract implements Listener {
	
	private CKV pl;
	
	public PlayerInteract(CKV pl) {
		this.pl = pl;
	}
	
	@EventHandler
	public void onInteract(final PlayerInteractEvent event) {
		if (event.getHand() == EquipmentSlot.HAND) {
			if (event.getClickedBlock() != null) {
				if (event.getClickedBlock().getLocation().equals(pl.getConfigHandler().getVaultLoc())) {
					if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
						if (event.getPlayer().getGameMode() == GameMode.SURVIVAL) {
							event.setCancelled(true);
							pl.getVaultHandler().openVault(event.getPlayer());
						} else {
							event.setCancelled(true);
							pl.getSoundHandler().sendFailedSound(event.getPlayer());
							event.getPlayer().sendMessage(pl.getConfigHandler().getStringWithColor("ChatMessages.CrateOpenSurvival"));
						}
					} else if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
						event.setCancelled(true);
						pl.getSoundHandler().sendConfirmSound(event.getPlayer());
						event.getPlayer().sendMessage(pl.getConfigHandler().getStringWithColor("ChatMessages.RightClick"));
					}
				}
			}
		}
		
	}

}
