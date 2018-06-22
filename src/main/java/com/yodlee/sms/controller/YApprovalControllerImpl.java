package com.yodlee.sms.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yodlee.sms.dataTypes.ApprovalBody;
import com.yodlee.sms.dataTypes.ApprovalLinkBody;
import com.yodlee.sms.dataTypes.AuditStatus;
import com.yodlee.sms.dataTypes.MaintenanceScheduleData;
import com.yodlee.sms.dataTypes.YADADTResponse;
import com.yodlee.sms.persistence.MaintenanceAudit;
import com.yodlee.sms.persistence.MaintenanceSchedule;
import com.yodlee.sms.services.YAuditBatchUpdateService;
import com.yodlee.sms.services.YAuditFetchService;
import com.yodlee.sms.services.YAuditLinkUpdateService;
import com.yodlee.sms.services.YAuditStatusFetchService;
import com.yodlee.sms.services.YAuthenticationValidatorService;
import com.yodlee.sms.services.YMaintenanceBatchUpdateService;
import com.yodlee.sms.services.YMaintenanceFetchService;
import com.yodlee.sms.yad.response.AppResponse;

@Controller
@Path("/approval")
public class YApprovalControllerImpl implements YApprovalController{

	private static Logger logger =LoggerFactory.getLogger(YApprovalControllerImpl.class);
	private String browserIp;

	@Inject 
	private YAuditFetchService yAuditFetchService;

	@Inject
	private YMaintenanceFetchService yMaintenanceFetchService;

	@Inject
	private YMaintenanceBatchUpdateService yMaintenanceBatchUpdateService;

	@Inject
	private YAuditBatchUpdateService yAuditBatchUpdateService;

	@Inject
	private YAuditStatusFetchService auditStatusFetchService;

	@Inject
	private YAuditLinkUpdateService yAuditLinkUpdateService;

	@Inject
	private YAuthenticationValidatorService yAuthenticationValidatorService;

	public Response collectAuditData(String token) {
		logger.info("token==collectAuditData->"+token);
		logger.info("ip->"+browserIp);
		boolean isAuthenticUser = false;
		if(yAuthenticationValidatorService.validateUser(token, browserIp)) {
			isAuthenticUser = true;
		}
		logger.info("isauthentic user->"+isAuthenticUser);
		if(isAuthenticUser) {
			List<MaintenanceAudit> searchResult = yAuditFetchService.getAuditTableData();
			return Response.status(Status.OK).entity(searchResult).build();
		}
		return Response.status(Status.GATEWAY_TIMEOUT).build();
	}

	public Response collectMaintenanceData(String token) {
		logger.info("token==collectMaintenanceData->"+token);
		logger.info("ip->"+browserIp);
		boolean isAuthenticUser = false;
		if(yAuthenticationValidatorService.validateUser(token, browserIp)) {
			isAuthenticUser = true;
		}
		logger.info("isauthentic user->"+isAuthenticUser);
		if(isAuthenticUser) {
			List<MaintenanceSchedule> searchResult = yMaintenanceFetchService.getMaintenanceTableData();
			return Response.status(Status.OK).entity(searchResult).build();
		}
		return Response.status(Status.GATEWAY_TIMEOUT).build();
	}

	@Override
	public Response createMaintenanceInfo(String token,
			MaintenanceScheduleData maintenanceScheduleData) {
		logger.info("token==createBulkMaintenanceInfo->"+token);
		logger.info("ip->"+browserIp);
		logger.info("MaintenanceScheduleData->"+maintenanceScheduleData);
		boolean isAuthenticUser = false;
		if(yAuthenticationValidatorService.validateUser(token, browserIp)) {
			isAuthenticUser = true;
		}
		logger.info("isauthentic user->"+isAuthenticUser);
		if(isAuthenticUser) {
			YADADTResponse response = yMaintenanceBatchUpdateService.updateMaintenanceTable(maintenanceScheduleData);
			return Response.status(Status.OK).entity(response).build();
		}
		return Response.status(Status.GATEWAY_TIMEOUT).build();
	}

	@Override
	public Response updateAuditForIsApproved(String token, ApprovalBody approvalBody) {
		logger.info("token==updateBulkAuditData->"+token);
		logger.info("ip->"+browserIp);
		logger.info("approvalBody->"+approvalBody);
		boolean isAuthenticUser = false;
		if(yAuthenticationValidatorService.validateUser(token, browserIp)) {
			isAuthenticUser = true;
		}
		logger.info("isauthentic user->"+isAuthenticUser);
		if(isAuthenticUser) {
			List<MaintenanceAudit> maintenanceAuditList = yAuditBatchUpdateService.updateAuditTable(approvalBody);
			return Response.status(Status.OK).entity(maintenanceAuditList).build();
		}
		return Response.status(Status.GATEWAY_TIMEOUT).build();
	}

	public Response collectAuditStatus(String token) {
		logger.info("token==collectAuditStatus->"+token);
		logger.info("ip->"+browserIp);
		boolean isAuthenticUser = false;
		if(yAuthenticationValidatorService.validateUser(token, browserIp)) {
			isAuthenticUser = true;
		}
		logger.info("isauthentic user->"+isAuthenticUser);
		if(isAuthenticUser) {

			AuditStatus auditStatus = auditStatusFetchService.getAuditTableStatus();
			return Response.status(Status.OK).entity(auditStatus).build();
		}
		return Response.status(Status.GATEWAY_TIMEOUT).build();
	}

	@Override
	public Response updateAuditLink(String token, ApprovalLinkBody approvalLinkBody) {
		logger.info("token==updateBulkAuditLinks->"+token);
		logger.info("ip->"+browserIp);
		logger.info("approvalLinkBody->"+approvalLinkBody);
		boolean isAuthenticUser = false;
		if(yAuthenticationValidatorService.validateUser(token, browserIp)) {
			isAuthenticUser = true;
		}
		logger.info("isauthentic user->"+isAuthenticUser);
		if(isAuthenticUser) {

			List<MaintenanceAudit> maintenanceAuditList = yAuditLinkUpdateService.updateAuditTable(approvalLinkBody);
			return Response.status(Status.OK).entity(maintenanceAuditList).build();
		}
		return Response.status(Status.GATEWAY_TIMEOUT).build();
	}

	@Override
	public Response collectMaintenanceDataById(String token, int sumInfoId) {
		logger.info("token==collectMaintenanceDataById->"+token);
		logger.info("ip->"+browserIp);
		boolean isAuthenticUser = false;
		if(yAuthenticationValidatorService.validateUser(token, browserIp)) {
			isAuthenticUser = true;
		}
		logger.info("isauthentic user->"+isAuthenticUser);
		if(isAuthenticUser) {

			AppResponse searchResult = yMaintenanceFetchService.getMaintetnanceWindowForSumInfo(sumInfoId);
			return Response.status(Status.OK).entity(searchResult).build();
		}
		return Response.status(Status.GATEWAY_TIMEOUT).build();
	}

	@Override
	public Response collectMaintenanceDataByDate(String token, String startDate, String endDate) {
		logger.info("token==collectMaintenanceDataByDate->"+token);
		logger.info("ip->"+browserIp);
		boolean isAuthenticUser = false;
		if(yAuthenticationValidatorService.validateUser(token, browserIp)) {
			isAuthenticUser = true;
		}
		logger.info("isauthentic user->"+isAuthenticUser);
		if(isAuthenticUser) {
			List<MaintenanceSchedule> searchResult = yMaintenanceFetchService.getMaintenanceWindowForDates(startDate, endDate);
			return Response.status(Status.OK).entity(searchResult).build();
		}
		return Response.status(Status.GATEWAY_TIMEOUT).build();
	}

	@RequestMapping("/auditPage")
	public String validateTokenForAudit(HttpServletRequest request, @QueryParam("token") String token) {
		logger.info("auth token:"+token);
		logger.info("ip->"+request.getRemoteAddr());
		browserIp = request.getRemoteAddr();
		boolean isAuthenticUser = false;

		if(yAuthenticationValidatorService.validateUser(token, browserIp)) {
			isAuthenticUser = true;
		}
		logger.info("isauthentic user->"+isAuthenticUser);
		if(isAuthenticUser) {
			return "audit.html";
		}
		return "customError.html";
	}

	@RequestMapping("/maintenancePage")
	public String validateTokenForMaintenance(HttpServletRequest request, @QueryParam("token") String token) {
		logger.info("auth token:"+token);
		logger.info("ip->"+request.getRemoteAddr());
		browserIp = request.getRemoteAddr();
		boolean isAuthenticUser = false;

		if(yAuthenticationValidatorService.validateUser(token, browserIp)) {
			isAuthenticUser = true;
		}
		logger.info("isauthentic user->"+isAuthenticUser);
		if(isAuthenticUser) {
			return "maintenance.html";
		}
		return "customError.html";
	}
}
