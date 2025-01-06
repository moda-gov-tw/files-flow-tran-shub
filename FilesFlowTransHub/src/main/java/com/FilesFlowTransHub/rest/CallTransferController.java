package com.FilesFlowTransHub.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.FilesFlowTransHub.constant.CaseStatusConstEnum;
import com.FilesFlowTransHub.domain.ServiceMain;
import com.FilesFlowTransHub.dto.ConnectionInfo;
import com.FilesFlowTransHub.dto.FileInfo;
import com.FilesFlowTransHub.dto.ResponseInfo;
import com.FilesFlowTransHub.dto.ServiceRequest;
import com.FilesFlowTransHub.service.CaseService;
import com.FilesFlowTransHub.service.ExceptionService;
import com.FilesFlowTransHub.service.FileService;
import com.FilesFlowTransHub.service.ServiceService;
import com.FilesFlowTransHub.util.SFTPFileTransUtils;
import com.FilesFlowTransHub.util.SystemUtils;

import jakarta.validation.Valid;

import com.FilesFlowTransHub.service.FileTransferService;
@RestController
@RequestMapping("/api")
public class CallTransferController {

	@Autowired
	private CaseService caseService;

	@Autowired
	private ExceptionService exceptionService;

	@Autowired
	private FileService fileService;
	
	@Autowired
	private ServiceService serviceService;

	@Autowired
	private FileTransferService fileTransferService;
	
	@PostMapping("/callService")
	public ResponseInfo callService(@Valid @RequestBody ServiceRequest request, BindingResult result) {
 
		if (result.hasErrors()) {
			System.out.println(result.getFieldErrors());
			ResponseInfo response = new ResponseInfo();
			response.setStatusCode(CaseStatusConstEnum.ERROR.getKey());
			response.setStatusMessage("請檢查所輸入的 服務代碼(serviceId) 是否為 10 碼或是合規字元");
			return response;
		}
		
		String serviceId = request.getServiceId();
		
		ResponseInfo response = new ResponseInfo();

		if (!serviceService.isServiceIdValid(serviceId)) {
			response.setStatusCode(CaseStatusConstEnum.ERROR.getKey());
			response.setStatusMessage("此 服務代碼(serviceId) 不存在或未開放");
		} else {
			String caseId = SystemUtils.generateCaseId(serviceId);

			caseService.insertCaseMainAndStatus(serviceId, caseId, CaseStatusConstEnum.INITIAL.getKey());

			// 回傳成功訊息
			response.setCaseId(caseId);
			response.setStatusCode(CaseStatusConstEnum.INITIAL.getKey());
			response.setStatusMessage("成案成功");

			startFileTransfer(caseId);
		}

		return response;
	}

	private void startFileTransfer(String caseId) {
		ExecutorService executorService = Executors.newFixedThreadPool(5);
		executorService.submit(() -> {
			try {
				String serviceId = caseId.substring(0, 10);

				List<FileInfo> files = new ArrayList<>();
				ServiceMain service = serviceService.findByServiceId(serviceId);
				ConnectionInfo sourceInfo = SystemUtils.createConnectionInfo(service, true);

				files = SFTPFileTransUtils.listFiles(sourceInfo);

				if (!files.isEmpty()) {
					
					fileService.saveFileMain(caseId, service.getSourcePath());
					fileService.saveFileDetails(caseId, files);

					// 下載檔案至暫存區
					fileTransferService.handleDownload(caseId, sourceInfo, files); 

					// 修改案件狀態為 已收件
					caseService.updateCaseStatus(caseId.substring(0, 10), caseId,
							CaseStatusConstEnum.RECEIVED.getKey());
					
					// 取得目的端連線資訊
					ConnectionInfo targetInfo = SystemUtils.createConnectionInfo(service, false);

					// 修改案件狀態為 處理中
					caseService.updateCaseStatus(caseId.substring(0, 10), caseId,
							CaseStatusConstEnum.PROCESSING.getKey());
					
					// 上傳檔案至目的地
					fileTransferService.handleUpload(caseId, targetInfo, files);
					
				} else {
					caseService.updateCaseStatus(caseId.substring(0, 10), caseId, CaseStatusConstEnum.ERROR.getKey());
					exceptionService.insertException(caseId.substring(0, 10), caseId, "來源端無檔案。");
					executorService.shutdown();
				}

			} catch (Exception e) {
				e.printStackTrace();
				caseService.updateCaseStatus(caseId.substring(0, 10), caseId, CaseStatusConstEnum.ERROR.getKey());
				exceptionService.insertException(caseId.substring(0, 10), caseId, e.getMessage());
				executorService.shutdown();
			}
		});

		// 關閉 executor
		executorService.shutdown();
	}

}
