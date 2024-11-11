package com.FilesFlowTransHub.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter
@ToString
public class ConnectionInfo {
	private String account;
	private String host;
	private int port;
	private String cred;
	private String remoteDir;
}
