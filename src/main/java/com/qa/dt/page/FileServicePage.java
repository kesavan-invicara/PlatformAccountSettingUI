package com.qa.dt.page;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class FileServicePage extends CommonReusablesPage {
	public FileServicePage() {
		PageFactory.initElements(driver, this);
	}
//	WebElements
	
	
	public WebElement folderLabel(String value) {
		return driver.findElement(By.xpath("//div[@class='bg-white file-service__folders-tree']//div[text()='"+value+"']"));
	}
	public WebElement childFolderLabel(String value) {
		return driver.findElement(By.xpath("//div[text()='"+value+"']"));
	}
	public WebElement docType(String value) {
		return driver.findElement(By.xpath("//div[@id='"+value+"']"));
	}
	public WebElement filesKebabMenu(String value) {
		return driver.findElement(By.xpath("(//div[text()='"+value+"']/ancestor::tr/td)[last()]"));
	}
	public WebElement selectOption(String value) {
		return driver.findElement(By.xpath("//div[@id='long-menu']//div[contains(@style,'opacity: 1')]//li[text()='" + value + "']"));
	}
	public WebElement breadCrumbFileServices(String value) {
		return driver.findElement(By.xpath("//span[text()='File Service']/ancestor::div[contains(@class,'breadcrumb-menu')]//span[text()='"+value+"']"));
	}
	public WebElement currentTag() {
		return driver.findElement(By.xpath("//div[text()='File Version History']/ancestor::div[@role='dialog']//tbody/tr//img"));
	}
	public WebElement fileNameInVersionHistory(String value) {
		return driver.findElement(By.xpath("//input[@value='"+value+"'][@disabled]"));
	}
	public WebElement viewFileFromVersionHistory() {
		return driver.findElement(By.xpath("//div[text()='File Version History']/ancestor::div[@role='dialog']//tbody/tr[1]//div[@class='my-auto pointer']/img[@alt='current']"));
	}
	public WebElement deleteFileFromVersionHistory() {
		return driver.findElement(By.xpath("//div[text()='File Version History']/ancestor::div[@role='dialog']//tbody/tr[1]//div[@class='my-auto pointer']/img[@alt='delete']"));
	}
	public WebElement fileVersionHistoryHeader(String value) {
		return driver.findElement(By.xpath("//div[text()='File Version History']/ancestor::div[@role='dialog']//thead//span[text()='"+value+"']"));
	}
	public WebElement versionDropdown(String fileName,int index) {
		return driver.findElement(By.xpath("//div[text()='"+fileName+"']/parent::div/parent::td/parent::tr/td["+index+"]//button"));
	}
	public WebElement chooseVersion(String version) {
		return driver.findElement(By.xpath("//li[text()='"+version+"']"));
	}
	public WebElement deleteFileVersionHistoryBtn(String versionNo) {
		return driver.findElement(By.xpath("//span[text()='"+versionNo+"']/ancestor::tr/td//img[@alt='delete']"));
	}
	public WebElement downloadOption() {
		return driver.findElement(By.xpath("//a[text()='Download']"));
	}
	public WebElement searchfield() {
		return driver.findElement(By.xpath("//input[@placeholder='Search by Name or Tag']"));
	}
	public WebElement filterByTagOrName() {
		return driver.findElement(By.xpath("//span[text()='Filter by:']/parent::div//input"));
	}
	public WebElement filterByFileOrFolder() {
		return driver.findElement(By.xpath("(//span[text()='Filter by:']/parent::div//input)[last()]"));
	}
	public WebElement labelValue(String value) {
		return driver.findElement(By.xpath("//div[text()='"+value+"']"));
	}
	public WebElement addTags() {
		return driver.findElement(By.xpath("//div[contains(@class,' multi-label-input')]//input"));
	}
	public WebElement removeTags() {
		return driver.findElement(By.xpath("//img[@alt='close']"));
	}
	public WebElement tagsLabel() {
		return driver.findElement(By.xpath("//div[contains(@class,'tag-column-tag')]"));
	}
	
	
	
	public void verifyFileServicePageDisplayed(String projectName) {
		elementDisplayed(folderLabel(projectName), "Navigated to file service page successfully", "Failed to navigate to file service page");
	}
	
	/**
	 * @author Iyappan.Kasinathan
	 * @description This method is used to verify child folder is displayed under parent folder as expected
	 */
	public void verifyFoldersDisplayed(String parentFolder,String childFolder) {
		try {
			waitpause();
			for(int i=1;i<4;i++) {
				elementClick(folderLabel(parentFolder));
				waitpause();
				try {
				folderLabel(childFolder).isDisplayed();
					break;
				}catch(NoSuchElementException e) {
					continue;
				}
			}	
//			javascriptClick(folderLabel(parentFolder));
			elementDisplayed(childFolderLabel(childFolder),childFolder + " folder is displayed under " + parentFolder + " as expected","Failed to displaye the folder");
		} catch (Exception e) {
			e.printStackTrace();
			failedStep(e.getMessage());
		}
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description This method is used to perform actions
	 * @param fileName - Named of the file to be selected
	 * @param action - View/File Version History
	 */
	public void viewFilesActions(String fileName, String action,String documentType, String delMsg) {
		try {
			waitpause();
			elementClick(filesKebabMenu(fileName));
			elementClick(selectOption(action));
			switch (action) {
			case "View":
				elementDisplayed(docType(documentType), "File viewed succesfully", "Failed to view the file");
				break;
			case "File Version History":
				elementDisplayed(childFolderLabel("File Version History"), "File version history popup displayed successfully", "Failed to display file version history");
				elementDisplayed(fileNameInVersionHistory(fileName));
				break;
			case "Delete":
				waitpause();
				waitUntilElementVisibility(deleteItBtn());
				scrollusingElements(deleteItBtn());
				elementClick(deleteItBtn());
				String notificationMsg = getElementText(notificationMsg());
//				comparetwoText(notificationMsg, delMsg);
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			failedStep(e.getMessage());
		}
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description This method is used to verify the current tag
	 */
	public void verifyCurrentTag() {
		try {
		    String value = getElementAttribute(currentTag(), "src");
		    if(value.contains("current-tag")) {
		    	passedStep("Current tag is displayed for the file updated atlast");
		    }else {
		    	failedStep("Current tag is not displayed for the file updated atlast");
		    }
		
		}catch (Exception e) {
			e.printStackTrace();
			failedStep(e.getMessage());
		}
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description This method is used to select the version of the file
	 * @param fileName Name of the file
	 * @param versionName no of version
	 */
	public void selectVersion(String fileName,String versionName) {
		try {
			int index = getIndex(tableHeader(), "Version");
			elementClick(versionDropdown(fileName,index));
			waitUntilElementVisibility(chooseVersion(versionName));
			if(chooseVersion(versionName).isDisplayed()) {
				elementClick(chooseVersion(versionName));
			}else {
				failedStep("Version dropdown doesn't displayed");
			}
		}catch (Exception e) {
			e.printStackTrace();
			failedStep(e.getMessage());
		}
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description This method is used to delete the file version history
	 */
	public void deleteFileVersionHistory(String versionNo) {
		try {
			elementClick(deleteFileVersionHistoryBtn(versionNo));
			if(deleteItBtn().isDisplayed()) {
				elementClick(deleteItBtn());
			}else {
				failedStep("Failed to display delete confirmation dialog box");
			}
			waitUntilElementVisibility(notificationMsg());
			String notificationMsg = getElementText(notificationMsg());
			comparetwoText(notificationMsg, "Version 2 of file sample.pdf was deleted successfully.");
			elementClick(closeButton());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	public void downloadFile(String fileName) {
		try {
			elementClick(filesKebabMenu(fileName));
			elementClick(downloadOption());
			waitPause2();
			String file = directoryActions("getFileName","Downloads");
			String fileSize = directoryActions("getSize","Downloads");
			directoryActions("deleteFile","Downloads");
			System.out.println(file);
			System.out.println(fileSize);
			assertEquals(file, fileName, "File is not downloaded as expected");
			passedStep("file downloaded successfully");
		}catch (Exception e) {
			e.printStackTrace();
			failedStep(e.getMessage());
		}
	}
	public void searchFilesFolders(String tagOrName,String fileOrFolder,String value) {
		try {
		elementClick(filterByTagOrName());
		elementClick(labelValue(tagOrName));
		elementClick(filterByFileOrFolder());
		elementClick(labelValue(fileOrFolder));
		elementSendkeys(searchfield(), value);
		elementDisplayed(labelValue(value));
		}catch (Exception e) {
			e.printStackTrace();
			failedStep(e.getMessage());
		}
	}
	public void createTags(String fileName,String tagName) {
		try {
			viewFilesActions(fileName, "Info", null,null);
			elementSendKeys(addTags(), tagName);
			elementClick(saveAndCloseButton());
			waitUntilElementVisibility(notificationMsg());
			String notificationMsg = getElementText(notificationMsg());
			comparetwoText(notificationMsg, "Tags were updated successfully.");		
		}catch (Exception e) {
			e.printStackTrace();
			failedStep(e.getMessage());
		}
	}
	public void deleteTags(String fileName) {
		try {
			viewFilesActions(fileName, "Info", null,null);
			elementClick(removeTags());
			elementClick(saveAndCloseButton());
			waitUntilElementVisibility(notificationMsg());
			String notificationMsg = getElementText(notificationMsg());
			comparetwoText(notificationMsg, "Tags were updated successfully.");		
		}catch (Exception e) {
			e.printStackTrace();
			failedStep(e.getMessage());
		}
	}
	public void verifyTagsCreated(String tagName) {
		try {
			waitUntilElementVisibility(tagsLabel());
			elementDisplayed(tagsLabel());
			String value = getElementText(tagsLabel());
			assertEquals(value, tagName);
			passedStep("Tags are created successfully");			
		}catch (Exception e) {
			e.printStackTrace();
			failedStep(e.getMessage());
		}
	}
	public void verifyTagsDeleted(String tagName) {
		try {
			waitpause();
			waitUntilElementVisibility(tagsLabel());
			elementIsDisplayed(tagsLabel());			
			failedStep("Failed to delete tags");			
		}catch (Exception e) {
			passedStep("Tags are deleted successfully");
		}
	}
	
}
