		
	/*	sample comment
		name of the method	:	gotoUrl
	 	Brief Description	:	going to a particular webpage			//needs to be included for every reusable method when working on a project
	 	Arguments			:	url --> String
	 	created by			:	Automation team
	 	created date		:	5/5/22
	 	Last modified date	:	5/5/22 
	*/	

package com.base.methods;

import java.awt.Robot;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.salesforceApp.utilities.CommonUtilities;
import com.salesforceApp.utilities.Constants;
import com.salesforceApp.utilities.GenerateReports;

import io.github.bonigarcia.wdm.WebDriverManager;


public class BaseScripts {

		protected static WebDriver driver;											
		protected static WebDriverWait wait;
		protected static Alert alert;
		protected static Actions action;
		protected static Robot robot;
		public static GenerateReports report;
		
		@BeforeTest
		public static void initialTestSetup() {							
			System.out.println("@BeforeTest execution started");
			report = GenerateReports.getInstance();								//Creating instance of Extent report. There will be a single report for an entire test.
		}
		
		@Parameters({"browser"})
		@BeforeMethod
		public static void setUp(String browserName, Method method) throws IOException {
			
			System.out.println("@BeforeMethod execution started and browser name=" +browserName);
			report.startSingleTestReport(method.getName());
			setDriver(browserName);
			loginDirect();
		}
		
		@AfterMethod
		public static void tearDown() throws InterruptedException {
			System.out.println("@AfterMethod execution started");
			Thread.sleep(5000);
			closeAllDrivers();
		}
		
		@AfterTest
		public static void finalTearDown() {
			System.out.println("@AfterTest execution started");
		}
				
		public static void setDriver(String browserName) {
			
			if(browserName.equalsIgnoreCase("edge")) {
				WebDriverManager.edgedriver().setup();
				driver = new EdgeDriver();
				System.out.println("Edge driver instance created");	
			}
			else if(browserName.equalsIgnoreCase("chrome")) {
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
				System.out.println("Chrome driver instance created");
			}
			else if(browserName.equalsIgnoreCase("firefox")) {
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
				System.out.println("Firefox driver instance created");
			}
		}
		
		public static WebDriver getDriverInstance() {
			return driver;
		}
			
		public static void loginDirect() throws IOException {
			
			String url = CommonUtilities.getPropertyValue("url");
			String usernameValue = CommonUtilities.getPropertyValue("username");
			String passwordValue = CommonUtilities.getPropertyValue("password");
			gotoUrl(url);
			maximizeWindow();
			waitforPageToLoad();
			loginToSalesforce(usernameValue, passwordValue);							//login to Salesforce
		}
			
		public static void loginToSalesforce(String usernameVal, String passwordVal) {
			
			WebElement username = locate(By.id("username"));
			waitUntilElementVisible(username); 							//wait until username field appears
			enterText(username, usernameVal, "username textbox");
			WebElement password = locate(By.id("password"));
			enterText(password, passwordVal, "password textbox");
			WebElement btn = locate(By.id("Login"));
			singleClick(btn, "login button");
		}
		
		public static void getEdgeDriver() {
			
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			System.out.println("Edge driver instance created");		//to log onto the console
			report.logTestInfo("Edge driver instance created");      //to log onto the Extent report
		}
		
		public static void getChromeDriver() {
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
				System.out.println("Chrome driver instance created");
				report.logTestInfo("Chrome driver instance created");
		}
		
		public static void getFirefoxDriver() {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			System.out.println("Firefox driver instance created");
			report.logTestInfo("Firefox driver instance created");
		}
		
		public static void gotoUrl(String url) {
			driver.get(url);
			System.out.println("url entered is " + url);
			report.logTestInfo("url entered is " + url);
		}
		
		public static void maximizeWindow() {
			driver.manage().window().maximize();
			System.out.println("Browser window maximized");
			report.logTestInfo("Browser window maximized");
		}
		
		public static WebElement locate(By locator) {
			WebElement element = driver.findElement(locator);
			return element;
		}
		
		public static List<WebElement> locateMultiple(By locator){
			List<WebElement> elements = driver.findElements(locator);
			return elements;
		}
		
		public static void clearElement(WebElement element, String objName) {		//object name is any user-defined name just to identify what the web element is
			if(element.isDisplayed()) {
				element.clear();
				System.out.println("pass: " +objName+ " element cleared");
				report.logTestInfo(objName + " field cleared");
			}
			else
				System.out.println("fail: " +objName+ " element not displayed");
		}
		
		public static void enterText(WebElement element, String text, String objName) {
			if (element.isDisplayed()) {
				element.clear();
				element.sendKeys(text);
				System.out.println("pass: text entered in " +objName);
				report.logTestInfo("text entered in " + objName + " field");
			}
			else
				System.out.println("fail: " + objName + " element not displayed");			
		}
		
		public static void singleClick(WebElement element, String objName) {
			waitUntilElementVisible(element);
			if (element.isDisplayed()) {
				element.click();
				System.out.println("pass: " + objName+ " element clicked");
				report.logTestInfo(objName + " clicked");
			}
			else {
				System.out.println("fail: " + objName + " element not displayed");
				report.logTestInfo(objName + "not displayed");
			}
		}
		
		public static void doubleClick(WebElement element, String objName) {
			waitUntilElementVisible(element);
			if (element.isDisplayed()) {
				Actions action = new Actions(driver);
				action.doubleClick(element).build().perform();
				System.out.println("pass: " + objName+ " element double clicked");
				report.logTestInfo(objName + " double clicked");
			}
			else {
				System.out.println("fail: " + objName + " element not displayed");	
				report.logTestInfo(objName + "not displayed");
			}
		}
		
		public static void rightClick(WebElement element, String objName) {
			waitUntilElementVisible(element);
			if (element.isDisplayed()) {
				Actions action = new Actions(driver);
				action.contextClick(element).build().perform();
				System.out.println("pass: " + objName+ " element right clicked");
				report.logTestInfo(objName + " right clicked");
			}
			else
				System.out.println("fail: " + objName + " element not displayed");
		}
		
		public static void selectByValueData(WebElement element, String value, String objName) {
			Select option = new Select(element);
			option.selectByValue(value);
			System.out.println(objName + " selected " + value);
			report.logTestInfo(objName + " selected " + value);
		}
		
		public static void selectByTextDisplayed(WebElement element, String text, String objName) {
			Select option = new Select(element);
			option.selectByVisibleText(text);
			System.out.println(objName + " selected " +text);
			report.logTestInfo(objName + " selected " +text);
		}
		
		public static void selectByIndexData(WebElement element, int index, String objName) {
			Select option = new Select(element);
			option.selectByIndex(index);
			System.out.println(objName + " selected option with index " + index);
			report.logTestInfo(objName + " selected option with index " + index);
		}
		
		public static void mouseHover(WebElement element) {
			waitUntilElementVisible(element);
			action = new Actions(driver);
			action.moveToElement(element).build().perform();	
		}
		
		private static Alert switchToAlert() {				//this method is defined as private because it will be used only by this BaseScripts class
			return driver.switchTo().alert();
		}
		
		public static void acceptWindowAlert() {
			waitUntilAlertIsPresent();
			alert = switchToAlert();
			System.out.println("alert text=" + alert.getText());
			report.logTestInfo("Alert text: " + alert.getText());
			alert.accept();
		}
		
		public static void dismissWindowAlert() {
			waitUntilAlertIsPresent();
			alert = switchToAlert();
			System.out.println("alert text=" + alert.getText());
			report.logTestInfo("Alert text: " + alert.getText());
			alert.dismiss();
		}
		
		public static void handlePromptAlert(String inputValue) {
			waitUntilAlertIsPresent();
			alert = switchToAlert();
			alert.sendKeys(inputValue);
			System.out.println("alert text=" + alert.getText());
			report.logTestInfo("Alert text: " + alert.getText());
			alert.accept();
		}
		
		public static void closeDriver() {
			implicitWait();
			driver.close();
			System.out.println("Driver instance closed");
			report.logTestInfo("Driver instance closed");
		}
		
		public static void closeAllDrivers() {
			implicitWait();
			driver.quit();
			System.out.println("All driver instances closed");
			report.logTestInfo("All driver instances closed");
		}
		
		public static void waitforPageToLoad() {
			driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		}
		
		public static void implicitWait() {
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		}
		
		public static void waitUntilVisibleByLocator(By locator) {
			wait = new WebDriverWait(driver,30);
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		}
		
		public static void waitUntilElementVisible(WebElement element) {
			wait = new WebDriverWait(driver,30);
			wait.until(ExpectedConditions.visibilityOf(element));
		}
		
		public static void waitUntilAlertIsPresent() {
			wait = new WebDriverWait(driver,30);
			wait.until(ExpectedConditions.alertIsPresent());
		}
		
		public static void waitUntilElementIsClickable(WebElement element) {
			wait = new WebDriverWait(driver,30);
			wait.until(ExpectedConditions.elementToBeClickable(element));
		}
		
		public static void fluentWait(By locator) {
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
					.withTimeout(Duration.ofSeconds(30))
					.pollingEvery(Duration.ofSeconds(5))
					.ignoring(NoSuchElementException.class, ElementNotInteractableException.class);
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		}
		
		public static void switchToNewWindow(String baseWindowHandle) {
			Set<String> allWindowHandles = driver.getWindowHandles();
			for (String handle : allWindowHandles) {
				if(!baseWindowHandle.equalsIgnoreCase(handle))
					driver.switchTo().window(handle);
			}
		}
		
		public static void readWebTable(WebElement table) {	
			List<WebElement> allRows = table.findElements(By.tagName("tr"));					//or use By.xpath("tbody/tr")
			System.out.println("number of rows=" + allRows.size());
			for (WebElement row : allRows) {
				
				List<WebElement> header = row.findElements(By.tagName("th"));
				for (WebElement head : header) {
					System.out.println(head.getText());
				}
				List<WebElement> allColData = row.findElements(By.tagName("td"));				//or use By.xpath("td")
				for (WebElement data : allColData) {
					System.out.print(data.getText() + "\t");	
				}
				System.out.println();
			}	
		}
		
		
//		public static String getCurrentMethodName() {
//			
//			String methodName = new Object() {}
//			.getClass()
//			.getEnclosingClass()
//			.getName();
//			return methodName;
//		}
		
		public static void captureScreenShot(String methodName) throws IOException {					//pass name of the method in which screenshot needs to be taken	
			
			String fdate = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date());
						
			TakesScreenshot screenCapture = (TakesScreenshot) driver;				//typecast driver to the type TakesScreenshot
			File sourceFile = screenCapture.getScreenshotAs(OutputType.FILE);		//Take the screenshot and convert it to some File (jpg, png, etc.) format
			String path = Constants.SCREENSHOT_PATH + methodName + "_" + fdate+ ".jpg";			//path of file where screenshot needs to be saved
			File destFile = new File(path);
			FileUtils.copyFile(sourceFile, destFile);	
		}

		
//------------------------------------------------------------------------------------------------------------//
//					REUSABLE METHODS FOR SALESFORCE APPLICATION ONLY		
		
		
		
		public static void logoutOfSalesforce() {	
			clickUserMenuDropdown();
			WebElement logoutButton = locate(By.xpath("//a[contains(text(),'Logout')]"));
			singleClick(logoutButton, "Logout button");
		}
		
		public static void closeLightningExperiencePopup(){
			
			WebElement closeBox = locate(By.id("tryLexDialogX"));
			singleClick(closeBox, "Pop-up close button");
		}
		
		public static void clickUserMenuDropdown() {
			
			implicitWait();
			WebElement userMenu = locate(By.id("userNavLabel"));
			singleClick(userMenu, "user menu dropdown");
		}
		
		public static void clickOnMySettings() {
			clickUserMenuDropdown();
			
			WebElement mySettingsLink = locate(By.xpath("//a[@title='My Settings']"));
			singleClick(mySettingsLink, "My Settings option");
		}
}
