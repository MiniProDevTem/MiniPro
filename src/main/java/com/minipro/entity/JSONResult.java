package com.minipro.entity;

import java.util.HashMap;

import com.minipro.util.JsonUtil;

public class JSONResult {
	
	final static String LOGIN="login";
	final static String SUCCESS="success";
	final static String FAIL="fail";
	private HashMap data =new HashMap();
	
	private String result=FAIL;
	
	private String message;
	
	public HashMap getData() {
		return data;
	}

	public void setData(HashMap data) {
		this.data = data;
	}
	
	public void fail(){
		result=FAIL;
	}
	
	public void fail(String message){
		result=FAIL;
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

	public String toJson(){
		String result=JsonUtil.toJson(this);
		return result;
	}
}
