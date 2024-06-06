package com.dev.shop.seller.service;

import com.dev.shop.reserve.dto.RoomDto;
import com.dev.shop.reserve.dto.RoomOptionDto;
import com.dev.shop.seller.dto.*;
import org.springframework.web.multipart.MultipartFile;

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

    List<ImageFileDto> getAdditionalImageByRoomNo(Long roomNo);

    ImageFileDto getThumbnailImageByRoomNo(Long roomNo);



    List<ReserveManageDto> getReserveManageInfoBySellerNo(Long sellerNo);

    List<ReservationDto> getReservationInfoByRoomNo(Long roomNo);





    void updateRoomInfoByData(RoomUpdateRequest data);



    // 이미지 관련 기능

    void sellerUploadExtraImagesByRoomNo(Long roomNo, List<MultipartFile> extraImages);


    void sellerUploadThumbnailImageByRoomNo(Long roomNo, MultipartFile thumbnailImage);


    List<RequestRoomOptionDto> getOptionInfoAndImage(Long roomNo);

    void sellerUpdateImageByImageNo(Long imageNo, MultipartFile extraImage);


    void insertRoomOptionByFormData(Long roomNo, List<String> titles, List<Integer> prices, List<String> contents, List<MultipartFile> images);
}
