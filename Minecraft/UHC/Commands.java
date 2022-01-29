package me.medacrofter2176.medauhc;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import me.medacrofter2176.medauhc.Files.Messages;
import me.medacrofter2176.utils.Utils;

public class Commands implements TabExecutor{
	//Variables
	Main plugin;
	List<String> tabList = new ArrayList<>();

	
	//Constructor
		public Commands(Main mainPlugin) {
			plugin = mainPlugin;
		}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		switch(cmd.getName()) {
		case "uhc":
			if(args.length != 0) {
				switch(args[0]) {
				case "start":
					sender.sendMessage("Starting UHC");
					plugin.uhcStart();
					return true;
					
				case "stop":
					sender.sendMessage("Stopping UHC");
					plugin.uhcStop();
					return true;
					
				case "reload":
					sender.sendMessage("Reloading UHC Files");
					plugin.reloadConfigFiles();
					plugin.setActions();
					plugin.loadDefaultValues();
					return true;
					
				case "info":
					sender.sendMessage("UHC info");
					Player p = (Player) sender;
					plugin.printActions(sender);
					plugin.initScoreboard(p);
					sender.sendMessage(Utils.color(Messages.get().getString("Messages.test")));
					return true;
				}
			}
		break;
		
		case "killboard":
			sender.sendMessage("killboard");
		return true;
		
		case "health":
			sender.sendMessage("health");
		return true;
		
		case "stats":
			if(args.length != 0) {
				plugin.stats(args[0], sender);
				return true;
			}
		break;
		case "forcestart":
			sender.sendMessage("forcestart");
		return true;
		
		case "vote":
			if(args.length != 0) {
				sender.sendMessage("vote");
				//code
				return true;
			}
		break;

		case "teamselect":
			if(args.length != 0) {
				sender.sendMessage("teamselect");
				//code
				return true;
			}
		break;
		
		case "teaminv":
			sender.sendMessage("teaminv");
		return true;
		
		case "teamstats":
			sender.sendMessage("teamstats");
		return true;
		
		case "teamchat":
			sender.sendMessage("teamchat");
		return true;
		
		case "teamloc":
			sender.sendMessage("teamloc");
		return true;
		}
		
		return false;
	}
		
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		tabList.clear();
		
		switch(cmd.getName()) {
		//uhc
		case "uhc":
			if(args.length == 1) {
				tabList.add("info");
				tabList.add("start");
				tabList.add("stop");
				tabList.add("reload");				
			}
		return tabList;
		
		//kill board
		case "killboard":
			//code
		return tabList;
		
		//health
		case "health":
			if(args.length == 1) {
				for(Player p : Bukkit.getOnlinePlayers()){
					tabList.add(p.getName());
				}
			}
		return tabList;
		
		//statistics
		case "stats":
			if(args.length == 1) {
				for(Player p : Bukkit.getOnlinePlayers()){
					tabList.add(p.getName());
				}
			}
		return tabList;
		
		//force start 
		case "forcestart":
			//code
		return tabList;

		//vote
		case "vote":
			if(args.length == 1) {
				tabList.add("yes");
				tabList.add("no");
			}
		return tabList;
		
		//team select
		case "teamselect":
			if(args.length == 1) {
				tabList.add("Black");
				tabList.add("White");
				tabList.add("Red");
				tabList.add("Yellow");
				tabList.add("Blue");
				tabList.add("Purple");
				tabList.add("Green");
				tabList.add("Cyan");
			}
		return tabList;
		
		//team inventory
		case "teaminv":
		return tabList;
		
		//team statistics
		case "teamstats":
		return tabList;
		
		//team chat
		case "teamchat":
		return tabList;
		
		//team location
		case "teamloc":
		return tabList;
		}
		
		return null;
	}
}
