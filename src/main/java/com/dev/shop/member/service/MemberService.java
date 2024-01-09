package com.dev.shop.member.service;


import com.dev.shop.member.dto.MemberDto;

public interface MemberService {

    void memberRegister(MemberDto memberDto);


    MemberDto memberInfoByAuthId(String authId);

    void updateMemberInfo(MemberDto memberDto, String memberNewPw, String memberNewPwChk);
}
