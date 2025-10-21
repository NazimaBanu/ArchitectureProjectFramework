package com.qa.opencart.pages;

import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import static com.qa.opencart.constants.AppConstants.*;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class ProductInfoPage {
	WebDriver driver;
	ElementUtil util;
	By productHeader=By.tagName("h1");
	By imgThumbnails=By.cssSelector("ul.thumbnails >li");
		public ProductInfoPage(WebDriver driver)
		{
			this.driver=driver;
			util=new ElementUtil(driver);
		}
		
		@Step("Product Header")
		public String getHeader() {
			return util.waitForElementVisible(productHeader,MEDIUM_DEFAULT_TIMEOUT).getText();
		}
		
		@Step("Product Count")
		public int getProductCount()
		{
			
			int productCount=util.waitForAllElementsVisible(imgThumbnails, DEFAULT_TIMEOUT).size();
			return productCount;
		}
		
		@Step("Product Info Page Url")
		public String getProductInfoPageUrl() {
			return util.waitForUrlContains(PRODUCT_INFO_PAGE_FRACTION_URL, DEFAULT_TIMEOUT);
			
		}

}
