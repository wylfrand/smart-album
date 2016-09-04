package com.mycompany.smartalbum.back.helper.velosity;

import org.eclipse.core.runtime.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * Unit tests for class.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/smartalbum-context-test.xml")
public class VelosityHelperTest {
	
	/**
	 * Service de création d'alerte
	 */
	
	/** The service. */
	@Autowired
	private VelosityHelper velocityHelper;
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		String template = velocityHelper.findJSPFByTemplate(TemplateEnum.DATATABLE_CB_IMG_VM);
		System.out.println(template);
		Assert.isNotNull(template);
	}

}
