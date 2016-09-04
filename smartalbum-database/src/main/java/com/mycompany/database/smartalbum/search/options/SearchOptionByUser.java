/**
 * License Agreement.
 *
 * Rich Faces - Natural Ajax for Java Server Faces (JSF)
 *
 * Copyright (C) 2007 Exadel, Inc.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1 as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
 */
package com.mycompany.database.smartalbum.search.options;

import java.util.ArrayList;
import java.util.List;




import com.mycompany.database.smartalbum.exception.PhotoAlbumException;
import com.mycompany.database.smartalbum.model.User;
import com.mycompany.database.smartalbum.services.ISearchDao;
import com.mycompany.database.smartalbum.services.ISearchOption;

/**
 * Class, that encapsulate functionality related to search by user entity.
 * @author Aristide Mvou
 */
public class SearchOptionByUser extends ISearchOption {

    private static final long serialVersionUID = 8243451972508378734L;
    private static final String TEMPLATE = "/includes/search/result/userResult.xhtml";
	private static final String USERS_SEARCH_RESULT = "Users search result";
	private static final String USERS = "Users";
	private static final String LABEL = "Utilisateur";

	/* (non-Javadoc)
	 * @see com.mycompany.database.smartalbum.search.ISearchOption#getName()
	 */
	@Override
	public String getName() {
		return USERS;
	}

	/* (non-Javadoc)
	 * @see com.mycompany.database.smartalbum.search.ISearchOption#getSearchResultName()
	 */
	@Override
	public String getSearchResultName() {
		return USERS_SEARCH_RESULT;
	}

	/* (non-Javadoc)
	 * @see com.mycompany.database.smartalbum.search.ISearchOption#getSearchResultTemplate()
	 */
	@Override
	public String getSearchResultTemplate() {
		return TEMPLATE;
	}

	/* (non-Javadoc)
	 * @see com.mycompany.database.smartalbum.search.ISearchOption#search(org.richfaces.photoalbum.service.ISearchAction)
	 */
	@Override
	public void search(ISearchDao action, String q, boolean searchInMyAlbums, boolean searchInShared,User user) throws PhotoAlbumException {
		List<User> searchByUsers = action.searchByUsers(q, searchInMyAlbums, searchInShared,user);
		if(searchByUsers != null){
			setSearchResult(searchByUsers);
		}else{
			setSearchResult(new ArrayList<User>());
		}
	}
	
	 @Override
	    public String getLabel() {
	        // TODO Auto-generated method stub
	        return LABEL;
	    }
}