package com.myPkg.utilities;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.myPkg.base.TestBase;

public class TestUtil extends TestBase {
	
	public static String screenshotPath;
	public static String screenshotName;
	
	public static void captureScreenshot() throws IOException {
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		Date d = new Date();
		screenshotName = d.toString().replace(":", "_").replace(" ", "_")+".jpg";
		FileUtils.copyFile(scrFile, new File(sysUserDir+"/target/surefire-reports/html/"+screenshotName));
	}
	
	public static String getDiscountItemPrice(double percentage, double price, double qty) {
		double discount = (price*percentage)/100;
		double roundOff = Math.round(discount*100.0) / 100.0;
		double discountedPrice = price - roundOff;
		String a = String.format("%.2f", (discountedPrice*qty)); // for adding 0, after decimal point e.g. "144.9" to "144.90" for qty 5
		return "$"+a;
	}
}
