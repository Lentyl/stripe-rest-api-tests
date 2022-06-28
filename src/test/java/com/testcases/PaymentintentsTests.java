package com.testcases;

import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.framework.apis.PaymentintentsAPI;
import com.framework.setUp.BaseTest;
import com.utilities.DataUtil;
import com.utilities.TestUtil;

import io.restassured.response.Response;

@Listeners(com.listeners.ExtentListeners.class)
public class PaymentintentsTests extends BaseTest {

	@Test(priority=1 ,dataProviderClass = DataUtil.class, dataProvider = "data")
	public void createValidPaymentintent(Hashtable <String, String> data) {	
		Response res = PaymentintentsAPI.postRqCreateValidPaymentintent(data);

		Assert.assertEquals(res.statusCode(), 200);
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "amount"), data.get("amount"));
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "currency"), data.get("currency"));
	}
	
	@Test(priority=2 ,dataProviderClass = DataUtil.class, dataProvider = "data")
	public void createPaymentintentWithoutValidAmount(Hashtable <String, String> data) {	
		Response res = PaymentintentsAPI.postRqCreatePaymentintentWithoutValidAmount(data);

		Assert.assertEquals(res.statusCode(), 400);
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "error.code"), "parameter_missing");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "error.message"), "Missing required param: amount.");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "error.param"), "amount");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "error.type"), "invalid_request_error");
	}
	
	@Test(priority=3 ,dataProviderClass = DataUtil.class, dataProvider = "data")
	public void createPaymentintentWithoutValidCurrency(Hashtable <String, String> data) {	
		Response res = PaymentintentsAPI.postRqCreatePaymentintentWithoutValidCurrency(data);
		
		Assert.assertEquals(res.statusCode(), 400, "This fail is caused by API defect!");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "error.code"), "parameter_missing", "This fail is caused by API defect!");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "error.message"), "Missing required param: currency.", "This fail is caused by API defect!");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "error.param"), "currency", "This fail is caused by API defect!");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "error.type"), "invalid_request_error", "This fail is caused by API defect!");
	}
	
	@Test(priority=4 ,dataProviderClass = DataUtil.class, dataProvider = "data")
	public void retrievePaymentintent(Hashtable <String, String> data) {	
		Response res = PaymentintentsAPI.getRqRetrievePaymentintent(data);
		
		Assert.assertEquals(res.statusCode(), 200);
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "amount"), "299");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "currency"), "pln");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "status"), "requires_payment_method");
	}
	
	@Test(priority=5 ,dataProviderClass = DataUtil.class, dataProvider = "data")
	public void updatePaymentintent(Hashtable <String, String> data) {	
		Response res = PaymentintentsAPI.postRqUpdatePaymentintent(data);
		
		Assert.assertEquals(res.statusCode(), 200);
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "amount"), "500");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "currency"), "usd");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "status"), "requires_payment_method");
		
	}
	
	@Test(priority=6 ,dataProviderClass = DataUtil.class, dataProvider = "data")
	public void confirmPaymentintentWithoutPaymentMethod(Hashtable <String, String> data) {	
		Response res = PaymentintentsAPI.postRqconfirmPaymentintentWithoutPaymentMethod(data);
		
		Assert.assertEquals(res.statusCode(), 400);
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "error.payment_intent.amount"), "500");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "error.message"), "You cannot confirm this PaymentIntent because it's missing a payment method. You can either update the PaymentIntent with a payment method and then confirm it again, or confirm it again directly with a payment method.");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "error.payment_intent.currency"), "usd");
	}
	
	@Test(priority=8 ,dataProviderClass = DataUtil.class, dataProvider = "data")
	public void cancelPaymentintent(Hashtable <String, String> data) {	
		Response res = PaymentintentsAPI.postRqCancelPaymentintent(data);
		
		Assert.assertEquals(res.statusCode(), 200);
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "amount"), "500");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "currency"), "usd");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "status"), "canceled");
	}
	
	@Test(priority=9 ,dataProviderClass = DataUtil.class, dataProvider = "data")
	public void listTwoPaymentintents(Hashtable <String, String> data) {	
		Response res = PaymentintentsAPI.getRqListTwoPaymentintents(data);
		
		Assert.assertEquals(res.statusCode(), 200);
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "data[0].amount"), "5000");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "data[0].currency"), "usd");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "data[0].status"), "requires_payment_method");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "data[1].amount"), "500");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "data[1].currency"), "usd");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "data[1].status"), "canceled");

	}
	
	@Test(priority=10 ,dataProviderClass = DataUtil.class, dataProvider = "data")
	public void searchPaymentintents(Hashtable <String, String> data) {	
		Response res = PaymentintentsAPI.getRqSearchPaymentintents(data);
		
		Assert.assertEquals(res.statusCode(), 200);
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "data[0].status"), "canceled");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "data[1].status"), "canceled");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "data[2].status"), "canceled");
	}
}
