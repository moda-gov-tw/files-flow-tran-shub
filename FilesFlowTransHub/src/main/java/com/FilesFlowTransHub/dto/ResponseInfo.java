package com.FilesFlowTransHub.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter
@ToString
public class ResponseInfo {
	private String caseId;
	private String statusCode;
	private String statusMessage;
}
