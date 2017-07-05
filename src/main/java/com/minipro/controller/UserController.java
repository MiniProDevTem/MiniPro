package com.minipro.controller;

import com.minipro.Authentication.AccessRequired;
import com.minipro.entity.User;
import com.minipro.service.UserService;
import com.minipro.service.param.OpenIDParam;
import org.springframework.beans.factory.annotation.Autowired;
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
    @AccessRequired
	public String createUser(@RequestParam(value="data",required=true)String data){
		return invokeService("user","createUser",data);
	}
	
	@RequestMapping("/updateInfo")
	@ResponseBody
    @AccessRequired
	public String updateUser(@RequestParam(value="data",required=true)String data){
		return invokeService("user","updateUser",data);
	}
	
	@RequestMapping("/recommend")
	@ResponseBody
	public String recommend(@RequestParam(value="data",required=true)String data){
		return invokeService("user","recommend",data);
	}
	
	@RequestMapping("/mark")
	@ResponseBody
    @AccessRequired
	public String markUser(@RequestParam(value="data",required=true)String data){
		return invokeService("user","mark",data);
	}
	
	@RequestMapping("/getContacts")
	@ResponseBody
    @AccessRequired
	public String getContacts(@RequestParam(value="uuid",required=true)String uuid){
		String data="{\"uuid\":\""+uuid+"\"}";
		return invokeService("user","getContacts",data);
	}
	
	@RequestMapping("/contactDetail")
	@ResponseBody
    @AccessRequired
	public String contactDetail(@RequestParam(value="uuid",required=true)String uuid){
		String data="{\"uuid\":\""+uuid+"\"}";
		return invokeService("user","contactDetail",data);
	}
	
	@RequestMapping("/getDetail")
	@ResponseBody
    @AccessRequired
	public String getDetail(@RequestParam(value="uuid",required=true)String uuid){
		String data="{\"uuid\":\""+uuid+"\"}";
		return invokeService("user","contactDetail",data);
	}
	
	@RequestMapping("/uploadImage")
	@ResponseBody
    @AccessRequired
	public String uploadImage(@RequestParam(value="data",required=true)String data){
		return invokeService("user","uploadImage",data);
	}
	
	@RequestMapping("/delImage")
	@ResponseBody
    @AccessRequired
	public String delImage(@RequestParam(value="data",required=true)String data){
		return invokeService("user","delImage",data);
	}
	
	@RequestMapping("/addGInform")
	@ResponseBody
	public String addGInform(@RequestParam(value="data",required=true)String data){
		return invokeService("user","addGInform",data);
	}
	
	@RequestMapping("/addHearOrVoice")
	@ResponseBody
	public String addHearOrVoice(@RequestParam(value="data",required=true)String data){
		return invokeService("user","addHearOrVoice",data);
	}
	
	
	
	
}
