package com.dev.shop.config;

import com.dev.shop.member.dto.MemberDetailsDto;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Slf4j


public class MemberAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public MemberAuthenticationProvider(@Qualifier("memberDetailsServiceImpl") UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //설계나 정책에 따라서 인증 메서드를 다양하게 구현할 수 있다. 아래는 가장 기본적인 예시이다.

        String memberId = (String) authentication.getPrincipal();
        String memberPw = (String) authentication.getCredentials();
        UsernamePasswordAuthenticationToken token;

        log.info("memberId : {}, memberPw : {}", memberId, memberPw);

        MemberDetailsDto user = (MemberDetailsDto) userDetailsService.loadUserByUsername(memberId);

        log.info("user = {}", user);

        if(user != null && passwordEncoder.matches(memberPw, user.getPassword())) {

            token = new UsernamePasswordAuthenticationToken(memberId, memberPw, user.getAuthorities());

            return token;
        }

        throw new BadCredentialsException("No such user or wrong password");


    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }

//        private boolean matchPassword(String loginPwd, String password) {
//            return loginPwd.equals(password);
//        }

}
