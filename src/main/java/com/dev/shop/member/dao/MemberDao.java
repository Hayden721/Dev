package com.dev.shop.member.dao;

import com.dev.shop.member.dto.MemberDetailsDto;
import com.dev.shop.member.dto.MemberDto;
import com.dev.shop.member.dto.ReservationCriteriaDto;
import com.dev.shop.member.dto.getReserveInfoDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface MemberDao {

    /**
     *
     * @param memberId - 로그인 멤버의 id
     * @return 멤버의 정보
     */
    MemberDetailsDto selectMemberById(String memberId);
    void insertMemberRegister(MemberDto memberDto);
    MemberDto selectMemberInfoById(String authId);
    void updateMemberInformation(MemberDto memberDto);


    String selectMemberPw(Long memberNo);

    Long selectMemberNo(String authId);

    List<getReserveInfoDto> selectReservationInfoByMemberNo(@Param("memberNo") Long memberNo, @Param("params") ReservationCriteriaDto params);

    int countReservationInfo(@Param("memberNo") Long memberNo, @Param("params") ReservationCriteriaDto params);

    /**
     * 멤버가 선택한 예약 삭제
     * @param reservationNo - 예약 번호
     */
    void updateReservationByReservationNo(Long reservationNo);
}
