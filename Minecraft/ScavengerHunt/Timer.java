package me.medacrofter2176.scavengerhunt;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class Timer extends BukkitRunnable{
	//Variables
	public Main plugin;
	int count = 0;
	int time = 0;
	
	//Constructor
	public Timer(Main plugin) {
		this.plugin = plugin;
		time = plugin.interval;
	}
	
	@Override
	public void run() { //Runs once a second (20 ticks)
		//Time Manager
		if (count <= 1 ) {
			count = time;
			//Check
			if(!plugin.player1.hasFound) {
				Bukkit.broadcastMessage(ChatColor.DARK_RED + plugin.player1.player.getName() + " failed to find their item!");
			}
			if(!plugin.player2.hasFound) {
				Bukkit.broadcastMessage(ChatColor.DARK_RED + plugin.player2.player.getName() + " failed to find their item!");
			}
			//Reset
			plugin.resetHuntFor(plugin.player1);
			plugin.resetHuntFor(plugin.player2);
			//Inc level
			if(plugin.currentLevel < 4) {
				plugin.currentLevel++;
			}
		} else {
			count--;
		}
		
		//Check
		if(!plugin.player1.hasFound) {
			if(plugin.player1.player.getInventory().getItemInMainHand().getType() == plugin.player1.item.material) {
				plugin.player1.hasFound = true;
				Bukkit.broadcastMessage(ChatColor.GOLD + plugin.player1.player.getName() + " has found their item!");
			}
		}
		if(!plugin.player2.hasFound) {
			if(plugin.player2.player.getInventory().getItemInMainHand().getType() == plugin.player2.item.material) {
				plugin.player2.hasFound = true;
				Bukkit.broadcastMessage(ChatColor.GOLD + plugin.player2.player.getName() + " has found their item!");
			}
		}
		if(plugin.player1.hasFound && plugin.player2.hasFound){
			count = 0;
		}
		
		//Timed messages
		if (count == 10) {
			Bukkit.broadcastMessage(ChatColor.RED + "You have 10 Seconds to hold your item!");
		} else if (count == 5) {
			Bukkit.broadcastMessage(ChatColor.RED + "You have 5 Seconds to hold your item!");
		} else if (count == 3) {
			Bukkit.broadcastMessage(ChatColor.RED + "You have 3 Seconds to hold your item!");
		} else if (count == 2) {
			Bukkit.broadcastMessage(ChatColor.RED + "You have 2 Seconds to hold your item!");
		} else if (count == 1) {
			Bukkit.broadcastMessage(ChatColor.RED + "You have 1 Second to hold your item!");
		}
	}
}
