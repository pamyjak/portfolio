package me.medacrofter2176.utils;

import java.util.ArrayList;
import java.util.List;

public class ActionType {
	// Action Enum
	public enum Action {
		INVINCIBILITY, FINAL_HEAL, PVP, 
		WORLD_BORDER, MEETUP, ETERNAL_DAY, ERROR
	}
	// Variables
	private Action action;
	private String name;
	private int time;
	private List<Integer> chatWarn = new ArrayList<>();
	private int value;
	
	// Constructor
	public ActionType(Action act, String name, int time, List<Integer> cw, int val) {
		this.action = act;
		this.name = name;
		this.time = time;
		this.chatWarn = cw;
		this.value = val;
	}
	
	// Action
	public void setAction(Action act) {
		this.action = act;
	}
	public Action getAction() {
		return this.action;
	}
	// Action
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return this.name;
	}	
	// Time
	public void setTime(int tm) {
		this.time = tm;
	}
	public int getTime() {
		return this.time;
	}
	// ChatWarn
	public void setWarn(List<Integer> cw) {
		this.chatWarn = cw;
	}
	public List<Integer> getWarn() {
		return this.chatWarn;
	}
	// Value
	public void setValue(int val) {
		this.value = val;
	}
	public int getValue() {
		return this.value;
	}
}
