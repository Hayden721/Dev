package com.dev.shop.reserve.dao;

import com.dev.shop.reserve.dto.RoomDto;
import com.dev.shop.reserve.dto.RoomOptionDto;
import com.dev.shop.reserve.dto.CriteriaDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface ReserveDao {


    List<RoomDto> selectRoomList(CriteriaDto criteriaDto);


    int countAllList(CriteriaDto criteriaDto);

    RoomDto selectRoomInfoByRoomNo(Long roomNo);

    /**
     * 방 옵션 조회
     * @param roomNo - 방 번호
     * @return 해당 방에 대한 옵션 값
     */
    List<RoomOptionDto> selectRoomOptionInfoByRoomNo(Long roomNo);
    ArrayList<Integer> getReservedStartTime(@Param("selectDate") String selectDate, @Param("roomNo") Long roomNo);
    ArrayList<Integer> getReservedEndTime(@Param("selectDate") String selectDate, @Param("roomNo") Long roomNo);

    List<Map<String, Integer>> getReservedAllTime(@Param("selectDate") String selectDate, @Param("roomNo") Long roomNo, @Param("optionNo") Long optionNo);

    void insertReserveInfo(@Param("parsingDate") LocalDate parsingDate, @Param("reserveStartTime") Integer reserveStartTime, @Param("reserveEndTime") Integer reserveEndTime, @Param("sellerNo") Long sellerNo,
                           @Param("memberNo") Long memberNo, @Param("roomNo") Long roomNo, @Param("optionNo") Long optionNo);

    String selectMemberNoByAuthId(String authId);


}
