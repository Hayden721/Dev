package com.dev.shop.seller.service;

import com.dev.shop.item.dto.FileResponse;
import com.dev.shop.seller.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SellerService {
    /**
     * sellerNo 조회
     * @param authId - 로그인한 아이디
     * @return sellerNo
     */
    Long getSellerNoByAuthId(String authId);

    /**
     * 셀러 회원가입
     * @param sellerRegister - 회원가입 필수 입력사항
     * @param sellerPwConfirm - 비밀번호 확인 데이터
     */
    void sellerRegister(SellerRegisterRequest sellerRegister, String sellerPwConfirm);

    /**
     * 아이디 중복 확인
     * @param sellerId - 확인 하려는 아이디
     * @return 0: 중복안됌, 1: 중복됌
     */
    int checkIdDuplicateBySellerId(String sellerId);

    /**
     * 방 만들기
     *
     * @param roomInfo - 방 필수 입력 정보
     * @param authId - 로그인한 아이디
     * @return 방 번호
     */
    Long createRoom(RoomRequest roomInfo, String authId);

    /**
     * 방 옵션 생성
     * @param options - 방 옵션 입력 데이터
     * @param generatedRoomNo - 방 번호
     */
    void createRoomOption(List<RoomOptionRequest> options, Long generatedRoomNo);

    int roomCountByAuthId(String authId);

    List<ReserveManageDto> getReserveManageInfoBySellerNo(Long sellerNo);

    List<ReservationDto> getReservationInfoByRoomNo(Long roomNo);

    List<OptionAndImageResponse> getOptionInfoAndImage(Long roomNo);

    List<RoomListDto> getRoomListByAuthId(String authId);

    void removeErrorRoom(Long roomNo);

    List<RoomOptionResponse> getRoomOptionInfo(Long roomNo);

    List<FileResponse> getRoomImages(Long roomNo);

    void errorCreateRoom(Long errorRoomNo);

    FileResponse getThumbnailImage(Long roomNo);

    List<FileResponse> getExtraImage(Long roomNo);

    RoomResponse getRoomInfo(Long roomNo);

    void updateOption(List<RoomOptionRequest> optionData);

    int getOptionCount(Long roomNo);

    void addOption(List<RoomOptionRequest> optionData, Long roomNo, List<MultipartFile> optionImages);

    void deleteRoomOption(Long optionNo, Long optionImageNo);
}
