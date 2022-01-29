package me.medacrofter2176.medauhc.Files;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.medacrofter2176.medauhc.Main;

public class PlayerData {
	//Variables
	private static String filename = "playerdata.yml";
	private static Main plugin;
	private static FileConfiguration customFile;
	private static File file;
	
	//Constructor
	public PlayerData(Main mainPlugin) {
		plugin = mainPlugin;
	}
	
	//*****Main_File*****
	public static void setup() {
		file = new File(plugin.getDataFolder(), filename);
		
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
	
	//*****File_Interactions*****
	//AddPlayer
	public static void addPlayer(Player player) {
		String uuid = player.getUniqueId().toString();
		int numPlayers = customFile.getInt("Total-Players");
		customFile.set("Total-Players", numPlayers + 1);
		customFile.set("Players." + uuid + ".Name", player.getName());
		customFile.set("Players." + uuid + ".Games", 0);
		customFile.set("Players." + uuid + ".Wins", 0);
		customFile.set("Players." + uuid + ".Kills", 0);
		customFile.set("Players." + uuid + ".Diamonds", 0);
		customFile.set("Players." + uuid + ".Gold", 0);
		save();
	}
	//Games
	public static int getGames(Player player) {
		String uuid = player.getUniqueId().toString();
		String dataPath = "Players." + uuid + ".Games";
		if(!customFile.contains(dataPath))
			addPlayer(player);
		return customFile.getInt(dataPath);
	}
	public static void setGames(Player player, int amount) {
		String uuid = player.getUniqueId().toString();
		String dataPath = "Players." + uuid + ".Games";
		if(!customFile.contains(dataPath))
			addPlayer(player);
		customFile.set(dataPath, amount);
		save();
	}
	//Wins
	public static int getWins(Player player) {
		String uuid = player.getUniqueId().toString();
		String dataPath = "Players." + uuid + ".Wins";
		if(!customFile.contains(dataPath))
			addPlayer(player);
		return customFile.getInt(dataPath);
	}
	public static void setWins(Player player, int amount) {
		String uuid = player.getUniqueId().toString();
		String dataPath = "Players." + uuid + ".Wins";
		if(!customFile.contains(dataPath))
			addPlayer(player);
		customFile.set(dataPath, amount);
		save();
	}
	//Kills
	public static int getKills(Player player) {
		String uuid = player.getUniqueId().toString();
		String dataPath = "Players." + uuid + ".Kills";
		if(!customFile.contains(dataPath))
			addPlayer(player);
		return customFile.getInt(dataPath);
	}
	public static void setKills(Player player, int amount) {
		String uuid = player.getUniqueId().toString();
		String dataPath = "Players." + uuid + ".Kills";
		if(!customFile.contains(dataPath))
			addPlayer(player);
		customFile.set(dataPath, amount);
		save();
	}
	//Diamonds
	public static int getDiamonds(Player player) {
		String uuid = player.getUniqueId().toString();
		String dataPath = "Players." + uuid + ".Diamonds";
		if(!customFile.contains(dataPath))
			addPlayer(player);
		return customFile.getInt(dataPath);
	}
	public static void setDiamonds(Player player, int amount) {
		String uuid = player.getUniqueId().toString();
		String dataPath = "Players." + uuid + ".Diamonds";
		if(!customFile.contains(dataPath))
			addPlayer(player);
		customFile.set(dataPath, amount);
		save();
	}
	//Gold
	public static int getGold(Player player) {
		String uuid = player.getUniqueId().toString();
		String dataPath = "Players." + uuid + ".Gold";
		if(!customFile.contains(dataPath))
			addPlayer(player);
		return customFile.getInt(dataPath);
	}
	public static void setGold(Player player, int amount) {
		String uuid = player.getUniqueId().toString();
		String dataPath = "Players." + uuid + ".Gold";
		if(!customFile.contains(dataPath))
			addPlayer(player);
		customFile.set(dataPath, amount);
		save();
	}
}
