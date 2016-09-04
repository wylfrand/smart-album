package com.mycompany.services.smartalbum.vo;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

public class MessageHTMLVO extends MessageHTMLBaseVO{
	
	private static final long serialVersionUID = 1L;
	
	
	private String title;

	private List<MessagePartVO> messageLines = new ArrayList<MessagePartVO>();

	/**
	 * @return the messageLines
	 */
	public List<MessagePartVO> getMessageLines() {
		return messageLines;
	}

	/**
	 * @param messageLines
	 *            the messageLines to set
	 */
	public void setMessageLines(List<MessagePartVO> messageLines) {
		this.messageLines = messageLines;
	}

	@Override
	public List<MessagePartBaseVO> getMessageLinesBase() {
		List<MessagePartBaseVO> parts = Lists.newArrayList();
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
