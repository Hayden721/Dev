package com.dev.shop.seller.service.impl;

import com.dev.shop.seller.dto.ImageFileDto;
import com.dev.shop.item.dto.FileResponse;
import com.dev.shop.reserve.dto.RoomDto;
import com.dev.shop.reserve.dto.RoomOptionDto;
import com.dev.shop.seller.dto.*;
import com.dev.shop.seller.dao.SellerDao;
import com.dev.shop.seller.service.SellerService;
import com.dev.shop.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SellerServiceImpl implements SellerService {
    private final SellerDao sellerDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final FileUtils fileUtils;

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
    public void removeRoomByRoomNo(Long roomNo) {

        int countRoomOption = sellerDao.selectRoomOptionCountByRoomNo(roomNo);

        // 옵션이 존재 할 때
        if(countRoomOption > 0) {
            // 1. roomOptionNo값 조회
            List<Long> roomOptionNo = sellerDao.selectRoomOptionNoByRoomNo(roomNo);
            log.debug("--- SellerServiceImpl room/detail roomOptionNo : {}", roomOptionNo);
            // 2. roomOptionNo로 roomOptionImage delete
            sellerDao.deleteRoomOptionImageByRoomOptionNo(roomOptionNo);

            // 3. roomOption 삭제
            sellerDao.deleteRoomOptionByRoomNo(roomNo);

            // 4. roomImage 사제
            sellerDao.deleteRoomImageByRoomNo(roomNo);

            // 5. room 삭제
            sellerDao.deleteRoomByRoomNo(roomNo);
        }else {
            //옵션이 존재 하지 않을 때

            // 1. roomImage 삭제
            sellerDao.deleteRoomImageByRoomNo(roomNo);
            // 2. room 삭제
            sellerDao.deleteRoomByRoomNo(roomNo);
        }
    }

    @Override
    public List<ImageFileDto> getAdditionalImageByRoomNo(Long roomNo) {
        return sellerDao.selectAdditionalImageByRoomNo(roomNo);
    }

    @Override
    public ImageFileDto getThumbnailImageByRoomNo(Long roomNo) {
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



    @Override
    public void updateRoomInfoByData(RoomUpdateRequest data) {

        List<UpdateRoomInfoDto> roomInfo = data.getRoomInfo();
        List<UpdateRoomOptionInfoDto> optionInfo = data.getOptionList();

        log.info("sellerserviceimpl optionInfo : {}",optionInfo);

        sellerDao.updateRoomInfoByRoomInfo(roomInfo);
        sellerDao.updateRoomOptionInfoByOptionInfo(optionInfo);
    }



    // 이미지 관련 기능

    /**
     * 이미지 업로드 (썸네일 이미지)
     * @param roomNo - 방 번호
     * @param thumbnailImage - 썸네일 이미지 데이터
     */
    @Override
    public void sellerUploadThumbnailImageByRoomNo(Long roomNo, MultipartFile thumbnailImage) {
        ImageFileDto refinedThumbnailImage = fileUtils.uploadFile(thumbnailImage);
        log.info("refinedThumbnailImage {}", refinedThumbnailImage);
        refinedThumbnailImage.setThumbnail('Y');
        refinedThumbnailImage.setRoomNo(roomNo);
//        refinedThumbnailImage.setCreatedDate(LocalDate.parse(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
        sellerDao.insertThumbnailImage(refinedThumbnailImage);
    }

    /**
     * 이미지 업로드 (추가 이미지)
     * @param roomNo - 방 번호
     * @param extraImages - 추가 이미지 데이터
     */
    @Override
    public void sellerUploadExtraImagesByRoomNo(Long roomNo, List<MultipartFile> extraImages) {
        log.info("extraImages {}", extraImages);
        List<ImageFileDto> refinedImages = fileUtils.uploadFiles(extraImages);
        log.info("refinedImage {} ", refinedImages);

        for(ImageFileDto imageFile : refinedImages) {
            imageFile.setThumbnail('N');
            imageFile.setRoomNo(roomNo);
        }

        sellerDao.insertExtraImages(refinedImages);
    }
    @Override
    public void sellerUpdateImageByImageNo(Long imageNo, MultipartFile extraImage) {

    }
}
