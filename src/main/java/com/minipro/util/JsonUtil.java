package com.minipro.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtil {
	
	private static Gson gson=new GsonBuilder().serializeNulls().create();
	
	public static <T>String toJson(T t){
		String str=gson.toJson(t);
		return str;
	}
	
	public static <T> T toObject(String json,Class<T> c){
		T t=gson.fromJson(json, c);
		return t;
	}

}
