package com.anglo.common_utility;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

public class CalculateMissingDates {

	static SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
	
	public static String missingDates(String tableName, String siteName, String year_month) throws ParseException, FileNotFoundException, SQLException {
		
		//Calling method for executing query to fetch list of dates for that month
		ArrayList<String> date_list = ExecuteMissingDateQuery.missingDatesQuery(tableName, siteName, year_month);

	    ArrayList<String> missingDates_1 = new ArrayList<>();
	    LinkedList<String> missingDates_list = new LinkedList<>();
	    
	    int count_start_date = 0, count_end_date = 0;
	    
	  //Function for finding out start and end date of that month
	    String firstLastDate = startEndDate(date_list); 
	    
	    String[] startEndDates = firstLastDate.split("/");
	    String startDate = startEndDates[0];
	    String endDate = startEndDates[1];
	    System.out.println(date_list.size());
	    if(!startDate.equals(date_list.get(0))) {
	    	System.out.println("=>"+startDate+"--"+date_list.get(0));
	    	date_list.add(0, startDate);
	    	count_start_date++;
	    }
	    if(!endDate.equals(date_list.get(date_list.size()-1))) {
	    	System.out.println("==>>"+endDate+"--"+date_list.get(date_list.size()-1));
	    	date_list.add(endDate);
	    	count_end_date++;
	    }
	    
	    for(int i=0;i<date_list.size()-1;i++) {
	    	
	    	Calendar calendar_1 = Calendar.getInstance();
	    	Calendar calendar_2 = Calendar.getInstance();
	    	
		    calendar_1.setTime(format.parse(date_list.get(i)));
		    calendar_2.setTime(format.parse(date_list.get(i+1)));		    
		    
		    long diff = calendar_2.getTimeInMillis() - calendar_1.getTimeInMillis();
		    long diffDays = diff/(24 * 60 * 60 * 1000);
		    
		    if(diffDays>1) {
		    	
		    	missingDates_1 = calculateDates(date_list.get(i), date_list.get(i+1));//calling function
		    	
		    	for(int j=0;j<missingDates_1.size();j++) {
			    	
			    	missingDates_list.add(missingDates_1.get(j));
			    }
		    }  
	    }
	    System.out.println(missingDates_list);
	    
	    if(count_start_date>0) missingDates_list.add(0, startDate);
	    if(count_end_date>0) missingDates_list.add(endDate);
	    
	    String temp= "";
	    for(int m=0;m<missingDates_list.size();m++) {
	    	
	    	temp = temp + missingDates_list.get(m) + "  ";
	    }
	    
	    return temp;
	}
	
	//Function for calculating missing dates between two dates
	public static ArrayList<String> calculateDates(String date1, String date2) throws ParseException {
		
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
	 
	    return missingDates;
	}
	
	public static String startEndDate(ArrayList<String> date2_list) {
		
		String date = date2_list.get(0);
		String return_values = null;
		
		YearMonth yearMonth = YearMonth.of( Integer.parseInt(date.substring(0,4)), 
				Integer.parseInt(date.substring(4,6))); 
		
		LocalDate ld = LocalDate.now();
		
		LocalDate firstDay = yearMonth.atDay( 1 );
		LocalDate lastDay = yearMonth.atEndOfMonth();
		
		if(ld.getMonth().getValue()==Integer.parseInt(date.substring(4,6)) &&
				ld.getYear() == Integer.parseInt(date.substring(0,4))) {
			
			lastDay = ld.minusDays(1);
			return_values = firstDay.toString().replaceAll("-", "") + "/" + lastDay.toString().replaceAll("-", "");
		}
		else 
			return_values = firstDay.toString().replaceAll("-", "") + "/" + lastDay.toString().replaceAll("-", "");
		
		System.out.println(return_values);
		
		return return_values;
	}


}
