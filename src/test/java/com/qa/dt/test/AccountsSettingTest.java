package com.qa.dt.test;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.Calendar;
import java.util.Properties;

import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.qa.dt.base.BaseClass;
import com.qa.dt.page.AccountSettingsPage;
import com.qa.dt.page.AdminHomePage;
import com.qa.dt.page.DashboardPage;
import com.qa.dt.page.DeleteUserPage;
import com.qa.dt.page.InvitationsPage;
import com.qa.dt.page.LoginPage;
import com.qa.dt.page.MyApplicationsPage;
import com.qa.dt.page.PasswordPage;
import com.qa.dt.page.ProjectSelection;
import com.qa.dt.page.UserInformationPage;
import com.qa.dt.util.ExtentManager;

@Listeners(com.qa.dt.util.ListenerClass.class)
public class AccountsSettingTest extends LoginintoTheApplicationTest {
	AccountSettingsPage accountSettingsPage;
	BaseClass baseClass;
	LoginPage loginPage;
	AdminHomePage adminHomePage;
	DashboardPage dashboardPage;
	UserInformationPage userInformationPage;
	PasswordPage passwordPage;
	DeleteUserPage deleteUserPage;
	InvitationsPage invitationsPage;
	MyApplicationsPage myApplicationsPage;
	
	/**
	 * @author Kesavan N
	 * @description  Verify Dashboard page in New Account Setting * 
	 * @throws Exception
	 */  

	@Test(priority = 1)
	public void verifyDashboardPage() throws Exception {    	
    	loginPage = new LoginPage();
    	accountSettingsPage= new AccountSettingsPage();   
		adminHomePage = new AdminHomePage(); 
		dashboardPage = new DashboardPage();	
		
		Properties loadProperties = loadProperties();
		System.out.println(loadProperties.getProperty("RfFirstName"));
		
    	ExtentManager.test.log(Status.INFO, "TC_Description - Verify clicking Account Settings link in Admin Home page is open account setting dashboard page and verify the dashboard page");	
		adminHomePage.clickLeftNavigation();	    	
		adminHomePage.clickAccountSettings();
		switchOrCloseTabs(1, "switch");

		// verify Dashboard page
		// verify page title, greetings and name
		ExtentManager.test.log(Status.INFO, "TC_Description - Verify Page Title, Greetings and Name");
		if(loadProperties.getProperty("mailinatorUrl").equals("Twinit")){
			Assert.assertEquals("Invicara - Account setting", driver.getTitle());
		} else if(loadProperties.getProperty("mailinatorUrl").equals("Mirrana")){
			Assert.assertEquals("Mirrana - Account setting", driver.getTitle());
		}
		System.out.println("getGreetings "+dashboardPage.getGreetings());
		Assert.assertEquals(decodePeriod(Calendar.getInstance())+",", dashboardPage.getGreetings());

		System.out.println("getGreetingName "+dashboardPage.getGreetingName());
		Assert.assertEquals(loadProperties.getProperty("RfFirstName")+"!", dashboardPage.getGreetingName());

		System.out.println("getInvitationsCount "+ dashboardPage.getInvitationsCount());
		System.out.println("getMyApplicationsCount "+ dashboardPage.getMyApplicationsCount());

		// verify Profile details links
		ExtentManager.test.log(Status.INFO, "TC_Description - Verify Profile details links");
		Assert.assertTrue(dashboardPage.userInformationLinkIsDisplayed());
		Assert.assertTrue(dashboardPage.passwordLinkIsDisplayed());
		Assert.assertTrue(dashboardPage.deleteAccountLinkIsDisplayed());

		// verify Authenticator details links
		ExtentManager.test.log(Status.INFO, "TC_Description - Verify Authenitcator details links");	
		if(loadProperties.getProperty("mailinatorUrl").equals("Twinit")){
			Assert.assertTrue(dashboardPage.authenticatorLinkIsDisplayed());
			Assert.assertTrue(dashboardPage.userAccessKeyLinkIsDisplayed());
		} else if(loadProperties.getProperty("mailinatorUrl").equals("Mirrana")){
			Assert.assertTrue(dashboardPage.mirranaAuthenticatorLinkIsDisplayed());
			Assert.assertTrue(dashboardPage.mirranaUserAccessKeyLinkIsDisplayed());
		}	
		

		// verify Applications & invitations details links
		ExtentManager.test.log(Status.INFO, "TC_Description - Verify Applications & invitations details links");
		Assert.assertTrue(dashboardPage.myApplicationsLinkIsDisplayed());
		Assert.assertTrue(dashboardPage.invitationsLinkIsDisplayed());		
		
		// click logout
		dashboardPage.clickUserInitialsLogo();
		dashboardPage.logout();				
	}

	/**
	 * @author Kesavan N
	 * @description  Verify User Information Page in New Account Setting * 
	 * @throws Exception
	 */

	@Test(priority = 2)
	public void verifyUserInformationPage() throws Exception {    	
    	loginPage = new LoginPage();
    	accountSettingsPage= new AccountSettingsPage();   
		adminHomePage = new AdminHomePage(); 
		dashboardPage = new DashboardPage();	
		userInformationPage = new UserInformationPage();
		
		Properties loadProperties = loadProperties();		
		
    	ExtentManager.test.log(Status.INFO, "TC_Description - Verify user information page");	
		adminHomePage.clickLeftNavigation();	    	
		adminHomePage.clickAccountSettings();
		switchOrCloseTabs(1, "switch");
		
		dashboardPage.clickUserInformationLink();

		userInformationPage.switchToFrame();

		System.out.println("userInformationPage.getEmail() "+userInformationPage.getEmail());
		
		Assert.assertEquals(userInformationPage.getEmail(),loadProperties.getProperty("Rfusername"));
		Assert.assertEquals(userInformationPage.getFirstName(),loadProperties.getProperty("RfFirstName"));
		Assert.assertEquals(userInformationPage.getLastName(),loadProperties.getProperty("RfLastName"));

		// . verify invalid email
		ExtentManager.test.log(Status.INFO, "TC_Description - verify error message for invalid email");
		userInformationPage.setEmail("test");
		userInformationPage.clickSaveButton();

		System.out.println("message "+userInformationPage.getMessage());
		Assert.assertEquals("Invalid email address.", userInformationPage.getMessage());
		userInformationPage.clickCancelButton();		
		
		driver.switchTo().defaultContent();
		userInformationPage.clickGoBack();

		dashboardPage.clickUserInformationLink();
		userInformationPage.switchToFrame();

		// 2. verify empty email
		ExtentManager.test.log(Status.INFO, "TC_Description - verify error message for empty email");
		userInformationPage.setEmail("");
		userInformationPage.clickSaveButton();

		System.out.println("message "+userInformationPage.getMessage());
		Assert.assertEquals("Please specify email.\n" + 
						"Please specify username.", userInformationPage.getMessage());
		userInformationPage.clickCancelButton();	
		Thread.sleep(10000);
	

		// 3. verify existing email	
		ExtentManager.test.log(Status.INFO, "TC_Description - verify error message for existing email");	
		userInformationPage.setEmail(loadProperties.getProperty("DbmAdminUsername"));
		userInformationPage.clickSaveButton();

		System.out.println("message "+userInformationPage.getMessage());
		Assert.assertEquals("Email already exists.\n" + 
						"Username already exists.\n" + 
						"Username already exists.", userInformationPage.getMessage());
		userInformationPage.clickCancelButton();
		Thread.sleep(10000);
		
		
		driver.switchTo().defaultContent();
		userInformationPage.clickGoBack();
		driver.navigate().refresh();

		dashboardPage.clickUserInformationLink();
		userInformationPage.switchToFrame();

		// 4. verify empty  firstname
		ExtentManager.test.log(Status.INFO, "TC_Description - verify error message for empty firstname");
		userInformationPage.setFirstName("");
		userInformationPage.clickSaveButton();

		System.out.println("message "+userInformationPage.getMessage());
		Assert.assertEquals("Please specify first name.", userInformationPage.getMessage());
		userInformationPage.clickCancelButton();
		Thread.sleep(5000);

		driver.switchTo().defaultContent();
		userInformationPage.clickGoBack();
		driver.navigate().refresh();

		dashboardPage.clickUserInformationLink();
		userInformationPage.switchToFrame();

		// 5. verify empty lastname
		ExtentManager.test.log(Status.INFO, "TC_Description - verify error message for empty lastname");
		userInformationPage.setLastName("");
		userInformationPage.clickSaveButton();

		System.out.println("message "+userInformationPage.getMessage());
		Assert.assertEquals("Please specify last name.", userInformationPage.getMessage());
		userInformationPage.clickCancelButton();

		// 6. verify update user information 	
		ExtentManager.test.log(Status.INFO, "TC_Description - verify update user information");		
		userInformationPage.setFirstName("Kesavan");
		userInformationPage.setLastName("N");
		userInformationPage.clickSaveButton();

		System.out.println("message "+userInformationPage.getMessage());
		Assert.assertEquals("Your account has been updated.", userInformationPage.getMessage());		
		
		driver.switchTo().defaultContent();
		userInformationPage.clickGoBack();
		driver.navigate().refresh();
		
		System.out.println("initials "+ loadProperties.getProperty("RfFirstName").substring(0, 1).toUpperCase()+loadProperties.getProperty("RfLastName").substring(0, 1).toUpperCase());

		Assert.assertEquals(loadProperties.getProperty("RfFirstName").substring(0, 1).toUpperCase()+loadProperties.getProperty("RfLastName").substring(0, 1).toUpperCase(), dashboardPage.getUserInitialsLogoText());

		// click logout
		dashboardPage.clickUserInitialsLogo();
		dashboardPage.logout();				
	}

	/**
	 * @author Kesavan N
	 * @description  Verify Password Page in New Account Setting * 
	 * @throws Exception
	 */
	@Test(priority = 3)
	public void verifyPasswordPage() throws Exception {    	
    	loginPage = new LoginPage();
    	accountSettingsPage= new AccountSettingsPage();   
		adminHomePage = new AdminHomePage(); 
		dashboardPage = new DashboardPage();	
		userInformationPage = new UserInformationPage();
		passwordPage = new PasswordPage();
		
		Properties loadProperties = loadProperties();		
		
    	ExtentManager.test.log(Status.INFO, "TC_Description - Verify Password page");	
		adminHomePage.clickLeftNavigation();	    	
		adminHomePage.clickAccountSettings();
		switchOrCloseTabs(1, "switch");
		
		dashboardPage.clickPasswordLink();

		passwordPage.switchToFrame();

		// 1. verify empty current password
		ExtentManager.test.log(Status.INFO, "TC_Description - verify error message for empty current password");
		passwordPage.setCurrentPassword("");
		Thread.sleep(5000);
		passwordPage.clickSavePasswordButton();

		System.out.println("message "+passwordPage.getMessage());
		Assert.assertEquals("Please specify password.", passwordPage.getMessage());	
			

		driver.switchTo().defaultContent();
		// if(loadProperties.getProperty("mailinatorUrl").equals("Twinit")){
		// 	passwordPage.clickGoBack();		
		// } else if(loadProperties.getProperty("mailinatorUrl").equals("Mirrana")){
		// 	driver.back();
		// }	
		Thread.sleep(10000);		
		passwordPage.clickGoBack();		
		dashboardPage.clickPasswordLink();
		passwordPage.switchToFrame();
		
		// 2. verify invalid current password
		ExtentManager.test.log(Status.INFO, "TC_Description - verify error message for invalid current password");
		passwordPage.setCurrentPassword("invalid");
		passwordPage.clickSavePasswordButton();

		System.out.println("message "+passwordPage.getMessage());
		Assert.assertEquals("Invalid existing password.", passwordPage.getMessage());	
		Thread.sleep(10000);

		// 3. verify empty new password
		ExtentManager.test.log(Status.INFO, "TC_Description - verify error message for empty new password");
		passwordPage.setCurrentPassword(loadProperties.getProperty("RfPassword"));
		passwordPage.setNewPassword("");
		passwordPage.clickSavePasswordButton();

		System.out.println("message "+passwordPage.getMessage());
		Assert.assertEquals("Please specify password.", passwordPage.getMessage());	
		Thread.sleep(10000);

		// 4. verify empty confirm password
		ExtentManager.test.log(Status.INFO, "TC_Description - verify error message for empty confirm password");
		passwordPage.setCurrentPassword(loadProperties.getProperty("RfPassword"));
		passwordPage.setNewPassword("123");
		passwordPage.setConfirmPassword("");
		passwordPage.clickSavePasswordButton();

		System.out.println("message "+passwordPage.getMessage());
		Assert.assertEquals("Password confirmation doesn't match.", passwordPage.getMessage());	
		Thread.sleep(10000);

		// 5. verify invalid new password with only numbers
		ExtentManager.test.log(Status.INFO, "TC_Description - verify error message for invalid new password with only numbers");
		passwordPage.setCurrentPassword(loadProperties.getProperty("RfPassword"));
		passwordPage.setNewPassword("123");
		passwordPage.setConfirmPassword("123");
		passwordPage.clickSavePasswordButton();

		System.out.println("message "+passwordPage.getMessage());
		Assert.assertEquals("Invalid password: must contain at least 1 special characters.", passwordPage.getMessage());	
		Thread.sleep(10000);

		// 6. verify invalid new password with only alphabets
		ExtentManager.test.log(Status.INFO, "TC_Description - verify error message for invalid new password with only alphabets");
		passwordPage.setCurrentPassword(loadProperties.getProperty("RfPassword"));
		passwordPage.setNewPassword("abc");
		passwordPage.setConfirmPassword("abc");
		passwordPage.clickSavePasswordButton();

		System.out.println("message "+passwordPage.getMessage());
		Assert.assertEquals("Invalid password: must contain at least 1 special characters.", passwordPage.getMessage());	
		Thread.sleep(10000);

		// 7. verify invalid new password with only alphabets and special characters
		ExtentManager.test.log(Status.INFO, "TC_Description - verify error message for invalid new password with only alphabets and special characters");
		passwordPage.setCurrentPassword(loadProperties.getProperty("RfPassword"));
		passwordPage.setNewPassword("abcABC@");
		passwordPage.setConfirmPassword("abcABC@");
		passwordPage.clickSavePasswordButton();

		System.out.println("message "+passwordPage.getMessage());
		Assert.assertEquals("Invalid password: minimum length 8.", passwordPage.getMessage());	
		Thread.sleep(10000);

		// 8. verify invalid new password with only numbers and special characters
		ExtentManager.test.log(Status.INFO, "TC_Description - verify error message for invalid new password with only numbers and special characters");
		passwordPage.setCurrentPassword(loadProperties.getProperty("RfPassword"));
		passwordPage.setNewPassword("123#");
		passwordPage.setConfirmPassword("123#");
		passwordPage.clickSavePasswordButton();

		System.out.println("message "+passwordPage.getMessage());
		Assert.assertEquals("Invalid password: must contain at least 1 upper case characters.", passwordPage.getMessage());	
		Thread.sleep(10000);

		// 9. verify change password
		ExtentManager.test.log(Status.INFO, "TC_Description - verify message for successful change password");
		passwordPage.setCurrentPassword(loadProperties.getProperty("RfPassword"));
		passwordPage.setNewPassword(loadProperties.getProperty("RfNewPassword"));
		passwordPage.setConfirmPassword(loadProperties.getProperty("RfConfirmPassword"));
		passwordPage.clickSavePasswordButton();

		System.out.println("message "+passwordPage.getMessage());
		Assert.assertEquals("Your password has been updated.", passwordPage.getMessage());	
		Thread.sleep(10000);
		
		driver.switchTo().defaultContent();
		passwordPage.clickGoBack();		

		// click logout
		dashboardPage.clickUserInitialsLogo();
		dashboardPage.clickLogout();	
		Thread.sleep(10000);		
		
		// log in with changed passsword
		loginPage.enterUserName(loadProperties().getProperty("Rfusername"));
		Thread.sleep(10000);
		loginPage.enterPassword(loadProperties().getProperty("RfConfirmPassword"));
		ExtentManager.test.log(Status.PASS, "Logged in the Admin application successfully by "+loadProperties().getProperty("Rfusername")+"with updated password");	
	
		if(loadProperties.getProperty("mailinatorUrl").equals("Twinit")){
			Assert.assertEquals("Invicara - Account setting", driver.getTitle());
		} else if(loadProperties.getProperty("mailinatorUrl").equals("Mirrana")){
			Assert.assertEquals("Mirrana - Account setting", driver.getTitle());
		}
		System.out.println("getGreetings "+dashboardPage.getGreetings());
		Assert.assertEquals(decodePeriod(Calendar.getInstance())+",", dashboardPage.getGreetings());

		System.out.println("getGreetingName "+dashboardPage.getGreetingName());
		Assert.assertEquals(loadProperties.getProperty("RfFirstName")+"!", dashboardPage.getGreetingName());
				
		dashboardPage.clickPasswordLink();

		passwordPage.switchToFrame();

		// reset to existing password
		ExtentManager.test.log(Status.INFO, "TC_Description - verify message for successful change password");
		passwordPage.setCurrentPassword(loadProperties.getProperty("RfConfirmPassword"));
		passwordPage.setNewPassword(loadProperties.getProperty("RfPassword"));
		passwordPage.setConfirmPassword(loadProperties.getProperty("RfPassword"));
		passwordPage.clickSavePasswordButton();

		System.out.println("message "+passwordPage.getMessage());
		Assert.assertEquals("Your password has been updated.", passwordPage.getMessage());	
		Thread.sleep(10000);

		driver.switchTo().defaultContent();
		passwordPage.clickGoBack();		

		// click logout
		dashboardPage.clickUserInitialsLogo();
		dashboardPage.clickLogout();			
		
	}

	/**
	 * @author Kesavan N
	 * @description  Verify Delete Account in New Account Setting * 
	 * @throws Exception
	 */
	@Test(priority = 4)
	public void verifyDeleteAccountPage() throws Exception {    	
    	loginPage = new LoginPage();
    	accountSettingsPage= new AccountSettingsPage();   
		adminHomePage = new AdminHomePage(); 
		dashboardPage = new DashboardPage();	
		userInformationPage = new UserInformationPage();
		passwordPage = new PasswordPage();
		deleteUserPage = new DeleteUserPage();
		
		Properties loadProperties = loadProperties();	
		
		// step 1 - create user
		String email = generateRandomEmail();
		String firstName = "Test";
		String lastName = "User";
		String password = loadProperties.getProperty("RfPassword");
		
		System.out.println("email "+email);
    	ExtentManager.test.log(Status.INFO, "TC_Description - Create New User and verify delete account");	
		adminHomePage.clickLeftNavigation();	
		adminHomePage.clickSignout();
		
		loginPage.clickSignUp();


		loginPage.enterSignUpEmail(email);
		loginPage.enterSignUpFirstName(firstName);
		loginPage.enterSignUpLastName(lastName);
		loginPage.enterSignUpPassword(password);
		loginPage.enterSignUpConfirmPassword(password);
		loginPage.clickSignUpConfirmationCheckbox();		
		loginPage.clickSignUpButton();
		Thread.sleep(10000);

		System.out.println("test "+loginPage.getSignUpMessage());
		Assert.assertEquals("Your account has been successfully created. Please click here to login",loginPage.getSignUpMessage());
		loginPage.clickClickHereLink();
		Thread.sleep(10000);

		// step 2: verify email if staging environments
		if(loadProperties.getProperty("RfUrl").contains("staging")){
			loginPage.enterPassword(password);
			System.out.println("test "+loginPage.getSignInEmailVerificationMessage());
			Assert.assertEquals("You need to verify your email address to activate your account.", loginPage.getSignInEmailVerificationMessage());
			loginPage.verifyEmail(loadProperties.getProperty("mailinatorUrl"), 
									email, 
									loadProperties.getProperty("Theme"));
			switchOrCloseTabs(1, "switch");						
			adminHomePage.clickLeftNavigation();
			adminHomePage.clickAccountSettings();
			switchOrCloseTabs(2, "switch");						
		} else {
			loginPage.enterPassword(password);
			adminHomePage.clickLeftNavigation();
			adminHomePage.clickAccountSettings();
			switchOrCloseTabs(1, "switch");
		}		
	
		
		// step 3 - delete user and verify Yes, Delete it button
		dashboardPage.clickDeleteAccountLink();

		accountSettingsPage.clickPasswordOption();
		accountSettingsPage.clickDeleteAccountOption();

		driver.switchTo().defaultContent();	
		Thread.sleep(10000);	
		
		ExtentManager.test.log(Status.INFO, "Verify Yes, Delete it button is disabled when DELETE text is not entered");	
		deleteUserPage.clickDeleteAccountButton();
		Assert.assertFalse(deleteUserPage.yesDeleteItButton().isEnabled());
		deleteUserPage.setConfirmDeleteTextbox("This");
		Assert.assertFalse(deleteUserPage.yesDeleteItButton().isEnabled());
		deleteUserPage.clickCancelButton();
		
		ExtentManager.test.log(Status.INFO, "Verify Yes, Delete it button is enabled when DELETE text is  entered");	
		deleteUserPage.clickDeleteAccountButton();
		deleteUserPage.setConfirmDeleteTextbox("DELETE");
		Assert.assertTrue(deleteUserPage.yesDeleteItButton().isEnabled());
		deleteUserPage.clickYesDeleteItButton();
		Thread.sleep(10000);

		// step 4 - verify message is displayed if deleted user email is entered
		loginPage.enterUserName(email);
		System.out.println("test "+loginPage.getSignInMessage());
		Assert.assertEquals("This email address is not registered. Would you like to sign up instead?", loginPage.getSignInMessage());
		Thread.sleep(5000);		
		
		
	}

	/**
	 * @author Kesavan N
	 * @description  Verify Reject Invitations and my applications in New Account Setting * 
	 * @throws Exception
	 */
	@Test(priority = 5)
	public void verifyRejectInvitationsAndMyApplications() throws Exception {    	
    	loginPage = new LoginPage();
    	accountSettingsPage= new AccountSettingsPage();   
		adminHomePage = new AdminHomePage(); 
		dashboardPage = new DashboardPage();	
		userInformationPage = new UserInformationPage();
		passwordPage = new PasswordPage();
		deleteUserPage = new DeleteUserPage();
		invitationsPage = new InvitationsPage();
		myApplicationsPage = new MyApplicationsPage();
		
		Properties loadProperties = loadProperties();	
		
		// step 1 - create user
		String email = generateRandomEmail();
		String firstName = "Test";
		String lastName = "User";
		String password = loadProperties.getProperty("RfPassword");
		
		System.out.println("email "+email);
    	ExtentManager.test.log(Status.INFO, "TC_Description - Create New User and verify invitations");	
		adminHomePage.clickLeftNavigation();	
		adminHomePage.clickSignout();

		Thread.sleep(5000);
		loginPage.clickSignUp();

		loginPage.enterSignUpEmail(email);
		loginPage.enterSignUpFirstName(firstName);
		loginPage.enterSignUpLastName(lastName);
		loginPage.enterSignUpPassword(password);
		loginPage.enterSignUpConfirmPassword(password);
		loginPage.clickSignUpConfirmationCheckbox();		
		loginPage.clickSignUpButton();
		Thread.sleep(10000);

		System.out.println("test "+loginPage.getSignUpMessage());
		Assert.assertEquals("Your account has been successfully created. Please click here to login",loginPage.getSignUpMessage());
		loginPage.clickClickHereLink();
		Thread.sleep(10000);

		// step 2 - verify email if staging environments
		if(loadProperties.getProperty("RfUrl").contains("staging")){
			loginPage.enterPassword(password);
			System.out.println("test "+loginPage.getSignInEmailVerificationMessage());
			Assert.assertEquals("You need to verify your email address to activate your account.", loginPage.getSignInEmailVerificationMessage());
			loginPage.verifyEmail(loadProperties.getProperty("mailinatorUrl"), 
									email, 
									loadProperties.getProperty("Theme"));
			switchOrCloseTabs(1, "switch");						
			adminHomePage.clickLeftNavigation();
			adminHomePage.clickAccountSettings();
			switchOrCloseTabs(2, "switch");						
		} else {
			loginPage.enterPassword(password);
			adminHomePage.clickLeftNavigation();
			adminHomePage.clickAccountSettings();
			switchOrCloseTabs(1, "switch");
		}

		// step 3 - verify page title, greetings and name
		ExtentManager.test.log(Status.INFO, "TC_Description - Verify Page Title, Greetings and Name");
		if(loadProperties.getProperty("mailinatorUrl").equals("Twinit")){
			Assert.assertEquals("Invicara - Account setting", driver.getTitle());
		} else if(loadProperties.getProperty("mailinatorUrl").equals("Mirrana")){
			Assert.assertEquals("Mirrana - Account setting", driver.getTitle());
		}
		
		System.out.println("getGreetings "+dashboardPage.getGreetings());
		Assert.assertEquals(decodePeriod(Calendar.getInstance())+",", dashboardPage.getGreetings());

		System.out.println("getGreetingName "+dashboardPage.getGreetingName());
		Assert.assertEquals(firstName+"!", dashboardPage.getGreetingName());

		// step 4 - verify invitation and application count before reject invitation
		System.out.println("getInvitationsCount "+ dashboardPage.getInvitationsCount());
		System.out.println("getMyApplicationsCount "+ dashboardPage.getMyApplicationsCount());
		Assert.assertEquals("0", dashboardPage.getInvitationsCount());
		Assert.assertEquals("00", dashboardPage.getMyApplicationsCount());

		// step 5 - verify my application page message
		dashboardPage.clickMyApplicationsLink();
		System.out.println("test "+myApplicationsPage.getNoApplicationsFoundMessage());
		Assert.assertEquals("No Applications Found", myApplicationsPage.getNoApplicationsFoundMessage());
		myApplicationsPage.clickGoBack();

		// step 6 - verify invitations page message
		dashboardPage.clickInvitationsLink();
		System.out.println("test "+invitationsPage.getNoInvitesFoundMessage());
		Assert.assertEquals("No Invites Found", invitationsPage.getNoInvitesFoundMessage());

		switchOrCloseTabs(0, "switch");
		// switchOrCloseTabs(1, "close");
		driver.navigate().refresh();
		adminHomePage.clickLeftNavigation();
		Thread.sleep(5000);	
		adminHomePage.clickSignout();

		// step 7 - login on automation user and send invitation to automation application
		loginPage.LoginTest(loadProperties.getProperty("AutomationUsername"), loadProperties.getProperty("AutomationPassword"));
		

		adminHomePage.clickLeftNavigation();	
		Thread.sleep(5000);		
		adminHomePage.clickApplicationsSettings();
		adminHomePage.selectAutomationApplication();
		Thread.sleep(5000);
		adminHomePage.clickThreeDotMenuOption();		
		adminHomePage.selectApplicationUserMenuOptions();
		Thread.sleep(300000);
		adminHomePage.clickThreeDotMenuOption();		
		adminHomePage.selectApplicationInviteNewUserMenuOptions();
		adminHomePage.inviteNewUser(email, "Admin");
		Thread.sleep(15000);
		waitUntilAlertPresent();
		System.out.println("alert text "+getTextInAlert());
		Assert.assertEquals("Invitations have been sent", getTextInAlert());
		acceptAlert();
		driver.navigate().refresh();
		Thread.sleep(5000);
		adminHomePage.clickLeftNavigationOnContent();
		adminHomePage.clickSignout();
		Thread.sleep(5000);

		// step 8 - login with new user and reject invitation
		loginPage.LoginTest(email, password);
		adminHomePage.clickLeftNavigation();
		adminHomePage.clickAccountSettings();

		switchOrCloseTabs(2, "switch");
		dashboardPage.clickInvitationsLink();
		Thread.sleep(10000);

		// invitationsPage.switchToFrame();
		// Thread.sleep(5000);
		invitationsPage.clickRejectInvite("automation");
		System.out.println("test "+invitationsPage.getMessage());
		Assert.assertEquals("Success\n" + 
						"Invite rejected successfully", invitationsPage.getMessage());
		Thread.sleep(10000);		
		driver.navigate().refresh();		
		invitationsPage.clickGoBack();		

		// step 9 - verify application count after rejecting invitaion
		Assert.assertEquals("00", dashboardPage.getMyApplicationsCount());		
		dashboardPage.clickUserInitialsLogo();		
		dashboardPage.clickLogout();

	}

	/**
	 * @author Kesavan N
	 * @description  Verify Accept Invitations and My Applications in New Account Setting * 
	 * @throws Exception
	 */
	@Test(priority = 6)
	public void verifyAcceptInvitationsAndMyApplilcation() throws Exception {    	
    	loginPage = new LoginPage();
    	accountSettingsPage= new AccountSettingsPage();   
		adminHomePage = new AdminHomePage(); 
		dashboardPage = new DashboardPage();	
		userInformationPage = new UserInformationPage();
		passwordPage = new PasswordPage();
		deleteUserPage = new DeleteUserPage();
		invitationsPage = new InvitationsPage();
		myApplicationsPage = new MyApplicationsPage();
		
		Properties loadProperties = loadProperties();	
		
		// step 1 - create user
		String email = generateRandomEmail();
		String firstName = "Test";
		String lastName = "User";
		String password = loadProperties.getProperty("RfPassword");
		
		System.out.println("email "+email);
    	ExtentManager.test.log(Status.INFO, "TC_Description - Create New User and verify invitations");	
		adminHomePage.clickLeftNavigation();	
		adminHomePage.clickSignout();

		Thread.sleep(5000);
		loginPage.clickSignUp();

		loginPage.enterSignUpEmail(email);
		loginPage.enterSignUpFirstName(firstName);
		loginPage.enterSignUpLastName(lastName);
		loginPage.enterSignUpPassword(password);
		loginPage.enterSignUpConfirmPassword(password);
		loginPage.clickSignUpConfirmationCheckbox();		
		loginPage.clickSignUpButton();
		Thread.sleep(10000);

		System.out.println("test "+loginPage.getSignUpMessage());
		Assert.assertEquals("Your account has been successfully created. Please click here to login",loginPage.getSignUpMessage());
		loginPage.clickClickHereLink();
		Thread.sleep(10000);

		// step 2 - verify email if staging environments
		if(loadProperties.getProperty("RfUrl").contains("staging")){
			loginPage.enterPassword(password);
			System.out.println("test "+loginPage.getSignInEmailVerificationMessage());
			Assert.assertEquals("You need to verify your email address to activate your account.", loginPage.getSignInEmailVerificationMessage());
			loginPage.verifyEmail(loadProperties.getProperty("mailinatorUrl"), 
									email, 
									loadProperties.getProperty("Theme"));
			switchOrCloseTabs(1, "switch");						
			adminHomePage.clickLeftNavigation();
			adminHomePage.clickAccountSettings();
			switchOrCloseTabs(2, "switch");						
		} else {
			loginPage.enterPassword(password);
			adminHomePage.clickLeftNavigation();
			adminHomePage.clickAccountSettings();
			switchOrCloseTabs(1, "switch");
		}

		// step 3 - verify page title, greetings and name
		ExtentManager.test.log(Status.INFO, "TC_Description - Verify Page Title, Greetings and Name");
		if(loadProperties.getProperty("mailinatorUrl").equals("Twinit")){
			Assert.assertEquals("Invicara - Account setting", driver.getTitle());
		} else if(loadProperties.getProperty("mailinatorUrl").equals("Mirrana")){
			Assert.assertEquals("Mirrana - Account setting", driver.getTitle());
		}
		
		System.out.println("getGreetings "+dashboardPage.getGreetings());
		Assert.assertEquals(decodePeriod(Calendar.getInstance())+",", dashboardPage.getGreetings());

		System.out.println("getGreetingName "+dashboardPage.getGreetingName());
		Assert.assertEquals(firstName+"!", dashboardPage.getGreetingName());

		// step 4 - verify invitations and my application count
		System.out.println("getInvitationsCount "+ dashboardPage.getInvitationsCount());
		System.out.println("getMyApplicationsCount "+ dashboardPage.getMyApplicationsCount());
		Assert.assertEquals("0", dashboardPage.getInvitationsCount());
		Assert.assertEquals("00", dashboardPage.getMyApplicationsCount());
		dashboardPage.clickInvitationsLink();
		
		System.out.println("test "+invitationsPage.getNoInvitesFoundMessage());
		Assert.assertEquals("No Invites Found", invitationsPage.getNoInvitesFoundMessage());

		switchOrCloseTabs(0, "switch");
		// switchOrCloseTabs(1, "close");
		driver.navigate().refresh();
		adminHomePage.clickLeftNavigation();
		Thread.sleep(5000);	
		adminHomePage.clickSignout();

		// step 5 - log on as automation user
		loginPage.LoginTest(loadProperties.getProperty("AutomationUsername"), loadProperties.getProperty("AutomationPassword"));		

		adminHomePage.clickLeftNavigation();	
		Thread.sleep(5000);		
		adminHomePage.clickApplicationsSettings();
		adminHomePage.selectAutomationApplication();
		Thread.sleep(5000);
		adminHomePage.clickThreeDotMenuOption();		
		adminHomePage.selectApplicationUserMenuOptions();
		Thread.sleep(300000);
		adminHomePage.clickThreeDotMenuOption();		
		adminHomePage.selectApplicationInviteNewUserMenuOptions();
		adminHomePage.inviteNewUser(email, "Admin");
		Thread.sleep(15000);
		waitUntilAlertPresent();
		System.out.println("alert text "+getTextInAlert());
		Assert.assertEquals("Invitations have been sent", getTextInAlert());
		acceptAlert();
		driver.navigate().refresh();
		Thread.sleep(5000);
		adminHomePage.clickLeftNavigationOnContent();
		adminHomePage.clickSignout();
		Thread.sleep(5000);

		// step 6 - logon as new user and accept inviation
		loginPage.LoginTest(email, password);
		adminHomePage.clickLeftNavigation();
		adminHomePage.clickAccountSettings();

		switchOrCloseTabs(2, "switch");
		Assert.assertEquals("1", dashboardPage.getInvitationsCount());
		dashboardPage.clickInvitationsLink();
		Thread.sleep(10000);

		// invitationsPage.switchToFrame();
		// Thread.sleep(5000);
		invitationsPage.clickAcceptInvite("automation");
		System.out.println("test "+invitationsPage.getMessage());
		Assert.assertEquals("Success\n" + 
						"Invite accepted successfully", invitationsPage.getMessage());
		Thread.sleep(10000);
		driver.navigate().refresh();
		invitationsPage.clickGoBack();		

		// step 7 - verify my application count after invitation is accepted
		Assert.assertEquals("01", dashboardPage.getMyApplicationsCount());

		// step 8 - verify application count and name in my application name
		dashboardPage.clickMyApplicationsLink();
		Thread.sleep(10000);
		System.out.println("test "+myApplicationsPage.getNoOfApplications());
		Assert.assertEquals(1, myApplicationsPage.getNoOfApplications());
		System.out.println("test "+ myApplicationsPage.isApplicationCardTitleExists("Authomation"));
		Assert.assertTrue( myApplicationsPage.isApplicationCardTitleExists("Automation"));

		dashboardPage.clickUserInitialsLogo();			
		dashboardPage.clickLogout();

	}

    @AfterMethod
	public void tearDown() {
		driver.quit();
	}
   
    
    
}

