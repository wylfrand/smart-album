package com.mycompany.database.smartalbum.services;

import com.mycompany.database.smartalbum.exception.PhotoAlbumException;
import com.mycompany.database.smartalbum.model.MessageHTML;
import com.mycompany.database.smartalbum.model.MessagePart;
/**
 * Interface for manipulating with messageHTM entity
 *
 * @author Aristide M'vou
 */

public interface IMessagePart {

        void addMessagePart(MessagePart messagePart) throws PhotoAlbumException;
        
        void deleteMessagePart(MessagePart messagePart) throws PhotoAlbumException;
        
        void removeAllMessagePart(MessageHTML message) throws PhotoAlbumException;
}
