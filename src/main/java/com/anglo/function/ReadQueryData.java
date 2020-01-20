package com.anglo.function;

import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import com.anglo.base.Base;
import com.anglo.common_utility.DBManager;
import com.aventstack.extentreports.ExtentTest;

public class ReadQueryData extends Base{

	public static LinkedHashMap<String, HashMap<String, Integer>> readData(ResultSet rs1, ExtentTest logger) throws SQLException {

		int column_count = rs1.getMetaData().getColumnCount();
		int row_count = 0;
		
		System.out.println("column count : " + column_count);
		
		LinkedHashMap<String, Integer> map_records = new LinkedHashMap<String, Integer>();
		
		while (rs1.next()) {

			map_records.put(rs1.getString(1)+rs1.getString(2), rs1.getInt(3));
			row_count++;
		}
		
		DBManager.site_records.put(DBManager.tablename, map_records);
		
		DBManager.count_of_records.add(row_count);
		System.out.println(DBManager.count_of_records);
		
		return DBManager.site_records;
	}
	
	//Method for reading table names from DB
	public static LinkedList<String> readSiteNames(String query_type) throws FileNotFoundException, SQLException{
		
		ResultSet rs3 = DBManager.readSiteQuery_resultSet(query_type);
		
		LinkedList<String> site_names_list = new LinkedList<String>();
		
		while (rs3.next()) {

			site_names_list.add(rs3.getString(1));
		}
		
		System.out.println("Site Name list : "+site_names_list);
		return site_names_list;
	}

	
/*	//Method for reading table names from DB
	public static LinkedList<String> readTableNames() throws FileNotFoundException, SQLException{
		
		ResultSet rs2 = DBManager.readTableQuery_resultSet();
		
		LinkedList<String> table_names_list = new LinkedList<String>();
		
		while (rs2.next()) {

			table_names_list.add(rs2.getString(1));
		}
		
		System.out.println("Table Name list : "+table_names_list);
		return table_names_list;
	}
	*/
}
