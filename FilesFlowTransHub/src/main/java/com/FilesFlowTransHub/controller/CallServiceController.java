package com.FilesFlowTransHub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class CallServiceController {
	
    @GetMapping("/callService")
    public String showPage(Model model) {
        return "callService";  
    }
}
