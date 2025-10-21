package com.qa.opencart.base;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import org.apache.commons.io.FileUtils; 

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.AccountPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductInfoPage;
import com.qa.opencart.pages.RegistrationPage;
import com.qa.opencart.pages.SearchResultsPage;

public class BaseTest {
	WebDriver driver;
	protected Properties prop;
	DriverFactory df;
	protected LoginPage login;
	protected AccountPage accPage;
	protected SearchResultsPage searchResultPage;
	protected ProductInfoPage productInfoPage;
	protected RegistrationPage registrationPage;
	private static final Logger log=LogManager.getLogger(DriverFactory.class);
	@BeforeTest
	public void setup() throws Exception 
	{
		df=new DriverFactory();
		prop=df.initProp();
		driver=df.initDriver(prop);
		login=new LoginPage(driver);
		//accPage=new AccountPage(driver);//Don't create object of accpage, if u run loginpage test or pdtTest, unnecessarily we create object of accPage, with addition to 
		//setup Driver factory, initialize loginpage driver and so on & then accountpage driver unnecessarily with addition to loginpage driver.. so remove this line
	}
	
	@BeforeMethod
	public void startTest()
	{
		log.info("Start running testcase");
	}
	
	
	  @AfterTest 
	  public void tearDown() { 
		  driver.quit();
		  log.info("-----Quit Browser------");
		  }
	 
	  @AfterMethod
	  public void attachScreenshot(ITestResult result)
	  {
//		  DriverFactory df=new DriverFactory();
		  if(!result.isSuccess())
		  {
			 
				/* Object testClass = result.getInstance(); */
//		        if(df.getDriver() instanceof WebDriver)
//		        {
		        	String testName = result.getName();
		        	 log.info("-----Screenshot Taken--------");
		        	String screenshot=saveScreenshotPNG(driver,testName);//df.getDriver()
		       // }

		  }
	  }



	private String saveScreenshotPNG(WebDriver driver2,String testName) {
		 String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	        String screenshotPath = System.getProperty("user.dir") +"/screenshots/"+ testName+ "_" + timestamp + ".png";

	        try {
	        
	            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	            File destFile = new File(screenshotPath);
	            FileUtils.copyFile(srcFile, destFile);//stuck here for copyFile error becoz of wrong import
	        } catch (IOException e) {
	            log.error("❌ Screenshot capture failed: " + e.getMessage());
	        }
	        return screenshotPath;
	}
	  
	  
}
