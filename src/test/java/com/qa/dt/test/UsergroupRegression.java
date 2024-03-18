package com.qa.dt.test;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.qa.dt.base.BaseClass;
import com.qa.dt.page.AccountSettingsPage;
import com.qa.dt.page.CommonReusablesPage;
import com.qa.dt.page.HomePage;
import com.qa.dt.page.LoginPage;
import com.qa.dt.page.PassportServicePage;
import com.qa.dt.page.ProjectSelection;
import com.qa.dt.util.ExtentManager;

@Listeners(com.qa.dt.util.ListenerClass.class)
public class UsergroupRegression extends LoginintoTheApplicationTest {
	AccountSettingsPage as;
	BaseClass bc;
	LoginPage lp;
	HomePage hp;
	ProjectSelection ps;
	CommonReusablesPage cr;
	PassportServicePage passportServicePg;
	String appName, projName;

	@BeforeClass
	public void precondition() throws Exception {
		lp = new LoginPage();
		as = new AccountSettingsPage();
		ps = new ProjectSelection();
		hp = new HomePage();
		cr = new CommonReusablesPage();
		passportServicePg = new PassportServicePage();
//		lp.loginTheApplication(loadProperties().getProperty("DbmAdminUsername"),loadProperties().getProperty("DbmAdminPassword"));
	}
	@Test(priority = -1)
	public void testData() throws Exception {
		projName = retrieveTestData("projectSelect", "Project", 9);
		appName = retrieveTestData("projectSelect", "Application", 9);		
		ps.selectApplicationAndWorkspace(appName, projName);
		hp.navigateToServices("passportservice");
		elementClick(passportServicePg.userGroupTab());
		String[] values = passportServicePg.getUserGroupTestdata();
		writeExcelData(values,"UserGroup");
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description Verify the validations of user group table, search/filtered functionality and view user group modal
	 * @TC_ID PA-40
	 * @throws Exception
	 */
	@Test(priority = 1)
	public void verifyUserGroupValidations() throws Exception {
		ExtentManager.test.log(Status.INFO, "TC_ID - PA-40");
		ExtentManager.test.log(Status.INFO, "TC_Description - Verify the validations of usergroup table, search/filtered functionality and view user group modal");
		projName = retrieveTestData("projectSelect","Project",2);
		appName = retrieveTestData("projectSelect", "Application", 2);
		String ugName = retrieveTestData("UserGroup", "Field Values", 2);
		String ugShortName = retrieveTestData("UserGroup", "Field Values", 3);
		String ugDescription = retrieveTestData("UserGroup", "Field Values", 4);
		String[] tableHeaders = { "Name", "Short Name", "Description", "Last Updated" };
		String[] rowsPerPageSelections = { "5", "10", "25" };
		String[] ugValues = { ugName, ugShortName, ugDescription };		
		ps.selectApplicationAndWorkspace(appName,projName);	
		hp.navigateToServices("passportservice");
		ps.verifyAppOnlySelectedUnderPS();
		ps.verifyAppProjectSelection(appName);
		elementDisplayed(passportServicePg.userGroupTab(),"Navigated to Passport services page successfully","Failed to navigate to Passport services page");
		assertTrue(isElementPresent(passportServicePg.WorkspacesTab(), 10),"Workspace tab is not displayed in passport services page");
		assertTrue(isElementPresent(passportServicePg.notificationTemplatesTab(), 10),"Notificationtemplates tab is not displayed in passport services page");
		passedStep("All the three tabs are displayed in passport services page successfully");	
		elementClick(passportServicePg.userGroupTab());
		cr.verifyTableHeaders(tableHeaders);
//		for(String value:tableHeaders) {
//		cr.sortingOrderOfTable("Ascending", value);
//		cr.sortingOrderOfTable("Descending", value);
//		}
//		for(String ugField:ugValues) {
//		cr.searchTheTable(ugField);
//		}
		passportServicePg.userGroupActions(ugName, "View");
		passportServicePg.viewUserGroupUIValidations(ugName, ugShortName, ugDescription);
		cr.closeModal();
		
	}

	@Test(priority = 2)
	public void validateUgFunctions() throws Exception {
		ExtentManager.test.log(Status.INFO, "TC_ID - PA-41,42,43,44");
		ExtentManager.test.log(Status.INFO, "TC_Description - Verify the user can create and delete ");
		projName = retrieveTestData("projectSelect","Project",9);
		appName = retrieveTestData("projectSelect", "Application", 9);
		String ugName = retrieveTestData("UserGroup", "Field Values", 2);
		ps.selectApplicationAndWorkspace(appName,projName);	
		hp.navigateToServices("passportservice");
		elementClick(passportServicePg.userGroupTab());
//		passportServicePg.createNewUserGroup("newTest");
//		passportServicePg.verifyWsOrUgCreated("newTest","usergroup");
		passportServicePg.userGroupActions(ugName, "Edit");
		passportServicePg.addAndRemoveUserFromUg(ugName);
		elementClick(passportServicePg.closeViewUserGrp());
//		passportServicePg.workspaceActions(ugName,"Delete");
	}
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

	

}
