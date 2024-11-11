package com.FilesFlowTransHub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.FilesFlowTransHub.domain.ServiceMain;

public interface ServiceMainRepository extends JpaRepository<ServiceMain, String>, JpaSpecificationExecutor<ServiceMain> {
	ServiceMain findByServiceId(String serviceId);
	
	List<ServiceMain> findAll();
}
