package com.FilesFlowTransHub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.FilesFlowTransHub.domain.CaseDetails;

public interface CaseDetailsRepository extends JpaRepository<CaseDetails, Long>, JpaSpecificationExecutor<CaseDetails> {
    List<CaseDetails> findByCaseId(String caseId);
}
