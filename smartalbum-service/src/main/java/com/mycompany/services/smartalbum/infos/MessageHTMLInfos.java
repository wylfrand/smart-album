package com.mycompany.services.smartalbum.infos;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

public class MessageHTMLInfos extends MessageHTMLBaseInfos{
	
	private static final long serialVersionUID = 1L;
	
	
	private String title;

	private List<MessagePartInfos> messageLines = new ArrayList<MessagePartInfos>();

	/**
	 * @return the messageLines
	 */
	public List<MessagePartInfos> getMessageLines() {
		return messageLines;
	}

	/**
	 * @param messageLines
	 *            the messageLines to set
	 */
	public void setMessageLines(List<MessagePartInfos> messageLines) {
		this.messageLines = messageLines;
	}

	@Override
	public List<MessagePartBaseInfos> getMessageLinesBase() {
		List<MessagePartBaseInfos> parts = Lists.newArrayList();
		parts.addAll(getMessageLines());
		return parts;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

}
