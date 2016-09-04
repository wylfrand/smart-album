package com.mycompany.services.smartalbum.vo;

public class MessagePartVO extends MessagePartBaseVO {
    
    private static final long serialVersionUID = 1L;
    
    private MessageHTMLVO messageHTML;
    
    /**
     * @return the messageHTML
     */
    public MessageHTMLVO getMessageHTML() {
        return messageHTML;
    }
    /**
     * @param messageHTML the messageHTML to set
     */
    public void setMessageHTML(MessageHTMLVO messageHTML) {
        this.messageHTML = messageHTML;
    }
    
    
}
