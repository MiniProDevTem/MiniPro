package com.minipro.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.minipro.conf.ErrorConfig;

import org.json.JSONObject;
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
import com.minipro.recommend.Recommend;
import com.minipro.recommend.UserBaseRecommend;
import com.minipro.service.param.IDParam;
import com.minipro.service.param.OpenIDParam;
import com.minipro.service.param.UserServiceParam;
import com.minipro.util.BaseUtil;
import com.minipro.util.CosUtil;
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
			rst.fail(ErrorConfig.INVALIDPARAM,"用户未登录\ninvalid parameter: openid is null");
			return rst;
		}
		System.out.println("openId"+openIdParam.getOpenId());
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
			rst.fail(ErrorConfig.INVALPARAM,"参数有误\ninvalid parameter: parameter is null");
			return rst;
		}
		
		User user=userMapper.getUserByUuid(p.getUuid());
		if(user==null){
			rst.fail(ErrorConfig.USERNOTEXIT,"用户不存在\ninvalid parameter: uuid is not exit");
			return rst;
		}
	    if(!userMapper.saveGameInform(p)){
			rst.fail(ErrorConfig.SERVERERROR,"数据出错\nerror");
	    	return rst;
	    }
	    
	    rst.success();
	    return rst;
	}
	
	
	public JSONResult create(UserServiceParam.CreateUserParam createUserParam){
		JSONResult rst=new JSONResult();
		rst.fail();
		if(createUserParam==null){
			rst.fail(ErrorConfig.INVALPARAM,"参数有误\ninvalid parameter: parameter is null");
			return rst;
		}
		
		rst.success();
	    return rst;
	}	
	public JSONResult createUser(UserServiceParam.CreateUserParam createUserParam){
		JSONResult rst=new JSONResult();
		rst.fail();
		
		if(createUserParam==null){
			rst.fail(ErrorConfig.INVALPARAM,"参数有误\ninvalid parameter: parameter is null");
			return rst;
		}
		User user=userMapper.getUserByOpenId(createUserParam.getOpenId());
		if(user!=null){
			String cause = String.format("用户已经注册 \nuser identified by openID: %s is registered",createUserParam.getOpenId());
			rst.fail(ErrorConfig.INVALIDPARAM, cause);
			return rst;
		}
		
		String uuid=BaseUtil.getUUID();
		user=userMapper.getUserByUuid(uuid);
		while(user!=null){
			uuid=BaseUtil.getUUID();
			user=userMapper.getUserByUuid(uuid);
		}
		
	    user=new User();

	    UpdateUtil.setValues(user, createUserParam);//赋值
	    user.setUuid(uuid);
	    String star=BaseUtil.getMonth(user.getBirthday());
	    if(star!=null)
	    	user.setStar(star);
	    if(!userMapper.createUser(user)){
	    	rst.fail(ErrorConfig.SERVERERROR,"数据保存失败!");
	    	return rst;
	    }
	    
	    rst.success();
	    rst.put("uuid", uuid);
	    return rst;
	}
	
	public JSONResult updateUser(UserServiceParam.UpdateUserParam updateUserParam){
		JSONResult rst=new JSONResult();
		rst.fail();
		
		if(updateUserParam==null){
			String cause = String.format("invalid operation\ninvalid request parameter, the parameter is %s",updateUserParam.toString());
			rst.fail(ErrorConfig.INVALIDPARAM, cause);
			return rst;
		}
		System.out.println(updateUserParam);
		
		User user=userMapper.getUserByUuid(updateUserParam.getUuid());
		if(user==null){//该用户不存在
			String cause = String.format("%s correspoding user is not found",updateUserParam.getUuid());
			rst.fail(ErrorConfig.NOTFOUND, "用户不存在\n"+cause);
			return rst;
		}
				
	    UpdateUtil.setValues(user, updateUserParam);//赋值
	    
	    System.out.println(user);
	    
	    if(!userMapper.updateUser(user)){
	    	rst.fail(ErrorConfig.SERVERERROR,"服务器出现错误,用户修改失败！");
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
			rst.fail(ErrorConfig.INVALPARAM,"参数有误\ninvalid parameter: parameter is null");
			return rst;
		}
		
		User user=userMapper.getUserByUuid(recommendParam.getUuid());
		if(user==null){//该用户不存在
			rst.fail(ErrorConfig.USERNOTEXIT,"该用户不存在");
			return rst;
		}
		RecommendSearchCriteria rsc=new RecommendSearchCriteria();
		UpdateUtil.setValues(rsc, recommendParam);
	
		
		Set<String> connectSets= (Set<String>) baseDao.select(CONNECTZSET+rsc.getUuid());
		Set<String> ulvSets=(Set<String>) baseDao.select(ULVZSET+rsc.getUuid());
		Set<String> lvdSets=(Set<String>) baseDao.select(LVDZSET+rsc.getUuid());
		
		if(connectSets==null)
			connectSets=new HashSet<String>();
		System.out.println("connectSets:"+connectSets);
		if(ulvSets==null)
			ulvSets=new HashSet<String>();
		Set<String> ignore=new HashSet<String>();
		ignore.addAll(connectSets);
		ignore.addAll(ulvSets);
		ignore.add(rsc.getUuid());
		ignore.addAll(lvdSets);
		if(lvdSets==null)
			lvdSets=new HashSet<String>();
		lvdSets.removeAll(ignore);
		ignore.addAll(lvdSets);
		
		rsc.setRemoveSets(ignore );
		
		
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
        // very important!!!!!!!!!!!!!!
        int candidateNum = chooseIds.size();
        int heroNum = heros.size();
        //heros_num + sex
        int categoryFeatureNum = heroNum + 1;
        //herosPlayCount + herosWinPer + age + (trank|warrior|assassin|master|auxiliary|shooter)_rate
        int numicFeatureNum = 2*heroNum + 7;
        
        
        //横坐标的映射
        Map<Integer,Integer> mapperHIds=new HashMap<Integer,Integer>();
        Map<Integer,Hero> mapperHeros=new HashMap<Integer,Hero>();
        for(int i=0;i<heros.size();i++){
        	mapperHIds.put(heros.get(i).getId(),i);
        	mapperHeros.put(i, heros.get(i));
        }
        String [] chooseIdStr=chooseIds.toArray(new String[] {});
        
        List<UserGameView> gameInforms=this.userMapper.getGameInform(chooseIdStr);
        
		//填充样本数据
        Map<Integer,User> mapperUser=new HashMap<Integer,User>(chooseIdStr.length);
        
        com.minipro.recommend.User []  candidates=new com.minipro.recommend.User[candidateNum];
        
        for(UserGameView ugv : gameInforms){
        	String userkey=ugv.getUuid();
        	//userindex: chooseIdStr 下标 
        	Integer userIndex=mapperIndex.get(userkey);
        	Integer heroKey=ugv.getHeroId();
        	if(mapperUser.get(userIndex)==null){
        		User u=new User();
        		UpdateUtil.setValues(u,ugv);
        		mapperUser.put(userIndex, u);//为
        	}
        	Integer heroIndex = -1;
        	if(heroKey!=null&&heroKey!=0){
        		heroIndex=mapperHIds.get(heroKey);
        	}
        		
        	if(userIndex==null){
        		
        		continue;
        	}
        	if(candidates[userIndex]==null){
        		candidates[userIndex] = new com.minipro.recommend.User();
        		
        		candidates[userIndex].setCategoryFeatures(new double[categoryFeatureNum]);
        		candidates[userIndex].setNumicFeatures(new double[numicFeatureNum]);
        		//set sex
        		candidates[userIndex].setCategoryFeatures(heroNum, ugv.getSex().equals("男") ? 0 : 1);
            	//set age
        		candidates[userIndex].setNumicFeatures(2 * heroNum, ugv.getAge());
            	//set trank_rate
        		candidates[userIndex].setNumicFeatures(2 * heroNum + 1, ugv.getTrankRate());
            	//set warrior_rate
        		candidates[userIndex].setNumicFeatures(2 * heroNum + 2, ugv.getWarriorRate());
            	//set assassin_rate
            	candidates[userIndex].setNumicFeatures(2 * heroNum + 3, ugv.getAssassinRate());
            	//master_rate
            	candidates[userIndex].setNumicFeatures(2 * heroNum + 4, ugv.getMasterRate());
            	//set auxiliary_rate
            	candidates[userIndex].setNumicFeatures(2 * heroNum + 5, ugv.getAuxiliaryRate());
            	//set shooter_rate
            	candidates[userIndex].setNumicFeatures(2 * heroNum + 6, ugv.getShooterRate());
        	}
        	if(heroIndex != -1)
        	{
        		candidates[userIndex].setCategoryFeatures(heroIndex, ugv.getIsOwn());
        		candidates[userIndex].setNumicFeatures(heroIndex, ugv.getTimeUse());
        		candidates[userIndex].setNumicFeatures(heroNum + heroIndex, ugv.getWinRate());
        	}
        	
        	
        	
        	//candidateCountOfUseHeros[userIndex][heroIndex]=ugv.getTimeUse();
        	//candidatePercentOfWinHeros[userIndex][heroIndex]=ugv.getWinRate();
        }
        com.minipro.recommend.User curUser = null;
        
        //填充个人信息
		
		List<UserGameView> ownGameInforms=this.userMapper.getOwnGameInform(rsc.getUuid());
		
		for(UserGameView ugv:ownGameInforms){
			Integer hkey=ugv.getHeroId();
			Integer hIndex = -1;
        	if(hkey!=null && hkey!=0){
        		hIndex=mapperHIds.get(hkey);
        	}
			if(curUser == null)
			{
				curUser = new com.minipro.recommend.User();
				curUser.setCategoryFeatures(new double[categoryFeatureNum]);
		        curUser.setNumicFeatures(new double[numicFeatureNum]);
				//set sex
	        	curUser.setCategoryFeatures(heroNum, ugv.getSex().equals("男") ? 0 : 1);
	        	//set age
	        	curUser.setNumicFeatures(2 * heroNum, ugv.getAge());
	        	//set trank_rate
	        	curUser.setNumicFeatures(2 * heroNum + 1, ugv.getTrankRate());
	        	//set warrior_rate
	        	curUser.setNumicFeatures(2 * heroNum + 2, ugv.getWarriorRate());
	        	//set assassin_rate
	        	curUser.setNumicFeatures(2 * heroNum + 3, ugv.getAssassinRate());
	        	//master_rate
	        	curUser.setNumicFeatures(2 * heroNum + 4, ugv.getMasterRate());
	        	//set auxiliary_rate
	        	curUser.setNumicFeatures(2 * heroNum + 5, ugv.getAuxiliaryRate());
	        	//set shooter_rate
	        	curUser.setNumicFeatures(2 * heroNum + 6, ugv.getShooterRate());
			}
			if(hIndex != -1){
				curUser.setCategoryFeatures(hIndex, ugv.getIsOwn());
				curUser.setNumicFeatures(hIndex, ugv.getTimeUse());
				curUser.setNumicFeatures(heroNum + hIndex, ugv.getWinRate());
			}
		}
		//set acceptCands
		//添加联系人相关信息
    	String [] constr=connectSets.toArray(new String[] {});
    	Map<String, com.minipro.recommend.User > mapperIDAndconUser=new HashMap<String,com.minipro.recommend.User >();
    	List<UserGameView> acceptCandsInfo= this.userMapper.getGameInform(constr);
    	for(UserGameView uv : acceptCandsInfo){
    		Integer hkey=uv.getHeroId();
    		Integer heroIndex = -1;
        	if(hkey!=null&&hkey!=0){
        		heroIndex=mapperHIds.get(hkey);
        	}
    		com.minipro.recommend.User acceptCandInfor=mapperIDAndconUser.get(uv.getUuid());
    		if(acceptCandInfor==null){
    			acceptCandInfor=new com.minipro.recommend.User();
    			acceptCandInfor.setCategoryFeatures(new double[categoryFeatureNum]);
        		acceptCandInfor.setNumicFeatures(new double[numicFeatureNum]);
        		//set sex
        		acceptCandInfor.setCategoryFeatures(heroNum, uv.getSex().equals("男") ? 0 : 1);
            	//set age
        		acceptCandInfor.setNumicFeatures(2 * heroNum, uv.getAge());
            	//set trank_rate
        		acceptCandInfor.setNumicFeatures(2 * heroNum + 1, uv.getTrankRate());
            	//set warrior_rate
        		acceptCandInfor.setNumicFeatures(2 * heroNum + 2, uv.getWarriorRate());
            	//set assassin_rate
        		acceptCandInfor.setNumicFeatures(2 * heroNum + 3, uv.getAssassinRate());
            	//master_rate
        		acceptCandInfor.setNumicFeatures(2 * heroNum + 4, uv.getMasterRate());
            	//set auxiliary_rate
        		acceptCandInfor.setNumicFeatures(2 * heroNum + 5, uv.getAuxiliaryRate());
            	//set shooter_rate
        		acceptCandInfor.setNumicFeatures(2 * heroNum + 6, uv.getShooterRate());
    			mapperIDAndconUser.put(uv.getUuid(), acceptCandInfor);
    		}
    		if(heroIndex != -1){
    			acceptCandInfor.setCategoryFeatures(heroIndex, uv.getIsOwn());
    			acceptCandInfor.setNumicFeatures(heroIndex, uv.getTimeUse());
    			acceptCandInfor.setNumicFeatures(heroNum + heroIndex, uv.getWinRate());
    		}
    	}
    	List<com.minipro.recommend.User> acceptCands=new ArrayList<com.minipro.recommend.User>(mapperIDAndconUser.values());
    	curUser.setCandsAccpeted(acceptCands);
    	
    	
    	//set rejectCands
    	//添加不喜欢的人的信息
    	constr=ulvSets.toArray(new String[] {});
    	mapperIDAndconUser=new HashMap<String,com.minipro.recommend.User >();
    	List<UserGameView> rejectCandsInfo= this.userMapper.getGameInform(constr);
    	for(UserGameView uv : rejectCandsInfo){
    		Integer hkey=uv.getHeroId();
    		Integer heroIndex = -1;
        	if(hkey!=null&&hkey!=0){
        		heroIndex=mapperHIds.get(hkey);
        	}
    		com.minipro.recommend.User rejectCandInfor=mapperIDAndconUser.get(uv.getUuid());
    		if(rejectCandInfor==null){
    			rejectCandInfor=new com.minipro.recommend.User();
    			rejectCandInfor.setCategoryFeatures(new double[categoryFeatureNum]);
        		rejectCandInfor.setNumicFeatures(new double[numicFeatureNum]);
        		//set sex
        		rejectCandInfor.setCategoryFeatures(heroNum, uv.getSex().equals("男") ? 0 : 1);
            	//set age
        		rejectCandInfor.setNumicFeatures(2 * heroNum, uv.getAge());
            	//set trank_rate
        		rejectCandInfor.setNumicFeatures(2 * heroNum + 1, uv.getTrankRate());
            	//set warrior_rate
        		rejectCandInfor.setNumicFeatures(2 * heroNum + 2, uv.getWarriorRate());
            	//set assassin_rate
        		rejectCandInfor.setNumicFeatures(2 * heroNum + 3, uv.getAssassinRate());
            	//master_rate
        		rejectCandInfor.setNumicFeatures(2 * heroNum + 4, uv.getMasterRate());
            	//set auxiliary_rate
        		rejectCandInfor.setNumicFeatures(2 * heroNum + 5, uv.getAuxiliaryRate());
            	//set shooter_rate
        		rejectCandInfor.setNumicFeatures(2 * heroNum + 6, uv.getShooterRate());
    			mapperIDAndconUser.put(uv.getUuid(), rejectCandInfor);
    		}
    		if(heroIndex != -1){
    			rejectCandInfor.setCategoryFeatures(heroIndex, uv.getIsOwn());
    			rejectCandInfor.setNumicFeatures(heroIndex, uv.getTimeUse());
    			rejectCandInfor.setNumicFeatures(heroNum + heroIndex, uv.getWinRate());
    		}
    	}
    	List<com.minipro.recommend.User> rejectCands=new ArrayList<com.minipro.recommend.User>(mapperIDAndconUser.values());
    	curUser.setCandsRejected(rejectCands);
    	
		Pair<List<Integer>, List<Integer>> result = Recommend.recommend(curUser, candidates);
		
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
			rst.fail(ErrorConfig.INVALIDPARAM, "invalid operation\n"+"invalid request parameter, "+cause);
			return rst;
		}
		
		if(markUserParam.getUuid().equals(markUserParam.getOuuid())){
			rst.fail(ErrorConfig.USERNOTEXIT,"目标uuid有误！");
			return rst;
		}
		
		User user=userMapper.getUserByUuid(markUserParam.getUuid());
		if(user==null){//该用户不存在
			String cause = String.format("%s corresponding user is not found", markUserParam.getUuid());
			rst.fail(ErrorConfig.INVALIDPARAM, "用户不存在\n"+"invalid request parameter, "+cause);
			return rst;
		}
		
		 user=userMapper.getUserByUuid(markUserParam.getOuuid());
		if(user==null){//该用户不存在
			String cause = String.format("%s corresponding user is not found", markUserParam.getUuid());
			rst.fail(ErrorConfig.INVALIDPARAM, "用户不存在\n"+"invalid request parameter, "+cause);
			return rst;
		}
		
		
		boolean result=false;
		//在三张表中检查，是否目标用户已经加入到某一张表中
		result=baseDao.isExit(LVZSET+markUserParam.getUuid(), markUserParam.getOuuid());
		if(result){
			rst.fail(ErrorConfig.ERROROPERATOR,"该用户已被你标记");
			return rst;
		}
		result=baseDao.isExit(CONNECTZSET+markUserParam.getUuid(), markUserParam.getOuuid());
		if(result){
			rst.fail(ErrorConfig.ERROROPERATOR,"该用户已是你的联系人");
			return rst;
		}
		result=baseDao.isExit(ULVZSET+markUserParam.getUuid(), markUserParam.getOuuid());
		if(result){
			rst.fail(ErrorConfig.ERROROPERATOR,"该用户已被你标记");
			return rst;
		}
		
		
		if(!markUserParam.isLike()){//标记为不喜欢
			if(result){
				String cause = String.format("%s corresponding user is not found", markUserParam.getUuid());
				rst.fail(ErrorConfig.INVALIDPARAM, "目标用户已被您标记为不喜欢！\n"+"invalid request parameter, "+cause);
			}else{
				baseDao.insertSortSet(ULVZSET+markUserParam.getUuid(),  markUserParam.getOuuid(), BaseUtil.getTimeStamp());
				rst.success();
			}
			return rst;
		}
	
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
			rst.fail(ErrorConfig.INVALIDPARAM, "目标用户已被您标记为喜欢\n"+"invalid request parameter, "+cause);
			return rst;
		}
		
		User user=userMapper.getUserByUuid(idParam.getUuid());
		if(user==null){//该用户不存在
			String cause = String.format("%s corresponding user is not found", idParam.getUuid());
			rst.fail(ErrorConfig.INVALIDPARAM, "用户不存在\n"+"invalid request parameter, "+cause);
			return rst;
		}
		
		Set<String> uuids=(Set<String>) baseDao.select(CONNECTZSET+idParam.getUuid());
		if(uuids==null||uuids.size()<=0){
			rst.success();
			rst.put("userList",new ArrayList<SUser>());
			return rst;
		}
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
			rst.fail(ErrorConfig.INVALIDPARAM, "操作错误\n"+cause);
			return rst;
		}
		
		User user=userMapper.getUserByUuid(idParam.getUuid());
		if(user==null){//该用户不存在
			String cause = String.format("%s corresponding user is not found", idParam.getUuid());
			rst.fail(ErrorConfig.INVALIDPARAM, "用户不存在\n"+"invalid request parameter, ");
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
			rst.fail(ErrorConfig.INVALIDPARAM, "操作错误\n"+cause);
			return rst;
		}
		
		User user=userMapper.getUserByUuid(imageParam.getUuid());
		if(user==null){//该用户不存在
			String cause = String.format("%s corresponding user is not found", imageParam.getUuid());
			rst.fail(ErrorConfig.INVALIDPARAM, "用户不存在\n"+"invalid request parameter, "+cause);
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
				rst.fail(ErrorConfig.SERVERERROR,"服务器内部出错，数据保存失败！");
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
			rst.fail(ErrorConfig.INVALIDPARAM, "操作错误\n"+cause);
			return rst;
		}
		User user=userMapper.getUserByUuid(imageParam.getUuid());
		if(user==null){//该用户不存在
			String cause = String.format("%s corresponding user is not found", imageParam.getUuid());
			rst.fail(ErrorConfig.INVALIDPARAM, "用户不存在\n"+"invalid request parameter, "+cause);
			return rst;
		}
		if(user.getImages()==null){
			rst.fail(ErrorConfig.ERROROPERATOR,"该用户无相册！");
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
			rst.fail(ErrorConfig.SERVERERROR,"服务器内部错误：数据修改失败！");
			return rst;
		}
		String result=CosUtil.del(imageParam.getImageUrl());
		JSONObject obj =new JSONObject(result);
		String code =obj.getString("code");
		if(!code.equals("0")){
			rst.fail("删除失败");
			return rst;
		}
		rst.success();
		return rst;
	}
	
	public JSONResult addHearOrVoice(UserServiceParam.HeadOrVoiceParam p){
		JSONResult rst=new JSONResult();
		rst.fail();
		
		if(p==null){
			rst.fail(ErrorConfig.INVALIDPARAM, "操作错误,invalid request parameter");
			return rst;
		}
		
		User user=userMapper.getUserByUuid(p.getUuid());
		if(user==null){//该用户不存在
			rst.fail(ErrorConfig.USERNOTEXIT,"该用户不存在");
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
				rst.fail(ErrorConfig.INVALPARAM,"参数有误,上传类型有误！");
				return rst;
		}
		if(!this.userMapper.updateUser(user)){
			rst.fail(ErrorConfig.SERVERERROR,"服务器出现错误,信息保存失败!");
			rst.put("timestamp", p.getTimestamp());
			return rst;
		}
		
		rst.success();
		rst.put("timestamp", p.getTimestamp());
		return rst;
	}
	
}
