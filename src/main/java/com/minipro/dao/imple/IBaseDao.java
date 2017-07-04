package com.minipro.dao.imple;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.minipro.dao.BaseDao;
@Repository
public class IBaseDao implements BaseDao {
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Override
	public boolean insertString(String key, String value) {
		try{
			redisTemplate.opsForValue().set(key, value);
			return true;
		}catch (Exception e) {  
            return false;  
        }  
	}

	@Override
	public boolean insertList(String key, Object value) {
		 try {  
	            redisTemplate.opsForList().leftPush(key, value);     
	            return true;  
	      } catch (Exception e) {   
	            return false;  
	      }  
	}

	@Override
	public boolean insertSet(String key, Object value) {
		try {  
            redisTemplate.opsForSet().add(key, value);     
            return true;  
		} catch (Exception e) {   
            return false;  
		}  
	}

	@Override
	public boolean insertHash(String key, String sonKey, Object value) {
		 try {  
	            redisTemplate.opsForHash().put(key, sonKey, value);     
	            return true;  
	        } catch (Exception e) {  
	            return false;  
	        }  
	}

	@Override
	public boolean insertSortSet(String key, Object value, double score) {
		try {  
            redisTemplate.opsForZSet().add(key, value,score);     
            return true;  
		} catch (Exception e) {   
            return false;  
		} 
	}

	@Override
	public Object select(String key,int totals) {
		try {
			DataType type = redisTemplate.type(key);
			if (DataType.NONE == type) {
				return null;
			} else if (DataType.STRING == type) {
				return redisTemplate.opsForValue().get(key);
			} else if (DataType.LIST == type) {
				return redisTemplate.opsForList().range(key, 0, -1);
			} else if (DataType.HASH == type) {
				return redisTemplate.opsForHash().entries(key);
			}else if(DataType.SET==type){
				return redisTemplate.opsForSet().members(key);
			}else if(DataType.ZSET==type){
				return redisTemplate.opsForZSet().range(key,0,-1);
			}else{
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Object select(String key ) {
		return select(key,100);
	}

	@Override
	public boolean delete(List<String> keys) {
		try {  
			redisTemplate.delete(keys);    
            return true;  
		} catch (Exception e) {   
            return false;  
		} 
	}

	@Override
	public boolean delete(String key) {
		try {  
			redisTemplate.delete(key);     
            return true;  
		} catch (Exception e) {   
            return false;  
		} 
	}

	@Override
	public boolean deleteFromHash(String key, String sonKey) {
		//redisTemplate.opsForHash().d;
		return false;
	}

	@Override
	public Set<Object> differ(String key, Set<String> otherkey) {
		Set<Object> result=redisTemplate.opsForSet().difference(key,otherkey);
		return result;
	}

	@Override
	public Set<Object> differ(String key, String otherkey) {
		Set<Object> result=redisTemplate.opsForSet().difference(key,otherkey);
		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T selectFromHash(String key, String sonkey, Class<T> c) {
		T t=(T) redisTemplate.opsForHash().get(key, sonkey);
		return t;
	}

	@Override
	public boolean isExit(String key,String sonkey) {
		DataType type = redisTemplate.type(key);
		if (DataType.NONE == type) {
			return false;
		} else if(DataType.SET==type){
			return redisTemplate.opsForSet().isMember(key, sonkey);
		}else if(DataType.ZSET==type){
			return redisTemplate.opsForZSet().score(key, sonkey)==null?false:true;
		}else{
			return false;
		}
	}
	
	
}
