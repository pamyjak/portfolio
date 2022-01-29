package me.medacrofter2176.utils;

import org.bukkit.entity.Player;

public class PlayerUHC {
	// Variables
	private Player player;
	private int team;
	private boolean isActive;
	private boolean isOnline;
	private int kills;
	private int gold;
	private int diamonds;
	
	// Constructor
	public PlayerUHC(Player player, int team, boolean isActive) {
		this.player = player;
		this.team = team;
		this.isActive = isActive;
		this.isOnline = true;
		this.kills = 0;
		this.diamonds = 0;
		this.gold = 0;
	}
	
	// Player
	public Player getPlayer() {
		return this.player;
	}
	
	// Team
	public int getTeam() {
		return this.team;
	}
	public void setTeam(int t) {
		this.team = t;
	}
	
	// Active
	public boolean isActive() {
		return this.isActive;
	}
	public void setActive(boolean bool) {
		this.isActive = bool;
	}
	
	// Kills
	public void incKills() {
		this.kills++;
	}
	public int getKills() {
		return this.kills;
	}
	public void setKills(int k) {
		this.kills = k;
	}

	// Diamonds
	public int getDiamonds() {
		return this.diamonds;
	}
	public void setDiamonds(int d) {
		this.diamonds = d;
	}
	public void incDiamonds() {
		this.diamonds++;
	}
	
	// Diamonds
	public int getGold() {
		return this.gold;
	}
	public void setGold(int d) {
		this.gold = d;
	}
	public void incGold() {
		this.gold++;
	}

	public boolean isOnline() {
		return this.isOnline;
	}

	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}
}
