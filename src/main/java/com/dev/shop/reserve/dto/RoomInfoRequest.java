package com.dev.shop.reserve.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString

//
public class RoomInfoRequest {
    private Long roomNo; // 번호(Primary Key)
    private String roomTitle; // 방제목
    private String roomDiv; // 방 구분 (스터디룸 / 회의실 / 공간대여)
    private String roomContent; // 상세내용
    private String postCode; // 우편 번호
    private String address; // 주소
    private String detailAddress; // 상세 주소
    private String extraAddress; // 추가 주소
    private Long sellerNo; // 생성자 번호
}