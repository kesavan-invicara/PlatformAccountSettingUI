package com.qa.dt.base;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Timeouts;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.SendKeysAction;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.google.common.base.Function;
import com.qa.dt.util.ExtentManager;
import com.qa.dt.util.Xls_Reader;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Base class
 * 
 * @author VasuSekar
 *
 */
public class BaseClass {
	public static WebDriver driver;
	public static Properties properties;
	public ExtentTest test;
	static Xls_Reader xl = new Xls_Reader();

	@BeforeSuite
	public void startReport() {
		ExtentManager.setExtent();
	}

	@AfterSuite
	public void endReport() {
		ExtentManager.endReport();
	}
	
	public WebElement getTitle() {
		return driver.findElement(By.xpath("//h3[@class='page-title']"));
	}
	public WebElement userAccessKey() {
		return driver.findElement(By.xpath("//span[text()='User access keys']"));
	}

	/**
	 * To load the properties Files
	 * 
	 * @return to properties file
	 * @throws IOException
	 */
	public static Properties loadProperties() throws IOException {
		// DOMConfigurator.configure("log.4j2.xml");
		// PropertyConfigurator.configure("Log4j.properties");
		FileInputStream stream = new FileInputStream("src\\main\\resources\\com.qa.config\\config.properties");
		properties = new Properties();
		properties.load(stream);
		return properties;
	}

	public static void configXl() {
		xl = new Xls_Reader();
	}

	/**
	 * To launch the browser based on properties file key and value
	 * 
	 * @param browserName
	 * @return driver
	 * @throws Exception
	 */
	public static WebDriver browser_LaunchIgnoreCase(String browserName) throws Exception {
		try {
			if (browserName.equalsIgnoreCase("chrome")) {
				WebDriverManager.chromedriver().setup();
				System.setProperty("webdriver.chrome.driver",
				System.getProperty("user.dir") + "\\chrome\\chromedriver.exe");
				ChromeOptions option= new ChromeOptions();
				option.addArguments("window-size=1366,768");
				option.addArguments("disable-infobars");
				// option.addArguments("--headless");
//				option.addArguments("--incognito");
				String downloadFilepath = System.getProperty("user.dir") + "\\Downloads\\";
				HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
				chromePrefs.put("profile.default_content_settings.popups", 0);
				chromePrefs.put("download.default_directory", downloadFilepath);
				option.setExperimentalOption("prefs", chromePrefs);
				driver = new ChromeDriver(option);
				driver.manage().window().maximize();
			}

			else if (browserName.equalsIgnoreCase("firefox")) {
//				System.setProperty("webdriver.geiko.driver",
//						System.getProperty("user.dir") + "\\Driver\\geckodriver.exe");
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
			}
			if (browserName.equalsIgnoreCase("ie")) {
//				System.setProperty("webdriver.ie.driver",
//						System.getProperty("user.dir") + "\\Driver\\IEDriverServer.exe");
				WebDriverManager.iedriver().setup();
				driver = new InternetExplorerDriver();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Is not a valid browser");
		}
		driver.manage().deleteAllCookies();
		driver.get("chrome://settings/clearBrowserData");
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_TAB);
		robot.keyRelease(KeyEvent.VK_TAB);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		return driver;
	}

	/**
	 * close All current window
	 */
	public static void driverQuit() {
		driver.quit();
	}

	/**
	 * launch the url based on given url
	 * 
	 * @param url the location of the web page relative to the url argument
	 * @throws Exception
	 */
	public static void launchURL(String url) throws Exception {
		try {
			driver.get(url);
			driver.manage().window().maximize();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("URL is incorrect");
		}
	}

	/**
	 * To fine the element is displayed or not displayed
	 * 
	 * @param element WebElement VariableName is to declare the name of the
	 *                WebElement
	 * @return
	 * @throws Exception
	 */
	public static boolean elementIsDisplayed(WebElement element) throws Exception {
		try {
			boolean displayed = element.isDisplayed();
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * To fine the element is enabled or not enabled
	 * 
	 * @param element WebElement VariableName is to declare the name of the
	 *                WebElement
	 * @return true or false
	 * @throws Exception
	 */
	public static boolean elementIsEnabled(WebElement element) throws Exception {
		try {
			boolean enabled = element.isEnabled();
			return enabled;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Element is not enabled");
		}
	}

	/**
	 * To fine the element is Selected or not Selected
	 * 
	 * @param element WebElement VariableName is to declare the name of the
	 *                WebElement
	 * @return true or false
	 * @throws Exception
	 */
	public static boolean elementIsSelected(WebElement element) throws Exception {
		try {
			boolean selected = element.isSelected();
			return selected;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Element is not selected");
		}
	}

	/**
	 * To click the element based on given element
	 * 
	 * @param element WebElement VariableName is to declare the name of the
	 *                WebElement
	 * @throws Exception
	 */
	public static void elementClick(WebElement element) throws Exception {
		try {
			waitUntilElementClickable(element);
			if (elementIsDisplayed(element) && elementIsEnabled(element)) {
				element.click();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Element is not clickable");
		}
	}

	/**
	 * To click the element based on given element using java script executor
	 * 
	 * @param element WebElement VariableName is to declare the name of the
	 *                WebElement
	 */
	public static void clickElementUsingJavascript(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click()", element);
	}

	/**
	 * To scorll the element based on given element using java script executor
	 * 
	 * @param element WebElement VariableName is to declare the name of the
	 *                WebElement
	 */
	public static void scrollIntoElementUsingJavascript(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView()",element);
	}

	/**
	 * To clear the text field
	 * 
	 * @param element WebElement VariableName is to declare the name of the
	 *                WebElement
	 */
	public static void elementClear(WebElement element) {
		element.clear();
	}

	/**
	 * This method switch the to frame using frame ID.
	 * 
	 * @param idValue : Frame ID wish to switch @param n Number use find and switch
	 *                the window id
	 * @return
	 */
	public String windowHandle(int n) {
		String oldwindow = driver.getWindowHandle();
		Set<String> newwindow = driver.getWindowHandles();
		driver.getWindowHandles();
		int i = 0;
		for (String string : newwindow) {
			if (i == n) {

				WebDriver window = driver.switchTo().window(string);
			}
		}
		return oldwindow;
	}

	/**
	 * Send the value to text field
	 * 
	 * @param element WebElement VariableName is to declare the name of the
	 *                WebElement
	 * @param data    String text field value
	 */
	public void elementSendkeys(WebElement element, String data) {
		element.clear();
		element.sendKeys(data);
	}

	/**
	 * Send the value to text field then click tab button
	 * 
	 * @param element WebElement VariableName is to declare the name of the
	 *                WebElement
	 * @param data    String text field value
	 * @throws Exception
	 */
	public void elementSentKeysTab(WebElement element, String data) throws Exception {
		element.sendKeys(data);
		element.sendKeys(Keys.TAB);
	}

	/**
	 * Send the value to text field then click tab button
	 * 
	 * @param element WebElement VariableName is to declare the name of the
	 *                WebElement
	 * @param value   String text field value
	 * @throws Exception
	 */
	public static void elementSendKeys(WebElement element, String value) throws Exception {
		waitUntilElementClickable(element);
		try {

			if (elementIsDisplayed(element) && elementIsEnabled(element)) {
				element.sendKeys(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Unable to send keys to the webelement");
		}
	}

	/**
	 * Get the text value from the element
	 * 
	 * @param element WebElement VariableName is to declare the name of the
	 *                WebElement
	 * @return text
	 * @throws Exception
	 */
	public static String getElementText(WebElement element) throws Exception {
		try {
			String text = element.getText();
			return text;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Unable to get the text from the webelement");
		}
	}

	/**
	 * Get the element attribute value
	 * 
	 * @param element WebElement VariableName is to declare the name of the
	 *                WebElement
	 * @param value   Get Attribute the from the element
	 * @return attribute value
	 * @throws Exception
	 */
	public static String getElementAttribute(WebElement element, String value) throws Exception {
		try {
			String attribute = element.getAttribute(value);
			return attribute;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Unable to get the text from the webelement");
		}
	}

	/**
	 * To select the element based on value index visible text
	 * 
	 * @param element WebElement VariableName is to declare the name of the
	 *                WebElement
	 * @param options String value use to select the value
	 * @param value   String value use to select the value
	 * @throws Exception
	 */
	public static void selectDropDown(WebElement element, String options, String value) throws Exception {
		waitUntilElementClickable(element);
		try {
			Select sc = new Select(element);
			if (options.equals("value")) {
				sc.selectByValue(value);
			} else if (options.equals("index")) {
				sc.selectByIndex(Integer.parseInt(value));
			} else if (options.equals("visibletext")) {
				sc.selectByVisibleText(value);
			}
		} catch (NumberFormatException e) {

			e.printStackTrace();
			throw new Exception("Unable to select from dropdown");
		}

	}

	/**
	 * Move to the Unovisible element
	 * 
	 * @param element WebElement VariableName is to declare the name of the
	 *                WebElement
	 * @throws Exception
	 */
	public static void moveToElement(WebElement element) throws Exception {
		try {
			waitUntilElementClickable(element);
			if (elementIsDisplayed(element) && elementIsEnabled(element)) {
				Actions a = new Actions(driver);
				a.moveToElement(element).build().perform();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Unable to move to the webelement");
		}
	}

	/**
	 * It is used to Keyboard Actions
	 * 
	 * @param element WebElement VariableName is to declare the name of the
	 *                WebElement
	 * @throws Exception
	 */
	public static void actionClickElement(WebElement element) throws Exception {
		try {
			waitUntilElementClickable(element);
			if (elementIsDisplayed(element) && elementIsEnabled(element)) {
				Actions a = new Actions(driver);
				a.click(element).build().perform();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Unable to move to the webelement");
		}
	}

	/**
	 * It is used to Keyboard Actions
	 * 
	 * @param element WebElement VariableName is to declare the name of the
	 *                WebElement
	 * @throws Exception
	 */
	public static void actionSendKeysElement(WebElement element, String value) throws Exception {
		try {
			waitUntilElementClickable(element);
			if (elementIsDisplayed(element) && elementIsEnabled(element)) {
				Actions a = new Actions(driver);
				a.sendKeys(element, value).build().perform();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Unable to send text to the webelement");
		}
	}

	/**
	 * Move to the element drag and drop
	 * 
	 * @param source WebElement VariableName is to declare the name of the
	 *               WebElement source
	 * @param target WebElement VariableName is to declare the name of the
	 *               WebElement target
	 * @throws Exception
	 */
	public static void dragAndDrop(WebElement source, WebElement target) throws Exception {
		try {
			Actions a = new Actions(driver);
			a.dragAndDrop(source, target).build().perform();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Unable to send text to the webelement");
		}
	}

	/**
	 * Mouse Double click
	 * 
	 * @param element WebElement VariableName is to declare the name of the
	 *                WebElement
	 * @throws Exception
	 */
	public static void doubleClickElement(WebElement element) throws Exception {
		try {
			waitUntilElementClickable(element);
			if (elementIsDisplayed(element) && elementIsEnabled(element)) {
				Actions a = new Actions(driver);
				a.doubleClick(element).build().perform();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Unable to move to the webelement");
		}
	}
	/**
	 * Mouse Double click
	 * 
	 * @param element Webelement to click
	 * @throws Exception
	 */
	public static void javascriptClick(WebElement element) throws Exception {
		try {
			waitUntilElementClickable(element);
			if (elementIsDisplayed(element) && elementIsEnabled(element)) {
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("arguments[0].click();", element);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Unable to click the webelement");
		}
	}

	/**
	 * IT is to mouse Right click
	 * 
	 * @param element WebElement VariableName is to declare the name of the
	 *                WebElement
	 * @throws Exception
	 */

	public static void contextClickElement(WebElement element) throws Exception {
		try {
			waitUntilElementClickable(element);
			if (elementIsDisplayed(element) && elementIsEnabled(element)) {
				Actions a = new Actions(driver);
				a.contextClick(element).build().perform();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Unable to move to the webelement");
		}
	}

	/**
	 * Wait until alert popup present
	 * 
	 */
	public static void waitUntilAlertPresent() throws Exception {
		try {
			WebDriverWait w = new WebDriverWait(driver, 20);
			w.until(ExpectedConditions.alertIsPresent());
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Alert is not present");
		}
	}

	/**
	 * To click the ok from alert popup
	 * 
	 * @throws Exception
	 */
	public static void acceptAlert() throws Exception {
		try {
			Alert a = driver.switchTo().alert();
			a.accept();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Unable to accept the alert");
		}
	}

	/**
	 * To click the ok from alert popup
	 * 
	 * @throws Exception
	 * @return returns alert text
	 */
	public static String getTextInAlert() throws Exception {
		String alertText = "";
		try {
			Alert a = driver.switchTo().alert();
			alertText = a.getText();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Unable to accept the alert");
		}
		return alertText;
	}

	/**
	 * To click the cancel the Alert Popup
	 * 
	 * @throws Exception
	 */
	public static void dismissAlert() throws Exception {
		try {
			Alert a = driver.switchTo().alert();
			a.dismiss();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Unable to dismiss the alert");
		}
	}

	/**
	 * to sent the value in alert popup
	 * 
	 * @param value String Value
	 * @throws Exception
	 */
	public static void sendKeysToAlert(String value) throws Exception {
		try {
			Alert a = driver.switchTo().alert();
			a.sendKeys(value);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Unable to send keys to the alert");
		}
	}

	/**
	 * Scroll up and down the window using coordinates
	 * 
	 * @param width  scroll up with
	 * @param height scroll up height
	 * @throws Exception
	 */

	/**
	 * Scroll up and down window using elements
	 * 
	 * @param Element WebElement VariableName is to declare the name of the
	 *                WebElement
	 * @throws Exception
	 */
	public static void scrollusingElements(WebElement Element) throws Exception {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView();", Element);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
	}

	/**
	 * Scroll up the bottom page of web page
	 * 
	 * @throws Exception
	 */
	public static void scrolltoBottomofPage() throws Exception {
		try {
			waitimplicit(3);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
	}

	/**
	 * Refresh the web page
	 * 
	 * @throws Exception
	 */
	public static void refreshWebPage() throws Exception {
		try {
			driver.navigate().refresh();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
	}

	/**
	 * Navigate the one tab to another tab using url
	 * 
	 * @param url based on navigate the tab
	 * @throws Exception
	 */
	public static void navigateToUrl(String url) throws Exception {
		try {
			driver.navigate().to(url);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
	}

	/**
	 * Return the back old tab
	 * 
	 * @throws Exception
	 */
	public static void navigateBack() throws Exception {
		try {
			driver.navigate().back();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
	}

	/**
	 * Moving forward to next tab
	 * 
	 * @throws Exception
	 */
	public static void navigateForward() throws Exception {
		try {
			driver.navigate().forward();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
	}

	/**
	 * take the screenshot
	 * 
	 * @param fileName name of the take screenshot
	 * @return png file
	 * @throws Exception
	 */
	public static File takeScreenShot(String fileName) throws Exception {
		File f = null;
		try {
			String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
			TakesScreenshot ts = (TakesScreenshot) driver;
			File screenshotAs = ts.getScreenshotAs(OutputType.FILE);
			f = new File(
					System.getProperty("user.dir") + "\\PassTestScreenShots\\" + fileName + "_" + dateName + ".png");
			FileUtils.copyFile(screenshotAs, f);

		} catch (WebDriverException e) {
			e.printStackTrace();
			throw new Exception();
		}
		return f;
	}

	/**
	 * take the screenshot
	 * 
	 * @param fileName name of the take screenshot
	 * @return png file
	 * @throws Exception
	 */
	public static String extendScreenShot(String filename) {
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		File source = takesScreenshot.getScreenshotAs(OutputType.FILE);
		String destination = System.getProperty("user.dir") + "\\FailTestScreenShots\\" + filename + "_" + dateName
				+ ".png";

		try {
			FileUtils.copyFile(source, new File(destination));
		} catch (Exception e) {
			e.getMessage();
		}
		return destination;
	}

	public static String extendPassScreenShot(String filename) {
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		File source = takesScreenshot.getScreenshotAs(OutputType.FILE);
		String destination = System.getProperty("user.dir") + "\\PassTestScreenShots\\" + filename + "_" + dateName
				+ ".png";

		try {
			FileUtils.copyFile(source, new File(destination));
		} catch (Exception e) {
			e.getMessage();
		}
		return destination;
	}

	/**
	 * compare two text it is used to validate the two text
	 * 
	 * @param expected Expected text value
	 * @param actual   Actual Text value
	 * @return True or false
	 * @throws Exception
	 */
	public boolean comparetwoText(String expected, String actual) throws Exception {
		try {
			Assert.assertEquals(expected, actual);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public boolean comparetwoList(List<String> expected, List<String> actual) throws Exception {
		try {

			Assert.assertEquals(expected,actual);
			passedStep("Both Expected and actual List table Data's are compared and verified successfully.");

			return true;
		} catch (Exception e) {
			failedStep("Both Expected and actual List table Data's are Failed to compare and verify.");			
			return false;
		}

	}

	/**
	 * To get the page title
	 * 
	 * @return String text
	 * @throws Exception
	 */
	public static String getPageTitle() throws Exception {
		try {
			String title = driver.getTitle();
			return title;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
	}

	/**
	 * To get the current url link
	 * 
	 * @return get url
	 */
	public static String getCurrenturl() throws Exception {
		try {
			return driver.getCurrentUrl();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
	}

	/**
	 * To read data from excel sheet and use it in the script
	 * 
	 * @param int   rownum excel sheet Row number
	 * @param int   cellnum excel sheet cell number
	 * @param sheet String Excel Sheet name
	 * @return String Value
	 * @throws Throwable
	 */
	public static String excel_read(int rownum, int cellnum, String sheet) throws Throwable {
		File file = new File("src\\main\\resources\\com.qa.testdata\\TestData.xlsx");
		FileInputStream fileInput = new FileInputStream(file);
		Workbook w = new XSSFWorkbook(fileInput);
		Sheet s = w.getSheet(sheet);
		Cell cell = s.getRow(rownum).getCell(cellnum);
		String valueOf = null;
		if (cell.getCellType() == 0) {
			double numericCellValue = cell.getNumericCellValue();
			long le = (long) numericCellValue;
			valueOf = String.valueOf(numericCellValue);
		} else if (cell.getCellType() == 1) {
			valueOf = cell.getStringCellValue();
		}
		return valueOf;
	}

	/**
	 * To write data to excel sheet
	 * 
	 * @param int   rownum excel sheet Row number
	 * @param int   cellnum excel sheet cell number
	 * @param sheet String Excel Sheet name
	 * @return String Value
	 * @throws Throwable
	 */
	public static void excel_write(int rownum, int cellnum, String value) throws Throwable {
		File file = new File("src\\main\\resources\\com.qa.testdata\\Book1.xlsx");
		FileInputStream fileInput = new FileInputStream(file);
		Workbook w = new XSSFWorkbook(fileInput);
		Sheet s = w.getSheet("Error Messages");
		Row row = s.createRow(rownum);
		Cell cell = s.getRow(rownum).getCell(cellnum);
		if (cell == null) {
			cell = row.createCell(cellnum);
			cell.setCellValue(value);
		} else {
			cell.setCellValue(value);
		}

		FileOutputStream fout = new FileOutputStream(file);
		w.write(fout);
		fout.close();
	}

	/**
	 * select value from DropDown by using selectByIndex
	 * 
	 * @param element WebElement VariableName is to declare the name of the
	 *                WebElement
	 * @param index   int select the value based on index
	 * @throws Exception
	 */
	public static void selectDropDownByIndex(WebElement element, int index) throws Exception {
		Select s = new Select(element);
		List<WebElement> options = s.getOptions();
		elementClick(options.get(index));
	}

	/**
	 * select value from DropDown by using selectByVisibleText
	 * 
	 * @param element WebElement VariableName is to declare the name of the
	 *                WebElement
	 * @param index   String select the value based on text
	 * @throws Exception
	 */
	public static void selectByVisibileText(WebElement element, String text) {
		Select s = new Select(element);
		s.selectByVisibleText(text);

	}

	/**
	 * Mouse double click using java script executor
	 * 
	 * @param element WebElement VariableName is to declare the name of the
	 *                WebElement
	 */
	public static void doubleClickUsingJavaScriptExecutor(WebElement element) {
		((JavascriptExecutor) driver).executeScript("var evt = document.createEvent('MouseEvents');"
				+ "evt.initMouseEvent('dblclick',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
				+ "arguments[0].dispatchEvent(evt);", element);
	}

	/**
	 * To enter the text using java script executor
	 * 
	 * @param element WebElement VariableName is to declare the name of the
	 *                WebElement
	 */
	public static void sendKeysJavascript(WebElement el, String keysToSend) {
		JavascriptExecutor ex = (JavascriptExecutor) driver;
		ex.executeScript("arguments[0].value='" + keysToSend + "';", el);
	}

	// wait methods
	/**
	 * wait Until Element is enable to click
	 * 
	 * @param element WebElement VariableName is to declare the name of the
	 *                WebElement
	 * @throws Exception
	 */
	public static void waitUntilElementClickable(WebElement element) throws Exception {
		WebDriverWait w = new WebDriverWait(driver, 120);
		w.until(ExpectedConditions.elementToBeClickable(element));
	}

	public static void waitUntilElementClickableforsec(WebElement element, int sec) throws Exception {
		WebDriverWait w = new WebDriverWait(driver, sec);
		w.until(ExpectedConditions.elementToBeClickable(element));
	}

	/**
	 * wait Until Element is enable to visible
	 * 
	 * @param element WebElement VariableName is to declare the name of the
	 *                WebElement
	 * @throws Exception
	 */
	public static void waitUntilElementVisibility(WebElement element) {
		WebDriverWait w = new WebDriverWait(driver, 30);
		w.until(ExpectedConditions.visibilityOf(element));

	}	
	
	/**
	 * wait for page load
	 * 
	 * @param int wait sec
	 * @throws Exception
	 */
	public static void waitimplicit(int sec) throws Exception {
		driver.manage().timeouts().implicitlyWait(sec, TimeUnit.SECONDS);

	}

	/**
	 * thread sleep wait the page based on given sec
	 * 
	 * @throws Exception
	 */
	public static void waitpause() throws Exception {
		Thread.sleep(4000);
	}

	public static void waitPause2() throws Exception {
		Thread.sleep(2000);

	}

	public static void waitForPageLoadJava() {

		Wait<WebDriver> wait = new WebDriverWait(driver, 30);
		wait.until(new Function<WebDriver, Boolean>() {
			public Boolean apply(WebDriver driver) {
				System.out.println("Current Window State       : "
						+ String.valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState")));
				return String.valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState"))
						.equals("complete");
			}
		});
	}

	/**
	 * \ wait page load wait for until all element present
	 */
	public static void waitForpageLoad() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("return document.readyState").toString().equals("complete");
	}
	

	public static void scrollPageInUpAndDown() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,-350)", "");
	}

	final public static boolean waitForElToBeRemove(WebElement element) {
		try {
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

			WebDriverWait wait = new WebDriverWait(driver, 10);

			boolean present = wait.ignoring(StaleElementReferenceException.class).ignoring(NoSuchElementException.class)
					.until(ExpectedConditions.invisibilityOfAllElements(element));

			return present;
		} catch (Exception e) {
			return false;
		} finally {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}
	}

	public boolean timeOuts(long orginalTime, long timeoutInSec) {
		long wait_time = timeoutInSec * 1000;
		long end_time = orginalTime + wait_time;
		return (System.currentTimeMillis() > end_time);

	}

	public boolean waitElementDisappear(WebElement element, long timeouts) {
		long ctime = System.currentTimeMillis();
		boolean isfound = this.isElementEnabled(element);
		while (isfound && !this.timeOuts(ctime, timeouts)) {
			isfound = this.isElementEnabled(element);
		}
		return !isfound;
	}

	public boolean isElementEnabled(WebElement element) {
		if (element == null)
			return false;
		try {
			return element.isEnabled();
		} catch (StaleElementReferenceException e) {
			return false;
		}
	}

	public boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException Ex) {
			return false;
		}
	}

	public void fluentWait(WebElement element, int timeOut) {
		Wait<WebDriver> wait = null;
		try {
			wait = new FluentWait<WebDriver>((WebDriver) driver).withTimeout(Duration.ofSeconds(20))
					.pollingEvery(Duration.ofSeconds(2)).ignoring(Exception.class);
			wait.until(ExpectedConditions.visibilityOf(element));
			element.click();
		} catch (Exception e) {
		}
	}

	public static void moveToElementByOffsetValue(WebElement element, int x, int y) {
		Actions a = new Actions(driver);
		a.moveToElement(element, x, y).build().perform();
	}

	public static void moveToElementByOffset(WebElement element) {
		Actions a = new Actions(driver);
		a.moveToElement(element, 200, 0).build().perform();
	}
	
	public static void fileUpload(String path) throws AWTException {
		Robot rb = new Robot();
		 // copying File path to Clipboard
			StringSelection str = new StringSelection(path);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(str, null);
			// press Contol+V for pasting
			rb.keyPress(KeyEvent.VK_CONTROL);
			rb.keyPress(KeyEvent.VK_V);
			// release Contol+V for pasting
			rb.keyRelease(KeyEvent.VK_CONTROL); 
			rb.keyRelease(KeyEvent.VK_V);
			// for pressing and releasing Enter��� 
			rb.keyPress(KeyEvent.VK_ENTER);
			rb.keyRelease(KeyEvent.VK_ENTER);
	}
	public void passedStep(String msg) {
		ExtentManager.test.log(Status.PASS, msg);
	}
	public void stepInfo(String msg) {
		ExtentManager.test.log(Status.INFO, msg);
	}
	public void failedStep(String msg) {
		ExtentManager.test.log(Status.FAIL, msg);
		Assert.fail();
	} 
	/**
	 * @author Iyappan.Kasinathan
	 * @description This method is used to switch or close the tabs
	 * @param i - the number need to pass for which tab to switch
	 * @param action - switch or close
	 * @throws Exception
	 */
	public void switchOrCloseTabs(int i, String action) throws Exception {
		waitPause2();
		ArrayList<String> tab = new ArrayList<String> (driver.getWindowHandles());
	    driver.switchTo().window(tab.get(i));
	    if(action=="close") {
	    	driver.close();
	    }
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description This method is used to retrieve test data from excel sheet
	 */
	public String retrieveTestData(String sheetName,String colName, int rowNum) throws Exception {
    	String appProjName = xl.getCellData(sheetName,colName,rowNum);
		return appProjName;
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description This method is used to verify whether the element is displayed or not
	 */
	public void elementDisplayed(WebElement element,String passMsg,String failMsg) {
		if(element.isDisplayed()) {
			ExtentManager.test.log(Status.PASS, passMsg);
		}else {
			ExtentManager.test.log(Status.FAIL, failMsg);
			fail();
		}
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description This method is used to verify whether the element is displayed or not
	 */
	public void elementDisplayed(WebElement element) {
		if(element.isDisplayed()==false) {
			fail();
		}
	}
	public By toByVal(WebElement we) {
	    // By format = "[foundFrom] -> locator: term"
	    // see RemoteWebElement toString() implementation
	    String[] data = we.toString().split(" -> ")[1].replace("]", "").split(": ");
	    String locator = data[0];
	    String term = data[1];
	    String value = term+"]";

	    switch (locator) {
	    case "xpath":
	        return By.xpath(value);
	    case "css selector":
	        return By.cssSelector(value);
	    case "id":
	        return By.id(value);
	    case "tag name":
	        return By.tagName(value);
	    case "name":
	        return By.name(value);
	    case "link text":
	        return By.linkText(value);
	    case "class name":
	        return By.className(value);
	    }
	    return (By) we;
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description This method is used to wait untill the element is visible
	 */
	public static void waitUntilElementsVisibility(List<WebElement> element) {
		WebDriverWait w = new WebDriverWait(driver, 30);
		w.until(ExpectedConditions.visibilityOfAllElements(element));

	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description This method is used to get index value for the list of elements
	 */
	public int getIndex(List<WebElement> element, String text) {
		int k = 1;
		try {
			for (WebElement wenElementNames : element) {
				String elementName = wenElementNames.getText().trim();				
				if (text.equalsIgnoreCase(elementName)) {
					break;
				} else {
					k++;
				}
			}
			return k;
		} catch (Exception E) {
			return 0;
		}
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description This method is used to verify whether the element is present or not
	 * @return boolean
	 */
	public Boolean isElementPresent(WebElement element, int sec) {
		try {
			WebDriverWait w = new WebDriverWait(driver, sec);
			w.until(ExpectedConditions.presenceOfElementLocated(toByVal(element)));
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description This method is used to split the value
	 */
	public int splitValue(String textToSplit, String textBySplit, int index) {
		String[] totalRecords = textToSplit.split(textBySplit);
		String count = totalRecords[index];
		int records = Integer.parseInt(count);
		return records;
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description This method is used to clear the existing value in the input fields
	 */
	public void clearField(WebElement element) {
		try {
			elementClick(element);
			String s = Keys.chord(Keys.CONTROL, "a");
			element.sendKeys(s);
			String s1 = Keys.chord(Keys.BACK_SPACE);
			element.sendKeys(s1);			
		} catch (Exception e) {
			e.printStackTrace();
			failedStep(e.getMessage());
		}
	}
	
	public String directoryActions(String action, String path) {
		String value=null;
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		switch(action) {
		case "createNewDir":
			boolean status = folder.mkdir();
			assertTrue(status);	
			break;
		case "getFileName":					
			value = listOfFiles[0].getName();
			break;
		case "getSize":		
			value = listOfFiles[0].length() / 1024 + " kb";
			break;
		case "deleteFile":
			boolean sts =listOfFiles[0].delete();
			Assert.assertTrue(sts);		
			break;
		}
		return value;
	}
	public void writeExcelData(String[] data,String sheetName) {
        // Load an existing Excel workbook
        String filePath = "src/main/resources/com.qa.testdata/TestData.xlsx";
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        Workbook workbook = null;
        try {
            fileInputStream = new FileInputStream(filePath);
            workbook = new XSSFWorkbook(fileInputStream); // Use XSSFWorkbook for .xlsx files
            // Get the existing sheet by name
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                System.err.println("The sheet 'ExistingSheetName' does not exist in the workbook.");
                return;
            }
            // Write data to the existing sheet
            for (int i = 0; i < data.length; i++) {
                Row row = sheet.getRow(i + 1); // Rows are 0-based, so we start at row 1
                if (row == null) {
                    row = sheet.createRow(i + 1);
                }
                Cell cell = row.createCell(1); // 2nd column (B)
                cell.setCellValue(data[i]);
            }
            // Save the changes back to the workbook
            fileOutputStream = new FileOutputStream(filePath);
            workbook.write(fileOutputStream);
            System.out.println("Multiple sets of data written to the existing sheet.");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
              
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

	/**
     * Determines if we're in the morning or afternoon
     * 
     * @param time the time to decode.
     * @return String representing the period of the day.
     */
    public static String decodePeriod(Calendar time) {
		 final String AM = "Good morning";
         final String PM = "Good afternoon";
         final String EVE = "Good evening";

        String decodedPeriod = null;
        int hour = time.get(Calendar.HOUR_OF_DAY);
        if (hour >= 18) {
            decodedPeriod = EVE;
        } else if (hour >= 12) {
            decodedPeriod = PM;
        } else {
            decodedPeriod = AM;
        }
        return decodedPeriod;
    }

	/**
     * generates random email
     *      * 
     * @return String representing the random email.
     */
    public static String generateRandomEmail() {
	   Random rand = new Random();	
	   String email = "automation"+ rand.nextInt(100000) + "@mailinator.com";
	   return email;
   }
	

}
