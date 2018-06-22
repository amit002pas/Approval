package com.yodlee.sms.services;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.core.JsonParseException;
import com.yodlee.iae.common.services.ServiceBase;
import com.yodlee.sms.dataTypes.MaintenanceScheduleData;
import com.yodlee.sms.dataTypes.MaintenanceWindow;
import com.yodlee.sms.dataTypes.YADADTResponse;
import com.yodlee.sms.repository.ApprovalRepository;
import com.yodlee.sms.util.RestTemplateUtil;
import com.yodlee.sms.util.ToolAuthenticator;
import com.yodlee.sms.util.ValueMapper;
import com.yodlee.sms.yad.request.AddRequest;
import com.yodlee.sms.yad.request.UpdateRequest;
import com.yodlee.sms.yad.response.AddUpdateResponse;
import com.yodlee.sms.yad.response.AppResponse;

@Named
public class YMaintenanceBatchUpdateService extends ServiceBase {
	private static Logger logger =LoggerFactory.getLogger(YMaintenanceBatchUpdateService.class);

	private static String adtDiffURL;
	private MaintenanceScheduleData maintenanceScheduleData;
	YADADTResponse yadAdtResponse;

	@Inject
	ApprovalRepository approvalRepository;

	private static String username;
	private static String password;

	@Value("${adt-diff-url}")
	public void setAdtDiffURL(String adtDiffURL) {
		YMaintenanceBatchUpdateService.adtDiffURL = adtDiffURL;
	}

	@Value("${yad-username}")
	public void setUsername(String username) {
		YMaintenanceBatchUpdateService.username = username;
	}

	@Value("${yad-password}")
	public void setPassword(String password) {
		YMaintenanceBatchUpdateService.password = password;
	}

	public YADADTResponse updateMaintenanceTable(MaintenanceScheduleData maintenanceScheduleData){
		this.maintenanceScheduleData = maintenanceScheduleData;
		execute();
		return yadAdtResponse;
	}

	@Override
	public void executeImpl() {
		String securityToken= Authorization();
		yadUpdateAndADTpush(securityToken);
	}

	private void yadUpdateAndADTpush(String securityToken) {
		YADADTResponse response=null;
		Calendar calendar = Calendar.getInstance();

		if (securityToken != null) {
			AppResponse appResponse = null;
			logger.info("window from audit:"+maintenanceScheduleData);
			boolean isSumInfoPresentInDB = true;
			boolean isAddRequest = false;
			boolean isNewMaintenanceRowRequired = true;
			boolean isAddUpdateSuccessfull = true;
			int sumInfoId = maintenanceScheduleData.getSumInfoId();

			//for checking the existing maintenance windows whether to update or add
			try {
				appResponse = RestTemplateUtil.fetchMaintenanceWindow(securityToken, sumInfoId);
				if(appResponse.getAppResponse().getDescription().contains("Sum Info ID is not Valid")) {
					isSumInfoPresentInDB = false;
				}
				else if(appResponse.getAppResponse().getDescription().contains("No Windows Present for Sum Info ID")) {
					isAddRequest = true;
				}
			}catch (HttpClientErrorException httpEx) {
				logger.info("HttpClientErrorException:"+httpEx);
				logger.info("Either no maintenance found or sumInfo doesn't exist");
			}
			AddRequest addRequest = null;
			UpdateRequest updateRequest = null;
			ValueMapper valueMapper;

			if(!isAddRequest && isSumInfoPresentInDB) {
				List<MaintenanceWindow> maintenanceWindowList = appResponse.getAppResponse().getResponseData()
						.getMaintenanceWindows();
				for (MaintenanceWindow maintenanceWindow : maintenanceWindowList) {
					logger.info("MaintenanceWindow:"+maintenanceWindow);
					if (maintenanceWindow.getSumInfoId() == sumInfoId) {
						logger.info("today: " + new Date(calendar.getTimeInMillis()));
						logger.info("main exp date " + new Date(maintenanceWindow.getExpiryDate()));
						if (maintenanceWindow.getExpiryDate() < calendar.getTimeInMillis()) {
							logger.info("Inside If");
							isNewMaintenanceRowRequired = false;
							updateRequest = new UpdateRequest();
							valueMapper = new ValueMapper();
							valueMapper.mapValues(updateRequest, sumInfoId, maintenanceWindow, maintenanceScheduleData);
							logger.info("update Request=>"+updateRequest);
							break;
						}
					}
				}
			}

			AddUpdateResponse addUpdateResponse = new AddUpdateResponse();
			response = new YADADTResponse();
			response.setId(maintenanceScheduleData.getAuditId());
			if(isSumInfoPresentInDB) {
				if (isNewMaintenanceRowRequired) {//add maintenance window
					valueMapper = new ValueMapper();
					addRequest = new AddRequest();
					valueMapper.mapValues(addRequest, sumInfoId, maintenanceScheduleData);

					try {
						logger.info("Addrequest=>"+addRequest);
						addUpdateResponse = RestTemplateUtil.addMaintenanceWindow(securityToken, addRequest);
						if(addUpdateResponse.getAddUpdateAppResponse().getStatus().contains("Failure")) {
							isAddUpdateSuccessfull=false;
						}
					} catch (HttpClientErrorException http) {
						logger.info("HttpClientErrorException"+http);
						logger.info("Error in Creating maintenance");
						isAddUpdateSuccessfull = false;
					}
				} else { //update maintenance window
					try {
						logger.info("updaterequest=>"+updateRequest);
						addUpdateResponse = RestTemplateUtil.updateMaintenanceWindow(securityToken, updateRequest);
						logger.info("addUpdateResponse=>"+addUpdateResponse);
						if(addUpdateResponse.getAddUpdateAppResponse().getStatus().contains("Failure")) {
							isAddUpdateSuccessfull=false;
						}
					} catch (HttpClientErrorException http) {
						logger.info("HttpClientErrorException"+http);
						logger.info("Error in Updating maintenance");
						isAddUpdateSuccessfull = false;
					}
				}
			}
			else {
				isAddUpdateSuccessfull = false;
			}
			response.setAddUpdateResponse(addUpdateResponse);

			String adtResponse = null;
			if(isAddUpdateSuccessfull) {// going for ADT push
				try {
					adtResponse = RestTemplateUtil.adtPush(securityToken, sumInfoId);
				} catch (HttpClientErrorException http) {
					logger.info("HttpClientErrorException"+http);
					logger.info("Error in ADT push");
				}

				JSONObject jsonObject = new JSONObject(adtResponse);
				JSONArray array = jsonObject.getJSONObject("appResponse").getJSONObject("responseData").getJSONArray("data");
				adtResponse = null;
				for (int i = 0; i < array.length(); i++) {
					JSONObject objectInArray = array.getJSONObject(i);
					logger.info("diffMigrationRequestId->"+objectInArray.get("diffMigrationRequestId"));

					if(!objectInArray.get("diffMigrationRequestId").toString().equals("null")) {
						logger.info("diffMigrationRequestId="+objectInArray.getInt("diffMigrationRequestId"));
						adtResponse = adtDiffURL + objectInArray.getInt("diffMigrationRequestId");
					}
				}
			}
			response.setAdtResponse(adtResponse);
			if(!response.getAddUpdateResponse().getAddUpdateAppResponse().getStatus().equals("Success")){
				String errorDescription = response.getAddUpdateResponse().getAddUpdateAppResponse().getDescription();
				logger.info("Error in yad push:"+errorDescription);
				approvalRepository.updateRowByIdForErrorMsg(response.getId(), errorDescription);
			}
			else if(response.getAdtResponse()==null){
				String errorDescription = "Error in ADT push: Diff Migration Id is NULL";
				logger.info("Error in adt push:"+errorDescription);
				approvalRepository.updateRowByIdForErrorMsg(response.getId(), errorDescription);
			}
			logger.info("ADT responselist=>"+response);
			yadAdtResponse = response;
		}

	}

	private String Authorization() {
		String username = ToolAuthenticator.doCrypt(YMaintenanceBatchUpdateService.username);
		String password = ToolAuthenticator.doCrypt(YMaintenanceBatchUpdateService.password);
		logger.info("username=>"+username);
		logger.info("password=>"+password);
		String securityToken = "";
		try {
			securityToken = ToolAuthenticator.getSecurityToken(username, password);
		} catch (JsonParseException e) {
			logger.info("JsonParseException"+e);
		} catch (IOException e) {
			logger.info("IOException"+e);
		}
		logger.info("token ...>> " + securityToken);
		return securityToken;
	}

	@Override
	public void mapInput() {
	}

	@Override
	public void mapOutput() {
	}

	@Override
	public void validate() {
	}
}
