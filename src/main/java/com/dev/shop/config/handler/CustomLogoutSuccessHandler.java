package com.dev.shop.config.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class CustomLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String refererUrl = request.getHeader("Referer");
        if (refererUrl != null && !refererUrl.isEmpty()) {
            if(refererUrl.endsWith("/sharespot/member/login")) {
                response.sendRedirect("/sharespot/member/main");
            }else {
                response.sendRedirect(refererUrl);
            }
        } else {
            response.sendRedirect("/sharespot/member/main");
            super.onLogoutSuccess(request, response, authentication);
        }

    }
}
