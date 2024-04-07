package com.dev.shop.seller.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SellerRoomDto {

    private Long roomNo;

    private String roomTitle;
    private String roomDiv;
    private String roomContent;

    private String postCode;
    private String address;
    private String detailAddress;
    private String extraAddress;

    private Long sellerNo;


}
