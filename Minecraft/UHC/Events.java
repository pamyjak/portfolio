package me.medacrofter2176.medauhc;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.medacrofter2176.medauhc.Files.PlayerData;
import me.medacrofter2176.utils.PlayerUHC;

public class Events implements Listener{
	//Variables
	private Main plugin;
	
	//Constructor
	public Events(Main mainPlugin) {
		this.plugin = mainPlugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	//Events
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		String uuid = player.getUniqueId().toString();
		
		//Remove from game
		if(plugin.playerMap.containsKey(uuid)) {
			Location deadLoc = player.getLocation();
			
			//TODO Fix auto-respawn. Should change gamemode of player killed and keep same location. inc killer's kills
			player.spigot().respawn();
			player.setGameMode(GameMode.SPECTATOR);
	
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> player.teleport(deadLoc), 1L);
		/*	
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				public void run() {
					player.teleport(deadLoc);
				}
			}, 1);
		*/
			plugin.playerMap.get(uuid).setActive(false);
			plugin.activePlayers--;
			PlayerData.setGold(player, plugin.playerMap.get(uuid).getGold() + PlayerData.getGold(player));
			PlayerData.setDiamonds(player, plugin.playerMap.get(uuid).getDiamonds() + PlayerData.getDiamonds(player));
			PlayerData.setKills(player, plugin.playerMap.get(uuid).getKills());
			PlayerData.setGames(player, PlayerData.getGames(player) + 1);
		}
		
		//Determine if PvP
		if(player.getKiller() instanceof Player) {
			Player killer = (Player) event.getEntity().getKiller();
			Bukkit.broadcastMessage(player.getName() + " was killed by " + killer.getName());
			String uuidK = killer.getUniqueId().toString();
			if(plugin.playerMap.containsKey(uuidK)) {
				plugin.playerMap.get(uuidK).incKills();
			}
		}
				
		//Update Score boards
	//	for(Player p : Bukkit.getOnlinePlayers()){
	//		plugin.initScoreboard(p);
	//	}
		
		plugin.printPlayerMap();
	}
	
	@EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		String uuid = player.getUniqueId().toString();
		if(event.getBlock().getType().equals(Material.DIAMOND_ORE)) {
			//Mined Diamonds
			plugin.playerMap.get(uuid).incDiamonds();
		} else if(event.getBlock().getType().equals(Material.GOLD_ORE)) {
			//Mined Gold
			plugin.playerMap.get(uuid).incGold();
		}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		String uuid = player.getUniqueId().toString();
		
		if(plugin.playerMap.containsKey(uuid)) {
			plugin.playerMap.get(uuid).setOnline(true);
			Bukkit.broadcastMessage("Online");
		} else {
			Bukkit.broadcastMessage("Add");
			plugin.playerMap.put(uuid, new PlayerUHC(player, 0, true));
		}
		plugin.printPlayerMap();		
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		String uuid = player.getUniqueId().toString();
		
		if(plugin.playerMap.containsKey(uuid)) {
			Bukkit.broadcastMessage("Offline");
			plugin.playerMap.get(uuid).setOnline(false);
		}
		plugin.printPlayerMap();
	}
}
