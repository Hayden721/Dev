package com.dev.shop.item.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor

@ToString
public class FileRequest {
    private Long roomimageNo;
    private String originalName;
    private String saveName;
    private long fileSize;
    private char thumbnail;
    private LocalDate uploadDate;
    private char fileDelete;
    private Long roomNo;

    @Builder
    public FileRequest(Long roomimageNo, String originalName, String saveName, long fileSize, char thumbnail, LocalDate uploadDate, char fileDelete, Long roomNo) {
        this.roomimageNo = roomimageNo;
        this.originalName = originalName;
        this.saveName = saveName;
        this.fileSize = fileSize;
        this.thumbnail = thumbnail;
        this.uploadDate = uploadDate;
        this.fileDelete = fileDelete;
        this.roomNo = roomNo;
    }
}
