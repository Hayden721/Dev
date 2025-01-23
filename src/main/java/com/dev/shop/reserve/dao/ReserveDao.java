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
     *
     * @param roomNo - 방 번호(Primary key)
     * @return 방 번호에 맞는 방 정보
     */
    RoomInfoRequest selectRoomInfoByRoomNo(Long roomNo);

    /**
     * 방 옵션 조회
     * @param roomNo - 방 번호(Primary key)
     * @return 해당 방에 대한 옵션 값
     */
    List<RoomOptionDto> selectRoomOptionInfoByRoomNo(Long roomNo);

    /**
     *
     * @param selectDate - 멤버가 선택한 날짜
     * @param roomNo - 방 번호(Primary key)
     * @return 선택한 날짜에 예약 가능한 시작 시간
     */
    ArrayList<Integer> getReservedStartTime(@Param("selectDate") String selectDate, @Param("roomNo") Long roomNo);

    /**
     *
     * @param selectDate - 멤버가 선택한 날짜
     * @param roomNo - 방 번호(Primary key)
     * @return 선택한 날짜에 예약 가능한 종료 시간
     */
    ArrayList<Integer> getReservedEndTime(@Param("selectDate") String selectDate, @Param("roomNo") Long roomNo);

    List<Map<String, Integer>> getReservedAllTime(@Param("selectDate") String selectDate, @Param("roomNo") Long roomNo, @Param("optionNo") Long optionNo);

    void insertReserveInfo(@Param("parsingDate") LocalDate parsingDate, @Param("reserveStartTime") Integer reserveStartTime, @Param("reserveEndTime") Integer reserveEndTime, @Param("sellerNo") Long sellerNo,
                           @Param("memberNo") Long memberNo, @Param("roomNo") Long roomNo, @Param("optionNo") Long optionNo, @Param("endDateTime") LocalDateTime endDateTime);

    Long selectMemberNoByAuthId(String authId);

    /**
     * 예약 시간 지난 예약 데이터 상태를 '종료' 로 만들기
     */
    void updateReservationStatus();

    /**
     *
     * @param roomNo - 방 번호(Primary key)
     * @return 썸네일 아닌 이미지
     */
    List<FileResponse> selectExtraImage(Long roomNo);

    /**
     *
     * @param roomNo - 방 번호(Primary key)
     * @return 썸네일 이미지 데이터
     */
//    RoomImageDto selectRoomThumbnailByRoomNo(Long roomNo);

    boolean selectBookmarkedRoom(@Param("memberNo") Long memberNo, @Param("roomNo") Long roomNo);


    List<OptionAndImageDto> selectOptionAndImage(Long roomNo);

    FileResponse selectThumbnailImage(Long roomNo);
}
