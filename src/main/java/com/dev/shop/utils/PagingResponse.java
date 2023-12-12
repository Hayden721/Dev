package com.dev.shop.utils;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
@Getter @ToString
public class PagingResponse<T> {

    private final List<T> list = new ArrayList<>();
    private final Pagination pagination;

    public PagingResponse(List<T> list, Pagination pagination) {
        this.list.addAll(list);
        this.pagination = pagination;
    }


}
