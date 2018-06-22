package com.yodlee.sms.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.web.bind.annotation.RequestBody;

import com.yodlee.sms.dataTypes.ApprovalBody;
import com.yodlee.sms.dataTypes.ApprovalLinkBody;
import com.yodlee.sms.dataTypes.MaintenanceScheduleData;

public interface YApprovalController {
	@GET
	@Path("/audit/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response collectAuditData(@HeaderParam("Authorization") String token);
	@GET
	@Path("/maintenance/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response collectMaintenanceData(@HeaderParam("Authorization") String token);
	@POST
	@Path("/maintenance/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createMaintenanceInfo(@HeaderParam("Authorization") String token, @RequestBody MaintenanceScheduleData maintenanceScheduleData);
	@PUT
	@Path("/audit/updateIsApproved")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateAuditForIsApproved(@HeaderParam("Authorization") String token, @RequestBody ApprovalBody approvalBody);
	@GET
	@Path("/audit/status")
	@Produces(MediaType.APPLICATION_JSON)
	public Response collectAuditStatus(@HeaderParam("Authorization") String token);
	@PUT
	@Path("/audit/updateLink")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateAuditLink(@HeaderParam("Authorization") String token, @RequestBody ApprovalLinkBody approvalLinkBody);
	@GET
	@Path("/maintenance/{sumInfoId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response collectMaintenanceDataById(@HeaderParam("Authorization") String token, @PathParam("sumInfoId") int sumInfoId);
	@GET
	@Path("/maintenance")
	@Produces(MediaType.APPLICATION_JSON)
	public Response collectMaintenanceDataByDate(@HeaderParam("Authorization") String token, @QueryParam("startDate") String startDate, @QueryParam("endDate") String endDate);
	
}
