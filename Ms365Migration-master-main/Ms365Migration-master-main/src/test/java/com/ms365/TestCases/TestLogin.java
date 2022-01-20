package com.ms365.TestCases;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

//import org.testng.*;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.ms365.PageObjects.Helper;
import com.ms365.PageObjects.LoginPage;

@SuppressWarnings("deprecation")
public class TestLogin extends Helper{
	
	
	// creating log object for logging messages to console/main.log file
	private static final Logger logger = LogManager.getLogger(TestLogin .class);
			
	 // this will configure the extentReport before the test
	Helper report = new Helper();
	@BeforeTest
	public void rep() {
		report.extentReportSetup();
	}
	
	// login to facebook testcase
   @Test(description="Login to Facebook")
	public void loginValidUser() throws Exception {
		test = extent.createTest("TestCase_ID_01_Validate MS365 loginPage");
		LoginPage loginPage = new LoginPage();
		loginPage.launchBrowser();
		loginPage.loginValidUser(prop.getProperty("username"), prop.getProperty("password"));
		
		
	}
	

   
// this will configure the extentReport before the test
   ITestResult result;
	//@AfterMethod
	public void repconfig() throws IOException {
		report.getResult(result);
	
	}
	
	
}
