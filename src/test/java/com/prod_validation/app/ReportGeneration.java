package com.prod_validation.app;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.text.ParseException;

//import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.anglo.base.Base;
import com.anglo.common_utility.DBManager;
import com.anglo.common_utility.ExtentReport;
import com.anglo.function.Common_Function;
import com.anglo.function.ReadQueryData;

public class ReportGeneration extends Base {
	
	@BeforeSuite
	public void initialSetUp() throws ClassNotFoundException, FileNotFoundException, SQLException {
		
		DBManager.DBConnection();
		ExtentReport.startTest();
	}
	
	@Parameters({"source_system","year","month"})
	@Test
	public void prodMonitoringSupport(String source_system, String year_xml, String month_xml) throws FileNotFoundException, SQLException, InterruptedException, ParseException {

		String[] table_names_lst = null;
		
		Base.source_system = source_system;
		
		//Select site & table names as per business unit
		if(source_system.equals("fms")) {
			
			table_names_lst = table_names_fms;
			site_names_lst = ReadQueryData.readSiteNames("fms_site_names");
			parent_logger = extent.createTest("FMS Missing Report");
			parent_logger.assignCategory("FMS");
			
			Common_Function.generateRecordForPublishingReport(table_names_lst, year_xml, month_xml);
			
		}else if(source_system.equals("pm")) {
			
			table_names_lst = table_names_pm;
			site_names_lst = ReadQueryData.readSiteNames("pm_site_names");
			parent_logger = extent.createTest("Process Metrics Missing Report");
			parent_logger.assignCategory("Process Metrics");
			
			Common_Function.generateRecordForPublishingReport(table_names_lst, year_xml, month_xml);
			
		}else if(source_system.equals("bl")) {
			
			parent_logger = extent.createTest("Blast Logic Missing Report");
			parent_logger.assignCategory("Blast Logic");
			/*table_names_lst = table_names_pm;
			site_names_lst = ReadQueryData.readSiteNames("pm_site_names");
			
			Common_Function.generateRecordForPublishingReport(table_names_lst);*/
			
		}else if(source_system.equals("si")) {
			
			table_names_lst = table_names_si;
			site_names_lst = ReadQueryData.readSiteNames("si_site_names");
			parent_logger = extent.createTest("Safety Indicator Missing Report");
			parent_logger.assignCategory("Safety Indicator");
			
			Common_Function.generateRecordForPublishingReport(table_names_lst, year_xml, month_xml);
		}
				
		if(!(site_names_lst.isEmpty())) {
			
			site_names_lst.clear();
		}
	}
	
	@AfterTest
	public void publishReport() {
		
		extent.flush();
	}
}
