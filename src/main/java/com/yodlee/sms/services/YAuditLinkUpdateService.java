package com.yodlee.sms.services;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yodlee.iae.common.services.ServiceBase;
import com.yodlee.sms.dataTypes.ApprovalLinkBody;
import com.yodlee.sms.persistence.MaintenanceAudit;
import com.yodlee.sms.repository.ApprovalRepository;

@Named
public class YAuditLinkUpdateService extends ServiceBase{
	private static Logger logger =LoggerFactory.getLogger(YAuditLinkUpdateService.class);
	
	@Inject
	ApprovalRepository approvalRepository;

	private ApprovalLinkBody approvalLinkBody;

	public List<MaintenanceAudit> updateAuditTable(ApprovalLinkBody approvalLinkBody){
		this.approvalLinkBody = approvalLinkBody;
		execute();
		return null;
	}

	@Override
	public void executeImpl() {
		logger.info("Inside YAuditLinkUpdateService");
		String id = approvalLinkBody.getId();
		String link = approvalLinkBody.getLink();
		logger.info("id->"+id+"|link->"+link);
		approvalRepository.updateRowByIdForLink(id, link);
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
