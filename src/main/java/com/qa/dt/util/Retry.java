package com.qa.dt.util;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer {
	
	int failedCount=0;
	int limit=2;

	public boolean retry(ITestResult result) {
		if (failedCount<limit) {
			failedCount++;
			return true;
		}
		return false;
	}
	

}
