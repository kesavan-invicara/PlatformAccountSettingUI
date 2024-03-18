package com.qa.dt.page;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.qa.dt.base.BaseClass;

public class CommonReusablesPage extends BaseClass {
	public CommonReusablesPage() {
		PageFactory.initElements(driver, this);
	}
//			WebElements

	public WebElement rowsPerPageValue() {
		return driver.findElement(By.xpath("//p[text()='Rows per page:']/parent::div//div[@id]"));
	}
	
	public WebElement titleName() {
		return driver.findElement(By.xpath("//h3"));
	}

	public WebElement selectRowsPerPageValue(String value) {
		return driver.findElement(By.xpath("//ul[@role='listbox']/li[text()='" + value + "']"));
	}

	public List<WebElement> totalRows() {
		return driver.findElements(By.xpath("//tbody/tr"));
	}

	public WebElement paginationLabel() {
		return driver.findElement(
				By.xpath("//p[text()='Rows per page:']/parent::div//div[@id]/parent::div/following-sibling::p"));
	}

	public WebElement ugNextPage() {
		return driver.findElement(By.xpath("//button[@aria-label='next page']"));
	}

	public WebElement ugNextPageDisabled() {
		return driver.findElement(By.xpath("//button[@aria-label='next page'][@disabled]"));
	}

	public WebElement ugFirstPage() {
		return driver.findElement(By.xpath("//button[@aria-label='first page']"));
	}

	public WebElement tableHeaders(String value) {
		return driver.findElement(By.xpath("//div[text()='" + value + "']"));
	}

	public WebElement tableHeader(String value) {
		return driver.findElement(By.xpath("//span[text()='" + value + "']/parent::th"));
	}

	public List<WebElement> tableHeader() {
		return driver.findElements(By.xpath("//thead/tr/th"));
	}

	public WebElement tableBody(int value, int index) {
		return driver.findElement(By.xpath("(//tbody/tr/td[" + value + "])[" + index + "]"));
	}

	public WebElement searchField() {
		return driver.findElement(By.xpath("//input[contains(@placeholder,'Search by')]"));
	}

	public List<WebElement> searchValue(String value) {
		return driver.findElements(By.xpath("//td[text()='" + value + "']"));
	}
	public WebElement closeButton() {
		return driver.findElement(By.xpath("//button[text()='Close']"));
	}
	public WebElement deleteItBtn() {
		return driver.findElement(By.xpath("//button[text()='Yes, delete it']"));
	}
	public WebElement notificationMsg() {
		return driver.findElement(By.xpath("(//div[@id='notistack-snackbar'])[last()]"));
	}
	public WebElement saveAndCloseButton() {
		return driver.findElement(By.xpath("//button[text()='Save & Close']"));
	}
	
	
	/**
	 * @author Iyappan.Kasinathan
	 * @description This method is used to select rows for pagination
	 */
	public void selectNoOfRows(String rowsValue) {
		try {
			waitUntilElementVisibility(rowsPerPageValue());
			elementClick(rowsPerPageValue());
			elementClick(selectRowsPerPageValue(rowsValue));
			stepInfo("Rows per page value selected as : " + rowsValue);
			waitUntilElementsVisibility(totalRows());
			int totalNoOfRows = totalRows().size();
			if (totalNoOfRows <= Integer.parseInt(rowsValue)) {
				passedStep("The number of user groups are displayed only user selected rows per page");
			} else {
				failedStep("The number of user groups are not displayed as per user selected rows per page");
			}
		} catch (Exception e) {
			e.printStackTrace();
			failedStep(e.getMessage());
		}

	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description This method is used to verify pagination function
	 */
	public void verifyPaginationFunctionality(String value) {
		try {
			String paginationValue = getElementText(paginationLabel());
			int totalItems = splitValue(paginationValue, " ", 2);
			int totalRows = 0;
			selectNoOfRows(value);
			int totalRowsInPage = totalRows().size();
			if(Integer.parseInt(value)<=totalRowsInPage) {
				passedStep("The number of rows displayed as per the selected");
			}else {
				failedStep("The number of rows are not displayed as per the selected");
			}

//			Boolean status = false;
//			while (status == false) {
//				int rowsPerPage = totalRows().size();
//				totalRows = totalRows + rowsPerPage;
//				if (ugNextPage().isEnabled()) {
//					elementClick(ugNextPage());
//					waitPause2();
//				}
//				status = ugNextPageDisabled().isDisplayed();
//			}
//			System.out.println(totalRows);
//			Assert.assertEquals(totalRows, totalItems);
//			passedStep("Pagination functionality is working as expected");
//			if (ugFirstPage().isEnabled()) {
//				elementClick(ugFirstPage());
//			}

		} catch (Exception e) {
			e.printStackTrace();
			failedStep(e.getMessage());
		}
	}

	public void sortingOrderOfTable(String action, String tableHeaderName) {
		try {
			selectNoOfRows("100");
			String rowsPerPage = getElementText(rowsPerPageValue());
			String paginationValue = getElementText(paginationLabel());
			int rowsPerPageValue = Integer.parseInt(rowsPerPage);
			int totalValue = splitValue(paginationValue, " ", 2);
			List<String> newOrderList = new ArrayList<>();
			List<String> originalOrderList = new ArrayList<>();
			int index = getIndex(tableHeader(), tableHeaderName);
			if (action == "Descending") {
				elementClick(tableHeaders(tableHeaderName));
				waitpause();
				String ascendingValue = tableHeader(tableHeaderName).getAttribute("aria-sort");
				Assert.assertEquals(ascendingValue, "ascending");
				elementClick(tableHeaders(tableHeaderName));
				waitpause();
				String descendingValue = tableHeader(tableHeaderName).getAttribute("aria-sort");
				Assert.assertEquals(descendingValue, "descending");

			}
			for (int i = 1; i <= totalValue; i++) {
				String value = getElementText(tableBody(index, i));
				newOrderList.add(value);
				if (i % rowsPerPageValue == 0) {
					elementClick(ugNextPage());
				}
			}
			originalOrderList = newOrderList;
			if (action == "Ascending") {
				Collections.sort(newOrderList);
			} else {
				Collections.sort(newOrderList, Collections.reverseOrder());

			}
			comparetwoList(originalOrderList, newOrderList);
			stepInfo(action + " order list : " + newOrderList);
			passedStep(action + " functionality of the table is working as expected");

		} catch (Exception e) {
			e.printStackTrace();
			failedStep(e.getMessage());
		}
	}

	public void searchTheTable(String searchText) {
		try {
			waitUntilElementVisibility(searchField());
			elementClick(searchField());
			elementSendKeys(searchField(), searchText);
			waitUntilElementsVisibility(searchValue(searchText));
			if (searchValue(searchText).size() > 0) {
				passedStep("Search functionality for the text " + searchText + " works as expected");
			} else {
				failedStep("Failed to fetch the filtered text");
			}
			clearField(searchField());

		} catch (Exception e) {
			e.printStackTrace();
			failedStep(e.getMessage());
		}
	}

	public void verifyTableHeaders(String[] value) {
		try {
			for (String name : value) {
				if(elementIsDisplayed(tableHeaders(name))) {
					passedStep(name+" is present in the usergroup table as expected");
				}else {
					failedStep(name+" is not present in the usergroup table");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			failedStep(e.getMessage());
		}
	}

	public void closeModal() {
		try {
			elementClick(closeButton());
		} catch (Exception e) {
			e.printStackTrace();
			failedStep(e.getMessage());
		}
	}
}
