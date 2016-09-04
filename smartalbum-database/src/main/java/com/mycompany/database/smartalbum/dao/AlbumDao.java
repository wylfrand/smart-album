package com.mycompany.database.smartalbum.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mycompany.database.smartalbum.exception.PhotoAlbumException;
import com.mycompany.database.smartalbum.model.Album;
import com.mycompany.database.smartalbum.model.Shelf;
import com.mycompany.database.smartalbum.repository.IAlbumJpaRepository;
import com.mycompany.database.smartalbum.services.IAlbumDao;

@Repository("albumDBService")
public class AlbumDao extends AbastractDao<Album, Long> implements IAlbumDao {

	private final static transient Logger log = LoggerFactory
			.getLogger(AlbumDao.class);
	
	@Autowired
	private IAlbumJpaRepository albumJpaJpaService;

	/**
	 * Persist album entity to database
	 * 
	 * @param album
	 *            - album to addc
	 * @throws PhotoAlbumException
	 */
	public void addAlbum(Album album) throws PhotoAlbumException {
		try {
			long start = System.currentTimeMillis();
			album.getShelf().addAlbum(album);
			albumJpaJpaService.saveAndFlush(album);
			long fin = System.currentTimeMillis();
			if(getLog().isDebugEnabled()){
            	getLog().debug("@@@ Temps écoulé pour récupérer l'entité {} [{}] ==> {}", 
            			       new Object[]{getBoClass(), album.getId(), getTime(start, fin)});
            }
			} catch (Exception e) {
				album.getShelf().removeAlbum(album);
				albumJpaJpaService.flush();
				throw new PhotoAlbumException(e.getMessage());
			}
	}

	/**
	 * Persist album entity to database
	 * 
	 * @param album
	 *            - album to add
	 * @return
	 */
	public Album findAlbumById(Long albumId) {
		Album album = albumJpaJpaService.findOne(albumId);
		return album;
	}
	
	
	/**
	 * Remove album entity from database
	 * 
	 * @param album
	 *            - album to delete
	 * @throws PhotoAlbumException
	 */
	public void deleteAlbum(Album album) throws PhotoAlbumException {
		Shelf parentShelf = album.getShelf();
		try {
			if(parentShelf == null){
				return;
			}
			album.setCoveringImage(null);
			//Remove from previous shelf 
			parentShelf.removeAlbum(album);
			albumJpaJpaService.saveAndFlush(album);
		} catch (Exception e) {
			parentShelf.addAlbum(album);
			albumJpaJpaService.flush();
			throw new PhotoAlbumException(e.getMessage());
		}
	}

	/**
	 * Synchronize state of album entity with database
	 * 
	 * @param album
	 *            - album to Synchronize
	 * @throws PhotoAlbumException
	 */
	public void editAlbum(Album album) throws PhotoAlbumException {
		try {
			albumJpaJpaService.saveAndFlush(album);
		} catch (Exception e) {
			log.error("Impossible de merger l'abum : {}", album.getName(), e);
			throw new PhotoAlbumException(e.getMessage());
		}
	}

	@Override
	protected Class<Album> getBoClass() {
		return Album.class;
	}

	@Override
	protected Logger getLog() {
		return log;
	}

	@Override
	public List<Album> findAll() {
		return albumJpaJpaService.findAll();
	}

}
