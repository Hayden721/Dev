package com.dev.shop.reserve.service.impl;

import com.dev.shop.reserve.dao.ReserveDao;
import com.dev.shop.reserve.dto.RoomDto;
import com.dev.shop.reserve.dto.RoomOptionDto;
import com.dev.shop.reserve.dto.CriteriaDto;
import com.dev.shop.reserve.service.ReserveService;

import com.dev.shop.utils.Pagination;
import com.dev.shop.utils.PagingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReserveServiceImpl implements ReserveService {

    private final ReserveDao reserveDao;

    @Override
    public PagingResponse<RoomDto> findAllRoom(CriteriaDto params) {
        // 조건에 해당하는 데이터가 없는 경우, 응답 데이터에 비어있는 리스트와 null을 담아 반환
        int count = reserveDao.countAllList(params);
        if (count < 1) {
            return new PagingResponse<>(Collections.emptyList(), null);
        }

        // Pagination 객체를 생성해서 페이지 정보 계산 후 SearchDto 타입의 객체인 params에 계산된 페이지 정보 저장
        Pagination pagination = new Pagination(count, params);
        params.setPagination(pagination);
        // 계산된 페이지 정보의 일부(limitStart, recordSize)를 기준으로 리스트 데이터 조회 후 응답 데이터 반환
        List<RoomDto> list = reserveDao.selectRoomList(params);
        return new PagingResponse<>(list, pagination);

    }

    @Override
    public RoomDto findRoomInfo(Long roomNo) {
        return reserveDao.selectRoomInfoByRoomNo(roomNo);
    }

    @Override
    public List<RoomOptionDto> findRoomOptionInfo(Long roomNo) {
        return reserveDao.selectRoomOptionInfoByRoomNo(roomNo);
    }

}
