package com.yodlee.sms.repository;

import java.util.List;

import javax.inject.Named;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.yodlee.sms.persistence.MaintenanceSchedule;

@Named
public interface MaintenanceScheduleRepository extends CrudRepository<MaintenanceSchedule, Integer> {
	
	@Query("select maintenanceSchedule from MaintenanceSchedule maintenanceSchedule where maintenanceSchedule.effectiveDate>=(sysdate-7)")
	public List<MaintenanceSchedule> findAllWindows();
	
	@Query("select maintenanceSchedule from MaintenanceSchedule maintenanceSchedule where maintenanceSchedule.effectiveDate>='?1' and maintenanceSchedule.expiryDate<='?2'")
	public List<MaintenanceSchedule> findWindowsByDate(String startDate, String endDate);

}
