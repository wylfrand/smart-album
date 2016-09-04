package com.mycompany.smartalbum.back.helper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mycompany.database.smartalbum.exception.PhotoAlbumException;
import com.mycompany.database.smartalbum.utils.Constants;
import com.mycompany.filesystem.model.FileMeta;
import com.mycompany.services.model.commun.InfosBeanForm;
import com.mycompany.smartalbum.back.service.SmartAlbumBackService;

import opiam.admin.applis.demo.beans.Person;
import opiam.admin.applis.demo.utils.GenerateTree;
import opiam.admin.applis.demo.utils.PasswordUtils;
import opiam.admin.faare.config.javabeans.JBProfile;
import opiam.admin.faare.exception.ServiceException;
import opiam.admin.faare.service.UserContext;
import opiam.admin.faare.service.services.StandardService;
import opiam.admin.faare.service.services.views.TreeNode;
import opiam.admin.faare.service.services.views.ViewGenerator;
import opiam.admin.faare.struts.utils.SessionContext;

@Component
public class LdapHelper implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Value("${opiam.technical.ldap.user.login}")
	protected String adminLadapLogin;

	@Value("${opiam.technical.ldap.user.password}")
	protected String adminLadapPassword;
	
	@Value("${opiam.people.search.base}")
	protected String ldapPeopleSearchBase;
	
	@Resource
	protected SmartAlbumBackService backService;
	
	private final transient Logger LOG = LoggerFactory
			.getLogger(LdapHelper.class);
	
	
	protected Person initNewLdapUser(InfosBeanForm pf)
			throws IOException, PhotoAlbumException
	{
		try {
			// Gets the user
			Person person = getLdapUser(pf);

			// checks the userId
			if (person.getId() == null) {
				backService.getErrorHandler()
						.addToErrors(Constants.LOGIN_ERROR);
				throw new PhotoAlbumException(Constants.LOGIN_ERROR);
			}

			// Manages the password attribute
			String newP = pf.getPassword();
			String confP = pf.getConfirmPassword();

			LOG.debug("New password : " + newP);

			// checks the password
			if ((newP != null) && (newP.trim().length() > 0)) {
				if (!newP.equals(confP)) {
					backService.getErrorHandler().addToErrors(
							Constants.CONFIRM_PASSWORD_NOT_EQUALS_PASSWORD);
					throw new PhotoAlbumException(Constants.CONFIRM_PASSWORD_NOT_EQUALS_PASSWORD);
				}

				person.setPassword(newP);

				// deletes the values in the form
				pf.setPassword("");
				pf.setConfirmPassword("");

				// checks the syntax
				if (!PasswordUtils.checkPassword(newP)) {
					backService.getErrorHandler().addToErrors(
							Constants.INVALID_LOGIN_OR_PASSWORD);

					throw new PhotoAlbumException(Constants.INVALID_LOGIN_OR_PASSWORD);
				}
			}

			// Manages the displayname and the commonName
			StringBuffer sb = new StringBuffer();
			sb.append(person.getGivenName());
			sb.append(" ");
			sb.append(person.getName());
			person.setCommonName(sb.toString());

			sb = new StringBuffer();
			sb.append(person.getName());
			sb.append(" ");
			sb.append(person.getGivenName());
			person.setDisplayName(sb.toString());

			// Manages the description
			if (pf.getDescription() != null) {
				// person.setDescription(pf.getDescription());
			}

			// Manages the managers
			// if (pf.getManagerPerson() != null) {
			// person.setManager(pf.getManagerInPerson());
			// } else {
			// person.setManager(new ArrayList());
			// }

			// Manages the photo
			FileMeta file = pf.getAvatarData();
			if (file != null) {

				byte[] buffer = file.getBytes();
				ByteArrayOutputStream baos = new ByteArrayOutputStream(
						buffer.length);
				baos.write(buffer, 0, buffer.length);

				LOG.debug("baos.size() = " + baos.size());

				if (baos.size() > 0) {
					Collection listBytes = new ArrayList();
					listBytes.add(baos.toByteArray());
					person.setPhoto(listBytes);
				}
			}

			// Manages the certificate
			// FormFile certfile = pf.getCertificateFile();
			//
			// if ((certfile != null) && (certfile.getFileSize() > 0)) {
			// ByteArrayOutputStream baos = new ByteArrayOutputStream();
			// InputStream stream = certfile.getInputStream();
			//
			// byte[] buffer = new byte[8192];
			// int bytesRead = 0;
			//
			// while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
			// baos.write(buffer, 0, bytesRead);
			// }
			//
			// _logger.debug("baos.size() = " + baos.size());
			//
			// if (baos.size() > 0) {
			// Collection listBytes = new ArrayList();
			// listBytes.add(baos.toByteArray());
			// person.setCertificate(listBytes);
			// }
			// }

			// Sets the dn, tree : ou=People, dc=mycompany,dc=com
			sb = new StringBuffer();
			sb.append("uid=");
			sb.append(person.getId());
			sb.append(",ou=People, dc=mycompany,dc=com");
			person.setDn(sb.toString());

		return person;
	}
	catch (Exception e) {
		LOG.error(e.getMessage());
		throw new PhotoAlbumException(Constants.REGISTRATION_ERROR+" : "+e.getMessage());
	}
}

	
	public Person createLdapUser(HttpServletRequest request, InfosBeanForm pf)
			throws IOException, PhotoAlbumException {
		
		UserContext userContext = findTechnicalUser(request);
		Person person = initNewLdapUser(pf);
		try{
			// Writes the operation on the output defined in the
			LOG.info("Create person : " + person.getDn() + " by user: "
					+ userContext.getDn());
			// Creates the search parameters from the "personSearch" request
            // defined in the requests_conf.xml.
//            List args =
//                StrutsService.getSearchArgumentWithAnyForm("personSearch", pf,
//                		userContext
//                                               );

            // Calls the StandardService to search the entries.
//            SearchResult searchResult =
//                StandardService.search(args, userContext);
           StandardService.create(person, userContext);

			return person;
		}
		catch (Exception e) {
			// Writes the error on the output defined in the
			// logger_config.properties.
			LOG.error(e.getMessage(),e);
			throw new PhotoAlbumException(Constants.REGISTRATION_ERROR+" : "+e.getMessage());
		}
	}
	
	public Person deleteLdapUser(HttpServletRequest request, Person person)
			throws IOException, PhotoAlbumException {
		
		UserContext userContext = findTechnicalUser(request);
		try{
			// Writes the operation on the output defined in the
			LOG.info("Delete person : " + person.getDn() + " by user: "
					+ userContext.getDn());
			StandardService.delete(person, userContext);
			return person;
		}
		catch (Exception e) {
			// Writes the error on the output defined in the
			// logger_config.properties.
			LOG.error(e.getMessage());
			throw new PhotoAlbumException(Constants.LDAP_DELETE_ERROR+" : "+e.getMessage());
		}
	}
	
	/**
     * This method is called to execute the action once the checks have been
     * performed by the SecureAction class.
     *
     * @param mapping              Struts mapping data.
     * @param actionForm           FormBean associated with the action.
     * @param request              HTTP request.
     * @param httpServletResponse  HTTP response.
     *
     * @return An ActionForward.
     *
     * @throws IOException  An I/O exception if failed or interrupted I/O operations occurs.
     * @throws PhotoAlbumException 
     * @throws ServletException  A ServletException if the servlet has a problem.
     */
    public Person searchPerson(HttpServletRequest request, String login
                                      ) throws IOException, PhotoAlbumException
    {
    		UserContext userContext = findTechnicalUser(request);
         // Loads the person entry to get the last updated entry.
            Person person = null;
            String dn = "uid="+login+","+ldapPeopleSearchBase;
			try {
				person = (Person) StandardService.load(dn, userContext);
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				LOG.error("Impossible de trouver la person dans l'annuaire",e);;
			}
            
            return person;
    }
    
	public boolean modifyPerson(HttpServletRequest request, Person ldapPerson, String newPassword) throws IOException, PhotoAlbumException {
		UserContext userContext = findTechnicalUser(request);
		// Loads the person entry to get the last updated entry.
		// checks the syntax
        if (PasswordUtils.checkPassword(newPassword))
        {
		    // Calls the StandardService to modify the entry.
		    try {
		    	ldapPerson.setPassword(newPassword);
				StandardService.modify(ldapPerson, userContext);
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				LOG.error("Impossible de mettre à jour la person dans l'annuaire",e);
				return false;
			}
        }
        return true;
	}
	
	protected Person getLdapUser(InfosBeanForm form) {
		Person user = new Person();
		user.setGivenName(form.getPrenom());
		user.setDepartment(form.getDepartement());
		user.setDisplayName(form.getNom());
		user.setPassword(form.getPassword());
		user.setId(form.getUsername());
		user.setMail(form.getEmail());
		user.setName(form.getNom());
		user.setPhone(form.getTelephone());
		user.setTitle(form.getCiv());
		return user;
	}
	
	private UserContext findTechnicalUser(HttpServletRequest request) throws PhotoAlbumException
	{
		SessionContext sessionContext = null;
		// Gets the session.
		HttpSession session = request.getSession();

		// Gets the session context.
		sessionContext = SessionContext.getInstance(session);

		// Gets the user context.
		UserContext userContext = sessionContext.getUserContext();
		
		
		try {
			StandardService.logon(adminLadapLogin, adminLadapPassword,userContext);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			throw new PhotoAlbumException(e.getMessage(),e.getCause());
		}
		// Kirshtein vaughan possède deux profiles, on choisi le profile qui
		// a l'acl de création // d'effectuer une création via opiam.
		int profileIdx = 1;
		// Puts the selected profile in the userContext
		userContext.chooseProfile(profileIdx);

		// Modifies the profile of the user.
		JBProfile profile = userContext.getJbProfile();
		opiam.admin.applis.demo.beans.Person user = (opiam.admin.applis.demo.beans.Person) userContext
				.getJbUser();
		user.setProfile(profile.getName());
		// Sets the user object in the session.
		userContext.setJbUser(user);
		
		// On s'authentifie avec un utilisateur technique qui a le droit
		
		return userContext;
	}
	
	protected void setLdapTreeInSession(HttpSession session, SessionContext sessionContext)
	{
		// Initializes the navigation
		TreeNode treeNode = ViewGenerator.generateTreeView(sessionContext.getUserContext());
		session.setAttribute("defaultTreeNode", treeNode);

		String tree = GenerateTree.getInstance().processTree(
				treeNode.getDefaultMutableTreeNode(), null);
		session.setAttribute("treeview", tree);
	}
}
