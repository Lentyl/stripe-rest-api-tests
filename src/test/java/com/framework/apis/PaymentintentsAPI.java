package com.framework.apis;

import static com.utilities.TestUtil.getJsonKeyValue;
import static io.restassured.RestAssured.given;

import java.util.Hashtable;

import com.framework.setUp.BaseTest;
import com.utilities.RunTimeTestData;

import io.restassured.response.Response;

public class PaymentintentsAPI extends BaseTest {
	
	public static Response postRqCreateValidPaymentintent(Hashtable<String, String> data) {

		Response response = given().auth().basic(config.getProperty("validKey"), "")
			.formParam("amount", data.get("amount"))
			.formParam("currency", data.get("currency"))
			.post(config.getProperty("paymentintentsAPIEndPoint"));
		
		
		if (getJsonKeyValue(response, "amount").equals("299") && getJsonKeyValue(response, "currency").equals("pln"))
			RunTimeTestData.paymentintentID = getJsonKeyValue(response, "id");

		return response;
	}
	
	public static Response postRqCreatePaymentintentWithoutValidAmount(Hashtable<String, String> data) {

		Response response = given().auth().basic(config.getProperty("validKey"), "")
			.formParam("currency", data.get("currency"))
			.post(config.getProperty("paymentintentsAPIEndPoint"));

		return response;
	}
	
	public static Response postRqCreatePaymentintentWithoutValidCurrency(Hashtable<String, String> data) {

		Response response = given().auth().basic(config.getProperty("validKey"), "")
			.formParam("amount", data.get("amount"))
			.post(config.getProperty("paymentintentsAPIEndPoint"));

		return response;
	}

	public static Response getRqRetrievePaymentintent(Hashtable<String, String> data) {

		Response response = given().auth().basic(config.getProperty("validKey"), "")
			.get(config.getProperty("paymentintentsAPIEndPoint")+"/"+RunTimeTestData.paymentintentID);

		return response;
	}
	
	public static Response postRqUpdatePaymentintent(Hashtable<String, String> data) {
		
		Response response = given().auth().basic(config.getProperty("validKey"), "")
			.formParam("amount", data.get("amount"))
			.formParam("currency", data.get("currency"))
			.post(config.getProperty("paymentintentsAPIEndPoint")+"/"+RunTimeTestData.paymentintentID);

		return response;
	}
	
	public static Response postRqconfirmPaymentintentWithoutPaymentMethod(Hashtable<String, String> data) {

		Response response = given().auth().basic(config.getProperty("validKey"), "")
			.post(config.getProperty("paymentintentsAPIEndPoint")+"/"+RunTimeTestData.paymentintentID+"/confirm");

		return response;
	}
	
	public static Response postRqCancelPaymentintent(Hashtable<String, String> data) {

		Response response = given().auth().basic(config.getProperty("validKey"), "")
			.post(config.getProperty("paymentintentsAPIEndPoint")+"/"+RunTimeTestData.paymentintentID+"/cancel");

		return response;
	}
	
	public static Response getRqListTwoPaymentintents(Hashtable<String, String> data) {

		Response response = given().auth().basic(config.getProperty("validKey"), "")
			.formParam("limit", "2")
			.get(config.getProperty("paymentintentsAPIEndPoint"));

		return response;
	}
	
	public static Response getRqSearchPaymentintents(Hashtable<String, String> data) {

		Response response = given().auth().basic(config.getProperty("validKey"), "")
			.formParam("query", "status:'canceled'")
			.get(config.getProperty("paymentintentsAPIEndPoint")+"/search");

		return response;
	}
}
