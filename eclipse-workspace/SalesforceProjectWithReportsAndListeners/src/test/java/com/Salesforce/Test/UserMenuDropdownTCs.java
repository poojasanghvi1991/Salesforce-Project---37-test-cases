package com.Salesforce.Test;

import static org.testng.Assert.assertTrue;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.base.methods.BaseScripts;

public class UserMenuDropdownTCs extends BaseScripts{

	@Test
	public static void TC05clickUserMenuDropdown() throws IOException {						//pass
		
		clickUserMenuDropdown();											//defined in BaseScripts class
		
		String [] userMenuArray = {"My Profile", "My Settings", "Developer Console", "Switch to Lightning Experience", "Logout"};
		List<String> expectedUserMenuList = new ArrayList<String>(Arrays.asList(userMenuArray));
		System.out.println("expected: " + expectedUserMenuList);
		
		List <WebElement> actualUserMenuList = locateMultiple(By.xpath("//div[@id='userNav-menuItems']//child::a"));		//get user menu drop down list
		List<String> arrayList = new ArrayList<String>();
		for(WebElement menu : actualUserMenuList) {
			String menuText = menu.getText();
			arrayList.add(menuText);
		}
		
		SoftAssert soft = new SoftAssert();
		System.out.println("User Menu Dropdown List in Salesforce: " + arrayList);
		report.logTestInfo("User Menu Dropdown List in Salesforce: " + arrayList);
		soft.assertTrue(arrayList.containsAll(expectedUserMenuList), "User Menu dropdown list does NOT contain the expected options");
		soft.assertAll();
		
		//get name of the currently executing method
		String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		captureScreenShot(methodName);
	}
	
	@Test
	public static void TC06setUpMyProfile() throws IOException, InterruptedException, AWTException {	//pass
		
		//String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		
		clickUserMenuDropdown();
		WebElement MyprofileLink = locate(By.xpath("//a[contains(text(),'My Profile')]"));
		singleClick(MyprofileLink, "My Profile option");
		waitforPageToLoad();
		Thread.sleep(3000);
		WebElement editButton = locate(By.xpath("//body[1]/div[1]/div[2]/table[1]/tbody[1]/tr[1]/td[1]/div[1]/div[2]/div[2]/div[1]/h3[1]/div[1]/div[1]/a[1]/img[1]"));
		singleClick(editButton, "edit profile iamge");

		Thread.sleep(3000);
		By frameLocator = By.id("contactInfoContentId");
		waitUntilVisibleByLocator(frameLocator);
		driver.switchTo().frame(locate(frameLocator));
		WebElement aboutTab = locate(By.xpath("//a[contains(text(),'About')]"));
		singleClick(aboutTab, "About Tab");
		WebElement lastName = locate(By.xpath("//input[@id='lastName']"));
		enterText(lastName, "Sanghvi", "Last Name textbox");
		WebElement saveButton = locate(By.xpath("//input[@value='Save All']"));
		singleClick(saveButton, "Save All button");	
	}
	
	@Test
	public static void TC06sharePost() throws IOException, InterruptedException {				//pass
		
		clickUserMenuDropdown();
		WebElement MyprofileLink = locate(By.xpath("//a[contains(text(),'My Profile')]"));
		singleClick(MyprofileLink, "My Profile option");
		waitforPageToLoad();
		
		//Click on post link
		Thread.sleep(3000);
		WebElement postlink = locate(By.id("publisherAttachTextPost"));
		singleClick(postlink, "Post link");
		Thread.sleep(3000);
		WebElement postFrame = locate(By.xpath("//iframe[@title='Rich Text Editor, publisherRichTextEditor']"));
		waitUntilElementVisible(postFrame);
		driver.switchTo().frame(postFrame);
		
		WebElement postArea = locate(By.xpath("/html[1]/body[1]"));
		singleClick(postArea, "Post textarea");
		enterText(postArea, "Good morning", "post text area");											
		
		driver.switchTo().defaultContent();
		implicitWait();
		WebElement shareButton = locate(By.id("publishersharebutton"));
		singleClick(shareButton, "Post share button");	
		
		Thread.sleep(4000);
		String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		captureScreenShot(methodName);
	}
	
	@Test
	public static void TC06uploadFile() throws IOException, InterruptedException {			//pass
		
		clickUserMenuDropdown();
		WebElement MyprofileLink = locate(By.xpath("//a[contains(text(),'My Profile')]"));
		singleClick(MyprofileLink, "My Profile option");
		waitforPageToLoad();
		
		//click on File link
		Thread.sleep(3000);
		WebElement fileLink = locate(By.xpath("//span[contains(text(),'File')]"));
		singleClick(fileLink, "File link");
		WebElement uploadFileBtn = locate(By.xpath("//a[@id='chatterUploadFileAction']"));
		singleClick(uploadFileBtn, "Upload file from computer button");
//		action = new Actions(driver);
//		action.moveToElement(uploadFileBtn).click().build().perform();
		Thread.sleep(2000);
		
		WebElement chooseFile = locate(By.name("chatterFile"));
		waitUntilElementVisible(chooseFile);
		Thread.sleep(3000);
		chooseFile.sendKeys("C:/Users/Pooja/Downloads/blue_rose.jpg");
		System.out.println("File to be uploaded sent");

		Thread.sleep(3000);
		WebElement shareButton = locate(By.id("publishersharebutton"));
		waitUntilElementIsClickable(shareButton);
		singleClick(shareButton, "Share Button");
//		action.moveToElement(shareButton).click().build().perform();
		
		if(driver.findElement(By.xpath("//span[@title='blue_rose']")).isDisplayed())
			assertTrue(true, "File has been Uploaded");
		else 
			assertTrue(false, "File has not been Uploaded");

		Thread.sleep(8000);
		String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		captureScreenShot(methodName);	
	}
	
	@Test
	public static void TC06addPhoto() throws IOException, InterruptedException, AWTException {		//pass in firefox
		
		clickUserMenuDropdown();
		WebElement MyprofileLink = locate(By.xpath("//a[contains(text(),'My Profile')]"));
		singleClick(MyprofileLink, "My Profile option");
		waitforPageToLoad();
		
		action = new Actions(driver);
		action.moveToElement(locate(By.className("chatter-photo"))).build().perform();
		
		WebElement addPhotoLink = locate(By.cssSelector("a#uploadLink"));
		waitUntilElementVisible(addPhotoLink);
		waitUntilElementIsClickable(addPhotoLink);
		action.moveToElement(addPhotoLink).click().build().perform();
		System.out.println("Add photo link clicked");						
		
		Thread.sleep(3000);
		WebElement photoFrame = locate(By.id("uploadPhotoContentId"));	
		waitUntilElementVisible(photoFrame);
		driver.switchTo().frame("uploadPhotoContentId");					//In CHROME - No frame element found by id uploadPhotoContentId
		
		WebElement chooseFile = locate(By.id("j_id0:uploadFileForm:uploadInputFile"));
		Thread.sleep(3000);
		chooseFile.sendKeys("C:\\Users\\Pooja\\Desktop\\bluerose.jpg");
		System.out.println("Image to be uploaded sent");
		
		Thread.sleep(5000);
		WebElement saveButton = locate(By.id("j_id0:uploadFileForm:uploadBtn"));
		singleClick(saveButton, "Save button");										
		
		Thread.sleep(5000);
		action.moveToElement(locate(By.className("cropPhotoArea"))).build().perform();
		robot = new Robot();
		robot.mouseWheel(-2);
		WebElement cropImageSave = locate(By.xpath("//*[@id='j_id0:j_id7:save']"));
		waitUntilElementVisible(cropImageSave);
		action.moveToElement(cropImageSave).click().build().perform();
		System.out.println("Image crop Save button clicked");	
		//singleClick(cropImageSave, "Image crop Save button");
		
		Thread.sleep(5000);
		String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		captureScreenShot(methodName);
		
	}
	
	@Test
	public static void TC07changeMySettings() throws IOException, InterruptedException, AWTException {		//entire testcase passed
		      
		//clickUserMenuDropdown();							//already added to clickOnMySettings() reusable method in BaseScripts class
		clickOnMySettings();
		Thread.sleep(3000);
		//download Login history data				--> pass
		WebElement personalTab = locate(By.xpath("//tbody/tr[1]/td[1]/div[1]/div[4]/div[2]/a[1]")); 	//div[@id='PersonalInfo']
		singleClick(personalTab, "Personal Tab");
		WebElement loginHistory = locate(By.xpath("//a[@id='LoginHistory_font']"));
		singleClick(loginHistory, "Login History Tab");
		WebElement downloadLink = locate(By.xpath("//a[contains(text(),'Download login history for last six months')]"));
		singleClick(downloadLink, "download login history link");
			
		//Customize display and Layout Tab	-->	pass
		robot = new Robot();
		robot.mouseWheel(-1);
		Thread.sleep(3000);
		WebElement displayLayoutTab = locate(By.xpath("//div[@id='DisplayAndLayout']"));
		singleClick(displayLayoutTab, "Display & Layout Tab");
		WebElement customizeTab = locate(By.xpath("//a[@id='CustomizeTabs_font']"));
		singleClick(customizeTab, "Customize My Tabs");
		Thread.sleep(3000);
		WebElement customAppdropdown = locate(By.xpath("//select[@id='p4']"));
		selectByTextDisplayed(customAppdropdown, "Salesforce Chatter", "Custom App Select box");
		Thread.sleep(3000);
		WebElement selectMenu = locate(By.xpath("//select[@id='duel_select_0']"));
		robot.mouseWheel(-2);
		Thread.sleep(2000);
		selectByTextDisplayed(selectMenu, "Reports", "Available Tabs Select menu");				//Cannot locate element with text: Reports
		WebElement addButton = locate(By.className("rightArrowIcon"));
		singleClick(addButton, "Add button");
		Thread.sleep(5000);
		WebElement saveButton = locate(By.name("save"));
		
		waitUntilElementIsClickable(saveButton);
		//action.moveToElement(saveButton).click().build().perform();
		singleClick(saveButton, "Save button");
		Thread.sleep(5000);
		//captureScreenShot(methodName);
		
		//Change email settings		--> pass
		Thread.sleep(3000);
		robot.mouseWheel(-1);
		WebElement emailTab = locate(By.xpath("//div[@id='EmailSetup']"));
		singleClick(emailTab, "Email Tab under My Settings");
		WebElement emailSettingsLink = locate(By.xpath("//a[@id='EmailSettings_font']"));
		singleClick(emailSettingsLink, "My Email Settings link");
		WebElement senderName = locate(By.id("sender_name"));
		enterText(senderName, "Pooja Sanghvi", "Email Name textbox");
		WebElement senderEmail = locate(By.id("sender_email"));
		enterText(senderEmail, "poojasanghvi1991@gmail.com", "Email Address textbox");
		WebElement radioButton = locate(By.cssSelector("#auto_bcc1"));
		singleClick(radioButton, "Automatic Bcc radio button");
		WebElement saveButton1 = locate(By.name("save"));
		singleClick(saveButton1, "Save button");
		System.out.println("Current page Title: " +driver.getTitle());
		
//		//Calendar and Reminder settings		--> pass
		Thread.sleep(3000);
		robot.mouseWheel(-1);
		WebElement calendarRemindersTab = locate(By.id("CalendarAndReminders"));
		singleClick(calendarRemindersTab, "Calendar & Reminders Tab");
		WebElement activityRemindersLink = locate(By.cssSelector("#Reminders_font"));
		singleClick(activityRemindersLink, "Activity Reminders Link");
		WebElement testReminderButton = locate(By.xpath("//input[@id='testbtn']"));
		singleClick(testReminderButton, "Open a Test Reminder");
//		String baseWindowHandle = driver.getWindowHandle();
//		switchToNewWindow(baseWindowHandle);
//		System.out.println(driver.getTitle());
//		WebElement dismissAllBtn = locate(By.id("Dismiss All"));
//		singleClick(dismissAllBtn, "Dismiss All button");

//		String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
//		captureScreenShot(methodName);
//		Thread.sleep(5000);
		//closeAllDrivers();
	}
	
	@Test
	public static void TC08DeveloperConsole() throws IOException, AWTException, InterruptedException {		//pass
		
		clickUserMenuDropdown();
		WebElement devConsole = locate(By.linkText("Developer Console"));
		singleClick(devConsole, "Developer Console Tab");
		
		String baseWindowHandle = driver.getWindowHandle();
		switchToNewWindow(baseWindowHandle);
		waitforPageToLoad();
		String expectedTitle = "Developer Console";
		String actualTitle = driver.getTitle();
		System.out.println("New window title: " + actualTitle);
		Assert.assertEquals(actualTitle, expectedTitle, "The correct webpage has not loaded");
		
		//close the Dev console by using ALT+F4 with the help of Robot class or driver.close()
		Thread.sleep(4000);
		robot = new Robot();
		robot.keyPress(KeyEvent.VK_ALT);
		robot.keyPress(KeyEvent.VK_F4);
	}
	
	@Test
	public static void TC09LogoutOfSalesforce() throws IOException, InterruptedException {		//pass
		
		clickUserMenuDropdown();
		implicitWait();
		WebElement logoutLink = locate(By.linkText("Logout"));
		singleClick(logoutLink, "Logout link");
		waitforPageToLoad();
		String expectedTitle = "Login | Salesforce";
		Thread.sleep(2000);
		String actualTitle = driver.getTitle();
		System.out.println(actualTitle);
		Assert.assertEquals(actualTitle, expectedTitle, "The expected webpage has not loaded");
	}
}
