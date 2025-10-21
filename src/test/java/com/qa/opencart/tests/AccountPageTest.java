package com.qa.opencart.tests;

import static com.qa.opencart.constants.AppConstants.*;

import java.util.List;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Epic 100:Design pages for open cart Account page epic")
@Feature("F_53:Open cart -Account Page feature")
@Story("US101:Implement aacount page for appln")
public class AccountPageTest extends BaseTest {
	//BT _> BC
	WebDriver driver;
	@BeforeClass
	public void accPageSetup()
	{
		accPage=login.inputCredentialsLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@Description("checking open cart account page title...")
	@Severity(SeverityLevel.MINOR)
	@Owner("Nazima")
	@Test(description="checking account page title")
	public void getAccountPageTitle()
	{
		String title=accPage.getAccountPageTitle();
		Assert.assertEquals(title, ACCOUNT_PAGE_TITLE);
	}
	
	@Description("checking open cart account page url...")
	@Severity(SeverityLevel.NORMAL)
	@Owner("Nazima")
	@Test(description="checking account page url")
	public void getAccountPageUrl() throws Exception
	{
		String url=accPage.getAccountPageUrl();
		Assert.assertTrue(url.contains(ACCOUNT_PAGE_FRACTION_URL));
	}
	
	@Description("checking open cart account page title Header...")
	@Severity(SeverityLevel.CRITICAL)
	@Owner("Nazima")
	@Test(description="checking account page title Header")
	public void getAccountPageTitleHeaders()
	{
		List<String> values=accPage.getAccountPageHeadersList();
		Assert.assertEquals(values,headersList);
	}
	
	@Description("checking open cart account page search functionality...")
	@Severity(SeverityLevel.BLOCKER)
	@Owner("Nazima")
	@Test(description="checking account page search test",priority=Short.MAX_VALUE)
	public void doSearchTest()
	{
		searchResultPage=accPage.searchProducts("macbook");
		Assert.assertTrue(searchResultPage.getSearchPageTitle().contains(SEARCH_RESULT_FRACTION_TITLE));
		
	}

}
