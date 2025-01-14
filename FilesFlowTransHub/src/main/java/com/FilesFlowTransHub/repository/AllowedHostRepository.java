package com.FilesFlowTransHub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.FilesFlowTransHub.domain.AllowedHost;

public interface AllowedHostRepository extends JpaRepository<AllowedHost, String>, JpaSpecificationExecutor<AllowedHost> {
	List<AllowedHost> findAll();
}
