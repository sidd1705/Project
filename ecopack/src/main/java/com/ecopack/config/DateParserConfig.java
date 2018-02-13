package com.ecopack.config;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.context.annotation.Configuration;

@Configuration
public class DateParserConfig {

	
	 DateFormat utcDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
	 DateFormat localDateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
	 String localDate;
	 
	 public String  utcToLocaleDateParser(String getUTCString){
		 
		 Date utcDate;
		try  {
			
			 utcDate = utcDateFormat.parse(getUTCString);
			 localDate = localDateFormat.format(utcDate);
			 
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 
		 return localDate;
	 }
	
}
