package com.dev.shop.seller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor

@ToString
public class PostRoomOptionDto {

    private Long roomOptionNo;

    @JsonProperty("roomOptionTitle")
    private String roomOptionTitle;
    @JsonProperty("roomOptionContent")
    private String roomOptionContent;
    @JsonProperty("roomOptionPrice")
    private int roomOptionPrice;

    private String roomNo;

    public PostRoomOptionDto() {
    }
}
