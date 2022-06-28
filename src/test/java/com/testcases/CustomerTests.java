package com.testcases;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.framework.setUp.BaseTest;
import com.utilities.DataUtil;
import com.utilities.RunTimeTestData;
import com.utilities.TestUtil;
import io.restassured.response.Response;

import com.framework.apis.CustomerAPI;


import java.util.Hashtable;

@Listeners(com.listeners.ExtentListeners.class)
public class CustomerTests extends BaseTest {

	@Test(priority=1 ,dataProviderClass = DataUtil.class, dataProvider = "data")
	public void createCustomerWithValidKey(Hashtable <String, String> data) {	
		Response res = CustomerAPI.postRqCreateCustomerWithValidKey(data);
		
		Assert.assertEquals(res.statusCode(), 200);
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "email"), data.get("email"));
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "description"), data.get("description"));
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "name"), data.get("name"));

	}

	@Test(priority=2, dataProviderClass = DataUtil.class, dataProvider = "data")
	public void createCustomerWithInvalidKey(Hashtable <String, String> data) {
		Response res = CustomerAPI.postRqCreateCustomerWithInvalidKey(data);

		Assert.assertEquals(res.statusCode(), 401);
		Assert.assertTrue(TestUtil.jsonHasValue(res.asString(), "\"message\": \"Invalid API Key provided: sk_test_"));
	}
	
	@Test(priority=3, dataProviderClass = DataUtil.class, dataProvider = "data")
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
	
	@Test(priority=4, dataProviderClass = DataUtil.class, dataProvider = "data")
	public void searchExistingTwoCustomers(Hashtable <String, String> data) {
		Response res = CustomerAPI.getRqSearchExistingTwoCustomers(data);

		Assert.assertEquals(res.statusCode(), 200);
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "data[0].email"), "michael88@wp.pl");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "data[0].name"), "Michael Jordan");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "data[1].email"), "robert@wp.pl");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "data[1].name"), "Robert Materka");
	}
	
	@Test(priority=5, dataProviderClass = DataUtil.class, dataProvider = "data")
	public void searchCustomersWithValidQueryParameter(Hashtable <String, String> data) {
		Response res = CustomerAPI.getRqSearchCustomersWithValidQueryParameter(data);

		Assert.assertEquals(res.statusCode(), 200);
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "data[0].name"), "Marek Krowa");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "data[0].email"), "krowa88@wp.pl");
	}
	
	@Test(priority=6, dataProviderClass = DataUtil.class, dataProvider = "data")
	public void createCustomerWithInvalidPhoneNumber(Hashtable <String, String> data) {
		Response res = CustomerAPI.postRqCreateCustomerWithInvalidPhoneNumber(data);
		
		Assert.assertEquals(res.statusCode(), 400, "This fail is caused by API defect!");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "error.message"), "Received unknown parameter: phone");
	}
	
	@Test(priority=7, dataProviderClass = DataUtil.class, dataProvider = "data")
	public void createCustomerWithInvalidEmail(Hashtable <String, String> data) {
		Response res = CustomerAPI.postRqCreateCustomerWithInvalidEmail(data);
		
		Assert.assertEquals(res.statusCode(), 400, "This fail is caused by API defect!");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "error.message"), "Received unknown parameter: email");
	}
	
	@Test(priority=8, dataProviderClass = DataUtil.class, dataProvider = "data")
	public void getCustomersWithSpecifiedEmailAddress(Hashtable <String, String> data) {
		Response res = CustomerAPI.getRqCustomersWithSpecifiedEmailAddress(data);

		Assert.assertEquals(res.statusCode(), 200);
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "data[0].email"), data.get("email"));
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "data[0].name"), data.get("name"));
	}
	
	@Test(priority=9, dataProviderClass = DataUtil.class, dataProvider = "data")
	public void searchWithoutQueryParameter(Hashtable <String, String> data) {
		Response res = CustomerAPI.getRqSearchWithoutQueryParameter(data);
		
		Assert.assertEquals(res.statusCode(), 400);
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "error.message"), "Missing required param: query.");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "error.code"), "parameter_missing");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "error.type"), "invalid_request_error");
	}
	
	@Test(priority=10, dataProviderClass = DataUtil.class, dataProvider = "data")
	public void createCustomerWithInvalidBalance(Hashtable <String, String> data) {
		Response res = CustomerAPI.postRqCreateCustomerWithInvalidBalance(data);
		
		Assert.assertEquals(res.statusCode(), 400);
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "error.message"), "Invalid integer: string");
	}
	
	@Test(priority=11, dataProviderClass = DataUtil.class, dataProvider = "data")
	public void updateCustomer(Hashtable <String, String> data) {
		Response res = CustomerAPI.postRqUpdateCustomer(data);
		
		Assert.assertEquals(res.statusCode(), 200);
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "description"), "Updating customer Mariusz Pdgorski");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "name"), "Mariusz Podgorski");
	}
	
	@Test(priority=12, dataProviderClass = DataUtil.class, dataProvider = "data")
	public void deleteCustomer(Hashtable <String, String> data) {
		Response res = CustomerAPI.deleteRqDeleteCustomer(data);

		Assert.assertEquals(res.statusCode(), 200);
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "object"), "customer");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "deleted"), "true");
	}
	
	@Test(priority=13, dataProviderClass = DataUtil.class, dataProvider = "data")
	public void RetrieveCustomer(Hashtable <String, String> data) {
		Response res = CustomerAPI.getRqRetrieveCustomer(data);
		
		Assert.assertEquals(res.statusCode(), 200);
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "id"), RunTimeTestData.customerIDList.get(0));
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "object"), "customer");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "deleted"), "true");
	}
}
