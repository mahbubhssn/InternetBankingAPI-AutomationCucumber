package com.mislbd.ababil.ibapitest;

import com.mislbd.ababil.ibapitest.auth.AuthData;
import com.mislbd.ababil.ibapitest.error.ErrorHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@SpringBootTest(classes = IbApiTestApplication.class,
		webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration
class IbApiTestMethods {

	@Value("${mislbd.resource.baseUrl}")
	private String resourceBaseUrl;

	RestTemplate restTemplate = new RestTemplate();
	JSONParser parser = new JSONParser();

	public Object parseJSON(String fileName) throws IOException, ParseException {
		ClassLoader classLoader = getClass().getClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream("./jsonFile/"+fileName+".json");
		Object object = parser.parse(new InputStreamReader(inputStream));

		return object;
	}

	public ResponseEntity<JSONObject> executePost(String fileName, String url) {
		restTemplate.setErrorHandler(new ErrorHandler());
		ResponseEntity<JSONObject> response=null;
		try {
			Object obj = parseJSON(fileName);
			if(obj instanceof JSONArray) {
				JSONArray jsonObject = (JSONArray) obj;
				HttpEntity<JSONArray> request = new HttpEntity<JSONArray>(jsonObject, getPostCallHeaders());
				response = restTemplate.exchange(resourceBaseUrl + "/" + url, HttpMethod.POST, request, JSONObject.class);
			}else{
				JSONObject jsonObject = (JSONObject) obj;
				HttpEntity<JSONObject> request = new HttpEntity<JSONObject>(jsonObject, getPostCallHeaders());
				System.out.println(resourceBaseUrl + "/" + url);
				response = restTemplate.exchange(resourceBaseUrl + "/" + url, HttpMethod.POST, request, JSONObject.class);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return response;
	}

	public ResponseEntity<JSONObject> executePut(String fileName,String url) {
		restTemplate.setErrorHandler(new ErrorHandler());
		ResponseEntity<JSONObject> response=null;
		try {
			Object obj = parseJSON(fileName);
			if(obj instanceof JSONArray) {
				JSONArray jsonObject = (JSONArray) obj;
				HttpEntity<JSONArray> request = new HttpEntity<JSONArray>(jsonObject, getPostCallHeaders());
				response = restTemplate.exchange(resourceBaseUrl + "/" + url, HttpMethod.PUT, request, JSONObject.class);
			}else{
				JSONObject jsonObject = (JSONObject) obj;
				HttpEntity<JSONObject> request = new HttpEntity<JSONObject>(jsonObject, getPostCallHeaders());
				response = restTemplate.exchange(resourceBaseUrl + "/" + url, HttpMethod.PUT, request, JSONObject.class);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return response;
	}

	public ResponseEntity<?> executeGet(String url,Class<?> type){
		restTemplate.setErrorHandler(new ErrorHandler());
		ResponseEntity<?> response =  null;
		try{
			JSONObject jsonObject = new JSONObject();
			HttpEntity<JSONObject> request = new HttpEntity<JSONObject>(jsonObject, getPostCallHeaders());
			response = restTemplate.exchange(resourceBaseUrl + "/" + url, HttpMethod.GET, request, type);
		}catch (Exception e){
			e.printStackTrace();
		}
		return response;
	}

	private HttpHeaders getPostCallHeaders(){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization"," Bearer "+ AuthData.accessToken);
		headers.add("apisecret", "TcndBRflucJsVVIP446f");
		headers.add("requestchannel", "WEB");
		return headers;
	}

	public void updateJSON(String contentValue, String fileName) throws IOException, ParseException{
		Object obj = parseJSON(fileName);
		JSONObject jsonObject = (JSONObject) obj;
		jsonObject.put("id", ""+contentValue+"");

		try(FileWriter file = new FileWriter("../target/test-classes/jsonFile/"+fileName+".json")){
			file.write(jsonObject.toString());
		}
	}

	public String getAccountNumber(String fileName) throws IOException, ParseException{
		Object obj = parseJSON(fileName);
		JSONObject jsonObject = (JSONObject) obj;

		String accountNumber = jsonObject.get("fromAccount").toString();

		return accountNumber;
	}
}
