package com.FilesFlowTransHub.service;

import java.io.File;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FilesFlowTransHub.constant.CaseStatusConstEnum;
import com.FilesFlowTransHub.dto.ConnectionInfo;
import com.FilesFlowTransHub.dto.FileInfo;
import com.FilesFlowTransHub.util.SFTPFileTransUtils;

@Service
public class FileTransferService {
	@Autowired
    private CaseService caseService;

    @Autowired
    private ExceptionService exceptionService;
    
    private final String currentDir = System.getProperty("user.dir");
    
    /**
     * 下載檔案暫存
     * @param caseId
     * @param sourceInfo
     * @param fileNameList
     */
    public void handleDownload(String caseId, ConnectionInfo sourceInfo, List<FileInfo> fileNameList) {
         
        String targetDir = currentDir + "/" + caseId;
        File caseDir = new File(targetDir);

        if (!caseDir.exists()) {
            caseDir.mkdirs();
        }

        try {
            if (SFTPFileTransUtils.isConnectionSuccess(sourceInfo)) {
                if (!fileNameList.isEmpty()) {
                    fileNameList.forEach(fileName -> {
                        System.out.println("開始下載檔案：" + fileName.getName());
                        SFTPFileTransUtils.downFile(sourceInfo, fileName.getName(), targetDir);
                    });
                } else {
                	System.out.println("該路徑下無檔案");
                    updateStatusWithError(caseId, "該路徑下無檔案");
                }
            } else {
                updateStatusWithError(caseId, "SFTP 連線失敗");
            }
        } catch (Exception e) {
            updateStatusWithError(caseId, e.getMessage());
        }
    }

    /**
     * 上傳檔案至指定目錄
     * @param caseId
     * @param targetInfo
     * @param fileNameList
     */
    public void handleUpload(String caseId, ConnectionInfo targetInfo, List<FileInfo> fileNameList) {
        
        String sourceUploadPath = currentDir + "/" + caseId;

        try {
            if (!SFTPFileTransUtils.isConnectionSuccess(targetInfo)) {
                throw new Exception("SFTP 連線失敗");
            }

            boolean hasError = false;
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(sourceUploadPath))) {
                for (FileInfo fileName : fileNameList) {
                    System.out.println("開始上傳檔案：" + fileName.getName());
                    try {
                        SFTPFileTransUtils.uploadFiles(targetInfo, fileName.getName(), sourceUploadPath);
                    } catch (Exception e) {
                        hasError = true;
                        updateStatusWithError(caseId, e.getMessage());
                    }
                }
            }
            if (!hasError) {
                caseService.updateCaseStatus(caseId.substring(0, 10), caseId, CaseStatusConstEnum.CLOSED.getKey());
            }
        } catch (Exception e) {
            updateStatusWithError(caseId, e.getMessage());
        }
    }

    private void updateStatusWithError(String caseId, String message) {
        caseService.updateCaseStatus(caseId.substring(0, 10), caseId, CaseStatusConstEnum.ERROR.getKey());
        exceptionService.insertException(caseId.substring(0, 10), caseId, message);
    }
}
