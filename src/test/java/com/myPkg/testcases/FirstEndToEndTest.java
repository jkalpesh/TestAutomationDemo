package com.myPkg.testcases;

import java.io.IOException;

import org.testng.annotations.Test;

import com.myPkg.base.TestBase;
import com.myPkg.utilities.TestUtil;

public class FirstEndToEndTest extends TestBase {

	@Test
	public void testCase1() throws InterruptedException, IOException {
		type("searchQueryTxt_CSS", "Printed Summer Dress");
		click("searchBtn_CSS");
		click("firstItemFromList_CSS");
		click("blueColor_CSS");
		select("sizeSelector_CSS", Character.toString('M'));
		click("qtyBtnPlus_CSS");
		click("addToCartBtn_CSS");
		click("proceedToCheckoutBtn_CSS");		
		
		// for qty 2
		String expectedPrice = TestUtil.getDiscountItemPrice(5, 30.51, 2); 
		String actualPrice = getElementText("totalProduct_CSS");//"$57.96";
		verifyEquals(expectedPrice, actualPrice);
		
		click("qtyBtnPlus51900_XPATH");
		Thread.sleep(3000);
		// for qty 3
		expectedPrice = TestUtil.getDiscountItemPrice(5, 30.51, 3); 
		actualPrice = getElementText("totalProduct_CSS");//"$86.94";
		verifyEquals(expectedPrice, actualPrice);
	}
}
