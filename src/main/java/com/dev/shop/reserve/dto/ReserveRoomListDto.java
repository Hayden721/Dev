package com.dev.shop.reserve.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class ReserveRoomListDto {

    private Long roomNo; // 번호(Primary Key)
    private String roomTitle; // 방제목
    private String roomDiv; // 방 구분 (스터디룸 / 회의실 / 공간대여)
    private String roomContent; // 상세내용
    private String postCode;
    private String address;
    private String detailAddress;
    private String extraAddress;
    private String roomDelete;
    private Long sellerNo;

    private int optionCnt;

    // thumbnail image
    private LocalDate uploadDate;
    private String saveName;
    private String originalName;

    public String getUploadDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return this.uploadDate.format(formatter);
    }
}
