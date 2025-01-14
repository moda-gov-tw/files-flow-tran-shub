package com.FilesFlowTransHub.dto;

 
 
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ServiceMainRequest {
	@NotNull(message = "服務代碼(ServiceID) 不可為空")
    @Size(min = 10,max = 10, message = "服務代碼(ServiceID) 長度不可超過 10 碼")
	@Pattern(regexp = "[A-Za-z0-9]+", message = "服務代碼(ServiceID) 不符合規定")
    private String serviceId;

    @Size(max = 1000, message = "描述文字 長度不可超過 1000 碼")
    private String serviceDescription;

    @Size(max = 200, message = "服務名稱 長度不可超過 200 碼")
    private String serviceName;

    @Size(max = 1, message = "狀態長度 不可超過 1 碼")
    private String serviceStatus;

    @Size(max = 100, message = "來源位置 長度不可超過 100 碼")
    private String sourceLocation;

    @Size(max = 5, message = "來源埠號 長度不可超過 5 碼")
    private String sourcePort;

    @Size(max = 500, message = "來源路徑 長度不可超過 500 碼")
    private String sourcePath;

    @Size(max = 50, message = "來源帳號 長度不可超過 50 碼")
    private String sourceId;

    @Size(max = 50, message = "來源密碼 長度不可超過 50 碼")
    private String sourceCred;

    @Size(max = 100, message = "目標位置 長度不可超過 100 碼")
    private String targetLocation;

    @Size(max = 5, message = "目標埠號 長度不可超過 5 碼")
    private String targetPort;

    @Size(max = 500, message = "目標路徑 長度不可超過 500 碼")
    private String targetPath;

    @Size(max = 50, message = "目標帳號 長度不可超過 50 碼")
    private String targetId;

    @Size(max = 50, message = "目標密碼 長度不可超過 50 碼")
    private String targetCred;
    
    @NotNull(message = "Editable 不可為空")
    private Boolean editable;
}
