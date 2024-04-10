package com.dev.shop.member.service;


import com.dev.shop.member.dto.MemberDto;
import com.dev.shop.member.dto.getReserveInfoDto;

import java.util.List;

public interface MemberService {

    void memberRegister(MemberDto memberDto);


    MemberDto memberInfoByAuthId(String authId);

    void updateMemberInfo(MemberDto memberDto, String memberNewPw, String memberNewPwChk);

    Long getMemberNoByAuthId(String authId);

    List<getReserveInfoDto> getReservationInfoByMemberNo(Long memberNo);

    void cancelReservation(Long reservationNo);


//    List<String> getRoomNameByRoomNo(Long roomNo);
//
//    List<String> getRoomOptionNameByRoptionNo(Long roptionNo);
}
