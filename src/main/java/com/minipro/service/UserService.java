package com.minipro.service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.minipro.conf.ErrorConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minipro.dao.BaseDao;
import com.minipro.entity.Image;
import com.minipro.entity.JSONResult;
import com.minipro.entity.User;
import com.minipro.service.param.IDParam;
import com.minipro.service.param.OpenIDParam;
import com.minipro.service.param.UserServiceParam;
import com.minipro.util.BaseUtil;
import com.minipro.util.UpdateUtil;

@Service
public class UserService extends AbstractService {
	
	@Autowired
	private BaseDao baseDao;
	
	/**
	 * 通过传入的openId从MAPPERHASH表中匹配，看是否存在
	 * @param openIdParam
	 * @return
	 */
	public JSONResult isExit(OpenIDParam openIdParam){
		JSONResult rst=new JSONResult();
		rst.fail();
		if(openIdParam==null){
			rst.fail(ErrorConfig.INVALIDPARAM,"用户未登录","invalid parameter: openid is null");
			return rst;
		}
		String uuid=baseDao.selectFromHash(MAPPERHASH, openIdParam.getOpenId(), String.class);
		if(uuid==null){//表示该用户第一次登陆
			rst.success();
			rst.put("exit", false);
			return rst;
		}
		
		rst.success();
		rst.put("uuid",uuid);
		rst.put("exit", true);
		return rst;
	}
	
	public JSONResult createUser(UserServiceParam.CreateUserParam createUserParam){
		JSONResult rst=new JSONResult();
		rst.fail();
		
		if(createUserParam==null){
			rst.fail();
			return rst;
		}
		
		String uuid=baseDao.selectFromHash(MAPPERHASH, createUserParam.getOpenId(), String.class);
		if(uuid != null){//表示该用户第一次登陆
			String cause = String.format("user identified by openID: %d is registered, corresponding uuid is %d",createUserParam.getOpenId(),uuid);
			rst.fail(ErrorConfig.INVALIDPARAM, "用户已经注册", cause);
			return rst;
		}
		
//	    uuid = BaseUtil.getUUID();
	    User user=new User();
	    user.setUuid(BaseUtil.getUUID());
	    UpdateUtil.setValues(user, createUserParam);//赋值
		//此处应该放入到一个事务当中
	    baseDao.insertHash(MAPPERHASH, user.getOpenId(), user.getUuid());
	    baseDao.insertSet(UKEY, user.getUuid());
	    baseDao.insertHash(HASHKEY, user.getUuid(),user);
	    
	    rst.success();
	    return rst;
	}
	
	public JSONResult updateUser(UserServiceParam.UpdateUserParam updateUserParam){
		JSONResult rst=new JSONResult();
		rst.fail();
		
		if(updateUserParam==null){
			String cause = String.format("invalid request parameter, the parameter is %s",updateUserParam.toString());
			rst.fail(ErrorConfig.INVALIDPARAM, "invalid operation", "invalid request parameter, ");
			return rst;
		}
		
		User user=baseDao.selectFromHash(HASHKEY, updateUserParam.getUuid(),User.class);
		if(user==null){//该用户不存在
			String cause = String.format("%s correspoding user is not found",updateUserParam.getUuid());
			rst.fail(ErrorConfig.NOTFOUND, "用户不存在", cause);
			return rst;
		}
		
	    UpdateUtil.setValues(user, updateUserParam);//赋值
	    
	    baseDao.insertHash(HASHKEY, user.getUuid(),user);
	    rst.put("timestamp",updateUserParam.getTimestamp());
	    rst.success();
	    return rst;
	}
	
	public JSONResult mark(UserServiceParam.MarkUserParam markUserParam){
		JSONResult rst=new JSONResult();
		rst.fail();
		
		if(markUserParam==null){
			String cause = String.format("invalid request parameter, the parameter is %s",markUserParam.toString());
			rst.fail(ErrorConfig.INVALIDPARAM, "invalid operation", "invalid request parameter, ");
			return rst;
		}
		
		User user=baseDao.selectFromHash(HASHKEY, markUserParam.getUuid(),User.class);
		if(user==null){//该用户不存在
			String cause = String.format("%s corresponding user is not found", markUserParam.getUuid());
			rst.fail(ErrorConfig.INVALIDPARAM, "用户不存在", "invalid request parameter, ");
			return rst;
		}
		user=baseDao.selectFromHash(HASHKEY, markUserParam.getOuuid(),User.class);
		if(user==null){//该用户不存在
			String cause = String.format("%s corresponding user is not found", markUserParam.getUuid());
			rst.fail(ErrorConfig.INVALIDPARAM, "用户不存在", "invalid request parameter, ");
			return rst;
		}
		boolean result=false;
		if(!markUserParam.isLike()){//标记为不喜欢
			result=baseDao.isExit(ULVZSET+markUserParam.getUuid(), markUserParam.getOuuid());
			if(result){
				String cause = String.format("%s corresponding user is not found", markUserParam.getUuid());
				rst.fail(ErrorConfig.INVALIDPARAM, "目标用户已被您标记为不喜欢！", "invalid request parameter, ");
			}else{
				baseDao.insertSortSet(ULVZSET+markUserParam.getUuid(),  markUserParam.getOuuid(), BaseUtil.getTimeStamp());
				rst.success();
			}
			return rst;
		}
		//判断该用户的喜欢人列表是否已存在目标用户
	    result=baseDao.isExit(LVZSET+markUserParam.getUuid(), markUserParam.getOuuid());
		if(result){
			String cause = String.format("%s corresponding user is not found", markUserParam.getUuid());
			rst.fail(ErrorConfig.INVALIDPARAM, "目标用户已被您标记为喜欢", "invalid request parameter, ");
			rst.fail();
			return rst;
		}
		baseDao.insertSortSet(LVZSET+markUserParam.getUuid(), markUserParam.getOuuid(), BaseUtil.getTimeStamp());
		
		result=baseDao.isExit(LVDZSET+markUserParam.getUuid(), markUserParam.getOuuid());
		rst.success();
		if(result){
			rst.put("isMS",true);
			baseDao.insertSortSet(CONNECTZSET+markUserParam.getUuid(), markUserParam.getOuuid(),BaseUtil.getTimeStamp());
		}else{
			rst.put("isMS", false);
		}
		return rst;
	}
	
	public JSONResult getContacts(IDParam idParam){
		JSONResult rst=new JSONResult();
		rst.fail();
		
		if(idParam==null){
			String cause = String.format("%s corresponding user is not found", idParam.getUuid());
			rst.fail(ErrorConfig.INVALIDPARAM, "目标用户已被您标记为喜欢", "invalid request parameter, ");
			return rst;
		}
		
		User user=baseDao.selectFromHash(HASHKEY, idParam.getUuid(),User.class);
		if(user==null){//该用户不存在
			String cause = String.format("%s corresponding user is not found", idParam.getUuid());
			rst.fail(ErrorConfig.INVALIDPARAM, "用户不存在", "invalid request parameter, ");
			return rst;
		}

		Set<String> uuids=(Set<String>) baseDao.select(CONNECTZSET+idParam.getUuid());
		
		Set<User> userSet=new HashSet<User>();
		for(String uuid : uuids){
			//此处数据可以重构一下，减少数据传输容量
			user=baseDao.selectFromHash(HASHKEY, uuid,User.class);
			if(user!=null)
				userSet.add(user);
		}
		rst.success();
		rst.put("userList",userSet);
		return rst;
	}
	
	public JSONResult contactDetail(IDParam idParam){
		JSONResult rst=new JSONResult();
		rst.fail();
		
		if(idParam==null){
			String cause = String.format("invalid request parameter %s", idParam.toString());
			rst.fail(ErrorConfig.INVALIDPARAM, "操作错误", cause);
			return rst;
		}
		
		User user=baseDao.selectFromHash(HASHKEY, idParam.getUuid(),User.class);
		if(user==null){//该用户不存在
			String cause = String.format("%s corresponding user is not found", idParam.getUuid());
			rst.fail(ErrorConfig.INVALIDPARAM, "用户不存在", "invalid request parameter, ");
			return rst;
		}
		
		rst.success();
		rst.put("user",user);
		return rst;
	}
	
	public JSONResult uploadImage(UserServiceParam.UploadImageParam imageParam ){
		JSONResult rst=new JSONResult();
		rst.fail();
		
		if(imageParam==null){
			String cause = String.format("invalid request parameter %s", imageParam.toString());
			rst.fail(ErrorConfig.INVALIDPARAM, "操作错误", cause);
			return rst;
		}
		
		User user=baseDao.selectFromHash(HASHKEY, imageParam.getUuid(),User.class);
		if(user==null){//该用户不存在
			String cause = String.format("%s corresponding user is not found", imageParam.getUuid());
			rst.fail(ErrorConfig.INVALIDPARAM, "用户不存在", "invalid request parameter, ");
			return rst;
		}
		Image image=new Image();
		image.setUrl(imageParam.getImageUrl());
		image.setTimestamp(BaseUtil.getTimeStamp());
		user.getImages().add(image);
		
		baseDao.insertHash(HASHKEY, user.getUuid(),user);
		rst.success();
		return rst;
	}
	
	public JSONResult delImage(UserServiceParam.DelImageParam imageParam ){
		JSONResult rst=new JSONResult();
		rst.fail();
		
		if(imageParam==null){
			String cause = String.format("invalid request parameter %s", imageParam.toString());
			rst.fail(ErrorConfig.INVALIDPARAM, "操作错误", cause);
			return rst;
		}
		
		User user=baseDao.selectFromHash(HASHKEY, imageParam.getUuid(),User.class);
		if(user==null){//该用户不存在
			String cause = String.format("%s corresponding user is not found", imageParam.getUuid());
			rst.fail(ErrorConfig.INVALIDPARAM, "用户不存在", "invalid request parameter, ");
			return rst;
		}
		Image image=null;
		Iterator<Image> itor=user.getImages().iterator();
		boolean flag=false;
		while(itor.hasNext()){
			image=itor.next();
			if(image.getUrl().equals(imageParam.getImageUrl())){
				itor.remove();
				flag=true;
			}
		}
		baseDao.insertHash(HASHKEY, user.getUuid(),user);
		rst.success();
		return rst;
	}
	
	
	
	
}
