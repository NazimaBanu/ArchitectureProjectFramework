package com.qa.opencart.utils;

import org.openqa.selenium.By;

public class StringUtils {
	
	/*
	 * Random Email generation
	 */
	public static String randomEmailGenerate()
	{
		return "testautomation" +System.currentTimeMillis()+"@setup.com";
	}

}
