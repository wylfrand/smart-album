package com.mycompany.database.smartalbum.dao;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.mycompany.database.smartalbum.exception.PhotoAlbumException;
import com.mycompany.database.smartalbum.model.User;
import com.mycompany.database.smartalbum.repository.IUserJpaRepository;
import com.mycompany.database.smartalbum.services.IUserDao;
import com.mycompany.database.smartalbum.transaction.CommitTransaction;
import com.mycompany.database.smartalbum.transaction.ReadonlyTransaction;
import com.mycompany.database.smartalbum.utils.Constants;

@Component("userDBService")
public class UserDao extends AbastractDao<User, Long> implements IUserDao{

        private final static transient Logger log = LoggerFactory.getLogger(UserDao.class);
        
        @Resource
        private IUserJpaRepository userJpaDBService;
        
        /*
         * (non-Javadoc)
         * @see com.sfr.application.gena.database.dao.AbastractDao#getBoClass()
         */
        @Override
        protected Class<User> getBoClass(){
                return User.class;
        }

        
        /**
     * Login user. If succes return logged user, otherwise return null
     * @param username - username
     * @param password - password
     * @return user if success
     */
        @ReadonlyTransaction
        public User login(String username, String password) {
        	return userJpaDBService.findByLoginAndPasswordHash(username,password);
//                return (User)em.createNamedQuery(Constants.USER_LOGIN_QUERY)
//                .setParameter(Constants.USERNAME_PARAMETER, username)
//                .setParameter(Constants.PASSWORD_PARAMETER, password)
//                .getSingleResult();
        }

        /**
     * Persist user entity to database
     * @param user - user to register
         * @throws PhotoAlbumException
     */
        @CommitTransaction
        public void register(User user) throws PhotoAlbumException {
                try{
//                em.persist(user);
//                em.flush();
                	userJpaDBService.saveAndFlush(user);
                }
        catch(Exception e){
                throw new PhotoAlbumException(e.getMessage());
        }
        }
        
        /**
     * Synchronize state of user entity with database
     * @return user if success
         * @throws PhotoAlbumException
     */
        @CommitTransaction
        public User updateUser(User user) throws PhotoAlbumException {
                try{
                //em.flush();
                	userJpaDBService.saveAndFlush(user);
                }
        catch(Exception e){
                throw new PhotoAlbumException(e.getMessage());
        }
                return user;
        }
        
        /**
     * Refresh state of user entity with database
     * @Param user - user to refresh
     * @return user if success
         * @throws PhotoAlbumException
     */
        @CommitTransaction
        public User refreshUser(User user){
                user = em.find(User.class, user.getId());
                em.refresh(user);
                return user;
        }
        
        /**
     * Check if user with specified login already exist
     * @return is user with specified login already exist
     */
        @ReadonlyTransaction
        public boolean isUserExist(String login) {
                return em.createNamedQuery(Constants.USER_EXIST_QUERY)
                .setParameter(Constants.LOGIN_PARAMETER, login)
                .getResultList().size() != 0;
        }
        
        /**
     * Check if user with specified email already exist
     * @return is user with specified email already exist
     */
        @ReadonlyTransaction
        public boolean isEmailExist(String email){
                return em.createNamedQuery(Constants.EMAIL_EXIST_QUERY)
                .setParameter(Constants.EMAIL_PARAMETER, email)
                .getResultList().size() != 0;
        }
        
        
        // GETTER / FLUX
        public Logger getLog(){
                return log;
        }
        
        /**
         * Persist album entity to database
         * 
         * @param album - album to add
         * @return
         */
       // @ReadonlyTransaction
        public User findUserById(Long userId) {
//            User user = findById(userId);
//            em.refresh(user);
//            return user;
        	return userJpaDBService.findUserById(userId);
        }


		@Override
		@ReadonlyTransaction
		public void refreshEmtityManager(User user) throws PhotoAlbumException {
			try {
				if(user == null)
				{
					em.getEntityManagerFactory().getCache().evictAll();
				}
				else
				{
					User userDb = em.find(User.class, user.getId());
					em.refresh(userDb);
				}
			} catch (Exception e) {
				throw new PhotoAlbumException(e.getMessage(), e);
			}
		}


		@Override
		 @ReadonlyTransaction
		public User findUserByEmail(String email) {
			return userJpaDBService.findUserByEmail(email);
		}


		@Override
		public List<User> findAll() {
			return userJpaDBService.findAll();
		}

}