package com.FilesFlowTransHub.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.FilesFlowTransHub.dto.ConnectionInfo;
import com.FilesFlowTransHub.dto.FileInfo;
import com.FilesFlowTransHub.service.AllowedHostService;
import com.FilesFlowTransHub.util.SFTPFileTransUtils;
import com.FilesFlowTransHub.util.SystemUtils;

import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ConnectionTestController {
	@Autowired
	private AllowedHostService allowedHostService;
	
    @PostMapping("/connectionTest")
    public Map<String, Object> testConnection(@Valid @RequestBody ConnectionInfo info, BindingResult result) {  
    	Map<String, Object> response = new HashMap<>();
        if (result.hasErrors()) {
            response.put("success", false);
            response.put("errors", result.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.toList()));
            return response;
        }
    	
        if (!SystemUtils.isValidIPv4(info.getHost()) || !allowedHostService.isAllowedHost(info.getHost())) {
            response.put("success", false);
            response.put("errors", Collections.singletonList("不是合法的IP或主機不在允許的清單中"));
            return response;
        }
 
        if (!SystemUtils.isAllowedPort(info.getPort())) {
            response.put("success", false);
            response.put("errors", Collections.singletonList("不是合法的埠號"));
            return response;
        }
        
        List<FileInfo> files = new ArrayList<>();
        try {
            files = SFTPFileTransUtils.listFiles(info);  
        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("errors", Collections.singletonList(e.getMessage()));
            return response;
        }

        response.put("success", true);
        response.put("fileInfos", files);

        return response;  
    }
}
