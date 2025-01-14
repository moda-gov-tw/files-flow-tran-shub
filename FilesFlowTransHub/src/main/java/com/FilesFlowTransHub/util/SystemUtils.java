package com.FilesFlowTransHub.util;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.FilesFlowTransHub.domain.AllowedHost;
import com.FilesFlowTransHub.domain.ServiceMain;
import com.FilesFlowTransHub.dto.ConnectionInfo;
import com.FilesFlowTransHub.repository.AllowedHostRepository;
import com.FilesFlowTransHub.service.AllowedHostService;

public class SystemUtils {
	
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();
    
	/**
	 * 生成 caseId
	 * @param serviceId
	 * @return
	 */
	public static String generateCaseId(String serviceId) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
		String date = LocalDate.now().format(dtf);

		String randomAlphanumeric = generateRandomAlphanumeric(9);

		return serviceId + "-" + date + "-" + randomAlphanumeric;
	}

	/**
	 * 生成隨機9碼英數
	 * @param length
	 * @return
	 */
	public static String generateRandomAlphanumeric(int length) {
		
		StringBuilder sb = new StringBuilder(length);

		for (int i = 0; i < length; i++) {
			sb.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
		}
		return sb.toString();
	}
	
	/**
	 * 建立連線參數
	 * @param service
	 * @param isSource
	 * @return
	 */
	public static ConnectionInfo createConnectionInfo(ServiceMain service, boolean isSource) {
        ConnectionInfo info = new ConnectionInfo();
        info.setAccount(isSource ? service.getSourceId() : service.getTargetId());
        info.setCred(Base64Utils.base64ToString(isSource ? service.getSourceCred() : service.getTargetCred()));
        info.setHost(isSource ? service.getSourceLocation() : service.getTargetLocation());
        info.setPort(Integer.parseInt(isSource ? service.getSourcePort() : service.getTargetPort()));
        info.setRemoteDir(isSource ? service.getSourcePath() : service.getTargetPath());
        return info;
    }
	
	/**
	 * 允許的sftp 埠 
	 * @param port 
	 * @return
	 */
    public static boolean isAllowedPort(int port) {
        return port == 22;  
    }
    /**
     * 檢驗字元
     * @param cred
     * @return
     */
	public static boolean isValidCred(String cred) {
	    if (cred == null || cred.length() > 50) {
	        return false;
	    }
	    return cred.matches("^[a-zA-Z0-9!@#$%^&*()_+=-]*$"); 
	}
	
	/**
	 * 檢驗ip格式
	 * @param ip
	 * @return
	 */
	public static boolean isValidIPv4(String ip) {
	    String ipv4Pattern = "^((25[0-5]|2[0-4][0-9]|[0-1]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[0-1]?[0-9][0-9]?)$";
	    return ip.matches(ipv4Pattern);
	}
}
