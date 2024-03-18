package com.qa.dt.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.Status;
import com.qa.dt.base.BaseClass;
import com.qa.dt.util.ExtentManager;

public class PasswordPage extends BaseClass {
    

	public PasswordPage() {
		PageFactory.initElements(driver, this);
	}

//	WebElements

    public WebElement iframe(){
        return driver.findElement(By.xpath("//*[@id='section1']/div/iframe[@class='iframe-profile']"));
    }
	
	public WebElement currentPassword(){
		return driver.findElement(By.xpath("//input[@id='currentPassword']"));
	}

	public WebElement newPassword() {
		return driver.findElement(By.xpath("//input[@id='newPassword']"));
	}	

	public WebElement confirmPassword() {
		return driver.findElement(By.xpath("//input[@id='confirmPassword']"));
	}

	public WebElement savePasswordButton() {
		return driver.findElement(By.xpath("//button[@id='savePassword']"));
	}	

	public WebElement message() {
		return driver.findElement(By.xpath("//*[@id='messageContainer']/div/span[2]"));
	}

    public WebElement goBack() {
		return driver.findElement(By.xpath("//*[@id='goBackButton']/div[2]/div/span/span"));
	}

    
			
	
//	Action Methods

    /**
	 * switch to iframe
	 *  
	 * @throws Exception
	 */	public void switchToFrame() throws Exception {		
        // driver.switchTo().frame(iframe());
        int size = driver.findElements(By.tagName("iframe")).size();	
        System.out.println("size "+size);
        driver.switchTo().defaultContent();
        driver.switchTo().frame(0);
        // System.out.println(driver.getPageSource());
	}

	/**
	 * set current password
	 *  
	 * @throws Exception
	 */	public void setCurrentPassword(String currentPassword) throws Exception {		
		waitUntilElementVisibility(currentPassword());	
        elementClear(currentPassword());
		elementSendKeys(currentPassword(), currentPassword);
	}

	/**
	 * set new password
	 *  
	 * @throws Exception
	 */	public void setNewPassword(String newPassword) throws Exception {		
		waitUntilElementVisibility(newPassword());	
        elementClear(newPassword());
		elementSendKeys(newPassword(), newPassword);
	}

    /**
	 * set confirm password
	 *  
	 * @throws Exception
	 */	public void setConfirmPassword(String confirmPassword) throws Exception {		
		waitUntilElementVisibility(confirmPassword());	
        elementClear(confirmPassword());
		elementSendKeys(confirmPassword(), confirmPassword);
	}
    

    /**
	 * get error message
	 *  
	 * @throws Exception
	 */	public String getMessage() throws Exception {           		
		waitUntilElementVisibility(message());	
		return message().getText();      
        
	}
	
	
	public void clickSavePasswordButton() throws Exception {
		waitUntilElementVisibility(savePasswordButton());
		scrollIntoElementUsingJavascript(savePasswordButton());
		elementClick(savePasswordButton());
		waitForpageLoad();
	}
	

    public void clickGoBack() throws Exception {
		waitUntilElementVisibility(goBack());
		waitUntilElementClickable(goBack());
		scrollIntoElementUsingJavascript(goBack());
		javascriptClick(goBack());
		// elementClick(goBack());
		waitForpageLoad();
	}
	
}
