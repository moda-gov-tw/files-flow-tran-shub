package com.FilesFlowTransHub.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FilesFlowTransHub.domain.AllowedHost;
import com.FilesFlowTransHub.repository.AllowedHostRepository;

@Service
public class AllowedHostService {
	@Autowired
	private AllowedHostRepository allowedHostRepository;

	/**
	 * 找出所有的允許host清單
	 * @param caseId
	 * @return
	 */
	public List<AllowedHost> findAll() {
		return allowedHostRepository.findAll();
	}

	/***
	 * 查詢所有允許的主機
	 * @param host
	 * @return
	 */
	public boolean isAllowedHost(String host) {
        List<AllowedHost> allowedHosts = allowedHostRepository.findAll();
        System.out.print(allowedHosts.size());
        // 檢查是否有匹配的主機
        for (AllowedHost allowedHost : allowedHosts) {
        	System.out.print(allowedHost);
            if (allowedHost.getAllowedHost().equals(host)) {
                return true;  // 如果找到匹配的主機，返回 true
            }
        }
        
        return false;  // 如果沒有匹配的主機，返回 false
	}
}
