package me.medacrofter2176.utils;

import org.bukkit.entity.Player;

import me.medacrofter2176.medauhc.Main;
import me.medacrofter2176.medauhc.Files.PlayerData;
import net.md_5.bungee.api.ChatColor;

public class Utils {
	// Variables
	public static Main plugin;
	
	// Constructor
	public Utils(Main mainPlugin) {
		plugin = mainPlugin;
	}
	
	// Chat Color
	public static String color(String str) {
		return ChatColor.translateAlternateColorCodes('&', str);
	}
	// Time Converter
	public static String toTime(int seconds) {
		int sec = seconds % 60;
        int temp = seconds / 60;
        int min = temp % 60;
        int hr = temp / 60;
        String output  = "";


        if(min < 10 && hr != 0) {
        	output = output + hr + ":" + min + ":";
        } else {
        	output = output + min + ":";
        }
        
        if(sec < 10) {
        	output = output + "0" + sec;
        } else {
        	output = output + sec;
        }
        
        return output;
	}
	
	// Integer to String
	public static String toString(int i) {
		return "" + i;
	}
	// Boolean to String
	public static String toString(boolean bool, boolean doColor) {
		String output = "";
		if(bool && doColor) output += "&2Enabled";
		if(!bool && doColor) output += "&cDisabled";
		if(bool && !doColor) output += "Enabled";
		if(!bool && !doColor) output += "Disabled";
		return output;
	}
	
	// Score board Parsing
	public static String sbParse(String str) {
		str = str.replaceAll("<time>", toTime(plugin.inGameTime));
		str = str.replaceAll("<action>", plugin.nextAction);
		str = str.replaceAll("<actionTime>", toTime(plugin.nextActionTime));
		str = str.replaceAll("<border>", toString(plugin.borderSize));
		str = str.replaceAll("<total>", toString(plugin.totalPlayers));
		str = str.replaceAll("<alive>", toString(plugin.activePlayers));

		return color(str);
	}
	
	// Statistics Parsing
	public static String statsParse(String str, Player player) {
		str = str.replaceAll("<player>", player.getName());
		str = str.replaceAll("<games>", toString(PlayerData.getGames(player)));
		str = str.replaceAll("<wins>", toString(PlayerData.getWins(player)));
		str = str.replaceAll("<loss>", toString(PlayerData.getGames(player) - PlayerData.getWins(player)));
		str = str.replaceAll("<kills>", toString(PlayerData.getKills(player)));
		str = str.replaceAll("<gold>", toString(PlayerData.getGold(player)));
		str = str.replaceAll("<diamonds>", toString(PlayerData.getDiamonds(player)));
		
		return color(str);
	}

	// Warn Parsing
	public static String warnParse(String str, int warnTime) {
		if(warnTime == 1) {
			str = str.replaceAll("<sec>", "second");			
		} else {
			str = str.replaceAll("<sec>", "seconds");
		}
		
		str = str.replaceAll("<warnTime>", toString(warnTime));
		
		return color(actionParse(str));
	}

	// Action Parsing
	public static String actionParse(String str) {
		str = str.replaceAll("<border>", toString(plugin.borderSize));
		
		str = str.replaceAll("<invincibilityToggle>", toString(plugin.invincibilityEnabled, false));
		str = str.replaceAll("<!invincibilityToggle>", toString(!plugin.invincibilityEnabled, false));
		str = str.replaceAll("<invincibilityToggleC>", toString(plugin.invincibilityEnabled, true));
		str = str.replaceAll("<!invincibilityToggleC>", toString(!plugin.invincibilityEnabled, true));
		
		str = str.replaceAll("<pvpToggle>", toString(plugin.pvpEnabled, false));
		str = str.replaceAll("<!pvpToggle>", toString(!plugin.pvpEnabled, false));
		str = str.replaceAll("<pvpToggleC>", toString(plugin.pvpEnabled, true));
		str = str.replaceAll("<!pvpToggleC>", toString(!plugin.pvpEnabled, true));
		
		str = str.replaceAll("<eternal_dayToggle>", toString(plugin.eternal_dayEnabled, false));
		str = str.replaceAll("<!eternal_dayToggle>", toString(!plugin.eternal_dayEnabled, false));
		str = str.replaceAll("<eternal_dayToggleC>", toString(plugin.eternal_dayEnabled, true));
		str = str.replaceAll("<!eternal_dayToggleC>", toString(!plugin.eternal_dayEnabled, true));
		
		return color(str);
	}
}
