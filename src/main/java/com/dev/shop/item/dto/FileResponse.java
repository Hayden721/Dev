package com.dev.shop.item.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Getter
@AllArgsConstructor
@ToString

public class FileResponse {
    private Long roomimageNo;
    private String originalName;
    private String saveName;
    private Long fileSize;
    private char thumbnail;
    private char fileDelete;
    private LocalDate uploadDate;

    public String getUploadDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return this.uploadDate.format(formatter);
    }

}
