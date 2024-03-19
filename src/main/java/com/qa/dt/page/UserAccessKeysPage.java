package com.qa.dt.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.Status;
import com.qa.dt.base.BaseClass;
import com.qa.dt.util.ExtentManager;

public class UserAccessKeysPage extends BaseClass{
    

	public UserAccessKeysPage() {
		PageFactory.initElements(driver, this);
	}

//	WebElements

    public WebElement iframe(){
        return driver.findElement(By.xpath("//*[@id='section1']/div/iframe[@class='iframe-profile']"));
    }	
	

	public WebElement generateNewAccessKeyButton() {
		return driver.findElement(By.xpath("//button[@id='accessKey' and text() = 'Generate New Access Key']"));
	}

    public WebElement revokeAccessKeyButton() {
		return driver.findElement(By.xpath("//button[@class='revokeKeyButton' and text() = 'Revoke Access Key']"));
	}
    
    public WebElement revokeAccessKeyButtonMirrana() {
		return driver.findElement(By.xpath("//button[@class='secondaryKeyButton' and text() = 'Revoke Access Key']"));
	}
    
    public WebElement deleteAccessKeyButton() {
		return driver.findElement(By.xpath("//*[@id='modalDeleteConfirmation']/form//button[text() = 'Delete Access Key']"));
	}

    public WebElement cancelButton() {
		return driver.findElement(By.xpath("//*[@id='modalDeleteConfirmation']/form//button[text() = 'Cancel']"));
	}

	public WebElement closeButton() {
		return driver.findElement(By.xpath("//*[@id='modalAccessKey']/form//button[text()='Close']"));
	}
    
    public WebElement downloadButton() {
		return driver.findElement(By.xpath("//*[@id='modalAccessKey']/form//button[text()='Download']"));
	}
    
    public WebElement accessKeyCopyButton() {
		return driver.findElement(By.xpath("//*[@id='modalAccessKey']/form//div[@class='access-key']//img"));
	}

    public WebElement secretKeyCopyButton() {
		return driver.findElement(By.xpath("//*[@id='modalAccessKey']/form//div[@class='secret-key']//img"));
	}

    public WebElement copiedMessage() {
		return driver.findElement(By.xpath("//div[@id='copiedMessage']//div[@class='copy-toggle-text']"));
	}

	public WebElement message() {
		return driver.findElement(By.xpath("//div[@id='successMessage']//div[@class='auth-access-text']"));
	}

    public WebElement goBack() {
		return driver.findElement(By.xpath("//*[@id='goBackButton']/div[2]/div/span/span"));
	}   

    public WebElement copyButton() {
		return driver.findElement(By.xpath("//*[@id='ua-keys']//button/span[text() = 'Copy']"));
	}

    
    public WebElement copyToggleText() {
		return driver.findElement(By.xpath("//*[@id='authAccessDiv']//div[@class='copy-toggle-text']"));
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
	 * get error message
	 *  
	 * @throws Exception
	 */	public String getMessage() throws Exception {           		
		waitUntilElementVisibility(message());	
		return message().getText();      
        
	}

    /**
	 * get copied message
	 *  
	 * @throws Exception
	 */	public String getCopiedMessage() throws Exception {           		
		waitUntilElementVisibility(copiedMessage());	
		return copiedMessage().getText();      
        
	}
	
	
	public void clickGenerateNewAccessKeyButton() throws Exception {
		waitUntilElementVisibility(generateNewAccessKeyButton());
		elementClick(generateNewAccessKeyButton());
		waitForpageLoad();
	}

    public void clickAccessKeyPopupDownloadButton() throws Exception {
		waitUntilElementVisibility(downloadButton());
		elementClick(downloadButton());
		waitForpageLoad();
	}

    public void clickAccessKeyPopupcloseButton() throws Exception {
		waitUntilElementVisibility(closeButton());
		elementClick(closeButton());
		waitForpageLoad();
	}

    public void clickAccessKeyCopyButton() throws Exception {
		waitUntilElementVisibility(accessKeyCopyButton());
		elementClick(accessKeyCopyButton());
		waitForpageLoad();
	}

    public void clickSecretKeyCopyButton() throws Exception {
		waitUntilElementVisibility(secretKeyCopyButton());
		elementClick(secretKeyCopyButton());
		waitForpageLoad();
	}

	public void clickRevokeAccessKeyButton() throws Exception {
		waitUntilElementVisibility(revokeAccessKeyButton());
		elementClick(revokeAccessKeyButton());
		waitForpageLoad();
	}

    public void clickRevokeAccessKeyButtonMirrna() throws Exception {
		waitUntilElementVisibility(revokeAccessKeyButtonMirrana());
		elementClick(revokeAccessKeyButtonMirrana());
		waitForpageLoad();
	}

    public void clickDeleteAccessKeyButton() throws Exception {
		waitUntilElementVisibility(deleteAccessKeyButton());
		elementClick(deleteAccessKeyButton());
		waitForpageLoad();
	}

    public void clickDeleteAccessKeyCancelButton() throws Exception {
		waitUntilElementVisibility(cancelButton());
		elementClick(cancelButton());
		waitForpageLoad();
	}

    public void clickCopyButton() throws Exception {
		waitUntilElementVisibility(copyButton());
		elementClick(copyButton());
		waitForpageLoad();
	}

    public void clickGoBack() throws Exception {
		waitUntilElementVisibility(goBack());
		waitUntilElementClickable(goBack());
		javascriptClick(goBack());
		waitForpageLoad();
	}

    public String getCopyToggleText() throws Exception {
        waitUntilElementVisibility(copyToggleText());
        return copyToggleText().getText();
    }
	
}
