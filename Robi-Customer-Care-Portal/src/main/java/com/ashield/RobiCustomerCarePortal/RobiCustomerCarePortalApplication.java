package com.ashield.RobiCustomerCarePortal;

import java.sql.Date;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages={"com.ashield"})
@EnableMongoRepositories ("com.ashield.repository") 
@EnableAutoConfiguration
@EnableScheduling
public class RobiCustomerCarePortalApplication {
	 //@PostConstruct
	 // public void init(){
	    // Setting Spring Boot SetTimeZone
	  //  TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	//  }

	public static void main(String[] args) {
		SpringApplication.run(RobiCustomerCarePortalApplication.class, args);
	}
	 
	 }
