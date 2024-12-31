package com.FilesFlowTransHub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
@Controller
public class CallServiceController {
	
    @GetMapping("/callService")
    public String showPage(HttpServletRequest request, Model model) {
        String nonce = (String) request.getAttribute("nonce");
        model.addAttribute("nonce", nonce);
        return "callService";  
    }
}
