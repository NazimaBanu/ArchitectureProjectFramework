package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.qa.opencart.constants.AppConstants.*;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {
	private WebDriver driver;
	ElementUtil util;
	By inputUsername=By.id("input-email");
	By inputPassword=By.id("input-password");
	By loginBtn=By.xpath("//input[@value=\"Login\"]");
	By forgotPwd=By.xpath("(//a[text()=\"Forgotten Password\"])[1]");
	By linkRegister=By.linkText("Register");
	public LoginPage(WebDriver driver)
	{
		this.driver=driver;
		util=new ElementUtil(driver);
	}
	
		@Step("getting login page title")
	  public String getTitleForLoginPage() 
	  { 
		 
		 return util.waitForTitleIs(LOGIN_PAGE_TITLE, DEFAULT_TIMEOUT);
	  }
	 
		@Step("getting login page url")
	  public String getLoginPageUrl() 
	  { 
		  return util.waitForUrlContains(LOGIN_PAGE_FRACTION_URL,DEFAULT_TIMEOUT);
		  
	  }
		
		@Step("login with valid username: {0} and password : {1}")
	  public AccountPage inputCredentialsLogin(String username, String password)
	  {//doEnterText
		  //If username is visible, then by the time password also visible, having height & width>0
		  util.waitForElementVisible(inputUsername, MEDIUM_DEFAULT_TIMEOUT).sendKeys(username);
		 // util.doSendKeys(inputUsername,username);  
		  util.doSendKeys(inputPassword, password); //It checks for nullchar while entering input. If they type null in the uservalue,we pass to the method, then it throws  Exception
		  util.doClick(loginBtn);
		  return new AccountPage(driver);
		  //return util.getPageTitle(); instead of this utility, we used waitforTitleIs.
	  }
	  
	  public boolean checkPresenceOfForgotPwdLink() {
		  return util.isElementDisplayed(forgotPwd);
	  }
	 
	  @Step("navigating to registration page")
	  public RegistrationPage clickAndNavigateToRegistrationPage()
	  {
		  util.waitForElementVisible(linkRegister, MEDIUM_DEFAULT_TIMEOUT);
		  util.doClick(linkRegister);
		  return new RegistrationPage(driver);
	  }
	  
}
