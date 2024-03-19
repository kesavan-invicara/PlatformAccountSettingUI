package com.qa.dt.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.Status;
import com.qa.dt.base.BaseClass;
import com.qa.dt.util.ExtentManager;

public class AuthenticatorPage extends BaseClass{
    

	public AuthenticatorPage() {
		PageFactory.initElements(driver, this);
	}

//	WebElements

    public WebElement iframe(){
        return driver.findElement(By.xpath("//*[@id='section1']/div/iframe[@class='iframe-profile']"));
    }
	
	public WebElement oneTimeCode(){
		return driver.findElement(By.xpath("//input[@id='totp']"));
	}

	public WebElement deviceName() {
		return driver.findElement(By.xpath("//input[@id='userLabel']"));
	}		

	public WebElement saveButton() {
		return driver.findElement(By.xpath("//button[@id='saveTOTPBtn']"));
	}

	public WebElement cancelButton() {
		return driver.findElement(By.xpath("//button[@id='cancelTOTPBtn']"));
	}

	public WebElement message() {
		return driver.findElement(By.xpath("//*[@id='messageContainer']/div/span[2]"));
	}

    public WebElement goBack() {
		return driver.findElement(By.xpath("//*[@id='goBackButton']/div[2]/div/span/span"));
	}
    
    public WebElement qrImage() {
        return driver.findElement(By.xpath("//div[@id='authenticator']//div/img[@alt='Figure: Barcode']"));
    }

    public WebElement manualModelLink() {
        return driver.findElement(By.xpath("//a[@id='mode-manual']"));
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
        driver.switchTo().frame(1);
        // System.out.println(driver.getPageSource());
	}

	/**
	 * set one time code value
	 *  
	 * @throws Exception
	 */	public void setOneTimeCode(String otcode) throws Exception {		
        scrollIntoElementUsingJavascript(oneTimeCode());
		waitUntilElementVisibility(oneTimeCode());	
        elementClear(oneTimeCode());        
		elementSendKeys(oneTimeCode(), otcode);
	}

	/**
	 * set first name 
	 *  
	 * @throws Exception
	 */	public void setDeviceName(String dName) throws Exception {		
		waitUntilElementVisibility(deviceName());	
        elementClear(deviceName());
		elementSendKeys(deviceName(), dName);
	}	

    /**
	 * get error message
	 *  
	 * @throws Exception
	 */	public String getMessage() throws Exception {    
        scrollIntoElementUsingJavascript(message());       		
		waitUntilElementVisibility(message());	
		return message().getText();      
        
	}
	
	
	public void clickSaveButton() throws Exception {
        scrollIntoElementUsingJavascript(saveButton());
		waitUntilElementVisibility(saveButton());
		javascriptClick(saveButton());
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

    public String getQRImageSrc() throws Exception {
        waitUntilElementVisibility(qrImage());
        return qrImage().getAttribute("src");
    }

    public void clickManualModelLink() throws Exception {
        waitUntilElementVisibility(manualModelLink());
        elementClick(manualModelLink());
    }
	
}

