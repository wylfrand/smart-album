package com.mycompany.smartalbum.back.controller.restCalls;

import java.io.File;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.mycompany.database.smartalbum.exception.PhotoAlbumException;
import com.mycompany.database.smartalbum.model.Album;
import com.mycompany.database.smartalbum.model.Comment;
import com.mycompany.database.smartalbum.model.Image;
import com.mycompany.database.smartalbum.model.MetaTag;
import com.mycompany.database.smartalbum.utils.Constants;
import com.mycompany.filesystem.model.FileMeta;
import com.mycompany.services.utils.Constant;
import com.mycompany.services.utils.RetourReponse;
import com.mycompany.smartalbum.back.controller.ABaseController;
import com.mycompany.smartalbum.back.form.ImageForm;
import com.mycompany.smartalbum.back.utils.ViewEnum;

@RestController
@RequestMapping("/imagesControllerAjax")
public class ImageControllerRestCalls extends ABaseController {

	private final static transient Logger LOG = LoggerFactory
			.getLogger(ImageControllerRestCalls.class);

	/***************************************************
	 * URL: /controller/delete/{value} get(): get file as an attachment
	 * 
	 * @param response
	 *            : passed by the server
	 * @param value
	 *            : value from the URL
	 * @return void
	 ****************************************************/
	@RequestMapping(value = "/delete/{imageId}", method = RequestMethod.POST)
	public @ResponseBody
	RetourReponse delete(HttpServletResponse response, @RequestParam final Boolean fromTmp,
			@PathVariable String imageId) {

		Image image = backService.getImageDBService().findImageById(
				Long.parseLong(imageId));
		String pathToDelete = null;
		if(!fromTmp){
			pathToDelete = image.getFullPath();
		}
		else{
			pathToDelete = File.separator+backService.getCurrentUser(true).getLogin()+File.separator+image.getName();
		}
		try {
			backService.getImageDBService().deleteImage(image);
		} catch (PhotoAlbumException e) {
			backService.getErrorHandler().addToErrors(
					Constants.IMAGE_DELETING_ERROR);

		}

		// Raise 'imageDeleted' event, parameter path - path of file to delete
		backService.getFileSystemService().deleteImage(pathToDelete, true);

		return RetourReponse.ok();
	}
	
	
	/***************************************************
	 * URL: /controller/delete/{value} get(): get file as an attachment
	 * 
	 * @param response
	 *            : passed by the server
	 * @param value
	 *            : value from the URL
	 * @return void
	 ****************************************************/
	@RequestMapping(value = "/deleteTmpFile/{fileName}", method = RequestMethod.POST)
	public @ResponseBody
	RetourReponse deleteTmpFile(HttpServletResponse response,
			@PathVariable String fileName) {

		String login = backService.getCurrentUser(true).getLogin();
		String pathToDelete = login+File.separator+fileName;
		// Raise 'imageDeleted' event, parameter path - path of file to delete
		backService.getFileSystemService().deleteImage(pathToDelete,false);

		return RetourReponse.ok();
	}

	@RequestMapping(value = "/showImageDetails/{albumId}/{pictureId}", method = RequestMethod.GET)
	public String showAlbum(
			@PathVariable("albumId") Long albumId,
			@PathVariable("pictureId") Long pictureId,
			@RequestParam(required = false, value = "pageNumber") String pageNumber,
			ModelMap model) {

		Album album = backService.getAlbumDBService().findAlbumById(albumId);
		// Check, that album was not deleted recently.
		if (album != null) {
			if (!backService.getFileSystemService().isDirectoryPresent(
					album.getPath())) {
				return ViewEnum.SMARTALBUM_VIEW_ERROR.getView();
			}
			LinkedList<FileMeta> files = new LinkedList<FileMeta>();
			

			// On affiche le détail d'un album
			for (FileMeta meta : files) {
				if (meta.getId().equals(pictureId)) {
					LinkedList<FileMeta> albumAafficher = new LinkedList<FileMeta>();
					albumAafficher.add(meta);
					model.put(Constant.SMARTALBUM_PICTUREDETAIL, meta);
					break;
				}
			}

		}

		return ViewEnum.IMAGE_DETAIL_VIEW.getView();
	}
	
	@RequestMapping(value = "/ajax/renameImage", method = RequestMethod.POST)
	public @ResponseBody
	RetourReponse  renameImages(
			HttpServletResponse response, 
			@RequestParam final Long imageId, 
			@RequestParam final String imageNewName,
			@RequestParam final Boolean isTmpImage,
			@RequestParam final String imageOldName,
			ModelMap model) throws PhotoAlbumException {
		RetourReponse retour = new RetourReponse();
		if(backService.renameImageInAlbum(imageId, imageNewName, isTmpImage, imageOldName)){
			retour.setResult(true);
		}
		return retour;
	}

	

	@RequestMapping(value = "/ajax/moveImages", method = RequestMethod.POST)
	public @ResponseBody
	RetourReponse  moveImages(
			HttpServletResponse response, @RequestBody ImageForm imageForm, ModelMap model) throws PhotoAlbumException {
		RetourReponse retour = new RetourReponse();
		Album currentAlbum = null;
		Object obj = backService.getCacheManager().getObjectFromCache(
				Constant.SMARTALBUM_PHOTOS_CURRENT_ALBUM);
		if (obj != null) {
			currentAlbum = (Album) obj;
		} else {
			throw new PhotoAlbumException();
		}
		List<Image> imagesToMove = Lists.newArrayList();
		for(Image img : currentAlbum.getImages())
		{
			if(imageForm.getImageNames().contains(img.getPath()))
			{
				imagesToMove.add(img);
			}
		}
		backService.dropImagesToAlbum(imageForm.getAlbumId(), imagesToMove);
		retour.setResult(true);
		return retour;
	}

	/**
	 * Method, that invoked when user add comment to image. Only registered
	 * users can add comments to image.
	 * 
	 * @param entity
	 *            - image
	 * @param message
	 *            - comment text
	 * @throws PhotoAlbumException
	 */
	public Comment addComment(Object entity, String message)
			throws PhotoAlbumException {
		if (null == backService.getCurrentUser(true).getLogin()
				|| entity == null) {
			backService.getErrorHandler().addToErrors(
					Constants.ADDING_COMMENT_ERROR);
			return null;
		}
		if (message.trim().equals("")) {
			backService.getErrorHandler().addToErrors(
					Constants.NULL_COMMENT_ERROR);
			return null;
		}
		Comment comment = new Comment();
		comment.setAuthor(backService.getCurrentUser(true));
		if (entity instanceof Image) {
			comment.setImage((Image) entity);
			comment.setAlbum(null);
		} else {
			comment.setAlbum((Album) entity);
			comment.setImage(null);
		}

		comment.setDate(new Date());
		comment.setMessage(message);
		if (entity instanceof Image) {
			backService.getCommentDBService().addImageComment(comment);
		} else {
			backService.getCommentDBService().addAlbumComment(comment);
		}
		LOG.debug("Le commenataire rajouté : {}", comment);

		return comment;
	}

	/**
	 * Method, that invoked when user delete comment. Only registered users can
	 * delete comments.
	 * 
	 * @param comment
	 *            - comment to delete
	 */
	public void deleteComment(Comment comment) {
		try {
			backService.getCommentDBService().deleteImageComment(comment);
		} catch (Exception e) {
			backService.getErrorHandler().addToErrors(
					Constants.DELETE_COMMENT_ERROR);
			return;
		}
	}

	/**
	 * Method, that invoked to retrieve most popular metatags.
	 * 
	 * @return List of most popular metatags
	 */
	public List<MetaTag> popularTags() {
		return backService.getMetaTagDBService().getPopularTags();
	}

	/**
	 * Method, that used to autocomplete 'metatags' field while typing.
	 * 
	 * @param suggest
	 *            - text to autocomplete
	 * @return List of similar metatags
	 */
	public List<MetaTag> autoComplete(Object suggest) {
		String temp = (String) suggest;
		if (temp.trim().equals("")) {
			return null;
		}
		return backService.getMetaTagDBService().getTagsLikeString(
				(String) suggest);
	}

	/**
	 * Method, that invoked to retrieve direct link to image, to represent in
	 * UI.
	 * 
	 * @param image
	 *            - image to get direct link
	 * @return List of similar metatags
	 */
	public String getImageDirectLink(Image image, HttpServletRequest request) {

		return createServerURL((HttpServletRequest) request)
				+ image.getFullPath();

	}

	private String createServerURL(HttpServletRequest request) {
		StringBuffer url = new StringBuffer();

		if (request != null) {
			String name = request.getServerName();
			String protocol = (request.getProtocol().split(Constants.SLASH))[0]
					.toLowerCase();

			int port = request.getServerPort();

			url.append(protocol);
			url.append("://");
			url.append(name);
			url.append(":");
			url.append(Integer.toString(port));
		}

		return url.toString();
	}

	@Override
	protected Logger getLoger() {
		// TODO Auto-generated method stub
		return LOG;
	}
}
