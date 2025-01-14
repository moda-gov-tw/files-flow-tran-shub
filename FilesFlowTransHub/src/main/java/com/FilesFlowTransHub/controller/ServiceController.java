package com.FilesFlowTransHub.controller;
 
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
 
import com.FilesFlowTransHub.domain.ServiceMain;
import com.FilesFlowTransHub.dto.ServiceMainRequest;
import com.FilesFlowTransHub.service.AllowedHostService;
import com.FilesFlowTransHub.service.ServiceService;
import com.FilesFlowTransHub.util.Base64Utils;
import com.FilesFlowTransHub.util.SystemUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class ServiceController {
	@Autowired
	private ServiceService serviceService;
	@Autowired
	private AllowedHostService allowedHostService;
	@GetMapping("/service")
	public String getServiceList(Model model) {

		List<ServiceMain> services = serviceService.findAll();

		model.addAttribute("services", services);

		return "serviceList";
	}

	@GetMapping({ "/service/add", "/service/edit/{serviceId}" })
	public String showAddOrEditService(@PathVariable(required = false) String serviceId, Model model, HttpServletRequest request) {
		
		String nonce = (String) request.getAttribute("nonce");
	    model.addAttribute("nonce", nonce);
		
		if (serviceId != null) {
			ServiceMain service = serviceService.findByServiceId(serviceId);
			model.addAttribute("service", service);
			model.addAttribute("editable", true);
		} else {
			model.addAttribute("service", new ServiceMain());
			model.addAttribute("editable", false);
		}
		return "serviceDetails";
	}

	@PostMapping("/service/save")
	public String saveService(@Valid @ModelAttribute ServiceMainRequest serviceDTO, BindingResult result,
			Model model, HttpServletRequest request) {

		 if (result.hasErrors()) {
		        model.addAttribute("service", serviceDTO);
		        model.addAttribute("editable", serviceDTO.getEditable());
		        model.addAttribute("errorMessage", "輸入數據有誤，請檢查");
		        return "serviceDetails";
		    }
		
		if (!serviceDTO.getEditable()) {
			ServiceMain existingService = serviceService.findByServiceId(serviceDTO.getServiceId());
			if (existingService != null) {
				model.addAttribute("service", serviceDTO);
				model.addAttribute("editable", serviceDTO.getEditable());
				model.addAttribute("errorMessage", "服務代碼已存在，請使用其他代碼");
				return "serviceDetails";
			}
		}
		
        if (!SystemUtils.isAllowedPort( Integer.parseInt(serviceDTO.getSourcePort())) || !SystemUtils.isAllowedPort( Integer.parseInt(serviceDTO.getTargetPort()))) {
		    model.addAttribute("service", serviceDTO);
		    model.addAttribute("editable", serviceDTO.getEditable());
		    model.addAttribute("errorMessage", "來源或目地端埠號欄位不正確");
            return "serviceDetails";
        }
		
		if (!SystemUtils.isValidIPv4(serviceDTO.getSourceLocation()) || !SystemUtils.isValidIPv4(serviceDTO.getTargetLocation())) {
		    model.addAttribute("service", serviceDTO);
		    model.addAttribute("editable", serviceDTO.getEditable());
		    model.addAttribute("errorMessage", "來源或目地端IP欄位資料格式不正確");
		    return "serviceDetails";
		}
        
        if (!allowedHostService.isAllowedHost(serviceDTO.getSourceLocation())) {
		    model.addAttribute("service", serviceDTO);
		    model.addAttribute("editable", serviceDTO.getEditable());
		    model.addAttribute("errorMessage", "來源位置IP欄位不是合法的IP或主機不在允許的清單中");
		    return "serviceDetails";
        }
		
        if (!allowedHostService.isAllowedHost(serviceDTO.getTargetLocation())) {
		    model.addAttribute("service", serviceDTO);
		    model.addAttribute("editable", serviceDTO.getEditable());
		    model.addAttribute("errorMessage", "目標位置IP欄位不是合法的IP或主機不在允許的清單中");
		    return "serviceDetails";
        }
		
		if (!SystemUtils.isValidCred(serviceDTO.getSourceCred()) || !SystemUtils.isValidCred(serviceDTO.getTargetCred())) {
		    model.addAttribute("service", serviceDTO);
		    model.addAttribute("editable", serviceDTO.getEditable());
		    model.addAttribute("errorMessage", "來源或目地端密碼欄位資料格式不正確");
		    return "serviceDetails";
		}
		
		
		ServiceMain service = new ServiceMain();
		service.setServiceId(serviceDTO.getServiceId());
		service.setServiceDescription(serviceDTO.getServiceDescription());
		service.setServiceName(serviceDTO.getServiceName());
		service.setServiceStatus(serviceDTO.getServiceStatus());
		service.setSourceLocation(serviceDTO.getSourceLocation());
		service.setSourcePort(serviceDTO.getSourcePort());
		service.setSourcePath(serviceDTO.getSourcePath());
		service.setSourceId(serviceDTO.getSourceId());
		service.setSourceCred(Base64Utils.stringToBase64(serviceDTO.getSourceCred()).trim());
		service.setTargetLocation(serviceDTO.getTargetLocation());
		service.setTargetPort(serviceDTO.getTargetPort());
		service.setTargetPath(serviceDTO.getTargetPath());
		service.setTargetId(serviceDTO.getTargetId());
		service.setTargetCred(Base64Utils.stringToBase64(serviceDTO.getTargetCred()).trim());
		service.setEditable(serviceDTO.getEditable());
		
		serviceService.save(service);
		return "redirect:/service";
	}


	
}
