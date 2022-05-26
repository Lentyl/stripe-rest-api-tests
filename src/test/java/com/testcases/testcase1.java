package com.testcases;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.framework.setUp.BaseTest;
import com.utilities.DataUtil;
import com.framework.apis.CreateCustomerAPI;


import java.util.Hashtable;

@Listeners(com.listeners.ExtentListeners.class)
public class testcase1 extends BaseTest {

	@Test(dataProviderClass = DataUtil.class, dataProvider = "data")
	public void createCustomerWithValidKey(Hashtable <String, String> data) {		
		Assert.assertEquals(CreateCustomerAPI.postRqCreateCustomerWithValidKey(data).statusCode(), 200);

	}

	@Test(dataProviderClass = DataUtil.class, dataProvider = "data")
	public void createCustomerWithInvalidKey(Hashtable <String, String> data) {
		Assert.assertEquals(CreateCustomerAPI.postRqCreateCustomerWithInvalidKey(data).statusCode(), 401);
	}

}
