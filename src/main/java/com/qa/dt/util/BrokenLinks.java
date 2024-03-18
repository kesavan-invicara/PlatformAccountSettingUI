package com.qa.dt.util;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.bouncycastle.jcajce.provider.keystore.BC;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.qa.dt.base.BaseClass;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BrokenLinks extends BaseClass {

	public static void main(String[] args) throws Throwable {
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.get("https://qa1-app.invicara.com/classify");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		List<WebElement> links = driver.findElements(By.tagName("a"));
		System.out.println("Total links :" + links.size());
		// to iterate the all the links
		int count = 0;
		int row = 0;
		for (int i = 0; i < links.size(); i++) {

			WebElement element = links.get(i);
			String attribute = element.getAttribute("href");
			// System.out.println(attribute);
			// To find the response code
			try {
				if (attribute != null) {
					URL url = new URL(attribute);
					URLConnection openConnection = url.openConnection();
					HttpURLConnection connection = (HttpURLConnection) openConnection;
					int responseCode = connection.getResponseCode();

					if (responseCode == 200) {
						
						System.out.println(attribute + " --->Code : " + connection.getResponseCode() + "--->"
								+ connection.getResponseMessage());
						excel_write(row, 0, attribute);
						count++;
						row++;
					}
				}
			} catch (Exception e) {

			}
		}
		System.out.println("Broken links: " + count);
	}
}
