package com.FilesFlowTransHub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SFTPConnectionController {

    @GetMapping("/connection")
    public String showConnectionPage(Model model) {
        return "connection";  
    }
}
