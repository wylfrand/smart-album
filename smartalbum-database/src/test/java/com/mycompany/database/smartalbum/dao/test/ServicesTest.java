package com.mycompany.database.smartalbum.dao.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.mycompany.database.smartalbum.model.Album;
import com.mycompany.database.smartalbum.model.Image;
import com.mycompany.database.smartalbum.model.Shelf;
import com.mycompany.database.smartalbum.model.User;
import com.mycompany.database.smartalbum.services.IAlbumDao;
import com.mycompany.database.smartalbum.services.IImageDao;
import com.mycompany.database.smartalbum.services.IShelfDao;
import com.mycompany.database.smartalbum.services.IUserDao;
import com.mycompany.database.smartalbum.transaction.ReadonlyTransaction;

@ContextConfiguration(locations = "classpath:spring/smartalbum-database-context-test.xml")
@TransactionConfiguration(transactionManager = "transactionManager")
public class ServicesTest extends AbstractTransactionalJUnit4SpringContextTests{
	
	private static transient final Logger log = LoggerFactory.getLogger(ServicesTest.class);

	@Resource
	private IAlbumDao albumDBService;

	@Resource
	private IImageDao imageDBService;

	@Resource
	private IShelfDao shelfDBService;
	
	@Resource
	private IUserDao userDBService;
	
	
	@Test
	@Rollback(value = true)
	@ReadonlyTransaction
	public void listDatabase(){
		log.debug("");
		albumDBService.print();
		
		
		for(Album myAlbum: albumDBService.findAll())
		{
			log.debug(myAlbum.getDescription());
		}
		
		for(Image myImage: imageDBService.findAll())
		{
			log.debug(myImage.getDescription());
		}
		
		for(Shelf myShelf: shelfDBService.findAll())
		{
			log.debug(myShelf.getDescription());
		}
		
		for(User myUser: userDBService.findAll())
		{
			log.debug(myUser.getEmail());
		}
	}
}