package com.dev.shop.reserve.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class OptionResponse {
    private Long roptionNo;
    private String roptionTitle;
    private String roptionPrice;
    private String roptionContent;

    private Long roomNo; // foreign key
}
