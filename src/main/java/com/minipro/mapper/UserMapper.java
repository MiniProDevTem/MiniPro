package com.minipro.mapper;

import java.util.List;

import com.minipro.criteria.RecommendSearchCriteria;
import com.minipro.entity.GameInform;
import com.minipro.entity.Hero;
import com.minipro.entity.SUser;
import com.minipro.entity.User;
import com.minipro.entity.UserGameView;

public interface UserMapper {

	public boolean createUser(User user);
	
	public boolean updateUser(User user);
	
	public User getUserByOpenId(String openId);
	
	public User getUserByUuid(String uuid);
	
	public List<SUser> getUserByUuids(String [] ids);
	
	public List<UserGameView> getGameInform(String [] ids);
	
	public List<String> getChooseUuid(RecommendSearchCriteria param);
	
	public int coutChooseUser(RecommendSearchCriteria param);

	public List<UserGameView> getOwnGameInform(String uuid);
	
	public List<User> getUserByDUuids(String [] ids);
	
	public int countHero();
	
	public List<Hero> getHeroList();
	
	public boolean saveGameInform(GameInform gif);
	
}
