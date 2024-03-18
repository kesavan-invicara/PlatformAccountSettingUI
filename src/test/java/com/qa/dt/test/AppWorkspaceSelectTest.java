package com.qa.dt.test;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.qa.dt.base.BaseClass;
import com.qa.dt.page.ProjectSelection;
import com.qa.dt.util.Xls_Reader;

public class AppWorkspaceSelectTest extends LoginintoTheApplicationTest {
	ProjectSelection ps;
	BaseClass bc;
	static Xls_Reader xl = new Xls_Reader();
	public String appName,projName;
	/**
	 * @author 
	 * @description The console application should show the options to the user to visit their account details page and to logout at all times
	 * @TC_ID PA-46,47
	 * @throws Exception
	 */
    @Test
	public void verifyApplicationProjectSelection() throws Exception {
    	appName = retrieveTestData("projectSelect","Application",2);
    	projName = retrieveTestData("projectSelect","Project",2);
		ps = new ProjectSelection();
		ps.verifyDefaultSelectText();
		ps.selectApplicationFromDd(appName);
		ps.selectProjectFromDd(projName);
		ps.verifyAppProjectSelection(appName+" | "+projName);
		String appName1 = retrieveTestData("projectSelect","Application",9);
		String projName1 = retrieveTestData("projectSelect","Project",9);
		ps = new ProjectSelection();
		ps.selectApplicationFromDd(appName1);
		ps.selectProjectFromDd(projName1);
		ps.verifyAppProjectSelection(appName1+" | "+projName1);
	}
    /**
	 * @author 
	 * @description The console application should show the options to the user to visit their account details page and to logout at all times
	 * @TC_ID PA-53
	 * @throws Exception
	 */
    @Test
	public void verifyChangeAppProjectSelection() throws Exception {
    	appName = retrieveTestData("projectSelect","Application",2);
    	projName = retrieveTestData("projectSelect","Project",2);
		ps = new ProjectSelection();
		ps.selectApplicationFromDd(appName);
		ps.selectProjectFromDd(projName);
		ps.verifyAppProjectSelection(appName+" | "+projName);
		
    	appName = retrieveTestData("projectSelect","Application",9);
    	projName = retrieveTestData("projectSelect","Project",9);
		ps = new ProjectSelection();
		ps.selectApplicationFromDd(appName);
		ps.selectProjectFromDd(projName);
		ps.verifyAppProjectSelection(appName+" | "+projName);
	}
    @AfterMethod
	public void tearDown() {
		driver.quit();
	}
	
	
	
}
