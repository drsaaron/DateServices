/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.products.services.date.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 *
 * @author AAR1069
 */
@Configuration
public class DateFormatConfiguration {
    
    @Value("${dateServices.date.format}")
    private String dateFormat;
    
    @Bean
    @Scope("singleton")
    public DateFormat getDateFormat() {
        return new SimpleDateFormat(dateFormat);
    }
}
