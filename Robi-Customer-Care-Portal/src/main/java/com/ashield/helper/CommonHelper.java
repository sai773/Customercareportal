package com.ashield.helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.servlet.annotation.HttpConstraint;

import org.apache.el.parser.ParseException;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class CommonHelper {


		public static String getStrDate() {
			String date = null;
			try{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date localTime = new Date(); 
				sdf.setTimeZone(TimeZone.getTimeZone("GMT+6"));
				date = sdf.format(localTime);
			}catch(Exception e){
			//	ErrorLogger.getLogger().error("Exception in StandardTimeZone : ",e);
			}
			return date;
		}

		public static int getHour() {
			String hour = "0";
			try{
				SimpleDateFormat sf = new SimpleDateFormat("HH");
				sf.setTimeZone(TimeZone.getTimeZone("GMT+6"));
				hour = sf.format(new Date());
			}catch(Exception e){
				//ErrorLogger.getLogger().error("Exception in getHour : ",e);
			}
			return Integer.parseInt(hour);
		}

		public static String getEndDate(String valid, String action) {
			int sum = 0;
			String num = "";
			String duration = "";
			String res = "";
			MDC.get("Robi-UNIQUE-ID");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sdf.setTimeZone(TimeZone.getTimeZone("GMT+6")); 
			try{
				if(valid.length() == 2) {
					duration = valid.substring(0, 1);
					num = valid.substring(1, 2);
				} else if(valid.length() > 2) {
					duration = valid.substring(0, 1);
					num = valid.substring(1, 3);
				}

				if(duration.equalsIgnoreCase("D")) {
					sum = Integer.parseInt(num);
				} else if (duration.equalsIgnoreCase("W")) {
					sum = Integer.parseInt(num) * 7;
				} else if (duration.equalsIgnoreCase("M")) {
					sum = Integer.parseInt(num) * 30;
				} else if (duration.equalsIgnoreCase("Y")) {
					sum = Integer.parseInt(num) * 365;
				}

				if(action.equalsIgnoreCase("rnw") || action.equalsIgnoreCase("act_grace")) {
					res = String.valueOf(1);	
				} else {
					//sdf.setTimeZone(TimeZone.getTimeZone("GMT+6")); 
					Calendar c = Calendar.getInstance();
					c.add(Calendar.DATE, sum);
					String endDate = sdf.format(c.getTime());
					//System.out.println("endDate : "+endDate);
					res = endDate;
				} 
			}catch(Exception e){
			//	ErrorLogger.getLogger().error("Exception in getEndDate : ",e);
			}
			return res;
		}

	
		public static String getStrDateFromDateFormat(Date date) {
			String strdate = null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			strdate = sdf.format(date);
			return strdate;
		}

		public static String getValidityInWords(String val){
			String valInWords = "";
			if(val!=null && !val.equalsIgnoreCase("0")) {
				switch(val.substring(0, 1)) {
				case "D":
					if(val.length()==2) {
						if(val.substring(1, 2).equalsIgnoreCase("1")){
							valInWords = "Day";
						} else {
							valInWords = val.substring(1, 2)+"Days";
						}
					} else {
						valInWords = val.substring(1, 3)+"Days";
					}
					break;
				case "W":
					valInWords = "Week";
					break;
				case "M":
					valInWords = "Month";
					break;
				case "Y":
					valInWords = "Year";
					break;
				default:
					valInWords = "";
					break;
				}
			}
			//return val.substring(1, val.length())+valInWords;
			return valInWords;
		}
		
		public static String getDaysCount(String val) {
			String valInWords = "";
			if(val!=null && !val.equalsIgnoreCase("0")) {
				switch(val.substring(0, 1)) {
				case "D":
					if(val.length()==2) {
						if(val.substring(1, 2).equalsIgnoreCase("1")){
							valInWords = "DAILY";
						} else {
							valInWords = val.substring(1, 2)+"DAYS";
						}
					} else {
						valInWords = val.substring(1, 3)+"DAYS";
					}
					break;
				case "W":
					valInWords = "WEEKLY";
					break;
				case "M":
					valInWords = "MONTHLY";
					break;
				case "Y":
					valInWords = "YEARLY";
					break;
				default:
					valInWords = "";
					break;
				}
			}
			return valInWords;
		}
		
		
	}



