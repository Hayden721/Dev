package com.dev.shop.item.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OptionFileReqeuest {
    private Long roptionimageNo;
    private  String originalName;
    private  String saveName;
    private  long fileSize;
    private LocalDate uploadDate;
    private char fileDelete;

    private Long roptionNo;

    @Builder
    public OptionFileReqeuest(Long roptionimageNo, String originalName, String saveName, Long fileSize, Long roptionNo, LocalDate uploadDate) {
        this.roptionimageNo = roptionimageNo;
        this.originalName = originalName;
        this.saveName = saveName;
        this.fileSize = fileSize;
        this.roptionNo = roptionNo;
        this.uploadDate = uploadDate;

    }


}


