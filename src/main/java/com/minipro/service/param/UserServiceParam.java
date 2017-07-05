package com.minipro.service.param;

public class UserServiceParam {
	
    public static class CreateUserParam{
    	private String openId;
    	private String name;
    	private String place;
    	private String birthday;
    	private String sex;
    	private String location;
    	private String qq;
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
		public String getQq() {
			return qq;
		}
		public void setQq(String qq) {
			this.qq = qq;
		}
    }
    
    public static class UpdateUserParam{
    	private String openId;
    	private String name;
    	private String place;
    	private String birthday;
    	private String sex;
    	private String location;
    	private String qq;
    	private String uuid;
    	private long timestamp;
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
		public String getQq() {
			return qq;
		}
		public void setQq(String qq) {
			this.qq = qq;
		}
		public String getUuid() {
			return uuid;
		}
		public void setUuid(String uuid) {
			this.uuid = uuid;
		}
		public long getTimestamp() {
			return timestamp;
		}
		public void setTimestamp(long timestamp) {
			this.timestamp = timestamp;
		}
		
    }
    
    public static class MarkUserParam{
    	private String uuid;
    	private String ouuid;//对方的uuid
    	private boolean isLike;
		public String getUuid() {
			return uuid;
		}
		public void setUuid(String uuid) {
			this.uuid = uuid;
		}
		public String getOuuid() {
			return ouuid;
		}
		public void setOuuid(String ouuid) {
			this.ouuid = ouuid;
		}
		public boolean isLike() {
			return isLike;
		}
		public void setLike(boolean isLike) {
			this.isLike = isLike;
		}
    	
    }
    
    public static class RecommendParam{
    	
    	private String uuid;
    	private String sex;
    	private Integer ageMin;
    	private Integer ageMax;
    	private Integer trank;
    	private Integer warrior;
    	private Integer assassin;
    	private Integer master;
    	private Integer shooter;
    	private Integer auxiliary;
    	
    	private Integer pageNo;
    	
    	private Integer limit;
    	private Integer offset;
		public String getUuid() {
			return uuid;
		}
		public void setUuid(String uuid) {
			this.uuid = uuid;
		}
		public String getSex() {
			return sex;
		}
		public void setSex(String sex) {
			this.sex = sex;
		}
		public Integer getAgeMin() {
			return ageMin;
		}
		public void setAgeMin(Integer ageMin) {
			this.ageMin = ageMin;
		}
		public Integer getAgeMax() {
			return ageMax;
		}
		public void setAgeMax(Integer ageMax) {
			this.ageMax = ageMax;
		}
		public Integer getTrank() {
			return trank;
		}
		public void setTrank(Integer trank) {
			this.trank = trank;
		}
		public Integer getWarrior() {
			return warrior;
		}
		public void setWarrior(Integer warrior) {
			this.warrior = warrior;
		}
		public Integer getAssassin() {
			return assassin;
		}
		public void setAssassin(Integer assassin) {
			this.assassin = assassin;
		}
		public Integer getMaster() {
			return master;
		}
		public void setMaster(Integer master) {
			this.master = master;
		}
		public Integer getShooter() {
			return shooter;
		}
		public void setShooter(Integer shooter) {
			this.shooter = shooter;
		}
		public Integer getAuxiliary() {
			return auxiliary;
		}
		public void setAuxiliary(Integer auxiliary) {
			this.auxiliary = auxiliary;
		}
		public Integer getPageNo() {
			return pageNo;
		}
		public void setPageNo(Integer pageNo) {
			this.pageNo = pageNo;
		}
		public Integer getLimit() {
			return limit;
		}
		public void setLimit(Integer limit) {
			this.limit = limit;
		}
		public Integer getOffset() {
			return offset;
		}
		public void setOffset(Integer offset) {
			this.offset = offset;
		}
    	
		
		
    	
    }
    
    public static class UploadImageParam{
    	private String uuid;
    	private String imageUrl;
		public String getUuid() {
			return uuid;
		}
		public void setUuid(String uuid) {
			this.uuid = uuid;
		}
		public String getImageUrl() {
			return imageUrl;
		}
		public void setImageUrl(String imageUrl) {
			this.imageUrl = imageUrl;
		}
    	
    }
    
    public static class DelImageParam{
    	private String uuid;
    	private String imageUrl;
		public String getUuid() {
			return uuid;
		}
		public void setUuid(String uuid) {
			this.uuid = uuid;
		}
		public String getImageUrl() {
			return imageUrl;
		}
		public void setImageUrl(String imageUrl) {
			this.imageUrl = imageUrl;
		}
    	
    }
    
    public static class HeadOrVoiceParam{
    	private String uploadUrl;
    	private int type;
    	private String uuid;
    	private String timestamp;
		public String getUploadUrl() {
			return uploadUrl;
		}
		public void setUploadUrl(String uploadUrl) {
			this.uploadUrl = uploadUrl;
		}
		public int getType() {
			return type;
		}
		public void setType(int type) {
			this.type = type;
		}
		public String getUuid() {
			return uuid;
		}
		public void setUuid(String uuid) {
			this.uuid = uuid;
		}
		public String getTimestamp() {
			return timestamp;
		}
		public void setTimestamp(String timestamp) {
			this.timestamp = timestamp;
		}
    }
}
