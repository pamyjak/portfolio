package me.medacrofter2176.medauhc.Files;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.medacrofter2176.medauhc.Main;

public class Messages {
	//Variables
	private static String filename = "messages.yml";
	private static Main plugin;
	private static FileConfiguration customFile;
	private static File file;
	
	//Constructor
	public Messages(Main mainPlugin) {
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
		defaultStruct();
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
	public static void defaultStruct() {
		customFile.set("Messages.test", "This is a &6special&f message.");
		
		customFile.set("Scoreboard.sidebar.name", "&6Meda UHC");
		List<String> sidebarList = new ArrayList<>();
		sidebarList.add("&8----------------");
		sidebarList.add("&7Game Time&8: &6<time>");
		sidebarList.add("&7<action>&8: &6<actionTime>");
		sidebarList.add("");
		sidebarList.add("&7Players&8: &6<alive>&8/&6<total>");
		sidebarList.add("&7Border Size&8: &6<border>");
		sidebarList.add("&8---------------- ");
		customFile.set("Scoreboard.sidebar.info", sidebarList);
		
		List<String> statsList = new ArrayList<>();
		statsList.add("&8-------------&6Stats&8-------------");
		statsList.add("&6<player>&8:");
		statsList.add("&eWin&8/&eLoss&8: &c<wins>&8/&c<loss>");
		statsList.add("&eKill&8/&eDeath&8: &c<kills>&8/&c<loss>");
		statsList.add("&eGold Mined&8: &c<gold>");
		statsList.add("&eDiamonds Mined&8: &c<diamonds>");
		statsList.add("&8------------------------------");
		customFile.set("Stats", statsList);
		
		customFile.set("Actions.Invincibility.Warn", "&6Invincibility &7<!invincibilityToggle> &7in &c<warnTime> &7<sec>");
		customFile.set("Actions.Invincibility.Action", "&6Invincibility &7<invincibilityToggleC>");
		customFile.set("Actions.Final_Heal.Warn", "&6Final Heal &7will be in &c<warnTime> &7<sec>");
		customFile.set("Actions.Final_Heal.Action", "You have been fully healed");
		customFile.set("Actions.PvP.Warn", "&6PvP &7will be &7<!pvpToggle> &7in &c<warnTime> &7<sec>");
		customFile.set("Actions.PvP.Action", "&6PvP &7is now &7<pvpToggleC>");
		customFile.set("Actions.Border.Warn", "&7The &6World Border &7will be shrinking in &c<warnTime> &7<sec>");
		customFile.set("Actions.Border.Action", "&7The &6World Border &7is now (&6<border>&7/&6<border>&7)");
		customFile.set("Actions.Meetup.Warn", "&6Meetup &7will be in &c<warnTime> &7<sec>");
		customFile.set("Actions.Meetup.Action", "&7It is now &6Meetup&7! Go to the surface at (0,0)");
		customFile.set("Actions.Eternal_Day.Warn", "");
		customFile.set("Actions.Eternal_Day.Action", "");
	}
	
	//File Getters
	public static List<String> getSidebarList() {
		return customFile.getStringList("Scoreboard.sidebar.info");
	}
	
	public static List<String> getStatsList() {
		return customFile.getStringList("Stats");
	}
	
	public static String getInvincibility(Boolean asWarn) {
		if(asWarn)
			return customFile.getString("Actions.Invincibility.Warn");			
		else
			return customFile.getString("Actions.Invincibility.Action");
	}
	
	public static String getFinalHeal(Boolean asWarn) {
		if(asWarn)
			return customFile.getString("Actions.Final_Heal.Warn");			
		else
			return customFile.getString("Actions.Final_Heal.Action");
	}
	
	public static String getPvP(Boolean asWarn) {
		if(asWarn)
			return customFile.getString("Actions.PvP.Warn");			
		else
			return customFile.getString("Actions.PvP.Action");
	}
	
	public static String getBorder(Boolean asWarn) {
		if(asWarn)
			return customFile.getString("Actions.Border.Warn");			
		else
			return customFile.getString("Actions.Border.Action");
	}
	
	public static String getMeetup(Boolean asWarn) {
		if(asWarn)
			return customFile.getString("Actions.Meetup.Warn");			
		else
			return customFile.getString("Actions.Meetup.Action");
	}
	
	public static String getEternalDay(Boolean asWarn) {
		if(asWarn)
			return customFile.getString("Actions.Eternal_Day.Warn");			
		else
			return customFile.getString("Actions.Eternal_Day.Action");
	}
}
