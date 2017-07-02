package com.minipro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minipro.dao.BaseDao;
import com.minipro.entity.JSONResult;
import com.minipro.entity.User;

@Service
public class UserService {
	
	@Autowired
	private BaseDao baseDao;

	private final static String HASHKEY="member";
	private final static String UKEY="mlist";

	public JSONResult addUser(User u){
		JSONResult rst=new JSONResult();
		rst.fail();
		if(u==null){
			rst.fail("参数有误，请检查参数");
			return rst;
		}
		User user =baseDao.selectFromHash(HASHKEY,u.getId(),u.getClass());
		if(user!=null){
			rst.fail("该用户已存在");
			return rst;
		}
		boolean result=baseDao.insertHash(HASHKEY, u.getId(),u);
		if(!result){
			rst.fail("该用户添加失败");
			return rst;
		}
		//result=baseDao.insertSet(UKEY, u.getId());
		rst.success();
		rst.getData().put("user", u);
		return rst;
	}
}
