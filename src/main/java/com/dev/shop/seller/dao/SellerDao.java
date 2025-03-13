package com.dev.shop.seller.dao;


import com.dev.shop.item.dto.FileResponse;
import com.dev.shop.item.dto.OptionFileReqeuest;
import com.dev.shop.reserve.dto.OptionResponse;
import com.dev.shop.seller.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SellerDao {
    /**
     * 아이디로 판매자 정보 가져온다.
     * @param sellerId - 로그인 하려는 아이디
     * @return 판매자 정보
     */
    SellerDetailsDto selectSellerById(String sellerId);

    /**
     * 판매자번호를 조회한다.
     * @param authId - 로그인한 아이디
     * @return 판매자번호
     */
    Long selectSellerNoByAuthId(String authId);

    /**
     * 방 생성을 위해 정보를 저장한다.
     * @param roomRequest
     * @return 방 번호(roomNo)
     */
    Long insertRoomInfo(RoomRequest roomRequest);

    /**
     * 방 옵션을 생성한다.
     * @param options - 방옵션 입력 데이터
     */
    void insertRoomOptionInfo(List<RoomOptionRequest> options);

    /**
     * 판매자가 생성한 공간을 조회한다.
     * @param sellerNo - 로그인한 판매자 번호
     * @return 판매자가 생성한 공간 정보
     */
    List<RoomListDto> selectRoomListBySellerNo(Long sellerNo);

    /**
     *
     * @param roomNo
     * @return
     */
    List<OptionResponse> selectRoomOptionInfoByRoomNo(Long roomNo);

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

    /**
     * 방 옵션을 조회한다.
     * @param roomNo -
     * @return
     */
    List<RoomOptionResponse> selectRoomOption(Long roomNo);

    void updateRoomProgress(Long errorRoomNo);

    /**
     * 썸네일 이미지를 조회한다.
     * @param roomNo -
     * @return
     */
    FileResponse selectThumbnailImage(Long roomNo);

    List<FileResponse> selectExtraImage(Long roomNo);


    RoomResponse selectRoomInfoByRoomNo(Long roomNo);

    void updateRoomInfo(RoomRequest room);

    int selectOptionCount(Long roomNo);

    void updateRoomOptionInfo(List<RoomOptionRequest> roomOption);

    void insertNewOptions(List<RoomOptionRequest> roomOption);

    void addOptionImage(List<OptionFileReqeuest> optionImageList);
}
