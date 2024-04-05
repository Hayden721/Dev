package com.dev.shop.seller.dao;

import com.dev.shop.item.dto.FileResponse;
import com.dev.shop.item.dto.OptionImageRequest;
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

    void deleteRoomByRoomNo(Long roomNo);

    void deleteRoomOptionByRoomNo(Long roomNo);

    List<FileResponse> selectAdditionalImageByRoomNo(Long roomNo);

    FileResponse selectThumbnailByRoomNo(Long roomNo);

    List<ReserveManageDto> selectReserveManageInfoBySellerNo(Long sellerNo);

    List<ReservationDto> selectReservationInfoByRoomNo(Long roomNo);



    List<RequestRoomOptionDto> selectOptionInfoAndImageByRoomNo(Long roomNo);
}
