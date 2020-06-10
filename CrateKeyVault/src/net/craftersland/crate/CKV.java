package net.craftersland.crate;

import java.util.logging.Logger;

import net.craftersland.crate.events.CreativeInteract;
import net.craftersland.crate.events.InventoryClose;
import net.craftersland.crate.events.InventoryDrag;
import net.craftersland.crate.events.InventoryInteract;
import net.craftersland.crate.events.PlayerEntityInteract;
import net.craftersland.crate.events.PlayerInteract;
import net.craftersland.crate.events.PlayerJoin;
import net.craftersland.crate.events.PlayerQuit;
import net.craftersland.crate.storage.MysqlSetup;
import net.craftersland.crate.storage.StorageHandler;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.slikey.effectlib.EffectManager;

public class CKV extends JavaPlugin {
	
	public static Logger log;
	public static String pluginName = "CrateKeyVault";
	public static boolean oldSounds = false;
	
	private static ConfigHandler cH;
	private static SoundHandler sH;
	private static MysqlSetup ms;
	private static StorageHandler stH;
	private static VaultHandler vH;
	private static EffectManager em;
	
	@Override
    public void onEnable() {
		log = getLogger();
		checkServerVersion();
		cH = new ConfigHandler(this);
		sH = new SoundHandler(this);
		ms = new MysqlSetup(this);
		stH = new StorageHandler(this);
		vH = new VaultHandler(this);
		em = new EffectManager(this);
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new PlayerJoin(this), this);
		pm.registerEvents(new PlayerQuit(this), this);
		pm.registerEvents(new InventoryClose(this), this);
		pm.registerEvents(new PlayerInteract(this), this);
		pm.registerEvents(new InventoryInteract(this), this);
		pm.registerEvents(new InventoryDrag(this), this);
		pm.registerEvents(new PlayerEntityInteract(this), this);
		pm.registerEvents(new CreativeInteract(this), this);
		CommandHandler cH = new CommandHandler(this);
    	getCommand("ckv").setExecutor(cH);
    	vH.createHologram();
		log.info(pluginName + " loaded successfully!");
	}
	
	@Override
    public void onDisable() {
		vH.deleteHologram();
		Bukkit.getScheduler().cancelTasks(this);
		HandlerList.unregisterAll(this);
		ms.closeConnection();
		log.info(pluginName + " is disabled!");
	}
	
	private void checkServerVersion() {
		String[] serverVersion = Bukkit.getBukkitVersion().split("-");
	    String version = serverVersion[0];
	    if (version.matches("1.12.2")) {
	    	oldSounds = true;
	    }
	}
	
	public ConfigHandler getConfigHandler() {
		return cH;
	}
	public SoundHandler getSoundHandler() {
		return sH;
	}
	public MysqlSetup getMysqlSetup() {
		return ms;
	}
	public StorageHandler getStorageHandler() {
		return stH;
	}
	public VaultHandler getVaultHandler() {
		return vH;
	}
	public EffectManager getEffectManager() {
		return em;
	}

}
