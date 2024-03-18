package com.qa.dt.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends CommonReusablesPage {

	public HomePage() {
		PageFactory.initElements(driver, this);
	}

	public static WebElement selectServices(String serviceName) {
		return driver.findElement(By.xpath("//a[contains(@href,'"+serviceName+"')]"));
	}
	public static WebElement selectServicesThroughTiles(String serviceName) {
		return driver.findElement(By.xpath("//div[text()='"+serviceName+"']/parent::div/div[contains(text(),'See More')]"));
	}
	
	/**
	 * @author Iyappan.Kasinathan
	 * @description This method is used to navigate to console services page
	 */
	public void navigateToServices(String serviceName) {
		try {
			elementClick(selectServices(serviceName));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			failedStep(e.getMessage());
		}
	}	

	/**
	 * @author Iyappan.Kasinathan
	 * @description This method is used to navigate to console services page through tiles from homepage
	 */
	public void navigateToServicesThroughTiles(String serviceName) {
		try {
			launchURL("https://qa1-app.in.invicara.com/console/#/");
			elementClick(selectServicesThroughTiles(serviceName));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			failedStep(e.getMessage());
		}
	}
	
	

}
