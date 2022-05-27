package com.Salesforce.Test;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.base.methods.BaseScripts;
import com.salesforceApp.utilities.CommonUtilities;


public class LoginPageTCs extends BaseScripts {

	
	@Test (priority=0, groups="login", enabled=true)
	public static void TC01noPasswordError() throws IOException {
		
//		getChromeDriver();
//		String url = CommonUtilities.getPropertyValue("url");
//		gotoUrl(url);
//		maximizeWindow();
		
		logoutOfSalesforce();
		
		String usernameValue = CommonUtilities.getPropertyValue("username");
		WebElement username = locate(By.id("username"));
		waitUntilElementVisible(username);
		enterText(username,usernameValue,"username textbox");
		clearElement(locate(By.id("password")), "password textbox");
		
		WebElement login = locate(By.id("Login"));
		singleClick(login,"login button");
		String expectedError = "Please enter your password.";
		String actualError = locate(By.id("error")).getText();
		System.out.println("login error message: " +actualError);
		Assert.assertTrue(actualError.equals(expectedError), "Actual error message differs from the expected one");

	}
	
	@Test (priority=1, groups="login", enabled=true)
	public static void TC02validLogin() throws IOException {
		
//		getChromeDriver();
//		String url = CommonUtilities.getPropertyValue("url");
//		gotoUrl(url);
//		maximizeWindow();
		
		logoutOfSalesforce();
		
		String usernameValue = CommonUtilities.getPropertyValue("username");
		String passwordValue = CommonUtilities.getPropertyValue("password");
		WebElement username = locate(By.id("username"));
		waitUntilElementVisible(username);
		enterText(username,usernameValue,"username textbox");
		WebElement password = locate(By.id("password"));
		enterText(password, passwordValue, "password textbox");
		
		WebElement loginBtn = locate(By.id("Login"));
		singleClick(loginBtn,"login button");
		System.out.println("Webpage Title: " + driver.getTitle());				//Home Page ~ Salesforce - Developer Edition
	}
	
	
	@Test (priority=2, groups="login", enabled=true)
	public static void TC03checkRememberMe() throws IOException, InterruptedException {
		
//		//getChromeDriver();
//		String url = CommonUtilities.getPropertyValue("url");
//		gotoUrl(url);
//		maximizeWindow();
		
		logoutOfSalesforce();
		
		//login to Salesforce web application
		String usernameValue = CommonUtilities.getPropertyValue("username");
		String passwordValue = CommonUtilities.getPropertyValue("password");
		WebElement username = locate(By.id("username"));
		waitUntilElementVisible(username);
		enterText(username,usernameValue,"username textbox");
		WebElement password = locate(By.id("password"));
		enterText(password, passwordValue, "password textbox");
		WebElement checkbox = locate(By.cssSelector("#rememberUn"));
		singleClick(checkbox, "Remember Me checkbox");
		WebElement loginBtn = locate(By.id("Login"));
		singleClick(loginBtn,"login button");
		System.out.println("Redirected to: " + driver.getTitle());
		
		//log out of Salesforce
		WebElement userMenu = locate(By.id("userNavLabel"));
		waitUntilElementVisible(userMenu);
		singleClick(userMenu, "user menu dropdown");
		WebElement logoutOption = locate(By.xpath("//a[contains(text(),'Logout')]"));
		singleClick(logoutOption, "Logout link");
		Thread.sleep(2000);
		String actualUsername = locate(By.id("idcard")).getText();
		System.out.println("username dispalyed: " + actualUsername);
		Assert.assertTrue(actualUsername.equals(usernameValue), "Didplayed username does NOT matcch with the expected username");
		
//		try {
//		Assert.assertEquals(actualUsername, usernameValue);
//		report.logTestPassed();
//		report.attachScreenshot()
//		}
//		catch(Exception e) {
//			report.logTestFailedWithException(e);
//		}
	}
	
	
	@Test (priority=3, groups="login", enabled=true)
	public static void TC04AforgotPasswordLink() throws IOException, InterruptedException {
		
//		//getChromeDriver();
//		String url = CommonUtilities.getPropertyValue("url");
//		gotoUrl(url);
//		maximizeWindow();
		
		logoutOfSalesforce();
	
		//click on forgot your password
		WebElement forgotPwdLink = locate(By.id("forgot_password_link"));
		singleClick(forgotPwdLink, "Forgot Your Password");
		//input username page
		WebElement username = locate(By.xpath("//input[@id='un']"));
		waitUntilElementVisible(username);
		String usernameValue = CommonUtilities.getPropertyValue("username");
		enterText(username, usernameValue, "Username textbox");
		//click on continue button
		WebElement continueBtn = locate(By.id("continue"));
		singleClick(continueBtn, "Continue button");
		
		//'Check your email' message displayed
		WebElement message = locate(By.className("message"));
		waitUntilElementVisible(message);
		System.out.println("Message displayed: " + message.getText());
	}
	
	
	@Test(priority=4, groups="login", enabled=true)
	public static void TC04BloginFailedError() throws IOException {
		
//		//getChromeDriver();
//		String url = CommonUtilities.getPropertyValue("url");
//		gotoUrl(url);
//		maximizeWindow();
		
		logoutOfSalesforce();
		
		WebElement username = locate(By.id("username"));
		waitUntilElementVisible(username);
		enterText(username,"123","username textbox");
		WebElement password = locate(By.id("password"));
		enterText(password, "22131", "password textbox");
		WebElement login = locate(By.id("Login"));
		singleClick(login,"login button");
		
		By errLocator = By.xpath("//div[@id='error']");
		waitUntilVisibleByLocator(errLocator);
		String expectedError = "Please check your username and password. If you still can't log in, contact your Salesforce administrator.";
		String actualError = locate(errLocator).getText();
		System.out.println("login error message: " +actualError);
		Assert.assertEquals(actualError, expectedError, "Displayed error does not match the expected one");
	}
	
}
