package com.framework.apis;

import static com.utilities.TestUtil.getJsonKeyValue;
import static io.restassured.RestAssured.given;

import java.util.Hashtable;

import com.framework.setUp.BaseTest;
import com.utilities.RunTimeTestData;
import io.restassured.response.Response;

public class ProductAPI extends BaseTest {
	
	
	public static Response postRqCreateProduct(Hashtable<String, String> data) {

		Response response = given().auth().basic(config.getProperty("validKey"), "")
			.formParam("name", data.get("name"))
			.formParam("description", data.get("description"))
			.formParam("active", data.get("active"))
			.post(config.getProperty("productsAPIEndPoint"));

			RunTimeTestData.productIdList.add(getJsonKeyValue(response, "id"));
		
		return response;
	}
	
	public static Response postRqCreateProductWithoutValidName(Hashtable<String, String> data) {

		Response response = given().auth().basic(config.getProperty("validKey"), "")
			.post(config.getProperty("productsAPIEndPoint"));

		return response;
	}
	
	public static Response getRqRetrieveProduct(Hashtable<String, String> data) {

		Response response = given().auth().basic(config.getProperty("validKey"), "")
			.get(config.getProperty("productsAPIEndPoint")+"/"+RunTimeTestData.productIdList.get(1));

		return response;
	}
	
	public static Response postRqUpdateProduct(Hashtable<String, String> data) {

		Response response = given().auth().basic(config.getProperty("validKey"), "")
			.formParam("name", data.get("name"))
			.formParam("description", data.get("description"))
			.formParam("active", data.get("active"))
			.post(config.getProperty("productsAPIEndPoint")+"/"+RunTimeTestData.productIdList.get(0));

		return response;
	}
	
	public static Response getRqListAllActiveProducts(Hashtable<String, String> data) {

		Response response = given().auth().basic(config.getProperty("validKey"), "")
			.formParam("active", data.get("active"))
			.get(config.getProperty("productsAPIEndPoint"));

		return response;
	}
	
	public static Response getRqSearchUpdatedProduct(Hashtable<String, String> data) {

		Response response = given().auth().basic(config.getProperty("validKey"), "")
			.get(config.getProperty("productsAPIEndPoint")+"/"+RunTimeTestData.productIdList.get(0));

		return response;
	}
	
	public static Response deleteRqDeleteProduct(Hashtable<String, String> data) {

		Response response = given().auth().basic(config.getProperty("validKey"), "")
			.delete(config.getProperty("productsAPIEndPoint")+"/"+RunTimeTestData.productIdList.get(Integer.parseInt(data.get("productIdIndex"))));

		return response;
	}
	
	public static Response getRqSearchDeletedProduct(Hashtable<String, String> data) {

		Response response = given().auth().basic(config.getProperty("validKey"), "")
			.get(config.getProperty("productsAPIEndPoint")+"/"+RunTimeTestData.productIdList.get(Integer.parseInt(data.get("productIdIndex"))));

		return response;
	}
}
