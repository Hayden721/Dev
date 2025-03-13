package com.dev.shop.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class RoomAndImageResponse {
    private Long roomNo; // 번호(Primary Key)
    private String roomTitle; // 방제목
    private String roomDiv; // 방 구분 (스터디룸 / 회의실 / 공간대여)
    private String roomContent; // 상세내용
    private String postcode; // 우편번호
    private String address; // 주소
    private String detailAddress; // 상세주소
    private String extraAddress; // 추가주소
    private char roomDelete; // 방 삭제여부
    private Long sellerNo; // 방 생성자

    private int optionCnt; // 옵션 개수

    // thumbnail image
    private LocalDateTime uploadDate; // 파일 업로드 날짜
    private String saveName; // 저장한 파일 이름
    private String originalName; // 원래 이름

    public String getUploadDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return this.uploadDate.format(formatter);
    }
}
