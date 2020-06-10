package net.craftersland.crate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.craftersland.crate.utils.DataSerializer;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

import de.slikey.effectlib.effect.IconEffect;
import de.slikey.effectlib.util.DynamicLocation;

public class VaultHandler {
	
	private CKV pl;
	private Map<UUID, Inventory> data = new HashMap<UUID, Inventory>();
	private Hologram holo = null;
	
	public VaultHandler(CKV pl) {
		this.pl = pl;
	}
	
	public void createHologram() {
		Bukkit.getScheduler().runTaskLater(pl, new Runnable() {

			@Override
			public void run() {
				holo = HologramsAPI.createHologram(pl, pl.getConfigHandler().getVaultLoc().add(0.5, 2.5, 0.5));
				for (String s : pl.getConfigHandler().getStringList("HologramText")) {
					holo.appendTextLine(s.replaceAll("&", "§"));
				}
				pl.getConfigHandler().loadVaultLocation();
				runEffects();
			}
			
		}, 20L * 5);
	}
	
	public void runEffects() {
		IconEffect eff = new IconEffect(pl.getEffectManager());
		eff.particle = Particle.SPELL_WITCH;
		eff.setDynamicOrigin(new DynamicLocation(pl.getConfigHandler().getVaultLoc().add(0.5, 1.0, 0.5)));
		eff.setDynamicTarget(new DynamicLocation(pl.getConfigHandler().getVaultLoc().add(0.5, 1.0, 0.5)));
		pl.getConfigHandler().loadVaultLocation();
		eff.yOffset = 0;
		eff.iterations = Integer.MAX_VALUE;
		eff.particleCount = 5;
		final IconEffect ef = eff;
		Bukkit.getScheduler().runTaskTimerAsynchronously(pl, new Runnable() {

			@Override
			public void run() {
				ef.run();
			}
			
		}, 20L, 10L);	
	}
	
	public void deleteHologram() {
		holo.delete();
	}
	
	public boolean isCrateKey(ItemStack i) {
		if (i.getType() == Material.TRIPWIRE_HOOK) {
			if (i.hasItemMeta() == true) {
				if (i.getItemMeta().hasLore() == true) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void openVault(Player p) {
		p.openInventory(data.get(p.getUniqueId()));
		pl.getSoundHandler().sendOpenedChestSound(p);
	}
	
	public void saveData(Player p, Inventory inv) {
		data.put(p.getUniqueId(), inv);
		pl.getSoundHandler().sendClosedChestSound(p);
		pl.getStorageHandler().setData(pl.getMysqlSetup().getConnection(), p.getUniqueId(), p.getName(), DataSerializer.itemsToBase64(inv.getContents()));
	}
	
	public void loadData(Player p) {
		Inventory inv = Bukkit.createInventory(p, pl.getConfigHandler().getInteger("Settings.VaultSize"), pl.getConfigHandler().getStringWithColor("Settings.VaultTitle"));
		if (pl.getStorageHandler().hasAccount(pl.getMysqlSetup().getConnection(), p.getUniqueId()) == true) {
			inv.setContents(DataSerializer.itemsFromBase64(pl.getStorageHandler().getData(pl.getMysqlSetup().getConnection(), p.getUniqueId())));
		}
		data.put(p.getUniqueId(), inv);
	}
	
	public void unloadData(Player p) {
		if (data.containsKey(p.getUniqueId()) == true) {
			data.remove(p.getUniqueId());
		}
	}

}
