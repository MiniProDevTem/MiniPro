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
    private String qqNumber;
    private String headUrl;
    private String voiceUrl;
    private int age;
    private String level;
    private String star;
    private String match;
    private String qq;
    
    List<Image>images;
    
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
	public String getQqNumber() {
		return qqNumber;
	}
	public void setQqNumber(String qqNumber) {
		this.qqNumber = qqNumber;
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
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getStar() {
		return star;
	}
	public void setStar(String star) {
		this.star = star;
	}
	public String getMatch() {
		return match;
	}
	public void setMatch(String match) {
		this.match = match;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public List<Image> getImages() {
		return images;
	}
	public void setImages(List<Image> images) {
		this.images = images;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	
}