package com.Salesforce.Test;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import com.base.methods.BaseScripts;

public class OpportunitiesPageTCs extends BaseScripts{

	public static void goToOpportunitiesPage() {
		
		implicitWait();
		WebElement opportunityTab = locate(By.id("Opportunity_Tab"));
		singleClick(opportunityTab, "Opportunities Tab");
		closeLightningExperiencePopup();		
	}
	
	@Test
	public static void TC15validateOpportunitiesView() throws IOException {	//pass
		
		goToOpportunitiesPage();
		
		//verify Opportunities View drop-down list
		WebElement selectOptyView = locate(By.id("fcf"));
		Select select = new Select(selectOptyView);
		List<WebElement> allViewsList = select.getOptions();
		System.out.println("\nList of all views under Leads Tab:");
		for(WebElement view : allViewsList) {
			System.out.println(view.getText());
		}	
	}
	
	@Test
	public static void TC16createNewOpportunity() throws IOException, InterruptedException {		//pass - just can't add the primary campaign source value
		
		goToOpportunitiesPage();
		WebElement newButton = locate(By.name("new"));
		singleClick(newButton, "New button");
		By optyNameLocator = By.name("opp3");
		fluentWait(optyNameLocator);
		WebElement optyName = locate(optyNameLocator);
		enterText(optyName, "second_opty", "Opportunity name textbox");
		WebElement accountName = locate(By.name("opp4"));
		enterText(accountName, "Pooja Test", "Account Name textbox");
		WebElement leadSource = locate(By.id("opp6"));
		selectByTextDisplayed(leadSource, "Web", "Lead Source dropdown list");
		WebElement stage = locate(By.id("opp11"));
		selectByTextDisplayed(stage, "Prospecting", "Stage dropdown list");
		WebElement probability = locate(By.id("opp12"));
		enterText(probability, "10%", "Probability (%) textbox");
		Thread.sleep(3000);
//		WebElement campaignSource = locate(By.id("opp17"));										//UnexpectedTagNameException: Element should have been "select" but was "input"
//		selectByTextDisplayed(campaignSource, "Facebook", "Primary Campaign source textbox");
		WebElement closeDate = locate(By.name("opp9"));
		enterText(closeDate, "5/30/2022", "Close Date");
		implicitWait();
		WebElement saveButton = locate(By.name("save"));
		waitUntilElementIsClickable(saveButton);
		singleClick(saveButton, "Save button");
		
		waitforPageToLoad();
		String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		captureScreenShot(methodName);	
	}
	
	@Test
	public static void TC17testOptyPipelineReport() throws IOException {		//pass 
		
		goToOpportunitiesPage();
		WebElement optyPipelineLink = locate(By.linkText("Opportunity Pipeline"));
		singleClick(optyPipelineLink, "Opportunity Pipeline link");
		waitforPageToLoad();
		String pageTitle = driver.getTitle();
		System.out.println("Page title: " + pageTitle);
		//get screenshot
		String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		captureScreenShot(methodName);
	}
	
	@Test
	public static void TC18testStuckOptyReport() throws IOException {			//pass
		
		goToOpportunitiesPage();
		WebElement stuckOpportunitiesLink = locate(By.linkText("Stuck Opportunities"));
		singleClick(stuckOpportunitiesLink, "Stuck Opportunities link");
		waitforPageToLoad();
		String pageTitle = driver.getTitle();
		System.out.println("Page title: " + pageTitle);						//just to verify the Opportunity Pipeline page has loaded
		
		String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		captureScreenShot(methodName);
	}
	
	@Test
	public static void TC19testQuarterlySummaryReport() throws IOException, InterruptedException {		//pass
		
		goToOpportunitiesPage();
		WebElement selectInterval = locate(By.xpath(" //select[@id='quarter_q']"));
		selectByTextDisplayed(selectInterval, "Current FQ", "Interval drop-down list");
		WebElement selectInclude = locate(By.xpath("//select[@id='open']"));
		selectByTextDisplayed(selectInclude, "All Opportunities", "Include drop-down list");
		WebElement runReportButton = locate(By.xpath("//input[@value='Run Report']"));
		singleClick(runReportButton, "Run Report button");
		System.out.println("Page title: " + driver.getTitle());
		
		Thread.sleep(3000);
		WebElement opportunityTab = locate(By.id("Opportunity_Tab"));
		singleClick(opportunityTab, "Opportunities Tab");
		WebElement selectInterval1 = locate(By.xpath(" //select[@id='quarter_q']"));
		selectByTextDisplayed(selectInterval1, "Next FQ", "Interval drop-down list");
		WebElement selectInclude1 = locate(By.xpath("//select[@id='open']"));
		selectByTextDisplayed(selectInclude1, "Open Opportunities", "Include drop-down list");
		WebElement runReportButton1 = locate(By.xpath("//input[@value='Run Report']"));
		singleClick(runReportButton1, "Run Report button");
		System.out.println("Page title: " + driver.getTitle());		
	}
}
