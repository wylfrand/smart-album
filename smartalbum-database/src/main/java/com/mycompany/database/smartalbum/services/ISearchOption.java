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
package com.mycompany.database.smartalbum.services;

import java.io.Serializable;
import java.util.List;




import com.google.common.collect.Lists;
import com.mycompany.database.smartalbum.exception.PhotoAlbumException;
import com.mycompany.database.smartalbum.model.User;

/**
 * Abstract class, that represent base functionality for particular search option(album, shelf, etc..)
 * @author Aristide Mvou
 */
public abstract class ISearchOption implements Serializable{
	
    private static final long serialVersionUID = 2043181311616177782L;

    private boolean selected = true;
	
	private List<?> searchResult; 
	/**
	 * Abstract method, that return name of particular search option. This name used in UI as header of rich:tab. Must be implemented in sub-classes
	 *
	 * @return name
	 */
	public abstract String getName();
	
	/**
         * Abstract method, that return label to print for particular search option. This label used in UI as header. Must be implemented in sub-classes
         *
         * @return label
         */
        public abstract String getLabel();
	
	/**
	 * Abstract method, that return description of particular search option. This description used in UI as header of page with search result. Must be implemented in sub-classes
	 *
	 * @return description of search option
	 */
	public abstract String getSearchResultName();
	
	/**
	 * Abstract method, that perform search in given option. Must be implemented in sub-classes
	 *
	 * @param action - action will be performed
	 * @param searchQuery - query to search
	 * @param searchInMyAlbums - is search in users albums will be performed
	 * @param searchInShared - is search in shared albums will be performed
	 * @throws PhotoAlbumException - in case of wrong search parameters
	 */
	public abstract void search(ISearchDao action, String searchQuery, boolean searchInMyAlbums, boolean searchInShared,User user) throws PhotoAlbumException;
	
	public boolean getSelected() {
		return selected;
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	@Override
	public String toString() {
		return getName();
	}

	/**
	 * Abstract method, that return template to render of particular search option. Must be implemented in sub-classes
	 *
	 * @return template to render
	 */
	public abstract String getSearchResultTemplate();
	
	public List<?> getSearchResult() {
	    if(searchResult == null)
	    {
	        searchResult = Lists.newArrayList();  
	    }
	    return searchResult;
	}

	public void setSearchResult(List<?> searchResult) {
		this.searchResult = searchResult;
	}
	
	public void clearSearchResult()
	{
	    searchResult = Lists.newArrayList();
	}
	
	
}