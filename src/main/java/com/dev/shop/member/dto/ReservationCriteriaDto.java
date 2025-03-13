package com.dev.shop.member.dto;

import com.dev.shop.utils.Pagination;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ReservationCriteriaDto {
    private int page;   // 현재 페이지 번호
    private int recordSize; // 페이지당 출력할 데이터 개수
    private int pageSize; // 하단에 출력될 페이지 사이즈

    private String keyword; // 검색 키워드
    private List<String> searchType; // 검색 타입

    private Pagination pagination;

    public ReservationCriteriaDto() {
        this.page = 1;
        this.recordSize = 9;
        this.pageSize = 5;
    }

    public String[] getTypeArr() {
        return keyword == null ? new String[]{} : keyword.split("");
    }

}
