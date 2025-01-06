package com.FilesFlowTransHub.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter
@ToString
public class ConnectionInfo {
	@NotNull
	@Size(max = 50, message = "帳號 長度不可超過 50 碼")
	private String account;
	@NotNull
	@Size(max = 15, message = "IP 長度不可超過 15 碼")
	private String host;
	@NotNull
	private int port;
	@NotNull
	@Size(max = 50, message = "密碼 長度不可超過 50 碼")
	private String cred;
	@NotNull
	@Size(max = 100, message = "目錄 長度不可超過 100 碼")
	private String remoteDir;
}
