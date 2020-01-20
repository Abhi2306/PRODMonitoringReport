package com.anglo.common_utility;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class CalculateMissingMonths {

	public static int missingMonths(int year, int month, ExtentTest logger) {
		
		LocalDate ld = LocalDate.now();
		int count = 0;
		
		if(year!=ld.getYear() || month!=ld.getMonthValue()) {
			
		    YearMonth startDate = YearMonth.of(year,month);
		    YearMonth endDate = YearMonth.of(ld.getYear(),ld.getMonthValue());

		    System.out.println(startDate);
		    System.out.println(endDate.plusMonths(1));
		    int year1, month1;
		    
		    String text;
		    
		    while((startDate).isBefore(endDate.plusMonths(1))) {
		    	
		    	year1 = Integer.parseInt(startDate.toString().substring(0, 4));
		    	month1 = Integer.parseInt(startDate.toString().substring(5));
		    	
		    	System.out.println(year1 + "=" + month1);
		    	
		    	if(month1==ld.getMonthValue()) {
		    		
		    		text = year1 +" "+ Month.of(month1)+" - "+(ld.getDayOfMonth()-1)+" days";
		    	}else {
		    		
		    		text = year1 +" "+ Month.of(month1)+" - "+
					    	YearMonth.of(year1, month1).lengthOfMonth()+" days";
		    	}
		    	
		    	if(count>0) {
		    		logger.log(Status.FAIL, MarkupHelper.createLabel(text, ExtentColor.RED));
		    	}
		    	
		        startDate = startDate.plusMonths(1);
		        count++;
		    }
		}

		return count;
	}
}
