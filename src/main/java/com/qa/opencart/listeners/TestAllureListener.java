package com.qa.opencart.listeners;

import com.qa.opencart.factory.DriverFactory;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import io.qameta.allure.Attachment;

public class TestAllureListener implements ITestListener 
{
	/*
	 * public static String getTestMethodName(ITestResult iTestResult) { return
	 * iTestResult.getMethod().getConstructorOrMethod().getName();
	 * 
	 * }
	 */
	  //Test attachments for Allure
	 
	  @Attachment(value="Page screenshot", type="image/png") 
	  public byte[] saveScreenshotPNG(WebDriver driver) 
	  { 
	   return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES); 
	  }
	  
	
	 // Called when test starts
    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("[STARTING TEST] " + result.getMethod().getMethodName());
    }

    // Called when test passes
    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("[PASSED TEST] " + result.getMethod().getMethodName());
    }

    // Called when test fails
    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("[FAILED TEST] " + result.getMethod().getMethodName());
        Object testClass = result.getInstance();
        //WebDriver driver = (BaseTest) testClass.getDriver();
        
        DriverFactory df=new DriverFactory();
        if(df.getDriver() instanceof WebDriver)
        {
        	System.out.println("Screenshot captured for test case" +result.getMethod().getMethodName());
        	saveScreenshotPNG(df.getDriver());
        }

        // Attach screenshot and error message to Allure
		/*
		 * if (driver != null) { saveScreenshot(driver); }
		 */
        saveTextLog(result.getThrowable() == null ? "Test failed" : result.getThrowable().getMessage());
    }

    // Called when test is skipped
    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("[SKIPPED TEST] " + result.getMethod().getMethodName());
        saveTextLog("Test skipped: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
       System.out.println("Test failed but it is in defined success ratio" +result.getMethod().getMethodName());
    }

    @Override
    public void onStart(ITestContext context) {
        System.out.println("========== Starting Test Suite: " + context.getName() + " ==========");
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("========== Finished Test Suite: " + context.getName() + " ==========");
    }

    // -------- Allure Attachments --------

    @Attachment(value = "Page Screenshot", type = "image/png")
    public byte[] saveScreenshot(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "Test Log", type = "text/plain")
    public static String saveTextLog(String message) {
        return message;
    }

    @Attachment(value = "{0}", type = "text/html") //instead of html page, replaced with {0}
    public static String attachHtml(String html) {
        return html;
    }
}
