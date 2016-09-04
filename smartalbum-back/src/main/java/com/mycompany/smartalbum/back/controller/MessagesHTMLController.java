package com.mycompany.smartalbum.back.controller;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.database.smartalbum.exception.PhotoAlbumException;
import com.mycompany.database.smartalbum.model.Album;
import com.mycompany.database.smartalbum.search.enums.MessageHTMLTypes;
import com.mycompany.database.smartalbum.utils.Entities;
import com.mycompany.filesystem.utils.MessageDescriptionForm;
import com.mycompany.services.utils.Constant;
import com.mycompany.services.utils.RetourReponse;


@RestController
@RequestMapping("/messageHTMLController")
public class MessagesHTMLController extends ABaseController {

	private final static transient Logger LOG = LoggerFactory
			.getLogger(MessagesHTMLController.class);

	
	@RequestMapping(value = "/ajax/create", method = RequestMethod.POST)
	public @ResponseBody
	RetourReponse updateHTMLMessages(HttpServletResponse response,
			@RequestParam Long imageId, @RequestParam String message,
			@RequestParam String type, ModelMap model) {
		RetourReponse retour = new RetourReponse();
		try {
			backService.createOrUpdateHTMLMessageInEntity(imageId, message,
					MessageHTMLTypes.fromValue(type), backService
							.getCurrentUser(false).getLogin(), Entities.IMAGE);
			retour.setResultObject(message);
			retour.setResult(true);
		} catch (PhotoAlbumException e) {
			LOG.error("Impossible de mettre à jour le message HTML", e);
		}
		return RetourReponse.ok();
	}

	@RequestMapping(value = "/ajax/remove", method = RequestMethod.POST)
	public @ResponseBody
	RetourReponse deleteHTMLMessages(HttpServletResponse response,
			@RequestParam Long messageId, @RequestParam Long imageId,
			ModelMap model) {
		RetourReponse retour = new RetourReponse();
		try {
			retour.setResultObject(null);
			retour.setResult(backService.deleteHTMLMessageById(messageId));
			model.put(Constant.IMAGE_CURRENT_WELCOME_MESS_ATTR, backService
					.createEntityHTMLMessage(backService.getImageDBService()
							.findImageById(imageId),
							MessageHTMLTypes.SHORTDESCRIPTION, "Message supprimé", backService
							.getCurrentUser(true).getLogin()));

		} catch (PhotoAlbumException e) {
			LOG.error("Impossible de mettre à jour le message HTML", e);
		}
		return RetourReponse.ok();
	}

	@RequestMapping(value = "/ajax/updateShortDescription", method = RequestMethod.POST)
	public @ResponseBody
	RetourReponse createAlbum(final @RequestBody MessageDescriptionForm aShortDescriptionForm) {
		RetourReponse retour = new RetourReponse();
		try {
			backService.updateHTMLMessageInEntity(
					aShortDescriptionForm.getId(), aShortDescriptionForm.getDescription(),
					Entities.IMAGE, aShortDescriptionForm.getTitle());
			retour.setResult(true);
			retour.setResultObject("Message créé avec succès...");
		} catch (PhotoAlbumException e) {
			LOG.error(
					"Impossible de mettre à jour la description courte du message HTML",
					e);
		}

		
		return retour;
	}
	
	/***
	 * URL: /messageHTMLController/ajax/update
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ajax/update", method = RequestMethod.POST)
	public @ResponseBody
	RetourReponse update(final @RequestBody MessageDescriptionForm aDescriptionForm) {

		RetourReponse retour = new RetourReponse();
		Entities foundEntity = Entities.fromValue(aDescriptionForm.getEntityType());
		if (foundEntity == null) {
			foundEntity = Entities.IMAGE;
		}
		
		if(aDescriptionForm.getId() > 0)
		{
			try {
				backService.updateHTMLMessageInEntity(aDescriptionForm.getId(), aDescriptionForm.getDescription(),
						foundEntity, null);
				retour.setResult(true);
			} catch (PhotoAlbumException e) {
				LOG.error("Impossible de mettre à jour le message HTML", e);
			}
		}
		else
		{
			Album album = (Album) backService.getCacheManager().getObjectFromCache(
					Constant.SMARTALBUM_PHOTOS_CURRENT_ALBUM);
			try {
				backService.createEntityHTMLMessage(album, MessageHTMLTypes.LONGDESCRIPTION, "Message mis à jour!", backService
						.getCurrentUser(true).getLogin());
			} catch (PhotoAlbumException e) {
				// TODO Auto-generated catch block
				LOG.error("Impossible de créer le message HTML de l'album {}", album.getName());
			}
		}

		RetourReponse response = new RetourReponse();
		response.setResultObject(aDescriptionForm.getDescription());
		response.setResult(true);
		return response;
	}


	@Override
	protected Logger getLoger() {
		// TODO Auto-generated method stub
		return LOG;
	}

}
