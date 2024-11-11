package com.FilesFlowTransHub.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.FilesFlowTransHub.dto.ConnectionInfo;
import com.FilesFlowTransHub.dto.FileInfo;
import com.FilesFlowTransHub.util.SFTPFileTransUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ConnectionTestController {

    @PostMapping("/connectionTest")
    public Map<String, Object> testConnection(@RequestBody ConnectionInfo info) {  

        List<FileInfo> files = new ArrayList<>();
        try {
            files = SFTPFileTransUtils.listFiles(info);  
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, Object> response = new HashMap<>();
        response.put("fileInfos", files);

        return response;  
    }
}
