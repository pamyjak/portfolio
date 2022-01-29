package me.medacrofter2176.huntercompass;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class Compass implements Listener{
	
	private Main plugin;
	
	public Compass(Main plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void playerInteract(PlayerInteractEvent event) {
		if(event.getPlayer() == plugin.hunter) {
			ItemStack inHand = plugin.hunter.getInventory().getItem(plugin.hunter.getInventory().getHeldItemSlot());
			if(inHand != null) {
				if(inHand.getType() == Material.COMPASS) {
					if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
						plugin.hunter.setCompassTarget(plugin.lastLoc);
						plugin.hunter.sendMessage(ChatColor.GREEN + "Set compass to " + plugin.runner.getName() + "'s last location");
						return;
					}
				}
			}
		}
		return;
	}
	
	@EventHandler
	public void playerMove(PlayerMoveEvent event) {
		if(event.getPlayer() == plugin.runner) {
			if(!(plugin.runner.getWorld().getEnvironment() == Environment.NETHER) && !(plugin.runner.getWorld().getEnvironment() == Environment.THE_END)) {				
				plugin.lastLoc = plugin.runner.getLocation();
				plugin.hunter.setCompassTarget(plugin.lastLoc);
			}
		}
	}
	
	@EventHandler
	public void playerRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		if(player == plugin.runner) {
			Bukkit.broadcastMessage(ChatColor.GREEN + "Reset Hunter and Runner");
			plugin.hunter = null;
			plugin.runner = null;
		}
		if(player == plugin.hunter) {
			player.getInventory().addItem(new ItemStack(Material.COMPASS, 1));
		}
	}
}
