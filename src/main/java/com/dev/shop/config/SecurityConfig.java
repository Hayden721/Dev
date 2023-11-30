package com.dev.shop.config;

import com.dev.shop.config.handler.CustomAuthenticationFailHandler;
import com.dev.shop.member.service.impl.CustomDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@EnableWebSecurity
@Slf4j
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomDetailsServiceImpl service;


    private final CustomAuthenticationFailHandler customAuthenticationFailHandler;

    @Bean
    BCryptPasswordEncoder BCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        http
                .csrf().disable() //일반 사용자에 대해 Session을 저장하지 않으므로 csrf을 disable 처리함.
//                .authenticationProvider(customAuthenticationProvider)
                .authorizeRequests()
                .antMatchers("/", "/member/login", "/member/register", "/css/**","/js/**", "/reserve/*").permitAll();

        http
            .formLogin()
            .loginPage("/member/login")
            .loginProcessingUrl("/member/login")
            .usernameParameter("memberId")
            .passwordParameter("memberPw")
            .failureHandler(customAuthenticationFailHandler)
            .defaultSuccessUrl("/");

        http
            .formLogin()
            .loginPage("/seller/login")
            .loginProcessingUrl("/seller/login")
            .usernameParameter("sellerId")
            .passwordParameter("sellerPw")
            .defaultSuccessUrl("/seller/main");


        http
            .logout() // 로그아웃 관련 처리
            .logoutUrl("/user/logout") // 로그아웃 URL 설정\
            .logoutSuccessUrl("/")
            .invalidateHttpSession(true) // 로그아웃 후 세션 초기화 설정
            .deleteCookies("JSESSIONID");

        http
            .sessionManagement()
            .maximumSessions(1) // -1 무제한
            .maxSessionsPreventsLogin(false); // true:로그인 제한 false(default):기존 세션 만료


        return http.build();
    }




}

