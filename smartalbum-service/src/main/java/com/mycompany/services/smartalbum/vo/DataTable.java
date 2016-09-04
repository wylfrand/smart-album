package com.mycompany.services.smartalbum.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataTable implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer draw;
	
	private Integer recordsTotal;
	
	private Integer recordsFiltered;
	
	private List<DataTableEntryVO> data;
	
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

	public List<DataTableEntryVO> getData() {
		if(data == null)
		{
			data = new ArrayList<>();
		}
		return data;
	}

	public void setData(List<DataTableEntryVO> data) {
		this.data = data;
	}

	
}
