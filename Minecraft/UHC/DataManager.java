package me.medacrofter2176.medauhc.Files;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.medacrofter2176.medauhc.Main;

public class DataManager {
	//Variables
	private static Main plugin;
	private static FileConfiguration customFile;
	private static File file;
	
	//Constructor
	public DataManager(Main mainPlugin) {
		plugin = mainPlugin;
	}

	//Methods
	public static void setup() {
		file = new File(plugin.getDataFolder(), "customconfig.yml");
		
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				//owww
			}
		}
		customFile = YamlConfiguration.loadConfiguration(file);
	}
	
	public static FileConfiguration get() {
		return customFile;
	}
	
	public static void save() {
		try {
			customFile.save(file);
		} catch (IOException e) {
			plugin.getLogger().log(Level.SEVERE, "Could not save file " + file, e);
		}
	}
	
	public static void reload() {
		customFile = YamlConfiguration.loadConfiguration(file);
	}
}











