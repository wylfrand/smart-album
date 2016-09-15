package com.mycompany.database.smartalbum.dao;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;

import com.mycompany.database.smartalbum.exception.PhotoAlbumException;
import com.mycompany.database.smartalbum.model.Album;
import com.mycompany.database.smartalbum.model.Image;
import com.mycompany.database.smartalbum.repository.IAlbumJpaRepository;
import com.mycompany.database.smartalbum.repository.IImageJpaRepository;
import com.mycompany.database.smartalbum.search.vo.ImageResume;
import com.mycompany.database.smartalbum.search.vo.SearchDataTableRequest;
import com.mycompany.database.smartalbum.services.IAlbumDao;
import com.mycompany.database.smartalbum.services.IImageDao;
import com.mycompany.database.smartalbum.transaction.CommitTransaction;
import com.mycompany.database.smartalbum.utils.Constants;
import com.mycompany.database.smartalbum.vo.DataTableEntity;

@Component("imageDBService")
public class ImageDao extends AbastractDao<Image, Long> implements IImageDao {
    
    private final static transient Logger log = LoggerFactory.getLogger(ImageDao.class);
    
    @Resource(name = "albumDBService")
    private IAlbumDao albumDBService;
    
    @Resource
    private IImageJpaRepository imageJpaDBService;
    
    @Resource
    private IAlbumJpaRepository albumJpaDBService;
    
    /**
     * Remove image entity from database
     * 
     * @param image - image to delete
     * @throws PhotoAlbumException
     */
    @CommitTransaction
    public void deleteImage(Image image) throws PhotoAlbumException {
        Album parentAlbum = image.getAlbum();
        if(image.getAlbum() != null){
	        parentAlbum.removeImage(image);
	        image.setImageTags(null);
        }
        imageJpaDBService.delete(image);
        imageJpaDBService.flush();
        
        if (parentAlbum!=null && parentAlbum.getImages().size() == 0) {
        	albumJpaDBService.delete(parentAlbum);
        }
    }
    
    /**
     * Remove image entity from database
     * 
     * @param image - image to delete
     * @throws PhotoAlbumException
     */
    @CommitTransaction
    public void updateImage(Image image) throws PhotoAlbumException {
        imageJpaDBService.saveAndFlush(image);
    }
    
    /**
     * Persist image entity to database
     * 
     * @param image - image to add
     * @throws PhotoAlbumException
     */
    @CommitTransaction
    public void addImage(Image image) throws PhotoAlbumException {
        image.getAlbum().addImage(image);
        imageJpaDBService.saveAndFlush(image);
    }
    
    /**
     * Check if image with specified path already exist in specified album
     * 
     * @param album - album to check
     * @param path - path to check
     * @return is image with specified path already exist
     */
    public boolean isImageWithThisPathExist(Album album, String path) {
        return em.createNamedQuery(Constants.IMAGE_PATH_EXIST_QUERY).setParameter(Constants.PATH_PARAMETER, path)
            .setParameter(Constants.ALBUM_PARAMETER, album).getResultList().size() != 0;
    }
    
    /**
     * Return count of images with path, that started from specified path already exist in specified album
     * 
     * @param album - album to check
     * @param path - path to check
     * @return count of images
     */
    public Long getCountIdenticalImages(Album album, String path) {
        return (Long) em.createNamedQuery(Constants.IMAGE_IDENTICAL_QUERY)
            .setParameter(Constants.PATH_PARAMETER, path + Constants.PERCENT)
            .setParameter(Constants.ALBUM_PARAMETER, album).getSingleResult();
    }
    
    @Override
    protected Class<Image> getBoClass() {
        return Image.class;
    }
    
    @Override
    protected Logger getLog() {
        return log;
    }
    
    @Override
    public Image findImageById(Long imageId) {
    	return imageJpaDBService.findOne(imageId);
    }
    
	@Override
	public Image findImageByNameAndUser(String imageName, Long id) {
		return imageJpaDBService.findImageByNameAndUserId(imageName,id);
	}
	
	//
	@Override
	public List<String> findAllImageNamesByAlbumId(final Long id) {
		return imageJpaDBService.queryfindImageNamesByAlbumId(id);
	}

	@Override
	public Image findImageByNameAndAlbumId(String imageName, Long id) {
		return imageJpaDBService.findImageByNameAndAlbumId(imageName, id);
	}

	@Override
	public DataTableEntity<Image> findImagesBySearchRequest(SearchDataTableRequest request, boolean shouldSearchInUser) {
		
		DataTableEntity<Image> response = new DataTableEntity<Image>();
		Direction direction = Direction.ASC;
		if(Direction.DESC.name().equals(request.getDirection().toUpperCase())){
			direction = Direction.DESC;
		}
		Page<Image> result = null;
		Sort sortFilter = new Sort(direction, request.getSortColumn());
		
		Pageable pageRequest = new PageRequest(request.getPageNumber(), request.getPageSize(), sortFilter);
		if(shouldSearchInUser){
			result = imageJpaDBService.queryfindImagesByUserIdAndSearchRequest(request.getUserId(), pageRequest);
		}
		else{
			result = imageJpaDBService.queryfindImagesByAlbumIdAndSearchRequest(request.getEntityId(), pageRequest);
		}
		
		response.setData(result.getContent());
		response.setDraw(request.getDraw());
		response.setRecordsTotal(Integer.parseInt(result.getTotalElements()+""));
		response.setRecordsFiltered(result.getNumberOfElements());
		
		return response;
	}

	@Override
	public void addImageInUserTmp(Image image) {
		image.getUser().getImages().add(image);
        imageJpaDBService.saveAndFlush(image);
	}
	
	@Override
	public void deleteAllImages(List<Image> imagesToDelete) {
		for(Image img : imagesToDelete){
			imageJpaDBService.delete(img);
		}
		imageJpaDBService.flush();
	}

	@Override
	public List<Image> findAll() {
		return imageJpaDBService.findAll();
	}

	@Override
	public List<ImageResume> findAllImageResumeByAlbumId(Long id) {
		return imageJpaDBService.findAllImageResumeByAlbumId(id);
	}
    
}
