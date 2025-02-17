package com.dev.shop.member.service.impl;

import com.dev.shop.member.dao.MemberDao;
import com.dev.shop.member.dto.*;
import com.dev.shop.member.service.MemberService;
import com.dev.shop.utils.Pagination;
import com.dev.shop.utils.PagingResponse;
import lombok.Builder;
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

    /**
     * 최근 등록된 방 조회 (6개 제한)
     * @return 최근 등록된 방과 이미지 정보
     */
    @Override
    public List<RoomAndImageDto> getMainInfoNewSpot() {
        return memberDao.selectRoomAndImage();
    }

    /**
     * 해당 방을 북마크 했는지 안했는지 확인한다.
     * @param memberId - 로그인한 유저의 아이디
     * @param roomNo - 북마크하려는 방 넘버
     * @return 북마크를 상태를 boolean 값으로 반환
     */
    @Override
    public boolean roomBookmark(String memberId, Long roomNo) {
        //memberId로 memberNo 조회
        Long memberNo = memberDao.selectMemberNo(memberId);
        log.info("memberNo : {}", memberNo);
        // 1. 북마크하려는 유저와 방 번호를 사용해서 db에 조회
        Boolean bookmark = memberDao.selectBookmarkData(memberNo, roomNo);
        log.info("bookmark : {}", bookmark);
        if(!bookmark) {
            memberDao.insertBookmark(memberNo, roomNo);
            // 북마크 insert 후 true 반환
            return true;
        }else {

            memberDao.deleteBookmark(memberNo, roomNo);
            // 북마크 delete 후 false 반환
            return false;
        }

        //2. 만약 북마크가 존재하지 않으면 insert / 존재한다면 delete
    }

    @Override
    public int checkIdDuplicateByMemberId(String memberId) {

        return memberDao.selectIdDuplicateByMemberId(memberId);

    }

    /**
     * 결제 내역 페이징
     * @param memberId - 로그인한 멤버 ID
     * @param params
     * @return - 페이지네이션 리스트
     */
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

    /**
     * 북마크된 공간 리스트를 가져온다.
     * @param memberId - 로그인한 멤버
     * @return 북마크된 공간 리스트
     */
    @Override
    public List<BookmarkedDto> getBookmarkedRoomListByMemberId(String memberId) {
        Long memberNo = memberDao.selectMemberNo(memberId);


        return memberDao.selectBookmarkedListByMemberNo(memberNo);
    }

    /**
     * 회원가입
     *
     * @param memberDto       - 입력한 회원가입 정보
     * @param memberPwConfirm - 비밀번호 확인
     */
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
    public MemberDto memberInfoByAuthId(String authId) {
        return memberDao.selectMemberInfoById(authId);
    }

    @Override
    public void updateMemberInfo(MemberDto memberDto, String memberNewPw, String memberNewPwChk) {

        String oldPw = memberDao.selectMemberPw(memberDto.getMemberNo());

        memberDto.setMemberPhone(memberDto.getMemberPhone());
        memberDto.setMemberUpdatedate(localTime);
        memberDto.setMemberEmail(memberDto.getMemberEmail());

        if (bCryptPasswordEncoder.matches(memberDto.getMemberPw(), oldPw) && memberNewPw.equals(memberNewPwChk) && memberDto.getMemberPw() != null) {

            memberDto.setMemberPw(bCryptPasswordEncoder.encode(memberNewPw));
            memberDao.updateMemberInformation(memberDto);

        } else {

            memberDao.updateMemberInformation(memberDto);
        }


    }

    /**
     * memberNo 조회
     * @param authId
     * @return memberNo
     */
    @Override
    public Long getMemberNoByAuthId(String authId) {
        return memberDao.selectMemberNo(authId);
    }

    /**
     * 게시글 리스트 조회
     * @param memberNo
     * @return
     */
    @Override
    public PagingResponse<getReserveInfoDto> getReservationInfoByMemberNo(Long memberNo, final ReservationCriteriaDto params) {
        int count = memberDao.countReservationInfo(memberNo, params);
        if(count < 1) {
            return new PagingResponse<>(Collections.emptyList(), null);
        }

        Pagination pagination = new Pagination(count, params);

        params.setPagination(pagination);

        List<getReserveInfoDto> list = memberDao.selectReservationInfoByMemberNo(memberNo, params);

        return new PagingResponse<>(list, pagination);
    }

    /**
     * 예약 삭제
     * @param reservationNo - 삭제할 예약 번호
     */
    @Override
    public void cancelReservation(Long reservationNo) {
        memberDao.updateReservationByReservationNo(reservationNo);
    }




}
