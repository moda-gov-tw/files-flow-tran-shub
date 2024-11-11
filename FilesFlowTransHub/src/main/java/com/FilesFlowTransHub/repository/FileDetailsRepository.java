package com.FilesFlowTransHub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.FilesFlowTransHub.domain.FileDetails;

public interface FileDetailsRepository extends JpaRepository<FileDetails, Long>, JpaSpecificationExecutor<FileDetails> {
    List<FileDetails> findByFolderId(String folderId);
}
