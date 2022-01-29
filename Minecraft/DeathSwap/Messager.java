package me.medacrofter2176.deathswap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class Messager extends BukkitRunnable{
	//Variables
	Main plugin;
	int count = 0;
	int delayTime;
	
	//Functions
	public Messager(Main plugin) {
		this.plugin = plugin;
		delayTime = plugin.delayTime;
		count = delayTime + 1;
	}
	
	@Override
	public void run() { //Runs once a second (20 ticks)
		//Manage time
		if (count <= 1 ) {
			count = delayTime;
			plugin.swap();
		} else {
			count--;
		}
		
		//Timed messages
		if(count == delayTime) {
		//	Bukkit.broadcastMessage("Swap!");
		} else if (count == 10) {
			Bukkit.broadcastMessage(ChatColor.GOLD + "Swapping in 10 Seconds");
		} else if (count == 5) {
			Bukkit.broadcastMessage(ChatColor.GOLD + "Swapping in 5 Seconds");
		} else if (count == 3) {
			Bukkit.broadcastMessage(ChatColor.GOLD + "Swapping in 3 Seconds");
		} else if (count == 2) {
			Bukkit.broadcastMessage(ChatColor.GOLD + "Swapping in 2 Seconds");
		} else if (count == 1) {
			Bukkit.broadcastMessage(ChatColor.GOLD + "Swapping in 1 Second");
		}
		
		
	}
}
