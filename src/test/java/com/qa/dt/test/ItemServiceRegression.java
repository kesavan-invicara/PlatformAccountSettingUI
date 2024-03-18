package com.qa.dt.test;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.qa.dt.base.BaseClass;
import com.qa.dt.page.AccountSettingsPage;
import com.qa.dt.page.CommonReusablesPage;
import com.qa.dt.page.DashboardPage;
import com.qa.dt.page.HomePage;
import com.qa.dt.page.ItemServicePage;
import com.qa.dt.page.LoginPage;
import com.qa.dt.page.ProjectSelection;
import com.qa.dt.util.ExtentManager;

@Listeners(com.qa.dt.util.ListenerClass.class)
public class ItemServiceRegression extends LoginintoTheApplicationTest {
	AccountSettingsPage as;
	BaseClass bc;
	LoginPage lp;
	HomePage hp;
	ProjectSelection ps;
	ItemServicePage itemServicePg;
	CommonReusablesPage cr;
	DashboardPage dp;
	String appName, projName;
	String[] itemServicesTableHeaders = { "_id", "_name", "_shortName", "_description", "_itemClass", "_userItemId","_userType" };
	String[] relatedItemsTableHeaders = { "_id", "_metadata" };
	String id,shortname,description,itemClass,userItemId,relatedItemId;

	@BeforeClass
	public void precondition() throws Exception {
		lp = new LoginPage();
		as = new AccountSettingsPage();
		cr = new CommonReusablesPage();
		ps = new ProjectSelection();
		hp = new HomePage();
		dp = new DashboardPage();
		itemServicePg = new ItemServicePage();		
		itemClass = retrieveTestData("ItemService", "Value", 5);
		userItemId = retrieveTestData("ItemService", "Value", 6);
		relatedItemId = retrieveTestData("ItemService", "Value", 7);
		
	}
//	@Test(priority = -1)
	public void testData() throws Exception {
		projName = retrieveTestData("projectSelect", "Project", 9);
		appName = retrieveTestData("projectSelect", "Application", 9);		
		ps.selectApplicationAndWorkspace(appName, projName);
		hp.navigateToServices("itemservice");
		String[] values = itemServicePg.getItemservicesTestData("itemTest");
		writeExcelData(values,"ItemService");
	}
	

	/**
	 * @author Iyappan.Kasinathan
	 * @description Verify the validations of item services group table, search/filtered functionality and view user group modal
	 * @TC_ID PA-58
	 * @throws Exception
	 */
	@Test(priority = 1)
	public void verifyItemServicesTable() throws Exception {
		ExtentManager.test.log(Status.INFO, "TC_ID - PA-58");
		ExtentManager.test.log(Status.INFO,
				"TC_Description - Verify the validations of usergroup table, search/filtered functionality and view user group modal");
		projName = retrieveTestData("projectSelect", "Project", 9);
		appName = retrieveTestData("projectSelect", "Application", 9);	
		id = retrieveTestData("ItemService", "Value", 2);
		shortname = retrieveTestData("ItemService", "Value", 3);
		description = retrieveTestData("ItemService", "Value", 4);
		
		String[] rowsPerPageSelections = { "5", "10", "25","50","100" };
		String[] searchValues = { id, shortname,description };
		ps.selectApplicationAndWorkspace(appName, projName);
		hp.navigateToServices("itemservice");
		cr.verifyTableHeaders(itemServicesTableHeaders);		
//		for (String value : itemServicesTableHeaders) {
//			cr.sortingOrderOfTable("Ascending", value);
//			cr.sortingOrderOfTable("Descending", value);
//		}
//		for (String value : rowsPerPageSelections) {
//			cr.verifyPaginationFunctionality(value);
//		}
//		for (String field : searchValues) {
//			cr.searchTheTable(field);
//		}		
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description Verify the JSON details of named user items in item services page.
	 * @TC_ID PA-58
	 * @throws Exception
	 */
	@Test(priority = 2)
	public void verifyNamedUserItemsDetailsDisplayedInJSON() throws Exception {
		ExtentManager.test.log(Status.INFO, "TC_ID - PA-58");
		ExtentManager.test.log(Status.INFO,	"TC_Description - Verify the JSON details of named user items in item services page.");
		projName = retrieveTestData("projectSelect", "Project", 9);
		appName = retrieveTestData("projectSelect", "Application", 9);	
		id = retrieveTestData("ItemService", "Value", 2);
		ps.selectApplicationAndWorkspace(appName, projName);
		hp.navigateToServices("itemservice");
		cr.verifyTableHeaders(itemServicesTableHeaders);	
		passedStep("Successfully navigated to Item services page");
		itemServicePg.selectNoOfRows("100");
		waitpause();
		elementClick(cr.tableHeaders("_itemClass"));		
		waitpause();
		itemServicePg.viewNamedUserItemsJSONdata(id, "View");
		itemServicePg.copyJSONcode();
		cr.closeModal();
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description Verify the referenced items and their JSON details are displayed after selecting the named user items.
	 * @TC_ID PA-61
	 * @throws Exception
	 */
	@Test(priority = 3)
	public void verifyReferencedItemsDisplayed() throws Exception {
		ExtentManager.test.log(Status.INFO, "TC_ID - PA-61");
		ExtentManager.test.log(Status.INFO,	"TC_Description - Verify the referenced items and their details are displayed after selecting the named user items.");
		projName = retrieveTestData("projectSelect", "Project", 9);
		appName = retrieveTestData("projectSelect", "Application", 9);	
		id = retrieveTestData("ItemService", "Value", 2);
		relatedItemId = retrieveTestData("ItemService", "Value", 7);
		
		ps.selectApplicationAndWorkspace(appName, projName);
		hp.navigateToServices("itemservice");
		cr.verifyTableHeaders(itemServicesTableHeaders);	
		passedStep("Successfully navigated to Item services page");
//		elementClick(cr.tableHeaders("_itemClass"));
		itemServicePg.selectNoOfRows("100");
		itemServicePg.selectNamedUserItems(id);
		cr.verifyTableHeaders(relatedItemsTableHeaders);	
		itemServicePg.viewRelatedItemsJSONdata(relatedItemId, "View");
		cr.closeModal();
		elementClick(itemServicePg.namedUserItemsLink());
		cr.verifyTableHeaders(itemServicesTableHeaders);	
		passedStep("Successfully navigated back to named user items page after clicking the Go Back Button");	
		
	}
	@Test(priority = 4)
	public void verifyVersionAndBreadcrumbs() throws Exception {
		ExtentManager.test.log(Status.INFO, "TC_ID - PA-257,322");
		ExtentManager.test.log(Status.INFO, "TC_Description - ");
		projName = retrieveTestData("projectSelect","Project",9);
		appName = retrieveTestData("projectSelect", "Application", 9);
		id = retrieveTestData("ItemService", "Value", 2);
		ps.selectApplicationAndWorkspace(appName,projName);	
		hp.navigateToServices("itemservice");
		cr.verifyTableHeaders(itemServicesTableHeaders);	
		passedStep("Successfully navigated to Item services page");
//		elementClick(cr.tableHeaders("_itemClass"));
		itemServicePg.selectNoOfRows("100");
		itemServicePg.selectNamedUserItems(id);
		cr.verifyTableHeaders(relatedItemsTableHeaders);	
		elementDisplayed(itemServicePg.breadCrumbItemServices(), "Breadcrumb is displayed as expected", "Failed to display breadcrumb");
		elementClick(itemServicePg.namedUserItemsLink());
		cr.verifyTableHeaders(itemServicesTableHeaders);
//		elementClick(cr.tableHeaders("_itemClass"));
		itemServicePg.selectNoOfRows("100");
		itemServicePg.viewNamedUserItemsJSONdata(id, "View");
		elementDisplayed(itemServicePg.versionDropDown("1"), "Version dropdown dispalyed as expected", "Failed to display the version dropdown");
	}
	@Test(priority = 5)
	public void verifyItemServiceFilterFunction() throws Exception {
		ExtentManager.test.log(Status.INFO, "TC_ID - PA-257,322");
		ExtentManager.test.log(Status.INFO, "TC_Description - ");
		String[] filterBy= {"itemClass","name"};
		String[] filterValue= {"Script","Upload Scripts"};
		projName = retrieveTestData("projectSelect","Project",9);
		appName = retrieveTestData("projectSelect", "Application", 9);
		id = retrieveTestData("ItemService", "Value", 2);
		ps.selectApplicationAndWorkspace(appName,projName);	
		hp.navigateToServices("itemservice");
		cr.verifyTableHeaders(itemServicesTableHeaders);	
		passedStep("Successfully navigated to Item services page");
		itemServicePg.filterBasicsearchQuery(filterBy, filterValue);
		elementClick(itemServicePg.switchToAdvQueryBtn());
		itemServicePg.verifyAdvSearchQueryData(filterValue);
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description Verify the JSON details of named user items in item services page.
	 * @TC_ID PA-58
	 * @throws Exception
	 */
	@Test(priority = 6)
	public void verifyNamedUserItemsUsersPermission() throws Exception {
		ExtentManager.test.log(Status.INFO, "TC_ID - PA-58");
		ExtentManager.test.log(Status.INFO,	"TC_Description - Verify the JSON details of named user items in item services page.");
		projName = retrieveTestData("projectSelect", "Project", 9);
		appName = retrieveTestData("projectSelect", "Application", 9);	
		id = retrieveTestData("ItemService", "Value", 2);
		ps.selectApplicationAndWorkspace(appName, projName);
		hp.navigateToServices("itemservice");
		cr.verifyTableHeaders(itemServicesTableHeaders);	
		passedStep("Successfully navigated to Item services page");
		itemServicePg.selectNoOfRows("100");
		waitpause();
		elementClick(cr.tableHeaders("_itemClass"));		
		waitpause();
		itemServicePg.viewNamedUserItemsJSONdata(id, "Users");
		itemServicePg.verifyUsersAccessRights(loadProperties().getProperty("Rfusername"));
		cr.closeModal();
	}
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

	

}
