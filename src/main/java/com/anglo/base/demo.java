package com.anglo.base;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class demo {

	public static void main(String[] args) {
		
		ArrayList<String> date_array_list = new ArrayList<String>();
		
		date_array_list.add("20191111");
		date_array_list.add("20191112");
		date_array_list.add("20191113");
		date_array_list.add("20191114");
		
		System.out.println(date_array_list.size());
		System.out.println(date_array_list.get(date_array_list.size()-1));
		
		String[] date_array = {"20191111","20191215","20191217","20191218","20191220"};
		String date = date_array[0];
		//String date = null;
		
		//LinkedHashMap<String, Integer> gfg1 = new LinkedHashMap<String, Integer>();
		
		LocalDate ld = LocalDate.now();
//		System.out.println("-> "+(ld.getDayOfMonth()-1));
//		System.out.println("--> "+(ld.getDayOfMonth()-1));
		
		//System.out.println(ld.getYear() + "  " + ld.getMonthValue());
		
/*		//========================Fetch Data from gfg created hashmap
		
		gfg1 = HashMapFunction();

		int count=0;
		
		Iterator<Map.Entry<String, Integer>> itr = gfg1.entrySet().iterator();
		
		while(itr.hasNext()) {
			
			if(count==(gfg1.size()-1)) {
				
				Entry<String, Integer> entry = itr.next(); 
				date = entry.getKey();
			}
			count++;
		}
*/		
		int arrayYear = Integer.parseInt(date.substring(0,4));
		int arrayMonth = Integer.parseInt(date.substring(4,6));
		
		if(arrayYear!=ld.getYear() || arrayMonth!=ld.getMonthValue()) {
			
		    YearMonth startDate = YearMonth.of(2019, 8);
		    YearMonth endDate = YearMonth.of(ld.getYear(), ld.getMonthValue());

		    int year1, month1;
		    
		    while(startDate.isBefore(endDate)) {
		    	
		    	year1 = Integer.parseInt(startDate.toString().substring(0, 4));
		    	month1 = Integer.parseInt(startDate.toString().substring(5));
		    	
		        //System.out.println(startDate.toString().substring(0, 4)+startDate.toString().substring(5));
		        returnValues(year1, month1);
		        startDate = startDate.plusMonths(1);
		    }
		}
		//System.out.println(Math.abs(-1));		
		//return return_values;	
		
	}
	
	public static void returnValues(int year, int month) {
		
		String return_values = null;
		YearMonth yearMonth = YearMonth.of(year,month); 
		
		LocalDate firstDay = yearMonth.atDay( 1 );
		LocalDate lastDay = yearMonth.atEndOfMonth();
		
//		System.out.println("->"+firstDay.toString().replaceAll("-", ""));
//		System.out.println("->"+lastDay.toString().replaceAll("-", ""));
		
		return_values = firstDay.toString().replaceAll("-", "") + "/" + lastDay.toString().replaceAll("-", "");
		
		System.out.println(return_values);

	}
	
	public static LinkedHashMap<String, Integer> HashMapFunction() {
		
		LinkedHashMap<String, Integer> gfg = new LinkedHashMap<String, Integer>();
		gfg.put("201901", 31);
		gfg.put("201902", 31);
		gfg.put("201903", 31);
		gfg.put("201904", 31);
		gfg.put("201905", 31);
		gfg.put("201906", 31);
		gfg.put("201907", 31);
		gfg.put("201908", 31);
		
		return gfg;
		
	}
}
