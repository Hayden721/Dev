package com.dev.shop.config;

import com.dev.shop.config.handler.CustomAuthenticationFailHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


@EnableWebSecurity
@Slf4j
@Order(2)
public class SellerSecurityConfig {

    private final SellerAuthenticationProvider sellerAuthenticationProvider;
    private final CustomAuthenticationFailHandler customAuthenticationFailHandler;

    @Autowired
    public SellerSecurityConfig( SellerAuthenticationProvider sellerAuthenticationProvider, CustomAuthenticationFailHandler customAuthenticationFailHandler) {

        this.sellerAuthenticationProvider = sellerAuthenticationProvider;
        this.customAuthenticationFailHandler = customAuthenticationFailHandler;
    }



    @Bean
    public SecurityFilterChain SellerFilterChain(HttpSecurity http) throws Exception {


        http. authorizeRequests().antMatchers( "/devroom/member/main","/devroom/member/login", "/devroom/member/register",
                "/css/**","/js/**", "/devroom/reserve/**", "/seller/login", "/test/**").permitAll();
        http
                .authenticationProvider(sellerAuthenticationProvider)
                .csrf().disable(); //일반 사용자에 대해 Session을 저장하지 않으므로 csrf을 disable 처리함.

        http.antMatcher("/seller/**")
                .authorizeRequests().anyRequest().hasAuthority("SELLER")
                .and()
                .formLogin()
                .loginPage("/seller/login")
                .loginProcessingUrl("/seller/login")
                .usernameParameter("sellerId")
                .passwordParameter("sellerPw")
                .defaultSuccessUrl("/seller/main")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/seller/logout") // 로그아웃 URL 설정\
                .logoutSuccessUrl("/seller/login")
                .invalidateHttpSession(true) // 로그아웃 후 세션 초기화 설정
                .deleteCookies("JSESSIONID");
        http
                .sessionManagement()
                .maximumSessions(1) // -1 무제한
                .maxSessionsPreventsLogin(false); // true:로그인 제한 false(default):기존 세션 만료

        return http.build();
    }

}