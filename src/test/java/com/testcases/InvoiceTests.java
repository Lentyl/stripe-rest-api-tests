package com.testcases;

import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.framework.apis.InvoiceAPI;
import com.framework.setUp.BaseTest;
import com.utilities.DataUtil;
import com.utilities.TestUtil;

import io.restassured.response.Response;

@Listeners(com.listeners.ExtentListeners.class)
public class InvoiceTests extends BaseTest {
	
	@Test(priority=1 ,dataProviderClass = DataUtil.class, dataProvider = "data")
	public void createInvoiceItem(Hashtable <String, String> data) {	
		Response res = InvoiceAPI.postRqCreateInvoiceItem(data);
		
		Assert.assertEquals(res.statusCode(), 200);
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "amount"), data.get("amount"));
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "currency"), data.get("currency"));
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "description"), data.get("description"));
	}
	
	@Test(priority=2 ,dataProviderClass = DataUtil.class, dataProvider = "data")
	public void createInvoice(Hashtable <String, String> data) {	
		Response res = InvoiceAPI.postRqCreateInvoice(data);

		Assert.assertEquals(res.statusCode(), 200);
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "object"), "invoice");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "account_country"), "PL");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "currency"), "pln");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "customer_email"), "krowa88@wp.pl");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "customer_name"), "Marek Krowa");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "description"), data.get("description"));
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "lines.data[0].amount"), "200");
	}
	
	@Test(priority=3 ,dataProviderClass = DataUtil.class, dataProvider = "data")
	public void createInvoiceWithoutValidCustomerId(Hashtable <String, String> data) {	
		Response res = InvoiceAPI.postRqCreateInvoiceWithoutValidCustomerId(data);

		Assert.assertEquals(res.statusCode(), 401);
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "error.type"), "invalid_request_error");
		Assert.assertTrue(TestUtil.jsonHasValue(res.asString(), "\"message\": \"Invalid API Key provided: sk_test_"));
	}
	
	@Test(priority=4 ,dataProviderClass = DataUtil.class, dataProvider = "data")
	public void updateInvoice(Hashtable <String, String> data) {	
		Response res = InvoiceAPI.postRqUpdateInvoice(data);
		
		Assert.assertEquals(res.statusCode(), 200);
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "description"), data.get("description"));
	}
	
	@Test(priority=5 ,dataProviderClass = DataUtil.class, dataProvider = "data")
	public void retrieveInvoice(Hashtable <String, String> data) {	
		Response res = InvoiceAPI.getRqRetrieveInvoice(data);
		
		Assert.assertEquals(res.statusCode(), 200);
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "object"), "invoice");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "account_country"), "PL");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "currency"), "pln");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "customer_email"), "krowa88@wp.pl");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "customer_name"), "Marek Krowa");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "description"), "Updated invoice");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "lines.data[0].amount"), "200");
	}
	
	@Test(priority=6 ,dataProviderClass = DataUtil.class, dataProvider = "data")
	public void finalizeInvoice(Hashtable <String, String> data) {
		Response res = InvoiceAPI.postRqFinalizeInvoice(data);
		
		Assert.assertEquals(res.statusCode(), 200);
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "currency"), "pln");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "customer_email"), "krowa88@wp.pl");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "customer_name"), "Marek Krowa");
	}
	

	
	@Test(priority=7 ,dataProviderClass = DataUtil.class, dataProvider = "data")
	public void payInvoiceThatWasAlreadyPaid(Hashtable <String, String> data) {	
		Response res = InvoiceAPI.postRqPayInvoiceThatWasAlreadyPaidpayInvoice(data);
		
		Assert.assertEquals(res.statusCode(), 400);
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "error.message"), "Invoice is already paid");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "type"), "invalid_request_error");
	}
	
	@Test(priority=8 ,dataProviderClass = DataUtil.class, dataProvider = "data")
	public void payInvoice(Hashtable <String, String> data) {	
		Response res = InvoiceAPI.postRqPayInvoice(data);
		
		Assert.assertEquals(res.statusCode(), 200);
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "object"), "invoice");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "account_country"), "PL");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "currency"), "pln");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "customer_email"), "krowa88@wp.pl");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "customer_name"), "Marek Krowa");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "description"), "Invoice to be paid");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "lines.data[0].amount"), "200");
	}
	
	@Test(priority=9 ,dataProviderClass = DataUtil.class, dataProvider = "data")
	public void voidInvoiceThatWasPaid(Hashtable <String, String> data) {	
		Response res = InvoiceAPI.postRqVoidInvoiceThatWasPaid(data);

		Assert.assertEquals(res.statusCode(), 400);
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "error.message"), "You can only pass in open invoices. This invoice isn't open.");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "error.type"), "invalid_request_error");
	}

	@Test(priority=10 ,dataProviderClass = DataUtil.class, dataProvider = "data")
	public void markInvoiceAsUncollectible(Hashtable <String, String> data) {	
		Response res = InvoiceAPI.postRqMarkInvoiceAsUncollectible(data);
		
		Assert.assertEquals(res.statusCode(), 200);
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "collection_method"), "charge_automatically");
	}
	
	@Test(priority=11 ,dataProviderClass = DataUtil.class, dataProvider = "data")
	public void listTwoLastInvoices(Hashtable <String, String> data) {	
		Response res = InvoiceAPI.getRqListTwoLastInvoices(data);
		
		Assert.assertEquals(res.statusCode(), 200);
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "object"), "list");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "data[0].description"), "Invoice to be paid");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "data[1].description"), "Updated invoice");
	}

	@Test(priority=12 ,dataProviderClass = DataUtil.class, dataProvider = "data")
	public void searchInvoices(Hashtable <String, String> data) {	
		Response res = InvoiceAPI.getRqSearchInvoices(data);

		Assert.assertEquals(res.statusCode(), 200);
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "object"), "search_result");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "data[0].account_country"), "PL");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "data[0].currency"), "pln");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "data[0].customer_email"), "jakub88@wp.pl");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "data[0].customer_name"), "Jakub Podgórski");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "data[0].lines.data[0].amount"), "1800");
	}
		

	@Test(priority=13 ,dataProviderClass = DataUtil.class, dataProvider = "data")
	public void deleteDraftInvoice(Hashtable <String, String> data) {	
		Response res = InvoiceAPI.deleteRqDeleteDraftInvoice(data);
		
		Assert.assertEquals(res.statusCode(), 200);
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "deleted"), "true");
	
	}
	
	@Test(priority=13 ,dataProviderClass = DataUtil.class, dataProvider = "data")
	public void deletePaidInvoice(Hashtable <String, String> data) {	
		Response res = InvoiceAPI.deleteRqDeletePaidInvoice(data);

		Assert.assertEquals(res.statusCode(), 404);
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "error.code"), "resource_missing");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "error.param"), "id");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "error.type"), "invalid_request_error");
		Assert.assertTrue(TestUtil.jsonHasValue(res.asString(), "\"message\": \"No such invoice:"));
	
	}
	
}
