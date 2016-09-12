package com.mycompany.smartalbum.back.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.mycompany.database.smartalbum.model.Album;
import com.mycompany.database.smartalbum.model.Image;
import com.mycompany.database.smartalbum.model.Sex;
import com.mycompany.database.smartalbum.model.Shelf;
import com.mycompany.database.smartalbum.model.User;
import com.mycompany.database.smartalbum.search.options.SearchOptionByAlbum;
import com.mycompany.database.smartalbum.search.options.SearchOptionByImage;
import com.mycompany.database.smartalbum.search.options.SearchOptionByShelf;
import com.mycompany.database.smartalbum.search.options.SearchOptionByTag;
import com.mycompany.database.smartalbum.search.options.SearchOptionByUser;
import com.mycompany.database.smartalbum.services.ISearchOption;
import com.mycompany.filesystem.model.CheckedFile;
import com.mycompany.filesystem.model.FileMeta;
import com.mycompany.filesystem.service.ImageDimension;
import com.mycompany.filesystem.utils.FileFilter;
import com.mycompany.filesystem.utils.HashUtils;
import com.mycompany.services.model.commun.InfosBeanForm;
import com.mycompany.services.smartalbum.infos.ShelfInfos;
import com.mycompany.services.smartalbum.vo.AlbumVO;
import com.mycompany.services.smartalbum.vo.ImageVO;
import com.mycompany.services.smartalbum.vo.ShelfVO;
import com.mycompany.services.smartalbum.vo.form.AlbumVOForm;
import com.mycompany.services.smartalbum.vo.form.ImageVOForm;
import com.mycompany.services.utils.Constant;
import com.mycompany.services.utils.HttpSessionCacheManager;
import com.mycompany.smartalbum.back.form.AlbumForm;
import com.mycompany.smartalbum.back.form.FileUploadForm;
import com.mycompany.smartalbum.back.form.ImageForm;
import com.mycompany.smartalbum.back.form.SearchForm;
import com.mycompany.smartalbum.back.form.ShelfForm;
import com.mycompany.smartalbum.back.form.UserForm;
import com.mycompany.smartalbum.back.form.validator.AlbumFormValidator;
import com.mycompany.smartalbum.back.form.validator.ImageFormValidator;
import com.mycompany.smartalbum.back.form.validator.PasswordFormValidator;
import com.mycompany.smartalbum.back.form.validator.SearchFormValidator;
import com.mycompany.smartalbum.back.form.validator.ShelfFormValidator;
import com.mycompany.smartalbum.back.form.validator.UserFormValidator;
import com.mycompany.smartalbum.back.service.SmartAlbumBackService;
import com.mycompany.smartalbum.back.service.impl.DnDHelper;
import com.mycompany.smartalbum.back.service.impl.SendMailService;

/**
 * Controller de base pour le back office
 * 
 * @author <b>Mvou</b>
 */
public abstract class ABaseController {

	// ----------- SERVICES ---------------------------//
	@Resource
	protected SmartAlbumBackService backService;

	@Resource
	protected DnDHelper dnDHelper;
	
	@Resource
	protected SendMailService mailService;

	protected Set<String> categories = new HashSet<String>();

	protected Map<String,CheckedFile> checkedPictures = new LinkedHashMap<String,CheckedFile>();
	
	protected LinkedList<FileMeta> tmpFilesToUpload = new LinkedList<FileMeta>();
	
	/** The mapper. */
	protected static Mapper mapper = new DozerBeanMapper();

	/**
	 * Size of a byte buffer to read/write file
	 */
	private static final int BUFFER_SIZE = 4096;

	@Resource
	protected UserFormValidator userFormValidator;
	
	@Resource
	protected PasswordFormValidator passwordFormValidator;

	FileMeta fileMeta = null;

	private final transient Logger LOG = getLoger();

	@Value("${smartalbum.filesystem.upload.root.path}")
	public String picturesRootPath;
	

	protected abstract Logger getLoger();

	// ----------- VALIDATEURS -----------------------//

	@Resource
	SearchFormValidator searchFormValidator;

	@Resource
	private UserFormValidator userDBFormValidator;

	@Resource
	private AlbumFormValidator albumDBFormValidator;

	@Resource
	private ImageFormValidator imageDBFormValidator;

	@Resource
	private ShelfFormValidator shelfDBFormValidator;
	
	@Resource(name = "httpSessionCacheManager")
	protected HttpSessionCacheManager cacheManager;

	protected SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

	/**
	 * Retourne un objet {@link User} créé é partir d'un objet {@link UserForm}
	 * 
	 * @param UserForm
	 *            , L'objet contenant les infos du formulaire permettant de
	 *            créer ou modifier un User
	 * @return un objet {@link User} représentant le user Base de données d'un
	 *         User
	 */
	protected User initUser(UserForm userForm) {

		return null;
	}

	/**
	 * Retourne un objet {@link Shelf} créé é partir d'un objet
	 * {@link ShelfForm}
	 * 
	 * @param shelfForm
	 *            , L'objet représentant le formulaire d'une étagére
	 * @return un objet {@link Shelf} contenant les infos base de donnée d'une
	 *         étagére
	 */
	protected Shelf initShelf(ShelfForm shelfForm) {

		return null;
	}

	/**
	 * Retourne un objet {@link UserForm} créé é partir d'un objet {@link User}
	 * 
	 * @param user
	 *            , L'objet contenant les infos base de données d'un User
	 * @return un objet {@link UserForm} représentant le formulaire d'un User
	 */
	protected UserForm initUserForm(User user) {

		return null;
	}

	/**
	 * Retourne un objet {@link ShelfForm} créé é partir d'un objet
	 * {@link Shelf}
	 * 
	 * @param Shelf
	 *            , L'objet contenant les infos base de données d'un Shelf
	 * @return un objet {@link ShelfForm} représentant le formulaire d'un Shelf
	 */
	protected ShelfForm initShelfConfigurationForm(Shelf shelf) {

		return null;
	}

	/**
	 * Retourne un objet {@link ImageForm} créé é partir d'un objet
	 * {@link Image}
	 * 
	 * @param Image
	 *            , L'objet contenant les infos base de données d'un Image
	 * @return un objet {@link ImageForm} représentant le formulaire d'un Image
	 */
	protected ImageForm initImgeForm(Image image) {

		return null;
	}

	/**
	 * @return the files
	 */
	@SuppressWarnings("unchecked")
	protected synchronized LinkedList<FileMeta> getFiles() {

		Object obj = backService.getCacheManager().getObjectFromCache(
				Constant.SMARTALBUM_SESSION_BEAN);
		LinkedList<FileMeta> files = null;
		if (obj != null) {
			files = (LinkedList<FileMeta>) obj;
		} else {
			files = new LinkedList<FileMeta>();
		}
		return files;
	}

	protected SearchForm getSearchForm() {
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
		} else {
			form = (SearchForm) searchFormObj;
		}
		return form;
	}

	protected void resetShelfForm() {
		backService.getCacheManager().putObjectInCache(
				Constant.SMARTALBUM_SHELF_FORM, new ShelfForm());
	}

	protected User getDbUser(InfosBeanForm form) {
		User user = new User();
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String dateNaissance = form.getJour() + "/" + form.getMois() + "/"
				+ form.getAnnee();
		Date date;
		try {
			date = formatter.parse(dateNaissance);
			user.setBirthDate(date);
		} catch (ParseException e) {
			getLoger().error("Impossible de parser la date!");
		}
		user.setConfirmPassword(form.getConfirmPassword());
		user.setEmail(form.getEmail());
		user.setFirstName(form.getPrenom());
		user.setHasAvatar(true);
		user.setLogin(form.getUsername());
		user.setPassword(form.getPassword());
		user.setPasswordHash(HashUtils.encodePasswd(user.getPassword()));
		user.setPreDefined(true);
		user.setSecondName(form.getNom());
		if (form.getCiv() != null && form.getCiv().equalsIgnoreCase("Mme")) {
			user.setSex(Sex.FEMALE);
		} else {
			user.setSex(Sex.MALE);
		}

		return user;
	}



	protected void doDownload(HttpServletRequest request,
			HttpServletResponse response, String filePath) throws IOException {

		// get absolute path of the application
		ServletContext context = request.getSession().getServletContext();
		String appPath = context.getRealPath("");
		System.out.println("appPath = " + appPath);

		// construct the complete absolute path of the file
		String fullPath = appPath + filePath;
		File downloadFile = new File(fullPath);
		FileInputStream inputStream = new FileInputStream(downloadFile);

		// get MIME type of the file
		String mimeType = context.getMimeType(fullPath);
		if (mimeType == null) {
			// set to binary type if MIME mapping not found
			mimeType = "application/octet-stream";
		}
		System.out.println("MIME type: " + mimeType);

		// set content attributes for the response
		response.setContentType(mimeType);
		response.setContentLength((int) downloadFile.length());

		// set headers for the response
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"",
				downloadFile.getName());
		response.setHeader(headerKey, headerValue);

		// get output stream of the response
		OutputStream outStream = response.getOutputStream();

		byte[] buffer = new byte[BUFFER_SIZE];
		int bytesRead = -1;

		// write bytes read from the input stream into the output stream
		while ((bytesRead = inputStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, bytesRead);
		}
		inputStream.close();
		outStream.close();
	}

	protected void compressAlbumAndSend (final HttpServletResponse response, File sourceFile, String fileName) throws IOException {
		ZipOutputStream out = new ZipOutputStream(response.getOutputStream());
		byte[] buf = new byte[1024];

		InputStream in;
		FileFilter filter = new FileFilter();
		List<File> filesToDownload = filter.convertSelectedPicturesToFiles(ImageDimension.ORIGINAL, sourceFile, getSelectedPictures(), true);
		response.setContentType("application/zip");
        response.addHeader("Content-Disposition", "attachment; filename="+"sampleZip.zip");
        response.setHeader("Set-Cookie", "fileDownload=true; path=/");
		// Loop through entities
		for (File file : filesToDownload) {
			// Write to zip file
			in = new FileInputStream(file);
			out.putNextEntry(new ZipEntry(file.getName()));
 
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.closeEntry();
			in.close();
		}
		out.close();
	}
	
	protected List<CheckedFile> getSelectedPictures() {
		Map<String, CheckedFile> checkedPictures = backService
				.getSelectedFiles();
		return Lists.newArrayList(checkedPictures.values());
	}
	
	/**
	 * <p>
	 * Renvoie le nom d'export du fichier.
	 * </p>
	 * 
	 * @return Le nom d'export du fichier.
	 */
	protected String getExportFilename(String name) {
		StringBuffer lBuff = new StringBuffer(256).append("SMART-").append(name.replaceAll(" ", "_")).append("-")
				.append((new SimpleDateFormat("dd_MM_yyyy-HH_mm_ss"))
				.format(Calendar.getInstance().getTime())).append(".zip");
		return lBuff.toString();
	}
	
	protected static UserDetails currentUserDetails(){
	    SecurityContext securityContext = SecurityContextHolder.getContext();
	    Authentication authentication = securityContext.getAuthentication();
	    if (authentication != null) {
	        Object principal = authentication.getPrincipal();
	        return principal instanceof UserDetails ? (UserDetails)principal : null;
	    }
	    return null;
	}
	
	protected FileUploadForm getFileUploadFormFromSelectedFiles(AlbumVOForm form, List<CheckedFile> checkedList)
	{
		StringBuffer options = new StringBuffer();
		FileUploadForm aFileUploadForm = new FileUploadForm();
		for (ImageVOForm photoSelectionne : form.getImages()) {
				options.append("<option>" + (photoSelectionne.getName()))
						.append("</option>");
			}
		form.getFilesToCreateOrModify().addAll(checkedList);
		if (form.getImages().size() > 0) {
			ImageVOForm defaultPicture = form.getCoveringImage();
			CheckedFile cf = new CheckedFile(defaultPicture.getName(), defaultPicture.getId());
			cf.setBasUrl(form.getPath());
			form.setAlbumCoverFile(cf);
			aFileUploadForm.setDefaultSelectedPicture(cf);
		}
		form.setSelectedFilesSize(form.getImages().size());
		aFileUploadForm.setSelectedFilesSize(form.getImages().size());
		form.setSelectedPicturesOptions(options.toString());
		aFileUploadForm.setSelectedPicturesOptions(options.toString());
		Set<ShelfInfos> shelvesInfos = convertToListInfos(backService.getCurrentUser(false).getShelves());
		form.getUserShelvesInfos().addAll(shelvesInfos);
		aFileUploadForm.getUserShelvesInfos().addAll(shelvesInfos);
		aFileUploadForm.setSelectedPicturesOptions(options.toString());
		
		return aFileUploadForm;
	}
	
	protected FileUploadForm getFileUploadFormFromSelectedFiles(AlbumForm form, List<CheckedFile> checkedList, AlbumVOForm album, boolean isTmpImage)
	{
		User user = backService.getCurrentUser(false);
		StringBuffer options = new StringBuffer();
 		FileUploadForm aFileUploadForm = new FileUploadForm();
		String basUrl = File.separator+user.getLogin();
		
		ImageVO defaultSelectedPicture = null;
		
		if(!isTmpImage){
			basUrl = album.getPath();
			for (ImageVOForm photoSelectionne : album.getImages()) {
				options.append("<option value='" + (basUrl+photoSelectionne.getName()) + "'>"+(photoSelectionne.getName()).toUpperCase()+"</option>");
			}
		}
		else{
			basUrl = basUrl+File.separator;
			for (CheckedFile photoSelectionne : checkedList) {
				options.append("<option value='" + (basUrl+photoSelectionne.getImageName()) + "'>"+(photoSelectionne.getImageName()).toUpperCase()+"</option>");
			}
		}
		if(isTmpImage)
		{
			Image defaultPicture = computeDefaultPictureToprint(checkedList, user.getImages());
			int size = checkedList.size();
			aFileUploadForm.setSelectedFilesSize(size);
			form.setSelectedFilesSize(size);
			
			if(defaultPicture !=null){
				defaultSelectedPicture = initImageVO(defaultPicture);
				CheckedFile defaultSelectedImage = new CheckedFile();
				defaultSelectedImage.setBasUrl(basUrl);
				defaultSelectedImage.setImageId(defaultSelectedPicture.getId());
				defaultSelectedImage.setImageName(defaultSelectedPicture.getName());
				form.setDefaultSelectedPicture(defaultSelectedPicture);
				aFileUploadForm.setDefaultSelectedPicture(defaultSelectedImage);
				
				
			}
		}
		else{
			ImageVOForm cover = album.getCoveringImage();
			int size = album.getImages().size();
			aFileUploadForm.setSelectedFilesSize(size);
			form.setSelectedFilesSize(size);
			CheckedFile defaultSelectedImage = new CheckedFile();
			defaultSelectedImage.setBasUrl(basUrl);
			defaultSelectedImage.setImageId(cover.getId());
			defaultSelectedImage.setImageName(cover.getName());
			aFileUploadForm.setDefaultSelectedPicture(defaultSelectedImage);
		}
		
		form.setSelectedPicturesOptions(options.toString());
		form.getUserShelvesInfos().addAll(convertToListInfos(user.getShelves()));
		aFileUploadForm.setSelectedPicturesOptions(options.toString());
		
		return aFileUploadForm;
	}

	private Image computeDefaultPictureToprint(List<CheckedFile> checkedList, List<Image> imageList) {
		Image defaultPicture = null;
		if (checkedList.size() == 0) {
			if(imageList.size()>0){
				defaultPicture = imageList.iterator().next();
			}
		}
		else{
			Iterator<Image> it = imageList.iterator();
			while(it.hasNext()){
				defaultPicture = it.next();
				if(checkedFilesContainsImage(checkedList,defaultPicture.getName())){
					break;
				}
			}
		}
		return defaultPicture;
	}
	
	
	boolean checkedFilesContainsImage(List<CheckedFile> checkedList,String imageName)
	{
		boolean result = false;
		for(CheckedFile file : checkedList ){
			if(file.getImageName().equalsIgnoreCase(imageName)){
				result = true;
				break;
			}
		}
		return result;
	}
	
	protected static Set<ShelfInfos> convertToListInfos(List<Shelf> shelves)
	{
		Set<ShelfInfos> result = new HashSet<>();
		for(Shelf shelf : shelves){
			ShelfInfos currentShelfInfos = mapper.map(shelf, ShelfInfos.class);
			if(!result.contains(currentShelfInfos))
				result.add(currentShelfInfos);
		}
		return result;
	}
	
	public ImageVO convertImageToVO(Image obj){
		return mapper.map(obj, ImageVO.class);
	}
	
	public AlbumVO convertAlbumToVO(Album obj){
		return mapper.map(obj, AlbumVO.class);
	}
	
	public ShelfVO convertUserToVO(Shelf obj){
		return mapper.map(obj, ShelfVO.class);
	}

	private ImageVO initImageVO(Image defaultPicture) {
		ImageVO imageVO = new ImageVO();
		imageVO.setCameraModel(defaultPicture.getCameraModel());
		imageVO.setCategory(defaultPicture.getCategory());
		imageVO.setPath(defaultPicture.getPath());
		imageVO.setCovering(true);
		imageVO.setCreated(defaultPicture.getCreated());
		imageVO.setDescription(defaultPicture.getDescription());
		imageVO.setWidth(defaultPicture.getWidth());
		imageVO.setHeight(defaultPicture.getHeight());
		imageVO.setVisited(defaultPicture.isVisited());
		imageVO.setName(defaultPicture.getName());
		imageVO.setAllowComments(true);
		imageVO.setId(defaultPicture.getId());
		imageVO.setUploaded(defaultPicture.getUploaded());
		imageVO.setSize(defaultPicture.getSize());
		imageVO.setVisited(false);
		imageVO.setShowMetaInfo(true);
		imageVO.setChecked(false);
		return imageVO;
	}
	
	protected String getJsonString(Object obj) throws JsonGenerationException, JsonMappingException, IOException
	{
		ObjectMapper mapper = new ObjectMapper();
		//Object to JSON in String
		String jsonInString = mapper.writeValueAsString(obj);
		
		return jsonInString;
	}
}
