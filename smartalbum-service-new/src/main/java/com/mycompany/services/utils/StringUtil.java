/*
 * OPIAM Suite
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */

package com.mycompany.services.utils;

import org.apache.log4j.Logger;


/**
 * Class with utility methods for strings.
 */
public final class StringUtil
{
    /** Instance of logger. */
    private static Logger _logger = Logger.getLogger(StringUtil.class);

    /** Utility class. */
    private StringUtil()
    {
    }

    
    /**
     * Replace a pattern in string.
     *
     * @param src    Input string.
     * @param limit  Limit size of the line to display.
     * @param motif  String to insert as a separator.
     *
     * @return Modified string.
     */
    public static String displayString(String src, int limit, String motif)
    {
        if (src != null)
        {
        	if (src.length() > limit)
        	{
                StringBuffer returnedString = new StringBuffer();
                if (motif == null)
                {
                	motif= "\n";
                }
                int begIndex= 0;
                int endIndex = limit;
                int sizeSrc = src.length();
                while (endIndex < sizeSrc)
                {
                	returnedString.append(src.substring(begIndex, endIndex));
                	returnedString.append(motif);
                	
                	begIndex = endIndex;
                	endIndex = endIndex + limit;
                }
            	returnedString.append(src.substring(begIndex));
            	return returnedString.toString();
        	}
        }
        return src;
    }

}
