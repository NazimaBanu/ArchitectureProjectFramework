package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.qa.opencart.constants.AppConstants.*;

import java.util.ArrayList;
import java.util.List;

import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class AccountPage {
WebDriver driver;
ElementUtil util;
By accPageHeaders=By.tagName("h2");
By searchField=By.name("search");
By searchIcon=By.cssSelector("div#search button");

	public AccountPage(WebDriver driver) {
		this.driver=driver;
		util=new ElementUtil(driver);
	}
	
	@Step("getting account page title")
	public String getAccountPageTitle() {
		
		return util.waitForTitleIs(ACCOUNT_PAGE_TITLE, MEDIUM_DEFAULT_TIMEOUT);
	}
	
	@Step("getting account page Url")
	public String getAccountPageUrl() {
		return util.waitForUrlContains(ACCOUNT_PAGE_FRACTION_URL, MEDIUM_DEFAULT_TIMEOUT);
		
	}
	
	@Step("Fetching account page header list")
	public ArrayList<String> getAccountPageHeadersList()
	{
		List<WebElement> listOfHeaders=util.getElements(accPageHeaders);
		ArrayList<String> listOfHeadersValueList=new ArrayList<String>();
		for(WebElement e:listOfHeaders)
		{
			String text=e.getText();
			listOfHeadersValueList.add(text);
		}
		return listOfHeadersValueList;
		
	}
	
	@Step("Validate  product Search in account page")
	public SearchResultsPage searchProducts(String searchKey) 
	{
		util.doSendKeys(searchField, searchKey);
		util.doClick(searchIcon);
		return new SearchResultsPage(driver);
//		int productCount=util.getCount(productsList);
//		return productCount;
//			
		
	}

}
