package com.dev.shop.member.service.impl;

import com.dev.shop.member.dao.MemberDao;
import com.dev.shop.member.dto.MemberDetailsDto;
import com.dev.shop.member.dto.MemberDto;
import com.dev.shop.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    public void memberRegister(MemberDetailsDto memberDetailsDto) {

        memberDetailsDto.setMemberPw(bCryptPasswordEncoder.encode(memberDetailsDto.getMemberPw()));
        memberDetailsDto.setMemberAuth("USER");
        memberDetailsDto.setAppendDate(localTime);
        memberDetailsDto.setUpdateDate(localTime);

        memberDao.insertMemberRegister(memberDetailsDto);
    }

    @Override
    public MemberDto memberInfoByAuthId(String authId) {
        return memberDao.selectMemberInfoById(authId);
    }

}
