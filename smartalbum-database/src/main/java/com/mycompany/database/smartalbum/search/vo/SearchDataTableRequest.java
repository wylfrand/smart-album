package com.mycompany.database.smartalbum.search.vo;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.MultiValueMap;

public class SearchDataTableRequest {
	
	private String sortColumn;
	
	private Long entityId;
	
	private Integer pageNumber;
	
	private Class<?> columnClass;
	
	private int pageSize;
	
	private String direction;
	
	private Long userId;
	
	private Integer draw;

	public SearchDataTableRequest(MultiValueMap<String, String> parametresAjax, Long userId) {
		this.pageSize = Integer.parseInt(parametresAjax.getFirst("length"));
		this.direction = parametresAjax.getFirst("order[0][dir]");
		this.sortColumn = parametresAjax.getFirst("columns[0][name]");
		int start = Integer.parseInt(parametresAjax.getFirst("start"));
		this.pageNumber = (start/pageSize);
		sortColumn = computeSortColumn(parametresAjax);
		if(StringUtils.isBlank(sortColumn)){
			this.sortColumn = "name";
		}
		this.userId = userId;
		this.draw = Integer.parseInt(parametresAjax.getFirst("draw"));
		try{
			this.entityId = Long.parseLong(parametresAjax.getFirst("albumId"));
		}
		catch(NumberFormatException exception){
			entityId = -1L;
		}
	}

	public String getSortColumn() {
		return sortColumn;
	}

	/**
	 * @param sortColumn the sortColumn to set
	 */
	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

	public void setSortColumnClass(Class<?> columnClass) {
		this.columnClass = columnClass;
		
	}

	/**
	 * @return the columnClass
	 */
	public Class<?> getColumnClass() {
		return columnClass;
	}

	/**
	 * @param columnClass the columnClass to set
	 */
	public void setColumnClass(Class<?> columnClass) {
		this.columnClass = columnClass;
	}

	/**
	 * @return the entityId
	 */
	public Long getEntityId() {
		return entityId;
	}

	/**
	 * @param entityId the entityId to set
	 */
	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	/**
	 * @return the pageNumber
	 */
	public Integer getPageNumber() {
		return pageNumber;
	}

	/**
	 * @param pageNumber the pageNumber to set
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return the direction
	 */
	public String getDirection() {
		return direction;
	}

	/**
	 * @param direction the direction to set
	 */
	public void setDirection(String direction) {
		this.direction = direction;
	}

	/**
	 * @return the draw
	 */
	public Integer getDraw() {
		return draw;
	}

	/**
	 * @param draw the draw to set
	 */
	public void setDraw(Integer draw) {
		this.draw = draw;
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	private String computeSortColumn(final MultiValueMap<String, String> parametresAjax)
	{
		String result = StringUtils.EMPTY;
		String sortColumnId = parametresAjax.getFirst("order[0][column]");
		String sortColumnNameFilter =  "columns["+sortColumnId+"][data]";
		String sortColumnIsOrderableFilter =  "columns["+sortColumnId+"][orderable]";
		String sortColumnDataTableName = parametresAjax.getFirst(sortColumnNameFilter);
		boolean isColumnSortable = Boolean.parseBoolean(parametresAjax.getFirst(sortColumnIsOrderableFilter));
		if(isColumnSortable){
			result = DataTableColumnEnum.fromValue(sortColumnDataTableName).getValue();
		}
		
		return result;
	}
}
