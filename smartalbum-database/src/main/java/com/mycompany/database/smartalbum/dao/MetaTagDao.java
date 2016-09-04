package com.mycompany.database.smartalbum.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.mycompany.database.smartalbum.exception.PhotoAlbumException;
import com.mycompany.database.smartalbum.model.Image;
import com.mycompany.database.smartalbum.model.MetaTag;
import com.mycompany.database.smartalbum.repository.IMetaTagJpaRepository;
import com.mycompany.database.smartalbum.services.IMetaTagDao;
import com.mycompany.database.smartalbum.utils.Constants;

@Component("metaTagDBService")
public class MetaTagDao extends AbastractDao<MetaTag, Long> implements IMetaTagDao {
    private final static transient Logger log = LoggerFactory.getLogger(ImageDao.class);
    
    @Resource
    private IMetaTagJpaRepository metaTagJpaDBService;
    
    /**
     * Synchronize state of image entity with database
     * 
     * @param image - image to Synchronize
     * @param metatagsChanged - boolean value, that indicates is metatags of this image were changed(add new or delete older)
     * @throws PhotoAlbumException
     */
    public void editImageMetaTags(Image image, boolean metatagsChanged) throws PhotoAlbumException {
        try {
            if (metatagsChanged) {
                // Create cash of metatags early associated with image
                final List<MetaTag> removals = new ArrayList<MetaTag>(image.getImageTags());
                
                // Get string representation of current metatgs, associated with image and split them by comma
                final String[] tokens = image.getMetaString().split(Constants.COMMA);
                
                // Populate set of tokens - 'candidates to metatags'
                final Set<String> toks = new HashSet<String>();
                for (String s : tokens) {
                    if (!"".equals(s)) {
                        toks.add(s.trim());
                    }
                }
                for (String s : toks) {
                    // Find metatag in early associated tags
                    MetaTag t = image.getTagByName(s);
                    if (t != null) {
                        // If found - no work needed
                        removals.remove(t);
                    } else {
                        // Find metatag in database
                        t = getTagByName(s);
                        if (t != null) {
                            // If found simple add reference to it
                            image.addMetaTag(t);
                        } else {
                            // Create new metatag
                            t = new MetaTag();
                            t.setTag(s);
                            image.addMetaTag(t);
                            // Persist to database to prevent concurrent creation of other metatags with given name
                            metaTagJpaDBService.saveAndFlush(t);
                        }
                    }
                    t = null;
                }
                for (MetaTag tag : removals) {
                    // If metatag in that collection, we need remove them
                    image.removeMetaTag(tag);
                }
                // If this image is covering for album, break the reference
                if (image.isCovering()) {
                    if (!image.equals(image.getAlbum().getCoveringImage())) {
                        image.getAlbum().setCoveringImage(image);
                    }
                } else {
                    if (image.equals(image.getAlbum().getCoveringImage())) {
                        image.getAlbum().setCoveringImage(image.getAlbum().getImages().get(0));
                    }
                }
            }
            metaTagJpaDBService.flush();
        } catch (Exception e) {
            throw new PhotoAlbumException(e.getMessage());
        }
    }
    
    /**
     * Find metatag object by its string representation
     * 
     * @param tag - string representation of metatag
     * @return metatag object or null
     */
    public MetaTag getTagByName(String tag) {
    	return metaTagJpaDBService.findMetaTagByTag(tag);
    }
    
    /**
     * Find most-popular metatags
     * @
     * @return list of most-popular metatags
     */
    public List<MetaTag> getPopularTags() {
    	return metaTagJpaDBService.findPopularTags();
    }
    
    /**
     * Find List of metatags, similar to specified string. Used in autosuggect
     * 
     * @param suggest - string to search
     * @return list of most-popular metatags
     */
    public List<MetaTag> getTagsLikeString(String suggest) {
    	return metaTagJpaDBService.findSuggestTags(suggest + Constants.PERCENT);
    }
    
    @Override
    protected Class<MetaTag> getBoClass() {
        return MetaTag.class;
    }
    
    @Override
    protected Logger getLog() {
        return log;
    }

	@Override
	public List<MetaTag> findAll() {
		return metaTagJpaDBService.findAll();
	}
}
