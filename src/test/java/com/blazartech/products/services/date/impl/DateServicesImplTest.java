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
import java.util.Calendar;
import java.util.Date;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
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
    DateServicesImplTest.DateServicesConfig.class
}
)
public class DateServicesImplTest {

    private static final Logger logger = Logger.getLogger(DateServicesImplTest.class);

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
    public void testGetCurrentDate() {
        logger.info("get current date");
        
        /* I'm not sure how else to test this one other than by formatting the
            current date and what the service gives as current date and confirming
            they are the same. */
        Date d = new Date();
        String expectedFormattedString = dateServices.formatDate(d);
        Date currentDate = dateServices.getCurrentDate();
        String result = dateServices.formatDate(currentDate);
        assertEquals(expectedFormattedString, result);
        
        // verify the time part
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        int hours = c.get(Calendar.HOUR_OF_DAY);
        int minutes = c.get(Calendar.MINUTE);
        assertEquals(hours, 0);
        assertEquals(minutes, 0);
    }
}
