package com.dev.shop.test.dao;

import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface TestDao {
    List<LocalDateTime> selectEndDateTime();
}
