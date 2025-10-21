package com.qa.opencart.factory;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

import com.qa.opencart.utils.ElementUtil;

public class OptionsManager
{
	
	Properties prop;
	public OptionsManager(Properties prop)
	{
		
		this.prop=prop;
	}
	
	public ChromeOptions runIndifferentModeChrome()
	{
		ChromeOptions co=new ChromeOptions();
		
		if(Boolean.parseBoolean(prop.getProperty("headless"))) //toggle feature on or off
		{
		System.out.println("----Running in headless--------");
		co.addArguments("--headless");	
		co.addArguments("--window-size=1920,1080"); //Headless browser’s small viewport hides or shrinks elements, so oftern occurs Element not interactable exception, it won't visible in page. so specify this setting
		//options.addArguments("--disable-gpu");
		//options.addArguments("--no-sandbox");
		}
		if(Boolean.parseBoolean(prop.getProperty("incognito")))
		{
		System.out.println("----Running in incognito--------");	
		co.addArguments("--incognito");
		}
		return co;
		
	}
	
	public FirefoxOptions runInDifferentModeFirefox()
	{
		FirefoxOptions fo=new FirefoxOptions();
		if(Boolean.parseBoolean(prop.getProperty("headless")))
		{
			System.out.println("----Running in headless--------");
			fo.addArguments("--headless");
			fo.addArguments("--window-size=1920,1080");
		}
		if(Boolean.parseBoolean(prop.getProperty("incognito")))
		{
			System.out.println("----Running in incognito--------");
			fo.addArguments("--incognito");
		}
		return fo;
	}
	public EdgeOptions runInDifferentModeEdge()
	{
		EdgeOptions eo=new EdgeOptions();
		if(Boolean.parseBoolean(prop.getProperty("headless")))
		{
			System.out.println("----Running in headless--------");
			eo.addArguments("--headless");
			eo.addArguments("--window-size=1920,1080");
		}
		if(Boolean.parseBoolean(prop.getProperty("incognito")))
		{
			System.out.println("----Running in incognito--------");
			eo.addArguments("inprivate");
		}
		return eo;
	}

}

/*
 * Cause                                             Explanation 
 * No wait before interaction                       In headless mode, rendering delays cause Selenium to act before the element is ready 
 * Element off-screen / small window                Headless browser’s small viewport hides or shrinks elements 
 * Stale page or wrong element                      Data-driven tests iterate fast; DOM references can become invalid 
 * Modal or overlay                                 Pop-ups or transitions block the element during iteration
 */

/*
 * When you switch to headless mode, Selenium behaves a bit differently from
 * “headed” mode because:
 * 
 * Headless browsers render differently — some elements that appear visible on
 * screen might not actually be “visible” in DOM terms.
 * 
 * Timing issues — pages sometimes take slightly longer to render or execute JS
 * in headless mode.
 * 
 * Viewport issues — default headless browser window sizes are smaller (often
 * 800×600), so elements can be “off-screen.”
 * 
 * Focus problems — without a UI, clicks or typing may fail unless the element
 * is truly interactable (displayed, enabled, visible).
 * 
 * When combined with DataProvider, this gets amplified:
 * 
 * The browser might be reused or closed/opened rapidly.
 * 
 * Test flow may not wait long enough before interacting again.
 */