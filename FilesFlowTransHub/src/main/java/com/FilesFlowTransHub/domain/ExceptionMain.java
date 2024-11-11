package com.FilesFlowTransHub.domain;

import org.hibernate.annotations.Cache;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.time.LocalDateTime;

@Entity
@Table(name = "fft_exception")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Getter 
@Setter
@ToString
public class ExceptionMain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 29)
    @Column(name = "case_id", length = 29)
    private String caseId;

    @Column(name = "exception_datetime")
    private LocalDateTime exceptionDatetime;

    @Size(max = 3000)
    @Column(name = "exception_message", length = 3000)
    private String exceptionMessage;

    @Size(max = 10)
    @Column(name = "service_id", length = 10)
    private String serviceId;
}
