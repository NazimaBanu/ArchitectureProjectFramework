package com.qa.opencart.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryClass implements IRetryAnalyzer
{
	private int count=0;
	private static int maxTry=3;

	@Override
	public boolean retry(ITestResult result) {
		if(!result.isSuccess()) //check if test not succeed
		{
			if(count < maxTry)//check if maxtry count is reached
			{
				count++; //Increase the maxTry count by 1
				result.setStatus(result.FAILURE); //Mark test as failure
				return true; //Tells TestNG to re-run the test
			}
			else
			{
				result.setStatus(result.FAILURE);//If maxCount is reached, test marked as failed
				
			}
		}
			else
			{
				result.setStatus(result.SUCCESS);//Iftest passes, testNG marked as passed
			}
		
		return false;
	}

}
