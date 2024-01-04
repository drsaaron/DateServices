package com.blazartech.products.services.date;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author AAR1069
 * @version $Id: DateServices.java 292 2016-06-06 20:52:30Z aar1069 $
 */

/*
$Log$
********************************************************************************/

public interface DateServices {

    /**
     * get the date after a given date.
     * @param d
     * @return
     */
    public Date getNextDate(Date d);

    /**
     * is a given date within a given range.
     * @param d the date to check
     * @param startDate start date of range
     * @param endDate end date of range.  can be null
     * @return
     */
    public boolean isDateInRange(Date d, Date startDate, Date endDate);
    
    /**
     * return a date in a standard format.
     * 
     * @param d
     * @return 
     */
    public String formatDate(Date d);
    
    /**
     * return a local date in standard format
     * @param d
     * @return 
     */
    public String formatDate(LocalDate d);
    
    /**
     * parse a date from a standard format.
     * 
     * @param date
     * @return
     */
    public Date parseDate(String date);
    
    /**
     * parse a local date from a standard format
     * @param date
     * @return 
     */
    public LocalDate parseLocalDate(String date);
    
    /**
     * get the current timestamp
     * @return current timestamp
     */
    public Date now();
    
    /**
     * get the current date.  the time will be midnight on that date
     * @return current date
     */
    public Date getCurrentDate();
    
    /**
     * convert a date to local date
     * @param d
     * @return 
     */
    public LocalDate convertDateToLocalDate(Date d);
    
    /**
     * convert a local date back to date
     * @param d
     * @return 
     */
    public Date convertLocalDateToDate(LocalDate d);
}
