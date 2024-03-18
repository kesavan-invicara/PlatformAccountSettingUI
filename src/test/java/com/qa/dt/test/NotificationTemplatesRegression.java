package com.qa.dt.test;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.qa.dt.base.BaseClass;
import com.qa.dt.page.AccountSettingsPage;
import com.qa.dt.page.HomePage;
import com.qa.dt.page.LoginPage;
import com.qa.dt.page.PassportServicePage;
import com.qa.dt.page.ProjectSelection;
import com.qa.dt.util.ExtentManager;
import com.qa.dt.util.Xls_Reader;
@Listeners(com.qa.dt.util.ListenerClass.class)
public class NotificationTemplatesRegression extends LoginintoTheApplicationTest {
	AccountSettingsPage as;
	BaseClass bc;
	LoginPage lp;
	ProjectSelection ps;
	HomePage hp;
	PassportServicePage passportServicePg;
	
	/**
	 * @author Iyappan.Kasinathan
	 * @description The console application should show the options to the user to visit their account details page and to logout at all times
	 * @TC_ID PA-51,213
	 * @throws Exception
	 */
    @Test(priority = 1)
	public void verifyViewAndEditFunInNT() throws Exception {    	
    	lp = new LoginPage();
    	as= new AccountSettingsPage();
    	ps = new ProjectSelection();
    	hp = new HomePage();
    	passportServicePg = new PassportServicePage();
    	String projName = retrieveTestData("projectSelect","Project",2);
    	String appName = retrieveTestData("projectSelect", "Application", 2);
    	ExtentManager.test.log(Status.INFO, "TC_ID - PA-51,213");
    	ExtentManager.test.log(Status.INFO, "TC_Description -");
		ps.selectApplicationAndWorkspace(appName, projName);
    	lp.verifyPlatformAppAndProjectName(projName, appName);
    	hp.navigateToServices("passportservice");
		elementClick(passportServicePg.notificationTemplatesTab());
		passportServicePg.passportServiceActions("USER_GROUP_INVITE","View");
	}
    @AfterMethod
	public void tearDown() {
		driver.quit();
	}
   
    
    
}
