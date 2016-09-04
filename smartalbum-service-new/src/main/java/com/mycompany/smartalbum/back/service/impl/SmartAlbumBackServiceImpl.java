package com.mycompany.smartalbum.back.service.impl;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.mycompany.database.smartalbum.exception.PhotoAlbumException;
import com.mycompany.database.smartalbum.model.Album;
import com.mycompany.database.smartalbum.model.Image;
import com.mycompany.database.smartalbum.model.MessageHTML;
import com.mycompany.database.smartalbum.model.MessagePart;
import com.mycompany.database.smartalbum.model.Shelf;
import com.mycompany.database.smartalbum.model.User;
import com.mycompany.database.smartalbum.services.IAlbumDao;
import com.mycompany.database.smartalbum.services.IImageDao;
import com.mycompany.database.smartalbum.services.IMessageHTML;
import com.mycompany.database.smartalbum.services.ISearchDao;
import com.mycompany.database.smartalbum.services.IShelfDao;
import com.mycompany.database.smartalbum.services.IUserDao;
import com.mycompany.database.smartalbum.utils.Constants;
import com.mycompany.filesystem.model.FileMeta;
import com.mycompany.filesystem.service.FileService;
import com.mycompany.filesystem.service.FileUploadService;
import com.mycompany.services.model.commun.enumeration.ApplicationsEnum;
import com.mycompany.services.model.commun.enumeration.MessageHTMLTypes;
import com.mycompany.services.utils.Constant;
import com.mycompany.services.utils.ErrorHandlerBean;
import com.mycompany.services.utils.HttpSessionCacheManager;
import com.mycompany.services.utils.Pagination;
import com.mycompany.services.utils.RetourReponse;
import com.mycompany.services.utils.SelectedPicture;
import com.mycompany.smartalbum.back.form.AlbumForm;
import com.mycompany.smartalbum.back.form.ShelfForm;
import com.mycompany.smartalbum.back.service.SmartAlbumBackService;

@Component
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
    private IImageDao imageDBService;
    
    @Resource
    private IShelfDao shelfDBService;
    
    @Resource
    private FileUploadService fileUploadService;
    
    @Resource
    private FileService fileSystemService;
    
    @Resource
    private IUserDao userDBService;
    
    @Resource
    private IMessageHTML messageDBService;
    
    @Resource
    private ErrorHandlerBean errorHandler;
    
    @Resource(name = "httpSessionCacheManager")
    private HttpSessionCacheManager cacheManager;
    
    @Value("${smartalbum.filesystem.upload.root.path}")
    private String picturesRootPath;
    
    private static final int MESSAGEPART_SIZE = 512;
    
    private static final String DEFAULT_SHORT_MESSAGE = "<h1 align=\"center\"><br /></h1><h1 align=\"center\"><font color=\"#0000FF\" face=\"comic sans ms\">LE MESSAGE DESCRIPTIF DE CET IMAGE N'A PAS ENCORE ETE CREE<br /></font></h1>";
    
    private static final String DEFAULT_LONG_MESSAGE = "<h1 align=\"center\"><br /></h1><h1 align=\"center\"><font color=\"#0000FF\" face=\"comic sans ms\">LE MESSAGE DETAILLE DECRIVANT CET IMAGE N'A PAS ENCORE ETE CREE<br /></font></h1>";
    
    private final static transient Logger log = LoggerFactory.getLogger(SmartAlbumBackServiceImpl.class);
    
    /**
     * Retourne un objet {@link Album} créé é partir d'un objet {@link AlbumForm}
     * 
     * @param shelfForm , L'objet représentant le formulaire d'un Album
     * @return un objet {@link Album} contenant les infos base de donnée d'un album
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
        newAlbum.setShowAfterCreate(true);
        return newAlbum;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.mycompany.smartalbum.back.service.impl.SmartAlbumBackService#
     * saveSelectedImages(com.mycompany.database.smartalbum.model.Album, int,
     * int, boolean)
     */
    @Override
    public void saveSelectedImages(Album anAlbum, final int coverPageIndex, final int coverPhotoIndex,
                                   boolean isExistingAlbum) {
        List<SelectedPicture> selectedPictures = getSelectedPicturesArray();
        String currentLogin = getCurrentUser(true).getLogin();
        for (SelectedPicture picture : selectedPictures) {
            int pageIndex = Integer.parseInt(picture.getPageIndex());
            int photoIndex = Integer.parseInt(picture.getPhotoIndex());
            
            Image currentImg = fileUploadService.saveImage(Constant.TMP_DIR + File.separator + currentLogin, pageIndex,
                                                           photoIndex, Constant.PAGE_SIZE, anAlbum, isExistingAlbum);
            if (currentImg != null) {
                if (pageIndex == coverPageIndex && photoIndex == coverPhotoIndex) {
                    currentImg.setCovering(true);
                    anAlbum.setCoveringImage(currentImg);
                    try {
                        albumDBService.editAlbum(anAlbum);
                    } catch (PhotoAlbumException e) {
                        log.error("Impossible de sauvegarder la couverture en BDD,", e);
                    }
                } else {
                    
                }
                
            }
        }
        resetSelectedFiles();
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
            List<SelectedPicture> selectedPictures = getSelectedPicturesArray();
            String filesSource = Constant.TMP_DIR+getCurrentUser(true).getLogin()+File.separator;
            for (SelectedPicture picture : selectedPictures) {
                Image currentImg = fileUploadService.saveImage(filesSource,
                                                               Integer.parseInt(picture.getPageIndex()),
                                                               Integer.parseInt(picture.getPhotoIndex()),
                                                               Constant.PAGE_SIZE, anAlbum, !isCreation);
                if (currentImg != null) {
                    anAlbum.addImage(currentImg);
                }
            }
           
        } else {
            for (Image picture : anAlbum.getImages()) {
                // Save to database
                try {
                    imageDBService.addImage(picture);
                } catch (PhotoAlbumException e) {
                    // TODO Auto-generated catch block
                    log.error("Impossible de sauvegarder cette image en BDD,", e);
                }
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
    public Map<String, String> getSelectedFiles() {
        Map<String, String> result = null;
        Object obj = cacheManager.getObjectFromCache(Constant.SMARTALBUM_CHECKED_PICTURES);
        if (obj != null) {
            result = (HashMap<String, String>) obj;
        } else {
            result = new HashMap<String, String>();
            cacheManager.putObjectInCache(Constant.SMARTALBUM_CHECKED_PICTURES, result);
        }
        return result;
    }
    
    public void resetSelectedFiles()
    {
        cacheManager.putObjectInCache(Constant.SMARTALBUM_CHECKED_PICTURES, new HashMap<String, String>());
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.mycompany.smartalbum.back.service.impl.SmartAlbumBackService#
     * removeSelectedFilesByName()
     */
    @Override
    public void removeSelectedFilesByName() {
        Map<String, String> checkedPictures = getSelectedFiles();
        Iterator<Entry<String, String>> it = checkedPictures.entrySet().iterator();
        String filesSource = Constant.TMP_DIR+getCurrentUser(true).getLogin()+File.separator;
        File tmp = new File(filesSource);
        String[] existingFilesNames = tmp.list();
        
        while (it.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
            
            int indexPage = Integer.parseInt(entry.getValue());
            int indexPhoto = Integer.parseInt(entry.getKey());
            String fileName = existingFilesNames[(indexPage - 1) * Constant.PAGE_SIZE + indexPhoto];
            
            FileUtils.deleteQuietly(new File(filesSource + fileName));
            it.remove();
        }
        resetSelectedFiles();
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.mycompany.smartalbum.back.service.impl.SmartAlbumBackService#
     * removeAllFilesByName()
     */
    @Override
    public void removeAllFilesByName() {
        
        String filesSource = Constant.TMP_DIR+getCurrentUser(true).getLogin()+File.separator;
        File tmp = new File(filesSource);
        String[] existingFilesNames = tmp.list();
        
        for (String fileName : existingFilesNames) {
            FileUtils.deleteQuietly(new File(filesSource + fileName));
        }
        resetSelectedFiles();
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.mycompany.smartalbum.back.service.impl.SmartAlbumBackService#
     * getSelectedFilesByPage()
     */
    @Override
    public Map<String, List<String>> getSelectedFilesByPage() {
        Map<String, List<String>> result = new HashMap<String, List<String>>();
        
        Map<String, String> checkedPictures = getSelectedFiles();
        Iterator<Entry<String, String>> it = checkedPictures.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
            
            String indexPage = entry.getValue();
            String indexPhoto = entry.getKey();
            List<String> currentselectedPictures = result.get(indexPage);
            
            if (currentselectedPictures == null) {
                currentselectedPictures = Lists.newArrayList();
                currentselectedPictures.add(indexPhoto);
                result.put(indexPage, currentselectedPictures);
            } else {
                currentselectedPictures.add(indexPhoto);
                result.put(indexPage, currentselectedPictures);
            }
            
        }
        return result;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.mycompany.smartalbum.back.service.impl.SmartAlbumBackService#
     * getSelectedPicturesArray()
     */
    @Override
    public List<SelectedPicture> getSelectedPicturesArray() {
        List<SelectedPicture> result = Lists.newArrayList();
        Map<String, List<String>> checkedPictures = getSelectedFilesByPage();
        Iterator<Entry<String, List<String>>> it = checkedPictures.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, List<String>> entry = (Map.Entry<String, List<String>>) it.next();
            String photoIndex = entry.getKey();
            List<String> pages = entry.getValue();
            for (String page : pages) {
                page = page.split("-")[0];
                String pageIndex = Integer.parseInt(page.split("-")[0]) - 1 + "";
                SelectedPicture aSelection = new SelectedPicture();
                aSelection.setPageIndex(pageIndex);
                aSelection.setPhotoIndex(photoIndex);
                result.add(aSelection);
            }
        }
        return result;
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
               //currentUser = getUserDBService().refreshUser(currentUser);
            currentUser = userDBService.findUserById(currentUser.getId());
          
           // userDBService.refreshUser(currentUser);
//            for(Shelf aShelf : currentUser.getShelves())
//            {
//                //shelfDBService.resetShelf(aShelf);
//                for(Album anAlbum : aShelf.getAlbums())
//                {
//                    albumDBService.resetAlbum(anAlbum);
//                }
//            }
            cacheManager.putObjectInCache(Constant.SMARTALBUM_CURRENT_USER, currentUser);
        }
        
        return (User) currentUser;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.mycompany.smartalbum.back.service.impl.SmartAlbumBackService#
     * getAbsoluteIndexOfImageInTmp(int, int)
     */
    @Override
    public int getAbsoluteIndexOfImageInTmp(int page, int position) {
        return Constant.PAGE_SIZE * page + position;
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
     * updateFilesToPrint(int, java.io.File,
     * com.mycompany.service.utils.Pagination, java.util.LinkedList,
     * java.util.List)
     */
    @Override
    public void updateFilesToPrint(final int targetPage, final File tmp, Pagination aPagination,
                                   LinkedList<FileMeta> files, Set<Image> images) {
        int nbPhotos = 0;
        if (tmp != null && tmp.isDirectory()) {
            String[] existingFiles = tmp.list();
            int fsStartRank = aPagination.getFileSystemStartRank();
            int fsStopRank = fsStartRank + aPagination.getNbPictures2Print();
            
            for (int i = 0; i < existingFiles.length; i++) {
                String fileAbsolutePath = tmp.getAbsolutePath() + File.separator + existingFiles[i];
                if (!fileAbsolutePath.endsWith(".svn") && !fileAbsolutePath.endsWith(".DS_Store")
                    && !fileAbsolutePath.contains("_small80") && !fileAbsolutePath.contains("_small120")
                    && !fileAbsolutePath.contains("_small160") && !fileAbsolutePath.contains("_small200")
                    && !fileAbsolutePath.contains("_medium")) {
                    if (nbPhotos >= fsStartRank && nbPhotos < fsStopRank) {
                        FileMeta fileMeta = fileUploadService.getFsFileByPath(fileAbsolutePath, existingFiles[i]);
                        if (fileMeta.getImage() == null) {
                            for (Image image : images) {
                                if (fileMeta.getFileName() != null && fileMeta.getFileName().equals(image.getName())) {
                                    fileMeta.setId(image.getId());
                                    try {
                                        if (image.getId() == null) {
                                            image.setMessagesHTML(null);
                                            getImageDBService().addImage(image);
                                            
                                        }
                                        Image bdImage = getImageDBService().findImageById(image.getId());
                                        if (bdImage != null && (bdImage.getMessagesHTML() == null
                                            || bdImage.getMessagesHTML().isEmpty())) {
                                            createImageHTMLMessage(image, MessageHTMLTypes.SHORTDESCRIPTION);
                                            createImageHTMLMessage(image,MessageHTMLTypes.LONGDESCRIPTION);
                                            fileMeta.setImage(bdImage);
                                        } else {
                                            fileMeta.setImage(bdImage);
                                        }
                                        fileMeta.setRelativePath(image.getFullPath());
                                        fileMeta.setFullPath(image.getFullPath());
                                    } catch (PhotoAlbumException e) {
                                        log.error("Impossible d'afficher le message html", e);
                                    }
                                }
                            }
                        }
                        files.add(fileMeta);
                    } else if (nbPhotos >= fsStopRank) {
                        break;
                    }
                    nbPhotos++;
                }
            }
        }
        updateSelectedPictures(targetPage, files);
    }
    
    @Override
    public void updateFilesInfos(final int targetPage, final File tmp, Pagination aPagination,
                                 LinkedList<FileMeta> files, Set<Image> images) {
        int nbPhotos = 0;
        if (tmp != null && tmp.isDirectory()) {
            String[] existingFiles = tmp.list();
            int fsStartRank = aPagination.getFileSystemStartRank();
            int fsStopRank = fsStartRank + aPagination.getNbPictures2Print();
            
            for (int i = 0; i < existingFiles.length; i++) {
                String fileAbsolutePath = tmp.getAbsolutePath() + File.separator + existingFiles[i];
                if (!fileAbsolutePath.endsWith(".svn") && !fileAbsolutePath.endsWith(".DS_Store")
                    && !fileAbsolutePath.contains("_small80") && !fileAbsolutePath.contains("_small120")
                    && !fileAbsolutePath.contains("_small160") && !fileAbsolutePath.contains("_small200")
                    && !fileAbsolutePath.contains("_medium")) {
                    if (nbPhotos >= fsStartRank && nbPhotos < fsStopRank) {
                        FileMeta fileMeta = fileUploadService.getFsFileByPath(fileAbsolutePath, existingFiles[i]);
                        if (fileMeta.getImage() == null) {
                            for (Image image : images) {
                                if (fileMeta.getFileName() != null && fileMeta.getFileName().equals(image.getName())) {
                                    fileMeta.setId(image.getId());
                                    try {
                                        if (image.getId() == null) {
                                            image.setMessagesHTML(null);
                                            getImageDBService().addImage(image);
                                        }
                                        fileMeta.setRelativePath(image.getFullPath());
                                        fileMeta.setFullPath(image.getFullPath());
                                    } catch (PhotoAlbumException e) {
                                        log.error("Impossible d'afficher le message html", e);
                                    }
                                }
                            }
                        }
                        files.add(fileMeta);
                    } else if (nbPhotos >= fsStopRank) {
                        break;
                    }
                    nbPhotos++;
                }
            }
        }
        updateSelectedPictures(targetPage, files);
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * com.mycompany.smartalbum.back.service.impl.SmartAlbumBackService#getFileCount
     * (java.io.File)
     */
    @Override
    public int getFileCount(final File tmp) {
        String[] existingFiles = tmp.list();
        int result = 0;
        for (String fileName : existingFiles) {
            if (!fileName.endsWith(".svn") && !fileName.endsWith(".DS_Store") && !fileName.contains("_small80")
                && !fileName.contains("_small120") && !fileName.contains("_small160")
                && !fileName.contains("_small200") && !fileName.contains("_medium")) {
                result++;
            }
        }
        return result;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.mycompany.smartalbum.back.service.impl.SmartAlbumBackService#
     * updateSelectedPictures(int, java.util.LinkedList)
     */
    @Override
    public void updateSelectedPictures(int currentPage, LinkedList<FileMeta> files) {
        Map<String, String> checkedPictures = getSelectedFiles();
        Iterator<Entry<String, String>> it = checkedPictures.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
            String pageKey = entry.getKey()
                .substring(0, entry.getKey().indexOf(Constant.SMARTALBUM_FILE_KEY_SEPARATOR));
            
            int indexPage = Integer.parseInt(pageKey);
            int indexPhoto = Integer.parseInt(entry.getValue());
            FileMeta currentFile = null;
            if (files.size() > indexPhoto) {
                currentFile = files.get(indexPhoto);
            }
            if (currentPage == indexPage && currentFile != null) {
                currentFile.setSelected(true);
                files.set(indexPage - 1, currentFile);
            }
        }
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.mycompany.smartalbum.back.service.impl.SmartAlbumBackService#
     * deleteEmptyAlbums()
     */
    @Override
    public void deleteEmptyAlbums() {
        List<Album> albums = albumDBService.findAll();
        for (Album anAlbum : albums) {
            if (anAlbum.getImages().isEmpty()) {
                try {
                    albumDBService.deleteAlbum(anAlbum);
                    
                    if (fileSystemService.isDirectoryEmpty(anAlbum.getPath())) {
                        fileSystemService.deleteDirectory(anAlbum.getPath());
                    }
                } catch (PhotoAlbumException e) {
                    // TODO Auto-generated catch block
                    log.debug("Impossible de supprimer un album vide...", e);
                }
            } else {
                if (fileSystemService.isDirectoryEmpty(anAlbum.getPath())) {
                    fileSystemService.deleteDirectory(anAlbum.getPath());
                    try {
                        albumDBService.deleteAlbum(anAlbum);
                    } catch (PhotoAlbumException e) {
                        // TODO Auto-generated catch block
                        log.debug("Impossible de supprimer un album vide...", e);
                    }
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
    public void synchronizeShelfWithDatabase(String shelfRootDirectoryPath, String shelfId) {
        List<String> albumNames = fileSystemService.findAllDirectoryNames(shelfRootDirectoryPath);
        Album album = null;
        if (albumNames != null) {
            for (String albumName : albumNames) {
                if (!albumName.equals(".svn") && !albumName.equals(".DS_Store")) {
                    try {
                        album = albumDBService.findAlbumById(Long.parseLong(albumName));
                        if (album != null) {
                            continue;
                        }
                    } catch (Exception e) {
                        log.warn("L'album "
                                 + albumName
                                 + " n'existe pas en BDD. Attention! tous les noms d'album doivent correspondre à des id BDD valide...");
                    }
                    // Si un album n'existe pas en base de données, on vérifie
                    // s'il a des photos, si oui,
                    // Alors on crée l'album en base
                    String albumPath = shelfRootDirectoryPath + File.separator + albumName;
                    List<String> images = fileSystemService.findAllDirectoryNames(albumPath);
                    if (images != null && images.size() > 0) {
                        log.debug("L'album " + albumName + " n'existe pas en base de données, on va le créer");
                        List<Image> imageList = Lists.newArrayList();
                        Album newAlbum = initSynchronizedAlbum(albumName);
                        newAlbum.setShelf(shelfDBService.findShelfById(Long.parseLong(shelfId)));
                        for (String imageName : images) {
                            if (!imageName.equals(".svn") && !imageName.equals(".DS_Store")) {
                                String relativePath = File.separator + albumPath + File.separator + imageName;
                                Image image = fileUploadService.getImageFileByPath(picturesRootPath + relativePath,
                                                                                   newAlbum.getPath() + relativePath);
                                if (image != null) {
                                    image.setAlbum(newAlbum);
                                    imageList.add(image);
                                }
                            }
                        }
                        newAlbum.getImages().addAll(imageList);
                        addAlbum(newAlbum, false);
                        log.debug("Album enregistré avec succès... On va maintenant renommer le répertoire...");
                        
                        // Il faut règler le cas
                        if (newAlbum.getId() != null && !newAlbum.getId().toString().equals(albumName)) {
                            fileSystemService.renameAlbumDirectory(newAlbum.getPath(), albumPath);
                        }
                        
                    } else {
                        fileSystemService.deleteDirectory(shelfRootDirectoryPath + File.separator + albumName);
                    }
                }
            }
        }
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.mycompany.smartalbum.back.service.impl.SmartAlbumBackService#
     * synchronizeShelvesWithDatabase()
     */
    @Override
    public void synchronizeShelvesWithDatabase() {
        // Supprime les albums vides enregistrés en base
        deleteEmptyAlbums();
        // Cré les albums correspondant à des répertoires de photos les supprime
        // s'ils sont vides
        List<String> userNames = fileSystemService.findAllDirectoryNames(File.separator);
        for (String userDirectory : userNames) {
            // On synchronise uniquement les albums de l'utilisateur connecté ou
            // ceux de l'étagère publique
            if (getCurrentUser(false) != null) {
                if (userDirectory.equals(getCurrentUser(false).getLogin())
                    || userDirectory.equals(Constant.SMARTALBUM_PUBLIC_SHELF_NAME)) {
                    List<String> shelfNames = fileSystemService.findAllDirectoryNames(File.separator + userDirectory);
                    for (String shelfName : shelfNames) {
                        if (!shelfName.equals(".svn") && !shelfName.equals(".DS_Store")) {
                            synchronizeShelfWithDatabase(File.separator + userDirectory + File.separator + shelfName,
                                                         shelfName);
                        }
                        
                    }
                }
            } else {
                break;
            }
        }
        
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
        return saveAlbum(newAlbum, 0, 0, isCreation);
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * com.mycompany.smartalbum.back.service.impl.SmartAlbumBackService#saveAlbum
     * (com.mycompany.database.smartalbum.model.Album, int, int, boolean)
     */
    @Override
    public RetourReponse saveAlbum(Album album, final int coverPageIndex, final int coverPhotoIndex, boolean isCreation) {
        if (album.getShelf() == null) {
            return getRetourResponse(Constants.SHELF_MUST_BE_NOT_NULL_ERROR, false, null);
        }
        User user = getCurrentUser(false);
        // Album name must be unique in shelf
        if (user.hasAlbumWithName(album)) {
            return getRetourResponse(Constants.SAME_ALBUM_EXIST_ERROR, false, null);
        }
        try {
            // Save to DB
            album.setCreated(new Date());
            albumDBService.addAlbum(album);
            // Creation de l'album sur le fs
            fileSystemService.onAlbumAdded(album);
            
            if (isCreation) {
                saveSelectedImages(album, coverPageIndex, coverPhotoIndex, false);
            } else {
                saveSelectedImages(album, isCreation);
            }
        } catch (Exception e) {
            log.debug("Impossible de sauvegarder correctement l'album", e);
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
                log.error("Impossible de rajouter une étagère", e);
                getErrorHandler().addToErrors(Constants.SHELF_SAVING_ERROR);
            } catch (Exception e) {
                log.error("IErreur imprévue lors de la création d'une étagère", e);
                getErrorHandler().addToErrors(Constants.SHELF_SAVING_ERROR);
            }
        } else {
            
            try {
                getShelfDBService().editShelf(result);
            } catch (PhotoAlbumException e) {
                getShelfDBService().resetShelf(result);
                response.setResult(false);
                response.setResultObject(result);
            }
        }
        return response;
    }
    
    /**
     * Method, that invoked on creation of the new shelf. Only registered users can create new shelves.
     * 
     * @param album - new album
     * @throws PhotoAlbumException
     */
    private void addShelf(Shelf shelf) throws PhotoAlbumException {
        if (getCurrentUser(true).hasShelfWithName(shelf)) {
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
        getFileSystemService().onShelfAdded(shelf);
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
        
        Set<Shelf> shelves = StringUtils.isNotBlank(albumForm.getSelectedOwnShelf()) ? getCurrentUser(false)
            .getShelves() : shelfDBService.getPredefinedShelves();
        
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
        	if(it.hasNext())
        	{
        		newAlbum.setShelf(it.next());
        	}
        }
        
        newAlbum.setShowAfterCreate(true);
        
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
        int coverSelectedPage = Integer.parseInt(albumForm.getSelectedCoverAlbum().split("/")[0]);
        int coverSelectedIndex = Integer.parseInt(albumForm.getSelectedCoverAlbum().split("/")[1]);
        return saveAlbum(album, coverSelectedPage, coverSelectedIndex, true);
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
    private MessageHTML createEmptyHTMLMessageInImage(Long imageId, MessageHTMLTypes type, String userLogin)
        throws PhotoAlbumException {
        
        Image anImage = imageDBService.findImageById(imageId);
        MessageHTML aNewMessageHTML = new MessageHTML();
        aNewMessageHTML.setApp_code(ApplicationsEnum.SMARTALBUM_BACK.name());
        aNewMessageHTML.setImage(anImage);
        aNewMessageHTML.setMessage_type(type.name());
        aNewMessageHTML.setLang_code("FR");
        aNewMessageHTML.setUserLogin(userLogin);
        aNewMessageHTML.setUpdate_date(new Date());
        aNewMessageHTML.setLine_size(Constant.MESSAGE_LINE_PART_SIZE);
        anImage.addOrReplaceMessageHTML(aNewMessageHTML);
        // imageDBService.editImage(anImage, false);
        messageDBService.addMessageHTML(aNewMessageHTML);
        
        // On rajoute un Id en le persistant en BDD
        
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
    public MessageHTML createOrUpdateHTMLMessageInImage(Long imageId, String message, MessageHTMLTypes type,
                                                        String login) throws PhotoAlbumException {
        
        Map<Integer, String> messageParts = getSplitedMessage(message, MESSAGEPART_SIZE);
        MessageHTML messageHtml = createEmptyHTMLMessageInImage(imageId, type, login);
        
        Iterator<Entry<Integer, String>> it = messageParts.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, String> entry = (Map.Entry<Integer, String>) it.next();
            MessagePart part = new MessagePart();
            part.setHtmlPart(entry.getValue());
            part.setMessageHTML(messageHtml);
            part.setOrder(entry.getKey()+"");
            messageDBService.addMessagePart(part);
            imageDBService.resetImage(messageHtml.getImage());
            it.remove(); // avoids a ConcurrentModificationException
        }
        messageDBService.editMessageHTML(messageHtml);
        
        messageHtml.updateHTMLDescription();
        // On met à jour la description du message qui sera affichee sur le
        // front
        return messageHtml;
    }
    
    @Override
    public void updateHTMLMessageInImage(Long messageId, String message) throws PhotoAlbumException {
        
        MessageHTML messageHtml = messageDBService.findMessageHTMLById(messageId);
        
        messageDBService.removeAllMessagePart(messageHtml);
        // imageDBService.editImage(messageHtml.getImage(), false);
        // DEBUG
        Map<Integer, String> messageParts = getSplitedMessage(message, MESSAGEPART_SIZE);
        Iterator<Entry<Integer, String>> it = messageParts.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, String> entry = (Map.Entry<Integer, String>) it.next();
            MessagePart part = new MessagePart();
            part.setHtmlPart(entry.getValue());
            part.setMessageHTML(messageHtml);
            part.setOrder(entry.getKey()+"");
            messageDBService.addMessagePart(part);
            it.remove(); // avoids a ConcurrentModificationException
        }
        
        for (MessageHTML amessage : messageHtml.getImage().getMessagesHTML()) {
            if (amessage.getId() == messageHtml.getId()) {
                amessage.setMessageLines(messageHtml.getMessageLines());
            }
        }
        
        messageDBService.editMessageHTML(messageHtml);
        
        // MessageHTML aMessageHTML =
        // messageDBService.findMessageHTMLById(messageId);
        // Image savedImage =
        // imageDBService.findImageById(messageHtml.getImage().getId());
        
        // On vérifie la consistence des messages html dans tous les objets
        // MessageHTML checkedMess =
        // getFirstMessagePartHTMLByType(savedImage,MessageHTMLTypes.fromValue(messageHtml.getMessage_type()));
        // if(checkedMess!=null)
        // {
        // if(aMessageHTML.getMessageLines().get(0).getId()!=checkedMess.getMessageLines().get(0).getId())
        // {
        // checkedMess.setMessageLines(aMessageHTML.getMessageLines());
        // messageDBService.mergeMessageHTML(checkedMess);
        // }
        // }
    }
    
    // private MessageHTML getFirstMessagePartHTMLByType(Image image,
    // MessageHTMLTypes type)
    // {
    // MessageHTML result = null;
    // if(image.getMessagesHTML().size()>0)
    // {
    // for(MessageHTML message : image.getMessagesHTML())
    // {
    // if(MessageHTMLTypes.fromValue(message.getMessage_type()).equals(type))
    // {
    // result = message;
    // if(result.getMessageLines().size()>0)
    // {
    // return result;
    // }
    // }
    // }
    // }
    // return null;
    // }
    
    @Override
    public boolean deleteHTMLMessageById(Long messageId) throws PhotoAlbumException {
        
        MessageHTML message = messageDBService.findMessageHTMLById(messageId);
        if (message != null) {
            messageDBService.deleteMessageHTML(message);
            return true;
        }
        return false;
    }
    
    @Override
    public MessageHTML createImageHTMLMessage(Image anImage, final MessageHTMLTypes typeDeMessage)
        throws PhotoAlbumException {
        
        MessageHTML result = null;
        
        if (anImage != null && anImage.getMessagesHTML()!=null && anImage.getMessagesHTML().size() > 0) {
            
            for (MessageHTML message : anImage.getMessagesHTML()) {
                
                if (message.getMessage_type().equals(typeDeMessage.name())) {
                    result = message;
                    log.debug("Un message HTML du type {} existe dejà dans l'image {}", typeDeMessage.name(),
                              anImage.toString());
                    break;
                }
            }
        }
        if (result == null) {
            switch (typeDeMessage) {
                case SHORTDESCRIPTION:
                    result = createOrUpdateHTMLMessageInImage(anImage.getId(), DEFAULT_SHORT_MESSAGE,
                                                              MessageHTMLTypes.SHORTDESCRIPTION, getCurrentUser(false)
                                                                  .getLogin());
                    anImage = result.getImage();
                    break;
                case LONGDESCRIPTION:
                    result = createOrUpdateHTMLMessageInImage(anImage.getId(), DEFAULT_LONG_MESSAGE,
                                                              MessageHTMLTypes.LONGDESCRIPTION, getCurrentUser(false)
                                                                  .getLogin());
                    anImage = result.getImage();
                    break;
                default:
                    break;
            }
        }
        
        return result;
    }
    
    @Override
    public boolean dropAlbumToShelf(Long albumId,Long shelfId)
    {
        Album albumToDrop = getAlbumDBService().findAlbumById(albumId);
        
        Shelf newShelf = getShelfDBService().findShelfById(shelfId);
        
        if(albumToDrop != null && newShelf != null)
        {
            if(!newShelf.getId().equals(albumToDrop.getShelf().getId()))
            {
                String oldPath = albumToDrop.getPath();
                
                if(dnDHelper.processDrop(albumToDrop, newShelf))
                {
                   
                    // Process drop is ok in the database, now process drop on the fs
                    getFileSystemService().renameAlbumDirectory(albumToDrop, oldPath);
                    return true;
                }
            }
        }
        
        return false;
    }
}
