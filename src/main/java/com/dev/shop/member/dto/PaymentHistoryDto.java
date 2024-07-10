package com.dev.shop.member.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class PaymentHistoryDto {
    private int rowNum;
    private String roomTitle;
    private String roptionTitle;
    private LocalDateTime reservationDate;
    private int startTime;
    private int endTime;
    private int totalPrice;


    public String getReservationDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return this.reservationDate.format(formatter);
    }
}
