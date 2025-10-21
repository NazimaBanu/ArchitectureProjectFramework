package com.qa.opencart.tests;
import static com.qa.opencart.constants.AppConstants.*;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.StringUtils;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Epic 20:Design registration functionality Pages")
@Feature("F_10:Registration functionality feature")
@Story("US01:Registration form details of the application")
public class RegistrationPageTest extends BaseTest {
	@BeforeClass
	public void registrationPageSetup()
	{
		registrationPage=login.clickAndNavigateToRegistrationPage();
	}
	
	
	@Description("validate the navigation to registration page...")
	@Severity(SeverityLevel.CRITICAL)
	@Owner("Nazima")
	@Test(description="checking registration page navigation url")
	public void navigateToRegistrationPage()
	{
	
		String url=registrationPage.checkRegistrationPageUrl();
		Assert.assertTrue(url.contains(REGISTRATION_PAGE_FRACTION_URL));
	}
	
	
	@DataProvider
	public Object[][] getData()
	{
		return new Object[][] {
			{"automation","automation","91345678956","automation34","yes"},
		
			{"test","test","976309263456","test1234","no"}
		};
	}
	
	
	@Description("Fill the registration form...")
	@Severity(SeverityLevel.MINOR)
	@Owner("Nazima")
	@Test(dataProvider="getData",priority=Short.MAX_VALUE,description="Enter details for signup in the registration form")
	public void fillTheRegistrationForm(String firstName, String lastName,String telephoneNumber,String password,String subscribeValue) throws Exception
	{
		Assert.assertTrue(registrationPage.enterDetailsInRegistrationForm(firstName,lastName, telephoneNumber, password, subscribeValue));
		
	}
	
	
	
}
