package com.anglo.common_utility;

import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.anglo.base.Base;

public class ExecuteMissingDateQuery extends Base {

	public static ArrayList<String> missingDatesQuery(String tableName, String siteName, String year_month) throws SQLException, FileNotFoundException {
		
		ArrayList<String> dates = new ArrayList<>();
		
		Statement stmt = con.createStatement();
		
		String query = FileReader.fileReader("datesQuery");
		
		if(source_system.equals("si")) {
			
			if(tableName.contains("production_coal")) {
				
				query = query.replace("column_name_variable", "REPLACE(SUBSTRING(data_date,0,11),'-','')");
			}else {
				
				query = query.replace("column_name_variable", "REPLACE(SUBSTRING(production_date,0,11),'-','')");
			}
		}else if(source_system.equals("fms") || source_system.equals("pm")){
			
			query = query.replace("column_name_variable", "date_key");
		}
		
		String query1 = query.replaceAll("table_name_variable", tableName);
		String query2 = query1.replaceAll("site_name_variable", siteName);
		String query3 = query2.replaceAll("year_month_variable", year_month);
		
		System.out.println(query3);
		ResultSet rs = stmt.executeQuery(query3);
		
		while(rs.next()) {
			
			dates.add(rs.getString(1));
		}
		
		//System.out.println("-->> "+dates);
		return dates;
	}
}
