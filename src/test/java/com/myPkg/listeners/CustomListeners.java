package com.myPkg.listeners;

import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.myPkg.base.TestBase;
import com.myPkg.utilities.TestUtil;
import com.relevantcodes.extentreports.LogStatus;

public class CustomListeners extends TestBase implements ITestListener {

	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		test = repo.startTest(result.getName().toUpperCase());
		
	}

	public void onTestSuccess(ITestResult result) {
		test.log(LogStatus.PASS, result.getName().toUpperCase()+" PASS");
		repo.endTest(test);
		repo.flush();
	}

	public void onTestFailure(ITestResult result) {
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		try {
			TestUtil.captureScreenshot();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Reporter.log("Screen Shot capture.");
		Reporter.log("<a target=\"_blank\" href="+TestUtil.screenshotName+">screenshot</a>");
		
		test.log(LogStatus.FAIL, result.getName().toUpperCase()+" failed with exeception: "+result.getThrowable());
		test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenshotName));
		repo.endTest(test);
		repo.flush();
	}

	public void onTestSkipped(ITestResult result) {
		
		test.log(LogStatus.SKIP, result.getName().toUpperCase()+" Skipping this test as Run mode No.");
		repo.endTest(test);
		repo.flush();		
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

}
