package com.FilesFlowTransHub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.FilesFlowTransHub.domain.ExceptionMain;

public interface ExceptionMainRepository extends JpaRepository<ExceptionMain, Long>, JpaSpecificationExecutor<ExceptionMain> {
    List<ExceptionMain> findByCaseId(String caseId);
}
