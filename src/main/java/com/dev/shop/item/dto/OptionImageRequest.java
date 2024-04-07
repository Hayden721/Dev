package com.dev.shop.item.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
public class OptionImageRequest {
    private Long optionImageNo;
    private final String originalName;
    private final String saveName;
    private final long fileSize;
    private char fileDelete;
    private LocalDate createdDate;

    private Long roptionNo;

    @Builder
    public OptionImageRequest(String originalName, String saveName, Long fileSize, Long roptionNo) {
        this.originalName = originalName;
        this.saveName = saveName;
        this.fileSize = fileSize;
        this.roptionNo = roptionNo;
    }

    public void setRoptionNo(Long roptionNo) {
        this.roptionNo = roptionNo;
    }

}


