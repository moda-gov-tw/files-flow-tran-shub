package com.FilesFlowTransHub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.FilesFlowTransHub.constant.CaseStatusConstEnum;
import com.FilesFlowTransHub.domain.CaseDetails;
import com.FilesFlowTransHub.domain.CaseMain;
import com.FilesFlowTransHub.domain.ExceptionMain;
import com.FilesFlowTransHub.domain.FileDetails;
import com.FilesFlowTransHub.domain.ServiceMain;
import com.FilesFlowTransHub.service.CaseService;
import com.FilesFlowTransHub.service.ExceptionService;
import com.FilesFlowTransHub.service.FileService;
import com.FilesFlowTransHub.service.ServiceService;

@Controller
public class CaseMainController {

	@Autowired
	private CaseService caseService;
	
	@Autowired
	private ServiceService serviceService;

	@Autowired
	private FileService fileService;

	@Autowired
	private ExceptionService exceptionService;

	@GetMapping("/caseMainList")
	public String getServiceList(Model model) {
		List<CaseMain> caseMainList = caseService.findAll();
	    for (CaseMain caseMain : caseMainList) {
	    	 String caseStatus = CaseStatusConstEnum.getValueByKey(caseMain.getCaseStatus());
	    	// 替換成中文描述
            caseMain.setCaseStatus(caseStatus);  
        }
		
		model.addAttribute("caseMainList", caseMainList);
		return "caseMainList";
	}

	@GetMapping("/caseDetails")
	public String getCaseMainDetails(@RequestParam("caseId") String caseId, Model model) {
		// 提取前十碼作為serviceId
		String serviceId = caseId.substring(0, 10);
		 
		ServiceMain service = serviceService.findByServiceId(serviceId);
		model.addAttribute("service", service);

		List<CaseDetails> caseDetailsList = caseService.findByCaseId(caseId);
	    for (CaseDetails caseDetail : caseDetailsList) {
	    	 String caseStatus = CaseStatusConstEnum.getValueByKey(caseDetail.getCaseStatus());
	    	 caseDetail.setCaseStatus(caseStatus);  // 替換成中文描述
       }
		
		model.addAttribute("caseDetailsList", caseDetailsList);

		List<FileDetails> fileDetailsList = fileService.findByFolderId(caseId);
		model.addAttribute("fileDetailsList", fileDetailsList);

		List<ExceptionMain> exceptionList = exceptionService.findByCaseId(caseId);
		model.addAttribute("exceptionList", exceptionList);

		return "caseDetails";
	}
}
