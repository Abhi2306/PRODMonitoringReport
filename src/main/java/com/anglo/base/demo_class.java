package com.anglo.base;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

public class demo_class {

	static SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
	
	public static void main(String[] args) throws ParseException {
	    
	    ArrayList<String> date = new ArrayList<>();
	    date.add("20191211");
	    date.add("20191215");
	    date.add("20191216");
	    date.add("20191220");
	    date.add("20191225");
	    ArrayList<String> missingDates_1 = new ArrayList<>();
	    LinkedList<String> missingDates_list = new LinkedList<>();
	    
	    int count_start_date = 0, count_end_date = 0;
	    
	    String firstLastDate = startEndDate(date);
	    String[] startEndDates = firstLastDate.split("/");
	    String startDate = startEndDates[0];
	    String endDate = startEndDates[1];
	    
	    if(!startDate.equals(date.get(0))) {
	    	date.add(0, startDate);
	    	count_start_date++;
	    }
	    if(!endDate.equals(date.get(date.size()-1))) {
	    	date.add(endDate);
	    	count_end_date++;
	    }
	    
	    System.out.println(date);
	    
	    for(int i=0;i<date.size()-1;i++) {
	    	
	    	Calendar calendar_1 = Calendar.getInstance();
	    	Calendar calendar_2 = Calendar.getInstance();
	    	
		    calendar_1.setTime(format.parse(date.get(i)));
		    calendar_2.setTime(format.parse(date.get(i+1)));		    
		    
		    long diff = calendar_2.getTimeInMillis() - calendar_1.getTimeInMillis();
		    long diffDays = diff/(24 * 60 * 60 * 1000);
		    
		    if(diffDays>1) {
		    	
		    	missingDates_1 = calculateMissingdate(date.get(i), date.get(i+1));
		    	
		    	for(int j=0;j<missingDates_1.size();j++) {
			    	
			    	missingDates_list.add(missingDates_1.get(j));
			    }
		    }  
	    }
	    
	    if(count_start_date>0) missingDates_list.add(0, startDate);
	    if(count_end_date>0) missingDates_list.add(endDate);
	    
	    String temp= "";
	    for(int m=0;m<missingDates_list.size();m++) {
	    	
	    	temp = temp + missingDates_list.get(m) + " ";
	    }
	    System.out.println(missingDates_list);
	    System.out.println("temp : "+temp);
	}
	
	public static ArrayList<String> calculateMissingdate(String date1, String date2) throws ParseException {
		
		String cd = null;
		ArrayList<String> missingDates = new ArrayList<>();
		
	    Calendar calendar1 = Calendar.getInstance();
	    calendar1.setTime(format.parse(date1));

	    Calendar calendar2 = Calendar.getInstance();
	    calendar2.setTime(format.parse(date2));

	    Date currentDate1 = calendar1.getTime();
	    Date currentDate2 = calendar2.getTime();
	    
	    LocalDateTime ldt = LocalDateTime.ofInstant(currentDate2.toInstant(), ZoneId.systemDefault());
	    LocalDateTime ld = ldt.minusDays(1);
	    Date endDate = Date.from( ld.atZone( ZoneId.systemDefault()).toInstant());
	    
	    while(!currentDate1.equals(endDate)){
	        calendar1.add(Calendar.DAY_OF_MONTH, 1);
	        currentDate1 = calendar1.getTime();
	        cd = format.format(currentDate1);
	        missingDates.add(cd);
	    }
	    
	    System.out.println(missingDates);
	    return missingDates;
	}
	
	public static String startEndDate(ArrayList<String> date2) {
		
		String date = date2.get(0);
		String return_values = null;
		
		LocalDate ld = LocalDate.now();
			
		YearMonth yearMonth = YearMonth.of( Integer.parseInt(date.substring(0,4)), 
				Integer.parseInt(date.substring(4,6))); 
		
		LocalDate firstDay = yearMonth.atDay( 1 );
		LocalDate lastDay = yearMonth.atEndOfMonth();
		
		System.out.println("->"+firstDay.toString().replaceAll("-", ""));
		System.out.println("->"+lastDay.toString().replaceAll("-", ""));
		
		if(ld.getMonth().getValue()==Integer.parseInt(date.substring(4,6))) {
			
			lastDay = ld;
			return_values = firstDay.toString().replaceAll("-", "") + "/" + lastDay.toString().replaceAll("-", "");
		}
		else 
			return_values = firstDay.toString().replaceAll("-", "") + "/" + lastDay.toString().replaceAll("-", "");
		
		System.out.println(return_values);
		
		return return_values;
	}
}
