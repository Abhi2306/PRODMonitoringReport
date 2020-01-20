package com.anglo.function;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

import com.anglo.base.Base;
import com.anglo.common_utility.CalculateMissingDates;
import com.anglo.common_utility.CalculateMissingMonths;
import com.anglo.common_utility.DBManager;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class compareData extends Base {

	public static LinkedHashMap<String, HashMap<String, Integer>> records_data = new LinkedHashMap<String, HashMap<String, Integer>>();

	public static void logicForComparingRecords(String siteName, String year_xml, String month_xml, ExtentTest logger) throws SQLException, InterruptedException, FileNotFoundException, ParseException {

		records_data = DBManager.site_records;
		LinkedList<Integer> count_arr = DBManager.count_of_records;
		LocalDate ld = LocalDate.now();
		
		String missingDatesList = null;

		int count_outer = 0;
		int current_month = ld.getMonthValue();

		String year = null, month = null;

		for (Entry<String, HashMap<String, Integer>> records : records_data.entrySet()) {

			System.out.println("====================");
			System.out.println(records.getKey() + " -> " + records.getValue());

			int count_inner = 0, fail_status_count=0;
			int year_int = 0, month_int=0;
			
			count_outer++; // count for getting actual number of sites for which data is populated
			
			String table_name_rep = records.getKey().replaceAll("dwh.", "");
			String table_name_rep1 = table_name_rep.replaceAll("_", " ");
			
			//Printing Site_Name in report
			logger.info(MarkupHelper.createLabel(table_name_rep1.toUpperCase(), ExtentColor.BLUE)); 

			for (Entry<String, Integer> rec : records.getValue().entrySet()) {

				/*System.out.println("count_outer : "+count_outer);
				System.out.println("count_inner : "+count_inner);
				System.out.println("count_arr : "+count_arr.get(count_outer - 1));*/
				//This will be the input for my function
				year = rec.getKey().substring(0, 4);
				month = rec.getKey().substring(4, 6);
				
				//try catch for handling NULL values in month & year column
				try { year_int = Integer.parseInt(year);
				} catch (Exception e) { year = "1900";}

				try { month_int = Integer.parseInt(month);
				} catch (Exception e) { month = "00";}
				
				count_inner++; // count for getting actual number of records, sitewise

				// Loop for comparing values only after provided configurable year and month
				if ((year_int >= Integer.parseInt(year_xml) && year_int != 1900) &&
						(month_int >= Integer.parseInt(month_xml) && month_int != 00)) { 

					YearMonth noOfDays = YearMonth.of(year_int, month_int);

					// Loop is to check for the current month data
					if (count_arr.get(count_outer - 1) == count_inner && current_month==month_int) { 

						System.out.print(year + "::" + Month.of(month_int).name() + ":");
						System.out.println(ld.getDayOfMonth() == rec.getValue());
						
						//Checking with minus 1 day date
						if ((ld.getDayOfMonth()-1) != rec.getValue()) {

							//Decreasing count as need to compare it with current day -1 data 
							int count1 = (ld.getDayOfMonth()-1)-rec.getValue().intValue();
						
							//System.out.println("1 - records.getKey() : "+records.getKey());
							
							missingDatesList = CalculateMissingDates.missingDates(records.getKey(), siteName, year+month);
							
							//System.out.println(count1+"---"+"missing : "+missingDatesList+"---"+year+month);
							
							logger.log(Status.FAIL, MarkupHelper.createLabel(year +" "+Month.of(month_int).name()+" - "+Math.abs(count1)+" days", ExtentColor.RED));
							
							logger.log(Status.INFO, missingDatesList);
							
							fail_status_count++;
						}
					} else if(year_int != 1900) { 

						// Loop is to check for all previous month data where days are not populated as expected
						if (noOfDays.lengthOfMonth() != rec.getValue()) { 							
							
							int count2 = noOfDays.lengthOfMonth()-rec.getValue().intValue();
							
							//System.out.println("2 - records.getKey() : "+records.getKey());

							missingDatesList = CalculateMissingDates.missingDates(records.getKey(), siteName, year+month);
							
							System.out.print(year + "->" + Month.of(month_int).name() + ":");
							System.out.println(noOfDays.lengthOfMonth() == rec.getValue());

							//Printing logs on Report
							logger.log(Status.FAIL, MarkupHelper.createLabel(year +" "+Month.of(month_int).name()+" - "+count2+" days", ExtentColor.RED));
							
							/*logger.log(Status.FAIL, "Data is missing for year " + 
									logger.info(MarkupHelper.createLabel(year, ExtentColor.RED)) + " month "+ 
									logger.info(MarkupHelper.createLabel(Month.of(month_int).name(), ExtentColor.RED))+" - "+count2);*/
							logger.log(Status.INFO, missingDatesList);
							
							fail_status_count++;
						}
					}
					
					//If loop Function for calculating Missing Months
					if (count_arr.get(count_outer - 1) == count_inner) {
						
						int count_temp = CalculateMissingMonths.missingMonths(year_int, month_int, logger);
						if(count_temp>0) fail_status_count++;
					}

				}
			}
			if(fail_status_count==0) {
				
				logger.log(Status.PASS, MarkupHelper.createLabel("No Data LOSS for this table..!!", ExtentColor.GREEN));
			}
		}
		System.out.println();
	}
}
