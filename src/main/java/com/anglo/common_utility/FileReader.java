package com.anglo.common_utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileReader {

	public static String fileReader(String fileName) throws FileNotFoundException {
		
		File file = new File("./src/main/java/com/resources/SQL Queries/"+fileName+".sql");
		Scanner sc = new Scanner(file);
		
		sc.useDelimiter("\\Z");
		
		String query = sc.next();
		
		sc.close();
		return query;
	}
	
/*	//Method for reading table_names from DB
	public static String readTableNames_Query() throws FileNotFoundException {
		
		File file = new File("./src/main/java/com/resources/SQL Queries/table_names.sql");
		Scanner sc = new Scanner(file);
		
		sc.useDelimiter("\\Z");
		
		return sc.next();
	}
	
	//Method for reading site_names from DB
	public static String readSiteNames_Query() throws FileNotFoundException {
		
		File file = new File("./src/main/java/com/resources/SQL Queries/site_names.sql");
		Scanner sc = new Scanner(file);
		
		sc.useDelimiter("\\Z");
		
		return sc.next();
	}
*/}
