package com.sfr.applications.maam.front.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextHolder implements ApplicationContextAware{

	/**
	 * Contexte Spring
	 */
	private static ApplicationContext applicationContext;
	
	/*
	 * (non-Javadoc)
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException{
		SpringContextHolder.applicationContext = applicationContext;
	}
	

	// GETTER / SETTER
	public static ApplicationContext getApplicationContext(){
		return applicationContext;
	}
}