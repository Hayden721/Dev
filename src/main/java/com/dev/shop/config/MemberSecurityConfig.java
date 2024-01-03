package com.dev.shop.config;

import com.dev.shop.config.handler.CustomAuthenticationFailHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Slf4j
@EnableWebSecurity
@Order(1)
public class MemberSecurityConfig {

    private final MemberAuthenticationProvider memberAuthenticationProvider;
    private final CustomAuthenticationFailHandler customAuthenticationFailHandler;

    public MemberSecurityConfig(MemberAuthenticationProvider memberAuthenticationProvider, CustomAuthenticationFailHandler customAuthenticationFailHandler) {
        this.memberAuthenticationProvider = memberAuthenticationProvider;
        this.customAuthenticationFailHandler = customAuthenticationFailHandler;
    }




    @Bean
    public SecurityFilterChain MemberFilterChain(HttpSecurity http) throws Exception {

        http. authorizeRequests().antMatchers( "/devroom/member/main","/devroom/member/login", "/devroom/member/register",
                "/css/**","/js/**", "/devroom/reserve/**", "seller/**").permitAll();

        http
                .authenticationProvider(memberAuthenticationProvider)
                .csrf().disable(); //일반 사용자에 대해 Session을 저장하지 않으므로 csrf을 disable 처리함.

        http.antMatcher("/devroom/**")
                .authorizeRequests().anyRequest().hasAuthority("USER")
                .and()
                .formLogin()
                .loginPage("/devroom/member/login")
                .loginProcessingUrl("/devroom/member/login")
                .usernameParameter("memberId")
                .passwordParameter("memberPw")
                .failureHandler(customAuthenticationFailHandler)
                .defaultSuccessUrl("/devroom/member/main")
                .permitAll()
                .and()
                .logout() // 로그아웃 관련 처리
                .logoutUrl("/devroom/member/logout") // 로그아웃 URL 설정\
                .logoutSuccessUrl("/devroom/member/main")
                .invalidateHttpSession(true) // 로그아웃 후 세션 초기화 설정
                .deleteCookies("JSESSIONID")
                .and()
                .sessionManagement()
                .maximumSessions(1) // -1 무제한
                .maxSessionsPreventsLogin(false); // true:로그인 제한 false(default):기존 세션 만료

        return http.build();
    }

}