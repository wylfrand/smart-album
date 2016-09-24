package com.mycompany.smartalbum.back.controller.restCalls;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.Gson;
import com.mycompany.database.smartalbum.exception.PhotoAlbumException;
import com.mycompany.database.smartalbum.model.Album;
import com.mycompany.database.smartalbum.model.Image;
import com.mycompany.database.smartalbum.model.User;
import com.mycompany.database.smartalbum.search.enums.MessageHTMLTypes;
import com.mycompany.database.smartalbum.search.vo.SearchDataTableRequest;
import com.mycompany.filesystem.model.FileMeta;
import com.mycompany.filesystem.utils.Constants;
import com.mycompany.services.model.commun.WowSliderImage;
import com.mycompany.services.smartalbum.vo.DataTable;
import com.mycompany.services.utils.Constant;
import com.mycompany.services.utils.RetourReponse;
import com.mycompany.smartalbum.back.controller.ABaseController;
import com.mycompany.smartalbum.back.helper.DataTableFactoryHelper;
import com.mycompany.smartalbum.back.utils.DataTableEnumType;

@RestController
@RequestMapping("/albumsControllerRest")
@SessionAttributes(value = { Constant.SMARTALBUM_ALBUM_FORM })
public class AlbumsControllerRestCalls extends ABaseController {
	
	@Resource
	DataTableFactoryHelper dataTableFactoryHelper;
	
	private final static transient Logger LOG = LoggerFactory
			.getLogger(AlbumsControllerRestCalls.class);

	@RequestMapping(value = "/ajax/deleteAlbum", method = RequestMethod.POST)
	public @ResponseBody
	RetourReponse deleteAlbum(final HttpServletResponse response,
			@RequestParam final Long albumId, final ModelMap model) {
		Album album = backService.getAlbumDBService().findAlbumById(albumId);
		backService.deleteAlbum(album);
		return RetourReponse.ok();
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ajax/editAlbum", method = RequestMethod.POST)
	public @ResponseBody
	RetourReponse ajaxEditAlbum(final HttpServletResponse response,
			@RequestParam final Long albumId, final ModelMap model) {

		Album album = backService.getAlbumDBService().findAlbumById(albumId);
		Object obj = backService.getCacheManager().getObjectFromCache(
				Constant.SMARTALBUM_SESSION_BEAN);
		LinkedList<FileMeta> files = null;
		if (obj != null) {
			files = (LinkedList<FileMeta>) obj;
		} else {
			return RetourReponse.ok();
		}
		for (FileMeta meta : files) {
			if (meta.isToAdd()) {
				String currentImagePathToCopy = Constants.TMP_DIR
						+ meta.getFileName();
				Image image = backService.getFileUploadService()
						.getImageFileByPath(currentImagePathToCopy,
								meta.getFileName());
				album.getImages().add(image);
				try {
					image.setAlbum(album);
					backService.getImageDBService().addImage(image);
					backService.getFileSystemService().addImage(
							image.getFullPath(), currentImagePathToCopy, true);
				} catch (PhotoAlbumException e) {
					LOG.error("Impossible de sauvegarder cette image en BDD,",
							e);
				}
			}
		}
		backService.editAlbum(album, true);
		backService.getCacheManager().putObjectInCache(
				Constant.SMARTALBUM_SESSION_BEAN, files);
		return RetourReponse.ok();
	}

	@Override
	protected Logger getLoger() {
		// TODO Auto-generated method stub
		return LOG;
	}

	@RequestMapping(value = "/ajax/dropAlbumToShelf", method = RequestMethod.POST)
	public @ResponseBody
	RetourReponse dropAlbumToShelf(
			@RequestParam(required = true, value = "albumId") final Long albumId,
			@RequestParam(required = true, value = "shelfId") final Long shelfId,
			final ModelMap model) {
		RetourReponse result =new  RetourReponse();
		
		if (backService.dropAlbumToShelf(albumId, shelfId)) {
			result = RetourReponse.ok();
		}
		else
		{
			RetourReponse response = new RetourReponse();
			response.setResult(false);
			result = response;
		}
		//Gson gsonObj = new Gson();
		return result;
	}
	
	@RequestMapping(value = "/ajax/printsImages", method = RequestMethod.POST)
	public @ResponseBody
	DataTable printsImages(final @RequestBody MultiValueMap<String, String>parametresAjax, @RequestParam(required = true, value = "albumId") final String albumId,@RequestParam(required = true, value = "dataTableType") final String dataTableType, HttpServletRequest request,
			final HttpServletResponse response, final ModelMap model) throws JSONException, PhotoAlbumException {
		
		LOG.debug("printsImages for dataTable processing ...");
		
		Album album = (Album)backService.getCacheManager().getObjectFromCache(
				Constant.SMARTALBUM_PHOTOS_CURRENT_ALBUM);
		
		DataTableEnumType dataTableTypeEnum = DataTableEnumType.fromValue(dataTableType);
		Long userId = null;
		if(album != null){
			userId = album.getOwner().getId();
		}
		else{
			userId = backService.getCurrentUser(true).getId();
		}
		SearchDataTableRequest currentRequest = new SearchDataTableRequest(parametresAjax, userId);
		String requestUrl = dataTableFactoryHelper.getServerUrl(request);
		DataTable table = dataTableFactoryHelper.buildImagesDataTable(currentRequest, requestUrl, dataTableTypeEnum);
		LOG.debug("printsImages for dataTable End");
		
		return table;
	}
	
	@RequestMapping(value = "/ajax/wowSliderImages", method = RequestMethod.POST)
	public @ResponseBody
	RetourReponse loadWowSliderImages(HttpServletRequest request, @RequestParam(required = true, value = "albumId") final String albumId,
			final ModelMap model) {
		RetourReponse result =new  RetourReponse();
		Album currentAlbum = null;
		List<WowSliderImage> wowImageList = new LinkedList<>();
		String requestUrl = dataTableFactoryHelper.getServerUrl(request);
		
		Object obj = backService.getCacheManager().getObjectFromCache(
				Constant.SMARTALBUM_PHOTOS_CURRENT_ALBUM);
		if (obj != null) {
			currentAlbum = (Album) obj;
			for(Image img : currentAlbum.getImages()){
				WowSliderImage wowImg = new WowSliderImage();
				wowImg.setSrc(requestUrl+"/imagesController/paintImage"+img.getFullPath());
				wowImg.setTitle(img.getName());
				wowImageList.add(wowImg);
			}
		} 
		result.setResult(true);
		Gson gsonObj = new Gson();
		result.setResultObject(gsonObj.toJson(wowImageList));
		
		return result;
	}
	
	/***************************************************
	 * URL: /controller/upload upload(): receives files
	 * 
	 * @param request
	 *            : MultipartHttpServletRequest auto passed
	 * @param response
	 *            : HttpServletResponse auto passed
	 * @return LinkedList<FileMeta> as json format
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 ****************************************************/
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public @ResponseBody LinkedList<FileMeta> upload(
			MultipartHttpServletRequest request, HttpServletResponse response) {

		// 1. build an iterator
		Iterator<String> itr = request.getFileNames();
		MultipartFile mpf = null;
		String userLogin = backService.getCurrentUser(true).getLogin();
		int nbFiles = 0;
		Album albumToUseForNewFile = null;
		Object obj = backService.getCacheManager().getObjectFromCache(
				Constant.SMARTALBUM_PHOTOS_CURRENT_ALBUM);
		if(obj!=null){
			Album anAlbum = (Album)obj;
			albumToUseForNewFile = backService.getAlbumDBService().findAlbumById(anAlbum.getId());
		}

		// 2. get each file
		if(albumToUseForNewFile!=null){
			while (itr.hasNext()) {
				// 2.1 get next MultipartFile
				mpf = request.getFile(itr.next());
				LOG.debug(mpf.getOriginalFilename() + " uploaded! " + mpf.getSize());
				// 2.2 if files > 10 remove the first from the list
				nbFiles = classifyNewImage(mpf, userLogin, nbFiles, albumToUseForNewFile);
			}
		}
		// result will be like this
		// [{"fileName":"app_engine-85x77.png","fileSize":"8 Kb","fileType":"image/png"},...]
		ModelMap model = new ModelMap();

		model.put(Constant.SMARTALBUM_PHOTOS_CONTROLLER,
				Constant.SMARTALBUM_PHOTOS_FILEUPLOADCONTROLLER);
		return tmpFilesToUpload;

	}

	private synchronized int classifyNewImage(MultipartFile mpf, String userLogin, int nbFiles, Album anAlbum) {
		// 2.3 create new fileMeta
		FileMeta fileMeta = backService.getFileUploadService().computeFileMetaBeforeSavingOriginal(
				mpf, userLogin, true);
		// 2.4 add to files
		fileMeta.setIndexInMemory(new Long(nbFiles++));
		// On va sauvegardr l'image parmis les fichiers non enregistrés de l'utilisateur
		try {
			User currentUser = backService.getCurrentUser(true);
			Image image = backService.getFileUploadService().constructImage(fileMeta);
			image.setAlbum(backService.getAlbumDBService().findAlbumById(anAlbum.getId()));
			image.setUser(null);
			String tmpPath = Constants.TMP_DIR
					+ File.separator + userLogin + File.separator;
			String relativePath = fileMeta.getFileName();
			fileMeta.setRelativePath(relativePath);
			// On rajoute un message court
			backService.addHTMLMessageInClassifiedImage(currentUser.getLogin(), image, MessageHTMLTypes.SHORTDESCRIPTION, "NON INITIALISER");
			
			// On rajoute un message long
			backService.addHTMLMessageInClassifiedImage(currentUser.getLogin(), image, MessageHTMLTypes.LONGDESCRIPTION, "DESCRIPTION LONGUE NON INITIALISEE");
			fileMeta.setBytes(null);
			tmpFilesToUpload.add(fileMeta);
			backService.getCacheManager().putObjectInCache(Constant.SMARTALBUM_TMP_UPLOADEDPICTURES,tmpFilesToUpload);
			if (!backService.getFileSystemService().addImage(
					picturesRootPath+image.getFullPath(),
					tmpPath + File.separator + fileMeta.getFileName(), false)) {
			}
		} catch (PhotoAlbumException e) {
			LOG.error("Une erreur est survenue pendant la sauvegarde de l'image",e);
		}
		return nbFiles;
	}


}