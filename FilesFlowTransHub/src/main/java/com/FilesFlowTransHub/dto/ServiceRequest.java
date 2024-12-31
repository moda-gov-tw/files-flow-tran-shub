package com.FilesFlowTransHub.dto;

 
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ServiceRequest {
	
	@NotBlank(message = "ServiceID 不可以為空值")
    @Size(min=10, max = 10, message = "ServiceID 長度不可超過 10 碼")
	@Pattern(regexp = "[A-Za-z0-9]+", message = "ServiceID 不符合規定")
	private String serviceId;
	
}
