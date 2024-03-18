package com.qa.dt.page;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.aventstack.extentreports.Status;
import com.qa.dt.base.BaseClass;
import com.qa.dt.util.ExtentManager;
import com.qa.dt.util.Log;
import com.qa.dt.util.Xls_Reader;

public class ProjectSelection extends CommonReusablesPage {
	public ProjectSelection() {
		PageFactory.initElements(driver, this);
	}
//	WebElements

	@FindBy(xpath = "//ul[contains(@class,'szh-menu--state-open szh-menu--dir-bottom')]")
	private static WebElement applicationDd;

	@FindBy(xpath = "//span[text()='Please select']")
	private static WebElement defaultText;
	
	@FindBy(xpath = "//div[@class='applications-selection__menu-item']")
	private static WebElement appSelectMenu;
	
	@FindBy(xpath = "//ul[contains(@class,'szh-menu--state-open szh-menu--dir-right')]")
	private static WebElement projectDd;

	@FindBy(xpath = "//div[@class='applications-selection']/*//span[@class='application-workspace-menu']")
	private static WebElement applicationProjectSelectedView;
	
	public List<WebElement> applicationList() {
		return driver.findElements(By.xpath("//li[text()='Application']/parent::ul//li[@class='szh-menu__item']"));
	}

//	Action Methods

	public boolean verifyDefaultSelectText() throws Exception {
		try {
			boolean elementIsDisplayed = elementIsDisplayed(defaultText);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			ExtentManager.test.log(Status.ERROR, "Unable To Login");
			return false;
		}

	}

	public void selectApplicationFromDd(String name) throws Exception {
		waitUntilElementClickableforsec(appSelectMenu, 150);
		try {	
			elementClick(appSelectMenu);
			waitUntilElementVisibility(applicationDd);
			WebElement findElement = driver.findElement(
					By.xpath("//ul[contains(@class,'szh-menu--state-open szh-menu--dir-bottom')]/li/div[text()='" + name
							+ "']"));
			moveToElement(findElement);
			elementIsDisplayed(projectDd);
		} catch (Exception e) {
			e.printStackTrace();

		}

	}
	
	public void selectProjectFromDd(String name) throws Exception {
		try {
			WebElement findElement = driver
					.findElement(By.xpath("//ul/div[@class='menu-container']/div/li[text()='" + name + "']"));
			elementClick(findElement);

		} catch (Exception e) {
			e.printStackTrace();

		}

	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description This method is used to verify selected application and project is displayed
	 */
	public boolean verifyAppProjectSelection(String appwkname) throws Exception {
		try {
			String text = getElementText(applicationProjectSelectedView);
			comparetwoText(appwkname,text);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			ExtentManager.test.log(Status.ERROR, "Unable To select Project");
			return false;
		}

	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description This method is used to Select application and project
	 */
	public void selectApplicationAndWorkspace(String appName, String projName) {
		
		try {
			verifyDefaultSelectText();
			selectApplicationFromDd(appName);
			selectProjectFromDd(projName);
			verifyAppProjectSelection(appName+" | "+projName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description This method is used to verify only application name displayed in passport services page
	 */
	public void verifyAppOnlySelectedUnderPS() {
		try {
		for(WebElement element:applicationList()) {
			String value = getElementAttribute(element, "role");
			Assert.assertEquals(value, "menuitem");
		}
		passedStep("Only application can be selected under passport services page");
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			failedStep(e.getMessage());
		}
	}
	
	public void updateWorkspaceNames() {
		try {
			
		}catch (Exception e) {
			e.printStackTrace();
			failedStep(e.getMessage());
		}
	}

}
