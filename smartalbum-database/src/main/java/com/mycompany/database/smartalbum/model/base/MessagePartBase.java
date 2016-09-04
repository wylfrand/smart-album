package com.mycompany.database.smartalbum.model.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.mycompany.database.smartalbum.model.ABuisnessObject;

@MappedSuperclass
public class MessagePartBase extends ABuisnessObject<Long> implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue
    private Long id;
    
    @Column(name = "HTML_PART", nullable = true, length = 512)
    String htmlPart;
    
	/** Code langue associé au message - Français par défaut **/
	@Column(name = "PART_ORDER", nullable = true, length = 40)
	private String order; 
	
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    /**
     * @return the htmlPart
     */
    public String getHtmlPart() {
        return htmlPart;
    }
    /**
     * @param htmlPart the htmlPart to set
     */
    public void setHtmlPart(String htmlPart) {
        this.htmlPart = htmlPart;
    }
    
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "MessagePart [id=" + id + ", htmlPart(size)=" + htmlPart.length() + ", order="+order+"]";
    }
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((order == null) ? 0 : order.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MessagePartBase other = (MessagePartBase) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (order == null) {
			if (other.order != null)
				return false;
		} else if (!order.equals(other.order))
			return false;
		return true;
	}
    
}