package com.dev.shop.seller.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ImageFileDto {
    private Long roomImageNo;
    private String originalName;
    private String saveName;
    private long fileSize;
    private char thumbnail;
    private char fileDelete;
    private LocalDate createdDate;

    private Long roomNo;

    public String getCreatedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return this.createdDate.format(formatter);
    }

    @Builder
    public ImageFileDto(String originalName, String saveName, Long fileSize, Long roomNo, LocalDate createdDate) {
        this.originalName = originalName;
        this.saveName = saveName;
        this.fileSize = fileSize;
        this.roomNo = roomNo;
        this.createdDate = createdDate;
    }


}


