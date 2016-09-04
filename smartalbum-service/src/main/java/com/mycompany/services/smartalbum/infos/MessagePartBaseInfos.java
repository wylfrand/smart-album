package com.mycompany.services.smartalbum.infos;

import java.io.Serializable;

public abstract class MessagePartBaseInfos implements Serializable{
	
private static final long serialVersionUID = 1L;
    
    private Long id;
    
    String htmlPart;
    
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
		MessagePartBaseInfos other = (MessagePartBaseInfos) obj;
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
