package com.qa.opencart.tests;

import org.testng.Assert;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Epic 600:Design pages for product  and its information")
@Feature("F_50:Product Info-List feature")
@Story("US101:Implement product info page for appln")
public class ProductInfoTest extends BaseTest{
	
	@BeforeClass
	public void productSetup()
	{
		accPage=login.inputCredentialsLogin(prop.getProperty("username"), prop.getProperty("password"));	
	}
	
	@Description("checking page header for the product searched...")
	@Severity(SeverityLevel.CRITICAL)
	@Owner("Nazima")
	@Test(description="checking login title")
	
	public void verifyPageHeader()
	{
		searchResultPage=accPage.searchProducts("macbook");
		productInfoPage=searchResultPage.selectProductToClick("MacBook Pro");
		Assert.assertEquals(productInfoPage.getHeader(),"MacBook Pro");
	}
	
	@DataProvider
	public Object[][] getProductImagesData()
	{
		return new Object[][] {
			{"macbook","MacBook Pro",4},
			{"macbook","MacBook Air",4},
			{"imac","iMac",3},
			{"samsung","Samsung SyncMaster 941BW",1},
			{"samsung","Samsung Galaxy Tab 10.1",7}
		};
	}
	@Description("checking page count for the products displayed...")
	@Severity(SeverityLevel.CRITICAL)
	@Owner("Nazima")
	@Test(dataProvider="getProductImagesData",description="checking count of the products displayed")
	public void verifyProductPageCount(String searchKey,String selectProduct, int countOfProduct)
	{
		searchResultPage=accPage.searchProducts(searchKey);
		productInfoPage=searchResultPage.selectProductToClick(selectProduct);
		int productCount=productInfoPage.getProductCount();
		Assert.assertEquals(productCount,countOfProduct);
	}
}
