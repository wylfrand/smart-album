package com.mycompany.smartalbum.back.controller;import java.util.List;import javax.servlet.http.HttpServletResponse;import org.slf4j.Logger;import org.slf4j.LoggerFactory;import org.springframework.stereotype.Controller;import org.springframework.ui.ModelMap;import org.springframework.web.bind.annotation.ModelAttribute;import org.springframework.web.bind.annotation.PathVariable;import org.springframework.web.bind.annotation.RequestMapping;import org.springframework.web.bind.annotation.RequestMethod;import org.springframework.web.bind.annotation.RequestParam;import org.springframework.web.bind.annotation.ResponseBody;import org.springframework.web.bind.annotation.SessionAttributes;import com.google.common.collect.Lists;import com.mycompany.database.smartalbum.exception.PhotoAlbumException;import com.mycompany.database.smartalbum.model.Album;import com.mycompany.database.smartalbum.model.Shelf;import com.mycompany.database.smartalbum.model.User;import com.mycompany.database.smartalbum.utils.Constants;import com.mycompany.services.smartalbum.infos.UserInfos;import com.mycompany.services.utils.Constant;import com.mycompany.services.utils.RetourReponse;import com.mycompany.smartalbum.back.form.ShelfForm;import com.mycompany.smartalbum.back.helper.MenuItemsEnum;import com.mycompany.smartalbum.back.service.impl.SynchroManager;import com.mycompany.smartalbum.back.utils.ViewEnum;@Controller@RequestMapping("/shelvesController")@SessionAttributes(value = {Constant.SMARTALBUM_SHELF_FORM,Constant.MENU_CURRENT_PAGE_ATTR})public class ShelvesController extends ABaseController {        private final static transient Logger LOG = LoggerFactory.getLogger(ShelvesController.class);        /***     * URL: /shelvesController/publicShelves     *      * @param model     * @return     */    @RequestMapping(value = "/publicShelves", method = RequestMethod.GET)    public String getPublicShelves(ModelMap model,@ModelAttribute(Constant.SMARTALBUM_SHELF_FORM) ShelfForm shelfForm) {    	List<Shelf> shelfList = getPredefinedShelves();        model.put(Constant.SMARTALBUM_PUBLIC_SHELVES, shelfList);        backService.getCacheManager().putObjectInCache(Constant.MENU_CURRENT_PAGE_ATTR, MenuItemsEnum.ETAGERES_PUBLIQUES);        return ViewEnum.PUBLIC_SHELVES_VIEW.getView();    }        /***     * URL: /shelvesController/myShelves     *      * @param model     * @return     */    @RequestMapping(value = "/myShelves", method = RequestMethod.GET)    public String getUserShelves(ModelMap model,@ModelAttribute(Constant.SMARTALBUM_SHELF_FORM) ShelfForm shelfForm) {    	User currentUser = backService.getCurrentUser(false);    	UserInfos userInfos = mapper.map(currentUser, UserInfos.class);    	userInfos.update();        if(currentUser!=null){            model.put(Constant.SMARTALBUM_USER_SHELVES, currentUser.getShelves());        }        else{            model.put(Constant.SMARTALBUM_USER_SHELVES, null);        }        Object albumObj  = backService.getCacheManager().getObjectFromCache(				Constant.SMARTALBUM_PHOTOS_CURRENT_ALBUM);        Album album = null;                if(albumObj != null){        	album = (Album)albumObj;        }		// Check, that album was not deleted recently.		if (album != null) {			if (!backService.getFileSystemService().isDirectoryPresent(					album.getPath())) {				model.put(						"exceptionStack",						"Impossible de trouver l'album "								+ album.getPath()								+ " sur le filesystème! Veuillez contacter votre administrateur.");				return ViewEnum.SMARTALBUM_VIEW_ERROR.getView();			}		}		model.put(Constant.SMARTALBUM_PHOTOS_CONTROLLER,				Constant.SMARTALBUM_PHOTOS_ALBUMCONTROLLER);		model.put(Constant.SMARTALBUM_PHOTOS_CURRENT_ALBUM, album);        backService.getCacheManager().putObjectInCache(Constant.MENU_CURRENT_PAGE_ATTR, MenuItemsEnum.MES_ETAGERES);        return ViewEnum.USER_SHELVES_VIEW.getView();    }        /***     * URL: /shelvesController/shelfDetails Get the shelf details     *      * @param model     * @return     */    @RequestMapping(value = "/shelfDetails/{shelfId}", method = RequestMethod.GET)    public String getshelfDetail(@PathVariable("shelfId") Long shelfId, ModelMap model) {                Shelf currentShelf = backService.getShelfDBService().findShelfById(shelfId);                List<Shelf> shelves = Lists.newArrayList();        shelves.add(currentShelf);        model.put(Constant.SMARTALBUM_CURRENT_SHELF, shelves);        return ViewEnum.SHELF_DETAIL_VIEW.getView();    }        /***     * URL: /shelvesController/synchronyseAll     *      * @param model     * @return refreshed page     */    @RequestMapping(value = "/synchronyseAll", method = RequestMethod.GET)    public String synchronyse(ModelMap model) {                SynchroManager manager = new SynchroManager();        manager.setBackService(backService);        manager.setUser(backService.getCurrentUser(true));        new Thread(manager).start();        //manager.run();                return ViewEnum.SMARTALBUM_REDIRECT_TO_PUBLICSHELVES.getView();    }        @RequestMapping(value = "/createShelf", method = RequestMethod.POST)    public String createShelf(HttpServletResponse response,                              @ModelAttribute(Constant.SMARTALBUM_SHELF_FORM) ShelfForm aShelfForm) {                aShelfForm.setId(-1L);        RetourReponse retour = backService.addOrModifyShelf(aShelfForm);        if (retour.getResult()) {            resetShelfForm();            if(aShelfForm.isShared()){                return ViewEnum.SMARTALBUM_REDIRECT_TO_PUBLICSHELVES.getView();            }            else{                return ViewEnum.SMARTALBUM_REDIRECT_TO_MYSHELVES.getView();            }        }        return ViewEnum.SMARTALBUM_VIEW_ERROR.getView();    }        /**     * Method, that invoked when user click 'Edit shelf' button or by inplaceInput component. Only registered users can edit shelves.     *      * @param shelf - shelf to edit     * @param editFromInplace - indicate whether edit process was initiated by inplaceInput component     */    @RequestMapping(value = "/modifyShelf", method = RequestMethod.POST)        public String modifyShelf(HttpServletResponse response, @ModelAttribute(Constant.SMARTALBUM_SHELF_FORM) ShelfForm aShelfForm) {            RetourReponse retour = backService.addOrModifyShelf(aShelfForm);            if (retour.getResult()) {                if(aShelfForm.isShared()){                    return ViewEnum.SMARTALBUM_REDIRECT_TO_PUBLICSHELVES.getView();                }                else{                    return ViewEnum.SMARTALBUM_REDIRECT_TO_MYSHELVES.getView();                }            }            return ViewEnum.SMARTALBUM_VIEW_ERROR.getView();        }        /**     * Method, that invoked when user click 'Delete shelf' button. Only registered users can delete shelves.     *      * @param image - shelf to delete     */	public boolean deleteShelf(Shelf shelf) {		if (shelf != null) {			String pathToDelete = shelf.getPath();			try {				backService.getShelfDBService().deleteShelf(shelf);			} catch (Exception e) {				backService.getErrorHandler().addToErrors(						Constants.SHELF_DELETING_ERROR);				try {					backService.getUserDBService().refreshEmtityManager(shelf.getOwner());				} catch (PhotoAlbumException e1) {					LOG.error("Impossiblde de rafraichir les données BDD", e1);					return false;				}				return false;			}			backService.getFileSystemService().onShelfDeleted(shelf,pathToDelete);		}		return true;	}		        @RequestMapping(value = "/ajax/deleteShelf", method = RequestMethod.POST)    public @ResponseBody    RetourReponse deleteAlbum(HttpServletResponse response,                    @RequestParam Long shelfId, ModelMap model) {            RetourReponse rep = new RetourReponse();            Shelf shelf = backService.getShelfDBService().findShelfById(shelfId);           if( deleteShelf(shelf))           {              rep.setResult(false);              return rep;                          }            return RetourReponse.ok();    }        @ModelAttribute(Constant.SMARTALBUM_SHELF_FORM)    public ShelfForm createDefaultClient() {    	    	return new ShelfForm();    }    /**     * This method used to populate 'pre-defined shelves' tree     *      * @return List of predefined shelves     */    public List<Shelf> getPredefinedShelves() {        return backService.getShelfDBService().getPredefinedShelves();    }    @Override    protected Logger getLoger() {        return LOG;    }    }