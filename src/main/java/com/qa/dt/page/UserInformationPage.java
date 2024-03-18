package com.qa.dt.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.Status;
import com.qa.dt.base.BaseClass;
import com.qa.dt.util.ExtentManager;

public class UserInformationPage extends BaseClass{
    

	public UserInformationPage() {
		PageFactory.initElements(driver, this);
	}

//	WebElements

    public WebElement iframe(){
        return driver.findElement(By.xpath("//*[@id='section1']/div/iframe[@class='iframe-profile']"));
    }
	
	public WebElement email(){
		return driver.findElement(By.xpath("//input[@id='email']"));
	}

	public WebElement firstName() {
		return driver.findElement(By.id("firstName"));
	}	

	public WebElement lastName() {
		return driver.findElement(By.id("lastName"));
	}

	public WebElement saveButton() {
		return driver.findElement(By.xpath("//*[@id='kc-form-buttons']/div/button[contains(text(),'Save')]"));
	}

	public WebElement cancelButton() {
		return driver.findElement(By.xpath("//*[@id='kc-form-buttons']/div/button[contains(text(),'Cancel')]"));
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
	 * set email value
	 *  
	 * @throws Exception
	 */	public void setEmail(String email) throws Exception {		
		waitUntilElementVisibility(email());	
        elementClear(email());
		elementSendKeys(email(), email);
	}

	/**
	 * set first name 
	 *  
	 * @throws Exception
	 */	public void setFirstName(String firstName) throws Exception {		
		waitUntilElementVisibility(firstName());	
        elementClear(firstName());
		elementSendKeys(firstName(), firstName);
	}

	/**
	 * set last name 
	 *  
	 * @throws Exception
	 */	public void setLastName(String lastName) throws Exception {		
		waitUntilElementVisibility(lastName());	
        elementClear(lastName());
		elementSendKeys(lastName(), lastName);
	}

    /**
	 * get email value
	 *  
	 * @throws Exception
	 */	public String getEmail() throws Exception {		
		waitUntilElementVisibility(email());	
		return email().getAttribute("value");
	}

	/**
	 * get first name 
	 *  
	 * @throws Exception
	 */	public String getFirstName() throws Exception {		
		waitUntilElementVisibility(firstName());	
		return firstName().getAttribute("value");
	}

	/**
	 * get last name 
	 *  
	 * @throws Exception
	 */	public String getLastName() throws Exception {		
		waitUntilElementVisibility(lastName());	
		return lastName().getAttribute("value");
	}

    /**
	 * get error message
	 *  
	 * @throws Exception
	 */	public String getMessage() throws Exception {           		
		waitUntilElementVisibility(message());	
		return message().getText();      
        
	}
	
	
	public void clickSaveButton() throws Exception {
		waitUntilElementVisibility(saveButton());
		elementClick(saveButton());
		waitForpageLoad();
	}

	public void clickCancelButton() throws Exception {
		waitUntilElementVisibility(cancelButton());
		elementClick(cancelButton());
		waitForpageLoad();
	}

    public void clickGoBack() throws Exception {
		waitUntilElementVisibility(goBack());
		waitUntilElementClickable(goBack());
		javascriptClick(goBack());
		waitForpageLoad();
	}
	
}
