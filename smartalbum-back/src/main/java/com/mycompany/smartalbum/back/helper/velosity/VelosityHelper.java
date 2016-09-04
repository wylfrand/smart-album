package com.mycompany.smartalbum.back.helper.velosity;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.app.event.implement.IncludeRelativePath;
import org.apache.velocity.runtime.RuntimeConstants;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

@Component
public class VelosityHelper {
	
	
    @Resource(name = "smartAlbumVelocityEngine")
    private VelocityEngine velocityEngine;

	public String  findJSPFByTemplate(final TemplateEnum template){
		String templateString = StringUtils.EMPTY;
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(RuntimeConstants.EVENTHANDLER_INCLUDE, IncludeRelativePath.class.getName());
		properties.put(RuntimeConstants.FILE_RESOURCE_LOADER_CACHE, String.valueOf(true));
		templateString = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
				"/template/"+template.name()+".vm", "UTF-8", properties);
		return templateString;
	}
	
	public String  findImageEntryByTemplate(final TemplateEnum template, final Map<String, Object> imageProperties){
		String templateString = StringUtils.EMPTY;
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(RuntimeConstants.FILE_RESOURCE_LOADER_CACHE, String.valueOf(true));
		properties.putAll(imageProperties);
		velocityEngine.setProperty(RuntimeConstants.EVENTHANDLER_INCLUDE, IncludeRelativePath.class.getName());
		templateString = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
				"/template/"+template.name()+".vm", "UTF-8", properties);
		return templateString;
	}

}
