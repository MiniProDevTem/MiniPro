package com.minipro.entity;

import java.io.Serializable;
import java.util.List;


public class User implements Serializable {

    private static final long serialVersionUID = -6011241820070393952L;
    
    private String uuid;//唯一标识
    private String openId;//来自QQ
    private String name;
    private String place;
    private String birthday;
    private String sex;
    private String location;
    private String headUrl;
    private String voiceUrl;
    private int age;
    private String levl;
    private String star;
    private String matching;
    private String qq;
    
    private double trankRate;
    private double warriorRate;
    private double assassinRate;
    private double masterRate;
    private double shooterRate;
    private double auxiliaryRate;
    
    private String images;
    
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	public String getHeadUrl() {
		return headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}

	public String getVoiceUrl() {
		return voiceUrl;
	}

	public void setVoiceUrl(String voiceUrl) {
		this.voiceUrl = voiceUrl;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	public String getLevl() {
		return levl;
	}
	public void setLevl(String levl) {
		this.levl = levl;
	}

	public String getStar() {
		return star;
	}

	public void setStar(String star) {
		this.star = star;
	}
	
	public String getMatching() {
		return matching;
	}
	public void setMatching(String matching) {
		this.matching = matching;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getImages() {
		return images;
	}
	public void setImages(String images) {
		this.images = images;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}
	public double getTrankRate() {
		return trankRate;
	}
	public void setTrankRate(double trankRate) {
		this.trankRate = trankRate;
	}
	public double getWarriorRate() {
		return warriorRate;
	}
	public void setWarriorRate(double warriorRate) {
		this.warriorRate = warriorRate;
	}
	public double getAssassinRate() {
		return assassinRate;
	}
	public void setAssassinRate(double assassinRate) {
		this.assassinRate = assassinRate;
	}
	public double getMasterRate() {
		return masterRate;
	}
	public void setMasterRate(double masterRate) {
		this.masterRate = masterRate;
	}
	public double getShooterRate() {
		return shooterRate;
	}
	public void setShooterRate(double shooterRate) {
		this.shooterRate = shooterRate;
	}
	public double getAuxiliaryRate() {
		return auxiliaryRate;
	}
	public void setAuxiliaryRate(double auxiliaryRate) {
		this.auxiliaryRate = auxiliaryRate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}