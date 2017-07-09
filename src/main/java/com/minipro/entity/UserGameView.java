package com.minipro.entity;

public class UserGameView extends User {
	
	private int timeUse;
	private float winRate; 
	private int heroId;
	private String hname;
	private int isOwn;
	public int getTimeUse() {
		return timeUse;
	}
	public void setTimeUse(int timeUse) {
		this.timeUse = timeUse;
	}
	public float getWinRate() {
		return winRate;
	}
	public void setWinRate(float winRate) {
		this.winRate = winRate;
	}
	public int getHeroId() {
		return heroId;
	}
	public void setHeroId(int heroId) {
		this.heroId = heroId;
	}
	public String getHname() {
		return hname;
	}
	public void setHname(String hname) {
		this.hname = hname;
	}
	public int getIsOwn() {
		return isOwn;
	}
	public void setIsOwn(int isOwn) {
		this.isOwn = isOwn;
	}
	
	
	
}
