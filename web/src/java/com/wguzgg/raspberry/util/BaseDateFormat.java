package com.wguzgg.raspberry.util;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BaseDateFormat extends SimpleDateFormat {

    private String customizeDF;
    private String[] dfs = new String[] {
            "yyyy-MM-dd'T'HH:mm:ss.SSSZ", 
            //Fri, 04 Nov 2011 01:40:43
            "EEE, dd MMM yyyy HH:mm:ss", 
            //Tue Oct 11 05:42:00 PDT 2011
            "EEE MMM dd HH:mm:ss z yyyy", 
            //Oct 11 05:42:00 PDT 2011
            "MMM dd HH:mm:ss z yyyy"};

	public BaseDateFormat() {
	    this(null);
	}
	
	public BaseDateFormat(String dateFormat) {
	    if (dateFormat != null && dateFormat.trim().length() > 0) {
	        customizeDF = dateFormat.trim();
	    }
	}
	
	@Override
	public StringBuffer format(Date date, StringBuffer toAppendTo,
			FieldPosition fieldPosition) {
	    String df = customizeDF;
	    if (df == null) {
	        df = dfs[0];
	    }
		return new SimpleDateFormat(df).format(date, toAppendTo, fieldPosition);
	}

	@Override
	public Date parse(String source, ParsePosition pos) {
	    if (customizeDF != null) {
	        Date result = new SimpleDateFormat(customizeDF).parse(source, pos);
	        if (result != null) {
	            return result;
	        }
	    }
		for (String df : dfs) {
			Date result = new SimpleDateFormat(df).parse(source, pos);
			if (result != null) {
				return result;
			}
		}
		return null;
	}
}
