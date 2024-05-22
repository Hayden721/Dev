package com.dev.shop.seller.dao;

import com.dev.shop.seller.dto.ImageFileDto;
import com.dev.shop.seller.dto.ImageFileDto;
import com.dev.shop.reserve.dto.RoomDto;
import com.dev.shop.reserve.dto.RoomOptionDto;
import com.dev.shop.seller.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SellerDao {

    SellerDetailsDto selectSellerById(String sellerId);

    void insertSellerRegister(SellerDto sellerDto);

    void insertRoomInfoBySellerRoomDto(SellerRoomDto sellerRoomDto);

    Long selectSellerNoByAuthId(String authId);

    void insertRoomOptionInfoByOptions(List<PostRoomOptionDto> options);

    List<RoomListDto> selectRoomListBySellerNo(Long sellerNo);

    RoomDto selectRoomDetailByRoomNo(Long roomNo);

    List<RoomOptionDto> selectRoomOptionInfoByRoomNo(Long roomNo);

    int selectRoomOptionCountByRoomNo(Long roomNo);

    int selectRoomCountBySellerNo(Long sellerNo);

    /**
     * 선택한 방을 삭제하는 메서드
     * @param roomNo - 삭제 하려는 방 번호
     */
    void deleteRoomByRoomNo(Long roomNo);

    void deleteRoomOptionByRoomNo(Long roomNo);

    List<ImageFileDto> selectAdditionalImageByRoomNo(Long roomNo);

    ImageFileDto selectThumbnailByRoomNo(Long roomNo);

    List<ReserveManageDto> selectReserveManageInfoBySellerNo(Long sellerNo);

    List<ReservationDto> selectReservationInfoByRoomNo(Long roomNo);



    List<RequestRoomOptionDto> selectOptionInfoAndImageByRoomNo(Long roomNo);

    List<Long> selectRoomOptionNoByRoomNo(Long roomNo);





    void deleteRoomOptionImageByRoomOptionNo(List<Long> roomOptionNo);

    void deleteRoomImageByRoomNo(Long roomNo);

    void updateRoomInfoByRoomInfo(List<UpdateRoomInfoDto> roomInfo);

    void updateRoomOptionInfoByOptionInfo(List<UpdateRoomOptionInfoDto> optionInfo);

    void insertThumbnailImage(ImageFileDto refinedThumbnailImage);


    void insertExtraImages(List<ImageFileDto> refinedImages);
}
