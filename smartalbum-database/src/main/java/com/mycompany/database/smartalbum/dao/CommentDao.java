package com.mycompany.database.smartalbum.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mycompany.database.smartalbum.exception.PhotoAlbumException;
import com.mycompany.database.smartalbum.model.Album;
import com.mycompany.database.smartalbum.model.Comment;
import com.mycompany.database.smartalbum.model.Image;
import com.mycompany.database.smartalbum.model.User;
import com.mycompany.database.smartalbum.repository.ICommentJpaRepository;
import com.mycompany.database.smartalbum.services.ICommentDao;
import com.mycompany.database.smartalbum.utils.Constants;

@Repository("commentDBService")
public class CommentDao extends AbastractDao<Comment, Long> implements ICommentDao {

	private final static transient Logger log = LoggerFactory
			.getLogger(CommentDao.class);
	
	@Autowired
	private ICommentJpaRepository commentJpaService;
	

	@Override
	protected Class<Comment> getBoClass() {
		return Comment.class;
	}

	@Override
	protected Logger getLog() {
		return log;
	}

	@Override
	public void addImageComment(Comment comment) throws PhotoAlbumException {
        comment.getImage().addComment(comment);
		commentJpaService.saveAndFlush(comment);
	}

	@Override
	public void deleteImageComment(Comment comment) throws PhotoAlbumException {
		try {
            Image image = comment.getImage();
            image.removeComment(comment);
            commentJpaService.delete(comment);
            commentJpaService.flush();
        } catch (Exception e) {
            throw new PhotoAlbumException(e.getMessage());
        }
		
	}
	
	@Override
	public void deleteAlbumComment(Comment comment) throws PhotoAlbumException
	{
		try {
			Album album = comment.getAlbum();
			album.removeComment(comment);
			commentJpaService.delete(comment);
			commentJpaService.flush();
		} catch (Exception e) {
			throw new PhotoAlbumException(e.getMessage());
		}
	}

	@Override
	public void addAlbumComment(Comment comment) throws PhotoAlbumException {
		comment.getAlbum().addComment(comment);
		commentJpaService.saveAndFlush(comment);
		commentJpaService.flush();
		
	}
	
    /**
     * Retrieve all cooments posted by given user.
     * 
     * @return list of comments
     */
    @SuppressWarnings("unchecked")
    public List<Comment> findAllUserComments(User user) {
        return (List<Comment>) em.createNamedQuery(Constants.USER_COMMENTS_QUERY)
            .setParameter(Constants.AUTHOR_PARAMETER, user).getResultList();
    }

	@Override
	public List<Comment> findAll() {
		return commentJpaService.findAll();
	}

}
