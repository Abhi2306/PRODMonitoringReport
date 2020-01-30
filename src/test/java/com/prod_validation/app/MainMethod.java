package com.prod_validation.app;

import java.util.ArrayList;

import org.testng.TestNG;

public class MainMethod {

	public static void main(String[] args) {
		
		TestNG test = new TestNG();
		//List<String> suite = Lists.newArrayList();
		ArrayList<String> suite = new ArrayList<String>();
		suite.add("/com.prod_monitoring.app/testng.xml");
		test.setTestSuites(suite);
		test.run();
	}
}
