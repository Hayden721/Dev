package com.dev.shop.config;

import com.dev.shop.config.handler.CustomAuthenticationFailHandler;
import com.dev.shop.config.handler.CustomAuthenticationSuccessHandler;
import com.dev.shop.config.handler.CustomLogoutSuccessHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;


@Slf4j
@EnableWebSecurity
@Order(1)
public class MemberSecurityConfig {

    private final MemberAuthenticationProvider memberAuthenticationProvider;
    private final CustomAuthenticationFailHandler customAuthenticationFailHandler;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;

    public MemberSecurityConfig(
            MemberAuthenticationProvider memberAuthenticationProvider,
            CustomAuthenticationFailHandler customAuthenticationFailHandler,
            CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler,
            CustomLogoutSuccessHandler customLogoutSuccessHandler

    ) {
        this.memberAuthenticationProvider = memberAuthenticationProvider;
        this.customAuthenticationFailHandler = customAuthenticationFailHandler;
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
        this.customLogoutSuccessHandler = customLogoutSuccessHandler;
    }




    @Bean
    public SecurityFilterChain MemberFilterChain(HttpSecurity http) throws Exception {

        http.authorizeRequests().antMatchers("/devroom/member/main", "/devroom/member/login", "/devroom/member/register", "/css/**", "/js/**", "/images/**", "/devroom/reserve/**", "/test/**").permitAll();

        http
                .authenticationProvider(memberAuthenticationProvider)
                .csrf().disable(); //일반 사용자에 대해 Session을 저장하지 않으므로 csrf을 disable 처리함.

        http.antMatcher("/devroom/**")
            .authorizeRequests().anyRequest().hasAuthority("USER")
            .and()
            .formLogin()
                .loginPage("/devroom/member/login") // 개발자가 설정한 로그인 페이지
                .loginProcessingUrl("/devroom/member/login")
                .usernameParameter("memberId")
                .passwordParameter("memberPw")
                .successHandler(customAuthenticationSuccessHandler)
                .failureHandler(customAuthenticationFailHandler)
//            .defaultSuccessUrl("/devroom/member/main", true) // 로그인 성공 시 리다이렉트할 페이지
            .permitAll() //
            .and()
            .logout() // 로그아웃 관련 처리
                .logoutUrl("/devroom/member/logout") // 로그아웃 URL 설정

                .logoutSuccessHandler(customLogoutSuccessHandler)

                .invalidateHttpSession(true) // 로그아웃 후 세션 초기화 설정
                .deleteCookies("JSESSIONID");
        http
            .sessionManagement()
            .maximumSessions(1) // -1 무제한
            .maxSessionsPreventsLogin(false); // true:로그인 제한 false(default):기존 세션 만료

        return http.build();
    }

}