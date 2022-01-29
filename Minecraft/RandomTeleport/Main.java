package me.medacrofter2176.randomteleport;

import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	//Variables
	public Player player1;
	public Player player2;
	private Timer timer;
	public int interval = 300; //Default at 5 minutes (300 seconds)
	public int tpRange = 1000; //Default teleport range at +/- 2500 blocks x and z
	public Location randLoc;
	
	//Functions
	@Override
	public void onEnable() {
		getLogger().info("onEnable has been envoked!");
	}
	
	@Override
	public void onDisable() {
		getLogger().info("onDisable has been envoked!");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("rtpstart")) {
			if(args.length >= 2) {
				player1 = Bukkit.getServer().getPlayer(args[0]);
				player2 = Bukkit.getServer().getPlayer(args[1]);
				if(args.length >= 3) {
					interval = Integer.parseInt(args[2]);
					if(args.length == 4) {
						tpRange = Integer.parseInt(args[3]);
					}
				}
				timer = new Timer(this);
			 	timer.runTaskTimer(this, 0, 20L); //20 ticks = 1 second
				Bukkit.broadcastMessage("Started RandomTeleport for " + player1.getName() + " and " + player2.getName() + 
						" every " + interval + " seconds with a range of " + tpRange + " blocks");
				return true;
			}
		}
		
		if(cmd.getName().equalsIgnoreCase("rtpstop")) {
			player1 = null;
			player2 = null;
			timer.cancel();
			Bukkit.broadcastMessage("Reset RandomTeleport");
			return true;
		}
		return false;
	}
	
	public void setRandLoc(Player player) {
		int min = -tpRange;
		int max = tpRange;
		int randX = ThreadLocalRandom.current().nextInt(min, max + 1);
		int randZ = ThreadLocalRandom.current().nextInt(min, max + 1);
		Block randLocBlock = player.getWorld().getHighestBlockAt(randX, randZ);
		randLoc = randLocBlock.getLocation().add(0, 1, 0); //Set the randLoc to the location above the highest block
		
		if(isSafeLoc(randLoc) == true) { //Location is safe
			return;
		} else { //Location is not safe
			setRandLoc(player); //Attempt new random location
			return;
		}
	}
	
	public boolean isSafeLoc(Location loc) {
		Block feet = loc.getBlock();
        Block head = feet.getRelative(BlockFace.UP);
        Block ground = feet.getRelative(BlockFace.DOWN);
        if ( ((feet.getType() == Material.AIR) || feet.isPassable()) && 
        	 ((head.getType() == Material.AIR) || head.isPassable()) &&
        	 ground.getType().isSolid() ) {
        	return true; //Location is Safe
        } else {
        	return false; //Location is not safe
        }
    }
	//End of Main
}
