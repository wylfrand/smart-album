package com.mycompany.services.smartalbum.vo;

import java.io.Serializable;

public abstract class DataTableEntryVO implements Serializable{
	private static final long serialVersionUID = -7042878411608396483L;
	public abstract DataTableEnumType getDataTableType();
}
