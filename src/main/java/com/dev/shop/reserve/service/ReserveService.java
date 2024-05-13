package com.dev.shop.reserve.service;

import com.dev.shop.reserve.dto.ReserveRoomListDto;
import com.dev.shop.reserve.dto.RoomDto;
import com.dev.shop.reserve.dto.RoomOptionDto;
import com.dev.shop.reserve.dto.CriteriaDto;
import com.dev.shop.seller.dto.RoomImageDto;
import com.dev.shop.utils.PagingResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface ReserveService {
    PagingResponse<ReserveRoomListDto> findAllRoom(CriteriaDto roomDto);

    RoomDto findRoomInfo(Long roomNo);


    List<RoomOptionDto> findRoomOptionInfo(Long roomNo);

    Map<String, ArrayList<Integer>> getAvailableReservationTime(String selectDate, Long roomNo, Long optionNo);



    String memberNoByAuthId(String authId);

    void insertReservation(String selectDate, Integer reserveStartTime, Integer reserveEndTime, Long sellerNo, Long memberNo, Long roomNo, Long optionNo);

    void updateReservationStatus();

    List<RoomImageDto> getRoomExtraImageByRoomNo(Long roomNo);


    RoomImageDto getRoomThumbnailImageByRoomNo(Long roomNo);
}
