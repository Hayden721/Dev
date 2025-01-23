package com.dev.shop.seller.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class RoomOptionRequest {
    private Long roptionNo;
    private String roptionTitle;
    private String roptionContent;
    private int roptionPrice;
    private Long roomNo;


    @Builder
    public RoomOptionRequest(Long roptionNo, String roptionTitle,
                             String roptionContent, int roptionPrice, Long roomNo) {
        this.roptionNo = roptionNo;
        this.roptionTitle = roptionTitle;
        this.roptionContent = roptionContent;
        this.roptionPrice = roptionPrice;
        this.roomNo = roomNo;
    }
}
