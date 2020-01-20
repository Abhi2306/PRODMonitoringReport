package com.anglo.common_utility;

import com.anglo.base.Base;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;

public class ExtentReport extends Base{

	public static void startTest() {
		
		ExtentHtmlReporter reporter = new ExtentHtmlReporter("./Extent_Reports/reports.html");
		
		reporter.loadXMLConfig("./extent-config.xml");
		
		extent = new ExtentReports();
		extent.attachReporter(reporter);
		
		//logger.info(MarkupHelper.createLabel("Fact_Equipment_Status Report", ExtentColor.BLUE));
		
		extent.setSystemInfo("Host Name", System.getProperty("os.name"));
		extent.setSystemInfo("Java Version", System.getProperty("java.version"));
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		extent.setSystemInfo("Time Zone", System.getProperty("user.timezone"));	
		
		reporter.config().setChartVisibilityOnOpen(true);
		reporter.config().setDocumentTitle("PROD Monitoring Report");
		reporter.config().setReportName("Site/Table Wise Missing Dates Report");
		reporter.config().setTestViewChartLocation(ChartLocation.TOP);
		
	}
}
