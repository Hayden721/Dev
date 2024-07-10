package com.dev.shop.reserve.dto;

import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Accessors(chain = true)
public class OptionAndImageDto {
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
    private LocalDateTime createdDate;

    private Long roomNo;

    public String getCreatedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return this.createdDate.format(formatter);
    }
}


