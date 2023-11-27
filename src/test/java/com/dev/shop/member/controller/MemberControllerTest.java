package com.dev.shop.member.controller;

import com.dev.shop.member.dao.MemberDao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;

@MybatisTest
class MemberControllerTest {

    @Autowired
    private MemberDao memberDao;

    @Test
    @DisplayName("회원가입")
    void memberRegister(){

    }


}