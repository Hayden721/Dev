package com.dev.shop.seller.dto;


import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OptionAndImageResponse {
    private Long roptionNo;
    private String roptionTitle;
    private int roptionPrice;
    private String roptionContent;
    private char roptionDelete;
    private String roomNo;

    private Long roptionImageNo;
    private String originalName;
    private String saveName;
    private int fileSize;
    private char fileDelete;
    private LocalDate uploadDate;


    public String getUploadDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return this.uploadDate.format(formatter);
    }
}
