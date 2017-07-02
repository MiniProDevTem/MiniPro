package com.minipro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller	
@RequestMapping(value="/user")
public class UserController extends AbstractController{

	
	@RequestMapping("/addUser")
	@ResponseBody
	public String addUser(@RequestParam(value = "id", required = true)String id){
		
		String date="{\"id\":\""+id+"\",\"name\":\"kang\",\"password\":\"knag\"}";
		return invokeService("User","addUser",date);

	}
}

//User u=JsonUtil.toObject(date, User.class);
//JSONResult rst=userService.addUser(u);
//return JsonUtil.toJson(rst);
