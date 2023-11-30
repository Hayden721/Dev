package com.dev.shop.member.service;

import com.dev.shop.member.dto.MemberDetailsDto;
import com.dev.shop.member.dto.MemberDto;


public interface MemberService {


    public void memberRegister(MemberDetailsDto memberDetailsDto);


    MemberDto memberInfoByAuthId(String authId);
}
