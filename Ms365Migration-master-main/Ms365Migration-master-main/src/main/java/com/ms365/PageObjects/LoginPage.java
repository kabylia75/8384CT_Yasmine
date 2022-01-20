package com.ms365.PageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class LoginPage extends Helper {
	
	
			
	
	//capturing xpaths with using page factury 
	
	
	// facebook username
	@FindBy(xpath="//*[@id='email']") 
	private WebElement usernameField;
	
	// facebook password
	@FindBy(xpath="//*[@id='pass']") 
	private WebElement passwordField;
	
	// Click login
	@FindBy(xpath="//button[@type='submit']") 
	private WebElement loginBtn;
	
	// lunching the browser
	
	public void launchBrowser() {
		OpenBrowser();
		// PageFactory is used to find elements with @FindBy specified
		PageFactory.initElements(driver, this);
	}
	
	//to login to facebook
	public void loginValidUser(String username, String password) {
		
		usernameField.sendKeys(username);
		passwordField.sendKeys(password);
		loginBtn.click();
		System.out.println("zaktest LoginPage" );
		
	}
	
	
}
