package com.salesforceApp.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;


public class CommonUtilities {
	
	public static String getPropertyValue(String key) throws IOException{
		
		String path = Constants.APPLICATION_PROPERTIES_PATH;
		FileInputStream fin = new FileInputStream(path);
		Properties ob = new Properties();
		ob.load(fin);
		String value = ob.getProperty(key);
		fin.close();
		return value;
	}

	
	public static String takescreenshot(WebDriver driver, String methodName) {
		
		String fdate = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date());
		
		GenerateReports report = GenerateReports.getInstance();
		TakesScreenshot scrShot = (TakesScreenshot) driver;										//Convert web driver object to TakeScreenshot
		File SrcFile = 	scrShot.getScreenshotAs(org.openqa.selenium.OutputType.FILE);			//Call getScreenshotAs method to create image file
		report.logTestInfo("screeen shot captured");
		String filePath = Constants.SCREENSHOT_PATH + methodName + "_"  + fdate + ".jpg" ;
		File DestFile=new File(filePath);
		try {
			FileUtils.copyFile(SrcFile, DestFile);												//Copy file at destination
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return filePath;
	}
}
