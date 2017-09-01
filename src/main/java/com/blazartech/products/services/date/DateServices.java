package com.blazartech.products.services.date;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



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
     * parse a date from a standard format.
     * 
     * @param date
     * @return
     */
    public Date parseDate(String date);
}
