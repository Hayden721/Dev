package com.dev.shop.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import java.time.LocalDate;
import java.util.Date;

@Getter
@AllArgsConstructor
@ToString
public class ReservationResponse {
    private int rowNum;
    private Long reservationNo;
    private LocalDate reservationDate;
    private Date reservationCreationdate;
    private int startTime;
    private int endTime;
    private char reservationStatus;

    private Long roomNo;
    private Long roomOptionNo;

    private String roomTitle;
    private String roomOptionTitle;
}

