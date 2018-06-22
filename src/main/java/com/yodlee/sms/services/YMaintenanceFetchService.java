	package com.yodlee.sms.services;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.yodlee.iae.common.services.ServiceBase;
import com.yodlee.sms.persistence.MaintenanceSchedule;
import com.yodlee.sms.repository.ApprovalRepository;
import com.yodlee.sms.util.RestTemplateUtil;
import com.yodlee.sms.util.ToolAuthenticator;
import com.yodlee.sms.yad.response.AppResponse;

@Named
public class YMaintenanceFetchService extends ServiceBase{
	private Logger logger =LoggerFactory.getLogger(this.getClass());

	private static boolean isRequestForAll = false;
	private static boolean isRequestForSumInfo = false;
	private static boolean isRequestForForDate = false;

	@Inject
	ApprovalRepository approvalRepository;

	private List<MaintenanceSchedule> maintenancelist;
	private AppResponse appResponse;
	private int sumInfoId;
	private String startDate;
	private String endDate;

	public List<MaintenanceSchedule> getMaintenanceTableData(){
		logger.info("all->"+isRequestForAll);
		isRequestForAll = true;
		execute();
		isRequestForAll = false;
		return maintenancelist;
	}
	public AppResponse getMaintetnanceWindowForSumInfo(int sumInfoId) {
		logger.info("suminfo->"+isRequestForSumInfo);
		isRequestForSumInfo = true;
		this.sumInfoId = sumInfoId;
		execute();
		isRequestForSumInfo = false;
		logger.info("appresponse------>"+appResponse);
		return appResponse;
	}
	public List<MaintenanceSchedule> getMaintenanceWindowForDates(String effectiveDate, String expiryDate){
		logger.info("date->"+isRequestForForDate);
		isRequestForForDate = true;
		startDate = effectiveDate;
		endDate = expiryDate;
		execute();
		isRequestForForDate = false;
		return maintenancelist;
	}
	
	@Override
	public void executeImpl() {
		if(isRequestForAll) {
			maintenancelist = approvalRepository.getAllWindows();
			logger.info("Output from DB "+maintenancelist.toString());
		}
		else if(isRequestForSumInfo) {
			String securityToken= Authorization();
			getMaintenanceWindowForSumInfo(securityToken);
		}
		else if(isRequestForForDate) {
			maintenancelist = approvalRepository.getWindowForDates(startDate, endDate);
		}
	}

	private void getMaintenanceWindowForSumInfo(String securityToken) {
		if(securityToken != null) {
			appResponse = RestTemplateUtil.fetchMaintenanceWindow(securityToken, sumInfoId);
		}
	}

	@Override
	public void mapInput() {
		if(isRequestForForDate) {
			SimpleDateFormat sdfToFormat = new SimpleDateFormat("dd-MM-yy");
			SimpleDateFormat sdfInFormat = new SimpleDateFormat("MM-dd-yyyy");
			
			try {
				startDate = sdfToFormat.format(sdfInFormat.parse(startDate));
				endDate = sdfToFormat.format(sdfInFormat.parse(endDate));
			} catch (ParseException e) {
				logger.info("Could not parse Date");
				logger.info("ParseException"+e);
			}
		}

	}

	@Override
	public void mapOutput() {
		if(isRequestForAll || isRequestForForDate) {
			SimpleDateFormat sdfDB = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdfResponse = new SimpleDateFormat("MM-dd-yyyy");
			for(MaintenanceSchedule maintenanceSchedule:maintenancelist) {

				try {
					String date = maintenanceSchedule.getEffectiveDate().split(" ")[0];
					Date dateInDateFormat = sdfDB.parse(date);
					date = sdfResponse.format(dateInDateFormat);
					maintenanceSchedule.setEffectiveDate(date);

				} catch (ParseException e) {
					logger.info("ParseException"+e);
				}

				try {
					logger.info("Expiry Date :"+maintenanceSchedule.getExpiryDate());
					if(maintenanceSchedule.getExpiryDate()==null || maintenanceSchedule.getExpiryDate()=="")
						continue;
					String date = maintenanceSchedule.getExpiryDate().split(" ")[0];
					Date dateInDateFormat = sdfDB.parse(date);
					date = sdfResponse.format(dateInDateFormat);
					maintenanceSchedule.setExpiryDate(date);

				} catch (ParseException e) {
					logger.info("ParseException"+e);
				}

			}
		}
	}

	@Override
	public void validate() {
		// TODO Auto-generated method stub

	}
	
	private String Authorization() {
		String securityToken = "";
		try {
			securityToken = ToolAuthenticator.getSecurityToken("srai", "samiT@@19$$$");
		} catch (JsonParseException e) {
			logger.info("JsonParseException"+e);
		} catch (IOException e) {
			logger.info("IOException"+e);
		}
		logger.info("token ...>> " + securityToken);
		return securityToken;
	}

}
