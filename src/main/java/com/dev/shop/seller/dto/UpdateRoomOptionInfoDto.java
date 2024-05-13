package com.dev.shop.seller.dto;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRoomOptionInfoDto {
    private String roptionNo;
    private String roptionTitle;
    private String roptionPrice;
    private String roptionContent;

}
