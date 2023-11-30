package com.dev.shop.reserve.service.impl;

import com.dev.shop.reserve.dao.ReserveDao;
import com.dev.shop.reserve.dto.RoomDto;
import com.dev.shop.reserve.service.ReserveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReserveServiceImpl implements ReserveService {

    private final ReserveDao reserveDao;

    @Override
    public List<RoomDto> findAllRoom() {
        return reserveDao.selectAllRoom();
    }
}
