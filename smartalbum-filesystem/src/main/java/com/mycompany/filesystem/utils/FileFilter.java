/**
 * 
 */
package com.mycompany.filesystem.utils;

import java.io.File;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Lists;
import com.mycompany.filesystem.model.CheckedFile;
import com.mycompany.filesystem.service.ImageDimension;

/**
 * @author aristidemvou
 *
 */
public class FileFilter implements Filter {
	
	@Override
	public String[] execute(ImageDimension dimention, File tmp) {
		String[] existingFiles = tmp.list(FileUtils.imageFilter);
		List<String> result = Lists.newArrayList();
		for (String fileName : existingFiles) {
			if (!fileName.endsWith(".svn") && !fileName.endsWith(".DS_Store")) {
				switch (dimention) {
				case ORIGINAL:
					if (!fileName.contains("_small80")
							&& !fileName.contains("_small120")
							&& !fileName.contains("_small160")
							&& !fileName.contains("_small200")
							&& !fileName.contains("_medium")) {
						result.add(fileName);
					}
					break;
				case SIZE_120:
					if (fileName.contains("_small120")) {
						result.add(fileName);
					}
					break;
				case SIZE_160:
					if (fileName.contains("_small160")) {
						result.add(fileName);
					}
					break;
				case SIZE_200:
					if (fileName.contains("_small200")) {
						result.add(fileName);
					}
					break;
				case SIZE_80:
					if (fileName.contains("_small80")) {
						result.add(fileName);
					}
					break;
				case SIZE_MEDIUM:
					if (fileName.contains("_medium")) {
						result.add(fileName);
					}
					break;
				default:
					if (fileName.contains("_medium")) {
						result.add(fileName);
					}
					break;
				}
			}
		}
		return result.toArray(new String[result.size()]);
	}
	
	public String getNameWithoutExtension(String fileName)
	{
		if(StringUtils.isBlank(fileName))
		return StringUtils.EMPTY;
		
		String result = fileName;
		
		if(fileName.contains("_small120"))
		{
			result = fileName.replace("_small120", "");
		}
		
		if(fileName.contains("_small160"))
		{
			result = fileName.replace("_small160", "");
		}
		
		if(fileName.contains("_small200"))
		{
			result = fileName.replace("_small200", "");
		}
		
		if(fileName.contains("_medium"))
		{
			result = fileName.replace("_medium", "");
		}
		if(fileName.contains("_small80"))
		{
			result = fileName.replace("_small80", "");
		}
		return result;
	}
	
	public List<File> convertSelectedPicturesToFiles(ImageDimension dimention, File tmp, List<CheckedFile> pictures, boolean allFiles) {
		String[] existingFiles = tmp.list(FileUtils.imageFilter);
		String prefixe = tmp.getAbsolutePath();
		List<File> result = Lists.newArrayList();
		for (String fileName : existingFiles) {
			if (!fileName.endsWith(".svn") && !fileName.endsWith(".DS_Store") && (pictures.contains(fileName) || allFiles)) {
				switch (dimention) {
				case ORIGINAL:
					if (!fileName.contains("_small80")
							&& !fileName.contains("_small120")
							&& !fileName.contains("_small160")
							&& !fileName.contains("_small200")
							&& !fileName.contains("_medium")) {
						File file = new File(prefixe+File.separator+fileName);
						result.add(file);
					}
					break;
				case SIZE_120:
					if (fileName.contains("_small120")) {
						File file = new File(prefixe+File.separator+fileName);
						result.add(file);
					}
					break;
				case SIZE_160:
					if (fileName.contains("_small160")) {
						File file = new File(prefixe+File.separator+fileName);
						result.add(file);
					}
					break;
				case SIZE_200:
					if (fileName.contains("_small200")) {
						File file = new File(prefixe+File.separator+fileName);
						result.add(file);
					}
					break;
				case SIZE_80:
					if (fileName.contains("_small80")) {
						File file = new File(prefixe+File.separator+fileName);
						result.add(file);
					}
					break;
				case SIZE_MEDIUM:
					if (fileName.contains("_medium")) {
						File file = new File(prefixe+File.separator+fileName);
						result.add(file);
					}
					break;
				default:
					if (fileName.contains("_medium")) {
						File file = new File(prefixe+File.separator+fileName);
						result.add(file);
					}
					break;
				}
			}
		}
		return result;
	}
	

}
