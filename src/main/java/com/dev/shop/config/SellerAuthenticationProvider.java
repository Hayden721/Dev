package com.dev.shop.config;

import com.dev.shop.seller.dto.SellerDetailsDto;
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


public class SellerAuthenticationProvider implements AuthenticationProvider {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public SellerAuthenticationProvider(@Qualifier("sellerDetailsServiceImpl") UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //설계나 정책에 따라서 인증 메서드를 다양하게 구현할 수 있다. 아래는 가장 기본적인 예시이다.

        String sellerID = (String) authentication.getPrincipal();
        String sellerPw = (String) authentication.getCredentials();
        UsernamePasswordAuthenticationToken token;

        log.info("sellerId : {}, sellerPw : {}", sellerID, sellerPw);

        SellerDetailsDto user = (SellerDetailsDto) userDetailsService.loadUserByUsername(sellerID);

        log.info(" Seller user = {}", user);


        if(user != null && passwordEncoder.matches(sellerPw, user.getPassword())) {

            token = new UsernamePasswordAuthenticationToken(sellerID, sellerPw, user.getAuthorities());

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
