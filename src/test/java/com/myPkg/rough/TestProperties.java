package com.myPkg.rough;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TestProperties {
	public static void main(String[] args) throws IOException {
		String sysRootPath = System.getProperty("user.dir");
		Properties config = new Properties();
		Properties OR = new Properties();
		
		FileInputStream fis = new FileInputStream(sysRootPath+"/src/test/resources/properties/Config.properties");
		config.load(fis);
		fis = new FileInputStream(sysRootPath+"/src/test/resources/properties/OR.properties");
		OR.load(fis);
		
		System.out.println(config.getProperty("testsiteurl"));
	}
}
