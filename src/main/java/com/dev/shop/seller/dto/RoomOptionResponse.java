package com.dev.shop.seller.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RoomOptionResponse {
    private Long roptionNo;
    private String roptionTitle;
    private int roptionPrice;
    private String roptionContent;

}
