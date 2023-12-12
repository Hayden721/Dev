package com.dev.shop.reserve.dao;

import com.dev.shop.reserve.dto.RoomDto;
import com.dev.shop.reserve.dto.RoomOptionDto;
import com.dev.shop.reserve.dto.CriteriaDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

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

}
