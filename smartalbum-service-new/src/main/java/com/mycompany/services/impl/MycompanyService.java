package com.mycompany.services.impl;

import javax.annotation.Resource;

import net.sf.ehcache.Cache;

/**
 * Représente un service de base pour l'environnement MAAM
 * 
 * @author amv
 */
public abstract class MycompanyService{

	@Resource(name = "myCompanyCache")
	protected Cache myCompanyCache;
}