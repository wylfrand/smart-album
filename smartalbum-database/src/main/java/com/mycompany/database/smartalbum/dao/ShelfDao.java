package com.mycompany.database.smartalbum.dao;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.mycompany.database.smartalbum.exception.PhotoAlbumException;
import com.mycompany.database.smartalbum.model.Shelf;
import com.mycompany.database.smartalbum.repository.IShelfJpaRepository;
import com.mycompany.database.smartalbum.services.IShelfDao;

@Component("shelfDBService")
public class ShelfDao extends AbastractDao<Shelf, Long> implements IShelfDao {
    
    private final static transient Logger log = LoggerFactory.getLogger(ShelfDao.class);
    
    @Resource
    private IShelfJpaRepository shelfJpaDBService;
    
    /**
     * Persist shelf entity to database
     * 
     * @param shelf - shelf to add
     * @throws PhotoAlbumException
     */
    public void addShelf(Shelf shelf) throws PhotoAlbumException {
        try {
        	// Add reference to user
            shelf.getOwner().addShelf(shelf);
        	shelfJpaDBService.saveAndFlush(shelf);
        } catch (Exception e) {
            throw new PhotoAlbumException(e.getMessage(), e);
        }
    }
    
    /**
     * Remove shelf entity from database
     * 
     * @param shelf - shelf to delete
     * @throws PhotoAlbumException
     */
    public void deleteShelf(Shelf shelf) throws PhotoAlbumException {
        try {
            // Remove reference from user
            shelf.getOwner().removeShelf(shelf);
            shelfJpaDBService.delete(shelf);
        } catch (Exception e) {
            shelf.getOwner().addShelf(shelf);
            throw new PhotoAlbumException(e.getMessage());
        }
    }
    
    /**
     * Synchronize state of shelf entity with database
     * 
     * @param shelf - shelf to Synchronize
     * @throws PhotoAlbumException
     */
    public void editShelf(Shelf shelf) throws PhotoAlbumException {
        try {
            shelfJpaDBService.saveAndFlush(shelf);
        } catch (Exception e) {
            throw new PhotoAlbumException(e.getMessage());
        }
    }
    
    /**
     * Return list of shared shelves(pre-defined)
     * 
     * @param shelf - shelf to Synchronize
     */
    public List<Shelf> getPredefinedShelves() {
    	return shelfJpaDBService.queryUserShelves();
    }
    
    @Override
    protected Class<Shelf> getBoClass() {
        return Shelf.class;
    }
    
    @Override
    protected Logger getLog() {
        return log;
    }
    
    /**
     * Persist shelf entity to database
     * 
     * @param shelfId - shelf to add
     * @return
     */
    @Override
    public Shelf findShelfById(Long shelfId) {
    	return shelfJpaDBService.findOne(shelfId);
    }

	@Override
	public Shelf findShelfByName(final String name) {
		return shelfJpaDBService.findShelfByName(name);
	}

	@Override
	public List<Shelf> findAll() {
		return shelfJpaDBService.findAll();
	}

}
