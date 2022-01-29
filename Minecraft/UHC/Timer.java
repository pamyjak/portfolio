package me.medacrofter2176.medauhc;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Timer extends BukkitRunnable{
	//Variables
	public Main plugin;

	//Constructor
	public Timer(Main mainPlugin) {
		this.plugin = mainPlugin;
	}
	
	@Override
	public void run() {
		plugin.inGameTime++;
		if(plugin.nextAction != "Endgame")
			plugin.nextActionTime--;
		
		while(plugin.nextActionTime == 0 && plugin.nextAction != "Endgame") {
			plugin.takeAction();
		}
		
		if(plugin.actions.size() != 0 && plugin.actions.get(0).getWarn() != null) {
			if(plugin.actions.get(0).getWarn().contains(plugin.nextActionTime)) {
				plugin.warnAction(plugin.nextActionTime, plugin.actions.get(0).getAction());
			}
		}
		
		
		
		
		// Display Score boards
		for(Player player : Bukkit.getOnlinePlayers()){
			plugin.initScoreboard(player);
		}
	}
}
