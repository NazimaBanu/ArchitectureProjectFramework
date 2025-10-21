package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import static com.qa.opencart.constants.AppConstants.*;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.exceptions.CustomException;
import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.StringUtils;

import io.qameta.allure.Step;

public class RegistrationPage {
	WebDriver driver;
	ElementUtil util;
	By firstNameField=By.id("input-firstname");
	By lastNameField=By.id("input-lastname");
	By emailField=By.id("input-email");
	By telephoneField=By.id("input-telephone");
	By passwordField1=By.id("input-password");
	By confirmPasswordField=By.id("input-confirm");
	By subscribeYes=By.xpath("(//input[@name=\"newsletter\"])[1]");
	By subscribeNo=By.xpath("(//input[@name=\"newsletter\"])[2]");
	By btnContinue=By.xpath("//input[@value=\"Continue\"]");
	By headerSuccess=By.tagName("h1");
	By btnContinueOnRegistration=By.xpath("//a[text()=\"Continue\"]");
	By linkLogout=By.xpath("(//a[text()=\"Logout\"])[2]");
	By privacyPolicy=By.name("agree");
	By linkRegister=By.linkText("Register");
	public RegistrationPage(WebDriver driver)
	{
		this.driver=driver;
		util=new ElementUtil(driver);
	}
	
	@Step("Checking Registration page url")
	public String checkRegistrationPageUrl()
	{
		return util.waitForUrlContains(REGISTRATION_PAGE_FRACTION_URL,MEDIUM_DEFAULT_TIMEOUT);
	}
	
	
	@Step("Enter details in Registration Form")
	public boolean enterDetailsInRegistrationForm(String firstName, String lastName, String telephone, String password, String subscribeValue) throws Exception
	{
		util.waitForElementVisible(firstNameField, DEFAULT_TIMEOUT);
		util.doSendKeys(firstNameField, firstName);
		util.doSendKeys(lastNameField, lastName);
		util.doSendKeys(emailField, StringUtils.randomEmailGenerate());
		util.doSendKeys(telephoneField, telephone);
		util.doSendKeys(passwordField1, password);
		util.doSendKeys(confirmPasswordField, password);
		if(subscribeValue.equalsIgnoreCase("yes"))
		{
		util.isElementDisplayed(subscribeYes);
		util.doClick(subscribeYes);
		}
		else if(subscribeValue.equalsIgnoreCase("no"))
		{
			//util.waitForAllElementsVisible(subscribeNo, DEFAULT_TIMEOUT);
			util.isElementDisplayed(subscribeNo );
			util.doClick(subscribeNo);
		}
		util.doClick(privacyPolicy);
		util.doClick(btnContinue);
		util.waitForElementVisible(headerSuccess, MEDIUM_DEFAULT_TIMEOUT);
		if(util.doElementGetText(headerSuccess).contains(AppConstants.REGISTER_SUCCESS_FRACTION_TEXT))
		{
			System.out.println("Registration is successful");
			/*
			 * JavascriptExecutor js=(JavascriptExecutor)driver;
			 * js.executeScript("window.scrollBy(0, 1990)");
			 * js.executeScript("window.scrollTo(0.document.body.scrollHeight);");
			 */
			//regisPageLogout();
			util.waitForElementVisible(linkLogout, DEFAULT_TIMEOUT);
			util.doClick(linkLogout);
			util.doClick(linkRegister); //We have to provide register link here, it won't be taken from @BeforeClass for data driven tests. @BeforeClass will be executed only once for each & every test
			return true;
		}
		return false;
		/*
		 * if(util.isElementDisplayed(headerSuccess)) {
		 */

			
//return new AccountPage(driver);	//when u click on 2nd continue, then it is navigating to cart page, so it is not able to run the next data in the dataprovider to the test.
		/*
		 * } else { System.out.println("Registration is not successful"); throw new
		 * CustomException("Registration is not successful"); }
		 */	
		
	}
	
	@Step("Verify registration logout")
	public void regisPageLogout() throws Exception
	{
		util.doClick(linkLogout);
		
	}

}
