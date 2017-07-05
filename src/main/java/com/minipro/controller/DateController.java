package com.minipro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller	
@RequestMapping(value="/date")
public class DateController extends AbstractController{
	
	@RequestMapping("/addHero")
	@ResponseBody
	public String addHer(@RequestParam(value="data",required=true)String data){
		return invokeService("date","addHero",data);
	}
	
	@RequestMapping("/addGameInform")
	@ResponseBody
	public String addGameInform(@RequestParam(value="data",required=true)String data){
		return invokeService("date","addGameInform",data);
	}

}
