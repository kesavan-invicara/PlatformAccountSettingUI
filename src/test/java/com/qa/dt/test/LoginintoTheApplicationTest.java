package com.qa.dt.test;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import com.qa.dt.base.BaseClass;
import com.qa.dt.page.LoginPage;
import com.qa.dt.util.ExtentManager;
import com.qa.dt.util.Xls_Reader;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;



@Listeners(com.qa.dt.util.ListenerClass.class)
public class LoginintoTheApplicationTest extends BaseClass{
	static Xls_Reader xl = new Xls_Reader();
	static LoginPage lp;
	static Logger logger;
	
	
	public void browserLaunch() throws IOException, Exception {
		browser_LaunchIgnoreCase(loadProperties().getProperty("Browser"));
		Properties loadProperties = loadProperties();
		System.out.println(loadProperties.getProperty("RfUrl"));
		launchURL(loadProperties.getProperty("RfUrl"));
	}
	@BeforeMethod
	public void loginTheApplication() throws Throwable {
		browserLaunch();
		lp= new LoginPage();
		lp.enterUserName(loadProperties().getProperty("Rfusername"));
		Thread.sleep(10000);
		lp.enterPassword(loadProperties().getProperty("RfPassword"));
		// ExtentManager.test.log(Status.PASS, "Logged in the Admin application successfully by "+loadProperties().getProperty("Rfusername"));		
	}
	
	 @AfterMethod
	public void tearDown() {
		 lp.logout();
		driverQuit();
	}
	

}
