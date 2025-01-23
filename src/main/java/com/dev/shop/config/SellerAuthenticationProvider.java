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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

        String sellerID = (String) authentication.getPrincipal();
        String sellerPw = (String) authentication.getCredentials();
        UsernamePasswordAuthenticationToken token;

        log.info("sellerId : {}, sellerPw : {}", sellerID, sellerPw);

        try {
            SellerDetailsDto user = (SellerDetailsDto) userDetailsService.loadUserByUsername(sellerID);

            log.info(" Seller user = {}", user);

            if (user == null) {
                throw new BadCredentialsException("No such username or wrong password");
            }

            if (passwordEncoder.matches(sellerPw, user.getPassword())) {

                token = new UsernamePasswordAuthenticationToken(sellerID, sellerPw, user.getAuthorities());

                return token;
            }

        } catch (UsernameNotFoundException e) {
            log.info("인증 실패", e.getMessage());
            throw e;
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
