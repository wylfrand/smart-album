package com.mycompany.services.smartalbum.infos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

public class MessageHTMLInfos extends MessageHTMLBaseInfos implements MappingInfos<ImageInfos>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private ImageInfos parent;
	
	
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

	/**
	 * @return the parent
	 */
	public ImageInfos getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(ImageInfos parent) {
		this.parent = parent;
	}
	
	@Override
	public void update(ImageInfos entity, MappingOptions options) {
		parent = entity;
	}

}
