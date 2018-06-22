package com.yodlee.sms.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yodlee.sms.persistence.MaintenanceAudit;
import com.yodlee.sms.repository.ApprovalRepository;

@Named
public class YAuditUpdatedExpiredService {
	private static Logger logger =LoggerFactory.getLogger(YAuditUpdatedExpiredService.class);
	
	@Inject
	ApprovalRepository approvalRepository;
	
	public void updateExpiredWindows() {
		Date todayDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		List<MaintenanceAudit> auditList = approvalRepository.getAllAuditInfo();
		Date startDate;
		int isApproved;
		for(MaintenanceAudit maintenanceAudit: auditList) {
			try {
				startDate = sdf.parse(maintenanceAudit.getMaintenanceSchedule().getStartDateTime());
				isApproved = Integer.parseInt(maintenanceAudit.getIsApproved());
				logger.info("id:"+maintenanceAudit.getMaintenanceAuditId()+"|startaDate:"+startDate+"|isapproved:"+isApproved);
				if(startDate.before(todayDate)) {
					approvalRepository.updateExpiryStatusById(maintenanceAudit.getMaintenanceAuditId(), 1);
				}
			} catch (ParseException e) {
				logger.info("ParseException:"+e);
			}
		}
	}
}
