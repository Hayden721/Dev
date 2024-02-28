package com.dev.shop.reserve.dto;

import com.dev.shop.utils.Pagination;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class CriteriaDto {
    private int page;   // 현재 페이지 번호
    private int recordSize; // 페이지당 출력할 데이터 개수
    private int pageSize; // 하단에 출력될 페이지 사이즈

    private String keyword; // 검색 키워드
    private String searchLocation;  // 지역 검색
    private String searchDiv; // 방 유형 검색

    private Pagination pagination;

    public CriteriaDto() {
        this.page = 1;
        this.recordSize = 4;
        this.pageSize = 5;
    }

    public String[] getTypeArr() {
        return keyword == null ? new String[]{} : keyword.split("");
    }




}
