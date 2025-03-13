package com.dev.shop.config.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
@Slf4j
public class CustomFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String errorMsg;

        if(exception instanceof BadCredentialsException) {
            errorMsg = "아이디 또는 비밀번호가 일치하지 않습니다.";
        } else if(exception instanceof InternalAuthenticationServiceException) {
            errorMsg = "시스템 문제로 인해 요청을 처리할 수 없습니다.";
        } else if (exception instanceof UsernameNotFoundException) {
            errorMsg = "아이디 또는 비밀번호가 일치하지 않습니다.";
//                    "계정이 존재하지 않습니다. 회원가입을 해주세요";
        } else {
            errorMsg = "알 수 없는 이유로 인해 로그인이 실패했습니다.";
        }

        request.getSession().setAttribute("errorMsg", errorMsg);

        String requestURI = request.getRequestURI();
        if(requestURI.startsWith("/seller")) {
            setDefaultFailureUrl("/seller/login?error=true");
        } else {
            setDefaultFailureUrl("/sharespot/member/login?error=ture");
        }


        log.info("customSellerFailure: {}", request.getSession().getAttribute("errorMsg"));

        super.onAuthenticationFailure(request, response, exception);
    }
}
