package com.mycompany.smartalbum.back.form;

import java.io.Serializable;
import java.util.Date;



public class ShelfInfos implements Serializable
{
		private static final long serialVersionUID = -5903954994531619003L;
	
        private Long id=-1L;

        private String name;

        private String description;

        private boolean shared;

        private Date created;
        
        private boolean editFromInplace;
        
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

		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((id == null) ? 0 : id.hashCode());
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			return result;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ShelfInfos other = (ShelfInfos) obj;
			if (id == null) {
				if (other.id != null)
					return false;
			} else if (!id.equals(other.id))
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			return true;
		}
}