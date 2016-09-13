package com.mycompany.services.smartalbum.infos;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

public abstract class MessageHTMLBaseInfos implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	/** Code de l'application associé au message **/
	private String app_code;

	/**
	 * La table liée
	 */
	private String message_type;

	/** Code langue associé au message - Français par défaut **/
	private String lang_code;
	
	/** LOGIN de l'utilisateur ayant fait la dernière mise à jour **/
	private String userLogin;

	/** Date de dernière mise à jour du message **/
	private Date update_date;

	/**
	 * Dans la BD chaque message est découpé en plusieurs ligne pour que la
	 * taille du message ne soit pas limité
	 **/
	private int line_size;

	private String htmlDescription;

	/**
	 * Add comment to this image.
	 * 
	 * @param comment
	 *            - comment to add
	 */
	public void addMessagePart(MessagePartInfos messagePart) {
		if (messagePart == null) {
			throw new IllegalArgumentException("Null messagePart!");
		}
		getMessageLinesBase().add(messagePart);
	}

	public void updateHTMLDescription() {
		StringBuilder finalMessage = new StringBuilder();
		Map<String,String> orderedParts = Maps.newHashMap();
		int newOrder = 1;
		for (MessagePartBaseInfos part : getMessageLinesBase()) {
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
	
	public abstract List<MessagePartBaseInfos> getMessageLinesBase();

}
