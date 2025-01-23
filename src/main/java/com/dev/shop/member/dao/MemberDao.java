package com.dev.shop.member.dao;

import com.dev.shop.member.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface MemberDao {

    /**
     * 멤버 아이디를 통해
     * @param memberId - 로그인 멤버의 id
     * @return 멤버의 정보
     */
    MemberDetailsDto selectMemberById(String memberId);

    /**
     * 멤버 회원가입
     * @param memberDto - 회원가입 정보
     */
    void insertMemberRegister(MemberRequest member);

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

    List<RoomAndImageDto> selectRoomAndImage();

    void insertBookmark(@Param("memberNo") Long memberNo, @Param("roomNo") Long roomNo);

    void deleteBookmark(@Param("memberNo") Long memberNo, @Param("roomNo") Long roomNo);

    Boolean selectBookmarkData(@Param("memberNo") Long memberNo, @Param("roomNo") Long roomNo);

    int countPaymentHistory(@Param("memberNo") Long memberNo, @Param("params") ReservationCriteriaDto params);

    List<PaymentHistoryDto> selectMemberPaymentHistoryByMemberNo(@Param("memberNo") Long memberNo, @Param("params") ReservationCriteriaDto params);

    List<BookmarkedDto> selectBookmarkedListByMemberNo(Long memberNo);

    int selectIdDuplicateByMemberId(String memberId);


}
