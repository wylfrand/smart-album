package com.mycompany.smartalbum.back.form;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;
import com.mycompany.database.smartalbum.model.Album;
import com.mycompany.database.smartalbum.model.User;



public class ShelfForm implements Serializable
{
		private static final long serialVersionUID = -5903954994531619003L;
	
        private Long id=-1L;

        private String name;

        private String description;

        private User owner;

        private boolean shared;

        private Date created;
        
        private boolean editFromInplace;
        
        private List<Album> albums;
        

        /**
         * @return the id
         */
        public Long getId() {
            return id;
        }

        /**
         * @param id the id to set
         */
        public void setId(Long id) {
            this.id = id;
        }

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * @param name the name to set
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * @return the description
         */
        public String getDescription() {
            return description;
        }

        /**
         * @param description the description to set
         */
        public void setDescription(String description) {
            this.description = description;
        }

        /**
         * @return the owner
         */
        public User getOwner() {
            return owner;
        }

        /**
         * @param owner the owner to set
         */
        public void setOwner(User owner) {
            this.owner = owner;
        }

        /**
         * @return the shared
         */
        public boolean isShared() {
            return shared;
        }

        /**
         * @param shared the shared to set
         */
        public void setShared(boolean shared) {
            this.shared = shared;
        }

        /**
         * @return the created
         */
        public Date getCreated() {
            return created;
        }

        /**
         * @param created the created to set
         */
        public void setCreated(Date created) {
            this.created = created;
        }

        /**
         * @return the editFromInplace
         */
        public boolean isEditFromInplace() {
            return editFromInplace;
        }

        /**
         * @param editFromInplace the editFromInplace to set
         */
        public void setEditFromInplace(boolean editFromInplace) {
            this.editFromInplace = editFromInplace;
        }

        /**
         * @return the albums
         */
        public List<Album> getAlbums() {
            
            if(albums == null)
            {
                albums = Lists.newArrayList();  
            }
            return albums;
        }

        /**
         * @param albums the albums to set
         */
        public void setAlbums(List<Album> albums) {
            this.albums = albums;
        }
}