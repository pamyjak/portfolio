package me.medacrofter2176.scavengerhunt;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin {
	//Custom Class Types
	public class itemHunt{
		public Enum<Material> material;
		public ChatColor color;
		public int level;
	};
	public class scavengerType {
		public Player player;
		public boolean hasFound;
		public itemHunt item;
	};
	//Variables
	public scavengerType player1 = new scavengerType();
	public scavengerType player2 = new scavengerType();
	private Timer timer;
	private int defaultInterval = 300; //Default at 5 minutes (300 seconds)
	public int interval = defaultInterval;
	public int currentLevel = 1;
	List<Enum<Material>> level1 = new ArrayList<Enum<Material>>();
	List<Enum<Material>> level2 = new ArrayList<Enum<Material>>();
	List<Enum<Material>> level3 = new ArrayList<Enum<Material>>();
	List<Enum<Material>> level4 = new ArrayList<Enum<Material>>();
	List<itemHunt> itemHuntList = new ArrayList<itemHunt>();
	
	//Functions
	@Override
	public void onEnable() {
		initList();
	}
	
	@Override
	public void onDisable() {
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("scavstart")) {
			if(args.length == 2) {
				start(Bukkit.getServer().getPlayer(args[0]), Bukkit.getServer().getPlayer(args[1]), defaultInterval);
				return true;
			}
			if(args.length == 3) {
				start(Bukkit.getServer().getPlayer(args[0]), Bukkit.getServer().getPlayer(args[1]), Integer.parseInt(args[2]));
				return true;
			}
		}
		if(cmd.getName().equalsIgnoreCase("scavstop")) {
			stop();
			return true;
		}
		if(cmd.getName().equalsIgnoreCase("itemtest")) {
			Player p = Bukkit.getServer().getPlayer(args[0]);
			currentLevel = Integer.parseInt(args[1]);
			getLogger().info("Set random Item for " + p.getName());
			itemHunt item = randItem();
			p.sendMessage(ChatColor.GREEN + "Random item is now " + item.color + item.material.toString().toLowerCase());
			return true;
		}
		return false;
	}
	
	public void start(Player p1, Player p2, int intv) {
		player1.player = p1;
		player1.hasFound = true;
		player2.player = p2;
		player2.hasFound = true;
		interval = intv;
		currentLevel = 1;
		timer = new Timer(this);
	 	timer.runTaskTimer(this, 0, 20L); //20 ticks = 1 second
		Bukkit.broadcastMessage(ChatColor.GREEN + "Started ScavengerHunt for " + player1.player.getName() + " and "
								+ player2.player.getName() + " every " + interval + " seconds");
	}
	
	public void stop() {
		player1 = null;
		player2 = null;
		timer.cancel();
		Bukkit.broadcastMessage("Reset ScavengerHunt");
	}
	
	public itemHunt randItem() {
		int maxLen = 0;
		if(currentLevel >= 1) maxLen += level1.size();
		if(currentLevel >= 2) maxLen += level2.size();
		if(currentLevel >= 3) maxLen += level3.size();
		if(currentLevel >= 4) maxLen += level4.size();
		
		int rand = ThreadLocalRandom.current().nextInt(0, maxLen);
		if(rand >= 0 && rand <= (itemHuntList.size() -1)) {
			return itemHuntList.get(rand);
		} else {
			getLogger().info("ERROR: rand exceeds range in setItem()");
			return null;
		}
	}
	
	public void resetHuntFor(scavengerType scavenger) {
		scavenger.item = randItem();
		scavenger.hasFound = false;
		scavenger.player.sendMessage(ChatColor.GREEN + "You must find and hold: " + scavenger.item.color + 
									scavenger.item.material.toString().toLowerCase().replaceAll("_", " "));		
	}
	
	public void initList() {
		//Level 1
	//	getLogger().info("init level 1");
		level1.add(Material.DIRT);
		level1.add(Material.SAND);
		level1.add(Material.GRAVEL);
		level1.add(Material.FLINT);
		level1.add(Material.COBBLESTONE);
		level1.add(Material.STONE); 
		level1.add(Material.STONE_BRICKS); 
		level1.add(Material.SMOOTH_STONE);
		level1.add(Material.WHEAT_SEEDS);
		level1.add(Material.POPPY);
		level1.add(Material.DANDELION); 
		level1.add(Material.BRICK); 
		level1.add(Material.COAL);
		//Level2
	//	getLogger().info("init level 2");
		level2.add(Material.IRON_INGOT);
		level2.add(Material.GOLD_INGOT); 
		level2.add(Material.REDSTONE);
		level2.add(Material.LAPIS_LAZULI); 
		level2.add(Material.DIAMOND); 
		level2.add(Material.SNOWBALL); 
		level2.add(Material.COOKED_BEEF); // Meat
		level2.add(Material.COOKED_CHICKEN); 
		level2.add(Material.COOKED_COD); 
		level2.add(Material.COOKED_MUTTON); 
		level2.add(Material.COOKED_PORKCHOP); 
		level2.add(Material.COOKED_RABBIT);
		level2.add(Material.COOKED_SALMON);
		level2.add(Material.BLACK_WOOL); // Wool
		level2.add(Material.WHITE_WOOL);
		level2.add(Material.RED_WOOL);
		level2.add(Material.YELLOW_WOOL); 
		level2.add(Material.BLUE_WOOL);
		level2.add(Material.GREEN_WOOL); 
		level2.add(Material.CYAN_WOOL);
		level2.add(Material.MAGENTA_WOOL);	
		//Level 3
	//	getLogger().info("init level 3");
		level3.add(Material.OBSIDIAN);
		level3.add(Material.EMERALD);
		level3.add(Material.GOLDEN_APPLE);
		level3.add(Material.ENCHANTED_BOOK);
		level3.add(Material.NETHERRACK);
		level3.add(Material.NETHER_BRICK);
		level3.add(Material.GLOWSTONE_DUST);
		level3.add(Material.QUARTZ);
		level3.add(Material.BLACK_CONCRETE); // Concrete
		level3.add(Material.WHITE_CONCRETE);
		level3.add(Material.RED_CONCRETE);
		level3.add(Material.YELLOW_CONCRETE);
		level3.add(Material.BLUE_CONCRETE);
		level3.add(Material.GREEN_CONCRETE);
		level3.add(Material.CYAN_CONCRETE);
		level3.add(Material.MAGENTA_CONCRETE);	
		//Level 4
	//	getLogger().info("init level 4");
		level4.add(Material.ENDER_PEARL);
		level4.add(Material.GHAST_TEAR);
		level4.add(Material.TNT);
		level4.add(Material.BLAZE_ROD);
		level4.add(Material.NETHER_WART);
		level4.add(Material.ENCHANTED_GOLDEN_APPLE);
		
		//Initialize itemHuntList
	//	getLogger().info("init itemHuntList");
		for(int i1 = 0; i1 < level1.size(); i1++) {
			itemHunt temp = new itemHunt();
			temp.material = level1.get(i1);
			temp.color = ChatColor.DARK_GREEN;
			temp.level = 1;
			itemHuntList.add(temp);
		}
		for(int i2 = 0; i2 < level2.size(); i2++) {
			itemHunt temp = new itemHunt();
			temp.material = level2.get(i2);
			temp.color = ChatColor.AQUA;
			temp.level = 2;
			itemHuntList.add(temp);
		}
		for(int i3 = 0; i3 < level3.size(); i3++) {
			itemHunt temp = new itemHunt();
			temp.material = level3.get(i3);
			temp.color = ChatColor.GOLD;
			temp.level = 3;
			itemHuntList.add(temp);
		}
		for(int i4 = 0; i4 < level4.size(); i4++) {
			itemHunt temp = new itemHunt();
			temp.material = level4.get(i4);
			temp.color = ChatColor.DARK_PURPLE;
			temp.level = 4;
			itemHuntList.add(temp);
		}
		// Log in Console
	//	for(int j = 0; j < itemHuntList.size(); j++) {
	//		getLogger().info(j + ": lvl " + itemHuntList.get(j).level + ", " + itemHuntList.get(j).material.toString().toLowerCase().replaceAll("_", " "));
	//	}
	//	getLogger().info("init complete!");
	}
}
