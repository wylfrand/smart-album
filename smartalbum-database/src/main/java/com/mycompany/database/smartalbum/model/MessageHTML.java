package com.mycompany.database.smartalbum.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.google.common.collect.Lists;
import com.mycompany.database.smartalbum.model.base.MessageHTMLBase;
import com.mycompany.database.smartalbum.model.base.MessagePartBase;

/**
 * @author amvou
 */
@Entity
@Table(name = "MessageHTML")
public class MessageHTML extends MessageHTMLBase {


	private static final long serialVersionUID = 1L;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(nullable = true)
	private Image image;
	
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(nullable = true)
	private Album album;

	/** Chaque ligne contient une partie du message **/
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "messageHTML")
	@Fetch(FetchMode.SUBSELECT)
	private List<MessagePart> messageLines = new ArrayList<MessagePart>();
	
	private String title;

	/**
	 * @return the messageLines
	 */
	public List<MessagePart> getMessageLines() {
		return messageLines;
	}

	/**
	 * @param messageLines
	 *            the messageLines to set
	 */
	public void setMessageLines(List<MessagePart> messageLines) {
		this.messageLines = messageLines;
	}

	/**
	 * @return the image
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * @param image
	 *            the image to set
	 */
	public void setImage(Image image) {
		this.image = image;
	}

	@Override
	public List<MessagePartBase> getMessageLinesBase() {
		List<MessagePartBase> parts = Lists.newArrayList();
		parts.addAll(getMessageLines());
		return parts;
	}

	/**
	 * @return the album
	 */
	public Album getAlbum() {
		return album;
	}

	/**
	 * @param album the album to set
	 */
	public void setAlbum(Album album) {
		this.album = album;
	}

	public void setTitle(String title) {
		this.title = title;
		
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
}
