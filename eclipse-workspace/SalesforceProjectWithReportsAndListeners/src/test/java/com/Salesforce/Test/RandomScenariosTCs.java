package com.Salesforce.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.base.methods.BaseScripts;

public class RandomScenariosTCs extends BaseScripts {

	@Test
	public static void goToHomeTab() {

		WebElement homeTab = locate(By.linkText("Home"));
		singleClick(homeTab, "Home Tab");
		implicitWait();
		closeLightningExperiencePopup();
		
	}
	
	@Test
	public static void TC33verifyFullnameOfUser() throws IOException, InterruptedException {		//pass
			
		goToHomeTab();
		WebElement fullName = locate(By.xpath("//tbody/tr[1]/td[2]/div[1]/div[1]/div[1]/div[2]/span[1]/h1[1]/a[1]"));
		singleClick(fullName, "User's Firstname Lastname link");
		waitforPageToLoad();
		Thread.sleep(3000);
		String actualPageTitle = driver.getTitle();
		System.out.println("Page title when clicking on user's fullname link: " + actualPageTitle);
		
		clickUserMenuDropdown();
		//go to My Profile page
		WebElement myProfileLink = locate(By.linkText("My Profile"));
		singleClick(myProfileLink, "My Profile link");
		waitforPageToLoad();
		Thread.sleep(3000);
		String expectedPageTitle = driver.getTitle();
		System.out.println("Page title when clicking on My Profile link: " + expectedPageTitle);
		Assert.assertTrue(actualPageTitle.equals(expectedPageTitle), "Username link and My Profile link DO NOT open the same webpage");
	}
	
	@Test
	public static void TC34editLastName() throws AWTException, InterruptedException, IOException {			//pass
		
		goToHomeTab();
		WebElement fullName = locate(By.xpath("//tbody/tr[1]/td[2]/div[1]/div[1]/div[1]/div[2]/span[1]/h1[1]/a[1]"));
		singleClick(fullName, "User's Firstname Lastname link");
		waitforPageToLoad();
		
		//edit profile
		WebElement editProfile = locate(By.xpath("//img[@title='Edit Profile']"));
		singleClick(editProfile, "Edit Profile pencil");
		
		Thread.sleep(3000);
		By frameLocator = By.id("contactInfoContentId");
		waitUntilVisibleByLocator(frameLocator);
		driver.switchTo().frame(locate(frameLocator));
		WebElement aboutTab = locate(By.xpath("//a[contains(text(),'About')]"));
		singleClick(aboutTab, "About Tab");
		WebElement firstName = locate(By.name("firstName"));
		String firstnameVal = firstName.getAttribute("value");								//getAttribute("value") method; value will fetch the firstname value
		WebElement lastName = locate(By.xpath("//input[@id='lastName']"));
		enterText(lastName, "Abcd", "Last Name textbox");
		String fullNameVal = firstnameVal + " Abcd";
		WebElement saveButton = locate(By.xpath("//input[@value='Save All']"));
		singleClick(saveButton, "Save All button");
		
		WebElement updatedUsername = locate(By.id("tailBreadcrumbNode"));
		String updatedUsernameInHomepage = updatedUsername.getText();
		System.out.println("User's updated fullname under Home Tab: " +updatedUsernameInHomepage);
		
		WebElement nameOnUserMenuDropdown = locate(By.id("userNavLabel"));
		String nameOnUserMenuDropdownVal = nameOnUserMenuDropdown.getText();
		System.out.println("User's updated fullname on the user menu dropdown button: " +nameOnUserMenuDropdownVal);
		
		String nameOnPageTitle = driver.getTitle();
		System.out.println(nameOnPageTitle);
		Assert.assertTrue(nameOnPageTitle.contains(fullNameVal), "Updated user lastname does NOT show in the page title");
		
		if ((updatedUsernameInHomepage.equals(fullNameVal)) && (nameOnUserMenuDropdownVal.equals(fullNameVal)))
			System.out.println("Pass: Lastname has been updated as expected");
		else
			System.out.println("Fail: Lastname has not been updated as expected");
		
		Thread.sleep(3000);
		String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		captureScreenShot(methodName);
	}
	
	@Test
	public static void TC35customizeTabs() throws AWTException, IOException, InterruptedException {		//pass (need to add Libraries tab back before re-running the test case
		
		WebElement allTabsIcon = locate(By.id("AllTab_Tab"));
		singleClick(allTabsIcon, "All Tabs (+ icon)");
		implicitWait();
		WebElement customizeTabsBtn = locate(By.name("customize"));
		singleClick(customizeTabsBtn, "Customize My Tabs button");
		WebElement selectedTabsList = locate(By.id("duel_select_1"));
		String tabToRemove = "Libraries";
		selectByTextDisplayed(selectedTabsList, tabToRemove, "Selected Tabs Listbox");
		WebElement removeArrowBtn = locate(By.xpath("//img[@title='Remove']"));
		singleClick(removeArrowBtn, "Remove Arrow button");
		Robot robot = new Robot();
		robot.mouseWheel(-2);																//to move the screen up by 2 scrolls
		WebElement saveButton = locate(By.xpath("//input[@name='save']"));
		singleClick(saveButton, "Save button");
		
		waitforPageToLoad();
		String actualTitle = driver.getTitle();
		System.out.println("Page title: " +actualTitle);
		String expectedTitle = "All Tabs ~ Salesforce - Developer Edition";
		Assert.assertEquals(actualTitle, expectedTitle, "Expected webpage has NOT loaded");
		
		Thread.sleep(2000);
		clickUserMenuDropdown();
		WebElement logoutBtn = locate(By.linkText("Logout"));
		singleClick(logoutBtn, "Logout button");
		loginDirect();
		List<WebElement> tabBarItems = locateMultiple(By.xpath("//ul/li[following-sibling::li[@id='AllTab_Tab']]")); 	//gives all li elements under ul that come before li having id "AllTab_Tab"
		System.out.println("Number of tabs: " + tabBarItems.size());
		for (WebElement item : tabBarItems) {
			String tabText = item.getText();
			System.out.println(tabText);
			Assert.assertFalse(tabText.equals(tabToRemove), "Desired tab has NOT been removed from the Tab bar");
		}
		Thread.sleep(3000);
		String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		captureScreenShot(methodName);
	}
	
	@Test
	public static void TC36blockCalendarEvent() throws AWTException, InterruptedException, IOException {
		
		goToHomeTab();
		WebElement date = locate(By.xpath("/html[1]/body[1]/div[1]/div[2]/table[1]/tbody[1]/tr[1]/td[2]/div[1]/div[1]/div[1]/div[2]/span[2]/a[1]"));
		String actualDate = date.getText();
		System.out.println(actualDate);
		String expectedDate = new SimpleDateFormat("EEEE MMMM dd, yyyy").format(new Date());	//EEEE if for the full weekday name; MMMM is for the full month name
		System.out.println(expectedDate);														//Sunday May 15, 2022
		assertEquals(actualDate, expectedDate);
		singleClick(date, "Day and Date link");
		waitforPageToLoad();
		System.out.println("Page title after clicking on date link: " + driver.getTitle());
		
		//click on start time of calendar event
		Robot robot = new Robot();
		robot.mouseWheel(-2);
		WebElement time8pm = locate(By.id("p:f:j_id25:j_id61:28:j_id64"));
		singleClick(time8pm, "8:00 PM link");
		WebElement subjectIcon = locate(By.xpath("//tbody/tr[2]/td[2]/div[1]/a[1]/img[1]"));
		singleClick(subjectIcon, "Subject Combo icon");
		maximizeWindow();
		
		//switch to new pop-up window & then back to old window
		String baseWindowHandle = driver.getWindowHandle();
		switchToNewWindow(baseWindowHandle);
		Thread.sleep(3000);
		WebElement otherLink = locate(By.xpath("//a[contains(text(),'Other')]"));
		singleClick(otherLink, "Other link");
		driver.switchTo().window(baseWindowHandle);
		
		//change end time of calendar event
		WebElement endTime = locate(By.id("EndDateTime_time"));
		singleClick(endTime, "End time dropdown");
		WebElement time9pm = locate(By.id("timePickerItem_42"));
		singleClick(time9pm, "9:00 PM");
		WebElement saveButton = locate(By.name("save"));
		singleClick(saveButton, "Save button");
		waitforPageToLoad();
		System.out.println("Page title after creating a new Calendar event: " +driver.getTitle());
		
		//close reminder pop-up box
//		Thread.sleep(3000);
//		baseWindowHandle = driver.getWindowHandle();
//		switchToNewWindow(baseWindowHandle);
//		WebElement dismissAllBtn = locate(By.id("dismiss_all"));
//		singleClick(dismissAllBtn, "Dismiss All button");
//		driver.switchTo().window(baseWindowHandle);
		
		//first scroll the page down using JS Executor and then capture a screenshot
		JavascriptExecutor js = (JavascriptExecutor) driver; 
		js.executeScript("window.scrollBy(0,500)");
		
		Thread.sleep(3000);
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName(); 
		captureScreenShot(methodName);
	}
	
	@Test
	public static void TC37blockRecurringEvent() throws AWTException, IOException, InterruptedException {		//pass
		
		goToHomeTab();
		WebElement date = locate(By.xpath("/html[1]/body[1]/div[1]/div[2]/table[1]/tbody[1]/tr[1]/td[2]/div[1]/div[1]/div[1]/div[2]/span[2]/a[1]"));
		String actualDate = date.getText();
		System.out.println(actualDate);
		String expectedDate = new SimpleDateFormat("EEEE MMMM dd, yyyy").format(new Date());	//EEEE if for the full weekday name; MMMM is for the full month name
		System.out.println(expectedDate);
		assertEquals(actualDate, expectedDate);
		singleClick(date, "Day and Date link");
		implicitWait();
		System.out.println("Page title: " + driver.getTitle());
		
		//click on start time of calendar event
		Robot robot = new Robot();
		robot.mouseWheel(-2);
		WebElement time4pm = locate(By.id("p:f:j_id25:j_id61:20:j_id64"));
		singleClick(time4pm, "4:00 PM link");
		WebElement subjectIcon = locate(By.xpath("//tbody/tr[2]/td[2]/div[1]/a[1]/img[1]"));
		singleClick(subjectIcon, "Subject Combo icon");
		maximizeWindow();
		
		//switch to new pop-up window & then back to old window
		String baseWindowHandle = driver.getWindowHandle();
		switchToNewWindow(baseWindowHandle);
		Thread.sleep(3000);
		WebElement otherLink = locate(By.xpath("//a[contains(text(),'Other')]"));
		singleClick(otherLink, "Other link");
		driver.switchTo().window(baseWindowHandle);
		
		//change end time of calendar event
		WebElement startTime = locate(By.id("StartDateTime_time"));
		singleClick(startTime, "Start time drop-down");
		WebElement stime4pm = locate(By.id("timePickerItem_32"));
		singleClick(stime4pm, "7:00 PM");
		WebElement endTime = locate(By.id("EndDateTime_time"));
		singleClick(endTime, "End time dropdown");
		WebElement time7pm = locate(By.id("timePickerItem_38"));
		singleClick(time7pm, "7:00 PM");
	
		//check recurrence
		implicitWait();
		WebElement recurCheckbox = locate(By.name("IsRecurrence"));
		singleClick(recurCheckbox, "Recurrence checkbox");
		WebElement radioBtnWeekly = locate(By.id("rectypeftw"));
		singleClick(radioBtnWeekly, "Weekly radio button");
		WebElement recurEveryInput = locate(By.name("wi"));
		enterText(recurEveryInput, "1", "Recurs every textbox");
		
		String currentDay = new SimpleDateFormat("EEEE").format(new Date());				//Returns the day of the week with complete name Eg: Tuesday
		WebElement checkDay = locate(By.xpath("//input[contains(text(),currentDay) and @type='checkbox']"));
		singleClick(checkDay, currentDay + " checkbox");
		
		//calculate date 2 weeks from now
		int noOfDays = 14;
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, noOfDays);
		Date date1 = cal.getTime();
		String dateAfter2Weeks = new SimpleDateFormat("MM/dd/YYYY").format(date1);
		
		WebElement recurEndDate = locate(By.id("RecurrenceEndDateOnly"));
		enterText(recurEndDate, dateAfter2Weeks, "Recurrence End date textbox");
		System.out.println("Date 2 weeks from now: " + dateAfter2Weeks + "; entered in Recurrence End inputbox");
		//robot.keyPress(KeyEvent.VK_ENTER);
		
		singleClick(locate(By.xpath("//td[contains(text(),'Reminder')]")), "Reminder Text");
		
		//uncheck reminder checkbox
		WebElement remindercheck = locate(By.id("reminder_select_check"));
		waitUntilElementVisible(remindercheck);
		if (remindercheck.isSelected())
			singleClick(remindercheck, "Uncheck Reminder checkbox");
		
		robot.mouseWheel(2);
		WebElement allDayEvent = locate(By.id("evt15"));
		if(allDayEvent.isSelected())
			singleClick(allDayEvent, "Uncheck All-day event checkbox");
		
		Thread.sleep(3000);
		WebElement saveButton = locate(By.xpath("//input[@title='Save' and @type='submit']"));
		waitUntilElementIsClickable(saveButton);
		singleClick(saveButton, "Save button");
		
		implicitWait();
		WebElement monthViewIcon = locate(By.xpath("//img[@title='Month View']"));
		singleClick(monthViewIcon, "Month View icon");
		System.out.println("New Page Title: " + driver.getTitle());
		
		Thread.sleep(2000);
		String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		captureScreenShot(methodName);
	}
}










