package com.dev.shop.reserve.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OptionAddImageDto {
    // option
    private Long roptionNo;
    private String roptionTitle;
    private int roptionPrice;
    private String roptionContent;
    private char roptionDelete;

    //optionImage
    private Long roptionImageNo;
    private String originalName;
    private String saveName;
    private Long fileSize;
    private char fileDelete;
    private LocalDateTime uploadDate;

    private Long roomNo;

    public String getUploadDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return this.uploadDate.format(formatter);
    }
}


