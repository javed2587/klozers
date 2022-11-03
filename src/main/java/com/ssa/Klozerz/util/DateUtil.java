package com.ssa.Klozerz.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static String FOR_API_RESPONSE_FORMAT= "MM/dd/yyyy";
	public static String FOR_DB_SAVE_FORMAT="yyyy-MM-dd";
	
    public static Date stringToDate(String date, String format) throws ParseException {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            Date retDate = simpleDateFormat.parse(date);
            return retDate;
        } catch (Exception e) {
            return null;
        }
    }
    
    public static String formatDateReturnstring(Date date, String format) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        String retDate = simpleDateFormat.format(date);// .parse(simpleDateFormat.format(date));
        return retDate;
    }
    
    public static String stringToStringFormatChange(String date,String format) throws ParseException {
    	if(format == "MM/dd/yyyy") {
    	 	Date date1 = stringToDate(date,format);
        	String newDate = formatDateReturnstring(date1, FOR_DB_SAVE_FORMAT);
        	return newDate;
    	}else {
        	Date  date1 = stringToDate(date,format);
        	String newDate = formatDateReturnstring(date1, FOR_API_RESPONSE_FORMAT);
        	return newDate;
    	}

    }
    public static boolean validateDateFormat(String strDate)
    {
 	    /*
 	     * Set preferred date format,
 	     * For example MM-dd-yyyy, MM.dd.yyyy,dd.MM.yyyy etc.*/
 	    SimpleDateFormat sdfrmt = new SimpleDateFormat("MM/dd/yyyy");
 	    sdfrmt.setLenient(false);
 	    try
 	    {
 	        Date javaDate = sdfrmt.parse(strDate); 
 	        System.out.println(strDate+" is valid date format");
 	    }
 	    catch (ParseException e)
 	    {
 	        System.out.println(strDate+" is Invalid Date format");
 	        return false;
 	    }
 	    return true;
 	
    }
}
