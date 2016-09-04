package com.mycompany.filesystem.service.impl;

import javax.annotation.Resource;

import net.sf.ehcache.Cache;

/**
 * Représente un service de base pour l'environnement SmartAlbum
 * 
 * @author amv
 */
public abstract class SmartAlbumService{

	@Resource(name = "myCompanyCache")
	protected Cache myCompanyCache;
}