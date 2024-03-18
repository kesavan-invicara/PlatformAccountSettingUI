package com.qa.dt.page;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.python.modules.thread.thread;
import org.testng.Assert;
import com.aventstack.extentreports.Status;
import com.qa.dt.base.BaseClass;
import com.qa.dt.util.ExtentManager;

public class PassportServicePage extends CommonReusablesPage {
	public PassportServicePage() {
		PageFactory.initElements(driver, this);
	}
//			WebElements

	public WebElement userGroupTab() {
		return driver.findElement(By.xpath("//button[text()='User Groups']"));
	}
	public WebElement WorkspacesTab() {
		return driver.findElement(By.xpath("//button[text()='Workspaces']"));
	}
	public WebElement notificationTemplatesTab() {
		return driver.findElement(By.xpath("//button[text()='Notification Templates']"));
	}
	public WebElement userGroupThreedotMenu(String value) {
		return driver.findElement(By.xpath("//td[text()='"+value+"']/parent::tr/td//button"));
	}
	public WebElement selectUserGroupOption(String value) {
		return driver.findElement(By.xpath("//div[@id='long-menu']//div[contains(@style,'opacity: 1')]//li[text()='"+value+"']"));
	}
	public WebElement viewUserGroupModal() {
		return driver.findElement(By.xpath("//div[text()='View User Group']"));
	}
	public WebElement editWorkspaceModal() {
		return driver.findElement(By.xpath("//div[text()='Edit Workspace']"));
	}
	public WebElement editUsergroupModal() {
		return driver.findElement(By.xpath("//div[text()='View and edit']"));
	}
	public WebElement viewUgFields(String value) {
		return driver.findElement(By.xpath("//label[text()='"+value+"']/parent::div//div/input[@disabled]"));
	}
	public WebElement inputFields(String value) {
		return driver.findElement(By.xpath("//label[text()='"+value+"']/parent::div//div/input"));
	}
	public WebElement saveAndCloseViewUserGrp() {
		return driver.findElement(By.xpath("//button[text()='Send Invite']"));
	}
	public WebElement okButton() {
		return driver.findElement(By.xpath("//button[text()='OK']"));
	}
	
	public WebElement inviteUserGrpSection() {
		return driver.findElement(By.xpath("//div[@class=' user-group-form-invite']"));
	}
	public WebElement inviteUserField() {
		return driver.findElement(By.xpath("(//label[text()='User']/parent::div//input[@placeholder='Type user email or type user name'])[last()]"));
	}
	public WebElement tableHeaders(String value) {
		return driver.findElement(By.xpath("//span[text()='"+value+"']"));
	}	
	public WebElement closeViewUserGrp() {
		return driver.findElement(By.xpath("//button[text()='Close']"));
	}
	public WebElement workspaceName(String value) {
		return driver.findElement(By.xpath("//td[text()='"+value+"']"));
	}
	public WebElement addWsButton() {
		return driver.findElement(By.xpath("//button[text()='Add New Workspace']"));
	}
	public WebElement notificationMsg() {
		return driver.findElement(By.xpath("//div[@id='notistack-snackbar']"));
	}
	
	public WebElement addUgButton() {
		return driver.findElement(By.xpath("//span[text()='Add New User Group']"));
	}
	public WebElement validateSelectElement(String value) {
		return driver.findElement(By.xpath("//span[text()='"+value+"']"));
	}
	public WebElement selectElement(String value) {
		return driver.findElement(By.xpath("//button[text()='"+value+"']"));
	}
	public WebElement removeUser(String user) {
		return driver.findElement(By.xpath("//input[@value='"+user+"']/ancestor::div/label[text()='User']/parent::div/following-sibling::button"));
	}
	public WebElement getTableHeaderValueUg(int value) {
		return driver.findElement(By.xpath("//tbody/tr[1]/td["+value+"]"));
	}
	public WebElement userFullname(String userName) {
		return driver.findElement(By.xpath("//input[@value='"+userName+"']/ancestor::div[@style]/label"));
	}
	
	/**
	 * @author Iyappan.Kasinathan
	 * @description This method is used to perform the view/update/delete action for user group
	 */
	public void userGroupActions(String usergroupName, String action) {
		try {
			
//			elementClick(userGroupThreedotMenu(usergroupName));
			waitUntilElementVisibility(userGroupThreedotMenu(usergroupName));
			waitpause();
			elementClick(userGroupThreedotMenu(usergroupName));
			
			elementClick(selectUserGroupOption(action));
			switch(action) {
			case "View":
				waitUntilElementVisibility(viewUserGroupModal());
				elementDisplayed(viewUserGroupModal(),"View user group modal is displayed","Failed to view the user group modal");
				break;
			case "Edit":				
				waitUntilElementVisibility(editUsergroupModal());
				elementDisplayed(editUsergroupModal(),"View user group modal is displayed","Failed to view the user group modal");
				break;
			case "Delete":	
				waitUntilElementVisibility(deleteItBtn());
				elementClick(deleteItBtn());
				break;			
			}						
			
		} catch (Exception e) {
			e.printStackTrace();
			failedStep(e.getMessage());
		}
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description This method is used to verify the ui validations for user group
	 */
	public void viewUserGroupUIValidations(String ugName,String ugShortName,String ugDescription) {
		try {
			String expectedUgName = getElementAttribute(inputFields("User Group Name"), "value");
			comparetwoText(expectedUgName, ugName);  
			String expectedUgShortName = getElementAttribute(viewUgFields("User Group Short Name"), "value");
			comparetwoText(expectedUgShortName, ugShortName);
			String expectedUgDescription = getElementAttribute(viewUgFields("User Group Description"), "value");
			comparetwoText(expectedUgDescription, ugDescription);
			Assert.assertTrue(elementIsEnabled(closeViewUserGrp()), "Close button is not enabled"); 
			passedStep("All the userfields verified successfully");			
			
		} catch (Exception e) {
			e.printStackTrace();
			failedStep(e.getMessage());
		}
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description This method is used to invite new user
	 */
	public void inviteNewUser(String user) {
		try {
			elementSendKeys(inviteUserField(), user);
			elementClick(saveAndCloseViewUserGrp());
		}catch (Exception e) {
			e.printStackTrace();
			failedStep(e.getMessage());
		}
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description This method is used to verify the ui validations for user group
	 */
	public void editWorkspaceValidations(String ugName,String ugShortName,String ugDescription) {
		try {
			String expectedUgName = getElementAttribute(inputFields("Name"), "value");
			comparetwoText(expectedUgName, ugName);  
			String expectedUgShortName = getElementAttribute(viewUgFields("Short Name (Readonly)"), "value");
			comparetwoText(expectedUgShortName, ugShortName);
			String expectedUgDescription = getElementAttribute(inputFields("Description"), "value");
			comparetwoText(expectedUgDescription, ugDescription);
			Assert.assertTrue(elementIsEnabled(closeViewUserGrp()), "Close button is not enabled"); 
			Assert.assertTrue(elementIsEnabled(saveAndCloseButton()), "OK button is not enabled"); 
			ExtentManager.test.log(Status.INFO, "Ok button and Save and close button enabled as expected"); 
			passedStep("All the userfields verified successfully");			
			
		} catch (Exception e) {
			e.printStackTrace();
			failedStep(e.getMessage());
		}
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description This method is used to perform the view/update/delete action for user group
	 */
	public void passportServiceActions(String name, String action) {
		try {
			elementClick(userGroupThreedotMenu(name));
			elementClick(selectUserGroupOption(action));
			switch(action) {
			case "View":
				waitUntilElementVisibility(viewUserGroupModal());
				elementDisplayed(viewUserGroupModal(),"View user group modal is displayed","Failed to view the user group modal");
				break;
			case "Edit":				
				waitUntilElementVisibility(editWorkspaceModal());
				elementDisplayed(editWorkspaceModal(),"View user group modal is displayed","Failed to view the user group modal");
				break;
			case "Delete":	
				break;			
			}						
			
		} catch (Exception e) {
			e.printStackTrace();
			failedStep(e.getMessage());
		}
	}
	
	public void editNameAndDescription(String name) {
		try {
			clearField(inputFields("Name"));			
			elementSendKeys(inputFields("Name"), name);
			clearField(inputFields("Description"));
			elementSendKeys(inputFields("Description"),name);
			elementClick(saveAndCloseButton());
		} catch (Exception e) {
			e.printStackTrace();
			failedStep(e.getMessage());
		}
	}
	
	public void verifyWorkspacesNameUpdated(String value) {
		try {
			if(workspaceName(value+" updated").isDisplayed()) {
				passedStep("Able to update workspace name and reflected immediately successfully");
			}else {
				failedStep("Failed to update workspace name and reflected immediately successfully");
			}
		}catch (Exception e) {
			e.printStackTrace();
			failedStep(e.getMessage());
		}
	}
	
	public void createNewWorkspace(String label) {
		try {
			elementClick(addWsButton());
			elementSendkeys(inputFields("Name"), label );
			elementSendkeys(inputFields("Short Name (Readonly)"), label);
			elementSendkeys(inputFields("Description"), label);
			elementClick(saveAndCloseButton());
			String notificationMsg = getElementText(notificationMsg());
			comparetwoText(notificationMsg, "Saved");  
			
		}catch (Exception e) {
			e.printStackTrace();
			failedStep(e.getMessage());
		}
	}
	public void verifyWsOrUgCreated(String label,String ugWsName) {
		try {
			waitPause2();
			waitUntilElementVisibility(searchField());
			elementClick(searchField());
			elementSendKeys(searchField(), label);
			waitUntilElementVisibility(workspaceName(label)); 
			waitPause2();
			if (workspaceName(label).isDisplayed()) {
				passedStep(ugWsName +" " + label + " created as expected");
			} else {
				failedStep("Failed to create new "+ugWsName);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			failedStep(e.getMessage());
		}
	}
	public void createNewUserGroup(String label) {
		try {
			elementClick(addUgButton());
			elementSendkeys(inputFields("User Group Name"), label );
			elementSendkeys(inputFields("User Group Description"), label);
			elementSendkeys(inputFields("User Group Short Name"), label);
			elementClick(saveAndCloseViewUserGrp());
			waitUntilElementVisibility(notificationMsg());
			String notificationMsg = getElementText(notificationMsg());
			comparetwoText(notificationMsg, "User group "+label+" was saved!");  
			
		}catch (Exception e) {
			e.printStackTrace();
			failedStep(e.getMessage());
		}
	}
	public void addAndRemoveUserFromUg(String ugName) throws Exception {
		try {
			elementClick(selectElement("Invite and remove users"));
//			String owner = getElementAttribute(inviteUserField(), "value");
			elementClick(selectElement("Add More"));
			elementSendKeys(inviteUserField(), "abc@invicara.com");
			elementClick(saveAndCloseButton());
			userGroupActions(ugName,"Edit");
			elementClick(selectElement("Invite and remove users"));
			verifyUserFullname();
			elementDisplayed(validateSelectElement("(Pending"), "Pending warning msg displayed for the user not yet approved", "Failed to show the pending warning msg");
			elementClick(removeUser("abc@invicara.com"));
			elementClick(selectElement("Yes, remove it"));
			try {
			Boolean actualValue = isElementPresent(removeUser("abc@invicara.com"), 3);
			failedStep("Failed to cancel the invitation");
			}catch (Exception e) {
				passedStep("User invitation cancelled successfully");
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			failedStep(e.getMessage());
		}
	}
	public String[] getUserGroupTestdata() {
		try {
			String name = getElementText(getTableHeaderValueUg(1));
			String shortName = getElementText(getTableHeaderValueUg(2));
			String description = getElementText(getTableHeaderValueUg(3));
			String values[] = { name, shortName, description };
			return values;
		} catch (Exception e) {
			e.printStackTrace();
			failedStep(e.getMessage());
			return null;
		}
	}
	
	public void verifyUserFullname() {
		try {
			String fullName = getElementText(userFullname(loadProperties().getProperty("Rfusername")));
			Assert.assertEquals(fullName, loadProperties().getProperty("RfFullname"),"");
			passedStep("Fullname displayed above the userId successfully");
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
}
