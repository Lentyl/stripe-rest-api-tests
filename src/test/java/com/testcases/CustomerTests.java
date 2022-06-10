package com.testcases;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.framework.setUp.BaseTest;
import com.utilities.DataUtil;
import com.utilities.RunTimeTestData;
import com.utilities.TestUtil;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import com.framework.apis.CustomerAPI;


import java.util.Hashtable;

@Listeners(com.listeners.ExtentListeners.class)
public class CustomerTests extends BaseTest {

	@Test(dataProviderClass = DataUtil.class, dataProvider = "data")
	public void createCustomerWithValidKey(Hashtable <String, String> data) {	
		Response res = CustomerAPI.postRqCreateCustomerWithValidKey(data);
		Assert.assertEquals(res.statusCode(), 200);
	}

	@Test(dataProviderClass = DataUtil.class, dataProvider = "data")
	public void createCustomerWithInvalidKey(Hashtable <String, String> data) {
		Response res = CustomerAPI.postRqCreateCustomerWithInvalidKey(data);

		Assert.assertEquals(res.statusCode(), 401);
		Assert.assertTrue(TestUtil.jsonHasValue(res.asString(), "\"message\": \"Invalid API Key provided: sk_test_"));
	}
	
	@Test(dataProviderClass = DataUtil.class, dataProvider = "data")
	public void listAllCustomers(Hashtable <String, String> data) {
		Response res = CustomerAPI.getRqListAllCustomers(data);
		
		Assert.assertEquals(res.statusCode(), 200);
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "data[0].email"), "michael88@wp.pl");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "data[0].name"), "Michael Jordan");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "data[1].email"), "robert@wp.pl");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "data[1].name"), "Robert Materka");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "data[2].email"), "mariusz88@gmial.com");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "data[2].name"), "Mariusz Podgorski");
	}
	
	@Test(dataProviderClass = DataUtil.class, dataProvider = "data")
	public void getCustomersWithSpecifiedEmailAddress(Hashtable <String, String> data) {
		Response res = CustomerAPI.getRqCustomersWithSpecifiedEmailAddress(data);
				
		Assert.assertEquals(res.statusCode(), 200);
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "data[0].email"), data.get("email"));
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "data[0].name"), data.get("name"));
	}
	
	@Test(dataProviderClass = DataUtil.class, dataProvider = "data")
	public void createCustomerWithInvalidBalance(Hashtable <String, String> data) {
		Response res = CustomerAPI.postRqCreateCustomerWithInvalidBalance(data);
		
		Assert.assertEquals(res.statusCode(), 400);
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "error.message"), "Received unknown parameter: balance");
	}
	
	@Test(dataProviderClass = DataUtil.class, dataProvider = "data")
	public void createCustomerWithInvalidPhoneNumber(Hashtable <String, String> data) {
		Response res = CustomerAPI.postRqCreateCustomerWithInvalidPhoneNumber(data);

		Assert.assertEquals(res.statusCode(), 400);
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "error.message"), "Received unknown parameter: phone");
	}
	
	@Test(dataProviderClass = DataUtil.class, dataProvider = "data")
	public void createCustomerWithInvalidEmail(Hashtable <String, String> data) {
		Response res = CustomerAPI.postRqCustomerWithInvalidEmail(data);
		
		res.prettyPrint();
		Assert.assertEquals(res.statusCode(), 400);
		
		//Assert.assertEquals(CustomerAPI.postRqCreateCustomerWithInvalidKey(data).statusCode(), 401);
	}
	
	@Test(dataProviderClass = DataUtil.class, dataProvider = "data")
	public void searchExistingtwoCustomers(Hashtable <String, String> data) {
		//Assert.assertEquals(CustomerAPI.postRqCreateCustomerWithInvalidKey(data).statusCode(), 401);
	}
	
	@Test(dataProviderClass = DataUtil.class, dataProvider = "data")
	public void updateCustomer(Hashtable <String, String> data) {
		//Assert.assertEquals(CustomerAPI.postRqCreateCustomerWithInvalidKey(data).statusCode(), 401);
	}
	
	@Test(dataProviderClass = DataUtil.class, dataProvider = "data")
	public void deleteCustomer(Hashtable <String, String> data) {
		//Assert.assertEquals(CustomerAPI.deleteRqDeleteCustomer(data).statusCode(), 200);
	}
	
	@Test(dataProviderClass = DataUtil.class, dataProvider = "data")
	public void RetrieveCustomer(Hashtable <String, String> data) {
		//Assert.assertEquals(CustomerAPI.postRqCreateCustomerWithInvalidKey(data).statusCode(), 401);
	}
}
