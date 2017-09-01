/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.blazartech.products.services.date.impl;

import com.blazartech.products.services.date.DateServices;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author AAR1069
 * @version $Id: DateServicesImpl.java 292 2016-06-06 20:52:30Z aar1069 $
 */

/*
$Log$
********************************************************************************/

@Component
public class DateServicesImpl implements DateServices {

    @Override
    public Date getNextDate(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.DATE, 1);
        return c.getTime();
    }

    @Override
    public boolean isDateInRange(Date d, Date startDate, Date endDate) {
        if (d.after(startDate) || d.equals(startDate)) {
            if (endDate == null || (d.before(endDate) || d.equals(endDate))) {
                return true;
            }
        }
        return false;
    }
    
    @Value("${dateServices.date.format}")
    private String dateFormat;

    @Override
    public String formatDate(Date d) {
        DateFormat df = new SimpleDateFormat(dateFormat);
        return (d != null) ? df.format(d) : null;
    }

    @Override
    public Date parseDate(String date) {
        if (date == null || date.isEmpty()) {
            return null;
        }

        try {
            DateFormat df = new SimpleDateFormat(dateFormat);
            return df.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException("error parsing date " + date + " --> " + e.getMessage(), e);
        }
    }

    
}
