package com.mycompany.database.smartalbum.dao;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.mycompany.database.smartalbum.exception.PhotoAlbumException;
import com.mycompany.database.smartalbum.model.MessageHTML;
import com.mycompany.database.smartalbum.model.MessagePart;
import com.mycompany.database.smartalbum.repository.IMessagePartJpaRepository;
import com.mycompany.database.smartalbum.services.IMessagePart;
import com.mycompany.database.smartalbum.transaction.CommitTransaction;

@Component("messagePartDBService")
public class MessagePartDao extends AbastractDao<MessagePart, Long> implements IMessagePart {
    
    private final static transient Logger log = LoggerFactory.getLogger(MessagePartDao.class);
    
    @Resource
    private IMessagePartJpaRepository messagePartJpaDBService;
    
    /**
     * Remove messageHTML entity from database
     * 
     * @param messageHTML - messageHTML to delete
     * @throws PhotoAlbumException
     */
    @CommitTransaction
    public void deleteMessagePart(MessagePart messagePart) throws PhotoAlbumException {
        try {
            // Remove reference from album
            messagePart.getMessageHTML().removePart(messagePart);
            messagePart.setMessageHTML(null);
            messagePartJpaDBService.delete(messagePart);
            messagePartJpaDBService.flush();

        } catch (Exception e) {
            log.error("Le message html n'a pas pu être supprimé car une partie du message ne peut être effacé",e);
            throw new PhotoAlbumException(e.getMessage());
        }
    }
    
    
    
    /**
     * Remove messageHTML entity from database
     * 
     * @param messageHTML - messageHTML to delete
     * @throws PhotoAlbumException
     */
    public void removeAllMessagePart(MessageHTML message) throws PhotoAlbumException {
            // Remove reference from album
            if(message!=null && message.getMessageLines()!=null && !message.getMessageLines().isEmpty())
            {
                for(MessagePart part : message.getMessageLines())
                {
                    part.setMessageHTML(null);
                    messagePartJpaDBService.delete(part);
                }
                message.getMessageLines().clear();
                messagePartJpaDBService.flush();
            }
    }
    
    /**
     * Persist messagePart entity to database
     * 
     * @param messagePart - MessagePart to add
     * @throws PhotoAlbumException
     */
    public void addMessagePart(MessagePart messagePart) throws PhotoAlbumException {
        try {
            messagePart.getMessageHTML().addMessagePart(messagePart);
            messagePartJpaDBService.saveAndFlush(messagePart);
        } catch (Exception e) {
            log.error("Impossible de rajouter un message HTML",e);
            throw new PhotoAlbumException(e.getMessage());
        }
    }
    
    @Override
    protected Class<MessagePart> getBoClass() {
        return MessagePart.class;
    }
    
    @Override
    protected Logger getLog() {
        return log;
    }



	@Override
	public List<MessagePart> findAll() {
		return messagePartJpaDBService.findAll();
	}
}
