package com.mycompany.smartalbum.back.controller;

import java.util.ArrayList;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mycompany.database.smartalbum.exception.PhotoAlbumException;
import com.mycompany.database.smartalbum.services.ISearchOption;
import com.mycompany.filesystem.utils.Constants;
import com.mycompany.services.utils.Constant;
import com.mycompany.smartalbum.back.commun.enumeration.SearchOptionEnum;
import com.mycompany.smartalbum.back.form.SearchForm;
import com.mycompany.smartalbum.back.helper.MenuItemsEnum;
import com.mycompany.smartalbum.back.utils.ViewEnum;


/**
 * Controller de gestion de la page d'accueil
 * 
 * @author <b>amvou</b>
 */
@Controller
@RequestMapping(value = "/searchController")
@SessionAttributes(value = {Constant.SMARTALBUM_SEARCH_FORM,Constant.MENU_CURRENT_PAGE_ATTR})
public class SearchController extends ABaseController{
    
    private final static transient Logger LOG = LoggerFactory.getLogger(AlbumsController.class);
    
    /**
     * Affiche la page d'accueil du back office smartAlbum
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String homeIndex(ModelMap model, @ModelAttribute(Constant.SMARTALBUM_SEARCH_FORM) SearchForm searchForm) {
        
        model.put(Constant.SMARTALBUM_SEARCH_FORM, getSearchForm());
        backService.getCacheManager().putObjectInCache(Constant.MENU_CURRENT_PAGE_ATTR, MenuItemsEnum.RECHERCHE);
        
        return ViewEnum.SEARCH_VIEW.getView();
    }
	
    /**
     * Affiche la page d'accueil du back office smartAlbum
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String searIndex(ModelMap model, @ModelAttribute(Constant.SMARTALBUM_SEARCH_FORM) SearchForm searchForm) {
        
        backService.getCacheManager().putObjectInCache(Constant.MENU_CURRENT_PAGE_ATTR, MenuItemsEnum.RECHERCHE);
        
        // S'il y a des erreurs, on les rajoutes au formulaire
         search(searchForm);
         searchForm.setErrors(backService.getErrorHandler().getErrors());
        return ViewEnum.SEARCH_VIEW.getView();
    }
    
    @RequestMapping(value = "/ajax/search/shelf", method = RequestMethod.POST)
    public String searchDetailShelf(ModelMap model,@RequestParam String id){
            model.put(Constant.SEARCH_SHELF_RESULT, backService.getShelfDBService().findShelfById(new Long(id)));
            return ViewEnum.SEARCH_SHELF_VIEW.getView();
    }
    
    @RequestMapping(value = "/ajax/search/album", method = RequestMethod.POST)
    public String searchDetailAlbum(ModelMap model,@RequestParam String id){
            model.put(Constant.SEARCH_ALBUM_RESULT, backService.getAlbumDBService().findAlbumById(new Long(id)));
            return ViewEnum.SEARCH_ALBUM_VIEW.getView();
    }
    
    @RequestMapping(value = "/ajax/search/image", method = RequestMethod.POST)
    public String searchDetailImage(ModelMap model,@RequestParam String id){
            model.put(Constant.SEARCH_IMAGE_RESULT, backService.getImageDBService().findImageById(new Long(id)));
            return ViewEnum.SEARCH_IMAGE_VIEW.getView();
    }
    
    @RequestMapping(value = "/ajax/search/user", method = RequestMethod.POST)
    public String searchDetailUser(ModelMap model,@RequestParam String id){
           model.put(Constant.SEARCH_USER_RESULT, backService.getUserDBService().findUserById(new Long(id)));
            return ViewEnum.SEARCH_USER_VIEW.getView();
    }
        
	
        /**
         * Method, that perform search, when user clicks by 'Find' button.
         */
        public void search(SearchForm form) {
            
            // On efface le resultat de l'ancienne recherche
                form.clearSearchResult();
                if(!form.isSearchOptionSelected()){
                        //If no getSearchForm().getOptions() selected
                    backService.getErrorHandler().addToErrors(Constants.SEARCH_NO_OPTIONS_ERROR);
                    return;
                }
                if(!form.isWhereSearchOptionSelected()){
                        //If both search in My and search is shared unselected
                        backService.getErrorHandler().addToErrors(Constants.SEARCH_NO_WHERE_OPTIONS_ERROR);
                        return;
                }
                // Process the selection
                form.processSelection();
                
                // parse query
                Iterator<ISearchOption> it = getSearchForm().getOptions().iterator();
                //Search by first keyword by default
                String selectedKeyword = form.getKeywords().get(0).trim();
                while (it.hasNext()) {
                        ISearchOption option = it.next();
                        try{
                                if (option.getSelected()) {
                                        option.search(backService.getSearchService(), selectedKeyword , form.isSeachInMyAlbums(), form.isSearchInShared(), backService.getCurrentUser(false));
                                }
                               
                        }catch(PhotoAlbumException e){
                                backService.getErrorHandler().addToErrors(option.getName() + ":" + e.getMessage());
                        }
                }
                
                SearchForm searchOptionsHolder = new SearchForm(new ArrayList<ISearchOption>(getSearchForm().getOptions()),form.isSeachInMyAlbums(), form.isSearchInShared());
                backService.getCacheManager().putObjectInCache(Constant.SMARTALBUM_SEARCH_FORM, searchOptionsHolder);
        }

        /**
         * Method, that perform search by particular phrase
         * @param keyword - keyword to search
         */
        public void search(SearchForm form, String keyword) {
                if(!form.isSearchOptionSelected()){
                        backService.getErrorHandler().addToErrors(Constants.SEARCH_NO_OPTIONS_ERROR);
                        return;
                }
                Iterator<ISearchOption> it = getSearchForm().getOptions().iterator();
                String selectedKeyword = keyword.trim();
                while (it.hasNext()) {
                        ISearchOption option = it.next();
                        try{
                                if (option.getSelected()) {
                                        option.search(backService.getSearchService(), selectedKeyword , getSearchForm().isSeachInMyAlbums(), getSearchForm().isSearchInShared(), backService.getCurrentUser(false));
                                }
                        }catch(PhotoAlbumException e){
                                backService.getErrorHandler().addToErrors(option.getName() + ":" + e.getMessage());
                        }
                }
        }
        
        
        public void toogleSelectOption(String selectedOption)
        {
            SearchOptionEnum aUserSelectedOption = SearchOptionEnum.fromValue(selectedOption);
            if(aUserSelectedOption!=null)
            {
                Iterator<ISearchOption> it = getSearchForm().getOptions().iterator();
                while (it.hasNext()) {
                        ISearchOption option = it.next();
                        
                        if (SearchOptionEnum.fromValue(option.getName()).equals(aUserSelectedOption)) {
                            option.setSelected(!option.getSelected());
                                break;
                        }
                }
            }
        }

        @Override
        protected Logger getLoger() {
            // TODO Auto-generated method stub
            return LOG;
        }
        
    

}