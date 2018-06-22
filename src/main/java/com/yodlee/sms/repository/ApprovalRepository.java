package com.yodlee.sms.repository;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.yodlee.sms.dataTypes.StatusBody;
import com.yodlee.sms.persistence.MaintenanceAudit;
import com.yodlee.sms.persistence.MaintenanceSchedule;

@Named
public class ApprovalRepository {
	private static Logger logger =LoggerFactory.getLogger(ApprovalRepository.class);
	private static final String AUDITCOLLECTIONTYPE = "maintenanceAudit";
	private static final String COLLECTIONNAME = "smscollection";
	private static final String ISAPPROVED = "isApproved";
	private static final String ISEXPIRED = "isExpired";
	private static final String TOTAL = "total";
	private static final String TYPE = "type";
	
	@Inject
	MongoOperations mongoOperations;

	@Inject
	AuditRepository auditRepository;

	@Inject
	MongoTemplate mongoTemplate;

	@Inject
	MaintenanceScheduleRepository maintenanceScheduleRepository;

	public List<MaintenanceAudit> getAllAuditInfo(){
		logger.info("inside repository");
		return auditRepository.findAllByType(AUDITCOLLECTIONTYPE);
	}
	
	public List<StatusBody> getAuditStatus() {
		Aggregation agg = newAggregation(match(Criteria.where(TYPE).is(AUDITCOLLECTIONTYPE)),
				group(ISAPPROVED).count().as(TOTAL),
				project(TOTAL).and(ISAPPROVED).previousOperation());
		AggregationResults<StatusBody> groupResults = mongoTemplate.aggregate(agg, MaintenanceAudit.class, StatusBody.class);
		return groupResults.getMappedResults();
	}
	
	public void updateRowById(String id, int isApproved) {
		logger.info("id=>"+id);
		logger.info("isapproved=>"+isApproved);
		mongoOperations.updateFirst(new Query(Criteria.where(TYPE).is(AUDITCOLLECTIONTYPE).where("maintenanceAuditId").is(Integer.parseInt(id))),
				Update.update(ISAPPROVED, isApproved), 
				COLLECTIONNAME);
	}
	public void updateExpiryStatusById(String id, int isExpired) {
		logger.info("id=>"+id);
		logger.info("isapproved=>"+isExpired);
		mongoOperations.updateFirst(new Query(Criteria.where(TYPE).is(AUDITCOLLECTIONTYPE).where("maintenanceAuditId").is(Integer.parseInt(id))),
				Update.update(ISEXPIRED, isExpired), 
				COLLECTIONNAME);
	}
	
	public void updateRowByIdForLink(String id, String link) {
		mongoOperations.updateFirst(new Query(Criteria.where(TYPE).is(AUDITCOLLECTIONTYPE).where("maintenanceAuditId").is(Integer.parseInt(id))),
				Update.update("adtLink", link), 
				COLLECTIONNAME);
	}
	
	public void updateRowByIdForErrorMsg(String id, String msg) {
		mongoOperations.updateFirst(new Query(Criteria.where(TYPE).is(AUDITCOLLECTIONTYPE).where("maintenanceAuditId").is(Integer.parseInt(id))),
				Update.update("errorMsg", msg), 
				COLLECTIONNAME);
	}
	
	public List<MaintenanceSchedule> getAllWindows(){
		return maintenanceScheduleRepository.findAllWindows();
	}

	public List<MaintenanceSchedule> getWindowForDates(String startDate, String endDate) {
		return maintenanceScheduleRepository.findWindowsByDate(startDate, endDate);
	}
}
