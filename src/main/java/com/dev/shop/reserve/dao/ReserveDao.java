package com.dev.shop.reserve.dao;

import com.dev.shop.item.dto.FileResponse;
import com.dev.shop.reserve.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Mapper
public interface ReserveDao {

    List<ReserveRoomListDto> selectRoomList(CriteriaDto criteriaDto);

    /**
     * 개시물의 총 개수를 조회한다.
     * @param criteriaDto - list 페이지에서 보낸 criteria 데이터
     * @return 게시물의 개수
     */
    int countAllList(CriteriaDto criteriaDto);

    /**
     * 방 번호를 통해 room_tb에서 방 정보를 조회한다.
     * @param roomNo - 방 번호(Primary key)
     * @return 방 번호에 맞는 방 정보
     */
    RoomInfoRequest selectRoomInfoByRoomNo(Long roomNo);

    /**
     * 방 옵션 조회
     * @param roomNo - 방 번호(Primary key)
     * @return 해당 방에 대한 옵션 값
     */
    List<OptionResponse> selectRoomOptionInfo(Long roomNo);

    /**
     * 유저가 선택한 날짜에 예약 가능한 예약 시작 시간을 조회한다.
     * @param selectDate - 멤버가 선택한 날짜
     * @param roomNo - 방 번호(Primary key)
     * @return 선택한 날짜에 예약 가능한 시작 시간
     */
    ArrayList<Integer> getReservedStartTime(@Param("selectDate") String selectDate, @Param("roomNo") Long roomNo);

    /**
     * 유저가 선택한 날짜에 예약 가능한 예약 종료 시간을 조회한다.
     * @param selectDate - 멤버가 선택한 날짜
     * @param roomNo - 방 번호(Primary key)
     * @return 선택한 날짜에 예약 가능한 종료 시간
     */
    ArrayList<Integer> getReservedEndTime(@Param("selectDate") String selectDate, @Param("roomNo") Long roomNo);

    /**
     *
     * @param selectDate
     * @param roomNo
     * @param optionNo
     * @return
     */
    List<Map<String, Integer>> getReservedAllTime(@Param("selectDate") String selectDate, @Param("roomNo") Long roomNo, @Param("optionNo") Long optionNo);

    void insertReserveInfo(@Param("parsingDate") LocalDate parsingDate, @Param("reserveStartTime") Integer reserveStartTime, @Param("reserveEndTime") Integer reserveEndTime, @Param("sellerNo") Long sellerNo,
                           @Param("memberNo") Long memberNo, @Param("roomNo") Long roomNo, @Param("optionNo") Long optionNo, @Param("endDateTime") LocalDateTime endDateTime);


    /**
     * 예약 시간 지난 예약 데이터 상태를 '종료' 로 만들기
     */
    void updateReservationStatus();

    /**
     * 추가로 등록된 이미지를 조회한다.
     * @param roomNo - 방 번호(Primary key)
     * @return 썸네일 아닌 이미지
     */
    List<FileResponse> selectExtraImage(Long roomNo);

    /**
     * 북마크가 되어 있으면 true, 되어 있지 않으면 false 반환
     * @param memberNo - 회원 멤버 번호
     * @param roomNo - 북마크 하려는 방 번호
     * @return
     */
    Boolean selectBookmarkData(@Param("memberNo") Long memberNo, @Param("roomNo") Long roomNo);

    /**
     * 회원아이디를 통해 회원 번호를 조회한다.
     * @param memberId - 현재 로그인을 한 회원
     * @return 회원 번호(meber_no)
     */
    Long selectMemberNo(String memberId);

    /**
     * 북마크에 추가한다.
     * @param memberNo - 회원 번호
     * @param roomNo - 북마크를 추가하려는 방 번호
     */
    void insertBookmark(@Param("memberNo") Long memberNo, @Param("roomNo") Long roomNo);

    /**
     * 북마크에서 삭제한다.
     * @param memberNo - 회원 번호
     * @param roomNo - 북마크를 삭제하려는 방 번호
     */
    void deleteBookmark(@Param("memberNo") Long memberNo, @Param("roomNo") Long roomNo);

    /**
     * 해당 방 번호의 옵션과 이미지를 조회한다.
     * @param roomNo - 조회하려는 방 번호
     * @return 옵션, 이미지
     */
    List<OptionAddImageDto> selectOptionAndImage(Long roomNo);

    /**
     * 해당 방 번호의 썸네일 이미지를 조회한다.
     * @param roomNo - 조회하려는 방 번호
     * @return 썸네일 이미지
     */
    FileResponse selectThumbnailImage(Long roomNo);


}
