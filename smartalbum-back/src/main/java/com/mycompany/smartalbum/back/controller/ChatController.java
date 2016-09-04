package com.mycompany.smartalbum.back.controller;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mycompany.database.smartalbum.exception.PhotoAlbumException;
import com.mycompany.smartalbum.back.utils.ViewEnum;

@Controller
@RequestMapping("/chatController")
public class ChatController extends ABaseController {

	private final static transient Logger LOG = LoggerFactory
			.getLogger(ChatController.class);

	@RequestMapping(value = "/ajax/showChatRoom", method = RequestMethod.GET)
	public String createOrModifyAlbum(
			final HttpServletResponse response) throws PhotoAlbumException {
		return ViewEnum.PUBLIC_CHAT_ROOM.getView();
	}
	
	@RequestMapping(value = "/showChatRoom", method = RequestMethod.GET)
	public String showChatRoom(
			final HttpServletResponse response) throws PhotoAlbumException {
		return ViewEnum.PUBLIC_CHAT_ROOM.getView();
	}

	@Override
	protected Logger getLoger() {
		return LOG;
	}
}