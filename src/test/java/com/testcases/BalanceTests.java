package com.testcases;

import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.framework.apis.BalanceAPI;
import com.framework.setUp.BaseTest;
import com.utilities.DataUtil;
import com.utilities.TestUtil;

import io.restassured.response.Response;

@Listeners(com.listeners.ExtentListeners.class)
public class BalanceTests extends BaseTest {
	
	@Test(priority=1 ,dataProviderClass = DataUtil.class, dataProvider = "data")
	public void retrieveBalance(Hashtable <String, String> data) {	
		Response res = BalanceAPI.getRqRetrieveBalance(data);
		
		Assert.assertEquals(res.statusCode(), 200);
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "object"), "balance");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "available[0].amount"), "0");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "available[0].currency"), "pln");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "pending[0].amount"), "0");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "pending[0].currency"), "pln");
	}
	
	@Test(priority=2 ,dataProviderClass = DataUtil.class, dataProvider = "data")
	public void retrieveBalanceTransactionWithInvalidId(Hashtable <String, String> data) {	
		Response res = BalanceAPI.getRqRetrieveBalanceTransactionWithInvalidId(data);

		Assert.assertEquals(res.statusCode(), 404);
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "error.code"), "resource_missing");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "error.message"), "No such balance transaction: 'cus_LsK4ym1Nil7fiN'");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "error.param"), "id");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "error.type"), "invalid_request_error");
	}
	
	@Test(priority=3 ,dataProviderClass = DataUtil.class, dataProvider = "data")
	public void listAllBalanceTransactions(Hashtable <String, String> data) {	
		Response res = BalanceAPI.getRqListAllBalanceTransactions(data);
		
		Assert.assertEquals(res.statusCode(), 200);
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "object"), "list");
	}
	
	@Test(priority=3 ,dataProviderClass = DataUtil.class, dataProvider = "data")
	public void createCharge(Hashtable <String, String> data) {	
		Response res = BalanceAPI.getRqListAllBalanceTransactions(data);
		
		Assert.assertEquals(res.statusCode(), 200);
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "object"), "list");
	}
	
	
}
