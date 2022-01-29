package me.medacrofter2176.deathswap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	//Variables
	public Player player1;
	public Player player2;
	public int point1 = 0;
	public int point2 = 0;
	public Location loc1;
	public Location loc2;
	public int delayTime = 30; //Time in Seconds. 300 seconds = 5 minute
	private Messager messager;
	
	//Functions
	@Override
    public void onEnable() {
		getLogger().info("onEnable has been invoked!");
		new Events(this);
    }
    
    @Override
    public void onDisable() {
    	getLogger().info("onDisable has been invoked!");
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    	if (cmd.getName().equalsIgnoreCase("deathswap")) {
    		player1 = Bukkit.getServer().getPlayer(args[0]);
    		player2 = Bukkit.getServer().getPlayer(args[1]);
    		if (args.length > 2) {
    			delayTime = Integer.parseInt(args[2]);
    		}
    		Bukkit.broadcastMessage("Started Death Swap for " + player1.getName() + " and " + player2.getName() + 
    								" every " + delayTime + " seconds");
    		
    		messager = new Messager(this);
    		messager.runTaskTimer(this, 0, 20L); //20 ticks = 1 second
    		return true;
    	}
    	if (cmd.getName().equalsIgnoreCase("swapstop")) {
    		Bukkit.broadcastMessage("Reset swap");
    		player1 = null;
    		player2 = null;
    		messager.cancel();
    		return true;
    	}
    	if (cmd.getName().equalsIgnoreCase("setscore")) {
    		Player player = Bukkit.getServer().getPlayer(args[0]);
    		
    		if (player == player1) {
    			point1 = Integer.parseInt(args[1]);
    			Bukkit.broadcastMessage("Set score of " + player1.getName() + " to " + point1);
    			return true;
    		} else if (player == player2) {
    			point2 = Integer.parseInt(args[1]);
    			Bukkit.broadcastMessage("Set score of " + player2.getName() + " to " + point2);
    			return true;
    		}
    		return false;
    	}
    	if (cmd.getName().equalsIgnoreCase("getscore")) {
    		Bukkit.broadcastMessage(ChatColor.GREEN + "The current score is " + point1 + " to " + point2);
    		return true;
    	}
    	return false;
    }
	
    public void swap() {
    	loc1 = player1.getLocation();
    	loc2 = player2.getLocation();
    	player1.teleport(loc2);
    	player2.teleport(loc1);
    }
	
}
