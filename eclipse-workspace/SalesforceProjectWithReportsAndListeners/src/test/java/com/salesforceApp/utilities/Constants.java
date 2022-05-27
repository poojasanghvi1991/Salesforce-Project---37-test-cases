package com.salesforceApp.utilities;

public class Constants {

	public static final String USER_DIR = System.getProperty("user.dir");	//"user.dir" gives the current directory value; Instead of using "." to give the relative path, this is a standard approach
	
	public static final String APPLICATION_PROPERTIES_PATH = USER_DIR + "/src/test/resources/Data.properties";
	public static final String  SCREENSHOT_PATH = USER_DIR + "/screenshots/";
	public static final String  GENERATE_REPORT_PATH = USER_DIR + "/ExtentReports/";
}
