package com.dev.shop.seller.dto;


import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RequestRoomOptionDto {

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
    private LocalDateTime createdDate;


    public String getCreatedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return this.createdDate.format(formatter);
    }
}
