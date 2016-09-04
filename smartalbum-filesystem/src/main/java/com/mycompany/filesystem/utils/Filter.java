package com.mycompany.filesystem.utils;

import java.io.File;

import com.mycompany.filesystem.service.ImageDimension;

public interface Filter {
	
	public String[] execute(ImageDimension dimention, File tmp);

}
