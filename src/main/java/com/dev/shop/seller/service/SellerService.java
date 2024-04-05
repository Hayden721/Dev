package com.dev.shop.seller.service;

import com.dev.shop.item.dto.FileResponse;
import com.dev.shop.item.dto.OptionImageRequest;
import com.dev.shop.reserve.dto.RoomDto;
import com.dev.shop.reserve.dto.RoomOptionDto;
import com.dev.shop.seller.dto.*;

import java.util.List;

public interface SellerService {
    void sellerRegister(SellerDto sellerDto);


    void insertRoomOptionInfoByOptions(List<PostRoomOptionDto> options);

    Long insertRoomInfoByPostRoomDto(SellerRoomDto postRoomDto);

    Long getSellerNoByAuthId(String authId);

    List<RoomListDto> getRoomListBySellerNo(Long sellerNo);
    RoomDto getRoomDetailByRoomNo(Long roomNo);

    List<RoomOptionDto> getRoomOptionInfoByRoomNo(Long roomNo);

    int getRoomOptionCountByRoomNo(Long roomNo);

    int getRoomCountBySellerNo(Long sellerNo);

    void removeRoomOptionByRoomNo(Long roomNo);

    void removeRoomByRoomNo(Long roomNo);

    List<FileResponse> getAdditionalImageByRoomNo(Long roomNo);

    FileResponse getThumbnailImageByRoomNo(Long roomNo);



    List<ReserveManageDto> getReserveManageInfoBySellerNo(Long sellerNo);

    List<ReservationDto> getReservationInfoByRoomNo(Long roomNo);



    List<RequestRoomOptionDto> getOptionInfoAndImage(Long roomNo);
}
