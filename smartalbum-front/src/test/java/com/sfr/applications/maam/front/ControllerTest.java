package com.sfr.applications.maam.front;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.ui.ModelMap;

import com.mycompany.service.exception.AmeManagementException;
import com.sfr.applications.maam.front.controller.RestitutionAmeController;

@ContextConfiguration(locations = "classpath:spring/maam-context-test.xml")
@TransactionConfiguration(transactionManager = "maamTransactionManager")
public class ControllerTest extends TestController {
    
    @Resource
    private RestitutionAmeController restitutionAmeController;
    
    @Test
    public void doNothing() {
	
    }
    
    // @Test
    @Rollback(true)
    public void testController() {
	ModelMap model = new ModelMap();
	
	try {
	    restitutionAmeController.index(true,true, model,null);
	} catch (AmeManagementException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
}