package com.mycompany.database.smartalbum.search.vo;

public class SearchDataTableResponse<T> {
	
private Integer draw;
	
	private Integer recordsTotal;
	
	private Integer recordsFiltered;
	
	private T entitiObject;
	
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
	 * @return the recordsTotal
	 */
	public Integer getRecordsTotal() {
		return recordsTotal;
	}

	/**
	 * @param recordsTotal the recordsTotal to set
	 */
	public void setRecordsTotal(Integer recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	/**
	 * @return the recordsFiltered
	 */
	public Integer getRecordsFiltered() {
		return recordsFiltered;
	}

	/**
	 * @param recordsFiltered the recordsFiltered to set
	 */
	public void setRecordsFiltered(Integer recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	/**
	 * @return the entitiObject
	 */
	public T getEntitiObject() {
		return entitiObject;
	}

	/**
	 * @param entitiObject the entitiObject to set
	 */
	public void setEntitiObject(T entitiObject) {
		this.entitiObject = entitiObject;
	}

	

}
