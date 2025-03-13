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
    MemberDetailsDto selectMemberToken(String memberId);

    void insertMemberRegister(MemberRequest member);

    MemberResponse selectMemberInfo(String memberId);

    void updateMemberInformation(MemberResponse memberResponse);

    String selectMemberPw(Long memberNo);

    Long selectMemberNo(String authId);

    List<ReservationResponse> selectReservationInfo(@Param("memberNo") Long memberNo, @Param("params") ReservationCriteriaDto params);

    int countReservationInfo(@Param("memberNo") Long memberNo, @Param("params") ReservationCriteriaDto params);

    int countReservationEndInfo(@Param("memberNo") Long memberNo, @Param("params") ReservationCriteriaDto params);

    /**
     * 멤버가 선택한 예약 삭제
     * @param reservationNo - 예약 번호
     */
    void updateReservation(Long reservationNo);

    List<RoomAndImageResponse> selectRoomAndImage();




    int countPaymentHistory(@Param("memberNo") Long memberNo, @Param("params") ReservationCriteriaDto params);

    List<PaymentHistoryDto> selectMemberPaymentHistoryByMemberNo(@Param("memberNo") Long memberNo, @Param("params") ReservationCriteriaDto params);

    List<BookmarkResponse> selectBookmarkList(Long memberNo);

    int selectIdDuplicateByMemberId(String memberId);


    int updateMemberPassword(@Param("memberNo") Long memberNo, @Param("encodePassword") String encodePassword);

    int selectMemberAccount(@Param("memberId") String memberId, @Param("memberName") String memberName);

    List<ReservationResponse> selectReservationEndInfo(@Param("memberNo") Long memberNo, @Param("params") ReservationCriteriaDto params);
}
