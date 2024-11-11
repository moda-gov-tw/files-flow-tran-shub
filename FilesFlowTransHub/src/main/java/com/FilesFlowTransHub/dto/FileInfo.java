package com.FilesFlowTransHub.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter
@ToString
public class FileInfo {
    private String name;
    private long size;
    private String lastModified;

    public FileInfo(String name, long size, String lastModified) {
        this.name = name;
        this.size = size;
        this.lastModified = lastModified;
    }
}