package com.FilesFlowTransHub.controller;

 
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class SFTPConnectionController {

    @GetMapping("/connection")
    public String showConnectionPage(HttpServletRequest request, Model model) {
        String nonce = (String) request.getAttribute("nonce");
        model.addAttribute("nonce", nonce);
        return "connection";  
    }
}
