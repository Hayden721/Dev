package com.dev.shop.seller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class RoomListDto {
    private Long roomNo;
    private String roomTitle;
    private String roomDiv;
    private int cnt;
}
