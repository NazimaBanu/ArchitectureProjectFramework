package com.qa.opencart.pages;

import java.util.Properties;
import static com.qa.opencart.constants.AppConstants.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class SearchResultsPage {
	WebDriver driver;
	ElementUtil util;
	By productsList=By.cssSelector("div.product-thumb");

	/*
	 * By clickProductSelected=By.linkText("MacBook Pro"); By
	 * clickProduct=By.cssSelector("div.product-thumb img");
	 */

	public SearchResultsPage(WebDriver driver)
	{
		this.driver=driver;
		util=new ElementUtil(driver);
	}
	
	@Step("Getting search page title")
	public String getSearchPageTitle() {
		return util.waitForTitleContains(SEARCH_RESULT_FRACTION_TITLE, MEDIUM_DEFAULT_TIMEOUT);
	}
	@Step("Getting count of the product search")
	public int getProductCount() {
		//int count=util.getCount(productsList); //instead of this one, used below line
		int count=util.waitForAllElementsVisible(productsList, DEFAULT_TIMEOUT).size();
		System.out.println("Product Count for the search" +count);
		return count;
	}
	
	@Step("Selecting product from the product search")
	public ProductInfoPage selectProductToClick(String productName)
	{
		//div.product-thumb>div img
		 util.waitForElementVisible(productsList, DEFAULT_TIMEOUT);
		 util.doClickWebElement(driver.findElement(By.linkText(productName))); //Here we pass the linktext directly instead of the top area of the page, becoz it is dynamic
		 //we get supplied from the test class with the different data. One day we click on macbook pro, other day we want to click on macbook air
		 return new ProductInfoPage(driver);	
	}

	
}
