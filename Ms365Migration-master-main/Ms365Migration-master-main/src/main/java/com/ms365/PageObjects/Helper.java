package com.ms365.PageObjects;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.testng.ITestResult;
import org.testng.*;
//import org.testng.annotations.AfterMethod;
//import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import io.github.bonigarcia.wdm.WebDriverManager;

@SuppressWarnings("deprecation")
public class Helper {

	public static WebDriver driver;
	public static Properties prop = new Properties();
	
	// excel sheet classes
	public static XSSFSheet sheet;
	public static XSSFWorkbook workbook;
	public static XSSFCell cell;
	public static XSSFRow row;
	
	// extent classes
	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports extent;
	public static ExtentTest test;
	// path for where report is to be saved
	String dateName = new SimpleDateFormat("yyyyMMddhhmm").format(new Date());
	protected String htmlReportPath = System.getProperty("user.dir") + "/test-output/extent-report" + dateName + ".html";
	// path for html report config
	protected String htmlReporterConfigPath = System.getProperty("user.dir") + "/src/main/resources/htmlreporter-config.xml";
	
	
	public static void loadDataPropFile() {
		try {
			FileInputStream file = new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/data.properties");
			prop.load(file);
		} catch (IOException e) {
			System.out.println("Failed to load data properties file " + e.getMessage());
		}
		
	}
	
	public static void OpenBrowser() {
		loadDataPropFile();
		// configuring drivers
		if (prop.getProperty("browser").equalsIgnoreCase("chrome")){
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			//options.setHeadless(false);
			options.setAcceptInsecureCerts(true);
			options.addArguments("disable-extensions", "disable-popup-blocking");
			// to hide "Chrome is being controlled by automated" infobar message
			options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
			options.setExperimentalOption("useAutomationExtension", false);
			driver = new ChromeDriver(options);
		} 
		else if (prop.getProperty("browser").equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			FirefoxOptions ffOptions = new FirefoxOptions();
			ffOptions.setHeadless(false);
			ffOptions.setAcceptInsecureCerts(true);
			driver = new FirefoxDriver(ffOptions);
		} 
		else if(prop.getProperty("browser").equalsIgnoreCase("ie")) {
			WebDriverManager.iedriver().setup();
			InternetExplorerOptions ieOptions = new InternetExplorerOptions();
			ieOptions.ignoreZoomSettings();
			ieOptions.introduceFlakinessByIgnoringSecurityDomains();
			driver = new InternetExplorerDriver(ieOptions);
		} 
		else System.out.println("Please enter a valid browser");
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
		driver.get(prop.getProperty("url"));
		
	}
	
	// to take screenshots
	public static String getScreenshot(WebDriver driver, String screenshotName) {
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File src = ts.getScreenshotAs(OutputType.FILE);
	  
		// store screenshot in local project directory
		String path = System.getProperty("user.dir") + "/Screenshots/" + screenshotName + dateName + ".png";
		File destination = new File(path);
		try {
			FileUtils.copyFile(src, destination);
		} catch(IOException e) {
			System.out.println("Screenshot capture failed " + e.getMessage());
		}
		
		return path;
	}
	
	
	// extent report---------------------------------------
	// this will configure the extentReport before the test
	  // @BeforeTest
		public  void extentReportSetup() {
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
	   
	  
	   
	
		// getting the results to the reports 
		//@AfterMethod
		public  void getResult(ITestResult result) throws IOException {

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
			
		}
	
	
	
	
	
	
	
}