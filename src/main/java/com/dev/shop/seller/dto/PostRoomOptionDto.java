package com.dev.shop.seller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PostRoomOptionDto {

    private Long roomOptionNo;

    @JsonProperty("roomOptionTitle")
    private String roomOptionTitle;
    @JsonProperty("roomOptionContent")
    private String roomOptionContent;
    @JsonProperty("roomOptionPrice")
    private int roomOptionPrice;

    private Long roomNo;


    public PostRoomOptionDto(Long roomNo, String roomOptionTitle, String roomOptionContent, int roomOptionPrice) {
        this.roomNo = roomNo;
        this.roomOptionTitle = roomOptionTitle;
        this.roomOptionContent = roomOptionContent;
        this.roomOptionPrice = roomOptionPrice;
    }
}
