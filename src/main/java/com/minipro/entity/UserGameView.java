package com.minipro.entity;

public class UserGameView extends User {
	
	private int timeUse;
	private float winRate; 
	private int heroId;
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
	
	
	
}
