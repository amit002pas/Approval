package com.yodlee.sms.repository;

import java.util.List;

import javax.inject.Named;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.yodlee.sms.persistence.MaintenanceAudit;

@Named
public interface AuditRepository extends MongoRepository<MaintenanceAudit, String>{

	@Query(value="{$and:[{type:?0},{isExpired:0}]}}")
	List<MaintenanceAudit> findAllByType(String type);

}
