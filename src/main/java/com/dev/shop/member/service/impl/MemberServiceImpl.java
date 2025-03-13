package com.dev.shop.member.service.impl;

import com.dev.shop.member.dao.MemberDao;
import com.dev.shop.member.dto.*;
import com.dev.shop.member.service.MemberService;
import com.dev.shop.utils.Pagination;
import com.dev.shop.utils.PagingResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {


    private final MemberDao memberDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    SimpleDateFormat format = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:sss");
    Date time = new Date();
    String localTime = format.format(time);

    @Override
    public List<RoomAndImageResponse> getMainInfoNewSpot() {
        return memberDao.selectRoomAndImage();
    }

    @Override
    public int checkIdDuplicateByMemberId(String memberId) {
        return memberDao.selectIdDuplicateByMemberId(memberId);
    }

    @Override
    public MemberResponse getMemberInfo(String memberId) {
        return memberDao.selectMemberInfo(memberId);
    }

    @Override
    public boolean changeMemberPassword(String memberId, String password, String confirmPassword, String newPassword) {
        Long memberNo = memberDao.selectMemberNo(memberId);

        if(!newPassword.equals(confirmPassword)) {
            return false;
        }
        String encodePassword = bCryptPasswordEncoder.encode(confirmPassword);

        int result = memberDao.updateMemberPassword(memberNo, encodePassword);


        return result > 0;
    }

    @Override
    public Boolean changeMemberPassword(Long memberNo, String password, String confirmPassword) {

        if(!password.equals(confirmPassword)) {
            return false;
        }
        String encodePassword = bCryptPasswordEncoder.encode(confirmPassword);

        int result = memberDao.updateMemberPassword(memberNo, encodePassword);
        return result > 0;
    }

    @Override
    public boolean validPassword(String password, String memberId) {
        Long memberNo = memberDao.selectMemberNo(memberId);
        String DBPassword = memberDao.selectMemberPw(memberNo);

        return bCryptPasswordEncoder.matches(password, DBPassword);
    }

    @Override
    public boolean checkAccountExist(String memberId, String memberName) {
        int isMatch = memberDao.selectMemberAccount(memberId, memberName);

        return isMatch > 0;
    }


    @Override
    public PagingResponse<PaymentHistoryDto> getMemberPaymentHistoryByMemberId(String memberId, ReservationCriteriaDto params) {
        Long memberNo = memberDao.selectMemberNo(memberId);
        int paymentCount = memberDao.countPaymentHistory(memberNo, params);

        if(paymentCount < 1) {
            return new PagingResponse<>(Collections.emptyList(), null);
        }

        Pagination pagination = new Pagination(paymentCount, params);

        params.setPagination(pagination);

        List<PaymentHistoryDto> list = memberDao.selectMemberPaymentHistoryByMemberNo(memberNo, params);
        log.info("Payment Paging List : {}", list);
        return new PagingResponse<>(list, pagination);
    }



    @Override
    public List<BookmarkResponse> getBookmarkList(String memberId) {
        Long memberNo = memberDao.selectMemberNo(memberId);

        return memberDao.selectBookmarkList(memberNo);
    }

    @Override
    public void memberRegister(MemberRequest memberRequest, String memberPwConfirm) {

        if(memberRequest.getMemberPw().equals(memberPwConfirm)) {
            MemberRequest member = MemberRequest.builder()
                    .memberId(memberRequest.getMemberId())
                    .memberPw(bCryptPasswordEncoder.encode(memberRequest.getMemberPw()))
                    .memberEmail(memberRequest.getMemberEmail())
                    .memberPhone(memberRequest.getMemberPhone())
                    .memberName(memberRequest.getMemberName())
                    .memberCreationDate(localTime)
                    .memberAuth("MEMBER")
                    .build();
            memberDao.insertMemberRegister(member);
        }
    }

    @Override
    public void updateMemberInfo(MemberResponse memberResponse, String memberNewPw, String memberNewPwChk) {
        String oldPw = memberDao.selectMemberPw(memberResponse.getMemberNo());

        memberResponse.setMemberPhone(memberResponse.getMemberPhone());
        memberResponse.setMemberUpdatedate(localTime);
        memberResponse.setMemberEmail(memberResponse.getMemberEmail());

        if (bCryptPasswordEncoder.matches(memberResponse.getMemberPw(), oldPw) && memberNewPw.equals(memberNewPwChk) && memberResponse.getMemberPw() != null) {
            memberResponse.setMemberPw(bCryptPasswordEncoder.encode(memberNewPw));
            memberDao.updateMemberInformation(memberResponse);

        } else {
            memberDao.updateMemberInformation(memberResponse);
        }
    }

    @Override
    public Long getMemberNo(String authId) {
        return memberDao.selectMemberNo(authId);
    }

    @Override
    public PagingResponse<ReservationResponse> getReservationInfo(String authId, final ReservationCriteriaDto params) {
        Long memberNo = memberDao.selectMemberNo(authId);
        int count = memberDao.countReservationInfo(memberNo, params);
        if(count < 1) {
            return new PagingResponse<>(Collections.emptyList(), null);
        }

        Pagination pagination = new Pagination(count, params);
        params.setPagination(pagination);

        List<ReservationResponse> list = memberDao.selectReservationInfo(memberNo, params);

        return new PagingResponse<>(list, pagination);
    }

    @Override
    public PagingResponse<ReservationResponse> getReservationEndInfo(String authId, ReservationCriteriaDto params) {
        Long memberNo = memberDao.selectMemberNo(authId);
        int endReserveCount = memberDao.countReservationEndInfo(memberNo, params);
        if(endReserveCount < 1) {
            return new PagingResponse<>(Collections.emptyList(), null);
        }
        Pagination pagination = new Pagination(endReserveCount, params);
        params.setPagination(pagination);

        log.info("service params: {}", params);
        List<ReservationResponse> list = memberDao.selectReservationEndInfo(memberNo, params);

        return new PagingResponse<>(list, pagination);
    }

    /**
     * 예약 삭제
     * @param reservationNo - 삭제할 예약 번호
     */
    @Override
    public void cancelReservation(Long reservationNo) {
        memberDao.updateReservation(reservationNo);
    }
}
