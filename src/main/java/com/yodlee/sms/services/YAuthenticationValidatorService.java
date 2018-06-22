package com.yodlee.sms.services;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yodlee.sms.dataTypes.AuthValidationResponse;
import com.yodlee.sms.util.RestTemplateUtil;

@Named
public class YAuthenticationValidatorService{

	private static Logger logger =LoggerFactory.getLogger(YAuthenticationValidatorService.class);
	
	public boolean validateUser(String token, String browserIp) {
		
		token = token.replace("Bearer", "").trim();
		logger.info("tok-->"+token);
		AuthValidationResponse addUpdateResponse = RestTemplateUtil.validateUser(token, browserIp);
		if(addUpdateResponse.getAuthentication().equals("success")) {
			return true;
		}
		return false;
	}

}
