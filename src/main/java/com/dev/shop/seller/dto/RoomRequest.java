package com.dev.shop.seller.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class RoomRequest {
    private Long roomNo;
    private String roomTitle;
    private String roomDiv;
    private String roomContent;

    private String postcode;
    private String address;
    private String detailAddress;
    private String extraAddress;
    private char roomProgress;
    private Long sellerNo;

    @Builder
    public RoomRequest(Long roomNo, String roomTitle, String roomDiv, String roomContent,
                       String postcode, String address, String detailAddress, String extraAddress,
                       char roomProgress, Long sellerNo) {
        this.roomNo = roomNo;
        this.roomTitle = roomTitle;
        this.roomDiv = roomDiv;
        this.roomContent = roomContent;
        this.postcode = postcode;
        this.address = address;
        this.detailAddress = detailAddress;
        this.extraAddress = extraAddress;
        this.roomProgress = roomProgress;
        this.sellerNo = sellerNo;
    }
}
