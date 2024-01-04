/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.blazartech.products.services.date.impl;

import com.blazartech.products.services.date.DateServices;
import jakarta.inject.Provider;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

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

    @Autowired
    private Provider<DateFormat> dateFormatProvider;

    @Override
    public String formatDate(Date d) {
        DateFormat df = dateFormatProvider.get();
        return (d != null) ? df.format(d) : null;
    }

    @Override
    public Date parseDate(String date) {
        if (date == null || date.isEmpty()) {
            return null;
        }

        try {
            DateFormat df = dateFormatProvider.get();
            return df.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException("error parsing date " + date + " --> " + e.getMessage(), e);
        }
    }

    @Override
    public Date now() {
        return new Date();
    }
    
    @Override
    public Date getCurrentDate() {
        // convert to a string to get just the date part, then re-parse it.
        String dateString = formatDate(now());
        return parseDate(dateString);
    }

    @Override
    public LocalDate convertDateToLocalDate(Date dateToConvert) {
        Calendar c = Calendar.getInstance();
        c.setTime(dateToConvert);
        LocalDate d = LocalDate.now().withYear(c.get(YEAR)).withMonth(c.get(MONTH) + 1).withDayOfMonth(c.get(DAY_OF_MONTH));
        return d;
    }

    @Override
    public Date convertLocalDateToDate(LocalDate dateToConvert) {
        return java.util.Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }
    
    @Override
    public String formatDate(LocalDate d) {
        DateTimeFormatter df = DateTimeFormatter.ISO_DATE;
        return d.format(df);
    }
    
    @Override
    public LocalDate parseLocalDate(String d) {
        return LocalDate.parse(d);
    }
}
