package com.dev.shop.member.service.impl;

import com.dev.shop.member.dao.MemberDao;
import com.dev.shop.member.dto.MemberDto;
import com.dev.shop.member.dto.getReserveInfoDto;
import com.dev.shop.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
    public void memberRegister(MemberDto memberDto) {

        memberDto.setMemberPw(bCryptPasswordEncoder.encode(memberDto.getMemberPw()));
        memberDto.setMemberAuth("USER");
        memberDto.setAppendDate(localTime);
        memberDto.setUpdateDate(localTime);

        memberDao.insertMemberRegister(memberDto);
    }

    @Override
    public MemberDto memberInfoByAuthId(String authId) {
        return memberDao.selectMemberInfoById(authId);
    }

    @Override
    public void updateMemberInfo(MemberDto memberDto, String memberNewPw, String memberNewPwChk) {

        String oldPw = memberDao.selectMemberPw(memberDto.getMemberNo());

        memberDto.setMemberPhone(memberDto.getMemberPhone());
        memberDto.setUpdateDate(localTime);
        memberDto.setMemberEmail(memberDto.getMemberEmail());

        if (bCryptPasswordEncoder.matches(memberDto.getMemberPw(), oldPw) && memberNewPw.equals(memberNewPwChk) && memberDto.getMemberPw() != null) {

            memberDto.setMemberPw(bCryptPasswordEncoder.encode(memberNewPw));

            memberDao.updateMemberInformation(memberDto);

        } else {

            memberDao.updateMemberInformation(memberDto);
        }


    }

    @Override
    public Long getMemberNoByAuthId(String authId) {
        return memberDao.selectMemberNo(authId);
    }

    @Override
    public List<getReserveInfoDto> getReservationInfoByMemberNo(Long memberNo) {
        return memberDao.selectReservationInfoByMemberNo(memberNo);
    }

    @Override
    public void cancelReservation(Long reservationNo) {
        memberDao.updateReservationByReservationNo(reservationNo);
    }

}
