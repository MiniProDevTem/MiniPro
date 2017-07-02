package com.minipro.util;

import com.google.gson.Gson;

public class JsonUtil {
	
	private static Gson gson=new Gson();
	
	public static <T>String toJson(T t){
		String str=gson.toJson(t);
		return str;
	}
	
	public static <T> T toObject(String json,Class<T> c){
		T t=gson.fromJson(json, c);
		return t;
	}

}
