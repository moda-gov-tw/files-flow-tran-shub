package com.FilesFlowTransHub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.FilesFlowTransHub.domain.FileMain;

public interface FileMainRepository extends JpaRepository<FileMain, String>, JpaSpecificationExecutor<FileMain> {
    List<FileMain> findByFolderId(String folderId);
}
