package com.minipro.dao;

import java.util.List;
import java.util.Set;

public interface BaseDao {
	//插入string
	public boolean insertString(String key,String value);
	//插入list
	public boolean insertList(String key, Object value) ;
	
	public boolean insertSet(String key,Object value);
	
	public boolean insertHash(String key, String sonKey ,Object value);
	
	public boolean insertSortSet(String key,Object value,double score);
	
	public Object select(String key);
	
	public <T>T selectFromHash(String key,String sonkey,Class<T> t);
	
	public Object select(String key,int totals);
	
	public boolean delete(List<String> keys);
	
	public boolean delete(String key);
	
	public boolean deleteFromHash(String key,String sonKey);

	public Set<Object> differ(String key,Set<String> otherkey);//交集
	
	public Set<Object> differ(String key,String otherkey);//交集
	
	public boolean isExit(String key,String sonkey);
}
