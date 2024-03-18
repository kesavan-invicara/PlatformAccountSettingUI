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
public class WorkspaceRegression extends LoginintoTheApplicationTest {
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

	/**
	 * @author Iyappan.Kasinathan
	 * @description Verify the validations of workspaces, search/filtered functionality and view user group modal
	 * @TC_ID PA-33
	 * @throws Exception
	 */
//	@Test(priority = 1)
	public void verifyWorkspaceTableUiValidations() throws Exception {
		ExtentManager.test.log(Status.INFO, "TC_ID - PA-33");
		ExtentManager.test.log(Status.INFO, "TC_Description - Verify the validations of usergroup table, search/filtered functionality and view user group modal");
		projName = retrieveTestData("projectSelect","Project",9);
		appName = retrieveTestData("projectSelect", "Application", 9);
		String workspaceName ="cTest";
		String workspaceShortName = "cTest";
		String worspaceDescription = "cTest";
		String[] tableHeaders = { "Name", "Short Name", "Description", "Last Updated" };
		String[] rowsPerPageSelections = { "5", "10", "25" };
		String[] ugValues = { workspaceName, workspaceShortName, worspaceDescription };		
		ps.selectApplicationAndWorkspace(appName,projName);	
		hp.navigateToServices("passportservice");
		ps.verifyAppOnlySelectedUnderPS();
		ps.verifyAppProjectSelection(appName);		
		assertTrue(isElementPresent(passportServicePg.WorkspacesTab(), 10),"Workspace tab is not displayed in passport services page");		
		passedStep("All the three tabs are displayed in passport services page successfully");	
		elementClick(passportServicePg.WorkspacesTab());
		cr.verifyTableHeaders(tableHeaders);
//		for(String value:tableHeaders) {
//		cr.sortingOrderOfTable("Ascending", value);
//		cr.sortingOrderOfTable("Descending", value);
//		}
//		for(String ugField:ugValues) {
//		cr.searchTheTable(ugField);
//		}
		passportServicePg.passportServiceActions(workspaceName, "Edit");
		passportServicePg.editWorkspaceValidations(workspaceName, workspaceShortName, worspaceDescription);
		cr.closeModal();		
	}
	
//	@Test(priority = 2)
	public void verifyWorkspaceUpdatedOrNot() throws Exception {
		ExtentManager.test.log(Status.INFO, "TC_ID - PA-290");
		ExtentManager.test.log(Status.INFO, "TC_Description - Verify the reflected workspace name updated immediately");
		projName = retrieveTestData("projectSelect","Project",2);
		appName = retrieveTestData("projectSelect", "Application", 2);
		ps.selectApplicationAndWorkspace(appName,projName);	
		hp.navigateToServices("passportservice");
		elementClick(passportServicePg.WorkspacesTab());
		passportServicePg.passportServiceActions(projName, "Edit");
		passportServicePg.editNameAndDescription(projName+" updated");
		passportServicePg.verifyWorkspacesNameUpdated(projName);
		passportServicePg.passportServiceActions(projName+" updated", "Edit");
		passportServicePg.editNameAndDescription(projName);
	}
//	@Test(priority = 3)
	public void validateNewWorkspaceCreated() throws Exception {
		ExtentManager.test.log(Status.INFO, "TC_ID - PA-38,39");
		ExtentManager.test.log(Status.INFO, "TC_Description - Verify the reflected workspace name updated immediately");
		projName = retrieveTestData("projectSelect","Project",4);
		appName = retrieveTestData("projectSelect", "Application", 2);
		ps.selectApplicationAndWorkspace(appName,projName);
		hp.navigateToServices("passportservice");
		elementClick(passportServicePg.userGroupTab());
		passportServicePg.createNewUserGroup("test");
		passportServicePg.verifyWsOrUgCreated("test","workspace");
		passportServicePg.passportServiceActions("test","Delete");
	}
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

	

}
