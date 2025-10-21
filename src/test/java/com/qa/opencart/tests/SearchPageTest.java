package com.qa.opencart.tests;

import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static com.qa.opencart.constants.AppConstants.*;
import com.qa.opencart.base.BaseTest;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Epic 300:Design Search functionality Pages")
@Feature("F_60:Search functionality feature")
@Story("US701:Implement search page for appln")
public class SearchPageTest extends BaseTest{

	
	@BeforeClass
	public void searchResultPageSetup()
	{
		accPage=login.inputCredentialsLogin(prop.getProperty("username"), prop.getProperty("password"));
		
	}
	
	@Description("validate the search functionality of product searched...")
	@Severity(SeverityLevel.CRITICAL)
	@Owner("Nazima")
	@Test(description="checking product list based on the search")
	public void searchProductCount()
	{
		searchResultPage=accPage.searchProducts("macbook");
		int ProductCount=searchResultPage.getProductCount();
		Assert.assertEquals(ProductCount, 3);
	}
	
	
	@Description("validate selecting a product and verify url...")
	@Severity(SeverityLevel.BLOCKER)
	@Owner("Nazima")
	@Test(description="selecting product from list based on the search",priority=Short.MAX_VALUE)
	public void clickOnProductSelected()
	{
		searchResultPage=accPage.searchProducts("macbook");
		productInfoPage=searchResultPage.selectProductToClick("MacBook Pro");
		String text=productInfoPage.getProductInfoPageUrl();
		Assert.assertTrue(text.contains(PRODUCT_INFO_PAGE_FRACTION_URL));
		
	}
}
