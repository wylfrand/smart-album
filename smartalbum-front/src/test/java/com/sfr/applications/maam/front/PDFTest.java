package com.sfr.applications.maam.front;

import java.io.File;
import java.io.FileOutputStream;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import com.sfr.applications.maam.front.controller.GenerationPDFController;


@ContextConfiguration(locations = "classpath:spring/maam-context-test.xml")
public class PDFTest extends TestController{
        
        @Resource
        private GenerationPDFController generationPDFController;
       
        
        @Test
        public void testController() throws Exception{
            File f = new File("/testPdf.pdf");
            generationPDFController.testPdf("AME_1", new FileOutputStream(f));
        }
}