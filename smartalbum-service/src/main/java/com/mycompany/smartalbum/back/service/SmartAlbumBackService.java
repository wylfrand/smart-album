package com.mycompany.smartalbum.back.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mycompany.database.smartalbum.exception.PhotoAlbumException;
import com.mycompany.database.smartalbum.model.Album;
import com.mycompany.database.smartalbum.model.Image;
import com.mycompany.database.smartalbum.model.MessageHTML;
import com.mycompany.database.smartalbum.model.Shelf;
import com.mycompany.database.smartalbum.model.User;
import com.mycompany.database.smartalbum.search.enums.MessageHTMLTypes;
import com.mycompany.database.smartalbum.search.vo.SearchDataTableRequest;
import com.mycompany.database.smartalbum.search.vo.SearchDataTableResponse;
import com.mycompany.database.smartalbum.services.IAlbumDao;
import com.mycompany.database.smartalbum.services.ICommentDao;
import com.mycompany.database.smartalbum.services.IImageDao;
import com.mycompany.database.smartalbum.services.IMessageHTML;
import com.mycompany.database.smartalbum.services.IMetaTagDao;
import com.mycompany.database.smartalbum.services.ISearchDao;
import com.mycompany.database.smartalbum.services.IShelfDao;
import com.mycompany.database.smartalbum.services.IUserDao;
import com.mycompany.database.smartalbum.utils.Entities;
import com.mycompany.filesystem.model.CheckedFile;
import com.mycompany.filesystem.service.FileService;
import com.mycompany.filesystem.service.FileUploadService;
import com.mycompany.filesystem.service.ImageDimension;
import com.mycompany.services.smartalbum.infos.UserInfos;
import com.mycompany.services.smartalbum.vo.AlbumVO;
import com.mycompany.services.smartalbum.vo.ShelfVO;
import com.mycompany.services.smartalbum.vo.form.AlbumVOForm;
import com.mycompany.services.utils.ErrorHandlerBean;
import com.mycompany.services.utils.HttpSessionCacheManager;
import com.mycompany.services.utils.RetourReponse;
import com.mycompany.smartalbum.back.form.AlbumForm;
import com.mycompany.smartalbum.back.form.ShelfForm;

 public interface SmartAlbumBackService {
    
    /**
     * Retourne un objet {@link Album} créé é partir d'un objet {@link AlbumForm}
     * 
     * @param shelfForm , L'objet représentant le formulaire d'un Album
     * @return un objet {@link Album} contenant les infos base de donnée d'un album
     */
     Album initSynchronizedAlbum(String albumOldName);
    
     void saveSelectedImages(Album anAlbum, String coverImageName, boolean isExistingAlbum) throws PhotoAlbumException;
    
     void saveSelectedImages(Album anAlbum, boolean isCreation);
    
     Map<String, CheckedFile> getSelectedFiles();
    
     void removeSelectedFilesByName(boolean fromExistingAlbum) throws PhotoAlbumException;
    
     void removeAllFilesFromTmpByName() throws PhotoAlbumException;
    
     User getCurrentUser(boolean fromCache);
    
     BufferedImage rotatePerfectSquares(BufferedImage image, int _thetaInDegrees);
    
     int getFileCountByDimension(File tmp, ImageDimension dimention);
    
     void deleteEmptyAlbums(String userLoginToSynchronize);
    
     void synchronizeShelfWithDatabase(String shelfRootDirectoryPath, String shelfId, User user) throws PhotoAlbumException;
    
     Map<Integer, String> getSplitedMessage(String message, int size);
    
     RetourReponse addAlbum(Album newAlbum, boolean isCreation);
    
     RetourReponse saveAlbum(Album album, boolean isCreation, User user, String coverAlbumName);
    
     RetourReponse getRetourResponse(String error, boolean isOperationOK, Object result);
     
     /**
      * @return the imageDBService
      */
     IImageDao getImageDBService();
     
     /**
      * @return the imageDBService
      */
     IMetaTagDao getMetaTagDBService();
     
     /**
      * @return the fileUploadService
      */
     FileUploadService getFileUploadService();
     
     /**
      * @return the AlbumDBService
      */
     IAlbumDao getAlbumDBService();
     
     /**
      * @return the commentDBService
      */
     ICommentDao getCommentDBService();
     
     /**
      * @return the shelfDBService
      */
     IShelfDao getShelfDBService();
     
     /**
      * @return the SearchService
      */
     ISearchDao getSearchService() ;
     
     /**
      * @return the fileSystemService
      */
     FileService getFileSystemService();
     
     /**
      * @return the messageDBService
      */
     IMessageHTML getMessageDBService();
     
     IUserDao getUserDBService();
     
     /**
      * @return the errorHandler
      */
     ErrorHandlerBean getErrorHandler();
     
     /**
      * @return the cacheManager
      */
     HttpSessionCacheManager getCacheManager();
     
     Album initAlbum(AlbumForm albumForm);
     
     RetourReponse addAlbum(AlbumForm albumForm);
     
     String findHTMLMessageById(Long messageId);
     
     MessageHTML createOrUpdateHTMLMessageInEntity(Long entityId, String message, MessageHTMLTypes type, String login, Entities entity) throws PhotoAlbumException;
     
     boolean deleteHTMLMessageById(Long messageId) throws PhotoAlbumException;
     
     MessageHTML createEntityHTMLMessage(Object entity, MessageHTMLTypes typeDeMessage, String message, String login) throws PhotoAlbumException;

    void updateHTMLMessageInEntity(Long messageId, String message, Entities entity, String title) throws PhotoAlbumException;
    
    RetourReponse addOrModifyShelf(ShelfForm aShelfForm);
    
    boolean dropAlbumToShelf(Long albumId,Long shelfId);
    
    void saveImageToDisc(String userLogin, boolean isExistingAlbum,
			String imageName, String imageFullPath);
    
	boolean dropImagesToAlbum(Long albumId, List<Image> images);
	
	Shelf initSynchronizedShelf(String shelfOldId, User user);

	void modifyCurrentAlbum(AlbumForm anAlbumForm) throws PhotoAlbumException;

	String getCoveringPositionImageIdInWorkDirectory(Album album);

	String getCoveringNameFromPageAndPosition(int page, int position,
			String albumPath);
	boolean renameImageInAlbum(Long imageId, String newImageName, Boolean isTmpImage, String imageOldName);
	AlbumVO findAlbumVOById(String albumId);
	SearchDataTableResponse<Album> findAlbumBySearchRequest(SearchDataTableRequest request) throws PhotoAlbumException;

	void removeAllPicturesFromCurrentAlbum() throws PhotoAlbumException;

	void toogleSelectAllFiles() throws PhotoAlbumException;

	AlbumVOForm findAlbumFormById(String albumId);

	UserInfos findCurrentUserInfos(boolean fromCache);

	Set<ShelfVO> getPredefinedShelvesVO();
	List<ShelfVO> getUserShelvesVO();

	MessageHTML initHTMLMessageInEntity(String message, MessageHTMLTypes type, String login) throws PhotoAlbumException;

	Album findAlbumById(String albumId);

	void addHTMLMessageInNewTmpImage(User currentUser, Image image, MessageHTMLTypes messageType, String defaultMessage)
			throws PhotoAlbumException;

	void addHTMLMessageInClassifiedImage(String login, Image fsInitializedImage, MessageHTMLTypes messageType,
			String defaultMessage) throws PhotoAlbumException;

	void deleteAlbum(Album album);

	void editAlbum(Album album, boolean editFromInplace);
	
}
