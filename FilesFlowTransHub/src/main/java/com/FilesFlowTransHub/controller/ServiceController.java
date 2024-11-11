package com.FilesFlowTransHub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.FilesFlowTransHub.domain.ServiceMain;
import com.FilesFlowTransHub.service.ServiceService;
import com.FilesFlowTransHub.util.Base64Utils;

@Controller
public class ServiceController {
	@Autowired
	private ServiceService serviceService;

	@GetMapping("/service")
	public String getServiceList(Model model) {

		List<ServiceMain> services = serviceService.findAll();

		model.addAttribute("services", services);

		return "serviceList";
	}

	@GetMapping({ "/service/add", "/service/edit/{serviceId}" })
	public String showAddOrEditService(@PathVariable(required = false) String serviceId, Model model) {
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
	public String saveService(@ModelAttribute ServiceMain service, @RequestParam("editable") boolean isEdit,
			Model model) {

		if (!isEdit) {
			ServiceMain existingService = serviceService.findByServiceId(service.getServiceId());
			if (existingService != null) {
				model.addAttribute("service", service);
				model.addAttribute("editable", false);
				model.addAttribute("errorMessage", "服務代碼已存在，請使用其他代碼");
				return "serviceDetails";
			}
		}
		
		service.setSourceCred(Base64Utils.stringToBase64(service.getSourceCred()));
		service.setTargetCred(Base64Utils.stringToBase64(service.getTargetCred()));

		serviceService.save(service);
		return "redirect:/service";
	}

}
