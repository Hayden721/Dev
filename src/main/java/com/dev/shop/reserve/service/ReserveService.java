package com.dev.shop.reserve.service;

import com.dev.shop.reserve.dto.RoomDto;

import java.util.List;

public interface ReserveService {
    List<RoomDto> findAllRoom();
}
