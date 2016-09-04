package com.mycompany.smartalbum.back.helper;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.HtmlUtils;

import com.mycompany.database.smartalbum.exception.PhotoAlbumException;
import com.mycompany.database.smartalbum.model.Album;
import com.mycompany.database.smartalbum.model.Image;
import com.mycompany.database.smartalbum.search.vo.SearchDataTableRequest;
import com.mycompany.database.smartalbum.search.vo.SearchDataTableResponse;
import com.mycompany.filesystem.model.CheckedFile;
import com.mycompany.services.smartalbum.vo.DataTable;
import com.mycompany.services.smartalbum.vo.DataTableImageEntryVO;
import com.mycompany.services.utils.Constant;
import com.mycompany.smartalbum.back.helper.velosity.TemplateEnum;
import com.mycompany.smartalbum.back.helper.velosity.VelosityHelper;
import com.mycompany.smartalbum.back.service.SmartAlbumBackService;
import com.mycompany.smartalbum.back.utils.DataTableEnumType;

@Component
public class DataTableFactoryHelper {
	
	private final transient Logger LOG = LoggerFactory
			.getLogger(DataTableFactoryHelper.class);
	
	/** La colonne de trie **/
	public static final String ORDER_0_COLUMN = "order[0][column]";

	/** L'ordre de trie **/
	public static final String ORDER_0_DIR = "order[0][dir]";

	/** La chaine recherchée **/
	public static final String SEARCH_VALUE = "search[value]";

	/** La taille de la page **/
	public static final String LENGTH = "length";

	/** Variable Data attendu par le dataTable **/
	public static final String DATA = "data";

	/** Le nombre de résutat filtré **/
	public static final String RECORDS_FILTERED = "recordsFiltered";

	/** Le nombre total de données renvoyé par la requête **/
	public static final String RECORDS_TOTAL = "recordsTotal";
	
	@Resource
	public SmartAlbumBackService backService;
	
	@Autowired
	private VelosityHelper velocityHelper;
	
	public String initImageWithCheckBoxColumn(Image image, String controller, int compteurIndex, String severBaseUrl, boolean isImageSelectedInDataTable, String userLogin){
		String template = velocityHelper.findJSPFByTemplate(TemplateEnum.DATATABLE_CB_IMG_VM);
		
		if(isImageSelectedInDataTable){
			template=template.replaceAll("#CHECKED", "checked=\"checked\"");
		}
		else{
			template=template.replaceAll("#CHECKED", StringUtils.EMPTY);
		}
		template=template.replaceAll("#imageCount", String.valueOf(compteurIndex+1));
		template=template.replaceAll("#compteurIndex", String.valueOf(compteurIndex));
		template=template.replaceAll("#paginationActivePageNumber", "1");
		template=template.replaceAll("#imageId", String.valueOf(image.getId()));
		template=template.replaceAll("#fileName", image.getName());
		template=template.replaceAll("#controller",controller);
		if(image.getAlbum()!=null){
			template=template.replaceAll("#relativeUrl",image.getFullPath());}
			else{
				template=template.replaceAll("#relativeUrl",File.separator+userLogin+File.separator+image.getName());
			}
		template=template.replaceAll("#serverBaseUrl",severBaseUrl);
//		template=template.replaceAll("#isChecked",String.valueOf(image.isChecked()));
		
		
		return template;
	}
	
	public String initActionsColumn(Image image, String controller, int compteurIndex, String severBaseUrl, String userLogin){
		
		String template = velocityHelper.findJSPFByTemplate(TemplateEnum.DATATABLE_ACTIONS_VM);
		
		template=template.replaceAll("#controller",controller);
		template=template.replaceAll("#serverBaseUrl",severBaseUrl);
		template=template.replaceAll("#compteurIndex", String.valueOf(compteurIndex));
		template=template.replaceAll("#fileName", image.getName());
		template=template.replaceAll("#isTmpImage", String.valueOf(image.getAlbum() == null));
		
		// Initialisation des valeur de la popup pour la suppression d'une image
		String messageHeaderSuppression = "Suppression de l'image "+image.getName();
		String messageBodySuppression = "Attention, cette suppression est <b>irréversible</b><br/> !!";
		String confirmfunctionSuppression = String.format("javascript:deletePicto('%s', '%s', '%b');", compteurIndex,image.getId(),image.getAlbum()==null);
		template=template.replaceAll("#messageHeaderSuppression", HtmlUtils.htmlEscape(messageHeaderSuppression));
		template=template.replaceAll("#messageBodySuppression", HtmlUtils.htmlEscape(messageBodySuppression));
		template=template.replaceAll("#confirmfunctionSuppression", HtmlUtils.htmlEscape(confirmfunctionSuppression));
		
		
		// Initialisation des valeur de la popup pour la popup de modification
		template=template.replaceAll("#fileName", image.getName());
		template=template.replaceAll("#isTmpImage", String.valueOf(image.getId()<0));
		if(image.getAlbum()!=null){
		template=template.replaceAll("#relativeUrl",image.getFullPath());}
		else{
			template=template.replaceAll("#relativeUrl",File.separator+userLogin+File.separator+image.getName());
		}
		template=template.replaceAll("#imageId", String.valueOf(image.getId()));
		return template;
	}
	
	public String initBlogImageEntry(Image image,long currentAlbumId, String currentAlbumName, int compteurIndex, String imageBaseUrl)
	{
		Map<String, Object> imageProperties = new HashMap<>();
		imageProperties.put("image", image);
		imageProperties.put("currentAlbumId", currentAlbumId);
		imageProperties.put("currentAlbumName", currentAlbumName);
		imageProperties.put("compteurIndex", compteurIndex);
		imageProperties.put("imageBaseUrl", imageBaseUrl);
		String template = velocityHelper.findImageEntryByTemplate(TemplateEnum.DATATABLE_BLOG_ENTRY, imageProperties);
		return template;
	}
	
	public DataTable buildImagesDataTable(SearchDataTableRequest currentRequest, final String imageBaseUrl, DataTableEnumType smartAlbumPageTable) {
		SearchDataTableResponse<Album> responseTable = null;
		DataTable table = new DataTable();
		Album albumVO = null;
		
		if(DataTableEnumType.SLIDER.equals(smartAlbumPageTable)){
			return buildSliderImagesDataTable(currentRequest,imageBaseUrl, smartAlbumPageTable);
		}
		
		try {
			responseTable = backService.findAlbumBySearchRequest(currentRequest);
			albumVO = responseTable.getEntitiObject();
			if(albumVO != null){
				int compteur = -1;
				for(Image imageVO : albumVO.getImages())
				{
					compteur++;
					DataTableImageEntryVO imageEntry = null;
					if(DataTableEnumType.UPLOAD.equals(smartAlbumPageTable)){
						imageEntry = buildUploadImageEntry(currentRequest.getPageNumber(), imageBaseUrl, compteur, imageVO);
					}
					else if(DataTableEnumType.BLOG.equals(smartAlbumPageTable)){
						int compteurIndex = currentRequest.getPageNumber()*10+compteur;
						imageVO.setAlbum(albumVO);
						imageEntry = buildBlogImageEntry(imageVO,albumVO, compteurIndex, imageBaseUrl);
					}
					else{
						imageEntry = initDataTableImage(currentRequest.getPageNumber(), imageBaseUrl,
								false, imageVO,compteur );
					}
					table.getData().add(imageEntry);
				}
				table.setDraw(responseTable.getDraw());
				table.setRecordsFiltered(responseTable.getRecordsTotal());
				table.setRecordsTotal(responseTable.getRecordsTotal());
			}
		} catch (PhotoAlbumException e) {
			LOG.error("Erreur lors de la récupération de l'album",e);
		}
		
		return table;
	}
	
	public DataTable buildSliderImagesDataTable(SearchDataTableRequest currentRequest, final String imageBaseUrl, DataTableEnumType smartAlbumPageTable) {
		SearchDataTableResponse<Album> responseTable = null;
		DataTable table = new DataTable();
		Album albumVO = null;
		try {
			responseTable = backService.findAlbumBySearchRequest(currentRequest);
			albumVO = responseTable.getEntitiObject();
			if(albumVO != null){
				int compteur = -1;
				StringBuilder builder = new StringBuilder();
				builder.append("<div id='wowslider-container1'><div class='ws_images'><ul>");
				for(Image imageVO : albumVO.getImages()){
					compteur++;
					if(DataTableEnumType.SLIDER.equals(smartAlbumPageTable)){
						builder.append(buildSliderImageLIFromTemplate(imageVO, compteur, imageBaseUrl));
					}
				}
				builder.append("</ul></div></div>");
				DataTableImageEntryVO imageEntry = new DataTableImageEntryVO();
				imageEntry.setSliderEntry(builder.toString());
				table.getData().add(imageEntry);
				table.setDraw(responseTable.getDraw());
				table.setRecordsFiltered(responseTable.getRecordsTotal());
				table.setRecordsTotal(responseTable.getRecordsTotal());
			}
		} catch (PhotoAlbumException e) {
			LOG.error("Erreur lors de la récupération de l'album",e);
		}
		
		return table;
	}





	private DataTableImageEntryVO buildBlogImageEntry(Image imageVO, Album album, int compteurIndex, String imageBaseUrl) {
		DataTableImageEntryVO imageEntry = new DataTableImageEntryVO();
		String blogstringEntry = buildBlogImageEntryFromTemplate(imageVO, album.getId(), album.getName(), compteurIndex,imageBaseUrl);
		imageEntry.setBlogEntry(blogstringEntry);
		return imageEntry;
	}

	private DataTableImageEntryVO buildUploadImageEntry(int pageNumber,
			final String imageBaseUrl, int compteur, Image imageVO) {
		DataTableImageEntryVO imageEntry;
		Map<String,CheckedFile> map = new LinkedHashMap<>();
		Object obj = backService.getCacheManager().getObjectFromCache(Constant.SMARTALBUM_CHECKED_PICTURES);
		if(obj != null){
			map = (Map<String,CheckedFile>)obj;
		}
		boolean isImageCheckedInDataTable = (map.get(imageVO.getName())!=null);
		imageEntry = initDataTableImage(pageNumber, imageBaseUrl,
				isImageCheckedInDataTable, imageVO,compteur);
		imageEntry.setAction(getActionColumnFromTemplate(imageVO,imageEntry.getImageIndex(),imageEntry.getBaseUrl()));
		imageEntry.setImage(getImageColumnFromTemplate(imageVO,imageEntry.getImageIndex(),imageEntry.getBaseUrl(),imageEntry.isCheckedInDataTable()));
		return imageEntry;
	}
	
	private DataTableImageEntryVO initDataTableImage(int currentPageNumber, final String imageBaseUrl, boolean isImageCheckedInDataTable, Image imageVO, int compteurImage) {
		DataTableImageEntryVO imageEntry = new DataTableImageEntryVO();
		imageEntry.setFileDimension(imageVO.getHeight()+"X"+imageVO.getWidth()+ " (pxl)");
		imageEntry.setFileSize(String.valueOf(imageVO.getSize())+ " Ko");
		imageEntry.setFileName(imageVO.getName());
		imageEntry.setImageIndex(currentPageNumber*10+compteurImage);
		imageEntry.setCheckedInDataTable(isImageCheckedInDataTable);
		imageEntry.setBaseUrl(imageBaseUrl);
		return imageEntry;
	}
	
	private String buildSliderImageLIFromTemplate(Image image, int compteurIndex, String imageBaseUrl) {
		
		String sliderLine = "<li><img id='galeryImg"+compteurIndex+"' src='"+imageBaseUrl+"/imagesController/paintImage"+image.getFullPath()+"/_medium.html' alt='"+image.getName()+"' id='wows1_"+compteurIndex+"'/></li>";
		return sliderLine;
	}
	
	private String buildBlogImageEntryFromTemplate(Image image,long currentAlbumId, String currentAlbumName, int compteurIndex, String imageBaseUrl) {
		String template = initBlogImageEntry(image, currentAlbumId, currentAlbumName, compteurIndex, imageBaseUrl);
		return template;
	}
	
	public String getImageColumnFromTemplate(Image vo, int compteur, String secerBaseUrl, boolean isImageSelectedInDataTable)
	{
		String userLogin = backService.getCurrentUser(true).getLogin();
		String template = initImageWithCheckBoxColumn(vo, "albumsController", compteur, secerBaseUrl, isImageSelectedInDataTable, userLogin);
		return template;
	}
	
	public String getActionColumnFromTemplate(Image vo,int compteur, String secerBaseUrl)
	{
		String userLogin = backService.getCurrentUser(true).getLogin();
		String template = initActionsColumn(vo,"albumsController", compteur, secerBaseUrl, userLogin);
		return template;
	}
	
	public String getServerUrl(final HttpServletRequest request)
	{
		String result = StringUtils.EMPTY;
		String contextPath = request.getContextPath();
		//String serverName = request.getServerName();
		//int portNumber = request.getServerPort();
		//result = serverName + ":" +portNumber + contextPath;
		result = contextPath;
		
		return result;
	}
	
}
