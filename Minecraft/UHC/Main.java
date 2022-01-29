package me.medacrofter2176.medauhc;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import me.medacrofter2176.medauhc.Files.DataManager;
import me.medacrofter2176.medauhc.Files.Messages;
import me.medacrofter2176.medauhc.Files.PlayerData;
import me.medacrofter2176.utils.ActionType;
import me.medacrofter2176.utils.ActionType.Action;
import me.medacrofter2176.utils.PlayerUHC;
import me.medacrofter2176.utils.Utils;
import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin {
	// Variables
	public List<String> commandsList = new ArrayList<>();
	public List<ActionType> actions = new ArrayList<>();
	public HashMap<String, PlayerUHC> playerMap = new HashMap<String, PlayerUHC>();
	public Timer timer;
	
	public int inGameTime = 359; //5:59
	public String nextAction = "ERROR";
	public int nextActionTime = -404;
	public int totalPlayers = 0;
	public int activePlayers = 0;
	
	public int borderSize;
	public boolean pvpEnabled;
	public boolean meetupEnabled;
	public boolean invincibilityEnabled;
	public boolean eternal_dayEnabled;
	
	// Functions
	@Override
	public void onEnable() {
		// Construct Dependency Classes
		new DataManager(this);
		new PlayerData(this);
		new Messages(this);
		new Events(this);
		new Utils(this);

		// Setup Configs
		setupConfigs();
		loadDefaultValues();
		// Register Commands
		registerCommands();
		
		// Setup Actions list
		setActions();
	}

	public void setupConfigs() {
		// Setup Default Config
		getConfig().options().copyDefaults();
		saveDefaultConfig();

		// Setop DataManager Config
		DataManager.setup();
		DataManager.get().addDefault("Taco", "Rice");
		DataManager.get().options().copyDefaults(true);
		DataManager.save();

		// Setup PlayerData Config
		PlayerData.setup();
		PlayerData.get().addDefault("Total-Players", 0);
		PlayerData.get().options().copyDefaults(true);
		PlayerData.save();
		
		// Setup Messages Config
		Messages.setup();
		Messages.get().options().copyDefaults(true);
		Messages.save();
	}
	
	public void loadDefaultValues() {
		borderSize = getConfig().getInt("Start.BorderSize"); 
		pvpEnabled = getConfig().getBoolean("Start.PvP"); 
		meetupEnabled = getConfig().getBoolean("Start.Meetup"); 
		invincibilityEnabled = getConfig().getBoolean("Start.Invincibility");
		eternal_dayEnabled = getConfig().getBoolean("Start.EternalDay");
	}

	public void registerCommands() {
		// Commands
		commandsList.add("uhc");
		commandsList.add("killboard");
		commandsList.add("health");
		commandsList.add("stats");
		commandsList.add("forcestart");
		commandsList.add("vote");
		commandsList.add("teamselect");
		commandsList.add("teaminv");
		commandsList.add("teamstats");
		commandsList.add("teamchat");
		commandsList.add("teamloc");

		// Register commands
		for (int i = 0; i < commandsList.size(); i++) {
			getCommand(commandsList.get(i)).setExecutor(new Commands(this));
		}
	}

	public void reloadConfigFiles() {
		reloadConfig();
		PlayerData.reload();
		Messages.reload();
		DataManager.reload();
	}
	
	public void stats(String playerName, CommandSender sender) {
		Player player = Bukkit.getServer().getPlayer(playerName);
		List<String> statsList = Messages.getStatsList();
		for(int i = 0; i < statsList.size(); i++) {
			sender.sendMessage(Utils.statsParse(statsList.get(i), player));
		}
	}
	// Actions List
	public void setActions() {
		actions.clear();
		List<Integer> warnTime = new ArrayList<>();
		warnTime = getConfig().getIntegerList("Actions.Warn_Time");
		for(int i = 0; i < getConfig().getInt("Actions.NumOfActs"); i++) {
			String act = getConfig().getString("Actions.Act" + (i + 1) + ".Action");
			Action action = Action.ERROR;
			String name = "";
			int time;
			int value = 0;
			boolean warn;
			boolean isNull = false;
			switch(act) {
			default:
				isNull = true;
			break;
			case "INVINCIBILITY":
				action = Action.INVINCIBILITY;
				name = "Invincibility";
				isNull = false;
			break;
			case "FINAL_HEAL":
				action = Action.FINAL_HEAL;
				name = "Final Heal";
				isNull = false;
			break;
			case "PVP":
				action = Action.PVP;
				name = "PvP";
				isNull = false;
			break;
			case "WORLD_BORDER":
				action = Action.WORLD_BORDER;
				name = "World Border";
				isNull = false;
			break;
			case "MEETUP":
				action = Action.MEETUP;
				name = "Meetup";
				isNull = false;
			break;
			case "ETERNAL_DAY":
				action = Action.ETERNAL_DAY;
				name = "Eternal Day";
				isNull = false;
			break;
			}
			
			if(!isNull) {
				time = getConfig().getInt("Actions.Act" + (i + 1) + ".Time");
				value = getConfig().getInt("Actions.Act" + (i + 1) + ".Value");
				warn = getConfig().getBoolean("Actions.Act" + (i + 1) + ".Warn");
				if(warn) warnTime = getConfig().getIntegerList("Actions.Warn_Time");
				else warnTime = null;
				
				this.actions.add(new ActionType(action, name, time, warnTime, value));
			}
		}
		sortActions();
	}
	
	public void sortActions() {
		this.actions.sort(Comparator.comparing(ActionType::getTime));
		
	}
	
	public void printActions(CommandSender sender) {
		for(int i = 0; i < actions.size(); i++) {
			sender.sendMessage(ChatColor.GREEN + actions.get(i).getAction().toString() + ": ");
			sender.sendMessage("Time: " + actions.get(i).getTime());
			sender.sendMessage("Val.: " + actions.get(i).getValue());
			sender.sendMessage("Warn: " + actions.get(i).getWarn());
			sender.sendMessage("");
		}
	}
	
	// Score board
	public void initScoreboard(Player player) {
		//Generate score board manager
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getNewScoreboard();
		Objective objective = board.registerNewObjective("test", "dummy", "test");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		//Register and display score board
		objective.setDisplayName(Utils.color(Messages.get().getString("Scoreboard.sidebar.name")));
		List<String> displayList = Messages.getSidebarList();
		for(int i = 0; i < displayList.size(); i++) {
			objective.getScore(Utils.sbParse(displayList.get(i))).setScore(displayList.size() - i);
		}
		player.setScoreboard(board);	
	}
	
	public void uhcStart() {
		inGameTime = 0 - 1;
		sortActions();
		nextAction = actions.get(0).getName();
		nextActionTime = actions.get(0).getTime() - inGameTime;
		
		playerMap.clear();
		for(Player player : Bukkit.getOnlinePlayers()) {
			addPlayer(player);
		}
		
		timer = new Timer(this);
	 	timer.runTaskTimer(this, 0, 20L); //20 ticks = 1 second
	}
	
	public void addPlayer(Player player) {
		totalPlayers++;
		playerMap.put(player.getUniqueId().toString(), new PlayerUHC(player, 0, true));
	}

	public void removePlayer(Player player) {
		playerMap.remove(player.getUniqueId().toString());
	}
	
	public void printPlayerMap() {
		for(String uuid : playerMap.keySet()) {
			Bukkit.broadcastMessage("UUID: " + uuid
				+ ", Name: " + playerMap.get(uuid).getPlayer().getName()
				+ ", Team: " + playerMap.get(uuid).getTeam()
				+ ", Kills: " + playerMap.get(uuid).getKills()
				+ ", D: " + playerMap.get(uuid).getDiamonds()
				+ ", G: " + playerMap.get(uuid).getGold()
				+ ", Online: " + playerMap.get(uuid).isOnline());
		}
	}

	public void uhcStop() {
		timer.cancel();
	}
	
	public void takeAction() {
		Action nextAct = actions.get(0).getAction();
		switch(nextAct) {
		case INVINCIBILITY:
			invincibilityEnabled = !invincibilityEnabled;
			Bukkit.broadcastMessage(Utils.actionParse(Messages.getInvincibility(false)));
			break;
		case FINAL_HEAL:
			Bukkit.broadcastMessage(Utils.actionParse(Messages.getFinalHeal(false)));
			break;
		case PVP:
			pvpEnabled = !pvpEnabled;
			Bukkit.broadcastMessage(Utils.actionParse(Messages.getPvP(false)));
			break;
		case WORLD_BORDER:
			if(actions.get(0).getValue() != 0)
				borderSize = actions.get(0).getValue();
			Bukkit.broadcastMessage(Utils.actionParse(Messages.getBorder(false)));
			break;
		case MEETUP:
			Bukkit.broadcastMessage(Utils.actionParse(Messages.getMeetup(false)));
			break;
		case ETERNAL_DAY:
			eternal_dayEnabled = !eternal_dayEnabled;
			Bukkit.broadcastMessage(Utils.actionParse(Messages.getEternalDay(false)));
			break;
		default:
			return;	
		}
		
		// Cleanup / Stacked Actions 
		actions.remove(0);
		if(actions.size() != 0) {
			sortActions();
			nextAction = actions.get(0).getName();
			nextActionTime = actions.get(0).getTime() - inGameTime;
		} else {
			nextAction = "Endgame";
		}
	}
	
	public void warnAction(int warnTime, Action act) {
		switch(act) {
		case INVINCIBILITY:
			Bukkit.broadcastMessage(Utils.warnParse(Messages.getInvincibility(true), warnTime));
		return;
		case FINAL_HEAL:
			Bukkit.broadcastMessage(Utils.warnParse(Messages.getFinalHeal(true), warnTime));
		return;
		case PVP:
			Bukkit.broadcastMessage(Utils.warnParse(Messages.getPvP(true), warnTime));
		return;
		case WORLD_BORDER:
			Bukkit.broadcastMessage(Utils.warnParse(Messages.getBorder(true), warnTime));
		return;
		case MEETUP:
			Bukkit.broadcastMessage(Utils.warnParse(Messages.getMeetup(true), warnTime));
		return;
		case ETERNAL_DAY:
			Bukkit.broadcastMessage(Utils.warnParse(Messages.getEternalDay(true), warnTime));
		return;
		default:
		return;
		}
	}
	

	// Return Functions 
	/*
	public int getTime() {
		return inGameTime;
	}
	public int nextActionTime() {
		return nextActionTime;
	}
	public String nextAction() {
		return nextAction;
	}
	public int getBorder() {
		return borderSize;
	}
	public int totalPlayers() {
		return totalPlayers;
	}
	public int activePlayers() {
		return activePlayers;
	}
	public List<ActionType> getActions() {
		return this.actions;
	}
	*/
}

















