package com.dev.shop.seller.service.impl;

import com.dev.shop.item.dto.FileResponse;
import com.dev.shop.item.dto.OptionImageRequest;
import com.dev.shop.reserve.dto.RoomDto;
import com.dev.shop.reserve.dto.RoomOptionDto;
import com.dev.shop.seller.dto.*;
import com.dev.shop.seller.dao.SellerDao;
import com.dev.shop.seller.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {
    private final SellerDao sellerDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    SimpleDateFormat format = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:sss");
    Date time = new Date();
    String localTime = format.format(time);

    @Override
    public void sellerRegister(SellerDto sellerDto) {

        sellerDto.setSellerPw(bCryptPasswordEncoder.encode(sellerDto.getSellerPw()));
        sellerDto.setSellerAuth("SELLER");
        sellerDto.setAppendDate(localTime);
        sellerDto.setUpdateDate(localTime);

        sellerDao.insertSellerRegister(sellerDto);
    }

    @Override
    public Long insertRoomInfoByPostRoomDto(SellerRoomDto sellerRoomDto) {

        sellerDao.insertRoomInfoBySellerRoomDto(sellerRoomDto);


        return sellerRoomDto.getRoomNo();
    }

    @Override
    public Long getSellerNoByAuthId(String authId) {
        return sellerDao.selectSellerNoByAuthId(authId);
    }

    @Override
    public void insertRoomOptionInfoByOptions(List<PostRoomOptionDto> options) {

         sellerDao.insertRoomOptionInfoByOptions(options);
    }

    @Override
    public List<RoomListDto> getRoomListBySellerNo(Long sellerNo) {
        return sellerDao.selectRoomListBySellerNo(sellerNo);
    }

    @Override
    public RoomDto getRoomDetailByRoomNo(Long roomNo) {

        return sellerDao.selectRoomDetailByRoomNo(roomNo);
    }

    @Override
    public List<RoomOptionDto> getRoomOptionInfoByRoomNo(Long roomNo) {
        return sellerDao.selectRoomOptionInfoByRoomNo(roomNo);
    }

    @Override
    public int getRoomOptionCountByRoomNo(Long roomNo) {
        return sellerDao.selectRoomOptionCountByRoomNo(roomNo);
    }

    @Override
    public int getRoomCountBySellerNo(Long sellerNo) {
        return sellerDao.selectRoomCountBySellerNo(sellerNo);
    }
    @Override
    public void removeRoomOptionByRoomNo(Long roomNo) {
        sellerDao.deleteRoomOptionByRoomNo(roomNo);
    }

    @Override
    public void removeRoomByRoomNo(Long roomNo) {
        sellerDao.deleteRoomByRoomNo(roomNo);
    }

    @Override
    public List<FileResponse> getAdditionalImageByRoomNo(Long roomNo) {
        return sellerDao.selectAdditionalImageByRoomNo(roomNo);
    }

    @Override
    public FileResponse getThumbnailImageByRoomNo(Long roomNo) {
        return sellerDao.selectThumbnailByRoomNo(roomNo);
    }

    @Override
    public List<ReserveManageDto> getReserveManageInfoBySellerNo(Long sellerNo) {
        return sellerDao.selectReserveManageInfoBySellerNo(sellerNo);
    }

    @Override
    public List<ReservationDto> getReservationInfoByRoomNo(Long roomNo) {
        return sellerDao.selectReservationInfoByRoomNo(roomNo);
    }


    @Override
    public List<RequestRoomOptionDto> getOptionInfoAndImage(Long roomNo) {
        return sellerDao.selectOptionInfoAndImageByRoomNo(roomNo);
    }


}
