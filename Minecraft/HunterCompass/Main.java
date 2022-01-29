package me.medacrofter2176.huntercompass;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin {
	
	public Player hunter;
	public Player runner;
	public Location lastLoc;
	
	@Override
    public void onEnable() {
		getLogger().info("onEnable has been invoked!");
		new Compass(this);
    }
    
    @Override
    public void onDisable() {
    	getLogger().info("onDisable has been invoked!");
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    	if (cmd.getName().equalsIgnoreCase("setplayers")) {
    		hunter = Bukkit.getServer().getPlayer(args[0]);
    		runner = Bukkit.getServer().getPlayer(args[1]);
    		Bukkit.broadcastMessage(ChatColor.GREEN + "Set " + hunter.getName() + " as Hunter, and "
    								+ runner.getName() + " as Runner");
    		hunter.getInventory().addItem(new ItemStack(Material.COMPASS, 1));
    		return true;
    	}
    	return false;
	}
}

