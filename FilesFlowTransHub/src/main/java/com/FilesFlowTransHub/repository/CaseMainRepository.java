package com.FilesFlowTransHub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.FilesFlowTransHub.domain.CaseMain;
 

public interface CaseMainRepository extends JpaRepository<CaseMain, String>, JpaSpecificationExecutor<CaseMain>{
	CaseMain findByCaseId(String caseId);
	List<CaseMain> findAll();
}
