package com.mycompany.database.smartalbum.services;

import java.util.List;

import com.mycompany.database.smartalbum.exception.PhotoAlbumException;
import com.mycompany.database.smartalbum.model.MessageHTML;
import com.mycompany.database.smartalbum.utils.Entities;
/**
 * Interface for manipulating with messageHTM entity
 *
 * @author Aristide M'vou
 */

public interface IMessageHTML {

        void addMessageHTML(MessageHTML messageHTML) throws PhotoAlbumException;
        
        void deleteMessageHTML(MessageHTML messageHTM) throws PhotoAlbumException;
        
        void editMessageHTML(MessageHTML messageHTM) throws PhotoAlbumException;
        
        void resetMessageHTML(MessageHTML messageHTM);
        
        void mergeMessageHTML(MessageHTML messageHTML);
        
        List<MessageHTML> findAll();
        
        MessageHTML findMessageHTMLById(Long messageHTMId);
        
		void editMessageHTML(MessageHTML aNewMessageHTML, Entities image);

		void save(MessageHTML aNewMessageHTML);

		void deleteMessageHTML(MessageHTML message, Entities image);
}
