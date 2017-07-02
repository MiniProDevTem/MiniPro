package com.minipro.util;

import java.util.Date;

public class BaseUtil {
	
	public static String getUUID(String openId){
		
		return openId;
	}
	
	public static long getTimeStamp(){
		Date date=new Date();
		return date.getTime();
	}

}
