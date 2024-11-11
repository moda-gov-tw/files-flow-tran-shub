package com.FilesFlowTransHub.domain;

import org.hibernate.annotations.Cache;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "fft_service_main")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Getter 
@Setter
@ToString
@Accessors(fluent = false) 
public class ServiceMain {
    @Id
    @NotNull
    @Size(max = 10)
    @Column(name = "service_id", length = 10, nullable = false)
    private String serviceId;
    
    @Size(max = 1000)
    @Column(name = "service_description", length = 1000)
    private String serviceDescription;
    
    @Size(max = 200)
    @Column(name = "service_name", length = 200)
    private String serviceName;
    
    @Size(max = 1)
    @Column(name = "service_status", length = 1)
    private String serviceStatus;
    

    @Size(max = 100)
    @Column(name = "source_location", length = 100)
    private String sourceLocation;

    @Size(max = 30)
    @Column(name = "source_port", length = 30)
    private String sourcePort;

    @Size(max = 500)
    @Column(name = "source_path", length = 500)
    private String sourcePath;

    @Size(max = 50)
    @Column(name = "source_id", length = 50)
    private String sourceId;

    @Size(max = 50)
    @Column(name = "source_cred", length = 50)
    private String sourceCred;
    

    @Size(max = 100)
    @Column(name = "target_location", length = 100)
    private String targetLocation;

    @Size(max = 30)
    @Column(name = "target_port", length = 30)
    private String targetPort;

    @Size(max = 500)
    @Column(name = "target_path", length = 500)
    private String targetPath;

    @Size(max = 50)
    @Column(name = "target_id", length = 50)
    private String targetId;

    @Size(max = 50)
    @Column(name = "target_cred", length = 50)
    private String targetCred;
    
    @Transient
    private boolean editable;
    
}
