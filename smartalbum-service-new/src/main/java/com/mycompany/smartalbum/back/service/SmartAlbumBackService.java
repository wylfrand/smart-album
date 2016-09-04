package com.mycompany.smartalbum.back.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mycompany.database.smartalbum.exception.PhotoAlbumException;
import com.mycompany.database.smartalbum.model.Album;
import com.mycompany.database.smartalbum.model.Image;
import com.mycompany.database.smartalbum.model.MessageHTML;
import com.mycompany.database.smartalbum.model.User;
import com.mycompany.database.smartalbum.services.IAlbumDao;
import com.mycompany.database.smartalbum.services.IImageDao;
import com.mycompany.database.smartalbum.services.IMessageHTML;
import com.mycompany.database.smartalbum.services.ISearchDao;
import com.mycompany.database.smartalbum.services.IShelfDao;
import com.mycompany.database.smartalbum.services.IUserDao;
import com.mycompany.filesystem.model.FileMeta;
import com.mycompany.filesystem.service.FileService;
import com.mycompany.filesystem.service.FileUploadService;
import com.mycompany.services.model.commun.enumeration.MessageHTMLTypes;
import com.mycompany.services.utils.ErrorHandlerBean;
import com.mycompany.services.utils.HttpSessionCacheManager;
import com.mycompany.services.utils.Pagination;
import com.mycompany.services.utils.RetourReponse;
import com.mycompany.services.utils.SelectedPicture;
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
    
     void saveSelectedImages(Album anAlbum, int coverPageIndex, int coverPhotoIndex, boolean isExistingAlbum);
    
     void saveSelectedImages(Album anAlbum, boolean isCreation);
    
     Map<String, String> getSelectedFiles();
    
     void removeSelectedFilesByName();
    
     void removeAllFilesByName();
    
    /**.
     * Methode permettant de calculer la liste des fichiers 
     * sélectionnés pour chaque page
     * @return Les fichiers sélecionnés par page
     */
     Map<String, List<String>> getSelectedFilesByPage();
    
     List<SelectedPicture> getSelectedPicturesArray();
    
     User getCurrentUser(boolean fromCache);
    
     int getAbsoluteIndexOfImageInTmp(int page, int position);
    
     BufferedImage rotatePerfectSquares(BufferedImage image, int _thetaInDegrees);
    
     void updateFilesToPrint(int targetPage, File tmp, Pagination aPagination, LinkedList<FileMeta> files,
                                   Set<Image> images);
    
     int getFileCount(File tmp);
    
     void updateSelectedPictures(int currentPage, LinkedList<FileMeta> files);
    
     void deleteEmptyAlbums();
    
     void synchronizeShelfWithDatabase(String shelfRootDirectoryPath, String shelfId);
    
     void synchronizeShelvesWithDatabase();
    
     Map<Integer, String> getSplitedMessage(String message, int size);
    
     RetourReponse addAlbum(Album newAlbum, boolean isCreation);
    
     RetourReponse saveAlbum(Album album, int coverPageIndex, int coverPhotoIndex, boolean isCreation);
    
     RetourReponse getRetourResponse(String error, boolean isOperationOK, Object result);
     
     /**
      * @return the imageDBService
      */
     IImageDao getImageDBService();
     
     /**
      * @return the fileUploadService
      */
     FileUploadService getFileUploadService();
     
     /**
      * @return the AlbumDBService
      */
     IAlbumDao getAlbumDBService();
     
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
     
     MessageHTML createOrUpdateHTMLMessageInImage(Long imageId, String message, MessageHTMLTypes type, String login) throws PhotoAlbumException;
     
     boolean deleteHTMLMessageById(Long messageId) throws PhotoAlbumException;
     
     MessageHTML createImageHTMLMessage(Image image, MessageHTMLTypes typeDeMessage) throws PhotoAlbumException;

    void updateHTMLMessageInImage(Long messageId, String message) throws PhotoAlbumException;
    
    RetourReponse addOrModifyShelf(ShelfForm aShelfForm);
    
    void updateFilesInfos(final int targetPage, final File tmp, Pagination aPagination,
                                   LinkedList<FileMeta> files, Set<Image> images);
    
    boolean dropAlbumToShelf(Long albumId,Long shelfId);
}
