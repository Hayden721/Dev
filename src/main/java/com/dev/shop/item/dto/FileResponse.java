package com.dev.shop.item.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Getter
@AllArgsConstructor
@ToString

public class FileResponse {
    private Long roomImageNo;
    private String originalName;
    private String saveName;
    private Long fileSize;
    private char thumbnail;
    private char fileDelete;
    private LocalDateTime createDate;
    private Long roomNo;

    public String getCreateDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return this.createDate.format(formatter);
    }

}
