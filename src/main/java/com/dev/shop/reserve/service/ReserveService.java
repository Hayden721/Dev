package com.dev.shop.reserve.service;

import com.dev.shop.item.dto.FileResponse;
import com.dev.shop.reserve.dto.*;
import com.dev.shop.utils.PagingResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface ReserveService {
    /**
     * 예약 가능한 방 조회
     * @param roomDto -
     * @return
     */
    PagingResponse<ReserveRoomListDto> findAllRoom(CriteriaDto roomDto);

    RoomInfoRequest findRoomInfo(Long roomNo);

    Map<String, ArrayList<Integer>> getAvailableReservationTime(String selectDate, Long roomNo, Long optionNo);

    /**
     *
     * @param authId
     * @return
     */
    Long getMemberNo(String authId);

    void insertReservation(String selectDate, Integer reserveStartTime, Integer reserveEndTime, Long sellerNo, Long memberNo, Long roomNo, Long optionNo);

    void updateReservationStatus();

    /**
     *
     * @param roomNo
     * @return
     */
    FileResponse getThumbnailImage(Long roomNo);

    List<FileResponse> getExtraImage(Long roomNo);



    boolean getBookmarkValue(String memberId, Long roomNo);


    List<OptionAddImageDto> getOptionAndImageByRoomNo(Long roomNo);


    boolean roomBookmark(String memberId, Long roomNo);

    String getRoomInfo(Long roomNo, Long optionNo);
}
