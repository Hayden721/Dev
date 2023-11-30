package com.dev.shop.member.service.impl;

import com.dev.shop.member.dao.MemberDao;
import com.dev.shop.member.dto.MemberDetailsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@RequiredArgsConstructor
public class CustomDetailsServiceImpl implements UserDetailsService {

    private final MemberDao memberDao;

    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        log.info("--- [CustomDetailsServiceImpl] 여기까지 옴");

        MemberDetailsDto memberDetailsDto = memberDao.selectMemberById(memberId);
        log.info("--- [customDetailServiceImpl] 데이터값 확인 : {}", memberDetailsDto);
        if (memberDetailsDto == null) {
            throw new UsernameNotFoundException("해당 아이디가 존재하지 않습니다.");

        }
        return memberDetailsDto;
    }
}
