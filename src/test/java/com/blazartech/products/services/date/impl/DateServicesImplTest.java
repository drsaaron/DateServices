/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.products.services.date.impl;

import com.blazartech.products.services.date.DateServices;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author AAR1069
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {
    DateServicesImplTest.DateServicesConfig.class,
    DateFormatConfiguration.class
})
public class DateServicesImplTest {

    private static final Logger logger = LoggerFactory.getLogger(DateServicesImplTest.class);

    @Autowired
    private DateServices dateServices;

    @Value("${dateServices.date.format}")
    private String dateFormat;

    @Test
    public void testSetProperty() {
        System.out.println("testSetProperty");
        assertEquals(dateFormat, "yyyy-MM-dd");
    }

    @Configuration
    @PropertySource("classpath:test.properties")
    static class DateServicesConfig {

        @Bean
        public DateServices getDateServices() {
            return new DateServicesImpl();
        }
    }

    public DateServicesImplTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    private Date buildDate(String date) {
        try {
            DateFormat df = new SimpleDateFormat(dateFormat);
            return df.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException("error parsing date " + date + " --> " + e.getMessage(), e);
        }
    }
    
    private LocalDate buildLocalDate(String date) {
        return LocalDate.parse(date);
    }

    /**
     * Test of getNextDate method, of class DateServicesImpl.
     */
    @Test
    public void testGetNextDate() {
        logger.info("getNextDate");
        Date d = buildDate("2017-12-31");
        Date expResult = buildDate("2018-01-01");
        Date result = dateServices.getNextDate(d);
        assertEquals(expResult, result);
    }

    /**
     * Test of isDateInRange method, of class DateServicesImpl.
     */
    @Test
    public void testIsDateInRange() {
        logger.info("isDateInRange");
        Date d = buildDate("2018-01-01");
        Date startDate = buildDate("2017-12-15");
        Date endDate = buildDate("2018-01-15");
        boolean expResult = true;
        boolean result = dateServices.isDateInRange(d, startDate, endDate);
        assertEquals(expResult, result);
    }

    @Test
    public void testIsDateInRangeWithNullEnd() {
        logger.info("isDateInRangeWithNullend");
        Date d = buildDate("2018-01-01");
        Date startDate = buildDate("2017-12-15");
        Date endDate = null;
        boolean expResult = true;
        boolean result = dateServices.isDateInRange(d, startDate, endDate);
        assertEquals(expResult, result);
    }

    @Test
    public void testIsDateInRangeOutOfRange() {
        logger.info("isDateInRangeOutOfRange");
        Date d = buildDate("2018-01-02");
        Date startDate = buildDate("2017-12-15");
        Date endDate = buildDate("2018-01-01");
        boolean expResult = false;
        boolean result = dateServices.isDateInRange(d, startDate, endDate);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testIsDateInRange_localDate() {
        logger.info("isDateInRange_localDate");
        LocalDate d = buildLocalDate("2018-01-01");
        LocalDate startDate = buildLocalDate("2012-12-15");
        LocalDate endDate = buildLocalDate("2018-01-15");
        boolean expResult = true;
        boolean result = dateServices.isDateInRange(d, startDate, endDate);
        assertEquals(expResult, result);
    }

    @Test
    public void testIsDateInRangeWithNullEnd_localDate() {
        logger.info("isDateInRangeWithNullend_localDate");
        LocalDate d = buildLocalDate("2018-01-01");
        LocalDate startDate = buildLocalDate("2012-12-15");
        LocalDate endDate = null;
        boolean expResult = true;
        boolean result = dateServices.isDateInRange(d, startDate, endDate);
        assertEquals(expResult, result);
    }

    @Test
    public void testIsDateInRangeOutOfRange_localDate() {
        logger.info("isDateInRangeOutOfRange_localDate");
        LocalDate d = buildLocalDate("2018-01-02");
        LocalDate startDate = buildLocalDate("2012-12-15");
        LocalDate endDate = buildLocalDate("2018-01-01");
        boolean expResult = false;
        boolean result = dateServices.isDateInRange(d, startDate, endDate);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testIsDateInRange_localDate_onEndDate() {
        logger.info("isDateInRange_localDate_onStartDate");
        LocalDate d = buildLocalDate("2018-01-15");
        LocalDate startDate = buildLocalDate("2012-01-15");
        LocalDate endDate = buildLocalDate("2018-01-15");
        boolean expResult = true;
        boolean result = dateServices.isDateInRange(d, startDate, endDate);
        assertEquals(expResult, result);
    }

    @Test
    public void testIsDateInRangeWithNullEnd_localDate_onStartDate() {
        logger.info("isDateInRangeWithNullend_localDate");
        LocalDate d = buildLocalDate("2012-12-15");
        LocalDate startDate = buildLocalDate("2012-12-15");
        LocalDate endDate = null;
        boolean expResult = true;
        boolean result = dateServices.isDateInRange(d, startDate, endDate);
        assertEquals(expResult, result);
    }

    /**
     * Test of formatDate method, of class DateServicesImpl.
     */
    @Test
    public void testFormatDate() {
        logger.info("formatDate");
        String expectedDate = "2018-01-12";
        Date d = buildDate(expectedDate);
        String result = dateServices.formatDate(d);
        assertEquals(expectedDate, result);
    }

    @Test
    public void testParseDate() {
        logger.info("parseDate");
        String myDate = "2018-01-12";
        Date parsedDate = dateServices.parseDate(myDate);
        Date compareToDate = buildDate(myDate);
        assertEquals(parsedDate, compareToDate);
    }

    @Test
    public void testGetCurrentDate() {
        logger.info("get current date");
        
        Date d = new Date(); // the true current date
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        Date currentDate = dateServices.getCurrentDate();
        Calendar current = Calendar.getInstance();
        current.setTime(currentDate);
        
        // verify the date part
        int month = c.get(Calendar.MONTH), currentMonth = current.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR), currentYear = current.get(Calendar.YEAR);
        int day = c.get(Calendar.DAY_OF_MONTH), currentDay = current.get(Calendar.DAY_OF_MONTH);
        assertEquals(currentMonth, month);
        assertEquals(currentYear, year);
        assertEquals(currentDay, day);
        
        // verify the time part
        int hours = current.get(Calendar.HOUR_OF_DAY);
        int minutes = current.get(Calendar.MINUTE);
        assertEquals(hours, 0);
        assertEquals(minutes, 0);
    }
    
    @Test
    public void testConvertDateToLocalDate() {
        
        logger.info("convertDateToLocalDate");
        
        String testDateString = "2024-03-16";
        Date testDate = buildDate(testDateString);
        
        LocalDate localDate = dateServices.convertDateToLocalDate(testDate);
        
        assertEquals(LocalDate.parse(testDateString), localDate);
        assertEquals(2024, localDate.getYear());
        assertEquals(3, localDate.getMonthValue());
        assertEquals(16, localDate.getDayOfMonth());
    }
    
    @Test
    public void testConvertLocalDateToDate() {
        
        logger.info("convertLocalDateToDate");
        
        String testDateString = "2024-09-11";
        LocalDate testLocalDate = LocalDate.parse(testDateString);
        
        Date date = dateServices.convertLocalDateToDate(testLocalDate);
        
        assertEquals(buildDate(testDateString), date);
    }
    
    @Test
    public void testFormatDate_localDate() {
        logger.info("testFormatDate_localDate");
        
        String testDateString = "2024-07-12";
        LocalDate ld = LocalDate.parse(testDateString);
        String formattedString = dateServices.formatDate(ld);
        
        assertEquals(testDateString, formattedString);
    }
    
    @Test
    public void testGetNextLocalDate() {
        logger.info("getNextLocalDate");
        
        LocalDate value = LocalDate.parse("2025-12-31");
        LocalDate expectedValue = LocalDate.parse("2026-01-01");
        
        LocalDate ret = dateServices.getNextLocalDate(value);
        
        assertEquals(expectedValue, ret);
    }
    
    @Test
    public void testGetPriorLocalDate() {
        logger.info("getPriorLocalDate");
        
        LocalDate expectedValue = LocalDate.parse("2025-12-31");
        LocalDate value = LocalDate.parse("2026-01-01");
        
        LocalDate ret = dateServices.getPriorLocalDate(value);
        
        assertEquals(expectedValue, ret);
    }
    
    @Test
    public void testGetPriorLocalDate_dayCount() {
        logger.info("getPriorLocalDate_dayCount");
        
        LocalDate expectedValue = LocalDate.parse("2025-12-01");
        LocalDate value = LocalDate.parse("2026-01-01");
        
        LocalDate ret = dateServices.getPriorLocalDate(value, 31);
        
        assertEquals(expectedValue, ret);
    }
    
    @Test
    public void testGetPriorMonth() {
        logger.info("getPriorMonth");
        
        LocalDate expectedValue = LocalDate.parse("2025-11-30");
        LocalDate value = LocalDate.parse("2025-12-31");
        
        LocalDate ret = dateServices.getPriorMonth(value, 1);
        
        assertEquals(expectedValue, ret);
    }
}
