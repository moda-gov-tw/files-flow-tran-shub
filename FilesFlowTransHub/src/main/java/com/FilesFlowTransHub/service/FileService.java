package com.FilesFlowTransHub.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FilesFlowTransHub.domain.FileDetails;
import com.FilesFlowTransHub.domain.FileMain;
import com.FilesFlowTransHub.dto.FileInfo;
import com.FilesFlowTransHub.repository.FileDetailsRepository;
import com.FilesFlowTransHub.repository.FileMainRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Service
public class FileService {
    @Autowired
    private FileMainRepository fileMainRepository;

    @Autowired
    private FileDetailsRepository fileDetailsRepository;
    
    /**
     * 由案號尋找檔案清單
     * @param folderId
     * @return
     */
    public List<FileDetails> findByFolderId(String folderId){
    	return fileDetailsRepository.findByFolderId(folderId);
    }
    
    /**
     * 儲存檔案清單
     * @param caseId
     * @param folderPath
     */
    public void saveFileMain(String caseId, String folderPath) {
    	// insert data to filemain
        FileMain fileMain = new FileMain();
        fileMain.setFolderId(caseId);
        fileMain.setCaseCreateDatetime(LocalDateTime.now());
        fileMain.setFolderPath(folderPath);
        fileMainRepository.save(fileMain);
    }

    /**
     * 儲存檔案明細
     * @param caseId
     * @param files
     */
    public void saveFileDetails(String caseId, List<FileInfo> files) {
    	// insert data to file details
        for (FileInfo file : files) {
            FileDetails fileDetails = new FileDetails();
            fileDetails.setFileName(file.getName());
            fileDetails.setFileSize(BigDecimal.valueOf(file.getSize()));
            fileDetails.setFolderId(caseId);
            fileDetails.setLastUploadDatetime(LocalDateTime.now());
            fileDetailsRepository.save(fileDetails);
        }
    }
}
