package com.dev.shop.reserve.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @AllArgsConstructor @ToString
public class RoomDto {

    private Long roomNo; // 번호(Primary Key)
    private String roomTitle; // 방제목
    private String roomDiv; // 방 구분 (스터디룸 / 회의실 / 공간대여)
    private String roomAddress; // 주소 시/군/구 나중에 생각
    private String roomContent; // 상세내용

    private Long sellerNo;

}