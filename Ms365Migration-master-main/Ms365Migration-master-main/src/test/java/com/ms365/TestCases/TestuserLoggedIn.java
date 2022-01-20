package com.ms365.TestCases;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.ms365.PageObjects.Helper;
import com.ms365.PageObjects.LandingPage;
import com.ms365.PageObjects.LoginPage;

//@SuppressWarnings("deprecation")
public class TestuserLoggedIn extends Helper {

	// creating log object for logging messages to console/main.log file
		private static final Logger logger = LogManager.getLogger(TestLogin .class);
				
				
				
	// this will configure the extentReport before the test
	   @BeforeTest
		public void extentReportSetup() {
		loadDataPropFile();
		// configuration for extent report
		htmlReporter = new ExtentHtmlReporter(htmlReportPath);
		htmlReporter.loadXMLConfig(htmlReporterConfigPath);
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		// general information related to application
		extent.setSystemInfo("Application", prop.getProperty("application"));
		extent.setSystemInfo("Environment", prop.getProperty("environment"));
		extent.setSystemInfo("OS", prop.getProperty("os"));
		extent.setSystemInfo("User", prop.getProperty("username"));
		extent.setSystemInfo("Browser", prop.getProperty("browser"));				
		}	
	   
	   
		// validate the user loggedin as expected to his facebook account
	   @Test(description="Login user validation")
		public void userLoggedInasExpected() throws Exception {
			test = extent.createTest("TestCase_ID_02_Validate MS365 usersLoggedIn");
			 LandingPage   landingPage  = new  LandingPage ();
			 landingPage.launchBrowser();
			 landingPage.loginValidUser();
			
			
			
		}
	   
	// getting the results to the reports 
		@AfterMethod
		public void getResult(ITestResult result) throws IOException {

			if (result.getStatus() == ITestResult.FAILURE) {
				// add testcase name and exception to report
				test.fail(MarkupHelper.createLabel(result.getName()+" - "+result.getThrowable().getMessage(), ExtentColor.RED));
				// take and store screenshot
				String screenshotPath = getScreenshot(driver, result.getName());
				// add screenshot to report
				test.fail("Test Failed Screenshot below ", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
			} 
			else if (result.getStatus() == ITestResult.SKIP) {
				test.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " - Test Case SKIPPED", ExtentColor.ORANGE)); 
			} 
			else if (result.getStatus() == ITestResult.SUCCESS) {
				test.log(Status.PASS, MarkupHelper.createLabel(result.getName()+" Test Case PASSED", ExtentColor.GREEN));
			}
			
			extent.flush();
			//driver.quit();
			logger.info("Test result sent to extent report");
		}
	   
	   
}
