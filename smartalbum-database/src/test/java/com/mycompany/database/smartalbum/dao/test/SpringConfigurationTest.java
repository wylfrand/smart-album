package com.mycompany.database.smartalbum.dao.test;

import java.util.Arrays;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = "classpath:spring/smartalbum-database-context-test.xml")
public class SpringConfigurationTest extends AbstractJUnit4SpringContextTests{
    
    private static final Logger log = LoggerFactory.getLogger(SpringConfigurationTest.class);
     

    @Test
    public void dumpBeans(){
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        log.info("LISTE BEAN SPRING **************DEBUT*****************");
        for(String beanName:beanNames){
            try{
                Object bean = applicationContext.getBean(beanName);
                
                log.debug("---");
                log.debug("{}:{}", beanName, bean);
            }catch (Exception e){
                // bean abstract or advised
            	log.debug("Error in dumpBeans (	bean abstract or advised): {}", e.getMessage());
            }
        }
        
        log.info("LISTE BEAN SPRING *************FIN******************");
    }
}