package com.dev.shop.member.service;


import com.dev.shop.member.dto.MemberDto;
import com.dev.shop.member.dto.ReservationCriteriaDto;
import com.dev.shop.member.dto.getReserveInfoDto;
import com.dev.shop.seller.dto.ReservationDto;
import com.dev.shop.utils.PagingResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MemberService {

    void memberRegister(MemberDto memberDto);


    MemberDto memberInfoByAuthId(String authId);

    void updateMemberInfo(MemberDto memberDto, String memberNewPw, String memberNewPwChk);

    Long getMemberNoByAuthId(String authId);

    PagingResponse<getReserveInfoDto> getReservationInfoByMemberNo(Long memberNo, ReservationCriteriaDto params);

    void cancelReservation(Long reservationNo);


//    List<String> getRoomNameByRoomNo(Long roomNo);
//
//    List<String> getRoomOptionNameByRoptionNo(Long roptionNo);
}
