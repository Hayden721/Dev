package com.dev.shop.config.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        String previousPageUrl = (String) request.getSession().getAttribute("previousPageUrl");
        System.out.println(previousPageUrl);
        if(previousPageUrl != null && !previousPageUrl.isEmpty()) {
            getRedirectStrategy().sendRedirect(request, response, previousPageUrl);
        } else {
            setDefaultTargetUrl("/devroom/member/main");
            super.onAuthenticationSuccess(request, response, authentication);
        }





    }
}
