package com.anglo.common_utility;

import com.anglo.base.Base;
import com.anglo.function.ReadQueryData;
import com.aventstack.extentreports.ExtentTest;

import java.io.FileNotFoundException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager extends Base {
	
	public static void DBConnection()
			throws ClassNotFoundException, SQLException, FileNotFoundException {
		
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		
		//Prod Connectivity
		con = DriverManager.getConnection(
				"jdbc:sqlserver://mneudbpshared001.database.windows.net:1433;database=mneu-db-p-shared-001;user=azureadmin@mneudbpshared001;password=Anglo@123;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");		
		
		//Test connectivity
		/*con = DriverManager.getConnection(
				"jdbc:sqlserver://mneudbtshared001.database.windows.net:1433;database=mneu-db-t-shared-001;user=azureadmin@mneudbtshared001;password=Anglo@123;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");		
		*/
		if(con != null) {
			System.out.println("Connection is successful..!!");	
		}
	}

	public static void readDataQuery(String site_name, String table_name, ExtentTest logger) throws SQLException, FileNotFoundException {

		Statement stmt1 = con.createStatement();

		tablename = table_name;

		String query = FileReader.fileReader("record_fetching_query");

		String replaced_query = query.replaceAll("Site_Name_Variable", site_name);
		
		String replaced_query_1 = replaced_query.replaceAll("Table_Name_Variable", table_name);

		System.out.println("=====Data For " + table_name + " table=====");
		System.out.println(replaced_query_1);

		rs = stmt1.executeQuery(replaced_query_1);

		ReadQueryData.readData(rs,logger);
	}

	//Method specifically for reading site names from DB
	public static ResultSet readSiteQuery_resultSet(String site_name_query) throws SQLException, FileNotFoundException {
		
		Statement stmt2 = con.createStatement();
		
		//String query_SiteName = FileReader.readSiteNames_Query();
		String query_SiteName = FileReader.fileReader(site_name_query);
		//System.out.println(query_SiteName);
		
		ResultSet rs2 = stmt2.executeQuery(query_SiteName);
		
		return rs2;
	} 
	
/*	//Method specifically for reading table names from DB
	public static ResultSet readTableQuery_resultSet() throws SQLException, FileNotFoundException {
		
		Statement stmt2 = con.createStatement();
		
		//String query_tableName = FileReader.readTableNames_Query();
		String query_tableName = FileReader.fileReader("table_names");
		//System.out.println(query_tableName);
		
		ResultSet rs1 = stmt2.executeQuery(query_tableName);
		
		return rs1;
	}*/
}
