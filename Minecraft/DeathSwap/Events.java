package me.medacrofter2176.deathswap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class Events implements Listener {
	//Variables
	private static Main plugin;
	
	public Events(Main plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler 
	public void onKill(PlayerDeathEvent event) {
        Player player = (Player) event.getEntity();
        if (player == plugin.player1) {
        	plugin.point2++;
        	Bukkit.broadcastMessage(ChatColor.GREEN + "Score: " + plugin.point1 + " to " + plugin.point2);
        } else if (player == plugin.player2) {
        	plugin.point1++;
        	Bukkit.broadcastMessage(ChatColor.GREEN + "Score: " + plugin.point1 + " to " + plugin.point2);
        }
	}
}
