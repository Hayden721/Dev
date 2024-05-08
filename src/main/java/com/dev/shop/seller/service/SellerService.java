package com.dev.shop.seller.service;

import com.dev.shop.item.dto.FileResponse;
import com.dev.shop.item.dto.OptionImageRequest;
import com.dev.shop.reserve.dto.RoomDto;
import com.dev.shop.reserve.dto.RoomOptionDto;
import com.dev.shop.seller.dto.*;

import java.util.List;

public interface SellerService {
    void sellerRegister(SellerDto sellerDto);

    /**
     *
     * @param options - seller 유저가 입력한 옵션 정보
     */
    void insertRoomOptionInfoByOptions(List<PostRoomOptionDto> options);

    /**
     *
     * @param postRoomDto
     * @return roomNo
     */
    Long insertRoomInfoByPostRoomDto(SellerRoomDto postRoomDto);

    Long getSellerNoByAuthId(String authId);

    List<RoomListDto> getRoomListBySellerNo(Long sellerNo);
    RoomDto getRoomDetailByRoomNo(Long roomNo);

    List<RoomOptionDto> getRoomOptionInfoByRoomNo(Long roomNo);

    int getRoomOptionCountByRoomNo(Long roomNo);

    int getRoomCountBySellerNo(Long sellerNo);

    void removeRoomByRoomNo(Long roomNo);

    List<FileResponse> getAdditionalImageByRoomNo(Long roomNo);

    FileResponse getThumbnailImageByRoomNo(Long roomNo);



    List<ReserveManageDto> getReserveManageInfoBySellerNo(Long sellerNo);

    List<ReservationDto> getReservationInfoByRoomNo(Long roomNo);



    List<RequestRoomOptionDto> getOptionInfoAndImage(Long roomNo);

    void updateRoomInfoByData(RoomUpdateRequest data);
}
