package com.yodlee.sms.util;

import com.yodlee.sms.constants.Constants;
import com.yodlee.sms.dataTypes.MaintenanceScheduleData;
import com.yodlee.sms.dataTypes.MaintenanceWindow;
import com.yodlee.sms.yad.request.AddRequest;
import com.yodlee.sms.yad.request.UpdateRequest;

public class ValueMapper {

	public void mapValues(Object obj, int sumInfo, MaintenanceScheduleData maintenanceWindow) {
		if (obj instanceof AddRequest) {
			((AddRequest) obj).setSumInfoId(maintenanceWindow.getSumInfoId());
			((AddRequest) obj).setStartDateTime(maintenanceWindow.getStartDateTime());
			((AddRequest) obj).setEndDateTime(maintenanceWindow.getEndDateTime());
			((AddRequest) obj).setExpiryDate(maintenanceWindow.getExpiryDate());
			((AddRequest) obj).setNotes(maintenanceWindow.getNotes());
			((AddRequest) obj).setRecurrance(Constants.RECURRANCE);
		} 
	}
	
	public void mapValues(Object obj, int sumInfo, MaintenanceWindow maintenanceWindow, MaintenanceScheduleData maintenanceScheduleData) {
		if (obj instanceof UpdateRequest) {
			((UpdateRequest) obj).setSumInfoId(maintenanceWindow.getSumInfoId());
			((UpdateRequest) obj).setMaintenanceScheduleId(maintenanceWindow.getMaintenanceScheduleId());
			((UpdateRequest) obj).setStartDateTime(maintenanceScheduleData.getStartDateTime());
			((UpdateRequest) obj).setEndDateTime(maintenanceScheduleData.getEndDateTime());
			((UpdateRequest) obj).setExpiryDate(maintenanceScheduleData.getExpiryDate());
			((UpdateRequest) obj).setNotes(maintenanceScheduleData.getNotes()+":UPDATED");
			((UpdateRequest) obj).setRecurrance(Constants.RECURRANCE);
		} 
	}
}
