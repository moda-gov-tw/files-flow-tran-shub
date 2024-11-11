package com.FilesFlowTransHub.domain;

import org.hibernate.annotations.Cache;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "fft_file_details")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Getter 
@Setter
@ToString
public class FileDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Size(max = 1000)
    @Column(name = "file_name", length = 1000, nullable = false)
    private String fileName;

    @Column(name = "file_size")
    private BigDecimal fileSize;

    @NotNull
    @Size(max = 29)
    @Column(name = "folder_id", length = 29, nullable = false)
    private String folderId;

    @Column(name = "last_upload_datetime")
    private LocalDateTime lastUploadDatetime;


    @Size(max = 256)
    @Column(name = "hash_value", length = 256)
    private String hashValue;
}
