package com.dev.shop.seller.dto;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRoomInfoDto {
    private String roomTitle;
    private String roomContent;
    private String roomDiv;
}
