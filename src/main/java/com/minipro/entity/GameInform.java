package com.minipro.entity;

public class GameInform {
	
	private String uuid;
	private int hid;
	private int timeUser;
	private double winRate;
	
	private int isOwn;
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public int getHid() {
		return hid;
	}
	public void setHid(int hid) {
		this.hid = hid;
	}
	public int getTimeUser() {
		return timeUser;
	}
	public void setTimeUser(int timeUser) {
		this.timeUser = timeUser;
	}
	public double getWinRate() {
		return winRate;
	}
	public void setWinRate(double winRate) {
		this.winRate = winRate;
	}
	public int getIsOwn() {
		return isOwn;
	}
	public void setIsOwn(int isOwn) {
		this.isOwn = isOwn;
	}
	

}
