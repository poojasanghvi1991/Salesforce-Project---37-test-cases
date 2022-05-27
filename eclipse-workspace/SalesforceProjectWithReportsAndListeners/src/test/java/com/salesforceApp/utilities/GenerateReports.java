package com.salesforceApp.utilities;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class GenerateReports {

	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports extent;
	public static ExtentTest logger;
	private static GenerateReports ob;
	
	private GenerateReports() {
		//private constructor of singleton class
	}
	
	public static GenerateReports getInstance() {		//throughout the Test execution, there will be only one object of GenerateReports class from start to end
		if (ob==null) {
			ob = new GenerateReports();
		}
		return ob;
	}
	
	public void startExtentReport() {							//creates the extent report and sets all the display standards
		
		String fdate = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date());
		
		htmlReporter = new ExtentHtmlReporter(Constants.GENERATE_REPORT_PATH + "report" + fdate + ".html");		//generates an HTML report
		extent = new ExtentReports();
		
		htmlReporter.config().setDocumentTitle("Test Execution Report");
		htmlReporter.config().setReportName("Firebase regression tests");
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setTheme(Theme.STANDARD);
		
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("Host Name", "Tekarch");
		extent.setSystemInfo("User Name", "Pooja");
		extent.setSystemInfo("Environment", "Automation Testing");
		
	}
	
	public void startSingleTestReport(String testName) {

		logger = extent.createTest(testName);
	}
	
	public void logTestInfo(String message) {
		logger.log(Status.INFO, message);
	}
	
	public void logTestPassed(String testcaseName) {
		logger.log(Status.PASS,  MarkupHelper.createLabel(testcaseName + " has passed the test", ExtentColor.GREEN));
	}
	
	public void logTestFailed(String testcaseName) {
		logger.log(Status.FAIL,  MarkupHelper.createLabel(testcaseName + " has failed the test", ExtentColor.RED));
	}
	
	public void logTestSkipped(String testcaseName) {
		logger.log(Status.SKIP,  MarkupHelper.createLabel(testcaseName + " has skipped the test", ExtentColor.YELLOW));
	}
	
	public void logTestFailedWithException(Exception e) {
		logger.log(Status.ERROR, e);
	}
	
	public void logTestWarning(String testcaseName) {
		logger.log(Status.WARNING,  MarkupHelper.createLabel(testcaseName + " has a warning message", ExtentColor.AMBER));
	}
	
	public void attachScreenshot(String path) throws IOException {
		logger.addScreenCaptureFromPath(path);
	}

	public void endReport() {
		extent.flush();
	}
}