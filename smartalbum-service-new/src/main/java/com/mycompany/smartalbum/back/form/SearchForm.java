package com.mycompany.smartalbum.back.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;
import com.mycompany.database.smartalbum.services.ISearchOption;
import com.mycompany.filesystem.utils.Constants;

public class SearchForm implements Serializable {
    
    /**
	 * 
	 */
    private static final long serialVersionUID = 7329503100388730168L;
    
    public SearchForm(List<ISearchOption> options, boolean seachInMyAlbums, boolean searchInShared) {
        this.options = options;
        this.seachInMyAlbums = seachInMyAlbums;
        this.searchInShared = searchInShared;
    }
    
    List<ISearchOption> options;
    
    private int resultLimit = 20;
    
    boolean seachInMyAlbums;
    
    boolean searchInShared;
    
    ISearchOption selectedOption;
    
    private String searchQuery;
    
    private List<String> errors = Lists.newArrayList();
    
    List<String> keywords = new ArrayList<String>();
    
    boolean isOptionSelected() {
        return selectedOption != null;
    }
    
    public List<ISearchOption> getOptions() {
        
        if (options == null) {
            options = Lists.newArrayList();
        }
        return options;
    }
    
    public boolean isResultExist() {
        for (ISearchOption option : getOptions()) {
            if (option.getSelected() && option.getSearchResult() != null && option.getSearchResult().size() > 0) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isWhereSearchOptionSelected() {
        return seachInMyAlbums || searchInShared;
}
    
    public boolean isSearchOptionSelected() {
        boolean isOptionSelected = false;
        for (ISearchOption i : options) {
            if (i.getSelected()) {
                isOptionSelected = true;
                break;
            }
        }
        return isOptionSelected;
    }
    
    public void clearSearchResult()
    {
        for (ISearchOption i : options) {
            if (i.getSelected()) {
                i.clearSearchResult();
            }
        }
        
        getErrors().clear();
    }
    
    /**
     * Method, invoked when user select or unselect search option.
     */
    private List<String> parse(String searchQuery2) {
            return Arrays.asList(searchQuery2.split(Constants.COMMA));
    }
    
    /**
     * Method, used to construct criteria string, to represent this string in UI.
     */
    public String getCriteriaString(){
            StringBuilder s = new StringBuilder();
            for(ISearchOption option:getOptions()) {
                    if(option.getSelected()){
                            s.append(option.getName() + Constants.COMMA + " ");
                    }
            }
            if (s.length() >= 2) {
                    s.delete(s.length() - 2, s.length());
            }
            return s.toString();
    }
    
    /**
     * Method, invoked when user select or unselect search option.
     */
    public void processSelection() {
            Iterator<ISearchOption> it = options.iterator();
            while (it.hasNext()) {
                    ISearchOption option = it.next();
                    if (option.getSelected()) {
                            selectedOption = option;
                            break;
                    }
            }
    }
    
    public boolean isSeachInMyAlbums() {
        return seachInMyAlbums;
    }
    
    public boolean isSearchInShared() {
        return searchInShared;
    }
    
    /**
     * @return the selectedOption
     */
    public ISearchOption getSelectedOption() {
        return selectedOption;
    }
    
    /**
     * @param selectedOption the selectedOption to set
     */
    public void setSelectedOption(ISearchOption selectedOption) {
        this.selectedOption = selectedOption;
    }

    /**
     * @return the searchQuery
     */
    public String getSearchQuery() {
        return searchQuery;
    }

    /**
     * @param searchQuery the searchQuery to set
     */
    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    /**
     * @param seachInMyAlbums the seachInMyAlbums to set
     */
    public void setSeachInMyAlbums(boolean seachInMyAlbums) {
        this.seachInMyAlbums = seachInMyAlbums;
    }

    /**
     * @param searchInShared the searchInShared to set
     */
    public void setSearchInShared(boolean searchInShared) {
        this.searchInShared = searchInShared;
    }

    /**
     * @return the keywords
     */
    public List<String> getKeywords() {
        
        keywords = parse(searchQuery);
        return keywords;
    }

    /**
     * @param keywords the keywords to set
     */
    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    /**
     * @return the resultLimit
     */
    public int getResultLimit() {
        return resultLimit;
    }

    /**
     * @param resultLimit the resultLimit to set
     */
    public void setResultLimit(int resultLimit) {
        this.resultLimit = resultLimit;
    }

    /**
     * @return the errors
     */
    public List<String> getErrors() {
        return errors;
    }

    /**
     * @param errors the errors to set
     */
    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
    
}
