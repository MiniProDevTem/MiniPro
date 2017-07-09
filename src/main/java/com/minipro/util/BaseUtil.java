package com.minipro.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
	
	public static void main(String[] args) {
		String date="1995/6/20";
		System.out.println(getMonth(date));
	}
	
	public static String getMonth(String data){
		if(data==null){
			return null;
		}
		String[] starArr = {"魔羯座","水瓶座", "双鱼座", "牡羊座",   
		        "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座" };     
		int[] DayArr = {22, 20, 19, 21, 21, 21, 22, 23, 23, 23, 23, 22};  // 两个星座分割日  
		
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");//小写的mm表示的是分钟 
			Date date;
			date = sdf.parse(data);
			int month=date.getMonth();
			int day=date.getDay();
			int index=month;
		    if (day < DayArr[month - 1]) {  
		            index = index - 1;  
		    }  
		    return starArr[index];  
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	   
	}

}
