package com.mycompany.smartalbum.back.service.impl;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mycompany.database.smartalbum.exception.PhotoAlbumException;
import com.mycompany.database.smartalbum.model.ABuisnessObject;
import com.mycompany.database.smartalbum.model.Album;
import com.mycompany.database.smartalbum.model.Image;
import com.mycompany.database.smartalbum.model.MessageHTML;
import com.mycompany.database.smartalbum.model.MessagePart;
import com.mycompany.database.smartalbum.model.Shelf;
import com.mycompany.database.smartalbum.model.User;
import com.mycompany.database.smartalbum.repository.IAlbumJpaRepository;
import com.mycompany.database.smartalbum.repository.IImageJpaRepository;
import com.mycompany.database.smartalbum.search.enums.MessageHTMLTypes;
import com.mycompany.database.smartalbum.search.vo.SearchDataTableRequest;
import com.mycompany.database.smartalbum.search.vo.SearchDataTableResponse;
import com.mycompany.database.smartalbum.services.IAlbumDao;
import com.mycompany.database.smartalbum.services.ICommentDao;
import com.mycompany.database.smartalbum.services.IImageDao;
import com.mycompany.database.smartalbum.services.IMessageHTML;
import com.mycompany.database.smartalbum.services.IMessagePart;
import com.mycompany.database.smartalbum.services.IMetaTagDao;
import com.mycompany.database.smartalbum.services.ISearchDao;
import com.mycompany.database.smartalbum.services.IShelfDao;
import com.mycompany.database.smartalbum.services.IUserDao;
import com.mycompany.database.smartalbum.utils.Constants;
import com.mycompany.database.smartalbum.utils.Entities;
import com.mycompany.database.smartalbum.vo.DataTableEntity;
import com.mycompany.filesystem.model.CheckedFile;
import com.mycompany.filesystem.model.FileMeta;
import com.mycompany.filesystem.service.FileService;
import com.mycompany.filesystem.service.FileUploadService;
import com.mycompany.filesystem.service.ImageDimension;
import com.mycompany.filesystem.utils.FileFilter;
import com.mycompany.services.model.commun.enumeration.ApplicationsEnum;
import com.mycompany.services.smartalbum.infos.MappingOptions;
import com.mycompany.services.smartalbum.infos.ShelfInfos;
import com.mycompany.services.smartalbum.infos.UserInfos;
import com.mycompany.services.smartalbum.vo.AlbumVO;
import com.mycompany.services.smartalbum.vo.form.AlbumVOForm;
import com.mycompany.services.utils.Constant;
import com.mycompany.services.utils.ErrorHandlerBean;
import com.mycompany.services.utils.HttpSessionCacheManager;
import com.mycompany.services.utils.RetourReponse;
import com.mycompany.smartalbum.back.form.AlbumForm;
import com.mycompany.smartalbum.back.form.ShelfForm;
import com.mycompany.smartalbum.back.service.SmartAlbumBackService;

@Service
public class SmartAlbumBackServiceImpl implements SmartAlbumBackService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// ----------- SERVICES ---------------------------//
	@Resource
	protected DnDHelper dnDHelper;
	@Resource
	private ISearchDao searchService;

	@Resource
	private IAlbumDao albumDBService;
	
	@Resource
	private ICommentDao commentDBService;
	
	@Resource
	private IAlbumJpaRepository albumJpaDBService;
	
	@Resource
	private IImageDao imageDBService;
	
	@Resource
	private IImageJpaRepository imageJpaDBService;

	@Resource
	private IShelfDao shelfDBService;

	@Resource
	private FileUploadService fileUploadService;

	@Resource
	private FileService fileSystemService;

	@Autowired
	private IUserDao userDBService;

	@Resource
	private IMessageHTML messageDBService;
	
	@Resource
	private IMessagePart messagePartDBService;
	
	@Resource
	private IMetaTagDao metaTagDBService;

	@Resource
	private ErrorHandlerBean errorHandler;

	@Resource(name = "httpSessionCacheManager")
	private HttpSessionCacheManager cacheManager;

	@Value("${smartalbum.filesystem.upload.root.path}")
	private String picturesRootPath;

	@Value("${smartalbum.filesystem.upload.global.root.path}")
	private String tmpFile;

	@Value("${smartalbum.filesystem.upload.root.path}")
	private String rootWorkFile;
	
	/** The mapper. */
	private static Mapper mapper = new DozerBeanMapper();

	private static final int MESSAGEPART_SIZE = 512;

	private static final String DEFAULT_SHORT_MESSAGE = "<h1 align=\"center\"><br /></h1><h1 align=\"center\"><font color=\"#0000FF\" face=\"comic sans ms\">LE MESSAGE DESCRIPTIF DE CETTE IMAGE N'A PAS ENCORE ETE CREE<br /></font></h1>";

	private final static transient Logger LOG = LoggerFactory.getLogger(SmartAlbumBackServiceImpl.class);

	/**
	 * Retourne un objet {@link Album} créé é partir d'un objet
	 * {@link AlbumForm}
	 * 
	 * @param shelfForm
	 *            , L'objet représentant le formulaire d'un Album
	 * @return un objet {@link Album} contenant les infos base de donnée d'un
	 *         album
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mycompany.smartalbum.back.service.impl.SmartAlbumBackService#
	 * initSynchronizedAlbum(java.lang.String)
	 */
	@Override
	public Album initSynchronizedAlbum(String albumOldName) {

		Album newAlbum = new Album();
		newAlbum.setCreated(new Date());
		newAlbum.setName("SYNCHRONISED_ALBUM_" + albumOldName);
		newAlbum.setDescription("Cet album a été syncronisé à partir d'images déposées sur le serveur ...");
		newAlbum.setId(null);

		// List<Shelf> shelves = shelfDBService.getPredefinedShelves();
		//
		// // On recupère l'étagère dans lequel l'album sera rangée
		// /* TODO: Il faut ranger cet album dans l'étagère des albums
		// synchronisés **/
		// if(shelves.size()>0)
		// {
		// newAlbum.setShelf(shelves.get(0));
		// }
		newAlbum.setShowAfterCreate(false);
		return newAlbum;
	}

	@Override
	public Shelf initSynchronizedShelf(String shelfOldId, User user) {

		Shelf newShelf = new Shelf();
		newShelf.setCreated(new Date());
		newShelf.setDescription("Cet étagère a été syncronisé à partir d'images déposées sur le serveur ...");
		newShelf.setId(null);
		newShelf.setName("SYNCHRONISED_SHELF_" + shelfOldId);
		newShelf.setOwner(user);
		newShelf.setShared(false);
		return newShelf;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mycompany.smartalbum.back.service.impl.SmartAlbumBackService#
	 * saveSelectedImages(com.mycompany.database.smartalbum.model.Album, int,
	 * int, boolean)
	 */
	@Override
	public void saveSelectedImages(Album anAlbum, final String coverImageName, boolean isExistingAlbum) throws PhotoAlbumException {
		//String currentLogin = getCurrentUser(true).getLogin();
		Map<String, CheckedFile> selectedPictures = getSelectedFiles();
		//String filesSource = getFilesSourceDirectory(false,currentLogin);
		
		if (isExistingAlbum) {
			updateExistingAlbum(anAlbum, coverImageName, selectedPictures);
		} else {
			upNewAlbumWithTmpFiles(anAlbum, coverImageName, selectedPictures);
		}
		
		try {
			albumDBService.editAlbum(anAlbum);
		} catch (PhotoAlbumException e) {
			LOG.error("Impossible de sauvegarder la couverture en BDD,", e);
			return;
		}

		for (Image image : anAlbum.getImages()) {
			String currentWorkDir = rootWorkFile;
			String userLogin = anAlbum.getOwner().getLogin();
			String tmpWorkDir = tmpFile + File.separator + userLogin;
			fileSystemService.moveFile(currentWorkDir + File.separator + image.getFullPath(),tmpWorkDir + File.separator + image.getName());
		}
		resetSelectedFiles();
	}

	private void upNewAlbumWithTmpFiles(Album anAlbum, final String coverImageName,
			Map<String, CheckedFile> selectedPictures) throws PhotoAlbumException {
		User user = getCurrentUser(false);
		for (CheckedFile picture : selectedPictures.values()) {
			Image currentImg = null;
			if (!picture.getImageName().equals(coverImageName)) {
				currentImg = getImageDBService().findImageByNameAndUser(picture.getImageName(),
						user.getId());
				user.removeImage(currentImg);
				anAlbum.addImage(currentImg);
				
			} else {
				currentImg = getImageDBService().findImageByNameAndUser(coverImageName, user.getId());
				user.removeImage(currentImg);
				anAlbum.addImage(currentImg);
				anAlbum.setCoveringImage(currentImg);
			}
			
			// 1 - On modifie l'image de couverture si nécessaire
			if (currentImg != null) {
				imageDBService.updateImage(currentImg);
			}
		}
	}

	private void updateExistingAlbum(final Album anAlbum, final String coverImageName,
			Map<String, CheckedFile> selectedPictures) {
		if(anAlbum.getOwner() ==null) return;
		for (CheckedFile picture : selectedPictures.values()) {
			Image currentImg = findImageInList(anAlbum.getOwner().getImages(), picture.getImageName());
			if (currentImg != null) {
				anAlbum.addImage(currentImg);
				if (picture.getImageName().equals(coverImageName)) {
					currentImg.setCovering(true);
					anAlbum.setCoveringImage(currentImg);
				}
			}
		}
	}
	
	Image findImageInList(List<Image> imageList, String imageName){
		Image result = null;
		for(Image img : imageList){
			if(img.getName().equals(imageName)){
				result = img;
				break;
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mycompany.smartalbum.back.service.impl.SmartAlbumBackService#
	 * saveSelectedImages(com.mycompany.database.smartalbum.model.Album,
	 * boolean)
	 */
	@Override
	public void saveSelectedImages(Album anAlbum, boolean isCreation) {
		if (isCreation) {
			Map<String, CheckedFile> selectedPictures = getSelectedFiles();
			String filesSource = Constant.TMP_DIR + getCurrentUser(true).getLogin() + File.separator;
			for (CheckedFile picture : selectedPictures.values()) {
				Image currentImg = initImageOnFs(filesSource, picture.getImageName(),
						anAlbum, !isCreation);
				if (currentImg != null) {
					anAlbum.addImage(currentImg);
				}
			}
			try {
				albumDBService.editAlbum(anAlbum);
			} catch (PhotoAlbumException e) {
				LOG.error("Impossible de sauvegarder l'album avec toutes les images importées ", e);
			}
			for (Image image : anAlbum.getImages()) {
				String currentWorkDir = rootWorkFile;
				String userLogin = anAlbum.getOwner().getLogin();
				String tmpWorkDir = tmpFile + File.separator + userLogin;
				
				if (!fileSystemService.addImage(currentWorkDir + File.separator + image.getFullPath(),
						tmpWorkDir + File.separator + image.getName(), true)) {
				}
				// Remove from tmp file:
				fileSystemService.deleteImage(userLogin + File.separator + image.getName(), false);
			}
		}
		resetSelectedFiles();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mycompany.smartalbum.back.service.impl.SmartAlbumBackService#
	 * getSelectedFiles()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Map<String, CheckedFile> getSelectedFiles() {
		Map<String, CheckedFile> result = new LinkedHashMap<String, CheckedFile>();
		Object obj = cacheManager.getObjectFromCache(Constant.SMARTALBUM_CHECKED_PICTURES);
		if (obj != null) {
			result = (LinkedHashMap<String, CheckedFile>) obj;
		} else {
			resetSelectedFiles();
		}
		return result;
	}

	public void resetSelectedFiles() {
		LinkedHashMap<String, CheckedFile> object = (LinkedHashMap<String, CheckedFile>)cacheManager.getObjectFromCache(Constant.SMARTALBUM_CHECKED_PICTURES);
		object.clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mycompany.smartalbum.back.service.impl.SmartAlbumBackService#
	 * removeSelectedFilesByName()
	 */
	@Override
	public void removeSelectedFilesByName(boolean fromExistingAlbum) throws PhotoAlbumException {
		Map<String, CheckedFile> checkedPictures = getSelectedFiles();
		String userLogin = getCurrentUser(true).getLogin();
		String filesSource = null;
		if(!checkedPictures.isEmpty()){
			checkedPictures.get(0);
			filesSource = getFilesSourceDirectory(fromExistingAlbum, userLogin);
			for(CheckedFile currentCheckedPhoto : checkedPictures.values())
			{
				if (fromExistingAlbum) {
					Image currentImage = getImageDBService().findImageById(currentCheckedPhoto.getImageId());
					if (currentImage != null) {
						getImageDBService().deleteImage(currentImage);
					}
				}
				getFileSystemService().deleteImage(filesSource + currentCheckedPhoto.getImageName(), fromExistingAlbum);
			}
			resetSelectedFiles();
		}
	}
	@Override
	public void toogleSelectAllFiles() throws PhotoAlbumException {
		Map<String, CheckedFile> checkedPictures = getSelectedFiles();
		Object currentObject = getCacheManager()
				.getObjectFromCache(Constant.SMARTALBUM_PHOTOS_CURRENT_ALBUM);
		Album currentAlbum = null;
		List<String> existingFilesNames = new ArrayList<>();
		if (currentObject == null) {
			List<Image> images = getCurrentUser(false).getImages();
			for(Image img : images){
				existingFilesNames.add(img.getName());
			}
			
		} else {
			currentAlbum = (Album)currentObject;
			List<Image> images = currentAlbum.getImages();
			for(Image img : images){
				existingFilesNames.add(img.getName());
			}
		}
		if (checkedPictures.size()>=0 && checkedPictures.size()<existingFilesNames.size()) {
			for(String imageName : existingFilesNames)
			{
					CheckedFile file = null;
					Image currentImage = null;
					if(currentAlbum != null){
						currentImage = currentAlbum.getImageByName(imageName);
					}else{
						currentImage = getCurrentUser(false).getImageByName(imageName);
					}
					if(currentImage!=null){
						file = new CheckedFile(imageName,currentImage.getId());
						checkedPictures.put(imageName, file);
					}
			}
		}
		else{
			checkedPictures = new LinkedHashMap<>();
			resetSelectedFiles();
		}
		getCacheManager()
		.putObjectInCache(Constant.SMARTALBUM_CHECKED_PICTURES,checkedPictures);
	}

	private String getFilesSourceDirectory(boolean fromExistingAlbum, String userLogin) throws PhotoAlbumException {
		String filesSource;
		if (fromExistingAlbum) {
			Album currentAlbum = getCurrentAlbumFromCache();
			if(currentAlbum!=null){
			filesSource = Constant.WORK_DIR + currentAlbum.getPath() + File.separator;
			}
			else{
				filesSource = Constant.TMP_DIR + userLogin + File.separator;
			}
			
		} else {
			filesSource = Constant.TMP_DIR + userLogin + File.separator;
		}
		return filesSource;
	}

	private Album getCurrentAlbumFromCache() throws PhotoAlbumException {
		Album currentAlbum = null;
		Object obj = getCacheManager().getObjectFromCache(Constant.SMARTALBUM_PHOTOS_CURRENT_ALBUM);
		if (obj != null) {
			currentAlbum = (Album) obj;
		} else {
			LOG.error("Aucun album sélectionné ! Veuillez contacter votre administrateur.");
			
		}
		return currentAlbum;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mycompany.smartalbum.back.service.impl.SmartAlbumBackService#
	 * removeAllFilesByName()
	 */
	@Override
	public void removeAllFilesFromTmpByName() throws PhotoAlbumException {

		String filesSource = getFilesSourceDirectory(false,getCurrentUser(true).getLogin());
		File tmp = new File(filesSource);
		String[] existingFilesNames = tmp.list(com.mycompany.filesystem.utils.FileUtils.imageFilter);

		for (String fileName : existingFilesNames) {
			FileUtils.deleteQuietly(new File(filesSource + fileName));
		}
		resetSelectedFiles();
	}
	
	@Override
	public void removeAllPicturesFromCurrentAlbum() throws PhotoAlbumException {
		
		Album currentAlbum = getCurrentAlbumFromCache();
		if(currentAlbum!=null){
			getAlbumDBService().deleteAlbum(currentAlbum);
		}else{
			imageDBService.deleteAllImages(getCurrentUser(false).getImages());
		}
		String filesSource = getFilesSourceDirectory(true,getCurrentUser(true).getLogin());
		File tmp = new File(filesSource);
		String[] existingFilesNames = tmp.list(com.mycompany.filesystem.utils.FileUtils.imageFilter);

		for (String fileName : existingFilesNames) {
			FileUtils.deleteQuietly(new File(filesSource + fileName));
		}
		resetSelectedFiles();
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mycompany.smartalbum.back.service.impl.SmartAlbumBackService#
	 * getCurrentUser()
	 */
	@Override
	public User getCurrentUser(boolean fromCache) {
		Object person = cacheManager.getObjectFromCache(Constant.SMARTALBUM_CURRENT_USER);
		User currentUser = (User) person;
		if (fromCache) {
			return currentUser;
		} else {
			if (currentUser != null) {
				currentUser = userDBService.findUserById(currentUser.getId());
			}
			cacheManager.putObjectInCache(Constant.SMARTALBUM_CURRENT_USER, currentUser);
		}

		return (User) currentUser;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mycompany.smartalbum.back.service.impl.SmartAlbumBackService#
	 * getCurrentUser()
	 */
	@Override
	public UserInfos findCurrentUserInfos(boolean fromCache) {
		Object person = cacheManager.getObjectFromCache(Constant.SMARTALBUM_CURRENT_USER_VO);
		UserInfos currentUser = (UserInfos) person;
		if (fromCache && currentUser!=null) {
			return currentUser;
		} else {
			User currentUserDB = getCurrentUser(false);
			currentUser = mapper.map(currentUserDB, UserInfos.class);
			currentUser.updateUser(new MappingOptions());
			cacheManager.putObjectInCache(Constant.SMARTALBUM_CURRENT_USER_VO, currentUser);
		}
		return currentUser;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mycompany.smartalbum.back.service.impl.SmartAlbumBackService#
	 * rotatePerfectSquares(java.awt.image.BufferedImage, int)
	 */
	@Override
	public BufferedImage rotatePerfectSquares(BufferedImage image, int _thetaInDegrees) {
		double _theta = _thetaInDegrees * Math.PI / 180;
		/*
		 * Affline transform only works with perfect squares. The following code
		 * is used to take any rectangle image and rotate it correctly. To do
		 * this it chooses a center point that is half the greater length and
		 * tricks the library to think the image is a perfect square, then it
		 * does the rotation and tells the library where to find the correct top
		 * left point. The special cases in each orientation happen when the
		 * extra image that doesn't exist is either on the left or on top of the
		 * image being rotated. In both cases the point is adjusted by the
		 * difference in the longer side and the shorter side to get the point
		 * at the correct top left corner of the image. NOTE: the x and y axes
		 * also rotate with the image so where width > height the adjustments
		 * always happen on the y axis and where the height > width the
		 * adjustments happen on the x axis.
		 */
		AffineTransform xform = new AffineTransform();

		if (image.getWidth() > image.getHeight()) {
			xform.setToTranslation(0.5 * image.getWidth(), 0.5 * image.getWidth());
			xform.rotate(_theta);

			int diff = image.getWidth() - image.getHeight();

			switch (_thetaInDegrees) {
			case 90:
				xform.translate(-0.5 * image.getWidth(), -0.5 * image.getWidth() + diff);
				break;
			case 180:
				xform.translate(-0.5 * image.getWidth(), -0.5 * image.getWidth() + diff);
				break;
			default:
				xform.translate(-0.5 * image.getWidth(), -0.5 * image.getWidth());
				break;
			}
		} else if (image.getHeight() > image.getWidth()) {
			xform.setToTranslation(0.5 * image.getHeight(), 0.5 * image.getHeight());
			xform.rotate(_theta);

			int diff = image.getHeight() - image.getWidth();

			switch (_thetaInDegrees) {
			case 180:
				xform.translate(-0.5 * image.getHeight() + diff, -0.5 * image.getHeight());
				break;
			case 270:
				xform.translate(-0.5 * image.getHeight() + diff, -0.5 * image.getHeight());
				break;
			default:
				xform.translate(-0.5 * image.getHeight(), -0.5 * image.getHeight());
				break;
			}
		} else {
			xform.setToTranslation(0.5 * image.getWidth(), 0.5 * image.getHeight());
			xform.rotate(_theta);
			xform.translate(-0.5 * image.getHeight(), -0.5 * image.getWidth());
		}

		AffineTransformOp op = new AffineTransformOp(xform, AffineTransformOp.TYPE_BILINEAR);

		return op.filter(image, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mycompany.smartalbum.back.service.impl.SmartAlbumBackService#
	 * getFileCount (java.io.File)
	 */
	@Override
	public int getFileCountByDimension(final File tmp, ImageDimension dimention) {
		FileFilter filter = new FileFilter();
		String[] existingFiles = filter.execute(dimention, tmp);
		return existingFiles.length;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mycompany.smartalbum.back.service.impl.SmartAlbumBackService#
	 * deleteEmptyAlbums()
	 */
	@Override
	public void deleteEmptyAlbums(String login) {
		List<Album> albums = albumDBService.findAll();
		for (Album anAlbum : albums) {
			if (anAlbum.getImages().isEmpty()) {
				try {
					albumDBService.deleteAlbum(anAlbum);

					if (fileSystemService.isDirectoryEmpty(anAlbum.getPath())) {
						fileSystemService.deleteDirectory(anAlbum.getPath());
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					LOG.debug("Impossible de supprimer un album vide...", e);
				}
			} else {
				if (fileSystemService.isDirectoryEmpty(anAlbum.getPath())) {
					fileSystemService.deleteDirectory(anAlbum.getPath());
					try {
						albumDBService.deleteAlbum(anAlbum);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						LOG.debug("Impossible de supprimer un album vide...", e);
					}
				}
			}
		}
		String albumAbsolutePath = null;
		albumAbsolutePath = picturesRootPath + File.separator + login;
		File userDir = new File(albumAbsolutePath);
		String[] filesNames = userDir.list(com.mycompany.filesystem.utils.FileUtils.imageFilter);
		for (String currentShelfName : filesNames) {
			String shelfAbsolutePath = albumAbsolutePath + File.separator + currentShelfName;
			File currentShelf = new File(shelfAbsolutePath);
			if (currentShelf.isDirectory()) {
				String[] albumNames = currentShelf.list(com.mycompany.filesystem.utils.FileUtils.imageFilter);
				if (albumNames.length > 0) {
					for (String albumName : albumNames) {
						File currentAlbum = new File(shelfAbsolutePath + File.separator + albumName);
						if (currentAlbum.isDirectory() && currentAlbum
								.list(com.mycompany.filesystem.utils.FileUtils.imageFilter).length == 0) {
							currentAlbum.delete();
						}
					}
				} else {
					currentShelf.delete();
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mycompany.smartalbum.back.service.impl.SmartAlbumBackService#
	 * synchronizeShelfWithDatabase(java.lang.String, java.lang.String)
	 */
	@Override
	public void synchronizeShelfWithDatabase(String shelfRelativePath, String shelfId, User user) throws PhotoAlbumException {
		List<String> albumNames = fileSystemService.findAllDirectoryNames(shelfRelativePath);
		Album album = null;
		for (String albumName : albumNames) {
			album = albumDBService.findAlbumById(Long.parseLong(albumName));
			// L'album n'existe pas en base, on va essayer de le créer
			String albumPath = shelfRelativePath + File.separator + albumName;
			File tmp = new File(picturesRootPath + File.separator + albumPath);
			FileFilter filter = new FileFilter();
			String[] images = filter.execute(ImageDimension.ORIGINAL, tmp);
			Album albumToUpdate = album;
			if (images != null && images.length > 0) {
				if (album == null) {
					LOG.debug("L'album " + albumName + " n'existe pas en base de données, on va le créer");
					Album newAlbum = initSynchronizedAlbum(albumName);
					newAlbum.setShelf(getCurrentShelf(shelfId, user));
					albumToUpdate = newAlbum;
					// Création du message html par défaut
					createEntityHTMLMessage(albumToUpdate, MessageHTMLTypes.LONGDESCRIPTION, DEFAULT_SHORT_MESSAGE, user.getLogin());
					createEntityHTMLMessage(albumToUpdate, MessageHTMLTypes.SHORTDESCRIPTION, DEFAULT_SHORT_MESSAGE, user.getLogin());
					// Creation de l'album sur le fs
					fileSystemService.onAlbumAdded(albumToUpdate);
				} else {
						LOG.debug("L'album " + albumName + " existe en base de données, on va le mettre à jour");
				}
				try {
					// On initialise la liste d'images à sauvegarder dans l'album
					List<Image> imageList = initFilesToRegister(albumPath, images, albumToUpdate, user.getLogin());
					// Sauvegarde des images
					albumToUpdate.getImages().addAll(imageList);
					getAlbumDBService().editAlbum(albumToUpdate);
					LOG.debug("Album enregistré avec succès... On va maintenant renommer le répertoire...");
				} catch (PhotoAlbumException e) {
					LOG.debug("L'album " + albumName + " n'a pas pu être enregistré");
				}
				// On déplace les fichiers sur le FS si nécessaire
				if (albumToUpdate.getId() != null && !albumToUpdate.getId().toString().equals(albumName) && !albumToUpdate.getShelf().getId().equals(shelfId)) {
					fileSystemService.renameAlbumDirectory(albumToUpdate.getPath(), albumPath);
				}
			} else {
				fileSystemService.deleteDirectory(shelfRelativePath + File.separator + albumName);
			}
		}
	}

	private List<Image> initFilesToRegister(String albumPath, String[] images, Album newAlbum, String login) {
		List<Image> imageList = new ArrayList<>();
		for (String imageName : images) {
			if (newAlbum.getImageByName(imageName) == null) {
				// On initialise l'image à partir de l'ancien
				// emplacement
				String imageRelativePath = albumPath + File.separator + imageName;
				Image image = fileUploadService.getImageFileByPath(picturesRootPath + imageRelativePath,
						imageRelativePath);
				// On met à jour avec le nouvel album
				if (image != null) {
					try {
						// Mise à jour de l'album si nécessaire
						newAlbum.addImage(image);
						// On rajoute un message court de l'image
						createEntityHTMLMessage(image, MessageHTMLTypes.LONGDESCRIPTION, "NON INITIALISER", login);
						// On rajoute un message long
						createEntityHTMLMessage(image, MessageHTMLTypes.SHORTDESCRIPTION,
								"DESCRIPTION LONGUE NON INITIALISEE", login);
					} catch (PhotoAlbumException e) {
						LOG.error("Impossible de créer le message HTML dans l'image", e);
					}
					imageList.add(image);
				}
			}
		}
		return imageList;
	}
	
	private void addHTMLMessageInImage(final String login, final Image image, final MessageHTMLTypes messageType,
			final String defaultMessage, boolean imageIsInAnAlbum) {
		if (image != null) {
			if (!image.hasMessageType(messageType)) {
				try {
					// On créé une image sans message:
					if (image.getId() == null || image.getId() < 0) {
						if (imageIsInAnAlbum) {
							getImageDBService().addImage(image);
						} else {
							getImageDBService().addImageInUserTmp(image);
						}
					}
					// On met à jour l'image avec le message HTML
					MessageHTML defaultHTMLMessage = initHTMLMessageInEntity(defaultMessage, messageType, login);
					defaultHTMLMessage.setImage(image);
					image.getMessagesHTML().add(defaultHTMLMessage);
					getImageDBService().updateImage(image);
				} catch (PhotoAlbumException e) {
					LOG.error("Impossible de d'initialiser la description courte de l'image");
				}
			}
			else {
				LOG.debug("Un message HTML du type {} existe dejà dans l'image {}", messageType.name(), image.toString());
			}
		} 
	}
	
	private void addHTMLMessageInNewAlbum(final String login, final Album album, final MessageHTMLTypes messageType, final String defaultMessage) {
		if(album!=null){
			try {
			if(album.getId()==null || album.getId()<0){
				getAlbumDBService().addAlbum(album);
			}
			MessageHTML defaultHTMLMessage = initHTMLMessageInEntity(defaultMessage, messageType, login);
			defaultHTMLMessage.setAlbum(album);
			album.getMessagesHTML().add(defaultHTMLMessage);
			getAlbumDBService().editAlbum(album);
			} catch (PhotoAlbumException e) {
				LOG.error("Impossible de d'initialiser la description de l'album");
			}
		}
	}

	private Shelf getCurrentShelf(String shelfId, User user) {
		Shelf targetShelf = shelfDBService.findShelfById(Long.parseLong(shelfId));
		if (targetShelf == null) {
			targetShelf = shelfDBService.findShelfByName("SYNCHRONISED_SHELF_" + shelfId);
		}

		if (targetShelf == null) {
			targetShelf = initSynchronizedShelf(shelfId, user);
			try {
				addShelfWithoutDeletingFsFiles(targetShelf, user);
			} catch (PhotoAlbumException e) {
				LOG.error("Impossible d'initialiser l'album");
			}
		}

		return targetShelf;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mycompany.smartalbum.back.service.impl.SmartAlbumBackService#
	 * getSplitedMessage(java.lang.String, int)
	 */
	@Override
	public Map<Integer, String> getSplitedMessage(String message, int size) {
		Map<Integer, String> result = new HashMap<Integer, String>();
		if (message == null || size <= 0)
			return result;

		int i = 1;
		int debut = 0, fin = message.length();
		if (size > fin) {
			result.put(1, message);
			return result;
		}
		String messagePart;
		int index = 0;
		// Tant qu'on peut copier une partie du message
		while (true) {
			debut = index;
			if (i * size - 1 > fin) {
				// Si l'on est en fin de message, alors on recopie le reste du
				// message
				index = fin;
			} else {
				index = i * size - 1;
			}

			if (debut >= fin) {
				break;
			}
			messagePart = message.substring(debut, index);
			// On enregistre la partie du message
			result.put(i++, messagePart);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mycompany.smartalbum.back.service.impl.SmartAlbumBackService#addAlbum
	 * (com.mycompany.database.smartalbum.model.Album, boolean)
	 */
	@Override
	public RetourReponse addAlbum(Album newAlbum, boolean isCreation) {
		return saveAlbum(newAlbum, isCreation, getCurrentUser(false), null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mycompany.smartalbum.back.service.impl.SmartAlbumBackService#
	 * saveAlbum (com.mycompany.database.smartalbum.model.Album, int, int,
	 * boolean)
	 */
	@Override
	public RetourReponse saveAlbum(Album album, boolean isCreation, User user, String coverAlbumName) {
		if (album.getShelf() == null) {
			return getRetourResponse(Constants.SHELF_MUST_BE_NOT_NULL_ERROR, false, null);
		}
		// Album name must be unique in shelf
		if (user!= null && user.hasAlbumWithName(album)) {
			return getRetourResponse(Constants.SAME_ALBUM_EXIST_ERROR, false, null);
		}
		try {

			if (isCreation) {
				// Save to DB
				album.setCreated(new Date());
				albumDBService.addAlbum(album);
				// Creation de l'album sur le fs
				fileSystemService.onAlbumAdded(album);
				saveSelectedImages(album, coverAlbumName, false);
				// Création du message html de par défaut
			} else {
				albumDBService.editAlbum(album);
				saveSelectedImages(album, false);
			}

		} catch (Exception e) {
			LOG.debug("Impossible de sauvegarder correctement l'album", e);
			return getRetourResponse(Constants.ALBUM_SAVING_ERROR, false, null);
		}
		return getRetourResponse(null, true, "Ajout ok");
	}

	@Override
	public RetourReponse addOrModifyShelf(ShelfForm aShelfForm) {
		Shelf result = null;
		RetourReponse response = new RetourReponse();
		result = initShelf(aShelfForm);

		if (aShelfForm.getId() < 0) {
			// Cas d'une création
			try {
				addShelf(result);
				response.setResult(true);
				response.setResultObject(result);

			} catch (PhotoAlbumException e) {
				LOG.error("Impossible de rajouter une étagère", e);
				getErrorHandler().addToErrors(Constants.SHELF_SAVING_ERROR);
			} catch (Exception e) {
				LOG.error("IErreur imprévue lors de la création d'une étagère", e);
				getErrorHandler().addToErrors(Constants.SHELF_SAVING_ERROR);
			}
		} else {

			try {
				getShelfDBService().editShelf(result);
			} catch (PhotoAlbumException e) {
				response.setResult(false);
				response.setResultObject(result);
			}
		}
		return response;
	}

	/**
	 * Method, that invoked on creation of the new shelf. Only registered users
	 * can create new shelves.
	 * 
	 * @param album
	 *            - new album
	 * @throws PhotoAlbumException
	 */
	private void addShelf(Shelf shelf) throws PhotoAlbumException {
		addShelfCommon(shelf, getCurrentUser(true));
		getFileSystemService().onShelfAdded(shelf);
	}

	private void addShelfCommon(Shelf shelf, User user) throws PhotoAlbumException {
		if (user.hasShelfWithName(shelf)) {
			getErrorHandler().addToErrors(Constants.SAME_SHELF_EXIST_ERROR);
			return;
		}
		try {
			shelf.setCreated(new Date());
			getShelfDBService().addShelf(shelf);
		} catch (PhotoAlbumException e) {
			getErrorHandler().addToErrors(Constants.SHELF_SAVING_ERROR);
			throw e;
		}
	}

	private void addShelfWithoutDeletingFsFiles(Shelf shelf, User user) throws PhotoAlbumException {
		addShelfCommon(shelf, user);
	}

	@Override
	public RetourReponse getRetourResponse(String error, boolean isOperationOK, Object result) {
		RetourReponse reponse = new RetourReponse();
		if (!isOperationOK) {
			reponse.setResult(false);
			reponse.setResultObject(error);
			return reponse;
		} else {
			reponse.setResult(true);
			reponse.setResultObject(result);
			return reponse;
		}
	}

	/**
	 * @return the searchService
	 */
	@Override
	public ISearchDao getSearchService() {
		return searchService;
	}

	/**
	 * @return the albumDBService
	 */
	@Override
	public IAlbumDao getAlbumDBService() {
		return albumDBService;
	}
	
	/**
	 * @return the albumDBService
	 */
	@Override
	public ICommentDao getCommentDBService() {
		return commentDBService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mycompany.smartalbum.back.service.impl.test#getImageDBService()
	 */
	@Override
	public IImageDao getImageDBService() {
		return imageDBService;
	}

	/**
	 * @return the shelfDBService
	 */
	@Override
	public IShelfDao getShelfDBService() {
		return shelfDBService;
	}
	
	/**
     * This method used to populate 'pre-defined shelves' tree
     * 
     * @return List of predefined shelves
     */
	@Override
    public List<ShelfInfos> getPublicShelvesInfos() {
        List<Shelf> shelfList = shelfDBService.getPredefinedShelves();
        UserInfos userInfos = null;
        ShelfInfos shelfInfos = null;
        Map<Long,UserInfos> alreadyComputedUserInfos = new HashMap<>();
        
        List<ShelfInfos> result = new ArrayList<>();
        for(Shelf currentShelf : shelfList){
        	Long currentUserInfosId = currentShelf.getOwner().getId();
        	userInfos = alreadyComputedUserInfos.get(currentUserInfosId);
        	if(userInfos == null){
	        	userInfos = mapper.map(currentShelf.getOwner(), UserInfos.class);
	        	userInfos.updateUser(new MappingOptions());
	        	alreadyComputedUserInfos.put(userInfos.getId(),userInfos);
        	}
        	shelfInfos = userInfos.getShelfWithId(currentShelf.getId());
        	result.add(shelfInfos);
        }
        return result;
    }
	

	/**
     * This method used to populate 'pre-defined shelves' tree
     * 
     * @return List of predefined shelves
     */
	@Override
    public List<ShelfInfos> getUserShelvesInfos() {
		User currentUser = getCurrentUser(false);
		UserInfos userInfos = mapper.map(currentUser, UserInfos.class);
		MappingOptions options = new MappingOptions();
//		options.setLoadingAlbumsImagesParent(true);
//		options.setLoadingAlbumsParent(true);
//		options.setLoadingShelvesParent(true);
//		options.setLoadingUserImagesParent(true);
		userInfos.updateUser(options);
        return userInfos.getShelves();
    }
	
	
	


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mycompany.smartalbum.back.service.impl.test#getFileUploadService()
	 */
	@Override
	public FileUploadService getFileUploadService() {
		return fileUploadService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mycompany.smartalbum.back.service.impl.test#getFileSystemService()
	 */
	@Override
	public FileService getFileSystemService() {
		return fileSystemService;
	}

	/**
	 * @return the userDBService
	 */
	@Override
	public IUserDao getUserDBService() {
		return userDBService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mycompany.smartalbum.back.service.impl.test#getMessageDBService()
	 */
	@Override
	public IMessageHTML getMessageDBService() {
		return messageDBService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mycompany.smartalbum.back.service.impl.test#getErrorHandler()
	 */
	@Override
	public ErrorHandlerBean getErrorHandler() {
		return errorHandler;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mycompany.smartalbum.back.service.impl.test#getCacheManager()
	 */
	@Override
	public HttpSessionCacheManager getCacheManager() {
		return cacheManager;
	}

	public Album initAlbum(AlbumForm albumForm) {

		Album newAlbum = new Album();
		newAlbum.setCreated(new Date());
		newAlbum.setName(albumForm.getName());
		newAlbum.setDescription(albumForm.getDescription());
		newAlbum.setId(null);

		List<Shelf> shelves = StringUtils.isNotBlank(albumForm.getSelectedOwnShelf())
				? albumForm.getUser().getShelves() : shelfDBService.getPredefinedShelves();

		// On recupère l'étagère dans lequel l'album sera rangée
		for (Shelf aShelf : shelves) {
			if (StringUtils.isNotBlank(albumForm.getSelectedOwnShelf())) {
				if (aShelf.getId() == Integer.parseInt(albumForm.getSelectedOwnShelf())) {
					newAlbum.setShelf(aShelf);
					aShelf.addAlbum(newAlbum);
					break;
				}
			} else if (StringUtils.isNotBlank(albumForm.getSelectedPubShelf())) {
				if (aShelf.getId() == Integer.parseInt(albumForm.getSelectedPubShelf())) {
					newAlbum.setShelf(aShelf);
					aShelf.addAlbum(newAlbum);
					break;
				}
			}
		}

		if (newAlbum.getShelf() == null && shelves.size() > 0) {

			Iterator<Shelf> it = shelves.iterator();
			if (it.hasNext()) {
				newAlbum.setShelf(it.next());
			}
		}
		return newAlbum;
	}

	public Shelf initShelf(ShelfForm shelfForm) {

		Shelf newShelf = null;

		if (shelfForm.getId() < 0) {
			newShelf = new Shelf();
			newShelf.setCreated(new Date());
			newShelf.setName(shelfForm.getName());
			newShelf.setDescription(shelfForm.getDescription());
			newShelf.setId(null);
			newShelf.setOwner(getCurrentUser(false));
			newShelf.setShared(shelfForm.isShared());
		} else {
			newShelf = getShelfDBService().findShelfById(shelfForm.getId());
			newShelf.setName(shelfForm.getName());
			newShelf.setDescription(shelfForm.getDescription());
			newShelf.setShared(shelfForm.isShared());
		}

		return newShelf;
	}

	public RetourReponse addAlbum(AlbumForm albumForm) {
		Album album = initAlbum(albumForm);
		String coverAlbumName = albumForm.getDefaultSelectedPicture().getName();
		if(StringUtils.isNotBlank(albumForm.getSelectedCoverAlbum())){
			String coverAlbumPath = albumForm.getSelectedCoverAlbum();
			String[] coverAlbumNameArray = coverAlbumPath.split(File.separator);
			int size = coverAlbumNameArray.length;
			coverAlbumName = coverAlbumNameArray[size - 1];
		}
		return saveAlbum(album, true, albumForm.getUser(), coverAlbumName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mycompany.smartalbum.back.service.impl.SmartAlbumBackService#
	 * findHTMLMessageById(java.lang.Long)
	 */
	@Override
	public String findHTMLMessageById(Long messageId) {

		MessageHTML message = messageDBService.findMessageHTMLById(messageId);
		StringBuilder finalMessage = new StringBuilder();
		if (message != null) {
			for (MessagePart part : message.getMessageLines()) {
				finalMessage.append(part.getHtmlPart());
			}
		}
		return finalMessage.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mycompany.smartalbum.back.service.impl.SmartAlbumBackService#
	 * createEmptyHTMLMessageInAlbum(java.lang.Long, java.lang.String)
	 */
	private MessageHTML createEmptyHTMLMessageInEntity(Long entitiId, MessageHTMLTypes type, String userLogin,
			Entities entity) throws PhotoAlbumException {

		MessageHTML aNewMessageHTML = new MessageHTML();
		aNewMessageHTML.setApp_code(ApplicationsEnum.SMARTALBUM_BACK.name());
		aNewMessageHTML.setMessage_type(type.name());
		aNewMessageHTML.setLang_code("FR");
		aNewMessageHTML.setUserLogin(userLogin);
		aNewMessageHTML.setUpdate_date(new Date());
		aNewMessageHTML.setLine_size(Constant.MESSAGE_LINE_PART_SIZE);
		messageDBService.save(aNewMessageHTML);
		
		if (Entities.IMAGE.equals(entity)) {
			Image anImage = imageDBService.findImageById(entitiId);
			aNewMessageHTML.setImage(anImage);
			aNewMessageHTML.setAlbum(null);
			anImage.addOrReplaceMessageHTML(aNewMessageHTML);
			imageDBService.updateImage(anImage);
		} else if (Entities.ALBUM.equals(entity)) {
			Album anAlbum = albumDBService.findAlbumById(entitiId);
			aNewMessageHTML.setAlbum(anAlbum);
			aNewMessageHTML.setImage(null);
			anAlbum.getMessagesHTML().add(aNewMessageHTML);
			albumDBService.editAlbum(anAlbum);
		}
		return aNewMessageHTML;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mycompany.smartalbum.back.service.impl.SmartAlbumBackService#
	 * createHTMLMessageInAlbum(java.lang.Long, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public MessageHTML createOrUpdateHTMLMessageInEntity(Long entityId, String message, MessageHTMLTypes type,
			String login, Entities entity) throws PhotoAlbumException {

		Map<Integer, String> messageParts = getSplitedMessage(message, MESSAGEPART_SIZE);
		MessageHTML messageHtml = createEmptyHTMLMessageInEntity(entityId, type, login, entity);

		Iterator<Entry<Integer, String>> it = messageParts.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Integer, String> entry = (Map.Entry<Integer, String>) it.next();
			MessagePart part = new MessagePart();
			part.setHtmlPart(entry.getValue());
			part.setMessageHTML(messageHtml);
			part.setOrder(entry.getKey() + "");
			messagePartDBService.addMessagePart(part);
			if (Entities.IMAGE.equals(entity)) {
				messageDBService.editMessageHTML(messageHtml, Entities.IMAGE);
			} else {
				messageDBService.editMessageHTML(messageHtml, Entities.ALBUM);
			}
			it.remove(); // avoids a ConcurrentModificationException
		}
		// messageHtml.updateHTMLDescription();
		// On met à jour la description du message qui sera affichee sur le
		// front
		return messageHtml;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * java.lang.String)
	 */
	@Override
	public MessageHTML initHTMLMessageInEntity(String message, MessageHTMLTypes type, String login) throws PhotoAlbumException {

		Map<Integer, String> messageParts = getSplitedMessage(message, MESSAGEPART_SIZE);
		MessageHTML aNewMessageHTML = new MessageHTML();
		aNewMessageHTML.setApp_code(ApplicationsEnum.SMARTALBUM_BACK.name());
		aNewMessageHTML.setMessage_type(type.name());
		aNewMessageHTML.setLang_code("FR");
		aNewMessageHTML.setUserLogin(login);
		aNewMessageHTML.setUpdate_date(new Date());
		aNewMessageHTML.setLine_size(Constant.MESSAGE_LINE_PART_SIZE);
		aNewMessageHTML.setTitle("AUCUNE DESCRIPTION");
		aNewMessageHTML.setHtmlDescription("NON DECRIT LORS DE SA CREATION LE "+aNewMessageHTML.getUpdate_date());
		messageDBService.save(aNewMessageHTML);

		Iterator<Entry<Integer, String>> it = messageParts.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Integer, String> entry = (Map.Entry<Integer, String>) it.next();
			MessagePart part = new MessagePart();
			part.setHtmlPart(entry.getValue());
			part.setMessageHTML(aNewMessageHTML);
			part.setOrder(entry.getKey() + "");
			messagePartDBService.addMessagePart(part);
			it.remove(); // avoids a ConcurrentModificationException
		}
		return aNewMessageHTML;
	}
	
	@Override
	public void addHTMLMessageInNewTmpImage(final User currentUser, final Image fsInitializedImage, final MessageHTMLTypes messageType, final String defaultMessage) throws PhotoAlbumException {
		if(currentUser != null){
			fsInitializedImage.setUser(currentUser);
			fsInitializedImage.setAlbum(null);
			getImageDBService().addImageInUserTmp(fsInitializedImage);
			addHTMLMessageInImage(currentUser.getLogin(), fsInitializedImage, messageType, defaultMessage, false);
		}
	}
	
	@Override
	public void addHTMLMessageInClassifiedImage(String login,final Image fsInitializedImage, final MessageHTMLTypes messageType, final String defaultMessage) throws PhotoAlbumException {
		if(fsInitializedImage.getAlbum()!=null){
			fsInitializedImage.setUser(null);
			getImageDBService().addImage(fsInitializedImage);
			addHTMLMessageInImage(login, fsInitializedImage, messageType, defaultMessage, true);
		}
	}
	
	@Override
	public void updateHTMLMessageInEntity(Long messageId, String message, Entities entity, String title) throws PhotoAlbumException {
		MessageHTML messageHtml = messageDBService.findMessageHTMLById(messageId);
		messageHtml.setTitle(title);
		messagePartDBService.removeAllMessagePart(messageHtml);
		// DEBUG
		Map<Integer, String> messageParts = getSplitedMessage(message, MESSAGEPART_SIZE);
		Iterator<Entry<Integer, String>> it = messageParts.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Integer, String> entry = (Map.Entry<Integer, String>) it.next();
			MessagePart part = new MessagePart();
			part.setHtmlPart(entry.getValue());
			part.setMessageHTML(messageHtml);
			part.setOrder(entry.getKey() + "");
			messagePartDBService.addMessagePart(part);
			it.remove(); // avoids a ConcurrentModificationException
		}
		messageDBService.editMessageHTML(messageHtml, Entities.IMAGE.equals(entity) ? Entities.IMAGE : Entities.ALBUM);
	}

	@Override
	public boolean deleteHTMLMessageById(Long messageId) throws PhotoAlbumException {

		MessageHTML message = messageDBService.findMessageHTMLById(messageId);
		if (message != null) {
			messageDBService.deleteMessageHTML(message, Entities.IMAGE);
			return true;
		}
		return false;
	}

	@Override
	public MessageHTML createEntityHTMLMessage(Object entity, final MessageHTMLTypes typeDeMessage, String messageToPrint, String ownerLogin)
			throws PhotoAlbumException {

		if (entity == null) {
			return null;
		}
		Image anImage = null;
		Album anAlbum = null;
		MessageHTML result = null;
		boolean imgInAlbum = false;

		// Dans le cas d'une image on récupère l'image correspondant
		if (entity instanceof Image) {
			anImage = (Image) entity;
			imgInAlbum = (anImage.getAlbum() != null);
		}
		// Dans le cas d'un album on récupère l'album correspondant
		if (entity instanceof Album) {
			anAlbum = (Album) entity;
		}
		
		if (anAlbum!=null && !anAlbum.getMessagesHTML().isEmpty()) {
			MessageHTML message = anAlbum.getMessageHTMLByType(typeDeMessage);
			if (message!=null && message.getMessage_type().equals(typeDeMessage.name())) {
				LOG.warn("Un message HTML du type {} existe dejà dans l'album {}", typeDeMessage.name(),
						anAlbum.toString());
				result = message;
			}
		}
		
		if (result == null) {
			switch (typeDeMessage) {
			case SHORTDESCRIPTION:
				if(anImage!=null){
					addHTMLMessageInImage(ownerLogin,anImage, MessageHTMLTypes.SHORTDESCRIPTION,messageToPrint, imgInAlbum);
				}
				else if(anAlbum!=null){
					addHTMLMessageInNewAlbum(ownerLogin,anAlbum, MessageHTMLTypes.SHORTDESCRIPTION,messageToPrint);
				}
				break;
			case LONGDESCRIPTION:
				if(anImage!=null){
					addHTMLMessageInImage(ownerLogin,anImage, MessageHTMLTypes.LONGDESCRIPTION,messageToPrint, imgInAlbum);
				}
				else if(anAlbum!=null){
					addHTMLMessageInNewAlbum(ownerLogin,anAlbum, MessageHTMLTypes.LONGDESCRIPTION,messageToPrint);
				}
				break;
			default:
				break;
			}
		}
		return result;
	}

	@Override
	public boolean dropAlbumToShelf(Long albumId, Long shelfId) {
		Album albumToDrop = getAlbumDBService().findAlbumById(albumId);

		Shelf newShelf = getShelfDBService().findShelfById(shelfId);

		if (albumToDrop != null && newShelf != null) {
			if (!newShelf.getId().equals(albumToDrop.getShelf().getId())) {
				String oldPath = albumToDrop.getPath();

				if (dnDHelper.processDrop(albumToDrop, newShelf)) {
					// Process drop is ok in the database, now process drop on
					// the fs
					getFileSystemService().renameAlbumDirectory(albumToDrop, oldPath);
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public boolean dropImagesToAlbum(Long albumId, List<Image> images) {
		Album albumToDrop = getAlbumDBService().findAlbumById(albumId);
		if (albumToDrop != null && !images.isEmpty()) {
			if (dnDHelper.processDrop(images, albumToDrop)) {
				// Process drop is ok in the database,
				//
				return true;
			}
		}

		return false;
	}

	public boolean renameImageInAlbum(Long imageId, String newImageName, Boolean isTmpImage, String imageOldName) {
		boolean result = false;
		try {
				Image imageToRename = getImageDBService().findImageById(imageId);
				String pathOld = imageToRename.getFullPath();
				
				// Pour renommer une image il suffit juste que le nouveau nom diffère de l'ancien nom
				if (imageToRename != null && StringUtils.isNotBlank(newImageName)) {
					if (newImageName.equals(imageToRename.getName())) {
						return true;
					}
					imageToRename.setName(newImageName);
					imageToRename.setPath(newImageName);
					imageDBService.updateImage(imageToRename);
				}
				
				// Ensuite, suivant qu'on renomme un répertoire du dossier temporaire de l'utilisateur connecté ou non on met à jour le file système
				if (!isTmpImage) {
					if (pathOld != null && fileSystemService.renameImageFile(imageToRename, pathOld)) {
						result = true;
					}
				}
				else{
					pathOld = File.separator+getCurrentUser(true).getLogin()+File.separator+imageOldName;
					String imageNewFullPath = File.separator+getCurrentUser(true).getLogin()+File.separator+newImageName;
					if (fileSystemService.renameImageFile(imageNewFullPath, pathOld)) {
						result = true;
					}
				}
		} catch (PhotoAlbumException e) {
			LOG.error("Impossible de renommer l'image ", e);
		}
		return result;
	}

	public Image initImageOnFs(final String directory, String imageName, Album anAlbum, boolean isExistingAlbum) {
		FileMeta item = getFileUploadService().getFileFromFs(directory, imageName, ImageDimension.ORIGINAL);
		return initImage(item, anAlbum);
	}
	
	

	@Override
	public void modifyCurrentAlbum(AlbumForm anAlbumForm) throws PhotoAlbumException {
		Album currentAlbum = null;
		Object obj = getCacheManager().getObjectFromCache(Constant.SMARTALBUM_PHOTOS_CURRENT_ALBUM);
		if (obj != null) {
			currentAlbum = (Album) obj;
			Album managedAlbumEntity = getAlbumDBService().findAlbumById(currentAlbum.getId());
			if (managedAlbumEntity != null) {
				String imageName = anAlbumForm.getSelectedCoverAlbum();
				if(imageName!=null){
					String[] imageNameTab = imageName.split(File.separator);
					imageName = imageNameTab[imageNameTab.length-1];
				}
				Image newCoveringImage = currentAlbum.getCoveringImage();
				// 1 - On modifie l'image de couverture si nécessaire
				if (!currentAlbum.getCoveringImage().getName().equals(imageName)) {
					newCoveringImage = getImageDBService().findImageByNameAndAlbumId(imageName,managedAlbumEntity.getId());
					newCoveringImage.setCovering(true);
					managedAlbumEntity.getCoveringImage().setCovering(false);
					managedAlbumEntity.setCoveringImage(newCoveringImage);}
				else{
					newCoveringImage = getImageDBService().findImageByNameAndAlbumId(imageName, currentAlbum.getId());
				}
				// 2 - On modifie la description et le nom de l'image
				managedAlbumEntity.setDescription(anAlbumForm.getDescription());
				managedAlbumEntity.setName(anAlbumForm.getName());

				// 3 - On enregistre l'album ainsi mis à jour
				if (!saveAlbum(managedAlbumEntity, false, getCurrentUser(false), newCoveringImage.getName()).getResult().booleanValue()) {
					throw new PhotoAlbumException("Impossible de sauvegarder l'album " + managedAlbumEntity.getName());
				}
				// 4 - On déplace l'album ainsi modifié si nécessaire vers le
				// nouvel étagère
				Long newShelf = Long.parseLong(anAlbumForm.getSelectedOwnShelf());
				if (newShelf != managedAlbumEntity.getShelf().getId()) {
					if (!dropAlbumToShelf(managedAlbumEntity.getId(), newShelf)) {
						throw new PhotoAlbumException("Impossible de déplacer l'album " + managedAlbumEntity.getName()
								+ " des l'étagère " + newShelf);
					}
				}
				// 5 - On sauvegarde l'album courant dant le cache
				getCacheManager().putObjectInCache(Constant.SMARTALBUM_PHOTOS_CURRENT_ALBUM, managedAlbumEntity);
			}
		} else {
			throw new PhotoAlbumException(
					"Impossible de modifier l'album " + anAlbumForm.getName() + ", veuiller réactualiser votre page!");
		}
	}

	/**
	 * Listenet, that invoked during file upload process. Only registered users
	 * can upload images.
	 * 
	 * @param imageName
	 *            TODO
	 * @param imageFullPath
	 *            TODO
	 * @param event
	 *            - event, indicated that file upload started
	 */
	public void saveImageToDisc(String userLogin, boolean isExistingAlbum, String imageName, String imageFullPath) {
		// Save to disk
		String currentWorkDir = isExistingAlbum ? rootWorkFile + File.separator + userLogin
				: tmpFile + File.separator + userLogin;
		if (!fileSystemService.addImage(imageFullPath, currentWorkDir + File.separator + imageName, isExistingAlbum)) {
		}
	}

	private Image initImage(final FileMeta item, Album anAlbum) {
		Image image = getFileUploadService().constructImage(item);
		try {
			// Extract metadata(size, camera model etc..)
			getFileUploadService().extractMetadata(item, image);
		} catch (Exception e1) {
			return null;
		}
		image.setAlbum(anAlbum);
		if (image.getAlbum() == null) {
			return null;
		}
		try {
			// Check if image with given name already exist
			if (imageDBService.isImageWithThisPathExist(image.getAlbum(), image.getPath())) {
				// If exist generate new path for image
				String newPath = getFileUploadService().generateNewPath(image);
				image.setPath(newPath);
				image.setName(newPath);
			}

		} catch (Exception e) {
			LOG.debug("Impossible de rajouter une image", e);
			return null;
		}
		return image;
	}

	@Override
	public String getCoveringPositionImageIdInWorkDirectory(Album album) {
		String result = "";
		String work_dir = Constant.WORK_DIR + album.getPath();
		File tmp = new File(work_dir);
		FileFilter filter = new FileFilter();
		String[] fileNames = filter.execute(ImageDimension.ORIGINAL, tmp);
		int absolutePositionInWorkDir = -1;
		for (int i = 0; i < fileNames.length; i++) {
			if (fileNames[i].equals(album.getCoveringImage().getName())) {
				absolutePositionInWorkDir = i;
				break;
			}
		}
		if (absolutePositionInWorkDir > -1) {
			result = absolutePositionInWorkDir / Constant.PAGE_SIZE + "/"
					+ absolutePositionInWorkDir % Constant.PAGE_SIZE;
		}
		return result;
	}

	@Override
	public String getCoveringNameFromPageAndPosition(int page, int position, String albumPath) {
		String work_dir = Constant.WORK_DIR + albumPath;
		File tmp = new File(work_dir);
		FileFilter filter = new FileFilter();
		String[] fileNames = filter.execute(ImageDimension.ORIGINAL, tmp);
		String imageName = "";
		for (int i = 0; i < fileNames.length; i++) {
			int computedPage = i / Constant.PAGE_SIZE;
			int computedPosition = i % Constant.PAGE_SIZE;
			if (page == computedPage && position == computedPosition) {
				imageName = fileNames[i];
				break;
			}
		}

		return imageName;
	}
	/**
	 * Returns an album vo corresponding to the given album id
	 * @param albumId
	 * @return The album VO
	 */
	@Override
	public Album findAlbumById(String albumId)
	{
		Album album = albumDBService.findAlbumById(Long.parseLong(albumId));
		return album;
	}
	
	@Override
	public AlbumVOForm findAlbumFormById(String albumId)
	{
		Album album = albumDBService.findAlbumById(Long.parseLong(albumId));
		final AlbumVOForm result = mapper.map(album, AlbumVOForm.class);
		result.setPath(album.getPath());
		return result;
	}
	
	/**
	 * Returns an album vo corresponding to the given album id
	 * @param albumId
	 * @return The album VO
	 * @throws PhotoAlbumException Exception renvoyée en cas d'erreure
	 */
	@Override
	public SearchDataTableResponse<Album> findAlbumBySearchRequest(SearchDataTableRequest request) throws PhotoAlbumException
	{
		if(request.getSortColumn() == null || "0".equals(request.getSortColumn())){
			request.setSortColumn("id");
		}
		Field[] fields = Album.class.getDeclaredFields();
        for (Field field : fields) {
        	if(field.getName().toLowerCase().equals(request.getSortColumn().toLowerCase())){
        		request.setSortColumnClass(field.getType());
        	}
        }
        SearchDataTableResponse<Album> response = null;
        if(request.getEntityId()>0){
        	Album album = albumJpaDBService.findOne(request.getEntityId());
        	album.getImages().clear();
        	response = findSearDataTableFromDatabase(request,album);
        }
        else
        {
        	Album album = new Album();
        	album.setId(-1L);
        	response = findSearDataTableFromDatabase(request,album);
        }
		return response;
	}


	private SearchDataTableResponse<Album> findSearDataTableFromDatabase(SearchDataTableRequest request, Album album) throws PhotoAlbumException {
		DataTableEntity<Image> imageDataTable = imageDBService.findImagesBySearchRequest(request, album.getId()<0);
		for(ABuisnessObject<Long> entity : imageDataTable.getData()){
			album.getImages().add((Image)entity);
		}
		//final AlbumVO albumVO = mapper.map(album, AlbumVO.class);
		
		SearchDataTableResponse<Album> response = null;
		if(album != null){
			response = new SearchDataTableResponse<Album>();
			response.setEntitiObject(album);
			response.setDraw(request.getDraw());
			response.setRecordsFiltered(imageDataTable.getRecordsFiltered());
			response.setRecordsTotal(imageDataTable.getRecordsTotal());
		}
		return response;
	}

	@Override
	public AlbumVO findAlbumVOById(String albumId) {
		Album album = albumDBService.findAlbumById(Long.parseLong(albumId));
		final AlbumVO result = mapper.map(album, AlbumVO.class);
		return result;
	}

	/**
	 * @return the metaTagDBService
	 */
	public IMetaTagDao getMetaTagDBService() {
		return metaTagDBService;
	}

	/**
	 * @param metaTagDBService the metaTagDBService to set
	 */
	public void setMetaTagDBService(IMetaTagDao metaTagDBService) {
		this.metaTagDBService = metaTagDBService;
	}
	
	/**
	 * Method, that invoked when user click 'Edit album' button. Only registered
	 * users can edit albums.
	 * 
	 * @param album
	 *            - edited album
	 * @param editFromInplace
	 *            - indicate whether edit process was initiated by inplaceInput
	 *            component
	 */
	@Override
	public void editAlbum(final Album album, final boolean editFromInplace) {
		try {
			if (getCurrentUser(false).hasAlbumWithName(album)) {
				getErrorHandler().addToErrors(
						Constants.SAME_ALBUM_EXIST_ERROR);
				return;
			}
			if (editFromInplace) {
				// // We need validate album name manually
				// ClassValidator<Album> shelfValidator = new
				// ClassValidator<Album>(Album.class);
				// InvalidValue[] validationMessages =
				// shelfValidator.getInvalidValues(album, "name");
				// if (validationMessages.length > 0) {
				// for (InvalidValue i : validationMessages) {
				// backService.getErrorHandler().addToErrors(i.getMessage());
				// }
				// // If error occured we need refresh album to display correct
				// value in inplaceInput
				// backService.getAlbumDBService().resetAlbum(album);
				// return;
				// }
			}
			getAlbumDBService().editAlbum(album);
		} catch (Exception e) {
			getErrorHandler().addToErrors(
					Constants.ALBUM_SAVING_ERROR);
			LOG.error("Impossible de mettre à jour l'album {}! ",
					album.getName(), e);
			return;
		}
	}
	
	/**
	 * Method, that invoked when user click 'Delete album' button. Only
	 * registered users can delete albums.
	 * 
	 * @param album
	 *            - album to delete
	 */
	@Override
	public void deleteAlbum(final Album album) {
		
		try {
			String pathToDelete = album.getPath();
			getAlbumDBService().deleteAlbum(album);
			// Raise 'albumDeleted' event, parameter path - path of Directory to
			// delete
			getFileSystemService().onAlbumDeleted(album, pathToDelete);
		} catch (Exception e) {
			getErrorHandler().addToErrors(
					Constants.ALBUM_DELETING_ERROR);
			if(album!=null){
			LOG.error("Impossible de supprimer l'album {}", album.getId(), e);
			}
			return;
		}
	}
}
