package com.dev.shop.item.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
public class FileRequest {
    private Long roomImageNo;
    private final String originalName;
    private final String saveName;
    private final long fileSize;
    private char thumbnail;
    private char fileDelete;
    private LocalDate createdDate;

    private Long roomNo;

    @Builder
    public FileRequest(String originalName, String saveName, Long fileSize, Long roomNo) {
        this.originalName = originalName;
        this.saveName = saveName;
        this.fileSize = fileSize;
        this.roomNo = roomNo;
    }

    public void setRoomNo(Long roomNo) {
        this.roomNo = roomNo;
    }
    public void setThumbnail(char thumbnail){
        this.thumbnail = thumbnail;
    }
}


