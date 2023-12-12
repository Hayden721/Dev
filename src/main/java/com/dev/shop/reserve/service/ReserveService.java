package com.dev.shop.reserve.service;

import com.dev.shop.reserve.dto.RoomDto;
import com.dev.shop.reserve.dto.RoomOptionDto;
import com.dev.shop.reserve.dto.CriteriaDto;
import com.dev.shop.utils.PagingResponse;

import java.util.List;

public interface ReserveService {
    PagingResponse<RoomDto> findAllRoom(CriteriaDto roomDto);

    RoomDto findRoomInfo(Long roomNo);


    List<RoomOptionDto> findRoomOptionInfo(Long roomNo);
}
