package com.mycompany.database.smartalbum.dao.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.mycompany.database.smartalbum.exception.PhotoAlbumException;
import com.mycompany.database.smartalbum.model.Album;
import com.mycompany.database.smartalbum.model.User;
import com.mycompany.database.smartalbum.search.options.SearchOptionByAlbum;
import com.mycompany.database.smartalbum.search.options.SearchOptionByImage;
import com.mycompany.database.smartalbum.search.options.SearchOptionByShelf;
import com.mycompany.database.smartalbum.search.options.SearchOptionByTag;
import com.mycompany.database.smartalbum.search.options.SearchOptionByUser;
import com.mycompany.database.smartalbum.services.ISearchDao;
import com.mycompany.database.smartalbum.services.ISearchOption;
import com.mycompany.database.smartalbum.services.IUserDao;
import com.mycompany.database.smartalbum.transaction.ReadonlyTransaction;
import com.mycompany.database.smartalbum.utils.Constants;

@ContextConfiguration(locations = "classpath:spring/smartalbum-database-context-test.xml")
@TransactionConfiguration(transactionManager = "transactionManager")
public class ServiceSearchTest extends AbstractTransactionalJUnit4SpringContextTests{
	
	private static transient final Logger log = LoggerFactory.getLogger(ServiceSearchTest.class);
	
	private SearchInformationHolder searchOptionsHolder;
	
	@Resource
	ISearchDao searchService;
	
	@Resource
	private IUserDao userDBService;

	ISearchOption selectedOption;

	List<ISearchOption> options;
	
	String searchQuery = "";
	
	String selectedKeyword;
	
	List<String> keywords;
	
	boolean seachInMyAlbums = false;
	
	boolean searchInShared = true;
	
	private User user; 
	private List<String> parse(String searchQuery2) {
		return Arrays.asList(searchQuery2.split(Constants.COMMA));
	}
	
	/**
	 * Method, that perform search, when user clicks by 'Find' button.
	 */
	@Test
	@Rollback(value = true)
	@ReadonlyTransaction
	public void search() {
		
		log.debug("");
		searchOptionsHolder = null;
		//If no options selected
		keywords = new ArrayList<String>();
		keywords.add("album");
		// parse query
		keywords = parse(searchQuery);
		Iterator<ISearchOption> it = options.iterator();
		//Search by first keyword by default
		selectedKeyword = keywords.get(0).trim();
                while (it.hasNext()) {
                    ISearchOption option = it.next();
                    try {
                        if (option.getSelected()) {
                            option.search(searchService, selectedKeyword, seachInMyAlbums, searchInShared, user);
                        }
                        for (Object test : option.getSearchResult()) {
                            if (test instanceof Album) {
                                Album alb = (Album) test;
                                log.debug(alb.toString());
                            }
                        }
                    }catch (PhotoAlbumException e) {
                        log.error("Recherche impossible : ",e);
                    }
                    
                }
		searchOptionsHolder = new SearchInformationHolder(new ArrayList<ISearchOption>(options),seachInMyAlbums, searchInShared);
	}
	
	/**
	 * Default constructor. During instantiation populate in field options all possible search options
	 * kl
	 */
	@PostConstruct
	public void init() {
		options = new ArrayList<ISearchOption>();
		options.add(new SearchOptionByShelf());
		options.add(new SearchOptionByAlbum());
		options.add(new SearchOptionByImage());
		options.add(new SearchOptionByUser());
		options.add(new SearchOptionByTag());
		user = userDBService.findAll().get(0);
	}

	public boolean equals(Object obj) {
		return searchOptionsHolder.equals(obj);
	}

	public List<ISearchOption> getOptions() {
		return searchOptionsHolder.getOptions();
	}

//	public int hashCode() {
//		return searchOptionsHolder.hashCode();
//	}

	public boolean isSeachInMyAlbums() {
		return searchOptionsHolder.isSeachInMyAlbums();
	}

	public boolean isSearchInShared() {
		return searchOptionsHolder.isSearchInShared();
	}

//	public String toString() {
//		return searchOptionsHolder.toString();
//	}

}

class SearchInformationHolder{
	SearchInformationHolder(List<ISearchOption> options, boolean seachInMyAlbums, boolean searchInShared){
		this.options = options;
		this.seachInMyAlbums = seachInMyAlbums;
		this.searchInShared = searchInShared;
	}
	
	List<ISearchOption> options;
	
	boolean seachInMyAlbums;
	
	boolean searchInShared;

	public List<ISearchOption> getOptions() {
		return options;
	}

	public boolean isSeachInMyAlbums() {
		return seachInMyAlbums;
	}

	public boolean isSearchInShared() {
		return searchInShared;
	}
}
