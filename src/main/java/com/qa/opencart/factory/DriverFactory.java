package com.qa.opencart.factory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.qa.opencart.exceptions.CustomException;


public class DriverFactory {
	WebDriver driver; //It should not present with public or private in the webdriver driver
	Properties prop;
	OptionsManager options;
	FileInputStream fp;
	private static final Logger log=LogManager.getLogger(DriverFactory.class);
	public static String highlight;//keep this variable as static so that it is called directly from the class name without creating an object
	public WebDriver initDriver(Properties prop) throws Exception {
		
		String browserName=prop.getProperty("browser");
		options=new OptionsManager(prop);
		highlight=prop.getProperty("highlight");
		
		switch (browserName.toLowerCase().trim()) {
		case "chrome":
			driver = new ChromeDriver(options.runIndifferentModeChrome());
			break;
		case "firefox":
			driver = new FirefoxDriver(options.runInDifferentModeFirefox());
			break;
		case "edge":
			driver = new EdgeDriver(options.runInDifferentModeEdge());
			break;
		default:
			log.error("-------Invalid Browser--------------------");
			throw new CustomException("-------------Invalid Browser----------");
		}
		//fp=new FileInputStream("./src/test/resources/config/qa.config.properties");
		driver.get(prop.getProperty("url"));
		driver.manage().window().fullscreen();
		driver.manage().deleteAllCookies(); //To delete cookies before start any flow of the appln
		return driver;

	}
	
	public WebDriver getDriver()
	{
		return driver;
	}
	
	public Properties initProp() throws Exception
	{
		prop=new Properties();
		
		String envName=System.getProperty("env");
		if(envName==null)
		{
			log.warn("The env is null, hence running the tests on QA env by default");
			fp=new FileInputStream("./src/test/resources/config/prod.config.properties");
			
		}
		else
		{
			switch(envName.trim().toLowerCase())
			{
			case "qa":
				fp=new FileInputStream("./src/test/resources/config/qa.config.properties");
				break;
			case "uat":
				fp=new FileInputStream("./src/test/resources/config/uat.config.properties");
				break;
			case "dev":
				fp=new FileInputStream("./src/test/resources/config/dev.config.properties");
				break;
			case "prod":
				fp=new FileInputStream("./src/test/resources/config/prod.config.properties");
				break;
				default:
					log.error("----Invalid env supplied--------");
					throw new CustomException("--------Invalid environment name------" +envName);
					
			}
		}
		prop.load(fp);
		return prop;
	}

}
