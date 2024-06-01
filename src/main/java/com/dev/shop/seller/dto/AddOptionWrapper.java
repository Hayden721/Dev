package com.dev.shop.seller.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AddOptionWrapper {
    private List<AddOptionsDto> options;
}
