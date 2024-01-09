package com.dev.shop.seller.controller;

import com.dev.shop.seller.dto.SellerDto;
import com.dev.shop.seller.service.SellerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

@Controller
@RequestMapping("/seller")
@Slf4j
@RequiredArgsConstructor
public class SellerController {

    private final SellerService sellerService;

    @GetMapping("/main")
    public void mainGet() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        log.info("--- [/seller/login] 권한 확인 : {}", authorities);
    }

    @GetMapping("/login")
    public String loginGet(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("--- [/seller/login] 토큰값 : {}", authentication);

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        boolean hasUserAuthority = authorities.stream().anyMatch(grantedAuthority -> "USER".equals(grantedAuthority.getAuthority()));
        log.info("=================== {}", hasUserAuthority);

        if(hasUserAuthority) {
            return "redirect:/seller/logout";
        }

        log.info("------------------ authorities : {}",authorities);

        return "seller/login";
    }

    @GetMapping("/logout")
    public String sellerLogoutGet(HttpServletRequest request, HttpServletResponse response) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        return "redirect:/seller/login";
    }

    @GetMapping("/register")
    public String registerGet() {
        log.info("--- [/seller/register] --- GET");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication instanceof AnonymousAuthenticationToken) {
            return "/seller/register";
        }
        return "redirect:/seller/login";
    }

    @PostMapping("/register")
    public void memberRegisterPost(SellerDto sellerDto) {
        log.info("memberDetailDto : {}", sellerDto);

        sellerService.sellerRegister(sellerDto);

    }



}
