package com.mycompany.smartalbum.back.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mycompany.services.model.commun.InfosBeanForm;
import com.mycompany.services.utils.Constant;
import com.mycompany.smartalbum.back.helper.MenuItemsEnum;
import com.mycompany.smartalbum.back.utils.ViewEnum;


/**
 * Controller de gestion de la page d'accueil
 * 
 * @author <b>amvou</b>
 */
@Controller
@RequestMapping(value = "/home")
@SessionAttributes(value={Constant.SMARTALBUM_ALBUM_FORM,Constant.MENU_CURRENT_PAGE_ATTR, Constant.SMARTALBUM_INFOSPERSO_FORM})
public class HomeController extends ABaseController{
    
    private final static transient Logger LOG = LoggerFactory.getLogger(AlbumsController.class);
    
	
	/**
	 * Affiche la page d'accueil du back office smartAlbum
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String homeIndex(HttpServletRequest request,ModelMap model){
	    
	    backService.getCacheManager().putObjectInCache(Constant.MENU_CURRENT_PAGE_ATTR, MenuItemsEnum.ACCUEUIL);
	    
	    InfosBeanForm aInfosPersoForm = (InfosBeanForm) request.getSession().getAttribute(Constant.SMARTALBUM_INFOSPERSO_FORM);
	        if (aInfosPersoForm == null) {
	            request.getSession().setAttribute(Constant.SMARTALBUM_INFOSPERSO_FORM, new InfosBeanForm());
	            model.put(Constant.SMARTALBUM_INFOSPERSO_FORM, new InfosBeanForm());
	        } else {
	            model.put(Constant.SMARTALBUM_INFOSPERSO_FORM, aInfosPersoForm);
	        }
	    
	    return ViewEnum.HOME_VIEW.getView();
	}
	
	/**
	 * Affiche la page d'accueil du back office smartAlbum
	 */
	@RequestMapping(value = "/about", method = RequestMethod.GET)
	public String homeAbout(HttpServletRequest request,ModelMap model){
	    
	    backService.getCacheManager().putObjectInCache(Constant.MENU_CURRENT_PAGE_ATTR, MenuItemsEnum.ACCUEUIL);
	    
	    InfosBeanForm aInfosPersoForm = (InfosBeanForm) request.getSession().getAttribute(Constant.SMARTALBUM_INFOSPERSO_FORM);
	        if (aInfosPersoForm == null) {
	            request.getSession().setAttribute(Constant.SMARTALBUM_INFOSPERSO_FORM, new InfosBeanForm());
	            model.put(Constant.SMARTALBUM_INFOSPERSO_FORM, new InfosBeanForm());
	        } else {
	            model.put(Constant.SMARTALBUM_INFOSPERSO_FORM, aInfosPersoForm);
	        }
	    
	    return ViewEnum.HOME_VIEW.getView();
	}

    @Override
    protected Logger getLoger() {
        // TODO Auto-generated method stub
        return LOG;
    }
}