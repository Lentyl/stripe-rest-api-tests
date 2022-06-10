package com.framework.apis;

import static io.restassured.RestAssured.given;

import java.util.Hashtable;

import com.framework.setUp.BaseTest;
import com.utilities.RunTimeTestData;
import static com.utilities.TestUtil.*;

import io.restassured.response.Response;

public class CustomerAPI extends BaseTest {

	public static Response postRqCreateCustomerWithValidKey(Hashtable<String, String> data) {

		Response response = given().auth().basic(config.getProperty("validKey"), "")
				.formParam("email", data.get("email")).formParam("description", data.get("description"))
				.formParam("name", data.get("name")).post(config.getProperty("customerAPIEndPoint"));

		if (getJsonKeyValue(response, "name").equals("Mariusz Podgorski"))
			RunTimeTestData.customerID = getJsonKeyValue(response, "id");

		return response;

	}

	public static Response postRqCreateCustomerWithInvalidKey(Hashtable<String, String> data) {

		Response response = given().auth().basic(config.getProperty("invalidKey"), "")
				.formParam("email", data.get("email")).formParam("description", data.get("description"))
				.formParam("name", data.get("name")).post(config.getProperty("customerAPIEndPoint"));

		return response;
	}

	public static Response getRqListAllCustomers(Hashtable<String, String> data) {
		
		Response response = given().auth().basic(config.getProperty("validKey"), "")
				.get(config.getProperty("customerAPIEndPoint"));

		return response;
	}
	
	public static Response getRqCustomersWithSpecifiedEmailAddress(Hashtable<String, String> data) {
		
		Response response = given().auth().basic(config.getProperty("validKey"), "")
				.formParam("email", data.get("email"))
				.get(config.getProperty("customerAPIEndPoint"));

		return response;
	}
	
	public static Response postRqCreateCustomerWithInvalidBalance(Hashtable<String, String> data) {
		
		Response response = given().auth().basic(config.getProperty("validKey"), "")
				.formParam("balance", data.get("balance"))
				.get(config.getProperty("customerAPIEndPoint"));

		return response;
	}
	
	public static Response postRqCreateCustomerWithInvalidPhoneNumber(Hashtable<String, String> data) {
		
		Response response = given().auth().basic(config.getProperty("validKey"), "")
				.formParam("phone", data.get("phone"))
				.get(config.getProperty("customerAPIEndPoint"));

		return response;
	}
	
	public static Response postRqCustomerWithInvalidEmail(Hashtable<String, String> data) {
		
		Response response = given().auth().basic(config.getProperty("validKey"), "")
				.formParam("email", data.get("email"))
				.formParam("name", data.get("name"))
				.get(config.getProperty("customerAPIEndPoint"));

		return response;
	}
	
	public static Response deleteRqDeleteCustomer(Hashtable<String, String> data) {
		Response response = given().auth().basic(config.getProperty("validKey"), "")
				.delete(config.getProperty("customerAPIEndPoint")+"/"+RunTimeTestData.customerID);
		
		response.prettyPrint();

		return response;
	}

}
