package com.qa.opencart.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavaScriptUtil {
	WebDriver driver;
	JavascriptExecutor js;
	
	public JavaScriptUtil(WebDriver driver)
	{
		this.driver=driver;
		js=(JavascriptExecutor)this.driver;
	
		
	}
	public void flash(WebElement element)
	{
		String bgcolor=element.getCssValue("backgroundColor"); //blue
		for(int i=0;i<7;i++)
		{
			changeColor("rgb(0,200,0)",element);//green
			changeColor(bgcolor,element);
		}
	}
	private void changeColor(String color,WebElement element)
	{
		js.executeScript("arguments[0].style.backgroundColor='" +color +"'", element);
		try
		{
			Thread.sleep(20);
		}catch(Exception e)
		{
			
		}
		
	}
			

}
