package com.mycompany.database.smartalbum.vo;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class DataTableEntity<T> implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer draw;
	
	private Integer recordsTotal;
	
	private Integer recordsFiltered;
	
	private List<T> data;
	
	public Integer getDraw() {
		return draw;
	}

	public void setDraw(Integer draw) {
		this.draw = draw;
	}

	public Integer getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(Integer recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public Integer getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(Integer recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public List<T> getData() {
		if(data == null)
		{
			data = new LinkedList<>();
		}
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
}
