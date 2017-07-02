package com.minipro.service;

public class AbstractService {
	
	protected final static String HASHKEY="member";//储存用户个人信息
	protected final static String MAPPERHASH="mapper";//保存uuid和opendId的一个映射的表
	protected final static String UKEY="idSets";//保存用户uuid的一张表
	protected final static String LVZSET="lvset";//喜欢的人列表
	protected final static String LVDZSET="lvdset";//喜欢你的人列表
	protected final static String ULVZSET="ulvzset";//不喜欢人的列表
	protected final static String CONNECTZSET="connectzset";//联系人列表

}
