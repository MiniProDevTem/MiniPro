package com.minipro.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.minipro.conf.ErrorConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minipro.criteria.RecommendSearchCriteria;
import com.minipro.dao.BaseDao;
import com.minipro.entity.Album;
import com.minipro.entity.GameInform;
import com.minipro.entity.Hero;
import com.minipro.entity.Image;
import com.minipro.entity.JSONResult;
import com.minipro.entity.SUser;
import com.minipro.entity.User;
import com.minipro.entity.UserGameView;
import com.minipro.mapper.UserMapper;
import com.minipro.recommend.Pair;
import com.minipro.recommend.UserBaseRecommend;
import com.minipro.service.param.IDParam;
import com.minipro.service.param.OpenIDParam;
import com.minipro.service.param.UserServiceParam;
import com.minipro.util.BaseUtil;
import com.minipro.util.JsonUtil;
import com.minipro.util.UpdateUtil;

@Service
public class UserService extends AbstractService {
	
	@Autowired
	private BaseDao baseDao;
	@Autowired
	private UserMapper userMapper;
	
	/**
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
		
		User user=userMapper.getUserByOpenId(openIdParam.getOpenId());
		if(user==null){
			rst.success();
			rst.put("exit", false);
			return rst;
		}
		rst.success();
		rst.put("uuid",user.getUuid());
		rst.put("exit", true);
		return rst;
	}
	
	
	public JSONResult addGInform(GameInform p){
		JSONResult rst=new JSONResult();
		rst.fail();
		
		if(p==null){
			rst.fail("参数有误，请检查参数！");
			return rst;
		}
		
		User user=userMapper.getUserByUuid(p.getUuid());
		if(user==null){
			rst.fail("该用户不存在");
			return rst;
		}
	    if(!userMapper.saveGameInform(p)){
	    	rst.fail("数据保存失败!");
	    	return rst;
	    }
	    
	    rst.success();
	    return rst;
	}
	
	public JSONResult createUser(UserServiceParam.CreateUserParam createUserParam){
		JSONResult rst=new JSONResult();
		rst.fail();
		
		if(createUserParam==null){
			rst.fail();
			return rst;
		}
		String uuid=BaseUtil.getUUID();
		User user=userMapper.getUserByOpenId(createUserParam.getOpenId());
		if(user!=null){
			String cause = String.format("user identified by openID: %d is registered, corresponding uuid is %d",createUserParam.getOpenId(),uuid);
			rst.fail(ErrorConfig.INVALIDPARAM, "用户已经注册", cause);
			return rst;
		}
		
	   
	    user=new User();

	    UpdateUtil.setValues(user, createUserParam);//赋值
	    user.setUuid(uuid);
		//此处应该放入到一个事务当中
	    if(!userMapper.createUser(user)){
	    	rst.fail("数据保存失败!");
	    	return rst;
	    }
	    
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
	
		User user=userMapper.getUserByUuid(updateUserParam.getUuid());
		if(user==null){//该用户不存在
			String cause = String.format("%s correspoding user is not found",updateUserParam.getUuid());
			rst.fail(ErrorConfig.NOTFOUND, "用户不存在", cause);
			return rst;
		}
				
	    UpdateUtil.setValues(user, updateUserParam);//赋值
	    
	    if(!userMapper.updateUser(user)){
	    	rst.fail("用户修改失败！");
	    	return rst;
	    }
	    rst.put("timestamp",updateUserParam.getTimestamp());
	    rst.success();
	    return rst;
	}
	
	@SuppressWarnings("unchecked")
	public JSONResult recommend(UserServiceParam.RecommendParam recommendParam){
		JSONResult rst=new JSONResult();
		rst.fail();
		
		if(recommendParam==null){
			rst.fail("参数有误，请检查参数！");
			return rst;
		}
		
		User user=userMapper.getUserByUuid(recommendParam.getUuid());
		if(user==null){//该用户不存在
			rst.fail("该用户不存在");
			return rst;
		}
		RecommendSearchCriteria rsc=new RecommendSearchCriteria();
		UpdateUtil.setValues(rsc, recommendParam);
	
		
		Set<String> connectSets= (Set<String>) baseDao.select(CONNECTZSET+rsc.getUuid());
		Set<String> ulvSets=(Set<String>) baseDao.select(ULVZSET+rsc.getUuid());
		Set<String> lvdSets=(Set<String>) baseDao.select(LVDZSET+rsc.getUuid());
		if(ulvSets==null)
			ulvSets=new HashSet<String>();
		ulvSets.addAll(connectSets);
		//联系人+讨厌的人+自己
		ulvSets.add(rsc.getUuid());//把自己添加进去
		//保存了用户的喜欢人列表
		if(lvdSets==null)
			lvdSets=new HashSet<String>();
		lvdSets.removeAll(ulvSets);
		
		
		Set<String> removes=new HashSet<String>();
		removes.addAll(ulvSets);
		removes.addAll(lvdSets);
		removes.add(rsc.getUuid());
		
		rsc.setRemoveSets(removes );
		
		
		if(lvdSets.size()>NUMSOFPUSH){//被喜欢列表大于NUMSOFPUSH条，直接前NUMSOFPUSH推送，不进行计算
			String [] ids=new String[NUMSOFPUSH];
			int index=0;
			for(String s:lvdSets){
				if(index>=NUMSOFPUSH)
					break;
				ids[index++]=s;
			}
			List<User> lvSets=this.userMapper.getUserByDUuids(ids);
			rst.put("recommendUsers", lvSets);
			rst.success();
			return rst;
		}
		
		 int pageSize = OFFSET;
		 rsc.setOffset(0);
	     rsc.setLimit(pageSize);
	     List<String> chooseIds=this.userMapper.getChooseUuid(rsc);//选择pageSIze条数据
		
		 if(chooseIds.size()<=0){
			 rst.fail("系统无符合用户筛选条件的结果！");
			 return rst;
		 }
        //建立一个用户下表与uuid的映射
		Map<String,Integer> mapperIndex=new HashMap<String,Integer>();
        for(int i=0;i<chooseIds.size();i++){
        	mapperIndex.put(chooseIds.get(i),i);
        }
        
        List<Hero> heros=this.userMapper.getHeroList();
        //横坐标的映射
        Map<Integer,Integer> mapperHIds=new HashMap<Integer,Integer>();
        Map<Integer,Hero> mapperHeros=new HashMap<Integer,Hero>();
        for(int i=0;i<heros.size();i++){
        	mapperHIds.put(heros.get(i).getId(),i);
        	mapperHeros.put(i, heros.get(i));
        }
        
        String [] chooseIdStr=chooseIds.toArray(new String[] {});

        List<UserGameView> gameInforms=this.userMapper.getGameInform(chooseIdStr);
        
        int userNum = chooseIdStr.length, heroNum =heros.size();
		int[] userHaveHeros = new int[heroNum];//用户已经拥有的英雄
		float[] userCountOfUseHeros = new float[heroNum];//用户英雄使用的次数
		float[] userPercentOfWinHeros = new float[heroNum];//每一个英雄胜率
		float[][] candidateCountOfUseHeros = new float[userNum][heroNum];
		float[][] candidatePercentOfWinHeros = new float[userNum][heroNum];
		//填充样本数据
        Map<Integer,User> mapperUser=new HashMap<Integer,User>();
		for(UserGameView ugv:gameInforms){
        	String key=ugv.getUuid();
        	Integer userIndex=mapperIndex.get(key);
        	Integer heroKey=ugv.getHeroId();
        	if(mapperUser.get(userIndex)==null){
        		User u=new User();
        		UpdateUtil.setValues(u,ugv);
        		mapperUser.put(userIndex, u);//为
        	}
        	if(heroKey==null||heroKey==0){
        		continue;
        	}
        	Integer heroIndex=mapperHIds.get(heroKey);
        	if(userIndex==null){
        		continue;
        	}
        	
        	candidateCountOfUseHeros[userIndex][heroIndex]=ugv.getTimeUse();
        	candidatePercentOfWinHeros[userIndex][heroIndex]=ugv.getWinRate();
        }
        
		//填充个人信息
		
		List<UserGameView> ownGameInforms=this.userMapper.getOwnGameInform(rsc.getUuid());
		
		for(UserGameView ugv:ownGameInforms){
			Integer hkey=ugv.getHeroId();
			Integer hIndex=mapperHIds.get(hkey);
			if(hIndex==null)
				continue;
			userHaveHeros[hIndex]=1;//是否拥有该英雄
			userCountOfUseHeros[hIndex]=ugv.getTimeUse();
			userPercentOfWinHeros[hIndex]=ugv.getWinRate();
		}
		
		Pair<List<Integer>, List<Integer>> result = UserBaseRecommend.recommend(
				userHaveHeros, 
				userCountOfUseHeros, 
				userPercentOfWinHeros,  
				candidateCountOfUseHeros, 
				candidatePercentOfWinHeros);
		
		List<User> usresults=new ArrayList<User>();
		List<Integer> uIndex=result.getFirst();
		int flag=uIndex.size()>NUMSOFPUSH?NUMSOFPUSH:uIndex.size();//取推荐列表的前20个
		for(int i=0;i<flag;i++){
			User item=mapperUser.get(uIndex.get(i));
			if(item!=null)
				usresults.add(item);
		}
		if(lvdSets!=null&&lvdSets.size()>0){
			List<User> lvSets=this.userMapper.getUserByDUuids(lvdSets.toArray(new String[] {}));
			usresults.addAll(0, lvSets);
		}
		
		if(usresults.size()<=0){
			rst.fail("该用户无相似用户!");
			return rst;
		}
		
		List<Integer> hIndex=result.getSecond();
		List<Hero> hresults=new ArrayList<Hero>();
		for(Integer id : hIndex){
			Hero hero=mapperHeros.get(id);
			if(hero!=null)
				hresults.add(hero);
		}
		
		rst.put("recommendUsers", usresults);
		rst.put("recommendHeros", hresults);
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
		
		if(markUserParam.getUuid().equals(markUserParam.getOuuid())){
			rst.fail("目标uid有误！");
			return rst;
		}
		
		User user=userMapper.getUserByUuid(markUserParam.getUuid());
		if(user==null){//该用户不存在
			String cause = String.format("%s corresponding user is not found", markUserParam.getUuid());
			rst.fail(ErrorConfig.INVALIDPARAM, "用户不存在", "invalid request parameter, ");
			return rst;
		}
		
		 user=userMapper.getUserByUuid(markUserParam.getOuuid());
		if(user==null){//该用户不存在
			String cause = String.format("%s corresponding user is not found", markUserParam.getUuid());
			rst.fail(ErrorConfig.INVALIDPARAM, "用户不存在", "invalid request parameter, ");
			return rst;
		}
		
		
		boolean result=false;
		//在三张表中检查，是否目标用户已经加入到某一张表中
		result=baseDao.isExit(LVZSET+markUserParam.getUuid(), markUserParam.getOuuid());
		if(result){
			rst.fail("该用户已被你标记");
			return rst;
		}
		result=baseDao.isExit(CONNECTZSET+markUserParam.getUuid(), markUserParam.getOuuid());
		if(result){
			rst.fail("该用户已是你的联系人");
			return rst;
		}
		result=baseDao.isExit(ULVZSET+markUserParam.getUuid(), markUserParam.getOuuid());
		if(result){
			rst.fail("该用户已被你标记");
			return rst;
		}
		
		
		if(!markUserParam.isLike()){//标记为不喜欢
			if(result){
				String cause = String.format("%s corresponding user is not found", markUserParam.getUuid());
				rst.fail(ErrorConfig.INVALIDPARAM, "目标用户已被您标记为不喜欢！", "invalid request parameter, ");
			}else{
				baseDao.insertSortSet(ULVZSET+markUserParam.getUuid(),  markUserParam.getOuuid(), BaseUtil.getTimeStamp());
				rst.success();
			}
			return rst;
		}
		/*//判断该用户的喜欢人列表是否已存在目标用户
	    result=baseDao.isExit(LVZSET+markUserParam.getUuid(), markUserParam.getOuuid());
		if(result){
			String cause = String.format("%s corresponding user is not found", markUserParam.getUuid());
			rst.fail(ErrorConfig.INVALIDPARAM, "目标用户已被您标记为喜欢", "invalid request parameter, ");
			rst.fail();
			return rst;
		}*/
		baseDao.insertSortSet(LVZSET+markUserParam.getUuid(), markUserParam.getOuuid(), BaseUtil.getTimeStamp());
		baseDao.insertSortSet(LVDZSET+markUserParam.getOuuid(), markUserParam.getUuid(), BaseUtil.getTimeStamp());
		result=baseDao.isExit(LVDZSET+markUserParam.getUuid(), markUserParam.getOuuid());
		rst.success();
		if(result){
			rst.put("isMS",true);//互相加为联系人
			baseDao.insertSortSet(CONNECTZSET+markUserParam.getUuid(), markUserParam.getOuuid(),BaseUtil.getTimeStamp());
			baseDao.insertSortSet(CONNECTZSET+markUserParam.getOuuid(), markUserParam.getUuid(),BaseUtil.getTimeStamp());
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
		
		User user=userMapper.getUserByUuid(idParam.getUuid());
		if(user==null){//该用户不存在
			String cause = String.format("%s corresponding user is not found", idParam.getUuid());
			rst.fail(ErrorConfig.INVALIDPARAM, "用户不存在", "invalid request parameter, ");
			return rst;
		}

		Set<String> uuids=(Set<String>) baseDao.select(CONNECTZSET+idParam.getUuid());
		String [] ids = uuids.toArray(new String[] {}); 

		List<SUser> list =userMapper.getUserByUuids(ids);

		rst.success();
		rst.put("userList",list);
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
		
		User user=userMapper.getUserByUuid(idParam.getUuid());
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
		
		User user=userMapper.getUserByUuid(imageParam.getUuid());
		if(user==null){//该用户不存在
			String cause = String.format("%s corresponding user is not found", imageParam.getUuid());
			rst.fail(ErrorConfig.INVALIDPARAM, "用户不存在", "invalid request parameter, ");
			return rst;
		}
		
		Image image=new Image();
		image.setUrl(imageParam.getImageUrl());
		image.setTimestamp(BaseUtil.getTimeStamp());
		List<Image> images=null;
		Album album=null;
		String imgJson=null;
		if(user.getImages()==null){
			images=new ArrayList<Image>();
			images.add(image);
			album=new Album();
			album.setImages(images);
			imgJson=JsonUtil.toJson(album);
			user.setImages(imgJson);
			if(!userMapper.updateUser(user)){
				rst.fail("数据保存失败！");
				return rst;
			}
			rst.success();
			return rst;
		}
		
		imgJson=user.getImages();
		album=JsonUtil.toObject(imgJson,Album.class);
		album.getImages().add(image);
		imgJson=JsonUtil.toJson(album);
		user.setImages(imgJson);
		
		if(!userMapper.updateUser(user)){
			rst.fail("数据保存失败！");
			return rst;
		}
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
		
		User user=userMapper.getUserByUuid(imageParam.getUuid());
		if(user==null){//该用户不存在
			String cause = String.format("%s corresponding user is not found", imageParam.getUuid());
			rst.fail(ErrorConfig.INVALIDPARAM, "用户不存在", "invalid request parameter, ");
			return rst;
		}
		if(user.getImages()==null){
			rst.fail("该用户无相册！");
			return rst;
		}
		
		Album album=null;
		String imgJson=null;
		Image image=null;
		imgJson=user.getImages();
		album=JsonUtil.toObject(imgJson,Album.class);
		
		Iterator<Image> itor=album.getImages().iterator();
		boolean flag=false;
		while(itor.hasNext()){
			image=itor.next();
			if(image.getUrl().equals(imageParam.getImageUrl())){
				itor.remove();
				flag=true;
			}
		}
		
		imgJson=JsonUtil.toJson(album);
		user.setImages(imgJson);
		
		if(!userMapper.updateUser(user)){
			rst.fail("数据修改失败！");
			return rst;
		}
		
		rst.success();
		return rst;
	}
	
	public JSONResult addHearOrVoice(UserServiceParam.HeadOrVoiceParam p){
		JSONResult rst=new JSONResult();
		rst.fail();
		
		if(p==null){
			rst.fail("参数有误，请检查参数！");
			return rst;
		}
		
		User user=userMapper.getUserByUuid(p.getUuid());
		if(user==null){//该用户不存在
			rst.fail("该用户不存在");
			return rst;
		}
		switch(p.getType()){
			case 0:
				user.setHeadUrl(p.getUploadUrl());
				break;
			case 1:
				user.setVoiceUrl(p.getUploadUrl());
				break;
			default:
				rst.fail("上传类型有误！");
				return rst;
		}
		if(!this.userMapper.updateUser(user)){
			rst.fail("信息保存失败!");
			rst.put("timestamp", p.getTimestamp());
			return rst;
		}
		
		rst.success();
		rst.put("timestamp", p.getTimestamp());
		return rst;
	}
	
}
