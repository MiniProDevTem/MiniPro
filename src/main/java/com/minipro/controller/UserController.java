package com.minipro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller	
@RequestMapping(value="/user")
public class UserController extends AbstractController{

	@RequestMapping("/isExit")
	@ResponseBody
	public String isExit(@RequestParam(value="openId",required=true)String openId){
		String data="{\"openId\":\""+openId+"\"}";
		return invokeService("user","isExit",data);
	}
	
	@RequestMapping("/create")
	@ResponseBody
	public String createUser(@RequestParam(value="data",required=true)String data){
		return invokeService("user","createUser",data);
	}
	
	@RequestMapping("/updateInfo")
	@ResponseBody
	public String updateUser(@RequestParam(value="data",required=true)String data){
		return invokeService("user","updateUser",data);
	}
	
	@RequestMapping("/mark")
	@ResponseBody
	public String markUser(@RequestParam(value="data",required=true)String data){
		return invokeService("user","mark",data);
	}
	
	@RequestMapping("/getContacts")
	@ResponseBody
	public String getContacts(@RequestParam(value="uuid",required=true)String uuid){
		String data="{\"uuid\":\""+uuid+"\"}";
		return invokeService("user","getContacts",data);
	}
	
	@RequestMapping("/contactDetail")
	@ResponseBody
	public String contactDetail(@RequestParam(value="uuid",required=true)String uuid){
		String data="{\"uuid\":\""+uuid+"\"}";
		return invokeService("user","contactDetail",data);
	}
	
	@RequestMapping("/getDetail")
	@ResponseBody
	public String getDetail(@RequestParam(value="uuid",required=true)String uuid){
		String data="{\"uuid\":\""+uuid+"\"}";
		return invokeService("user","contactDetail",data);
	}
	
	@RequestMapping("/uploadImage")
	@ResponseBody
	public String uploadImage(@RequestParam(value="data",required=true)String data){
		return invokeService("user","uploadImage",data);
	}
	
	@RequestMapping("/delImage")
	@ResponseBody
	public String delImage(@RequestParam(value="data",required=true)String data){
		return invokeService("user","delImage",data);
	}
	
	
	
	
}
