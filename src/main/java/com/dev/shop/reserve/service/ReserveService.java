package com.dev.shop.reserve.service;

import com.dev.shop.reserve.dto.*;
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



    Long getMemberNoByAuthId(String authId);

    void insertReservation(String selectDate, Integer reserveStartTime, Integer reserveEndTime, Long sellerNo, Long memberNo, Long roomNo, Long optionNo);

    void updateReservationStatus();

    List<RoomImageDto> getRoomExtraImageByRoomNo(Long roomNo);


    RoomImageDto getRoomThumbnailImageByRoomNo(Long roomNo);

    boolean getBookmarkValue(String memberId, Long roomNo);


    List<OptionAndImageDto> getOptionAndImageByRoomNo(Long roomNo);
}
