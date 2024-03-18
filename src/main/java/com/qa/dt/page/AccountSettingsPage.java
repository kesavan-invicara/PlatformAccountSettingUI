package com.qa.dt.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.Status;
import com.qa.dt.base.BaseClass;
import com.qa.dt.util.ExtentManager;

public class AccountSettingsPage extends BaseClass {
	public AccountSettingsPage() {
		PageFactory.initElements(driver, this);
	}
//	WebElements
	
	public static WebElement profileInformation() {
		return driver.findElement(By.xpath("//a[normalize-space() = 'Profile Information']"));
	}
	public static WebElement profileInformationEmail() {
		return driver.findElement(By.xpath("//input[@id='email']"));
	}

	public static WebElement passwordOption() {
		return driver.findElement(By.xpath("//span[@id='targetElementId2'][text()= 'Password']"));
	}

	public static WebElement deleteAccountOption() {
		return driver.findElement(By.xpath("//span[@id='targetElementId3'][text()= 'Delete account']"));
	}


	/**
	 *@author Iyappan.Kasinathan
	 *@description This method used to navigate to profile information page
	 */
	public void navigateToProfileInformationPage() {
		waitForpageLoad();
		try {
			elementClick(profileInformation());
			ExtentManager.test.log(Status.INFO, "Profile information button is clicked");
			switchOrCloseTabs(2,"switch");
			waitForpageLoad();
			waitUntilElementVisibility(profileInformationEmail());
			String actualValue = getElementAttribute(profileInformationEmail(), "value");
			comparetwoText(actualValue, loadProperties().getProperty("Rfusername"));
			ExtentManager.test.log(Status.PASS, "Navigated to Profile information page successfully");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clickPasswordOption() throws Exception {
		waitUntilElementVisibility(passwordOption());
		elementClick(passwordOption());
		waitForpageLoad();
	}

	public void clickDeleteAccountOption() throws Exception {
		waitUntilElementVisibility(deleteAccountOption());
		elementClick(deleteAccountOption());
		waitForpageLoad();
	}
}
