package com.qa.dt.page;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class ItemServicePage extends CommonReusablesPage {
	public ItemServicePage() {
		PageFactory.initElements(driver, this);
	}

//			WebElements
	public WebElement relatedItemsThreedotMenu(String value) {
		return driver.findElement(By.xpath("//td[text()='" + value + "']/parent::tr/td//button"));
	}

	public WebElement namedUserItemsThreedotMenu(String value) {
		return driver.findElement(By.xpath("//a[text()='" + value + "']/parent::td/parent::tr/td//button"));
	}

	public WebElement selectOption(String value) {
		return driver.findElement(
				By.xpath("//div[@id='long-menu']//div[contains(@style,'opacity: 1')]//li[text()='" + value + "']"));
	}

	public WebElement jsonPopupModal() {
		return driver.findElement(By.xpath("//code[@class='language-json']"));
	}

	public WebElement linkElement(String value) {
		return driver.findElement(By.xpath("//a[text()='" + value + "']"));
	}

	public WebElement namedUserItemsLink() {
		return driver.findElement(By.xpath("//a/span[text()='Named user items']"));
	}

	public WebElement versionDropDown(String version) {
		return driver.findElement(By.xpath("//label[text()='Version']/parent::div//input[@value='" + version + "']"));
	}

	public WebElement tableBody(String value, int index) {
		return driver.findElement(By.xpath("//a[text()='" + value + "']/parent::td/parent::tr/td[" + index + "]"));
	}

	public WebElement breadCrumbItemServices() {
		return driver.findElement(By.xpath(
				"//span[text()='Related items']/preceding-sibling::span/button[contains(text(),'Version')]/ancestor::div[contains(@class,'breadcrumb-menu')]"));
	}

	public WebElement getid(String name) {
		return driver.findElement(By.xpath("//td[text()='" + name + "']/parent::tr/td/a"));
	}

	public WebElement getTableheaderValue(String name, int i) {
		return driver.findElement(By.xpath("//td[text()='" + name + "']/parent::tr/td[" + i + "]"));
	}

	public WebElement tablebody() {
		return driver.findElement(By.xpath("//tbody/tr/td"));
	}

	public WebElement filterOption() {
		return driver.findElement(By.xpath("//img[@alt='filter']"));
	}

	public WebElement filterKey() {
		return driver.findElement(By.xpath("(//input[@placeholder='Please select'])[last()]"));
	}

	public WebElement filterValue() {
		return driver.findElement(By.xpath("(//input[@placeholder='Input text'])[last()]"));
	}

	public WebElement addFilterBtn() {
		return driver.findElement(By.xpath("//button[text()=' Add Filter']"));
	}

	public WebElement searchBtn() {
		return driver.findElement(By.xpath("//button[text()='Search']"));
	}

	public WebElement clearAllBtn() {
		return driver.findElement(By.xpath("//div[@class='clearAllButton']"));
	}

	public WebElement switchToAdvQueryBtn() {
		return driver.findElement(By.xpath("//button[text()='Switch To Query']"));
	}

	public WebElement selectFilterName(String value) {
		return driver.findElement(
				By.xpath("//div[@class='auto-complete-options-pane']/div[contains(text(),'" + value + "')]"));
	}

	public WebElement textValue(String value) {
		return driver.findElement(By.xpath("//span[contains(text(),'" + value + "')]"));
	}

	public WebElement accessRightsOfNamedUserItems(String value) {
		return driver.findElement(
				By.xpath("//div[text()='" + value + "']/parent::div//div[contains(@class,'medium-label light')]"));
	}

	public WebElement searchUsers() {
		return driver.findElement(By.xpath("//input[@placeholder='Search users']"));
	}
	
	public WebElement copyNotificationSuccess() {
		return driver.findElement(By.xpath("//div[text()='Copied!']"));
	}
	
	public WebElement copyCodeButton() {
		return driver.findElement(By.xpath("//button[text()='Copy Code']"));
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description This method is used to view named user items JSON value
	 * @param itemName - Named user item
	 * @param action   - View/data/Users
	 */
	public void viewNamedUserItemsJSONdata(String itemName, String action) {
		try {
			elementClick(namedUserItemsThreedotMenu(itemName));
			elementClick(selectOption(action));
			switch (action) {
			case "View":
				elementDisplayed(jsonPopupModal(), "JSON popup is displayed successfully",
						"JSON popup is not displayed successfully");
				break;
			case "Data":				
				break;
			case "Users":
				elementDisplayed(searchUsers(), "Navigated to users page", "Failed to navigate users page");
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
			failedStep(e.getMessage());
		}
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description This method is used to view related items JSON value
	 * @param itemName - Named user item
	 * @param action   - View/data
	 */
	public void viewRelatedItemsJSONdata(String itemName, String action) {
		try {
			elementClick(relatedItemsThreedotMenu(itemName));
			elementClick(selectOption(action));
			switch (action) {
			case "View":
				waitUntilElementVisibility(jsonPopupModal());
				elementDisplayed(jsonPopupModal(), "JSON popup is displayed successfully",
						"JSON popup is not displayed successfully");
				break;
			case "Data":
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
			failedStep(e.getMessage());
		}
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description This method is used to select named user items which has related
	 *              items
	 */
	public void selectNamedUserItems(String value) {
		try {
			int index = getIndex(tableHeader(), "_itemClass");
			if (linkElement(value).isDisplayed() && tableBody(value, index).isDisplayed()) {
				elementClick(linkElement(value));
				String pageName = getElementText(titleName());
				Assert.assertEquals(pageName, "Related Items");
				stepInfo("Named user item is selected");
			}
		} catch (Exception e) {
			e.printStackTrace();
			failedStep(e.getMessage());
		}
	}

	public String[] getItemservicesTestData(String useritemName) {
		try {
			waitPause2();
			elementClick(tableHeaders("_itemClass"));
			waitPause2();
			selectNoOfRows("100");
			String relatedItemId = null;
			String id = getElementText(getid(useritemName));
			String shortName = getElementText(getTableheaderValue(useritemName, 3));
			String description = getElementText(getTableheaderValue(useritemName, 4));
			String itemClass = getElementText(getTableheaderValue(useritemName, 5));
			String userItemId = getElementText(getTableheaderValue(useritemName, 4));
			selectNamedUserItems(id);
			relatedItemId = getElementText(tablebody());
			String values[] = { id, shortName, description, itemClass, userItemId, relatedItemId };
			return values;
		} catch (Exception e) {
			e.printStackTrace();
			failedStep(e.getMessage());
			return null;
		}
	}

	public void filterBasicsearchQuery(String[] filterby, String[] filtervalue) {
		try {
			elementClick(filterOption());
			for (int i = 0; i < filterby.length; i++) {
				elementClick(filterKey());
				elementClick(selectFilterName(filterby[i]));
				filterValue().sendKeys(filtervalue[i]);
				elementClick(addFilterBtn());
			}
			elementClick(searchBtn());
			waitUntilElementsVisibility(searchValue(filtervalue[0]));
			if (searchValue(filtervalue[1]).size() == 1) {
				passedStep("Search functionality for the text " + filtervalue + " works as expected");
			} else {
				failedStep("Failed to fetch the filtered text");
			}
		} catch (Exception e) {
			e.printStackTrace();
			failedStep(e.getMessage());
		}
	}

	public void verifyAdvSearchQueryData(String[] filtervalue) {
		try {
			for (int i = 0; i < filtervalue.length; i++) {
				assertTrue(textValue(filtervalue[i]).isDisplayed());
				passedStep("Queries are formed as per basic search successfully");
			}
		} catch (Exception e) {
			e.printStackTrace();
			failedStep(e.getMessage());
		}
	}

	public void verifyUsersAccessRights(String user) {
		try {
			String accessRights = getElementText(accessRightsOfNamedUserItems(user));
			Assert.assertEquals(accessRights, "Access rights: create, read, update, delete, share, run, assign");
		} catch (Exception e) {
			e.printStackTrace();
			failedStep(e.getMessage());
		}
	}
	
	public void copyJSONcode() {
		try {
			elementClick(copyCodeButton());
			elementDisplayed(copyNotificationSuccess(), "Copied the code successfully", "Failed to copy the code");
		} catch (Exception e) {
			e.printStackTrace();
			failedStep(e.getMessage());
		}
	}

}
