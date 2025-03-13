package com.dev.shop.seller.service.impl;

import com.dev.shop.item.dto.FileResponse;
import com.dev.shop.item.dto.OptionFileReqeuest;
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
import java.util.*;
import java.util.stream.Collectors;

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
    public void sellerRegister(SellerRegisterRequest sellerRegister, String sellerPwConfirm) {

        if(sellerRegister.getSellerPw().equals(sellerPwConfirm)) {
            SellerRegisterRequest registerInfo = new SellerRegisterRequest();
            registerInfo.setSellerId(sellerRegister.getSellerId());
            registerInfo.setSellerName(sellerRegister.getSellerName());
            registerInfo.setSellerPw(bCryptPasswordEncoder.encode(sellerRegister.getSellerPw()));
            registerInfo.setSellerEmail(sellerRegister.getSellerEmail());
            registerInfo.setSellerPhone(sellerRegister.getSellerPhone());
            registerInfo.setSellerCreationdate(localTime);
            registerInfo.setSellerAuth("SELLER");

            sellerDao.insertSellerRegister(registerInfo);
        }
    }

    @Override
    public Long createRoom(RoomRequest roomInfo, String authId) {
        Long sellerNo = sellerDao.selectSellerNoByAuthId(authId);

        //로그인이 안된 상태에서 방을 생성하려고 할 때
        if (authId == null) {
            throw new RuntimeException("Invalid seller Id");
        }

        RoomRequest room = RoomRequest.builder()
                .roomTitle(roomInfo.getRoomTitle())
                .roomContent(roomInfo.getRoomContent())
                .roomDiv(roomInfo.getRoomDiv())
                .postcode(roomInfo.getPostcode())
                .address(roomInfo.getAddress())
                .detailAddress(roomInfo.getDetailAddress())
                .extraAddress(roomInfo.getExtraAddress())
                .sellerNo(sellerNo)
                .build();

        sellerDao.insertRoomInfo(room);

        return room.getRoomNo();
    }

    @Override
    public Long getSellerNoByAuthId(String authId) {
        return sellerDao.selectSellerNoByAuthId(authId);
    }

    @Override
    public int roomCountByAuthId(String authId) {

        Long sellerNo = sellerDao.selectSellerNoByAuthId(authId);

        return sellerDao.selectRoomCountByAuthId(sellerNo);
    }

    @Override
    public void createRoomOption(List<RoomOptionRequest> options, Long generatedRoomNo) {
        List<RoomOptionRequest> roomOption = new ArrayList<>();

        for(RoomOptionRequest option : options) {
            RoomOptionRequest roomOptionResp = new RoomOptionRequest();
            roomOptionResp.setRoomNo(generatedRoomNo);
            roomOptionResp.setRoptionTitle(option.getRoptionTitle());
            roomOptionResp.setRoptionContent(option.getRoptionContent());
            roomOptionResp.setRoptionPrice(option.getRoptionPrice());

            roomOption.add(roomOptionResp);
        }

        sellerDao.insertRoomOptionInfo(roomOption);
    }

    @Override
    public List<RoomListDto> getRoomListByAuthId(String authId) {
        Long sellerNo = getSellerNoByAuthId(authId);

        return sellerDao.selectRoomListBySellerNo(sellerNo);
    }

    @Override
    public void removeErrorRoom(Long roomNo) {
        sellerDao.deleteErrorRoom(roomNo);
    }

    @Override
    public List<RoomOptionResponse> getRoomOptionInfo(Long roomNo) {

        return sellerDao.selectRoomOption(roomNo);
    }

    @Override
    public void errorCreateRoom(Long errorRoomNo) {
        sellerDao.updateRoomProgress(errorRoomNo);
    }

    @Override
    public FileResponse getThumbnailImage(Long roomNo) {
        return sellerDao.selectThumbnailImage(roomNo);
    }

    @Override
    public List<FileResponse> getExtraImage(Long roomNo) {
        return sellerDao.selectExtraImage(roomNo);
    }

    @Override
    public RoomResponse getRoomInfo(Long roomNo) {
        return sellerDao.selectRoomInfoByRoomNo(roomNo);
    }


    // 옵션 업데이트
    @Override
    public void updateOption(List<RoomOptionRequest> optionData) {
        List<RoomOptionRequest> roomOption = new ArrayList<>();
        for(RoomOptionRequest optionInfo : optionData) {
            RoomOptionRequest roomOptionResp = RoomOptionRequest.builder()
                    .roptionNo(optionInfo.getRoptionNo())
                    .roptionTitle(optionInfo.getRoptionTitle())
                    .roptionContent(optionInfo.getRoptionContent())
                    .roptionPrice(optionInfo.getRoptionPrice())
                    .build();
            roomOption.add(roomOptionResp);
        }
        sellerDao.updateRoomOptionInfo(roomOption);
    }



    @Override
    public int getOptionCount(Long roomNo) {
        return sellerDao.selectOptionCount(roomNo);
    }

    @Override
    public void addOption(List<RoomOptionRequest> optionData, Long roomNo, List<MultipartFile> optionImages) {
        List<RoomOptionRequest> roomOption = new ArrayList<>();
        List<OptionFileReqeuest> optionImageList = new ArrayList<>();
        List<OptionFileReqeuest> refinedOptionImg = fileUtils.optionImageUploads(optionImages);
        //옵션 정보 add
        for(RoomOptionRequest option : optionData) {
            RoomOptionRequest roomOptionResp = RoomOptionRequest.builder()
                    .roomNo(roomNo)
                    .roptionTitle(option.getRoptionTitle())
                    .roptionContent(option.getRoptionContent())
                    .roptionPrice(option.getRoptionPrice())
                    .build();

            roomOption.add(roomOptionResp);
        }

        sellerDao.insertNewOptions(roomOption);

        // useGenerated를 사용해서 roptionNo 가지고 오기
        List<Long> generatedOptionNos = roomOption.stream()
                .map(RoomOptionRequest::getRoptionNo)
                .collect(Collectors.toList());

        if(!generatedOptionNos.isEmpty()) {
            for (int i = 0; i < generatedOptionNos.size(); i++) {
                OptionFileReqeuest refinedImg = refinedOptionImg.get(i);

                OptionFileReqeuest optionResp = OptionFileReqeuest.builder()
                        .originalName(refinedImg.getOriginalName())
                        .saveName(refinedImg.getSaveName())
                        .fileSize(refinedImg.getFileSize())
                        .uploadDate(refinedImg.getUploadDate())
                        .roptionNo(generatedOptionNos.get(i))
                        .build();

                optionImageList.add(optionResp);
            }
            sellerDao.addOptionImage(optionImageList);
        }
    }

    @Override
    public void deleteRoomOption(Long optionNo, Long optionImageNo) {
        // optionImageNo가 들어오지 않았을 경우
        if(optionImageNo == null || optionImageNo == 0) {
            sellerDao.deleteOption(optionNo);
        } else {
            // 이미지 삭제
            sellerDao.deleteOptionImage(optionImageNo);
            // 이미지 삭제 후 옵션 삭제
            sellerDao.deleteOption(optionNo);
        }
    }

    @Override
    public void updateRoom(RoomRequest roomInfo) {
        sellerDao.updateRoomInfo(roomInfo);
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
    public List<OptionAndImageResponse> getOptionInfoAndImage(Long roomNo) {
        return sellerDao.selectOptionAndImageByRoomNo(roomNo);
    }

    @Override
    public int checkIdDuplicateBySellerId(String sellerId) {

        return sellerDao.selectIdDuplicateBySellerId(sellerId);
    }



}
