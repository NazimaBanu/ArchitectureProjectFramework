package com.qa.opencart.tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import static com.qa.opencart.constants.AppConstants.*;
import com.qa.opencart.pages.LoginPage;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
@Epic("Epic 100:Design pages for open cart appln")
@Feature("F_50:Open cart -Login feature")
@Story("US101:Implement login page for appln")
public class LoginTest extends BaseTest
{
	public WebDriver driver;
	@Description("checking open cart login page title...")
	@Severity(SeverityLevel.MINOR)
	@Owner("Nazima")
	@Test(description="checking login title")
	public void loginPageTitleTest()
	{
		String title=login.getTitleForLoginPage();
		Assert.assertEquals(title,LOGIN_PAGE_TITLE);
	}
	
	@Description("checking open cart login page url...")
	@Severity(SeverityLevel.NORMAL)
	@Owner("Nazima")
	@Test(description="check login page url")
	public void loginPageUrlTest()
	{
		String url=login.getLoginPageUrl();
		Assert.assertNotNull(url);
		Assert.assertTrue(url.contains(LOGIN_PAGE_FRACTION_URL));
	}
	
	@Description("checking open cart forgot password link...")
	@Severity(SeverityLevel.CRITICAL)
	@Owner("Nazima")
	@Test(description="forgot password ")
	public void forgotPasswordLinkTest()
	{
		boolean result=login.checkPresenceOfForgotPwdLink();
		Assert.assertTrue(result);
		
	}
	

	@Description("checking open cart login feature...")
	@Severity(SeverityLevel.BLOCKER)
	@Owner("Nazima")
	@Test(priority=Short.MAX_VALUE, description="login with credentials")
	public void dologinTest()
	{
		//LoginPage login=new LoginPage(driver);It is not required & while u inherit loginpage object from baseTest, u should keep access modifier as protected
		//driver=setup(); //It is not correct to call, because it automatically loads befre every test
		accPage=login.inputCredentialsLogin(prop.getProperty("username"),prop.getProperty("password"));//"testAutomation@123.com", "Trial123"
		Assert.assertEquals(accPage.getAccountPageTitle(), ACCOUNT_PAGE_TITLE);
		
	}

	
	//priority is given by testNG & severity is given by Allure report
}
