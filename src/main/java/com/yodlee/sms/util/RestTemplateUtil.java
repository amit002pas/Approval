package com.yodlee.sms.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.yodlee.sms.constants.Constants;
import com.yodlee.sms.dataTypes.AuthValidationResponse;
import com.yodlee.sms.handlers.ToolsResponsehandler;
import com.yodlee.sms.yad.request.AddRequest;
import com.yodlee.sms.yad.request.FetchRequest;
import com.yodlee.sms.yad.request.Request;
import com.yodlee.sms.yad.request.UpdateRequest;
import com.yodlee.sms.yad.response.AddUpdateResponse;
import com.yodlee.sms.yad.response.AppResponse;


@Named
public class RestTemplateUtil {
	private static Logger logger =LoggerFactory.getLogger(RestTemplateUtil.class);
	
	private static String yadMentanenceURL;
	private static String yadAuthenticationURL;
	private static String adtMigrationURL;
	private static String yadAuthValidationURL;
	
	@Value("${yad-maintenance-url}")
	public void setYadMentanenceURL(String yadMentanenceURL) {
		RestTemplateUtil.yadMentanenceURL = yadMentanenceURL;
	}

	@Value("${yad-authentication-url}")
	public void setYadAuthenticationURL(String yadAuthenticationURL) {
		RestTemplateUtil.yadAuthenticationURL = yadAuthenticationURL;
	}

	@Value("${adt-migration-url}")
	public void setAdtMigrationURL(String adtMigrationURL) {
		RestTemplateUtil.adtMigrationURL = adtMigrationURL;
	}
	
	@Value("${yad-auth-validation-url}")
	public void setYadAuthValidationURL(String yadAuthValidationURL) {
		RestTemplateUtil.yadAuthValidationURL = yadAuthValidationURL;
	}

	public static AppResponse fetchMaintenanceWindow(String accessToken, int sumInfo) {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setErrorHandler(new ToolsResponsehandler());
		HttpHeaders headers =getHeader(accessToken);
		Request request = new Request();
		request.setFeature(Constants.FEATURE);
		request.setOperation(Constants.OPERATION_FETCH);
		FetchRequest fetchRequest = new FetchRequest();
		fetchRequest.setSumInfoId(sumInfo);
		request.setData(fetchRequest);
		Gson gson = new Gson();
		String requestJson = gson.toJson(request);
		HttpEntity<String> requestEntity = new HttpEntity<String>(requestJson, headers);
		AppResponse appResponse = null;
		try {
			String response = restTemplate.postForObject(yadMentanenceURL, requestEntity, String.class);
			logger.info("1st response>"+response);
			appResponse = gson.fromJson(response, AppResponse.class);
			logger.info("2nd> response"+appResponse);
		}catch (HttpClientErrorException httpEx) {
			logger.info("Error:"+httpEx);
		}
		return appResponse;

	}

	public static JsonNode generateToken(String userName, String password) throws JsonParseException, IOException {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setErrorHandler(new ToolsResponsehandler());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Map<String, String> map = new HashMap<>();
		map.put("username", userName);
		map.put("password", password);
		HttpEntity<Map<String, String>> request = new HttpEntity<Map<String, String>>(map, headers);
		logger.info(">>>request" + request.getBody());
		logger.info("uri:"+yadAuthenticationURL);
		String response = restTemplate.postForObject(yadAuthenticationURL, request, String.class);
		ObjectMapper mapper = new ObjectMapper();
		JsonFactory factory = mapper.getFactory();
		JsonParser parser = factory.createParser(response);
		JsonNode actualObj = mapper.readTree(parser);
		logger.info(">>>response" + actualObj);
		return actualObj;
	}
	
	public static AddUpdateResponse addMaintenanceWindow(String accessToken,AddRequest addRequest) throws HttpClientErrorException{
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new ToolsResponsehandler());
        HttpHeaders headers =getHeader(accessToken);
        Request request = new Request();
        request.setFeature(Constants.FEATURE);
        request.setOperation(Constants.OPERATION_ADD);
        request.setData(addRequest);
        Gson gson = new Gson();
        String requestJson = gson.toJson(request);
        logger.info("add req json:"+requestJson);
        HttpEntity<String> requestEntity = new HttpEntity<String>(requestJson, headers);
        String response = restTemplate.postForObject(yadMentanenceURL, requestEntity, String.class);
        logger.info("add req response json:"+response);
        AddUpdateResponse addUpdateResponse = gson.fromJson(response, AddUpdateResponse.class);
        return addUpdateResponse;

  }
  
  public static AddUpdateResponse updateMaintenanceWindow(String accessToken,UpdateRequest updateRequest)  throws HttpClientErrorException{
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new ToolsResponsehandler());
        HttpHeaders headers =getHeader(accessToken);
        Request request = new Request();
        request.setFeature(Constants.FEATURE);
        request.setOperation(Constants.OPERATION_UPDATE);
        request.setData(updateRequest);
        Gson gson = new Gson();
        String requestJson = gson.toJson(request);
        logger.info("^^^^^^^^^request :"+requestJson);
        HttpEntity<String> requestEntity = new HttpEntity<String>(requestJson, headers);
        String response = restTemplate.postForObject(yadMentanenceURL, requestEntity, String.class);
        AddUpdateResponse addUpdateResponse = gson.fromJson(response, AddUpdateResponse.class);
        logger.info("Response: "+addUpdateResponse);
        return addUpdateResponse;

  }
  
  public static String adtPush(String accessToken,int sumInfo) throws HttpClientErrorException {
		HttpHeaders headers =getHeader(accessToken);
		String request = "{"
							+ "\"" + Constants.TYPEKEY + "\"" +":" + "\"" +Constants.SUMINFOKEY + "\"" +","
							+ "\"" +Constants.DATAKEY + "\"" +":" 
														+ "{"
															+ "\"" + Constants.SUMINFOID + "\"" + ":" + sumInfo + ","
															+  "\"" +Constants.ENVIRONMENTKEY + "\"" +":[" + "\"" +Constants.ENVIRONMENTPROD +"\"]"
															+ "}"
							+ "}";
		logger.info("request->"+request);
		HttpEntity<String> requestEntity = new HttpEntity<String>(request, headers);
		RestTemplate restTemplate=new RestTemplate();
		restTemplate.setErrorHandler(new ToolsResponsehandler());
		ResponseEntity<?> response = restTemplate.postForEntity(adtMigrationURL, requestEntity, String.class);
		logger.info("response getting  :"+response.getBody());
		return (String)response.getBody();
	}
	 

  private static HttpHeaders getHeader(String accessToken) {
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		header.set(Constants.AUTHENTICATION_KEY, Constants.BEARER + accessToken);
		return header;
	}

  public static AuthValidationResponse validateUser(String token, String browserIp) {
	  RestTemplate restTemplate = new RestTemplate();
      restTemplate.setErrorHandler(new ToolsResponsehandler());
	  HttpHeaders headers =getHeader(token);
	  if(browserIp!=null) {
		  headers.set(Constants.BROWSERIPHEADER, browserIp);
	  }
	  HttpEntity<String> requestEntity = new HttpEntity<String>(null, headers);
	  logger.info("REQ->"+requestEntity);
	  String response = restTemplate.postForObject(yadAuthValidationURL, requestEntity, String.class);
	  logger.info("string RES->"+response);
	  Gson gson = new Gson();
	  AuthValidationResponse addUpdateResponse = gson.fromJson(response, AuthValidationResponse.class);
	  logger.info("RES->"+addUpdateResponse);
	  return addUpdateResponse;
  }
}
