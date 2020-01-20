package com.anglo.function;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.text.ParseException;

import com.anglo.base.Base;
import com.anglo.common_utility.DBManager;
import com.aventstack.extentreports.ExtentTest;

public class Common_Function extends Base {

	static String table_name = null;
	
	public static void generateRecordForPublishingReport(String[] table_names_lst, String year_xml, String month_xml) throws FileNotFoundException, 
					SQLException, InterruptedException, ParseException {
		
		for (int i = 0; i < site_names_lst.size(); i++) {
			
			ExtentTest node = parent_logger.createNode(site_names_lst.get(i));

			System.out.println("***************Data for " + site_names_lst.get(i)+"**************");

			for (int j = 0; j < table_names_lst.length; j++) {

				if(!(site_names_lst.get(i).toUpperCase().equals("VENETIA") &&
						table_names_lst[j].toLowerCase().equals("fact_shovel_buckets"))) {
					
					//System.out.println(site_names_lst.get(i)+" "+table_names_lst.get(j));
					
					table_name = "dwh."+table_names_lst[j];

					//this will return set of records data
					DBManager.readDataQuery(site_names_lst.get(i), table_name, node);
				}
			}
			
			//table & site name required for calculating missing dates
			compareData.logicForComparingRecords(site_names_lst.get(i), year_xml, month_xml, node); 
				
			//To clear records for first table before moving onto second one.
			if(!DBManager.site_records.isEmpty()) {
					
				DBManager.site_records.clear();
			}
			if(!DBManager.count_of_records.isEmpty()) {
					
				DBManager.count_of_records.clear();
			}
		}
	}
}
