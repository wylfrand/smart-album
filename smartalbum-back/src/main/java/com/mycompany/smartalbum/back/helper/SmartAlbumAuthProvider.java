package com.mycompany.smartalbum.back.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.common.collect.Lists;
import com.mycompany.database.smartalbum.model.User;
import com.mycompany.database.smartalbum.search.options.SearchOptionByAlbum;
import com.mycompany.database.smartalbum.search.options.SearchOptionByImage;
import com.mycompany.database.smartalbum.search.options.SearchOptionByShelf;
import com.mycompany.database.smartalbum.search.options.SearchOptionByTag;
import com.mycompany.database.smartalbum.search.options.SearchOptionByUser;
import com.mycompany.database.smartalbum.services.ISearchOption;
import com.mycompany.database.smartalbum.services.IUserDao;
import com.mycompany.filesystem.utils.HashUtils;
import com.mycompany.services.smartalbum.vo.UserVO;
import com.mycompany.services.utils.Constant;
import com.mycompany.smartalbum.back.form.AlbumForm;
import com.mycompany.smartalbum.back.form.SearchForm;
import com.mycompany.smartalbum.back.form.ShelfForm;
import com.mycompany.smartalbum.back.service.SmartAlbumBackService;

import opiam.admin.applis.demo.beans.Person;
import opiam.admin.faare.config.javabeans.JBProfile;
import opiam.admin.faare.exception.AuthenticationFailureException;
import opiam.admin.faare.exception.ServiceException;
import opiam.admin.faare.service.UserContext;
import opiam.admin.faare.service.services.StandardService;
import opiam.admin.faare.struts.utils.SessionContext;

/**
 * Le provider par défaut pour l'authentification d'une application web avec
 * Spring Security Default provider for the authentication of a web application
 * with spring security
 * 
 * @author amvou
 */
@Component
public class SmartAlbumAuthProvider implements AuthenticationProvider, Serializable {

	@Resource
	protected SmartAlbumBackService backService;
	
	@Autowired
	private IUserDao userDBService;

	private static final long serialVersionUID = 1L;

	protected static final Logger log = LoggerFactory
			.getLogger("SMARTALBUM_AUTHENTIFICATION");
	

	public static final String AUI_NOM = "sn";
	public static final String AUI_ENT_JUR = "AppointmentEntityName";
	public static final String AUI_PRENOM = "givenname";

	public static final String[] AUI_DATA = new String[] { AUI_NOM, AUI_PRENOM,
			AUI_ENT_JUR };

	/**
	 * Login AUI
	 */
	@Value("${smartalbum.authentification.aui.login}")
	private String ldapLogin;

	/**
	 * Password AUI
	 */
	@Value("${smartalbum.authentification.aui.password}")
	private String ldapPassword;
	
	/**
	 * Fichier properties AUI
	 */
	private String propertyFile;

	/**
	 * Mode bouchon?
	 */
	@Value("${smartalbum.authentification.mocked:false}")
	private boolean mockMode;

	private final static String MOCKED_USER = "MOCKED_USER";
	private final static String PROTECTED = "[PROTECTED]";
	private final static String CAN_ACCESS_APPLICATION_ROLE = "CAN_ACCESS_APPLICATION";
	private final static String SMARTALBUM_SU_ROLE = "SMARTALBUM_SU";
	private final static String SMARTALBUMSU_USER = "SMARTALBUMSU";

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.authentication.AuthenticationProvider#
	 * authenticate(org.springframework.security.core.Authentication)
	 */
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		// Le login qui cherche à s'authentifier
		String smartAlbumUserName = "smartalbum_anonymous";
		String smartAlbumPassword  = "smartalbum_anonymous";
		if(authentication!=null){
		 smartAlbumUserName = (String) authentication.getPrincipal();
		 smartAlbumPassword = (String) authentication.getCredentials();
		}
		

		SessionContext sessionContext = null;
		UserContext userContext = null;
		HttpSession session = null;

		// Params obligatoires
		if (StringUtils.isEmpty(smartAlbumUserName)
				|| StringUtils.isEmpty(smartAlbumPassword)) {
			throw new BadCredentialsException(
					"Les informations d'authentification sont obligatoires.");
		}

		if (log.isDebugEnabled()) {
			log.debug(
					"On va s'authentifier à SMARTALBUM avec le couple login/password suivant: [{}] / [{}]",
					smartAlbumUserName, smartAlbumPassword);
		} else {
			log.info(
					"On va s'authentifier à SMARTALBUM avec le couple login/password suivant: [{}] / [*******]",
					smartAlbumUserName);
		}

		// L'objet user SMARTALBUM
		List<GrantedAuthority> roles = Lists.newArrayList();

		SmartAlbumUser smartAlbumUser = null;

		// Mode bouchon
		if (mockMode) {
			log.debug(
					"Mode bouchonné pour le couple login/password suivant: [{}] / [{}]",
					smartAlbumUserName, smartAlbumPassword);

			// Super User
			if (smartAlbumUserName.equals(SMARTALBUMSU_USER)) {
				roles.add(new GrantedAuthority() {
					private static final long serialVersionUID = 1L;

					public String getAuthority() {
						return SMARTALBUM_SU_ROLE;
					}
				});
			}
			roles.add(new GrantedAuthority() {
				private static final long serialVersionUID = 1L;

				public String getAuthority() {
					return CAN_ACCESS_APPLICATION_ROLE;
				}
			});

			smartAlbumUser = new SmartAlbumUser(smartAlbumUserName, PROTECTED,
					true, true, true, true, roles);

			smartAlbumUser.setLogin(ldapLogin);
			smartAlbumUser.setFirstName(MOCKED_USER);
			smartAlbumUser.setName(MOCKED_USER);

			UsernamePasswordAuthenticationToken simpleUserAuthentication = new UsernamePasswordAuthenticationToken(
					smartAlbumUser, null, smartAlbumUser.getAuthorities());

			return simpleUserAuthentication;

			// Sinon en mode réel, on gère un super US au cas où !!
		} else {
			// Super User
			if (smartAlbumUserName.equals(SMARTALBUMSU_USER)) {
				roles.add(new GrantedAuthority() {
					private static final long serialVersionUID = 1L;

					public String getAuthority() {
						return SMARTALBUM_SU_ROLE;
					}
				});
				roles.add(new GrantedAuthority() {
					private static final long serialVersionUID = 1L;

					public String getAuthority() {
						return CAN_ACCESS_APPLICATION_ROLE;
					}
				});

				smartAlbumUser = new SmartAlbumUser(smartAlbumUserName,
						PROTECTED, true, true, true, true, roles);

				smartAlbumUser.setLogin(ldapLogin);
				UsernamePasswordAuthenticationToken simpleUserAuthentication = new UsernamePasswordAuthenticationToken(
						smartAlbumUser, null, smartAlbumUser.getAuthorities());

				return simpleUserAuthentication;
			}
			
			
		}
		UsernamePasswordAuthenticationToken simpleUserAuthentication = null;
		// Authentification LDAP
		try {
			// Gets the session.
			session = session();
			// Gets the session context.
			sessionContext = SessionContext.getInstance(session);
			// Gets the user context.
			userContext = sessionContext.getUserContext();

			// Calls the StandardService to log on.
			StandardService.logon(smartAlbumUserName, smartAlbumPassword,
					userContext);

			/*
			 * Suppression de l'objet utilisateur du cache pour obliger le
			 * chargement de cet objet avec la connexion utilisateur
			 */
			userContext.getCache().remove(userContext.getDn());

			// Gets the user profiles
			List profilesList = userContext.getProfiles();
			log.info("Found profiles : " + profilesList.size());

			// Returns the profiles page URI if the user has several profiles.
			if (profilesList.size() == 1) {
				log.info("One profile : " + profilesList.get(0).toString());

				JBProfile profile = userContext.getJbProfile();
				Person user = (Person) userContext.getJbUser();
				user.setProfile(profile.getName());
				userContext.setJbUser(user);

				// Sets the user object in the session.
				session.setAttribute("user", (Person) userContext.getJbUser());
			} else {
				// Sorts the profile
				Comparator comp = new Comparator() {
					@Override
					public int compare(final Object obj1, final Object obj2) {
						try {
							if ((obj1 != null) && (obj2 != null)
									&& (obj1 instanceof JBProfile)
									&& (obj2 instanceof JBProfile)) {
								String name1 = ((JBProfile) obj1).getName();
								String name2 = ((JBProfile) obj2).getName();
								return name1.compareToIgnoreCase(name2);
							} else {
								return -1;
							}
						} catch (Exception e) {
							return -1;
						}
					}
				};
				Collections.sort(profilesList, comp);
				userContext.setProfiles(profilesList);

				// Sets the user object in the session.
				session.setAttribute("connectedPerson", userContext.getJbUser());
			}

		} catch (AuthenticationFailureException e) {
			log.error("Impossible de s'authentifier!", e);
			backService.getCacheManager().putObjectInCache(Constant.ERROR_DETAIL_ATTR, e.getMessage());
			throw new BadCredentialsException(
					e.getMessage());

		} catch (ServiceException e) {
			log.error("Impossible de contruire jbTop!", e);
			backService.getCacheManager().putObjectInCache(Constant.ERROR_DETAIL_ATTR, e.getMessage());
			throw new BadCredentialsException(
					e.getMessage());
		} catch(Exception e)
		{
			backService.getCacheManager().putObjectInCache(Constant.ERROR_DETAIL_ATTR, e.getMessage());
			throw new BadCredentialsException(
					e.getMessage());
		}
		

		if (userContext != null && userContext.isLoggedIn()) {
			roles.add(new GrantedAuthority() {
				private static final long serialVersionUID = 1L;

				public String getAuthority() {
					return CAN_ACCESS_APPLICATION_ROLE;
				}
			});

			// Le token de base pour l'identification utilisé coté client est
			// donné à l'utilisateur si ce dernier existe en base de donnée
			User user = userDBService.login(
					smartAlbumUserName, HashUtils.encodePasswd(smartAlbumPassword));
			if (user != null) {
				smartAlbumUser = new SmartAlbumUser(smartAlbumUserName,
						PROTECTED, true, true, true, true, roles);
				smartAlbumUser.setUserContext(userContext);
				smartAlbumUser.setDbUser(user);
				simpleUserAuthentication = new UsernamePasswordAuthenticationToken(
						smartAlbumUser, null, smartAlbumUser.getAuthorities());

				initCurrentUserInSession(user);
				initSearchForm();
				//initAlbumForm();
				//initShelfForm();
				initCurrentControllerForUpload();
				backService.getFileSystemService().createTmpDirectoryForUser(
						user.getLogin());
			}
			else
			{
				backService.getCacheManager().putObjectInCache(Constant.ERROR_DETAIL_ATTR, "Utilisateur inconnu!");
				throw new BadCredentialsException("Utilisateur inconnu!");
			}
		}

		return simpleUserAuthentication;
	}

	private void initCurrentControllerForUpload() {
		Object searchFormObj = backService.getCacheManager()
				.getObjectFromCache(Constant.SMARTALBUM_PHOTOS_CONTROLLER);
		if (searchFormObj == null) {

			backService.getCacheManager().putObjectInCache(
					Constant.SMARTALBUM_PHOTOS_CONTROLLER, "controller");
		}

	}

	private void initSearchForm() {
		Object searchFormObj = backService.getCacheManager()
				.getObjectFromCache(Constant.SMARTALBUM_SEARCH_FORM);
		SearchForm form = null;
		if (searchFormObj == null) {
			List<ISearchOption> options = new ArrayList<ISearchOption>();
			options.add(new SearchOptionByShelf());
			options.add(new SearchOptionByAlbum());
			options.add(new SearchOptionByImage());
			options.add(new SearchOptionByUser());
			options.add(new SearchOptionByTag());
			form = new SearchForm(options, false, false);
			backService.getCacheManager().putObjectInCache(
					Constant.SMARTALBUM_SEARCH_FORM, form);
		}

	}

	private void initAlbumForm() {
		Object albumFormObj = backService.getCacheManager().getObjectFromCache(
				Constant.SMARTALBUM_ALBUM_FORM);
		AlbumForm form = null;
		if (albumFormObj == null) {
			form = new AlbumForm();
			form.setDefaultPicturePath("/default/noimage_small200.jpg");
			form.setPublicShelves(backService.getPredefinedShelvesVO());
			UserVO currentUser = backService.findCurrentUserVO(false);
			if (currentUser != null) {
				form.setUserShelves(currentUser.getShelves());

			}
			backService.getCacheManager().putObjectInCache(
					Constant.SMARTALBUM_ALBUM_FORM, form);
		}
	}

	private void initShelfForm() {
		Object shelfFormObj = backService.getCacheManager().getObjectFromCache(
				Constant.SMARTALBUM_SHELF_FORM);
		ShelfForm form = null;
		if (shelfFormObj == null) {
			form = new ShelfForm();
			form.setName("");
			form.setDescription("");
			form.setShared(true);
			backService.getCacheManager().putObjectInCache(
					Constant.SMARTALBUM_SHELF_FORM, form);
		}

	}

	private void initCurrentUserInSession(User user) {
		backService.getCacheManager().putObjectInCache(
				Constant.SMARTALBUM_CURRENT_USER, user);
	}

	// example usage
	public static HttpSession session() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder
				.currentRequestAttributes();
		return attr.getRequest().getSession(true); // true == allow create
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.authentication.AuthenticationProvider#supports
	 * (java.lang.Class)
	 */
	public boolean supports(Class<? extends Object> authentication) {
		return true;
	}

	// GETTER / SETTER
	public String getPropertyFile() {
		return propertyFile;
	}

	public void setPropertyFile(String propertyFile) {
		this.propertyFile = propertyFile;
	}
}
