package com.yodlee.sms.services;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yodlee.iae.common.services.ServiceBase;
import com.yodlee.sms.persistence.MaintenanceAudit;
import com.yodlee.sms.repository.ApprovalRepository;
import com.yodlee.sms.util.TTRILSMapper;

@Named
public class YAuditFetchService extends ServiceBase{
	private static Logger logger =LoggerFactory.getLogger(YAuditFetchService.class);
	@Inject
	ApprovalRepository approvalRepository;
	
	@Inject
	YAuditUpdatedExpiredService yAuditUpdatedExpiredService;
	
	@Inject 
	TTRILSMapper tTRILSMapper;
	
	private List<MaintenanceAudit> auditList;
	
	public List<MaintenanceAudit> getAuditTableData(){
		execute();
		return auditList;
	}
	
	@Override
	public void executeImpl() {
		logger.info("inside service");
		yAuditUpdatedExpiredService.updateExpiredWindows();
		auditList = approvalRepository.getAllAuditInfo();
		tTRILSMapper.updateTTRILSMapList();
		auditList = tTRILSMapper.updateTTRILSSites(auditList);
	}

	@Override
	public void mapInput() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mapOutput() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void validate() {
		// TODO Auto-generated method stub
		
	}

}
