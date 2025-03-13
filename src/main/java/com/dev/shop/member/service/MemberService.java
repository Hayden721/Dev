package com.dev.shop.member.service;


import com.dev.shop.member.dto.*;
import com.dev.shop.utils.PagingResponse;

import java.util.List;

public interface MemberService {
    /**
     * 멤버 회원가입
     *
     * @param memberRequest - 회원가입 정보
     * @param memberPwConfirm - 비밀번호 확인 값
     */
    void memberRegister(MemberRequest memberRequest, String memberPwConfirm);

    /**
     * 회원 정보 수정
     * @param memberResponse - 멤버 정보 dto
     * @param memberNewPw - 설정하려는 새로운 비밀번호
     * @param memberNewPwChk - 설정하려는 새로운 비밀번호 확인
     */
    void updateMemberInfo(MemberResponse memberResponse, String memberNewPw, String memberNewPwChk);

    Long getMemberNo(String authId);

    PagingResponse<ReservationResponse> getReservationInfo(String memberId, ReservationCriteriaDto params);

    void cancelReservation(Long reservationNo);


    List<RoomAndImageResponse> getMainInfoNewSpot();

    /**
     * 멤버가 결제한 내역 조회
     *
     * @param memberId - 로그인한 멤버 ID
     * @param params
     * @return 결제 내역
     */
    PagingResponse<PaymentHistoryDto> getMemberPaymentHistoryByMemberId(String memberId, ReservationCriteriaDto params);

    List<BookmarkResponse> getBookmarkList(String memberId);


    int checkIdDuplicateByMemberId(String memberId);
    /**
     *
     * @param memberId - 로그인 한 멤버의 아이디
     * @return 로그인 한 멤버의 정보
     */

    MemberResponse getMemberInfo(String memberId);

    /**
     * 마이페이지 비밀번호를 바꿀 때
     * @param memberId
     * @param password
     * @param confirmPassword
     * @param newPassword
     * @return
     */
    boolean changeMemberPassword(String memberId, String password, String confirmPassword, String newPassword);

    /**
     * 비밀번호를 찾을 때
      * @param memberNo
     * @param password
     * @param confirmPassword
     */
    Boolean changeMemberPassword(Long memberNo, String password, String confirmPassword);
    boolean validPassword(String password, String memberId);

    boolean checkAccountExist(String memberId, String memberName);


    PagingResponse<ReservationResponse> getReservationEndInfo(String authId, ReservationCriteriaDto params);
}
