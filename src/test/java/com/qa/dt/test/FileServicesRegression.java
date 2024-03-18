package com.qa.dt.test;

import org.openqa.selenium.NoSuchElementException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.qa.dt.base.BaseClass;
import com.qa.dt.page.AccountSettingsPage;
import com.qa.dt.page.ApiMethodsPage;
import com.qa.dt.page.CommonReusablesPage;
import com.qa.dt.page.FileServicePage;
import com.qa.dt.page.HomePage;
import com.qa.dt.page.LoginPage;
import com.qa.dt.page.ProjectSelection;
import com.qa.dt.util.ExtentManager;

@Listeners(com.qa.dt.util.ListenerClass.class)
public class FileServicesRegression extends LoginintoTheApplicationTest {
	AccountSettingsPage as;
	BaseClass bc;
	LoginPage lp;
	HomePage hp;
	ProjectSelection ps;
	CommonReusablesPage cr;
	FileServicePage fileServicesPage;
	String appName, projName;
	ApiMethodsPage apiPage;
	String userName = "iyappan.k@indiumsoft.com";
	String password = "Invicara@123";
	String appId="c9a0c660-7b53-401f-ad5c-07e823d9cd4c";
	String nsFilter = "cTest_TBeBRnYh";
	String bearerTok=null;

	@BeforeClass
	public void precondition() throws Exception {
		lp = new LoginPage();
		as = new AccountSettingsPage();
		ps = new ProjectSelection();
		hp = new HomePage();
		cr = new CommonReusablesPage();
		fileServicesPage = new FileServicePage();
		apiPage = new ApiMethodsPage();
//		lp.loginTheApplication(loadProperties().getProperty("DbmAdminUsername"),loadProperties().getProperty("DbmAdminPassword"));
	}
	@Test(priority = -1)
	public void testData() throws Exception {
		projName = retrieveTestData("projectSelect","Project",9);
		appName = retrieveTestData("projectSelect", "Application", 9);
		ps.selectApplicationAndWorkspace(appName,projName);	
		hp.navigateToServices("fileservice");
		fileServicesPage.verifyFileServicePageDisplayed(projName);
		try {
			fileServicesPage.folderLabel("test").isDisplayed();
		} catch (NoSuchElementException e) {
			// Get bearer Token
			String bearerTok = apiPage.getBearerToken(userName, password, appId);
			// Create Foldery
			String folderId = apiPage.createFolder(bearerTok, "test", nsFilter);
			apiPage.uploadFile(bearerTok, folderId, nsFilter);
			apiPage.uploadFile(bearerTok, folderId, nsFilter);
			//
			String firstChildFolderid = apiPage.createChildFolder(bearerTok, "test2", nsFilter, folderId);
			// Upload file
			apiPage.uploadFile(bearerTok, firstChildFolderid, nsFilter);
			String secondChildFolderid = apiPage.createChildFolder(bearerTok, "test3", nsFilter, firstChildFolderid);

			// Delete folder
//			apiPage.deleteFolder(bearerTok, folderId,nsFilter);
		}
		
		
		
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description Verify the folders list are displayed in hierarchical model.
	 * @TC_ID PA-67
	 * @throws Exception
	 */
	@Test(priority = 1)
	public void verifyHierarchyOfFoldersDisplayed() throws Exception {
		ExtentManager.test.log(Status.INFO, "TC_ID - PA-67");
		ExtentManager.test.log(Status.INFO, "TC_Description - Verify the folders list are displayed in hierarchical model.");
		projName = retrieveTestData("projectSelect","Project",9);
		appName = retrieveTestData("projectSelect", "Application", 9);		
		ps.selectApplicationAndWorkspace(appName,projName);	
		hp.navigateToServices("fileservice");
		fileServicesPage.verifyFileServicePageDisplayed(projName);
		fileServicesPage.verifyFoldersDisplayed("cTest", "test");
		fileServicesPage.verifyFoldersDisplayed("test","test2");	
		fileServicesPage.verifyFoldersDisplayed("test2","test3");	
		fileServicesPage.viewFilesActions("sample.pdf", "View", "pdf-renderer",null);
	}
	@Test(priority = 2)
	public void verifyFileVersionHistoryandBreadcrumb() throws Exception {
		ExtentManager.test.log(Status.INFO, "TC_ID - PA-257,322");
		ExtentManager.test.log(Status.INFO, "TC_Description - ");
		projName = retrieveTestData("projectSelect","Project",9);
		appName = retrieveTestData("projectSelect", "Application", 9);
		String fileName = "sample.pdf";
		ps.selectApplicationAndWorkspace(appName,projName);	
		hp.navigateToServices("fileservice");
		fileServicesPage.verifyFileServicePageDisplayed(projName);
		fileServicesPage.verifyFoldersDisplayed("cTest", "test");
		fileServicesPage.verifyFoldersDisplayed("test", fileName);		
		elementDisplayed(fileServicesPage.breadCrumbFileServices("test"), "Breadcrumb is displayed as expected", "Failed to display breadcrumb");
		fileServicesPage.downloadFile(fileName);
		fileServicesPage.viewFilesActions(fileName, "File Version History", null,null);		
		fileServicesPage.verifyCurrentTag();
	}
	@Test(priority = 3)
	public void verifyFilterfunctionsInFileServices() throws Exception {
		ExtentManager.test.log(Status.INFO, "TC_ID - PA-257,322");
		ExtentManager.test.log(Status.INFO, "TC_Description - ");
		projName = retrieveTestData("projectSelect","Project",9);
		appName = retrieveTestData("projectSelect", "Application", 9);
		ps.selectApplicationAndWorkspace(appName,projName);	
		hp.navigateToServices("fileservice");
		fileServicesPage.verifyFileServicePageDisplayed(projName);
		fileServicesPage.verifyFoldersDisplayed("cTest", "test");
		fileServicesPage.searchFilesFolders("Name", "Folder", "test");
	}
	@Test(priority = 4)
	public void verifySortingFunction() throws Exception {
		String[] fileServiceHeaders = { "Name", "Version", "Size", "Updated By", "Last Updated"};
		ExtentManager.test.log(Status.INFO, "TC_ID - PA-257,322");
		ExtentManager.test.log(Status.INFO, "TC_Description - ");
		projName = retrieveTestData("projectSelect","Project",9);
		appName = retrieveTestData("projectSelect", "Application", 9);
		ps.selectApplicationAndWorkspace(appName,projName);	
		hp.navigateToServices("fileservice");
		fileServicesPage.verifyFileServicePageDisplayed(projName);
		fileServicesPage.verifyFoldersDisplayed("cTest", "test");
		cr.verifyTableHeaders(fileServiceHeaders);		
//		for (String value : fileServiceHeaders) {
//			cr.sortingOrderOfTable("Ascending", value);
//			cr.sortingOrderOfTable("Descending", value);
//		}		
	}
	@Test(priority = 5)
	public void verifyTheDeletionOfFilesAndFolder() throws Exception {
		ExtentManager.test.log(Status.INFO, "TC_ID - PA-257,322");
		ExtentManager.test.log(Status.INFO, "TC_Description - ");
		projName = retrieveTestData("projectSelect","Project",9);
		appName = retrieveTestData("projectSelect", "Application", 9);
		String fileName = "sample.pdf";
		String bearerTok = apiPage.getBearerToken(userName,password,appId);
		//Create Folder
		String folderId = apiPage.createFolder(bearerTok, "deleteFolder",nsFilter);
		apiPage.uploadFile(bearerTok, folderId,nsFilter);
		apiPage.uploadFile(bearerTok, folderId,nsFilter);
		ps.selectApplicationAndWorkspace(appName,projName);	
		hp.navigateToServices("fileservice");
		fileServicesPage.verifyFileServicePageDisplayed(projName);		
		fileServicesPage.verifyFoldersDisplayed("cTest", "deleteFolder");	
		fileServicesPage.verifyFoldersDisplayed("deleteFolder",fileName);	
		fileServicesPage.viewFilesActions(fileName, "File Version History", null,null);	
		fileServicesPage.deleteFileVersionHistory("2");		
		fileServicesPage.viewFilesActions(fileName, "Delete", null,"File sample.pdf was deleted successfully.");
//		fileServicesPage.verifyFoldersDisplayed("cTest", "deleteFolder");
		fileServicesPage.verifyFoldersDisplayed("cTest", "deleteFolder");
		fileServicesPage.viewFilesActions("deleteFolder", "Delete", null,"");
	}
	@Test(priority = 6)
	public void verifyAdditionAndDeletionOfTags() throws Exception {
		ExtentManager.test.log(Status.INFO, "TC_ID - PA-257,322");
		ExtentManager.test.log(Status.INFO, "TC_Description - ");
		projName = retrieveTestData("projectSelect","Project",9);
		appName = retrieveTestData("projectSelect", "Application", 9);
		String fileName = "test";
		String tagName="test";
		ps.selectApplicationAndWorkspace(appName,projName);	
		hp.navigateToServices("fileservice");
		fileServicesPage.verifyFileServicePageDisplayed(projName);	
		fileServicesPage.createTags(fileName,tagName);
		fileServicesPage.verifyTagsCreated(tagName);
		fileServicesPage.deleteTags(fileName);
		fileServicesPage.verifyTagsDeleted(tagName);
	}
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
	
	

	
	

	

}

