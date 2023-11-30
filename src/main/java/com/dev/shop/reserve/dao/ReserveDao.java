package com.dev.shop.reserve.dao;

import com.dev.shop.reserve.dto.RoomDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ReserveDao {

    List<RoomDto> selectAllRoom();
}
