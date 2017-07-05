package com.minipro.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minipro.conf.ErrorConfig;
import com.minipro.entity.Calcular;
import com.minipro.entity.GameInform;
import com.minipro.entity.Hero;
import com.minipro.entity.JSONResult;
import com.minipro.entity.User;
import com.minipro.mapper.UserMapper;

@Service
public class DateService {
	@Autowired
	private UserMapper userMapper;
	
	public JSONResult addHero(Hero hero){
		JSONResult rst=new JSONResult();
		rst.fail();
		
		if(hero==null){
			rst.fail("参数为空!");
			return rst;
		}
		
		if(!userMapper.insertHero(hero)){
			rst.fail("数据出错");
			return rst;
		}
		
		rst.success();
		return rst;
	}
	
	public JSONResult addGameInform(GameInform gif){
		JSONResult rst=new JSONResult();
		rst.fail();
		
		if(gif==null){
			rst.fail("参数为空!");
			return rst;
		}
		User user=userMapper.getUserByUuid(gif.getUuid());
		if(user==null){
			rst.fail(ErrorConfig.USERNOTEXIT,"用户不存在","invalid parameter: uuid is not exit");
			return rst;
		}
		
		if(!userMapper.saveGameInform(gif)){
			rst.fail("数据出错");
			return rst;
		}
		List<Calcular> list=userMapper.calcuDate(gif.getUuid());
		if(list==null||list.size()<=0){
			rst.fail("数据出错");
			return rst;
		}
		int sum=0;
		for(Calcular c :list){
			sum+=c.getTime();
		}
		BigDecimal b=null;
		for(Calcular c: list){
			double score=c.getTime()*1.0/sum;
			b=new BigDecimal(score);
			score=b.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
			switch(c.getLocation()){
			case "1":
				user.setTrankRate(score);
				break;
			case "2":
				user.setWarriorRate(score);
				break;
			case "3":
				user.setAssassinRate(score);
				break;
			case "4":
				user.setWarriorRate(score);
				break;
			case "5":
				user.setWarriorRate(score);
				break;
			case "6":
				user.setWarriorRate(score);
				break;
			default :
					break;
			}
		}
		
		if(!userMapper.updateUser(user)){
			rst.fail("数据保存失败！");
			return rst;
		}
		
		rst.success();
		return rst;
	}
	

}
