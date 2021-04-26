package com.ashield.pojoClasses;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryEntity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Document
@QueryEntity

public class cdr_logs {
	

	
	public cdr_logs() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	@Id
	private String id;
	
	@Field(value="Ashield transaction ID")
	private String ashieldid;
	

	
	@Field(value="Request Date time")
	private Date requestTime ;
	
	
	@Field(value="Referrer")
	private String referrer;
	
	@Field(value="Package Name")
	private String packagename;
	
	@Field(value="SP ID")
	private String spid;
	
	@Field(value="SP Name")
	private String spname;
	
	@Field(value="Product ID")
	private String productId;
	
	@Field(value="Product Name")
	private String productname;
	
	@Field(value="BUA1")
	private String bua1;
	
	@Field(value="Mip")
	private String mip;
	
	@Field(value="Callback URL")
	private String callbackurl;
	
	@Field(value="HE-HE BOT Status")
	private String hebotstatus;
	
	@Field(value="NetworkType")
	private String networktype;
	
	@Field(value="NetworkType ")
	private String networktype1;
	
	@Field(value="Get Image Request Date Time")
	private String getimagedatetime;
	
	@Field(value="Get Image Response code")
	private String getimgresponse;
	
	@Field(value="Click Coordinates")
	private String clickcordinates;
	
	@Field(value="Check Image Request Date Time")
	private String checkimagerequestdate;
	
	@Field(value="Check Image Response code")
	private String checkimageresponse;
	
	@Field(value="Click Status")
	private String clickstatus;
	
	@Field(value="Click Status Code")
	private String clickstatuscode;
	
	@Field(value="BUA2")
	private String bua2;
	
	@Field(value="IP2")
	private String ip2;
	
	@Field(value="Platform")
	private String platform;
	
	@Field(value="Scren Size")
	private String scrensize;
	
	@Field(value="OS CPU")
	private String oscpu;
	
	@Field(value="Iframe")
	private String iframe;

	@Field(value="Playstore APP")
	private String playstoreapp;
	
	@Field(value="Device OS")
	private String deviceos;
	
	@Field(value="Browser Name")
	private String browseros;
	
	@Field(value="Device Model")
	private String devicemodel;
	
	@Field(value="SDP Charge Status")
	private String sdpchargestatus;
	
	@Field(value="SDP Response Code")
	private String sdpresponsecode;
	
	@Field(value="Result Code")
	private String resultcode;
	
	@Indexed(unique = false)
	@Field(value="MSISDN")
	private String msisdn;
	
	@Field(value="Fraud_indicator")
	private String Fraud_indicator;
	
	@Field(value="RollUp")
	private String RollUp;
	
	
	
	}
