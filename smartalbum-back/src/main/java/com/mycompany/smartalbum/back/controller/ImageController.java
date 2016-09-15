package com.mycompany.smartalbum.back.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.mycompany.database.smartalbum.exception.PhotoAlbumException;
import com.mycompany.database.smartalbum.model.Album;
import com.mycompany.database.smartalbum.model.Comment;
import com.mycompany.database.smartalbum.model.Image;
import com.mycompany.database.smartalbum.model.MetaTag;
import com.mycompany.database.smartalbum.model.User;
import com.mycompany.database.smartalbum.utils.Constants;
import com.mycompany.filesystem.model.FileMeta;
import com.mycompany.services.utils.CommentForm;
import com.mycompany.services.utils.Constant;
import com.mycompany.services.utils.RetourReponse;
import com.mycompany.smartalbum.back.form.ImageForm;
import com.mycompany.smartalbum.back.utils.ViewEnum;

@Controller
@RequestMapping("/imagesController")
public class ImageController extends ABaseController {

	private final static transient Logger LOG = LoggerFactory
			.getLogger(ImageController.class);
	
	@RequestMapping(value = "/showImageDetails/{albumId}/{pictureId}", method = RequestMethod.GET)
	public String showImageDetails(
			@PathVariable("albumId") Long albumId,
			@PathVariable("pictureId") Long pictureId,
			@RequestParam(required = false, value = "pageNumber") String pageNumber,
			ModelMap model) {

		Image image = backService.getImageDBService().findImageById(pictureId);
		LOG.debug("Image à afficher : "+image.getFullPath());
		if (image.getAlbum() != null) {
			if (!backService.getFileSystemService().isDirectoryPresent(
					image.getAlbum().getPath())) {
				return ViewEnum.SMARTALBUM_VIEW_ERROR.getView();
			}
			// On affiche le détail d'un album
			model.put(Constant.SMARTALBUM_PICTUREDETAIL, image);
		}

		return ViewEnum.IMAGE_DETAIL_VIEW.getView();
	}

	@RequestMapping(value = "/paintImage/{userLogin}/{userShelf}/{userAlbum}/{userImage}/{substitute}", method = RequestMethod.GET)
	public void paintImage(HttpServletResponse response,
			@PathVariable String userLogin, @PathVariable String userShelf,
			@PathVariable String userAlbum, @PathVariable String userImage,
			@PathVariable String substitute) throws IOException {
		String path = userLogin + File.separator + userShelf + File.separator
				+ userAlbum + File.separator + userImage;
		File imageResource = backService.getFileSystemService()
				.getFileByPathFromUploadRoot(
						backService.getFileSystemService().transformPath(path,
								substitute));
		paintImageToBrowser(response, imageResource);
	}
	
	@RequestMapping(value = "/paintImage/{substitute}", method = RequestMethod.GET)
	public void paintImageNotFound(HttpServletResponse response, @PathVariable String substitute) throws IOException {
		paintImageToBrowser(response, null);
	}

	@RequestMapping(value = "/paintDefaultImage/{userLogin}/{userImage}", method = RequestMethod.GET)
	public void paintDefaultImage(HttpServletResponse response,
			@PathVariable String userLogin, @PathVariable String userImage)
			throws IOException {
		String path = userLogin + File.separator + userImage;
		File imageResource = backService.getFileSystemService()
				.getFileByPathFromUploadRoot(path);
		paintImageToBrowser(response, imageResource);
	}

	@RequestMapping(value = "/paintTmpImage/{imageName}/{controllerName}/{substitute}", method = RequestMethod.GET)
	public void paintDefaultTmpImage(HttpServletResponse response, @PathVariable String imageName,
			@PathVariable String substitute, @PathVariable String controllerName) throws IOException {
		String userLogin = backService.getCurrentUser(true).getLogin();
		String path = StringUtils.EMPTY;
		File imageResource = null;
		if("albumsController".equals(controllerName))
		{
			Album album = (Album)backService.getCacheManager().getObjectFromCache(Constant.SMARTALBUM_PHOTOS_CURRENT_ALBUM);
			path = album.getPath()+imageName;
			imageResource = backService.getFileSystemService()
					.getFileByPathFromUploadRoot(
							backService.getFileSystemService().transformPath(path,
									substitute));
		}
		else
		{
			path = userLogin + File.separator + imageName;
			imageResource = backService.getFileSystemService()
					.getFileByPathFromUploadRootTmp(
							backService.getFileSystemService().transformPath(path,
									substitute));
		}
		paintImageToBrowser(response, imageResource);
	}
	
	@RequestMapping(value = "/paintImage/{userLogin}/{imageName}/{substitute}", method = RequestMethod.GET)
	public void paintTmpImage(HttpServletResponse response, @PathVariable String userLogin,@PathVariable String imageName,
			@PathVariable String substitute) throws IOException {
		String path = StringUtils.EMPTY;
		File imageResource = null;
			path = userLogin + File.separator + imageName;
			imageResource = backService.getFileSystemService()
					.getFileByPathFromUploadRootTmp(
							backService.getFileSystemService().transformPath(path,
									substitute));
		paintImageToBrowser(response, imageResource);
	}
	
	/***************************************************
	 * URL: /controller/get/{value} get(): get file as an attachment
	 * 
	 * @param response
	 *            : passed by the server
	 * @param value
	 *            : value from the URL
	 * @return void
	 ****************************************************/
	@RequestMapping(value = "/download/{userLogin}/{userShelf}/{userAlbum}/{userImage}/{substitute}", method = RequestMethod.GET)
	public void downloadImageFromAlbum(HttpServletResponse response, @PathVariable String userLogin, @PathVariable String userShelf,
			@PathVariable String userAlbum, @PathVariable String userImage,
			@PathVariable String substitute) {

			String fileRelativePath = userLogin+ File.separator+userShelf+File.separator+userAlbum+File.separator+userImage;
			FileMeta file = backService.getFileUploadService().getFsFile(
					Constant.WORK_DIR + fileRelativePath, substitute, fileRelativePath);
		
		try {
			response.setContentType(file.getFileType());
			response.setHeader("Content-disposition", "attachment; filename=\""
					+ file.getFileName() + "\"");
			response.setContentType(file.getFileType());
			response.setHeader("Content-disposition",
					"attachment; filename=\"" + file.getFileName() + "\"");
			FileCopyUtils.copy(file.getBytes(), response.getOutputStream());
			
		} catch (IOException e) {
			LOG.debug("Impossible de renvoyé le flux binaire du fichier {}",
					file.getFileName(), e);
		}
	}
	
	@RequestMapping(value = "/download/{userLogin}/{userImage}/{substitute}", method = RequestMethod.GET)
	public void downloadImageFromTmp(HttpServletResponse response, @PathVariable String userLogin, @PathVariable String userImage,
			@PathVariable String substitute) {

		String relativePath = userLogin +File.separator+ userImage;
		FileMeta file = backService.getFileUploadService().getFsFile(
				Constant.TMP_DIR+relativePath, substitute, relativePath);
		
		try {
			response.setContentType(file.getFileType());
			response.setHeader("Content-disposition", "attachment; filename=\""
					+ file.getFileName() + "\"");
			response.setContentType(file.getFileType());
			response.setHeader("Content-disposition",
					"attachment; filename=\"" + file.getFileName() + "\"");
			FileCopyUtils.copy(file.getBytes(), response.getOutputStream());
			
		} catch (IOException e) {
			LOG.debug("Impossible de renvoyé le flux binaire du fichier {}",
					file.getFileName(), e);
		}
	}


	@RequestMapping(value = "/paintAvatarImage/{userLogin}", method = RequestMethod.GET)
	public void paintAvatarImage(HttpServletResponse response,
			@PathVariable String userLogin) throws IOException {

		User aUser = backService.getUserDBService().findUserById(
				new Long(userLogin));
		String path = Constant.WORK_DIR + userLogin + File.separator
				+ Constants.AVATAR_JPG;
		File avatarData = backService.getFileSystemService()
				.getFileByPathFromUploadRoot(aUser.getPath());

		if (aUser.getHasAvatar().booleanValue()
				&& (avatarData == null || !avatarData.exists())) {

			avatarData = backService.getFileSystemService()
					.getFileByAbsolutePath(path);
		}

		if (!aUser.getHasAvatar().booleanValue() || avatarData == null
				|| !avatarData.isFile()) {
			if (aUser.getSex().getKey() == null
					|| (aUser.getSex().getKey() != null && aUser.getSex()
							.getKey().equals("1"))) {
				avatarData = new File("/img/shell/avatar_default.png");
			} else if (aUser.getSex().getKey() != null
					&& aUser.getSex().getKey().equals("0")) {
				avatarData = new File("/img/shell/avatar_w_default.png");
			}
		}
		paintImageToBrowser(response, avatarData);
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

	@RequestMapping(value = "/ajax/addComment", method = RequestMethod.POST)
	public String addComment(
			HttpServletResponse response,final @RequestBody CommentForm aCommentForm,
			ModelMap model) {
		RetourReponse retour = new RetourReponse();
		Comment newComment = null;
		try {
			if (aCommentForm.getImageId() != -1) {
				newComment = addComment(backService.getImageDBService()
						.findImageById(aCommentForm.getImageId()),
						aCommentForm.getMessage());
				model.put("comments", newComment.getImage().getComments());
				model.put("albumId", -1L);
				model.put("imageId", newComment.getImage().getId());
			} else {
				newComment = addComment(backService.getAlbumDBService()
						.findAlbumById(aCommentForm.getAlbumId()),
						aCommentForm.getMessage());
				model.put("comments", newComment.getAlbum().getComments());
				model.put("albumId", newComment.getAlbum().getId());
				model.put("imageId", -1L);
			}

			model.put("comment", newComment);
			retour.setResult(true);
			retour.setResultObject(aCommentForm.getMessage());
		} catch (Exception e) {
			LOG.error(
					"Impossible de mettre à jour la description courte du message HTML",
					e);
			backService.getErrorHandler().addToErrors(
					Constants.SAVE_COMMENT_ERROR);
		}
		return ViewEnum.SMARTALBUM_COMMENT_ITEM_TEMPLATE.getView();
	}
	
	@RequestMapping(value = "/fullScreenSlider", method = RequestMethod.GET)
	public String getFullImgFile(ModelMap model) throws PhotoAlbumException {
		
		Album album = (Album)backService.getCacheManager().getObjectFromCache(Constant.SMARTALBUM_PHOTOS_CURRENT_ALBUM);
		model.put("currentAlbum", album);
		return ViewEnum.COMMON_FULL_SCREEN_PAGE_VIEW.getView();
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

	public void paintImage(HttpServletResponse response, String path)
			throws IOException {
		if (null == path) {
			return;
		}
		File imageResource = backService.getFileSystemService()
				.getFileByPathFromUploadRoot(path);
		paintImageToBrowser(response, imageResource);
	}

	private void paintImageToBrowser(HttpServletResponse response,
			File imageResource) throws IOException {
		if (imageResource != null && imageResource.exists()) {
			byte[] toWrite = new byte[Constants.DEFAULT_BUFFER_SIZE];
			FileInputStream in = new FileInputStream(imageResource);
			try {
				while (in.read(toWrite) != -1) {
					response.getOutputStream().write(toWrite);
				}
			} finally {
				in.close();
			}
		} else {
			// String suffix = excludeFilePrefix(imageResource.getPath());
			paintImage(response, backService.getFileSystemService()
					.transformPath(Constants.DEFAULT_ORIGINAL_PICTURE, null));
			return;
		}
	}
	
	@Override
	protected Logger getLoger() {
		// TODO Auto-generated method stub
		return LOG;
	}
}
