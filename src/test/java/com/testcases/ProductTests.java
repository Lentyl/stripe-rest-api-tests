package com.testcases;

import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.framework.apis.ProductAPI;
import com.framework.setUp.BaseTest;
import com.utilities.DataUtil;
import com.utilities.RunTimeTestData;
import com.utilities.TestUtil;

import io.restassured.response.Response;

@Listeners(com.listeners.ExtentListeners.class)
public class ProductTests extends BaseTest {
	
	@Test(priority=1 ,dataProviderClass = DataUtil.class, dataProvider = "data")
	public void createProduct(Hashtable <String, String> data) {	
		Response res = ProductAPI.postRqCreateProduct(data);

		Assert.assertEquals(res.statusCode(), 200);
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "name"), data.get("name"));
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "description"), data.get("description"));
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "active"), data.get("active"));
	}
	
	@Test(priority=2 ,dataProviderClass = DataUtil.class, dataProvider = "data")
	public void createProductWithoutValidName(Hashtable <String, String> data) {	
		Response res = ProductAPI.postRqCreateProductWithoutValidName(data);

		Assert.assertEquals(res.statusCode(), 400);
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "error.code"), "parameter_missing" );
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "error.message"), "Missing required param: name.");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "error.type"), "invalid_request_error");

	}
	
	@Test(priority=3 ,dataProviderClass = DataUtil.class, dataProvider = "data")
	public void retrieveProduct(Hashtable <String, String> data) {	
		Response res = ProductAPI.getRqRetrieveProduct(data);
		
		Assert.assertEquals(res.statusCode(), 200);
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "name"), "Balloons" );
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "object"), "product" );
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "active"), "false");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "description"), "Very durable and colorful");
	}
	
	@Test(priority=4 ,dataProviderClass = DataUtil.class, dataProvider = "data")
	public void updateProduct(Hashtable <String, String> data) {	
		Response res = ProductAPI.postRqUpdateProduct(data);

		Assert.assertEquals(res.statusCode(), 200);
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "name"), "rubber duck" );
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "description"), "Very durable and yellow" );
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "active"), "true");
	}
	
	@Test(priority=5 ,dataProviderClass = DataUtil.class, dataProvider = "data")
	public void listAllActiveProducts(Hashtable <String, String> data) {	
		Response res = ProductAPI.getRqListAllActiveProducts(data);
		
		Assert.assertEquals(res.statusCode(), 200);
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "data[0].name"), "rubber duck");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "data[0].active"), "true" );
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "data[0].description"), "Very durable and yellow" );
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "data[1].name"), "LEGO BRICKS");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "data[1].active"), "true" );
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "data[1].description"), "plastic bricks" );
	}
	
	@Test(priority=6 ,dataProviderClass = DataUtil.class, dataProvider = "data")
	public void searchUpdatedProduct(Hashtable <String, String> data) {	
		Response res = ProductAPI.getRqSearchUpdatedProduct(data);

		Assert.assertEquals(res.statusCode(), 200);
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "data[0].name"), "rubber duck");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "data[0].active"), "true" );
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "data[0].description"), "Very durable and yellow" );
	}
	
	@Test(priority=7 ,dataProviderClass = DataUtil.class, dataProvider = "data")
	public void deleteProduct(Hashtable <String, String> data) {	
		Response res = ProductAPI.deleteRqDeleteProduct(data);
		
		Assert.assertEquals(res.statusCode(), 200);
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "object"), "product");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "deleted"), "true" );
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "id"), RunTimeTestData.productIdList.get(Integer.parseInt(data.get("productIdIndex"))));
	}
	
	@Test(priority=8 ,dataProviderClass = DataUtil.class, dataProvider = "data")
	public void searchDeletedProduct(Hashtable <String, String> data) {	
		Response res = ProductAPI.getRqSearchDeletedProduct(data);
		
		Assert.assertEquals(res.statusCode(), 404);
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "error.code"), "resource_missing");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "error.message"), "No such product: '"+RunTimeTestData.productIdList.get(0)+"'");
		Assert.assertEquals(TestUtil.getJsonKeyValue(res, "error.type"), "invalid_request_error" );
	}
}
