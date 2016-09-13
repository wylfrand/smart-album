package com.mycompany.smartalbum.back.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mycompany.database.smartalbum.exception.PhotoAlbumException;
import com.mycompany.database.smartalbum.model.Album;
import com.mycompany.database.smartalbum.model.Image;
import com.mycompany.database.smartalbum.model.User;
import com.mycompany.filesystem.utils.Constants;
import com.mycompany.services.smartalbum.infos.ShelfInfos;
import com.mycompany.services.utils.Constant;
import com.mycompany.services.utils.RetourReponse;
import com.mycompany.smartalbum.back.form.AlbumForm;
import com.mycompany.smartalbum.back.service.impl.AlbumWorker;
import com.mycompany.smartalbum.back.utils.ViewEnum;
import com.mycompany.smartalbum.back.utils.ViewEnumNames;

@Controller
@RequestMapping("/albumsController")
@SessionAttributes(value = { Constant.SMARTALBUM_ALBUM_FORM })
public class AlbumsController extends ABaseController {

	private final static transient Logger LOG = LoggerFactory
			.getLogger(AlbumsController.class);

	@RequestMapping(value = "/createOrModifyAlbum", method = RequestMethod.POST)
	public String createOrModifyAlbum(
			final HttpServletResponse response,
			@ModelAttribute(Constant.SMARTALBUM_ALBUM_FORM) final AlbumForm anAlbumForm) throws PhotoAlbumException {
		RetourReponse retour = new RetourReponse();
		
		AlbumWorker worker = new AlbumWorker();
		User user = backService.getCurrentUser(false);
		anAlbumForm.setUser(user);
		worker.setCurrentAlbumForm(anAlbumForm);
		worker.setBackService(backService);
		String result = null;
		if(user!=null){
			//new Thread(worker).start();
			worker.run();
			if (retour.getResult()) {
				result =  ViewEnum.SMARTALBUM_REDIRECT_TO_PUBLICSHELVES.getView();
			}
			else{
				throw new PhotoAlbumException("Impossible de sauvegarder l'album : "+ retour.getResultObject());
			}
		}
		return result;
	}

	@RequestMapping(value = "/editAlbum/{albumId}", method = RequestMethod.GET)
	public String editAlbum(
			final HttpServletResponse response,
			@PathVariable("albumId") final Long albumId,
			final ModelMap model) {
		
		AlbumForm albumForm = initAlbumForm(model);

		Album album = backService.getAlbumDBService().findAlbumById(albumId);
		List<ShelfInfos> shelvesInfos = backService.getUserShelvesInfos();
		albumForm.getUserShelvesInfos().addAll(shelvesInfos);
		
		model.put(Constant.SMARTALBUM_PHOTOS_CONTROLLER,Constant.SMARTALBUM_PHOTOS_ALBUMCONTROLLER);
		backService.getCacheManager().putObjectInCache(
				Constant.SMARTALBUM_PHOTOS_CONTROLLER,
				Constant.SMARTALBUM_PHOTOS_ALBUMCONTROLLER);

		model.put(Constant.SMARTALBUM_PHOTOS_CURRENT_ALBUM, album);
		backService.getCacheManager().putObjectInCache(
				Constant.SMARTALBUM_PHOTOS_CURRENT_ALBUM, album);
		model.put(Constant.SMARTALBUM_CURRENT_VIEW, ViewEnumNames.FILE_UPLOAD);

		return ViewEnum.FILE_UPLOAD_VIEW.getView();
	}

	@RequestMapping(value = "/showAlbum/{albumId}", method = RequestMethod.GET)
	public String showAlbum(@PathVariable("albumId") final Long albumId,
			final ModelMap model) {
		Album album = backService.getAlbumDBService().findAlbumById(albumId);
		// Check, that album was not deleted recently.
		if (album != null) {
			if (!backService.getFileSystemService().isDirectoryPresent(
					album.getPath())) {
				model.put(
						"exceptionStack",
						"Impossible de trouver l'album "
								+ album.getPath()
								+ " sur le filesystème! Veuillez contacter votre administrateur.");
				return ViewEnum.SMARTALBUM_VIEW_ERROR.getView();
			}
			model.put(Constant.SMARTALBUM_PHOTOS_CONTROLLER,
					Constant.SMARTALBUM_PHOTOS_ALBUMCONTROLLER);
			model.put(Constant.SMARTALBUM_PHOTOS_CURRENT_ALBUM, album);
			backService.getCacheManager().putObjectInCache(
					Constant.SMARTALBUM_PHOTOS_CURRENT_ALBUM, album);
			model.put(Constant.SMARTALBUM_CATEGORIES_LIST, categories);
			model.put(Constant.SMARTALBUM_CURRENT_VIEW,
					ViewEnumNames.ALBUM_DETAIL);
		}
		return ViewEnum.ALBUM_DETAIL_VIEW.getView();
	}

	
	@Override
	protected Logger getLoger() {
		return LOG;
	}

	@RequestMapping(value = "/download/{albumId}", method = RequestMethod.GET)
	public void downloadFile(final HttpServletRequest request,
			final HttpServletResponse response, @PathVariable final String albumId) {
		
		Album anAlbum = backService.getAlbumDBService().findAlbumById(Long.parseLong(albumId));
		String tmpFile = Constants.WORK_DIR+anAlbum.getPath();
		File tmp = new File(tmpFile);
		try {
			compressAlbumAndSend(response,tmp, getExportFilename(anAlbum.getName()));
		} catch (IOException e) {
			LOG.error("impossible de télécharger le zip");
		}
	}
	
	@RequestMapping(value = "/ajax/organizeImages", method = RequestMethod.POST)
	public String organizeImages(
			@RequestParam(required = true, value = "albumId") final Long albumId,
			final ModelMap model) {
		Album album = backService.getAlbumDBService().findAlbumById(albumId);
		HashSet<String> imagesCategories = new HashSet<String>();
		int index = 0;
		for(Image image : album.getImages())
		{
			String category = "--"+((index/Constant.PAGE_SIZE)+1)+"--";
			imagesCategories.add(category);
			image.setCategory(category);
			index++;
		}
		// Print the updated album
		model.put(Constant.ALBUM_SOURCE, album);
		model.put(Constant.ALBUM_DEST,album.getShelf().getAlbums().iterator().next());
		model.put(Constant.OPERATION_SUCCESS, true);
		model.put("imagesForm", new AlbumForm());
		model.put("albums",album.getShelf().getAlbums());
		model.put("categories",imagesCategories);
		
		return ViewEnum.IMAGES_MANAGER_VIEW.getView();
	}
}