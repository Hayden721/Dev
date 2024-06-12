package com.dev.shop.seller.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InsertOptionDto {

    private Long roomNo;

    private String title;
    private String price;
    private String content;

    private Long roomOptionNo;

    //이미지
    private String originalName;
    private String saveName;
    private long fileSize;
    private char fileDelete;
    private LocalDate createdDate;



    public String getCreatedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return this.createdDate.format(formatter);
    }

    @Builder
    public InsertOptionDto(String originalName, String saveName, Long fileSize, Long roomNo, LocalDate createdDate) {
        this.originalName = originalName;
        this.saveName = saveName;
        this.fileSize = fileSize;
        this.roomNo = roomNo;
        this.createdDate = createdDate;
    }


}


