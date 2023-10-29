package com.dev.shop.user.dao;

import com.dev.shop.user.dto.User;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserDao {

    public String selectData();

    public int selectEmailPw(User user);

}
