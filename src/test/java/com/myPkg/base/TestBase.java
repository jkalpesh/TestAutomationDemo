package com.myPkg.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.myPkg.utilities.ExtentManager;
import com.myPkg.utilities.TestUtil;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class TestBase {

	/*
	 * Initillizing all the below things WebDriver, Properties, Logs, ExtentReports, DB,
	 * Excel, Mail,
	 * 
	 */

	public static WebDriver driver;
	public static Properties config = new Properties();
	public static Properties OR = new Properties();
	public static FileInputStream fis;
	public static Logger log = Logger.getLogger("devpinoyLogger");
	public static WebDriverWait eWait;
	public static String sysUserDir = System.getProperty("user.dir");
	public ExtentReports repo = ExtentManager.getInstance(); 
	public static ExtentTest test;

	@BeforeSuite
	public void setUp() {
		if (driver == null) {
			try {
				fis = new FileInputStream(sysUserDir + "/src/test/resources/properties/Config.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				config.load(fis);
				log.debug("Config file loaded.");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				fis = new FileInputStream(sysUserDir + "/src/test/resources/properties/OR.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				OR.load(fis);
				log.debug("OR file loaded.");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (config.getProperty("browser").equals("firefox")) {
				System.setProperty("webdriver.gecko.driver",
						sysUserDir + "/src/test/resources/executables/geckodriver");
				driver = new FirefoxDriver();
			} else if (config.getProperty("browser").equals("chrome")) {
//				System.setProperty("webdriver.chrome.driver", sysRootPath+"/src/test/resources/executables/chromedriver");
				System.setProperty("webdriver.chrome.driver", "/Users/mehuljamod/Documents/drivers/chromedriver");
				driver = new ChromeDriver();
				log.debug("chrome lunched.");
			}

			driver.get(config.getProperty("testsiteurl"));
			log.debug("navigate to: "+config.getProperty("testsiteurl"));
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicitly.wait")),
					TimeUnit.SECONDS);
			eWait = new WebDriverWait(driver, 5);
		}
	}

	@AfterSuite
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
		log.debug("Test execution completed.");
	}
	
	public boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch(NoSuchElementException e) {
			return false;
		}
	}
	
	public void click(String locator) {
		WebDriverWait eWait = new WebDriverWait(driver, 10);
		if(locator.endsWith("_CSS"))
			eWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(OR.getProperty(locator)))).click();
//			driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
		else if(locator.endsWith("_XPATH"))
			eWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(OR.getProperty(locator)))).click();
//			driver.findElement(By.xpath(OR.getProperty(locator))).click();
		else if(locator.endsWith("_ID"))
			eWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(OR.getProperty(locator)))).click();
//			driver.findElement(By.id(OR.getProperty(locator))).click();
		log.debug("Clicking on: "+locator);
		test.log(LogStatus.INFO, "Clicking on: "+locator);
	}
	
	public void type(String locator, String value) {
		if(locator.endsWith("_CSS"))
			driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
		else if(locator.endsWith("_XPATH"))
			driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);
		else if(locator.endsWith("_ID"))
			driver.findElement(By.id(OR.getProperty(locator))).sendKeys(value);
		log.debug("Typing in: "+locator+" enter value as "+value);
		test.log(LogStatus.INFO, "Typing in: "+locator+" enter value as "+value);
	}
	
	static WebElement dropdown;
	public void select(String locator, String value) {
		if(locator.endsWith("_CSS"))
			dropdown = driver.findElement(By.cssSelector(OR.getProperty(locator)));
		else if(locator.endsWith("_XPATH"))
			dropdown = driver.findElement(By.xpath(OR.getProperty(locator)));
		else if(locator.endsWith("_ID"))
			dropdown = driver.findElement(By.id(OR.getProperty(locator)));
		
		Select select = new Select(dropdown);
		select.selectByVisibleText(value);
		log.debug("Selecting from dropdown: "+locator+" value as "+value);
		test.log(LogStatus.INFO, "Selecting from dropdown: "+locator+" value as "+value);
	}
	
	public String getElementText(String locator) {
		WebElement ele = null;
		if(locator.endsWith("_CSS"))
			ele = driver.findElement(By.cssSelector(OR.getProperty(locator)));
		else if(locator.endsWith("_XPATH"))
			ele = driver.findElement(By.xpath(OR.getProperty(locator)));
		else if(locator.endsWith("_ID"))
			ele = driver.findElement(By.id(OR.getProperty(locator)));
		
		String txt = ele.getText();
		log.debug("Getting text from element: "+locator+" text is "+txt);
		test.log(LogStatus.INFO, "Getting text from element: "+locator+" text is "+txt);
		return txt;
	}
	
	public static void verifyEquals(String expected, String actual) throws IOException {
		try {
			Assert.assertEquals(actual, expected);
			TestUtil.captureScreenshot();
			Reporter.log("<br>"+"Varification success: expected ["+expected+"] but found ["+actual+"]");
			Reporter.log("<a target=\"_blank\" href="+TestUtil.screenshotName+">screenshot</a>");
			
			test.log(LogStatus.PASS, "Verification success: expected ["+expected+"] but found ["+actual+"]");
			test.log(LogStatus.PASS, test.addScreenCapture(TestUtil.screenshotName));
		} catch (Throwable t) {
			TestUtil.captureScreenshot();
			Reporter.log("<br>"+"Varification failure:"+t.getMessage());
			Reporter.log("<a target=\"_blank\" href="+TestUtil.screenshotName+">screenshot</a>");
			
			test.log(LogStatus.FAIL, "Verification failed with exeception: "+t.getMessage());
			test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenshotName));
		}
	}
}
