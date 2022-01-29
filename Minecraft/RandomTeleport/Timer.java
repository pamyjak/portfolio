package me.medacrofter2176.randomteleport;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World.Environment;
import org.bukkit.scheduler.BukkitRunnable;

public class Timer extends BukkitRunnable{
	//Variables
	public Main plugin;
	int count = 0;
	int time = 0;
	Location loc1;
	Location loc2;
	
	//Constructor
	public Timer(Main plugin) {
		this.plugin = plugin;
		time = plugin.interval;
	}
	
	//Functions
	@Override
	public void run() { //Runs once a second (20 ticks)
		//Manage time
		if (count <= 1 ) {
			count = time;
			
			//Cannot safely teleport a player who is in the Nether or End
			if(!(plugin.player1.getWorld().getEnvironment() == Environment.NETHER) && !(plugin.player1.getWorld().getEnvironment() == Environment.THE_END)) {
				plugin.player1.teleport(loc1.add(0.5, 0, 0.5));
			}
			if(!(plugin.player2.getWorld().getEnvironment() == Environment.NETHER) && !(plugin.player2.getWorld().getEnvironment() == Environment.THE_END)) {
				plugin.player2.teleport(loc2.add(0.5, 0, 0.5));
			}
		} else {
			count--;
		}
		
		//Timed messages
		if (count == 10) {
		//	Bukkit.broadcastMessage(ChatColor.GOLD + "Time in 10 Seconds");
		} else if (count == 5) {
		//	Bukkit.broadcastMessage(ChatColor.GOLD + "Time in 5 Seconds");
			plugin.setRandLoc(plugin.player1);
			loc1 = plugin.randLoc;
			plugin.setRandLoc(plugin.player2);
			loc2 = plugin.randLoc;
		} else if (count == 3) {
			Bukkit.broadcastMessage(ChatColor.GOLD + "Teleporting in 3 Seconds");
		} else if (count == 2) {
			Bukkit.broadcastMessage(ChatColor.GOLD + "Teleporting in 2 Seconds");
		} else if (count == 1) {
			Bukkit.broadcastMessage(ChatColor.GOLD + "Teleporting in 1 Second");
		}
		
	}
}
