package com.FilesFlowTransHub.domain;

import org.hibernate.annotations.Cache;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.time.LocalDateTime;

@Entity
@Table(name = "fft_file_main")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Getter 
@Setter
@ToString
public class FileMain {

    @Id
    @Size(max = 29)
    @Column(name = "folder_id", length = 29, nullable = false)
    private String folderId;

    @Column(name = "case_create_datetime")
    private LocalDateTime caseCreateDatetime;

    @Size(max = 200)
    @Column(name = "folder_path", length = 200)
    private String folderPath;

}
