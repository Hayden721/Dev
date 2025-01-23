package com.dev.shop.seller.dao;


import com.dev.shop.item.dto.FileResponse;
import com.dev.shop.item.dto.OptionFileReqeuest;
import com.dev.shop.reserve.dto.RoomOptionDto;
import com.dev.shop.seller.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SellerDao {
    /**
     * 아이디로 판매자 정보 가져오기
     * @param sellerId - 로그인 하려는 아이디
     * @return 판매자 정보
     */
    SellerDetailsDto selectSellerById(String sellerId);

    /**
     * 판매자번호 조회
     * @param authId - 로그인한 아이디
     * @return 판매자번호
     */
    Long selectSellerNoByAuthId(String authId);

    /**
     *
     * @param roomResponse
     * @return
     */
    Long insertRoomInfo(RoomRequest roomResponse);

    /**
     * 방 옵션 생성
     * @param options - 방옵션 입력 데이터
     */
    void insertRoomOptionInfo(List<RoomOptionRequest> options);

    List<RoomListDto> selectRoomListBySellerNo(Long sellerNo);

    

    List<RoomOptionDto> selectRoomOptionInfoByRoomNo(Long roomNo);

    int selectRoomOptionCountByRoomNo(Long roomNo);

    /**
     * 선택한 방을 삭제하는 메서드
     * @param roomNo - 삭제 하려는 방 번호
     */
    void deleteRoomByRoomNo(Long roomNo);

    void deleteRoomOptionByRoomNo(Long roomNo);


    List<ReserveManageDto> selectReserveManageInfoBySellerNo(Long sellerNo);

    List<ReservationDto> selectReservationInfoByRoomNo(Long roomNo);

    List<OptionAndImageResponse> selectOptionAndImageByRoomNo(Long roomNo);

    List<Long> selectRoomOptionNoByRoomNo(Long roomNo);

    void deleteRoomOptionImagesByRoomOptionNo(List<Long> roomOptionNo);

    void deleteRoomImageByRoomNo(Long roomNo);

    void insertOptionInfoByOptionData(RoomOptionRequest item);

    void deleteOptionImage(Long optionImageNo);

    void deleteOption(Long optionNo);



    int selectIdDuplicateBySellerId(String sellerId);

    void insertSellerRegister(SellerRegisterRequest registerInfo);



    int selectRoomCountByAuthId(Long sellerNo);


    void deleteErrorRoom(Long roomNo);

    List<RoomOptionResponse> selectRoomOption(Long roomNo);

    List<FileResponse> selectRoomImage(Long roomNo);

    void updateRoomProgress(Long errorRoomNo);

    FileResponse selectThumbnailImage(Long roomNo);

    List<FileResponse> selectExtraImage(Long roomNo);


    RoomResponse selectRoomInfoByRoomNo(Long roomNo);

    void updateRoomInfo(RoomRequest room);

    int selectOptionCount(Long roomNo);

    void updateRoomOptionInfo(List<RoomOptionRequest> roomOption);

    void insertNewOptions(List<RoomOptionRequest> roomOption);

    void addOptionImage(List<OptionFileReqeuest> optionImageList);
}
