package com.yodlee.sms.services;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import com.yodlee.iae.common.services.ServiceBase;
import com.yodlee.sms.dataTypes.AuditStatus;
import com.yodlee.sms.dataTypes.StatusBody;
import com.yodlee.sms.repository.ApprovalRepository;

@Named
public class YAuditStatusFetchService extends ServiceBase{
	
	@Inject
	ApprovalRepository approvalRepository;
	
	private List<StatusBody> statusBodyList;
	private AuditStatus auditStatus;
	
	public AuditStatus getAuditTableStatus(){
		execute();
		return auditStatus;
	}

	@Override
	public void executeImpl() {
		
		AuditStatus as = new AuditStatus();
		statusBodyList = approvalRepository.getAuditStatus();
		
		for(StatusBody statusBody:statusBodyList){
			if(statusBody.getIsApproved().equals("-1")){
				as.setRejected(statusBody.getTotal());
			}
			else if(statusBody.getIsApproved().equals("1")){
				as.setApproved(statusBody.getTotal());
			}
			else if(statusBody.getIsApproved().equals("0")){
				as.setPending(statusBody.getTotal());
			}
		}
		
		as.setTotal(as.getApproved()+as.getPending()+as.getRejected());
		
		auditStatus = as;
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
