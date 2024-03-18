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
public class LoginPage extends CommonReusablesPage {
	static Xls_Reader reader;
	
	public LoginPage() {
		reader	= new Xls_Reader();
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "(//div[@class='logo'])[1]")
	private WebElement invicaraLogo;

	

	@FindBy(xpath = "(//button[@class='primary'])[1]")
	private WebElement loginButton;

	@FindBy(xpath = "//input[@id='password']")
	private WebElement passWord;

	@FindBy(xpath = "//div[@class='alert alert-danger alert-warning']")
	private WebElement invalidUserNameErrorMsg;
	
	// @FindBy(xpath = "(//button[contains(@class, 'secondary') and text() = 'Sign Up']")
	// private static WebElement SignUpButton;
	
	@FindBy(xpath = "//h1[text()='Sign In']")
	private static WebElement SignInHeaderName;

	public static WebElement SignUpButtonOnLoginPage() {
		return driver.findElement(By.xpath("//button[contains(@class, 'secondary') and text() = 'Sign Up']"));
	}
	
	public static WebElement userAccountDropdownButton() {
		return driver.findElement(By.xpath("//div[contains(@class,'MuiAvatar-root MuiAvatar-circular MuiAvatar-colorDefault')]"));
	}
	public static WebElement userProfile() throws IOException {
		return driver.findElement(By.xpath("//li[text()='"+loadProperties().getProperty("RfFullname")+"']"));
	}
	public static WebElement logOutButton() {
		return driver.findElement(By.xpath("//li[text()='Logout']"));
	}
	public WebElement userName() {
		return driver.findElement(By.xpath("//input[@name='username']"));
	}
	public WebElement password() {
		return driver.findElement(By.xpath("//input[@id='password']"));
	}
	public WebElement loginButton() {
		return driver.findElement(By.xpath("//button[text()='Login']"));
	}
	public WebElement userProfileAppName() {
		return driver.findElement(By.xpath("//li[@class='szh-menu__header profile-menu__header']"));
	}
	public WebElement userProfileProjectName() {
		return driver.findElement(By.xpath("//li[@class='szh-menu__header']"));
	}
	public WebElement getReleaseVersion() {
		return driver.findElement(By.xpath("//li[text()='Release Version']/following-sibling::li[@class='szh-menu__header']"));
	}

	public WebElement signUpEmail(){
		return driver.findElement(By.xpath("//form/input[@id='email']"));
	}

	public WebElement signUpFirstName(){
		return driver.findElement(By.xpath("//form//input[@id='firstName']"));
	}

	public WebElement signUpLastName(){
		return driver.findElement(By.xpath("//form//input[@id='lastName']"));
	}

	public WebElement signUpPassword(){
		return driver.findElement(By.xpath("//form//input[@id='password']"));
	}

	public WebElement signUpConfirmPassword(){
		return driver.findElement(By.xpath("//form//input[@id='cPassword']"));
	}

	public WebElement signUpConfirmationCheckbox(){
		return driver.findElement(By.xpath("//form//input[@type='checkbox']"));
	}
	
	public WebElement signUpButton(){
		return driver.findElement(By.xpath("//*[@id='signup']//button[contains(text(),'Sign Up')]"));
	}

	public WebElement signUpMessage(){
		return driver.findElement(By.xpath("//div[@id='response']"));
	}

	public WebElement signUpClickHereLink(){
		return driver.findElement(By.xpath("//*[@id='response']/a[contains(text(),'click here')]"));
	}

	public WebElement signInMessage(){
		return driver.findElement(By.xpath("//*[@id='em-response']"));
	}

	public WebElement signInEmailVerificationMessage(){
		return driver.findElement(By.xpath("//*[@id='login']/div[2]"));
	}


	

	public boolean invicaroLogoIsDisplayed() throws Exception {
		boolean displayed = invicaraLogo.isDisplayed();
		return displayed;
	}
	public boolean SignInNameIsDisplayed() throws Exception {
		boolean displayed = SignInHeaderName.isDisplayed();
		return displayed;
	}
	public void browserLaunch() throws IOException, Exception {
		try {
		browser_LaunchIgnoreCase(loadProperties().getProperty("Browser"));
		Properties loadProperties = loadProperties();
		System.out.println(loadProperties.getProperty("RfUrl"));
		launchURL(loadProperties.getProperty("RfUrl"));
		}catch (Exception e) {
			e.printStackTrace();
			failedStep(e.getMessage());
		}
	}
	/**
	 * @author 
	 * @description This method is used to log into the application
	 */
	public void loginTheApplication(String username, String password) throws Exception {
		try {
			browserLaunch();
			enterUserName(username);
			Thread.sleep(10000);
			enterPassword(password);
//			ExtentManager.test.log(Status.PASS,"Logged in the Console application successfully by " + username);
		} catch (Exception e) {
			e.printStackTrace();
//			failedStep(e.getMessage());
		}
	}
	/**
	 * Enter The User Name For Login
	 * 
	 * @param uName Login User Name
	 * @throws Exception
	 */
	public void enterUserName(String uName) throws Exception {
		elementSendKeys(userName(), uName);
//		sendKeysJavascript(userName, uName);
		elementClick(loginButton());
	}
	/**
	 * To Enter The Password For Login To click The Login Button
	 * 
	 * @param upassWord Login Password
	 * @return 
	 * @return 
	 * @return Return To Home Page
	 * @throws Exception
	 */
	public ProjectSelection enterPassword(String psw) throws Exception {
		waitUntilElementVisibility(password());
		elementSendKeys(password(), psw);
		elementClick(loginButton());
		return new ProjectSelection();
	}
	public void LoginTest(String uName,String psw ) throws Exception {
		sendKeysJavascript(userName(), uName);
		elementClick(loginButton());
		Thread.sleep(5000);
		if (passWord.isDisplayed()) {
			elementSendKeys(passWord, psw);
			elementClick(loginButton());
			Thread.sleep(3000);
		}
		else if (invalidUserNameErrorMsg.isDisplayed()) {
			System.out.println("Invalide User Name");
		}
		
	}
	/**
	 * To Click The SignUp Button
	 * @return
	 * @throws Exception
	 */
	
	public void clickSignUp() throws Exception {
		waitUntilElementClickable(SignUpButtonOnLoginPage());
		elementClick(SignUpButtonOnLoginPage());
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description This method used to select user profile from user profile drop down.
	 */
	public void selectUserProfile() {
		try {
			elementClick(userAccountDropdownButton());
			if(userProfile().isDisplayed()) {
				ExtentManager.test.log(Status.INFO, "User profile dropdown is selected");
				elementClick(userProfile());
				switchOrCloseTabs(1,"switch");
				Assert.assertTrue(userAccessKey().isDisplayed());
				ExtentManager.test.log(Status.PASS, "Navigated to Account information page successfully");
			}else {
				failedStep("User profile dropdown is not displayed");
			}
			
			
		}catch (Exception e) {
			e.printStackTrace();
			failedStep(e.getMessage());
		}
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description This method is used to verify the application and project names are displayed as selected
	 */
	public void verifyPlatformAppAndProjectName(String expectedProjectName,String expectedAppname) {
		try {
		elementClick(userAccountDropdownButton());
		if(userProfile().isDisplayed()) {
			String actualAppName = getElementText(userProfileAppName());
			Assert.assertEquals(actualAppName, expectedAppname);
			String actualProjectName = getElementText(userProfileProjectName());
			Assert.assertEquals(actualProjectName, expectedProjectName);
			passedStep("Both Application name: "+actualAppName+" and Project name: "+actualProjectName+" are displayed as expected");
			
		}
		}catch (Exception e) {
			e.printStackTrace();
			failedStep(e.getMessage());
		}
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description This method is used to verify the release version of the application
	 */
	public void verifyReleaseVersion() {
		try {
			if(userProfile().isDisplayed()) {
				String releaseVersion = getElementText(getReleaseVersion());
				Assert.assertEquals(releaseVersion, loadProperties().getProperty("ReleaseVersion"));
			}
		}catch (Exception e) {
			e.printStackTrace();
			failedStep(e.getMessage());
		}
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description This method used to logout from the application
	 */
	public void logout() {
		try {
			driver.navigate().refresh();
			elementClick(userAccountDropdownButton());
			if(logOutButton().isDisplayed()) {
				elementClick(logOutButton());
				waitUntilElementVisibility(SignInHeaderName);
				Assert.assertEquals(SignInNameIsDisplayed(), true);		
				passedStep("Logged out from the application successfully");
			}else {
				failedStep("User profile dropdown is not displayed");
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			failedStep(e.getMessage());
		}
	}

	/**
	 * Enter email for signup
	 * 
	 * @param email signup email
	 * @throws Exception
	 */
	public void enterSignUpEmail(String email) throws Exception {
		elementSendKeys(signUpEmail(), email);
		elementClick(signUpEmail());
	}

	/**
	 * Enter signup firstname
	 * 
	 * @param email signup firstname
	 * @throws Exception
	 */
	public void enterSignUpFirstName(String firstName) throws Exception {
		elementSendKeys(signUpFirstName(), firstName);
		elementClick(signUpFirstName());
	}

	/**
	 * Enter signup lastname
	 * 
	 * @param email signup lastname
	 * @throws Exception
	 */
	public void enterSignUpLastName(String lastName) throws Exception {
		elementSendKeys(signUpLastName(), lastName);
		elementClick(signUpLastName());
	}

	/**
	 * Enter signup password
	 * 
	 * @param email signup password
	 * @throws Exception
	 */
	public void enterSignUpPassword(String password) throws Exception {
		elementSendKeys(signUpPassword(), password);
		elementClick(signUpPassword());
	}

	/**
	 * Enter signup confirm password
	 * 
	 * @param email signup confirm password
	 * @throws Exception
	 */
	public void enterSignUpConfirmPassword(String confirmPassword) throws Exception {
		elementSendKeys(signUpConfirmPassword(), confirmPassword);
		elementClick(signUpConfirmPassword());
	}

	/**
	 * click signup confirmation checkbox
	 * 	
	 * @throws Exception
	 */
	public void clickSignUpConfirmationCheckbox() throws Exception {
		elementClick(signUpConfirmationCheckbox());
	}

	/**
	 * click signup
	 * 	
	 * @throws Exception
	 */
	public void clickSignUpButton() throws Exception {
		elementClick(signUpButton());
	}

	/**
	 * get signup success message
	 *  
	 * @throws Exception
	 */	public String getSignUpMessage() throws Exception {           		
		waitUntilElementVisibility(signUpMessage());	
		return signUpMessage().getText();              
	}

	/**
	 * click click here link
	 * 	
	 * @throws Exception
	 */
	public void clickClickHereLink() throws Exception {
		waitUntilElementVisibility(signUpClickHereLink());	
		elementClick(signUpClickHereLink());
	}

	/**
	 * get signin success message
	 *  
	 * @throws Exception
	 */	public String getSignInMessage() throws Exception {           		
		waitUntilElementVisibility(signInMessage());	
		return signInMessage().getText();              
	}

	/**
	 * get signin email verification message
	 *  
	 * @throws Exception
	 */	public String getSignInEmailVerificationMessage() throws Exception {           		
		waitUntilElementVisibility(signInEmailVerificationMessage());	
		return signInEmailVerificationMessage().getText();              
	}

	

	public WebElement mailinatorInboxField(){
		return driver.findElement(By.xpath("//input[@id='inbox_field']"));
	}

	public WebElement mailinatorInboxGoButton(){
		return driver.findElement(By.xpath("//*[@id='inbox_pane']//button[contains(text(),'GO')]"));
	}

		
	public WebElement getMailinatorInboxEmail(String from){
		return driver.findElement(By.xpath("//div[@class='os-padding']//table[@class='table-striped jambo_table']//td[2][contains(text(),"+from+")]"));
	}

	public WebElement mailinatorEmailBodyIframe(){
		return driver.findElement(By.xpath("//iframe[@id='html_msg_body']"));
	}

	public WebElement mailinatorVerifyEmailButton(){
		return driver.findElement(By.xpath("//a[@title='Verify Email']"));
	}

	

	public void verifyEmail (String url, String email,String theme)  throws Exception {
		driver.navigate().to(url);
		waitUntilElementVisibility(mailinatorInboxField());
		elementClear(mailinatorInboxField());
		elementSendKeys(mailinatorInboxField(),email);
		elementClick(mailinatorInboxGoButton());
		elementClick(getMailinatorInboxEmail(theme));
		Thread.sleep(10000);
		switchToMailinatorIFrame();
		elementClick(mailinatorVerifyEmailButton());
		
	}

	/**
	 * switch to iframe
	 *  
	 * @throws Exception
	 */	public void switchToMailinatorIFrame() throws Exception {		
        // driver.switchTo().frame(iframe());
        int size = driver.findElements(By.tagName("iframe")).size();	
        System.out.println("size "+size);
        driver.switchTo().defaultContent();
        driver.switchTo().frame(mailinatorEmailBodyIframe());
        // System.out.println(driver.getPageSource());
	}

}
