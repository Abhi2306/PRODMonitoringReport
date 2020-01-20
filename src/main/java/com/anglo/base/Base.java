package com.anglo.base;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class Base {

	public static ResultSet rs = null;
	public static Connection con = null;
	public static String sitename = null;
	public static String query = null;
	public static String tablename = null;
	
	public static ExtentReports extent;
	public static ExtentTest parent_logger;
	
	public static String[] table_names_fms = {"Fact_Equipment_Status","Fact_Shovel_Buckets","Fact_Truck_Cycles"};
	public static String[] table_names_pm = {"Fact_Plant_Metrics"};
	public static String[] table_names_bl = {"Fact_KPI","Fact_System_Usage"};
	
	public static List<String> site_names_lst = null;
	
	public static LinkedHashMap<String, HashMap<String, Integer>> site_records = new LinkedHashMap<String, HashMap<String, Integer>>();
	public static LinkedList<Integer> count_of_records = new LinkedList<Integer>();
}
