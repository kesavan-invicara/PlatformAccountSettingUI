package com.qa.dt.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.Status;
import com.qa.dt.base.BaseClass;
import com.qa.dt.util.ExtentManager;

public class InvitationsPage extends BaseClass {
    

	public InvitationsPage() {
		PageFactory.initElements(driver, this);
	}

//	WebElements

    public WebElement iframe(){
        return driver.findElement(By.xpath("//*[@id='section1']/div/iframe[@class='iframe-profile']"));
    }
	
	public WebElement acceptInvite(int row){
		return driver.findElement(By.xpath("//*[@id='table-container']/table[@class='my-dynamic-table']/tr["+row+"]/td[@class='acceptInvite']"));
	}

    public WebElement columnApplication(){
		return driver.findElement(By.xpath("//*[@id='table-container']/table[@class='my-dynamic-table']/tr[1]/th"));
	}

    public WebElement rejectInvite(int row){
		return driver.findElement(By.xpath("//*[@id='table-container']/table[@class='my-dynamic-table']/tr["+row+"]/td[@class='rejectInvite']"));
	}

	
	public WebElement message() {
		return driver.findElement(By.xpath("//div[@class='auth-access-text']"));
	}

    public WebElement goBack() {
		return driver.findElement(By.xpath("//*[@id='goBackButton']/div[2]/div/span/span"));		
	}

	public WebElement noInvitesFoundMessage(){
		return driver.findElement(By.xpath("//*[@id='table-container']/div"));
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
	 * accept invite for given application anme
	 *  
	 * @throws Exception
	 */	public void clickAcceptInvite(String applicationName) throws Exception {		
		// waitUntilElementVisibility(columnApplication());	
        int noOfRows = driver.findElements(By.xpath("//*[@id='table-container']/table[@class='my-dynamic-table']/tr")).size();
		System.out.println(noOfRows);
        String applicationNameInInviteTable = "";
        for(int i=2;i<noOfRows+1;i++){
            applicationNameInInviteTable = driver.findElement(By.xpath("//*[@id='table-container']/table[@class='my-dynamic-table']/tr["+noOfRows+"]/td[1]")).getText();
			System.out.println("applicationNameInInviteTable "+applicationNameInInviteTable);
            if(applicationNameInInviteTable.equals(applicationName)){
                elementClick(acceptInvite(i));
                break;
            }     
        }  
        
	}

    /**
	 * reject invite for given application anme
	 *  
	 * @throws Exception
	 */	public void clickRejectInvite(String applicationName) throws Exception {		
		// waitUntilElementVisibility(columnApplication());	
        int noOfRows = driver.findElements(By.xpath("//*[@id='table-container']/table[@class='my-dynamic-table']/tr")).size();
		System.out.println(noOfRows);
        String applicationNameInInviteTable = "";
        for(int i=2;i<noOfRows+1;i++){
            applicationNameInInviteTable = driver.findElement(By.xpath("//*[@id='table-container']/table[@class='my-dynamic-table']/tr["+noOfRows+"]/td[1]")).getText();
			System.out.println("applicationNameInInviteTable "+applicationNameInInviteTable);
            if(applicationNameInInviteTable.equals(applicationName)){
                elementClick(rejectInvite(i));
                break;
            }     
        }  
        
	}

	

    /**
	 * get error message
	 *  
	 * @throws Exception
	 */	public String getMessage() throws Exception {           		
		waitUntilElementVisibility(message());	
		return message().getText();      
        
	}		

    public void clickGoBack() throws Exception {
		waitUntilElementVisibility(goBack());
		elementClick(goBack());
		waitForpageLoad();
	}

	public String getNoInvitesFoundMessage() throws Exception{
		waitUntilElementVisibility(noInvitesFoundMessage());
		return noInvitesFoundMessage().getText();
	}
	
}
