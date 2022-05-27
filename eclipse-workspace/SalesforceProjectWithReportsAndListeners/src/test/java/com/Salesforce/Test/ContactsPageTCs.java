package com.Salesforce.Test;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.base.methods.BaseScripts;

public class ContactsPageTCs extends BaseScripts{
	
	@Test
	public static void goToContactsTab() throws IOException {
		
		//click on contacts tab
		implicitWait();
		WebElement contactsTab = locate(By.linkText("Contacts"));
		singleClick(contactsTab, "Contacts Tab");
		closeLightningExperiencePopup();
	}
	
	@Test
	public static void TC25createNewContact() throws IOException {			//pass
		
		goToContactsTab();
		WebElement newButton = locate(By.name("new"));
		singleClick(newButton, "New button");
		WebElement lastName = locate(By.id("name_lastcon2"));
		waitUntilElementVisible(lastName);
		enterText(lastName, "XYZ1", "Last Name textbox");
		WebElement accountName = locate(By.id("con4"));
		enterText(accountName, "Pooja", "Account Name textbox");
		WebElement saveButton = locate(By.name("save"));
		waitUntilElementIsClickable(saveButton);
		singleClick(saveButton, "Save button");
		System.out.println("Page title: " + driver.getTitle());
	}
	
	@Test
	public static void TC26createNewContactsView() throws IOException {			//pass
		
		goToContactsTab();
		WebElement createNewViewLink = locate(By.linkText("Create New View"));
		singleClick(createNewViewLink, "Create New View Link");
		WebElement viewName= locate(By.id("fname"));
		enterText(viewName, "Pooja2", "View Name textbox");
		WebElement uniqueViewName = locate(By.id("devname"));
		enterText(uniqueViewName, "test_engineer_Pooja2", "View Unique Name");
		WebElement saveButton = locate(By.name("save"));
		singleClick(saveButton, "Save button");
		waitforPageToLoad();
		WebElement viewDropdown = locate(By.name("fcf"));
		Select select = new Select(viewDropdown);
		List<WebElement> viewList = select.getOptions();
		for (WebElement option : viewList) {
			String val = option.getText();
			if(val.equals("Pooja1")) {
				System.out.println("Pass: Created view name is listed in the view dropdown list");
				break;
			}		
		}
		viewDropdown.click();
		implicitWait();
		String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		captureScreenShot(methodName);
	}
	
	
	@Test
	public static void TC27changeRecentContactsDropdown() throws IOException { 		 //pass
	
		goToContactsTab();
		WebElement selectRecentContacts = locate(By.xpath("//select[@id='hotlist_mode']"));
		selectByTextDisplayed(selectRecentContacts, "Recently Created", "Recent contacts drop-down list");
	}
	
	@Test
	public static void TC28changeContactsView() throws IOException, InterruptedException { 			//pass
		
		goToContactsTab();
		WebElement selectView = locate(By.xpath(" //select[@id='fcf']"));
		selectByTextDisplayed(selectView, "My Contacts", "View drop-down list");
		
		Thread.sleep(3000);
		Select select = new Select(driver.findElement(By.name("fcf")));
		String val = select.getFirstSelectedOption().getText();
		if (val.equals("My Contacts"))
			System.out.println("pass: My contacts view is displayed");				//change view from "My Contacts" to something else after executing this
	}
	
	
	@Test
	public static void TC29openSomeRandomContact() throws IOException {			//pass
		
		goToContactsTab();
		WebElement randomContact = locate(By.linkText("Gonzalez, Rose"));
		singleClick(randomContact, "Rose Gonzalez Contact link");
		System.out.println("New page title - " + driver.getTitle());
	}
	
	@Test
	public static void TC30skipViewName() throws IOException {			//pass
		
		goToContactsTab();
		WebElement createNewViewLink = locate(By.linkText("Create New View"));
		singleClick(createNewViewLink, "Create New View Link");
		WebElement uniqueViewName = locate(By.id("devname"));
		enterText(uniqueViewName, "EFGH", "View Unique Name");
		WebElement saveButton = locate(By.name("save"));
		singleClick(saveButton, "Save button");
		implicitWait();
		String expectedError = "Error: You must enter a value";
		WebElement errorMessage = locate(By.xpath("//div[@class='errorMsg']"));
		String actualError = errorMessage.getText();
		System.out.println("Error message - "+ actualError);				//Error: You must enter a value	
		Assert.assertEquals(actualError, expectedError, "The error message does not match the expected error.");
	}
	
	@Test
	public static void TC31cancelCreatingNewView() throws IOException {		//pass
		
		goToContactsTab();
		WebElement createNewViewLink = locate(By.linkText("Create New View"));
		singleClick(createNewViewLink, "Create New View Link");
		WebElement viewName= locate(By.id("fname"));
		enterText(viewName, "EFGH", "View Name textbox");
		WebElement uniqueViewName = locate(By.id("devname"));
		enterText(uniqueViewName, "ABCD", "View Unique Name");
		WebElement cancelButton = locate(By.name("cancel"));
		singleClick(cancelButton, "Cancel button");
		waitforPageToLoad();
		String expectedTitle = "Contacts: Home ~ Salesforce - Developer Edition";
		String actualTitle = driver.getTitle();
		System.out.println("Page title:" + actualTitle);
		Assert.assertEquals(actualTitle, expectedTitle, "Expected webpage is NOT displayed");	
	}
	
	@Test
	public static void TC32testSaveAndNewButton() throws IOException{		//pass
		
		goToContactsTab();
		WebElement newButton = locate(By.name("new"));
		singleClick(newButton, "New button");
		WebElement lastName = locate(By.id("name_lastcon2"));
		waitUntilElementVisible(lastName);
		enterText(lastName, "Indian", "Last Name textbox");
		WebElement accountName = locate(By.id("con4"));
		enterText(accountName, "Global Media", "Account Name textbox");					//First manually created an account named Global Media
		WebElement save_newButton = locate(By.name("save_new"));
		singleClick(save_newButton, "Save & New button");
		waitforPageToLoad();
		System.out.println("Page Title - " + driver.getTitle());
	}
}
