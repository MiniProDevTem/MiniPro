package com.minipro.dao;

import java.util.List;
import java.util.Set;

public interface BaseDao {
	//插入string
	boolean insertString(String key,String value);
	//插入list
	boolean insertList(String key, Object value) ;
	
	boolean insertSet(String key,Object value);
	
	boolean insertHash(String key, String sonKey ,Object value);
	
	boolean insertSortSet(String key,Object value,double score);
	
	Object select(String key);
	
	<T>T selectFromHash(String key,String sonkey,Class<T> t);
	
	Object select(String key,int totals);
	
	boolean delete(List<String> keys);
	
	boolean delete(String key);
	
	boolean deleteFromHash(String key,String sonKey);

	Set<Object> differ(String key,Set<String> otherkey);//交集
	
	Set<Object> differ(String key,String otherkey);//交集
	
	boolean isExit(String key,String sonkey);
	
	Object getRandomFromSet(String key);
}
