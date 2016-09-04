package com.mycompany.services.smartalbum.vo;

import java.io.Serializable;

public class DataTableMetadata implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Integer DT_RowId;
	
	public DataTableMetadata(Integer id)
	{
		this.DT_RowId = id;
	}

	public Integer getDT_RowId() {
		return DT_RowId;
	}

	public void setDT_RowId(Integer dT_RowId) {
		DT_RowId = dT_RowId;
	}
	
	
	
}
