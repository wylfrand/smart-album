package com.sfr.applications.maam.front.utils;

import org.springframework.web.util.HtmlUtils;

public class ToolTip {
	
	 	/** id of the tooltip, mandatory attribute **/
		private String id;
	    
		/** css class of the tooltip, optional  attribute **/
	    private String ttclass = "aa";
	    
	    /** data-placement of the tooltip, optional  attribute **/
	    private String dataPlacement = "right";
	    
	    /** href link to initialize the tooltip, optional  attribute **/
	    private String href = "javascript:void(0);";
	    
	    /** data-original-title of the tooltip, mandatory  attribute **/
	    private String dataOriginalTitle;
	    
	    /** the message that will be shown at first glance before the tooltip is printed, mandatory  attribute **/
	    private String dataBrokenText;
	    
	    private String defaultBaliseName = "span";
	    
	    private static final String separator = " ";
	    
	    public ToolTip(String id,String ttclass,String dataPlacement,String href,String dataOriginalTitle, String dataBrokenText, String defaultBaliseName)
	    {
	    	this.id = id;
	    	this.ttclass = ttclass;
	    	this.dataPlacement = dataPlacement;
	    	this.href = href;
	    	this.dataOriginalTitle = dataOriginalTitle;
	    	this.dataBrokenText = dataBrokenText;
	    	this.defaultBaliseName = defaultBaliseName;
	    }
	    
	    public ToolTip(String id,String dataOriginalTitle, String dataBrokenText, String defaultBaliseName)
	    {
	    	this.id = id;
	    	this.dataOriginalTitle = dataOriginalTitle;
	    	this.dataBrokenText = dataBrokenText;
	    	this.defaultBaliseName = defaultBaliseName;
	    }
	    
	    public String toString()
	    {
	    	StringBuffer sbResult = new StringBuffer();
	    	// On creer la balise
	    	sbResult.append("<")
	    			.append(defaultBaliseName)
	    			.append(separator);
	    	
	    	sbResult.append("id='");
	        sbResult.append(id);
	        sbResult.append("'");
	        sbResult.append(separator);
	        
	        sbResult.append("rel='tooltip'");
	        sbResult.append(separator);

	        sbResult.append("data-placement='");
	        sbResult.append(dataPlacement);
	        sbResult.append("'");
	        sbResult.append(separator);
	        
	        sbResult.append("class='");
	        sbResult.append(ttclass);
	        sbResult.append("'");
	        sbResult.append(separator);
	        
	        sbResult.append("href='");
	        sbResult.append(href);
	        sbResult.append("'");
	        sbResult.append(separator);
	        
	        sbResult.append("data-original-title=\"");
	        sbResult.append(HtmlUtils.htmlEscape(dataOriginalTitle));
	        sbResult.append("\">");
	        
	        sbResult.append(dataBrokenText);
	        sbResult.append("</");
	        sbResult.append(defaultBaliseName)
			.append(">");
	    	
	    	return sbResult.toString();
	    }

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getTtclass() {
			return ttclass;
		}

		public void setTtclass(String ttclass) {
			this.ttclass = ttclass;
		}

		public String getDataPlacement() {
			return dataPlacement;
		}

		public void setDataPlacement(String dataPlacement) {
			this.dataPlacement = dataPlacement;
		}

		public String getHref() {
			return href;
		}

		public void setHref(String href) {
			this.href = href;
		}

		public String getDataOriginalTitle() {
			return dataOriginalTitle;
		}

		public void setDataOriginalTitle(String dataOriginalTitle) {
			this.dataOriginalTitle = dataOriginalTitle;
		}

		public String getDefaultBaliseName() {
			return defaultBaliseName;
		}

		public void setDefaultBaliseName(String defaultBaliseName) {
			this.defaultBaliseName = defaultBaliseName;
		}

		public String getDataBrokenText() {
			return dataBrokenText;
		}

		public void setDataBrokenText(String dataBrokenText) {
			this.dataBrokenText = dataBrokenText;
		}

}
