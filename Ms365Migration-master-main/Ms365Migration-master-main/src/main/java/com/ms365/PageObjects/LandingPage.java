package com.ms365.PageObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
//import org.testng.Assert;



public class LandingPage extends Helper  {
	
	private static final Logger logger = LogManager.getLogger(LandingPage.class);
	//capturing xpaths with using page factury 
	
	
		// validate the user loggedin as expected 
		@FindBy(xpath="//span[contains(text(),'AB Chekir')]") 
		private WebElement pageTtile;
		
		
		
		// lunching the browser
		
		
		public void launchBrowser() {
			//OpenBrowser();
			// PageFactory is used to find elements with @FindBy specified
			PageFactory.initElements(driver, this);
		}
		
		
		// // validate the user loggedin as expected 
		public void loginValidUser() throws InterruptedException {
			
			
			
			String OwnerName=pageTtile.getAttribute("title");
			
			System.out.println("PageTtile"+" " + OwnerName);
			//Assert.assertEquals(driver.findElement(null), "Yelp Camp");
			logger.info("TestCase #02 userValidation");
			
			
			
		}

}
