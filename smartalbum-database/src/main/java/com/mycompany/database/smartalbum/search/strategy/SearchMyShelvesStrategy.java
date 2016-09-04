/**
 * License Agreement.
 *
 *  JBoss RichFaces - Ajax4jsf Component Library
 *
 * Copyright (C) 2007  Exadel, Inc.
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
package com.mycompany.database.smartalbum.search.strategy;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;


import com.mycompany.database.smartalbum.model.User;
import com.mycompany.database.smartalbum.services.ISearchStrategy;
import com.mycompany.database.smartalbum.utils.Constants;
/**
 * Strategy to retrieve shelves, that belongs to user, that perform search
 *
 * @author Aristide M'vou
 */
public class SearchMyShelvesStrategy implements ISearchStrategy {
	/**
	 * Create query to retrieve shelves, that belongs to user, that perform search
	 *
	 * @param em - entityManager
	 * @param params - map of additional params for this query
	 * @param searchQuery - string to search
	 * @return List of shelves, that belongs to user, that perform search
	 */
	public Query getQuery(EntityManager em, Map<String, Object> params,
			String searchQuery) {
		Query query = em.createQuery(Constants.SEARCH_SHELVES_QUERY + Constants.SEARCH_SHELF_MY_ADDON);
		query.setParameter(Constants.QUERY_PARAMETER, Constants.PERCENT + searchQuery.toLowerCase() + Constants.PERCENT);
		User user = (User)params.get(Constants.USER_PARAMETER);
		if(null == user){
			return null;
		}
		query.setParameter(Constants.LOGIN_PARAMETER, user.getLogin());
		return query;
	}
}
