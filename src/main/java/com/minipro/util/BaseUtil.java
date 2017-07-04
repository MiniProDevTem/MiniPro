package com.minipro.util;

import java.util.Date;
import java.util.UUID;

public class BaseUtil {
	
	public static String getUUID(){
		return UUID.randomUUID().toString();
	}
	
	public static long getTimeStamp(){
		Date date=new Date();
		return date.getTime();
	}

}
