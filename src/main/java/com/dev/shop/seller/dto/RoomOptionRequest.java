package com.dev.shop.seller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class RoomOptionRequest {
    @JsonProperty("rOptionNo")
    private Long roptionNo;

    @JsonProperty("rOptionTitle")
    private String roptionTitle;

    @JsonProperty("rOptionContent")
    private String roptionContent;

    @JsonProperty("rOptionPrice")
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
