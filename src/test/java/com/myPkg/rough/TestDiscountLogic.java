package com.myPkg.rough;

import com.myPkg.utilities.TestUtil;

public class TestDiscountLogic {
	public static void main(String[] args) {
		double discountPercentage = 5;
		double perUnitPrice = 30.51;
		double qty = 2;
		System.out.println(TestUtil.getDiscountItemPrice(discountPercentage, perUnitPrice, qty));
	}
}
