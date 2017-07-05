package com.minipro.entity;

import java.text.Format;
import java.util.HashMap;

import com.minipro.util.JsonUtil;

public class JSONResult {
	
	final static String LOGIN="login";
	final static String SUCCESS="success";
	final static String FAIL="fail";
	private HashMap<String,Object> data =new HashMap<String,Object>();
	private String result=FAIL;
	private String message;
	public HashMap<String,Object> getData() {
		return data;
	}

	public void setData(HashMap<String,Object> data) {
		this.data = data;
	}
	
	public void fail(){
		result=FAIL;
	}
	
	public void fail(int errorCode, String simpleMessage, String cause) {
		result = FAIL;
		String message = String.format("ErrorCode: %d, %s \n%s",errorCode, simpleMessage, cause);
		this.message=message;
	}
	
	public void success(){
		result=SUCCESS;
	}
	
	public boolean toSuccess(){
		return SUCCESS.equals(result);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public void put(String key,Object obj){
		data.put(key, obj);
	}
	public String toJson(){
		String result=JsonUtil.toJson(this);
		return result;
	}
}
