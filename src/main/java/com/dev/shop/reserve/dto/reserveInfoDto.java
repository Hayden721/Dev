package com.dev.shop.reserve.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class reserveInfoDto {

    private Long reservationNo;
    private String reservationDate;
    private int startTime;
    private int endTime;
    private Long sellerNo;
    private Long memberNo;
    private Long roomNo;
    private Long roptionNo;
    private char reservationStatus;
    private LocalDateTime endDateTime;
}
