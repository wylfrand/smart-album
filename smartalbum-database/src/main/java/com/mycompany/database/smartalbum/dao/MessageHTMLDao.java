package com.mycompany.database.smartalbum.dao;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.mycompany.database.smartalbum.exception.PhotoAlbumException;
import com.mycompany.database.smartalbum.model.MessageHTML;
import com.mycompany.database.smartalbum.repository.IMessageHTMLJpaRepository;
import com.mycompany.database.smartalbum.services.IMessageHTML;
import com.mycompany.database.smartalbum.transaction.CommitTransaction;
import com.mycompany.database.smartalbum.utils.Entities;

@Component("messageHTMLDBService")
public class MessageHTMLDao extends AbastractDao<MessageHTML, Long> implements IMessageHTML {
    
    private final static transient Logger log = LoggerFactory.getLogger(MessageHTMLDao.class);
    
    @Resource
    private IMessageHTMLJpaRepository messageJpaDBService;
    
    /**
     * Persist messageHTML entity to database
     * 
     * @param messageHTML - messageHTML to add
     * @throws PhotoAlbumException
     */
    @CommitTransaction
    public void addMessageHTML(MessageHTML messageHTML) throws PhotoAlbumException {
            messageHTML.getImage().addOrReplaceMessageHTML(messageHTML);
            messageJpaDBService.saveAndFlush(messageHTML);
    }
    

    /**
     * Remove messageHTML entity from database
     * 
     * @param messageHTML - messageHTML to delete
     * @throws PhotoAlbumException
     */
    public void deleteMessageHTML(MessageHTML messageHTML) throws PhotoAlbumException {
        
        if(messageHTML == null) return;
        try {
            // Remove reference from album
            messageHTML.getImage().removeMessageHTML(messageHTML);
            messageJpaDBService.delete(messageHTML);
            messageJpaDBService.flush();
        } catch (Exception e) {
            log.error("Le message html d'id {} n'a pas pu être supprimé",messageHTML.getId(),e);
            throw new PhotoAlbumException(e.getMessage());
        }
    }
    
    /**
     * Synchronize state of messageHTML entity with database
     * 
     * @param messageHTML - messageHTML to Synchronize
     * @throws PhotoAlbumException
     */
    public void editMessageHTML(MessageHTML messageHTML) throws PhotoAlbumException {
        try {
            messageHTML.getImage().addOrReplaceMessageHTML(messageHTML);
            messageJpaDBService.saveAndFlush(messageHTML);
        } catch (Exception e) {
            log.error("Impossible de mettre à jour le message HTML",e);
            throw new PhotoAlbumException(e.getMessage());
        }
    }
    
    /**
     * Refresh state of given messageHTML
     * 
     * @param messageHTML - messageHTML to Synchronize
     */
    public void resetMessageHTML(MessageHTML messageHTML) {
        messageJpaDBService.flush();
    }
    
    /**
     * Refresh state of given messageHTML
     * 
     * @param messageHTML - messageHTML to Synchronize
     */
    public void mergeMessageHTML(MessageHTML messageHTML) {
        messageJpaDBService.saveAndFlush(messageHTML);
    }
    
    /*
     * (non-Javadoc)
     */
    @Override
    protected Class<MessageHTML> getBoClass() {
        return MessageHTML.class;
    }
    
    @Override
    protected Logger getLog() {
        return log;
    }
    
    /**
     * Persist messageHTML entity to database
     * 
     * @param messageHTMLId - messageHTML to add
     * @return
     */
    @Override
    public MessageHTML findMessageHTMLById(Long messageHTMLId) {
        return messageJpaDBService.findOne(messageHTMLId);
    }

	@Override
	public void editMessageHTML(MessageHTML aNewMessageHTML, Entities type) {
		if(Entities.ALBUM.equals(type)){
			aNewMessageHTML.getAlbum().getMessagesHTML().add(aNewMessageHTML);
		}
		else if(Entities.IMAGE.equals(type)){
			aNewMessageHTML.getImage().getMessagesHTML().add(aNewMessageHTML);
		}
		messageJpaDBService.saveAndFlush(aNewMessageHTML);
	}

	@Override
	public void deleteMessageHTML(MessageHTML aNewMessageHTML, Entities type) {
		if(Entities.ALBUM.equals(type)){
			aNewMessageHTML.getAlbum().setMessagesHTML(null);
		}
		else if(Entities.IMAGE.equals(type)){
			aNewMessageHTML.getImage().getMessagesHTML().remove(aNewMessageHTML);
		}
		messageJpaDBService.delete(aNewMessageHTML);
		
	}


	@Override
	public void save(MessageHTML aNewMessageHTML) {
		messageJpaDBService.saveAndFlush(aNewMessageHTML);
		
	}


	@Override
	public List<MessageHTML> findAll() {
		return messageJpaDBService.findAll();
	}
    
}
