package com.qa.dt.page;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.Status;
import com.qa.dt.base.BaseClass;
import com.qa.dt.util.ExtentManager;

public class MyApplicationsPage extends BaseClass {
    

	public MyApplicationsPage() {
		PageFactory.initElements(driver, this);
	}

//	WebElements

    public WebElement iframe(){
        return driver.findElement(By.xpath("//*[@id='section1']/div/iframe[@class='iframe-profile']"));
    }


    public WebElement goBack() {
		return driver.findElement(By.xpath("//*[@id='goBackButton']/div[2]/div/span/span"));		
	}

	public WebElement noApplicationsFoundMessage(){
		return driver.findElement(By.xpath("//div[@id='app-container']"));
	}

	//div[@id="app-container"]/div
	public List<WebElement> applicationContainer(){
		return driver.findElements(By.xpath("//div[@id='app-container']/div"));
	}

	public WebElement applicationCardTitle(int appContainerId){
		return driver.findElement(By.xpath("//div[@id='app-container']//div/span[@class='applicationCardTitleText']["+appContainerId+"]"));
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

    public void clickGoBack() throws Exception {
		waitUntilElementVisibility(goBack());
		elementClick(goBack());
		waitForpageLoad();
	}

	public String getNoApplicationsFoundMessage() throws Exception{
		waitUntilElementVisibility(noApplicationsFoundMessage());
		return noApplicationsFoundMessage().getText();
	}

	public int getNoOfApplications() throws Exception{
		return applicationContainer().size();
	}

	
	public boolean isApplicationCardTitleExists(String applicationName) throws Exception{
		int listOfApps =  getNoOfApplications();
		String appName = "";
		boolean isAppTitleExists = false;
		for(int i=1;i<listOfApps+1;i++){
			appName = driver.findElement(By.xpath("//div[@id='app-container']//div/span[@class='application-text']["+i+"]")).getText();
			if(applicationName.equalsIgnoreCase(appName.trim())){
				System.out.println("appName "+ appName);
				isAppTitleExists = true;
				break;
			}
		}
		return isAppTitleExists;
	}
	
}
