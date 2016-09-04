package com.mycompany.database.smartalbum.model.base;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.google.common.collect.Maps;
import com.mycompany.database.smartalbum.model.ABuisnessObject;
import com.mycompany.database.smartalbum.model.MessagePart;

/**
 * @author amvou
 */
@MappedSuperclass
public abstract class MessageHTMLBase extends ABuisnessObject<Long> implements Serializable {

	private static final long serialVersionUID = 3258575758867829856L;

	@Id
	@GeneratedValue
	private Long id;

	/** Code de l'application associé au message **/
	@Column(name = "APP_CODE", length = 128)
	private String app_code;

	/**
	 * La table liée
	 */
	@Column(name = "MESSAGE_TYPE", nullable = true, length = 40)
	private String message_type;

	/** Code langue associé au message - Français par défaut **/
	@Column(name = "CODE_LANG", nullable = true, length = 40)
	private String lang_code;
	
	/** LOGIN de l'utilisateur ayant fait la dernière mise à jour **/
	@NotNull
	@Column(name = "USER_LOGIN", length = 512)
	private String userLogin;

	/** Date de dernière mise à jour du message **/
	@Temporal(TemporalType.TIMESTAMP)
	private Date update_date;

	/**
	 * Dans la BD chaque message est découpé en plusieurs ligne pour que la
	 * taille du message ne soit pas limité
	 **/
	@Column(name = "LINE_SIZE", nullable = true, length = 40)
	private int line_size;

	@Transient
	private String htmlDescription;

	/**
	 * Add comment to this image.
	 * 
	 * @param comment
	 *            - comment to add
	 */
	public void addMessagePart(MessagePart messagePart) {
		if (messagePart == null) {
			throw new IllegalArgumentException("Null messagePart!");
		}
		getMessageLinesBase().add(messagePart);
	}

	public void updateHTMLDescription() {
		StringBuilder finalMessage = new StringBuilder();
		Map<String,String> orderedParts = Maps.newHashMap();
		int newOrder = 1;
		for (MessagePartBase part : getMessageLinesBase()) {
			if(part.getOrder()!=null)
			{
				orderedParts.put(part.getOrder(), part.getHtmlPart());
			}
			else
			{
				orderedParts.put(newOrder+++"", part.getHtmlPart());
			}
		}
		for(int i=0;i<orderedParts.size();i++)
		{
			finalMessage.append(orderedParts.get(i+1+""));
		}
		htmlDescription = finalMessage.toString();
	}
	
	/**
	 * This method remove image from collection of images of album
	 * 
	 * @param image
	 *            - image to remove
	 */
	public void removePart(MessagePart part) {
		if (part == null) {
			throw new IllegalArgumentException("Null part");
		}
		if (!part.getMessageHTML().equals(this)) {
			throw new IllegalArgumentException(
					"This MessageHTML does not contain this part!");
		}
		part.setMessageHTML(null);
		getMessageLinesBase().remove(part);
	}

	/**
	 * @return the app_code
	 */
	public String getApp_code() {
		return app_code;
	}

	/**
	 * @param app_code
	 *            the app_code to set
	 */
	public void setApp_code(String app_code) {
		this.app_code = app_code;
	}

	/**
	 * @return the message_type
	 */
	public String getMessage_type() {
		return message_type;
	}

	/**
	 * @param message_type
	 *            the message_type to set
	 */
	public void setMessage_type(String message_type) {
		this.message_type = message_type;
	}

	/**
	 * @return the userLogin
	 */
	public String getUserLogin() {
		return userLogin;
	}

	/**
	 * @param userLogin
	 *            the userLogin to set
	 */
	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	/**
	 * @return the update_date
	 */
	public Date getUpdate_date() {
		return update_date;
	}

	/**
	 * @param update_date
	 *            the update_date to set
	 */
	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}

	/**
	 * @return the line_size
	 */
	public int getLine_size() {
		return line_size;
	}

	/**
	 * @param line_size
	 *            the line_size to set
	 */
	public void setLine_size(int line_size) {
		this.line_size = line_size;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the lang_code
	 */
	public String getLang_code() {
		return lang_code;
	}

	/**
	 * @param lang_code
	 *            the lang_code to set
	 */
	public void setLang_code(String lang_code) {
		this.lang_code = lang_code;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MessageHTML [id=" + id + ", app_code=" + app_code
				+ ", message_type=" + message_type + ", lang_code=" + lang_code
				+ ", htmlDescription=" + htmlDescription + "]";
	}

	/**
	 * @return the htmlDescription
	 */
	public String getHtmlDescription() {

		updateHTMLDescription();
		return htmlDescription.replaceAll("\n", "");
	}

	/**
	 * @param htmlDescription
	 *            the htmlDescription to set
	 */
	public void setHtmlDescription(String htmlDescription) {

		this.htmlDescription = htmlDescription;
	}
	
	public abstract List<MessagePartBase> getMessageLinesBase();

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((app_code == null) ? 0 : app_code.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((message_type == null) ? 0 : message_type.hashCode());
		result = prime * result + ((userLogin == null) ? 0 : userLogin.hashCode());
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
		MessageHTMLBase other = (MessageHTMLBase) obj;
		if (app_code == null) {
			if (other.app_code != null)
				return false;
		} else if (!app_code.equals(other.app_code))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (message_type == null) {
			if (other.message_type != null)
				return false;
		} else if (!message_type.equals(other.message_type))
			return false;
		if (userLogin == null) {
			if (other.userLogin != null)
				return false;
		} else if (!userLogin.equals(other.userLogin))
			return false;
		return true;
	}
}
