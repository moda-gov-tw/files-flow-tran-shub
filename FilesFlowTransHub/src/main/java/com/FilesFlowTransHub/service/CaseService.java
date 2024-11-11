package com.FilesFlowTransHub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FilesFlowTransHub.domain.CaseDetails;
import com.FilesFlowTransHub.domain.CaseMain;
import com.FilesFlowTransHub.repository.CaseDetailsRepository;
import com.FilesFlowTransHub.repository.CaseMainRepository;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class CaseService {
	@Autowired
    private CaseMainRepository caseMainRepository;

    @Autowired
    private CaseDetailsRepository caseDetailsRepository;

    /**
     * 找尋所有傳輸紀錄
     * @return
     */
    public List<CaseMain> findAll() {
    	return caseMainRepository.findAll();
    }
    
    /**
     * 依caseId找尋傳輸紀錄
     * @param caseId
     * @return
     */
    public List<CaseDetails> findByCaseId(String caseId){
    	return caseDetailsRepository.findByCaseId(caseId);
    }
    
    /**
     * 儲存案號狀態
     * @param serviceId
     * @param caseId
     * @param caseStatus
     */
	public void insertCaseMainAndStatus (String serviceId, String caseId, String caseStatus) {
		// insert data to case main
		CaseMain caseMain = new CaseMain();
		caseMain.setCaseId(caseId);
		caseMain.setCaseCreateDatetime(LocalDateTime.now());
		caseMain.setCaseUpdateDatetime(LocalDateTime.now());
		caseMain.setCaseStatus(caseStatus);  
		caseMain.setServiceId(serviceId);
		caseMainRepository.save(caseMain);

		// insert data to case details
		CaseDetails caseDetails = new CaseDetails();
		caseDetails.setCaseId(caseId);
		caseDetails.setCaseStatus(caseStatus);
		caseDetails.setUpdateDatetime(LocalDateTime.now());
		caseDetailsRepository.save(caseDetails);
	}

	/**
	 * 更新案件狀態
	 * @param serviceId
	 * @param caseId
	 * @param caseStatus
	 */
	public void updateCaseStatus (String serviceId, String caseId, String caseStatus) {
		CaseMain caseMain = caseMainRepository.findByCaseId(caseId);
		if (caseMain != null) {
			caseMain.setCaseStatus(caseStatus);
			caseMainRepository.save(caseMain);

			CaseDetails caseDetails = new CaseDetails();
			caseDetails.setCaseId(caseId);
			caseDetails.setCaseStatus(caseStatus);
			caseDetails.setUpdateDatetime(LocalDateTime.now());
			caseDetailsRepository.save(caseDetails);
		}
	}
	

}
