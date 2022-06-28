package com.framework.apis;

import static io.restassured.RestAssured.given;

import java.util.Hashtable;

import com.framework.setUp.BaseTest;
import io.restassured.response.Response;

public class BalanceAPI extends BaseTest {
	
	public static Response getRqRetrieveBalance(Hashtable<String, String> data) {

		Response response = given().auth().basic(config.getProperty("validKey"), "")
			.get(config.getProperty("balanceAPIEndPoint"));

		return response;
	}

	public static Response getRqRetrieveBalanceTransactionWithInvalidId(Hashtable<String, String> data) {
		
		Response response = given().auth().basic(config.getProperty("validKey"), "")
				.get(config.getProperty("balanceTransactionsAPIEndPoint")+"/"+data.get("id"));

		return response;
	}

	public static Response getRqListAllBalanceTransactions(Hashtable<String, String> data) {
		
		Response response = given().auth().basic(config.getProperty("validKey"), "")
				.get(config.getProperty("balanceTransactionsAPIEndPoint"));

		return response;
	}
}
