package com.framework.apis;

import static com.utilities.TestUtil.getJsonKeyValue;
import static io.restassured.RestAssured.given;

import java.util.Hashtable;

import com.framework.setUp.BaseTest;

import com.utilities.RunTimeTestData;

import io.restassured.response.Response;

public class InvoiceAPI extends BaseTest {
	
	
	public static Response postRqCreateInvoiceItem(Hashtable<String, String> data) {

		Response response = given().auth().basic(config.getProperty("validKey"), "")
			.formParam("customer", data.get("customerId"))
			.formParam("amount", data.get("amount"))
			.formParam("currency", data.get("currency"))
			.formParam("description", data.get("description"))
			.post(config.getProperty("invoiceitemsAPIEndPoint"));
		
		return response;
	}
	
	public static Response postRqCreateInvoice(Hashtable<String, String> data) {

		Response response = given().auth().basic(config.getProperty("validKey"), "")
			.formParam("customer", data.get("customerId"))
			.formParam("description", data.get("description"))
			.post(config.getProperty("invoicesAPIEndPoint"));

		if (getJsonKeyValue(response, "customer").equals(data.get("customerId")))
			RunTimeTestData.invoiceID = getJsonKeyValue(response, "id");
		
		return response;
	}
	
	public static Response postRqCreateInvoiceWithoutValidCustomerId(Hashtable<String, String> data) {

		Response response = given().auth().basic(config.getProperty("invalidKey"), "")
			.post(config.getProperty("invoicesAPIEndPoint"));

		return response;
	}
	
	public static Response getRqRetrieveInvoice(Hashtable<String, String> data) {

		Response response = given().auth().basic(config.getProperty("validKey"), "")
			.get(config.getProperty("invoicesAPIEndPoint")+"/"+RunTimeTestData.invoiceID);

		return response;
	}
	
	public static Response postRqUpdateInvoice(Hashtable<String, String> data) {

		Response response = given().auth().basic(config.getProperty("validKey"), "")
			.formParam("description", data.get("description"))
			.formParam("collection_method", data.get("send_invoice"))
			.post(config.getProperty("invoicesAPIEndPoint")+"/"+RunTimeTestData.invoiceID);

		return response;
	}
	
	public static Response postRqFinalizeInvoice(Hashtable<String, String> data) {

		Response response = given().auth().basic(config.getProperty("validKey"), "")
			.post(config.getProperty("invoicesAPIEndPoint")+"/"+RunTimeTestData.invoiceID+"/finalize");

		return response;
	}
	
	public static Response postRqPayInvoiceThatWasAlreadyPaidpayInvoice(Hashtable<String, String> data) {

		Response response = given().auth().basic(config.getProperty("validKey"), "")
			.post(config.getProperty("invoicesAPIEndPoint")+"/"+RunTimeTestData.invoiceID+"/pay");

		return response;
	}
	
	public static Response postRqPayInvoice(Hashtable<String, String> data) {

		postRqCreateInvoiceItem(data);
		
		Response invoiceResponse = postRqCreateInvoice(data);
		
		Response response = given().auth().basic(config.getProperty("validKey"), "")
			.post(config.getProperty("invoicesAPIEndPoint")+"/"+getJsonKeyValue(invoiceResponse, "id")+"/pay");

		return response;
	}
	
	
	public static Response postRqVoidInvoiceThatWasPaid(Hashtable<String, String> data) {

		Response response = given().auth().basic(config.getProperty("validKey"), "")
			.post(config.getProperty("invoicesAPIEndPoint")+"/"+RunTimeTestData.invoiceID+"/void");

		return response;
	}
	
	public static Response postRqMarkInvoiceAsUncollectible(Hashtable<String, String> data) {

		Response response = given().auth().basic(config.getProperty("validKey"), "")
				.post(config.getProperty("invoicesAPIEndPoint")+"/"+RunTimeTestData.invoiceID);

		return response;
	}
	
	public static Response getRqRetrieveInvoicesLineItems(Hashtable<String, String> data) {

		Response response = given().auth().basic(config.getProperty("validKey"), "")
			.get(config.getProperty("invoicesAPIEndPoint"));

		return response;
	}
	
	public static Response getRqRetrieveUpcomingInvoice(Hashtable<String, String> data) {

		Response response = given().auth().basic(config.getProperty("validKey"), "")
			.get(config.getProperty("invoicesAPIEndPoint"));

		return response;
	}
	
	public static Response getRqListTwoLastInvoices(Hashtable<String, String> data) {

		Response response = given().auth().basic(config.getProperty("validKey"), "")
			.formParam("limit", "2")
			.get(config.getProperty("invoicesAPIEndPoint"));

		return response;
	}
	
	public static Response getRqSearchInvoices(Hashtable<String, String> data) {

		Response response = given().auth().basic(config.getProperty("validKey"), "")
			.formParam("query", "number:\""+data.get("invoiceNumber")+"\"")
			.get(config.getProperty("invoicesAPIEndPoint")+"/search");

		return response;
	}
	
	public static Response deleteRqDeleteDraftInvoice(Hashtable<String, String> data) {
		
		postRqCreateInvoiceItem(data);
		
		Response invoiceResponse = postRqCreateInvoice(data);

		Response response = given().auth().basic(config.getProperty("validKey"), "")
			.formParam("active", data.get("active"))
			.delete(config.getProperty("invoicesAPIEndPoint")+"/"+getJsonKeyValue(invoiceResponse, "id"));

		return response;
	}
	
	public static Response deleteRqDeletePaidInvoice(Hashtable<String, String> data) {

		Response response = given().auth().basic(config.getProperty("validKey"), "")
			.formParam("active", data.get("active"))
			.delete(config.getProperty("invoicesAPIEndPoint")+"/"+RunTimeTestData.invoiceID);

		return response;
	}
}
