package com.yodlee.sms.services;


import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import com.yodlee.iae.common.services.ServiceBase;
import com.yodlee.sms.dataTypes.ApprovalBody;
import com.yodlee.sms.persistence.MaintenanceAudit;
import com.yodlee.sms.repository.ApprovalRepository;
@Named
public class YAuditBatchUpdateService extends ServiceBase{

	@Inject
	ApprovalRepository approvalRepository;

	private ApprovalBody approvalBody;

	public List<MaintenanceAudit> updateAuditTable(ApprovalBody approvalBody){
		this.approvalBody = approvalBody;
		execute();
		return null;
	}

	@Override
	public void executeImpl() {
		String id = approvalBody.getId();
		int isApproved = approvalBody.getIsApproved();
		approvalRepository.updateRowById(id, isApproved);
	}

	@Override
	public void mapInput() {

	}

	@Override
	public void mapOutput() {

	}

	@Override
	public void validate() {
		// TODO Auto-generated method stub

	}

}
