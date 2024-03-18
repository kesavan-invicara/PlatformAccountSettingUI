package com.qa.dt.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.Status;
import com.qa.dt.base.BaseClass;
import com.qa.dt.util.ExtentManager;

public class DashboardPage extends BaseClass {
	public DashboardPage() {
		PageFactory.initElements(driver, this);
	}

//	WebElements

		
	public WebElement userInitialsLogo(){
		return driver.findElement(By.id("userInitialsLogo"));
	}

	public WebElement logout() {
		return driver.findElement(By.xpath("//*[@id='logoutForm']/button"));
	}	

	public WebElement greetings() {
		return driver.findElement(By.id("greeting"));
	}

	public WebElement greetingName() {
		return driver.findElement(By.xpath("//*[@id='dashboard']/div[1]/div[1]/div/span[2]"));
	}

	public WebElement invitationsCount() {
		return driver.findElement(By.xpath("//*[@id='dashboard']/div[1]/div[2]/div[1]/div/span"));
	}

	public WebElement myApplicationsCount() {
		return driver.findElement(By.xpath("//*[@id='dashboard']/div[1]/div[2]/div[2]/div/span/span"));
	}

	public WebElement userInformation() {
		return driver.findElement(By.xpath("//*[@id='sourceId1']//span[contains(text(),'User information')]"));
	}

	public WebElement password() {
		return driver.findElement(By.xpath("//*[@id='sourceId2']//span[contains(text(),'Password')]"));
	}

	public WebElement deleteAccount() {
		return driver.findElement(By.xpath("//*[@id='sourceId3']//span[contains(text(),'Delete account')]"));
	}

	public WebElement authenticator() {
		return driver.findElement(By.xpath("//*[@id='sourceId4']//span[contains(text(),'Authenticator')]"));
	}

	public WebElement authenticatorMirrana() {
		return driver.findElement(By.xpath("//span[@id='sourceId4' and text() = 'Authenticator']"));
	}

	public WebElement userAccessKeys() {
		return driver.findElement(By.xpath("//*[@id='sourceId5']//span[contains(text(),'User access keys')]"));
	}

	public WebElement userAccessKeysMirrana() {
		return driver.findElement(By.xpath("//span[@id='sourceId5' and text() = 'User access keys']"));
	}

	public WebElement myApplications() {
		return driver.findElement(By.xpath("//*[@id='sourceId6']//span[contains(text(),'My applications')]"));
	}

	public WebElement invitations() {
		return driver.findElement(By.xpath("//*[@id='sourceId7']//span[contains(text(),'Invitations')]"));
	}

			
	
//	Action Methods

	/**
	 * click User Initials Logo 
	 *  
	 * @throws Exception
	 */	public void clickUserInitialsLogo() throws Exception {		
		waitUntilElementVisibility(userInitialsLogo());	
		elementClick(userInitialsLogo());
	}

	/**
	 * click User Initials Logo 
	 *  
	 * @throws Exception
	 */	public String getUserInitialsLogoText() throws Exception {		
		waitUntilElementVisibility(userInitialsLogo());	
		return userInitialsLogo().getText();
	}

	/**
	 * click Logout 
	 *  
	 * @throws Exception
	 */	public void clickLogout() throws Exception {		
		waitUntilElementVisibility(logout());	
		elementClick(logout());
	}

	/**
	 * get Greetings 
	 *  
	 * @throws Exception
	 */	public String getGreetings() throws Exception {		
		waitUntilElementVisibility(greetings());	
		return greetings().getText();
	}

	/**
	 * get Greetings Name 
	 *  
	 * @throws Exception
	 */	public String getGreetingName() throws Exception {		
		waitUntilElementVisibility(greetingName());	
		return greetingName().getText();
	}

	/**
	 * get invitations count 
	 *  
	 * @throws Exception
	 */	public String getInvitationsCount() throws Exception {		
		waitUntilElementVisibility(invitationsCount());	
		return invitationsCount().getText();
	}

	/**
	 * get my applications count 
	 *  
	 * @throws Exception
	 */	public String getMyApplicationsCount() throws Exception {		
		waitForpageLoad();
		waitUntilElementVisibility(myApplicationsCount());	
		return myApplicationsCount().getText();
	}

	public boolean userInformationLinkIsDisplayed() throws Exception {
		boolean displayed = userInformation().isDisplayed();
		return displayed;
	}

	public boolean passwordLinkIsDisplayed() throws Exception {
		boolean displayed = password().isDisplayed();
		return displayed;
	}

	public boolean deleteAccountLinkIsDisplayed() throws Exception {
		boolean displayed = deleteAccount().isDisplayed();
		return displayed;
	}

	public boolean authenticatorLinkIsDisplayed() throws Exception {
		boolean displayed = authenticator().isDisplayed();
		return displayed;
	}

	public boolean mirranaAuthenticatorLinkIsDisplayed() throws Exception {
		boolean displayed = authenticatorMirrana().isDisplayed();
		return displayed;
	}

	public boolean userAccessKeyLinkIsDisplayed() throws Exception {
		boolean displayed = userAccessKey().isDisplayed();
		return displayed;
	}

	public boolean mirranaUserAccessKeyLinkIsDisplayed() throws Exception {
		boolean displayed = userAccessKeysMirrana().isDisplayed();
		return displayed;
	}

	public boolean myApplicationsLinkIsDisplayed() throws Exception {
		boolean displayed = myApplications().isDisplayed();
		return displayed;
	}

	public boolean invitationsLinkIsDisplayed() throws Exception {
		boolean displayed = invitations().isDisplayed();
		return displayed;
	}

	public void clickUserInformationLink() throws Exception {
		waitUntilElementVisibility(userInformation());
		elementClick(userInformation());
		waitForpageLoad();
	}

	public void clickPasswordLink() throws Exception {
		waitUntilElementVisibility(password());
		elementClick(password());
		waitForpageLoad();
	}

	public void clickDeleteAccountLink() throws Exception {
		waitUntilElementVisibility(deleteAccount());
		elementClick(deleteAccount());
		waitForpageLoad();
	}

	public void clickAuthenticatorLink() throws Exception {
		waitUntilElementVisibility(authenticator());
		elementClick(authenticator());
		waitForpageLoad();
	}

	public void clickUserAccessKeysLink() throws Exception {
		waitUntilElementVisibility(userAccessKey());
		elementClick(userAccessKey());
		waitForpageLoad();
	}

	public void clickMyApplicationsLink() throws Exception {
		waitUntilElementVisibility(myApplications());
		elementClick(myApplications());
		waitForpageLoad();
	}

	public void clickInvitationsLink() throws Exception {
		waitUntilElementVisibility(invitations());
		elementClick(invitations());
		waitForpageLoad();
	}



}
