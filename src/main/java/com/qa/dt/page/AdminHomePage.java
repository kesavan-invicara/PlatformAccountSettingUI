package com.qa.dt.page;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;

import com.aventstack.extentreports.Status;
import com.qa.dt.base.BaseClass;
import com.qa.dt.util.ExtentManager;
import com.qa.dt.util.Xls_Reader;

/**
 * Login Page Elements
 * 
 * @author VasuSekar
 *
 */
public class AdminHomePage extends CommonReusablesPage {
	static Xls_Reader reader;
	
	public AdminHomePage() {
		reader	= new Xls_Reader();
		PageFactory.initElements(driver, this);
	}

	// @FindBy(xpath = "(//*[@id=\"IfefSidePanelContent\"]/div/div/div/div[1]/span/button")
	// private WebElement leftNavigation;	

	@FindBy(xpath = "(//button[@class='primary'])[1]")
	private WebElement loginButton;

	@FindBy(xpath = "//input[@id='password']")
	private WebElement passWord;

	@FindBy(xpath = "//div[@class='alert alert-danger alert-warning']")
	private WebElement invalidUserNameErrorMsg;
	
	@FindBy(xpath = "(//button[@class='secondary'])[1]")
	private static WebElement SignUpButton;
	
	@FindBy(xpath = "//h1[text()='Sign In']")
	private static WebElement SignInHeaderName;
	
	public static WebElement leftNavigation(){
		return driver.findElement(By.xpath("//*[@id='IfefSidePanelContent']/div/div/div/div[1]/span"));
	}
	
	public static WebElement leftNavigationOnContent(){
		return driver.findElement(By.xpath("//*[@id=\"IfefSidePanelContent\"]/div/div/div/div/div[1]/span/div/button[1]"));
	}


	public static WebElement applications() {
		return driver.findElement(By.xpath("//div[@class='list']//a[contains(text(),'Applications')]"));
	}

	public static WebElement accountSettings() {
		return driver.findElement(By.xpath("//a[contains(text(),'Account Settings')]"));
	}

	public static WebElement signoutButton() {
		return driver.findElement(By.xpath("//*[@id='app']//button[contains(text(), 'Sign out')]"));
	}		
	
	public static WebElement automationApplication() {
		return driver.findElement(By.xpath("//div[@class='mbsc-lv-sl-c']//li[@id='e397febd-d40a-4cec-be83-df966569140d']"));
	}

	
	public static WebElement threeDotMenu() {
		return driver.findElement(By.xpath("//div[@class='view']/div/button"));
	}

	public static WebElement applicationUsersMenu() {
		return driver.findElement(By.xpath("//ul[@class='asf-popover-menu']//li/a[contains(text(),'Users')]"));
	}

	public static WebElement applicationInviteNewUserMenu() {
		return driver.findElement(By.xpath("//ul[@class='asf-popover-menu']/li/a[contains(text(),'Invite New User')]"));
	}
	
	public static WebElement inviteNewUserEmailTextbox() {
		return driver.findElement(By.xpath("//form//input[@type='email']"));
	}
	
	public static WebElement inviteNewUserApplicationTextbox() {
		return driver.findElement(By.xpath("//input[@type='text']"));
	}

	public static WebElement inviteNewUserSelectApplication(String application) {
		return driver.findElement(By.xpath("//div[@role='option' and @data-val='/"+application+"/']"));
	}

	public static WebElement inviteNewUserSetButton() {
		return driver.findElement(By.xpath("//div[@role='button' and text()='Set']"));
	}

	public static WebElement inviteNewUserInviteButton() {
		return driver.findElement(By.xpath("//form//button[@type='button' and text() ='Invite']"));
	}

	
	/**
	 * click left navigation 
	 *  
	 * @throws Exception
	 */
	public void clickLeftNavigation() throws Exception {
		Thread.sleep(10000);	
		waitUntilElementVisibility(leftNavigation());	
		elementClick(leftNavigation());
	}

	/**
	 * click left navigation on content
	 *  
	 * @throws Exception
	 */
	public void clickLeftNavigationOnContent() throws Exception {
		Thread.sleep(10000);	
		waitUntilElementVisibility(leftNavigationOnContent());	
		elementClick(leftNavigationOnContent());
	}

	/**
	 * click Applications menu options 
	 *  
	 * @throws Exception
	 */	public void clickApplicationsSettings() throws Exception {		
		waitUntilElementVisibility(applications());	
		elementClick(applications());
	}

	/**
	 * click Account Settings menu options 
	 *  
	 * @throws Exception
	 */	public void clickAccountSettings() throws Exception {		
		waitUntilElementVisibility(accountSettings());	
		elementClick(accountSettings());
	}

	/**
	 * click Sign out
	 *  
	 * @throws Exception
	 */	public void clickSignout() throws Exception {		
		waitUntilElementVisibility(signoutButton());	
		elementClick(signoutButton());
	}

	/**
	 * select Automation Application
	 *  
	 * @throws Exception
	 */	public void selectAutomationApplication() throws Exception {		
		waitUntilElementVisibility(automationApplication());	
		elementClick(automationApplication());
		waitForpageLoad();
	}

	/**
	 * click Three dot menu
	 *  
	 * @throws Exception
	 */	public void clickThreeDotMenuOption() throws Exception {		
		waitUntilElementVisibility(threeDotMenu());	
		elementClick(threeDotMenu());
	}

	/**
	 * select application user menu options
	 *  
	 * @throws Exception
	 */	public void selectApplicationUserMenuOptions() throws Exception {		
		waitUntilElementVisibility(applicationUsersMenu());	
		elementClick(applicationUsersMenu());
	}

	/**
	 * select application invite new user menu options
	 *  
	 * @throws Exception
	 */	public void selectApplicationInviteNewUserMenuOptions() throws Exception {		
		waitUntilElementVisibility(applicationInviteNewUserMenu());	
		elementClick(applicationInviteNewUserMenu());
	}

	public void inviteNewUser(String email, String application) throws Exception {	
		String app= "";	
		waitUntilElementVisibility(inviteNewUserEmailTextbox());	
		elementClear(inviteNewUserEmailTextbox());
		elementSendKeys(inviteNewUserEmailTextbox(),email);
		elementClick(inviteNewUserApplicationTextbox());
		if(application.equals("Admin")){
			app = "admin";
		} else {
			app = "digitaltwin";
		}
		Thread.sleep(5000);
		waitUntilElementClickable(inviteNewUserSelectApplication(app));
		elementClick(inviteNewUserSelectApplication(app));
		elementClick(inviteNewUserSetButton());
		elementClick(inviteNewUserInviteButton());
	}

	

}
