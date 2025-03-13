package com.dev.shop.config.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        // 이전페이지
        String previousPageUrl = (String) request.getSession().getAttribute("previousPageUrl");
        //System.out.println(previousPageUrl);

        // 이전 페이지가 널이 아니거나 비어있지 않을 경우
        if (previousPageUrl != null && !previousPageUrl.isEmpty()) {
            // 로그인 후 이전페이지로 이동
            response.sendRedirect(previousPageUrl);
        } else if ("/sharespot/member/register".equals(previousPageUrl)) {
            response.sendRedirect("/sharespot/main");
        } else {
            response.sendRedirect("/sharespot/main");
        }





    }
}
