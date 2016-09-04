package com.mycompany.smartalbum.back.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mycompany.smartalbum.back.utils.ViewEnum;

@Controller
@RequestMapping("/menuController")
public class MenuController extends ABaseController{
    
    private final static transient Logger LOG = LoggerFactory.getLogger(AlbumsController.class);
    
    
    /**
     * Helps to show contact page
     * @param model
     * @return Contact page view
     */
    @RequestMapping(value = "/contact", method = RequestMethod.GET)
    public String showContact(ModelMap model) {
        
        return ViewEnum.COMMON_CONTACT_VIEW.getView();
    } 
    
    /**
     * Helps to show contact page
     * @param model
     * @return Contact page view
     */
    @RequestMapping(value = "/underconstruction", method = RequestMethod.GET)
    public String underconstruction(ModelMap model) {
        
        return ViewEnum.COMMON_UNDERCONSTRUCTION_VIEW.getView();
    }
    
    /**
     * Helps to show contact page
     * @param model
     * @return Contact page view
     */
    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public String aboutProject(ModelMap model) {
        
        return ViewEnum.COMMON_ABOUT_VIEW.getView();
    }

    @Override
    protected Logger getLoger() {
        // TODO Auto-generated method stub
        return LOG;
    }
}