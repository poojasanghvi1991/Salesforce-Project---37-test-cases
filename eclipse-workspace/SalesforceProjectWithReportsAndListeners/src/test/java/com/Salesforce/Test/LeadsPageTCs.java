package com.Salesforce.Test;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.base.methods.BaseScripts;

public class LeadsPageTCs extends BaseScripts {

	@Test
	public static void TC20goToLeadsTab() throws IOException {		//pass
			
		implicitWait();
		WebElement leadsTab = locate(By.linkText("Leads"));
		singleClick(leadsTab, "Leads Tab");
		closeLightningExperiencePopup();
		
		String expectedTitle = "Leads: Home ~ Salesforce - Developer Edition";
		waitforPageToLoad();
		String actualTitle = driver.getTitle();
		System.out.println("Page title: " +actualTitle);
		Assert.assertTrue(actualTitle.equals(expectedTitle), "Expected webpage has not loaded");
	}
	
	@Test
	public static void TC21validateViewDropdownList() throws IOException{		//pass
		
		TC20goToLeadsTab();
		WebElement selectLeadsView = driver.findElement(By.id("fcf"));
		Select select = new Select(selectLeadsView);
		List<WebElement> allViewsList = select.getOptions();
		System.out.println("\nList of all views under Leads Tab:");
		for (WebElement view : allViewsList) {
			System.out.println(view.getText());
		}
		//System.out.println(allViewsList); 					//values stored are in the form of addresses, so can't print or use it directly
	}
		
	@Test
	public static void TC22andTC23setDefaultLeadsView() throws IOException, InterruptedException {		//pass - for TC22 and TC23
		
		TC20goToLeadsTab();
		WebElement selectLeadsView = locate(By.id("fcf"));
		selectByTextDisplayed(selectLeadsView, "Today's Leads", "View drop-down list");
		Thread.sleep(2000);
		logoutOfSalesforce();
		
		loginDirect();
		TC20goToLeadsTab();
		waitforPageToLoad();
		WebElement goButton = locate(By.xpath("//input[@title='Go!']"));
		singleClick(goButton, "Go! button");
		
		waitforPageToLoad();
		String expectedText = "Today's Leads";
		Select select = new Select(driver.findElement(By.xpath("//select[@name='fcf']")));
		WebElement option = select.getFirstSelectedOption();
		String optionText = option.getText();
		System.out.println("View currently selected: " + optionText);
		Assert.assertEquals(optionText, expectedText, "Default lead's view is not as expected");
	}
	
	@Test
	public static void TC24createNewLead() throws IOException {		//pass
		
		TC20goToLeadsTab();
		WebElement newButton = locate(By.name("new"));
		singleClick(newButton, "New button");
		WebElement lastName = locate(By.id("name_lastlea2"));
		waitUntilElementVisible(lastName);
		enterText(lastName, "ABCD", "Last Name textbox");
		WebElement company = locate(By.id("lea3"));
		enterText(company, "ABCD", "Company textbox");
		WebElement saveButton = locate(By.name("save"));
		singleClick(saveButton, "Save button");
		waitforPageToLoad();
		System.out.println(driver.getTitle());
		implicitWait();
		String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		captureScreenShot(methodName);	
	}
}
