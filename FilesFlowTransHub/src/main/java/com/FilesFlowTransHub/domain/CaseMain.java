package com.FilesFlowTransHub.domain;


import org.hibernate.annotations.Cache;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.time.LocalDateTime;

@Entity
@Table(name = "fft_case_main")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Getter 
@Setter
@ToString
public class CaseMain {

    @Id
    @NotNull
    @Size(max = 29)
    @Column(name = "case_id", length = 29, nullable = false)
    private String caseId;

    @Column(name = "case_create_datetime")
    private LocalDateTime caseCreateDatetime;

    @Size(max = 2)
    @Column(name = "case_status", length = 2)
    private String caseStatus;

    @Column(name = "case_update_datetime")
    private LocalDateTime caseUpdateDatetime;


    @Size(max = 10)
    @Column(name = "service_id", length = 10)
    private String serviceId;

 
}
