package com.mycompany.database.smartalbum.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.mycompany.database.smartalbum.model.base.MessagePartBase;

@Entity
@Table(name = "MESSAGE_PART")
public class MessagePart extends MessagePartBase {
    
    private static final long serialVersionUID = 1L;
    
    @NotNull
    @ManyToOne(cascade = CascadeType.REMOVE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(nullable = true)
    private MessageHTML messageHTML;
    
    /**
     * @return the messageHTML
     */
    public MessageHTML getMessageHTML() {
        return messageHTML;
    }
    /**
     * @param messageHTML the messageHTML to set
     */
    public void setMessageHTML(MessageHTML messageHTML) {
        this.messageHTML = messageHTML;
    }
    
    
}
