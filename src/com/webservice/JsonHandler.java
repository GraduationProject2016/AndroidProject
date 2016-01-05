package com.webservice;

import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class JsonHandler {

	public static JsonObject getJsonObjec(String str) {
		JsonReader reader = Json.createReader(new StringReader(str));
		JsonObject jsonObject = reader.readObject();
		reader.close();
		return jsonObject;
	}
}