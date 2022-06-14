package com.utilities;


import org.json.JSONObject;
import com.listeners.ExtentListeners;

import io.restassured.response.Response;

public class TestUtil {

	public static boolean jsonHasKey(String json, String key) {

		JSONObject jsonObject = new JSONObject(json);
		ExtentListeners.testReport.get().info("Validating the presence of Key : " + key);

		return jsonObject.has(key);
	}

	public static String getJsonKeyValue(Response json, String key) {
		
		ExtentListeners.testReport.get().info("Validating Value of Key : " + key);

		return json.jsonPath().get(key).toString();

	}

	public static boolean jsonHasValue(String jsonString, String checkedName) {
		
		ExtentListeners.testReport.get().info("Validating Value json : "+checkedName);
		
		return jsonString.contains(checkedName);
	}
	
}
