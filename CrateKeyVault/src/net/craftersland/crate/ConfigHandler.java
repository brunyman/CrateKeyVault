package net.craftersland.crate;

import java.io.File;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;


public class ConfigHandler {
	
	private CKV plugin;
	private static Location vaultLoc = null;
	
	public ConfigHandler(CKV plugin) {
		this.plugin = plugin;
		loadConfig();
	}
	
	public Location getVaultLoc() {
		if (vaultLoc == null) {
			loadVaultLocation();
		}
		return vaultLoc;
	}
	
	public void loadVaultLocation() {
		String[] data = getString("Settings.VaultLocation").split("#");
		vaultLoc = new Location(Bukkit.getWorld(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3]));
	}
	
	public void loadConfig() {
		File pluginFolder = new File("plugins" + System.getProperty("file.separator") + plugin.getInstance().getDescription().getName());
		if (pluginFolder.exists() == false) {
    		pluginFolder.mkdir();
    	}
		File configFile = new File("plugins" + System.getProperty("file.separator") + plugin.getInstance().getDescription().getName() + System.getProperty("file.separator") + "config.yml");
		if (configFile.exists() == false) {
			CKV.log.info("No config file found! Creating new one...");
			plugin.saveDefaultConfig();
		}
    	try {
    		CKV.log.info("Loading the config file...");
    		plugin.getConfig().load(configFile);
    		CKV.log.info("Config loaded successfully!");
    	} catch (Exception e) {
    		CKV.log.severe("Could not load the config file! You need to regenerate the config! Error: " + e.getMessage());
			e.printStackTrace();
    	}
	}
	
	public String getStringWithColor(String key) {
		if (!plugin.getConfig().contains(key)) {
			plugin.getLogger().severe("Could not locate " + key + " in the config.yml inside of the " + plugin.getInstance().getDescription().getName() + " folder! (Try generating a new one by deleting the current)");
			return "errorCouldNotLocateInConfigYml:" + key;
		} else {
			return plugin.getConfig().getString(key).replaceAll("&", "§");
		}
	}
	
	public String getString(String key) {
		if (!plugin.getConfig().contains(key)) {
			plugin.getLogger().severe("Could not locate " + key + " in the config.yml inside of the " + plugin.getInstance().getDescription().getName() + " folder! (Try generating a new one by deleting the current)");
			return "errorCouldNotLocateInConfigYml:" + key;
		} else {
			return plugin.getConfig().getString(key);
		}
	}
	
	public List<String> getStringList(String key) {
		if (!plugin.getConfig().contains(key)) {
			plugin.getLogger().severe("Could not locate " + key + " in the config.yml inside of the " + plugin.getInstance().getDescription().getName() + " folder! (Try generating a new one by deleting the current)");
			return null;
		} else {
			return plugin.getConfig().getStringList(key);
		}
	}
	
	public boolean getBoolean(String key) {
		if (!plugin.getConfig().contains(key)) {
			plugin.getLogger().severe("Could not locate " + key + " in the config.yml inside of the " + plugin.getInstance().getDescription().getName() + " folder! (Try generating a new one by deleting the current)");
			return false;
		} else {
			return plugin.getConfig().getBoolean(key);
		}
	}
	
	public double getDouble(String key) {
		if (!plugin.getConfig().contains(key)) {
			plugin.getLogger().severe("Could not locate " + key + " in the config.yml inside of the " + plugin.getInstance().getDescription().getName() + " folder! (Try generating a new one by deleting the current)");
			return 0.0;
		} else {
			return plugin.getConfig().getDouble(key);
		}
	}
	
	public int getInteger(String key) {
		if (!plugin.getConfig().contains(key)) {
			plugin.getLogger().severe("Could not locate " + key + " in the config.yml inside of the " + plugin.getInstance().getDescription().getName() + " folder! (Try generating a new one by deleting the current)");
			return 0;
		} else {
			return plugin.getConfig().getInt(key);
		}
	}

}
