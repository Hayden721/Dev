package com.dev.shop.seller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReserveManageDto {
    private Long roomNo;
    private String roomTitle;
    private String roomDiv;
    private String cnt;
}
