package com.framework.apis;

import static io.restassured.RestAssured.given;

import java.util.Hashtable;

import com.framework.setUp.BaseTest;

import io.restassured.response.Response;

public class CreateCustomerAPI extends BaseTest {
	
	public static Response postRqCreateCustomerWithValidKey(Hashtable <String, String> data) {
		
		Response response = given().auth().basic(config.getProperty("validKey"), "")
				.formParam("email", data.get("email"))
				.formParam("description", data.get("description"))
				.formParam("name", data.get("name"))
				.post(config.getProperty("customerAPIEndPoint"));
		
		return response;
		
	}
	
	public static Response postRqCreateCustomerWithInvalidKey(Hashtable <String, String> data) {
		
		Response response = given().auth().basic(config.getProperty("invalidKey"), "")
				.formParam("email", data.get("email"))
				.formParam("description", data.get("description"))
				.formParam("name", data.get("name"))
				.post(config.getProperty("customerAPIEndPoint"));
		
		return response;
	}

}
