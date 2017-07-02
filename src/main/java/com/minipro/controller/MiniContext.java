package com.minipro.controller;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class MiniContext implements ApplicationContextAware{
	
	private static ApplicationContext APPLICATION_CONTEXT;

	
	public static <T> T getBean(Class<T> clazz) {
		return getBean(clazz, true);
	}

	
	public static <T> T getBean(Class<T> clazz, boolean require) {
		if (require) {
			return APPLICATION_CONTEXT.getBean(clazz);
		}
		try {
			return APPLICATION_CONTEXT.getBean(clazz);
		} catch (NoSuchBeanDefinitionException e) {
			return null;
		}
	}

	
	public static Object getBean(String name) {
		return getBean(name, true);
	}

	
	public static Object getBean(String name, boolean require) {
		if (require) {
			return APPLICATION_CONTEXT.getBean(name);
		}
		try {
			return APPLICATION_CONTEXT.getBean(name);
		} catch (NoSuchBeanDefinitionException e) {
			return null;
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		APPLICATION_CONTEXT = applicationContext;
	}
}
