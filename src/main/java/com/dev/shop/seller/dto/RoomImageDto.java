package com.dev.shop.seller.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class RoomImageDto {
    private Long roomImageNo;
    private String originalName;
    private String saveName;
    private int fileSize;
    private LocalDate createdDate;
    private LocalDate deleteDate;
    private char thumbnail;
    private Long roomNo;

    @Builder
    public RoomImageDto(String originalName, String saveName, int fileSize) {
        this.originalName = originalName;
        this.saveName = saveName;
        this.fileSize = fileSize;
    }

    public void setPostId(Long roomImageNo) {
        this.roomImageNo = roomImageNo;
    }

}
