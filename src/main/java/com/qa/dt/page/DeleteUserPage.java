package com.qa.dt.page;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.Status;
import com.qa.dt.base.BaseClass;
import com.qa.dt.util.ExtentManager;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DeleteUserPage extends BaseClass {
    

	public DeleteUserPage() {
		PageFactory.initElements(driver, this);
	}

//	WebElements

    public WebElement iframe(){
        return driver.findElement(By.xpath("//*[@id='section1']/div/iframe[@class='iframe-profile']"));
    }
	
	public WebElement confirmDeleteTextbox(){
		return driver.findElement(By.xpath("//input[@id='deleteConfirmInput']"));
	}

	public WebElement yesDeleteItButton() {
		return driver.findElement(By.xpath("//*[@id='deleteUserAccount']//button[contains(text(), 'Yes, delete it')]"));
	}	

	public WebElement cancelButton() {
		return driver.findElement(By.xpath("//*[@id='deleteUserAccount']//button[contains(text(), 'Cancel')]"));
	}

	public WebElement deleteAccountButton() {
		return driver.findElement(By.xpath("//*[@id='section4']//button[@id='deleteAccount']"));
	}	

	public WebElement message() {
		return driver.findElement(By.xpath("//*[@id='messageContainer']/div/span[2]"));
	}

    public WebElement goBack() {
		return driver.findElement(By.xpath("//*[@id='goBackButton']/div[2]/div/span/span"));
	}

    
			
	
//	Action Methods

    /**
	 * set email value
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
	 * set confirm delete text
	 *  
	 * @throws Exception
	 */	public void setConfirmDeleteTextbox(String inputTxt) throws Exception {		
		waitUntilElementVisibility(confirmDeleteTextbox());	
        elementClear(confirmDeleteTextbox());
		elementSendKeys(confirmDeleteTextbox(), inputTxt);
	}

	/**
	 * click yes, delete it button
	 *  
	 * @throws Exception
	 */public void clickYesDeleteItButton() throws Exception {		
		waitUntilElementVisibility(yesDeleteItButton());	       
		elementClick(yesDeleteItButton());
	}

    /**
	 * click cancel button
	 *  
	 * @throws Exception
	 */	public void clickCancelButton() throws Exception {		
		waitUntilElementVisibility(cancelButton());	       
		elementClick(cancelButton());
	}
    

    /**
	 * get error message
	 *  
	 * @throws Exception
	 */	public String getMessage() throws Exception {           		
		waitUntilElementVisibility(message());	
		return message().getText();      
        
	}
	
	
	public void clickDeleteAccountButton() throws Exception {          
		// waitUntilElementVisibility(deleteAccountButton());        
		elementClick(deleteAccountButton());
		waitForpageLoad();
	}
	

    public void clickGoBack() throws Exception {
		waitUntilElementVisibility(goBack());
		elementClick(goBack());
		waitForpageLoad();
	}
	
}

