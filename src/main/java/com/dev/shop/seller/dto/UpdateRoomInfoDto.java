package com.dev.shop.seller.dto;


import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRoomInfoDto {
    private Long roomNo;
    private String roomTitle;
    private String roomContent;
    private String roomDiv;
    private String roomAddress;
    private String roomDetailAddr;
    private String roomExtraAddr;
}
