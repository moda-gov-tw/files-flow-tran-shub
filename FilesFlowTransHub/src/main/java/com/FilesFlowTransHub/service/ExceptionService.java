package com.FilesFlowTransHub.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FilesFlowTransHub.domain.ExceptionMain;
import com.FilesFlowTransHub.repository.ExceptionMainRepository;

@Service
public class ExceptionService {
	@Autowired
	private ExceptionMainRepository exceptionMainRepository;

	/**
	 * 依案號找出異常原因
	 * @param caseId
	 * @return
	 */
	public List<ExceptionMain> findByCaseId(String caseId) {
		return exceptionMainRepository.findByCaseId(caseId);
	}
	
	/**
	 * 儲存異常原因
	 * @param serviceId
	 * @param caseId
	 * @param message
	 */
	public void insertException(String serviceId, String caseId, String message) {
		// insert data to exception main
		ExceptionMain exception = new ExceptionMain();
		exception.setCaseId(caseId);
		exception.setExceptionDatetime(LocalDateTime.now());
		exception.setExceptionMessage(message);
		exception.setServiceId(caseId.substring(0, 10));
		exceptionMainRepository.save(exception);
	}
}
