package com.dev.shop.member.service;

import com.dev.shop.member.dto.MemberDto;


public interface MemberService {

    void memberRegister(MemberDto memberDto);


    MemberDto memberInfoByAuthId(String authId);
}
