package com.FilesFlowTransHub.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FilesFlowTransHub.domain.ServiceMain;
import com.FilesFlowTransHub.repository.ServiceMainRepository;

@Service
public class ServiceService {
	@Autowired
	private ServiceMainRepository serviceRepository;
	
	/**
	 * 儲存服務
	 * @param service
	 */
	public void save(ServiceMain service) {
		serviceRepository.save(service);
	}
	
	/**
	 * 找尋服務列表
	 * @return
	 */
	public List<ServiceMain> findAll() {
		return serviceRepository.findAll();
	}
	
	/**
	 * 依serviceId找出資訊
	 * @param serviceId
	 * @return
	 */
	public ServiceMain findByServiceId(String serviceId) {
		return serviceRepository.findByServiceId(serviceId);
	}
	
	/**
	 * 判斷serviceId是否存在
	 * @param serviceId
	 * @return
	 */
	public boolean isServiceIdValid(String serviceId) {
		ServiceMain serviceData = serviceRepository.findByServiceId(serviceId);
		if (serviceData == null) {
			return false;
		} else if ("0".equals(serviceData.getServiceStatus())) {
			return false;
		}

		return true;
	}
}
