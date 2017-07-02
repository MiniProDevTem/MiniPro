package com.minipro.controller;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.context.WebApplicationContext;

import com.minipro.entity.JSONResult;
import com.minipro.util.JsonUtil;

public class AbstractController {

	protected static final String RESPONSE_DATA_KEY = "data";

	public String invokeService(String namespace, String name) {
		return invokeService(namespace, name, null);
	}

	/**
	 * 
	 * @param namespace
	 * @param name
	 * @param param
	 * @return
	 */
	public String invokeService(String namespace, String name, String param) {
		if (name==null) {
			return null;
		}
		if (param==null) {
			param = "{}";
		} 
		String result=null;
		try {
			result = invokeLocal(namespace, name, param);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (result==null) {
			JSONResult rst=new JSONResult();
			rst.fail("出错");
			result = rst.toJson();
		}
		return result;
		
	}
	
	private String invokeLocal(String namespace, String name, String param) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		if (namespace==null) {
			return null;
		}
		String serviceName = namespace.substring(0,1).toLowerCase() + namespace.substring(1) + "Service";
		Object service = MiniContext.getBean(serviceName);
		if (service == null) {
			return null;
		}
		if (param==null){
			param = "{}";
		}
//		LoginInfo loginInfo = (LoginInfo) HarameSession.getUser();
//		Admin authentication = null;
//		Site site = null;
//		if (loginInfo != null && loginInfo.isValid()) {
//			authentication = loginInfo.getUser();
//			site = loginInfo.getSite();
//		}
		// find the method
		Method method = null;
		for (Method m : service.getClass().getDeclaredMethods()) {
			if (m.getName().equals(name)) {
				method = m;
				break;
			}
		}
		JSONResult jsonResult = new JSONResult();
		if (method == null) {
			jsonResult.fail();
			return jsonResult.toJson();
		}
		
		List<Object> args = new ArrayList<Object>();
		
		try {
			
			// find the user
			for (Class<?> clazz : method.getParameterTypes()) {
//				if (clazz.equals(Admin.class)) {
//					if (authentication == null) {
//						jsonResult.setStatus(JSONResultStatus.LOGIN);
//						return jsonResult.toJson();
//					}
//					args.add(authentication);
//				}else if (clazz.equals(Site.class)){
//					if (site == null){
//						jsonResult.setStatus(JSONResultStatus.LOGIN);
//						return jsonResult.toJson();
//					}
//					args.add(site);
//				}else{
					Object p = JsonUtil.toObject(param, clazz);
					if (p == null){
						jsonResult.fail("参数有误");
						return jsonResult.toJson();
					}
					args.add(p);
//				}
			}
			// invoke the method
			if (method.getParameterTypes().length == 0) {
				// no parameter
				jsonResult = (JSONResult)method.invoke(service);
			} else if (method.getParameterTypes().length == 1){
				jsonResult = (JSONResult)method.invoke(service, args.get(0));
			} else if (method.getParameterTypes().length == 2){
				jsonResult = (JSONResult)method.invoke(service, args.get(0), args.get(1));
			} else if (method.getParameterTypes().length == 3){
				jsonResult = (JSONResult)method.invoke(service, args.get(0), args.get(1), args.get(2));
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.fail();
			return jsonResult.toJson();
		}
		return jsonResult.toJson();
	}
}
