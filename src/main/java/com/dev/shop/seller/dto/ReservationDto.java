package com.dev.shop.seller.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class ReservationDto {
    private Long reservationNo;
    private LocalDate reservationDate;
    private int startTime;
    private int endTime;
    private String memberId;
}
