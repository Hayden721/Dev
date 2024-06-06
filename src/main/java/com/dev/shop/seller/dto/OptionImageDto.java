package com.dev.shop.seller.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OptionImageDto {
    private Long optionImageNo;
    private  String originalName;
    private  String saveName;
    private  long fileSize;
    private char fileDelete;
    private LocalDate createdDate;

    private Long roptionNo;

    @Builder
    public OptionImageDto(String originalName, String saveName, Long fileSize, Long roptionNo, LocalDate createdDate) {
        this.originalName = originalName;
        this.saveName = saveName;
        this.fileSize = fileSize;
        this.roptionNo = roptionNo;
        this.createdDate = createdDate;
    }


}


