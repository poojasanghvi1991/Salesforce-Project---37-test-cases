package com.Salesforce.Test;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import com.base.methods.BaseScripts;

public class AccountsPageTCs extends BaseScripts {

	@Test
	public static void goToSalesforceAccountsPage() throws IOException {		//generic method to use for the Accounts page test cases
				
		//go to Accounts page
		WebElement accountsTab = locate(By.linkText("Accounts"));
		singleClick(accountsTab, "Accounts Tab");
		implicitWait();
		closeLightningExperiencePopup();
	}
	
	@Test
	public static void TC10newAccountSetup() throws IOException, InterruptedException {		//pass
		
		goToSalesforceAccountsPage();
		//create new account
		Thread.sleep(3000);
		WebElement newButton = locate(By.name("new"));
		singleClick(newButton, "New button");
		WebElement accountName = locate(By.xpath("//input[@id='acc2']"));
		enterText(accountName, "Pooja Test1", "Account Name textbox");
		WebElement saveButton = locate(By.name("save"));
		singleClick(saveButton, "Save button");
		System.out.println("Page title: " + driver.getTitle());
		implicitWait();
		String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		captureScreenShot(methodName);
	}
	
	@Test
	public static void TC11createNewView() throws IOException, InterruptedException { 	//pass
		
		goToSalesforceAccountsPage();
		//create new view
		Thread.sleep(3000);
		WebElement newViewButton = locate(By.xpath("//a[contains(text(),'Create New View')]"));
		singleClick(newViewButton, "Create New View link");
		WebElement viewName= locate(By.id("fname"));
		enterText(viewName, "Pooja2", "View Name textbox");
		WebElement uniqueViewName = locate(By.id("devname"));
		enterText(uniqueViewName, "test_engineer_Pooja2", "View Unique Name");
		WebElement saveButton = locate(By.name("save"));
		singleClick(saveButton, "Save button");
		waitforPageToLoad();
		Thread.sleep(2000);
		WebElement viewDropdown = locate(By.xpath("//select[@name='fcf']"));
		waitUntilElementVisible(viewDropdown);
		action = new Actions(driver);
		action.moveToElement(viewDropdown).click().build().perform();
		Thread.sleep(3000);
		String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		captureScreenShot(methodName);
	}
	
	@Test
	public static void TC12editView() throws IOException, InterruptedException {	//pass
				
		goToSalesforceAccountsPage();
		//edit view
		Thread.sleep(3000);
		WebElement editButton = locate(By.xpath("//a[contains(text(),'Edit')]"));
		singleClick(editButton, "Edit button");	
		WebElement viewName = locate(By.name("fname"));
		waitUntilElementVisible(viewName);
		enterText(viewName, "New Pooja2", "View Name textbox");
		WebElement selectField = locate(By.xpath("//select[@id='fcol1']"));
		selectByTextDisplayed(selectField, "Account Name", "Select Field dropdown list");
		WebElement selectOperator = locate(By.xpath("//select[@id='fop1']"));
		selectByTextDisplayed(selectOperator, "contains", "Select Operator dropdown list");
		WebElement valueTextbox = locate(By.name("fval1"));
		enterText(valueTextbox, "a", "Value textbox");
		WebElement availableFieldsSelectbox = locate(By.id("colselector_select_0"));
		selectByTextDisplayed(availableFieldsSelectbox, "Last Activity", "Select Fields to Display");
		WebElement addButton = locate(By.xpath("//img[@title='Add']"));
		singleClick(addButton, "Add arrow button");
		WebElement saveButton = locate(By.xpath("//input[@title='Save' and @type='submit']"));
		singleClick(saveButton, "Save button");
		waitforPageToLoad();
		Thread.sleep(2000);
		WebElement viewDropdown = locate(By.xpath("//select[@name='fcf']"));
		waitUntilElementVisible(viewDropdown);
		action = new Actions(driver);
		action.moveToElement(viewDropdown).click().build().perform();
		Thread.sleep(3000);
		String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		captureScreenShot(methodName);		
	}
	
	@Test
	public static void TC13mergeAccounts() throws IOException, InterruptedException {		//pass
	
		goToSalesforceAccountsPage();
		WebElement mergeAccountsLink = locate(By.linkText("Merge Accounts"));
		singleClick(mergeAccountsLink, "Merge Accounts link under Tools");
		By searchboxLocator = By.xpath("//input[@id='srch']");
		waitUntilVisibleByLocator(searchboxLocator);
		WebElement findAccountsSearchbox = locate(searchboxLocator);
		enterText(findAccountsSearchbox, "pooja", "Find Accounts search box");
		WebElement findAccountsSearcBbutton = locate(By.name("srchbutton"));
		singleClick(findAccountsSearcBbutton, "Find Accounts button");
		WebElement firstCheckbox = locate(By.id("cid0"));
		if(!firstCheckbox.isSelected())
			singleClick(firstCheckbox, "first checkbox");
		WebElement secondCheckbox = locate(By.id("cid1"));
		if(!secondCheckbox.isSelected())	
			singleClick(secondCheckbox, "second checkbox");
		implicitWait();
		WebElement nextButton = locate(By.name("goNext"));
		singleClick(nextButton, "Next button");
		By mergeLocator = By.xpath("//input[@title='Merge'and @type='submit']");
		waitUntilVisibleByLocator(mergeLocator);
		WebElement mergeButton = locate(mergeLocator);
		singleClick(mergeButton, "Merge button");
		acceptWindowAlert();
		waitforPageToLoad();
		Thread.sleep(3000);
		String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		captureScreenShot(methodName);	
	}

	@Test
	public static void TC14createAccountReport() throws IOException, InterruptedException, AWTException {
		
		goToSalesforceAccountsPage();
		WebElement reportLink = locate(By.partialLinkText("last activity > 30 days"));
		singleClick(reportLink, "Accounts with last activity > 30 days Report link");
		implicitWait();
		//select report options
		WebElement dateFieldDropdown = locate(By.name("dateColumn"));
		singleClick(dateFieldDropdown, "Date Field dropdown menu");
		WebElement createdDateOption = locate(By.xpath("//div[contains(text(),'Created Date')]"));
		singleClick(createdDateOption, "Created Date option");
		WebElement fromDatePicker = locate(By.id("ext-gen152"));
		singleClick(fromDatePicker, "From date picker");
		WebElement todayButton = locate(By.xpath("//button[contains(text(),'Today')]"));	//problem with the id of Today button. It's constantly changing in every Test run.
		singleClick(todayButton, "Today button in From date picker");
		WebElement toDatePicker = locate(By.id("ext-gen154"));
		singleClick(toDatePicker, "To date picker");
		Thread.sleep(2000);
		robot = new Robot();
		robot.keyPress(KeyEvent.VK_SPACE);												//hitting the spacebar key selects Today's date
		System.out.println("Today's date sent in To date picker");
//		WebElement todayButton1 = locate(By.xpath("/html[1]/body[1]/div[18]/ul[1]/li[1]/div[1]/table[1]/tbody[1]/tr[3]/td[1]/table[1]/tbody[1]/tr[2]/td[2]/em[1]/button[1]"));	
//		singleClick(todayButton1, "Today button in To date picker");
		Thread.sleep(3000);
		WebElement saveButton = locate(By.xpath("//button[contains(text(),'Save')]"));
		singleClick(saveButton, "Save button");
		
		//save and run report
		By reportLocator = By.cssSelector("#saveReportDlg_reportNameField");
		fluentWait(reportLocator);
		WebElement reportName = locate(reportLocator);
		enterText(reportName, "Test report3", "Report Name textbox");
		WebElement uniqueReportName = locate(By.name("reportDevName"));
		enterText(uniqueReportName, "Test_report3unique", "Report Unique Name textbox");
		Thread.sleep(3000);
		WebElement saveRunReportButton = locate(By.xpath("//button[contains(text(),'Save and Run Report')]"));
		singleClick(saveRunReportButton, "Save and Run Report button");
		
		waitforPageToLoad();
		String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		captureScreenShot(methodName);			
	}
}
