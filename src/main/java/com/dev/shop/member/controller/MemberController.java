package com.dev.shop.member.controller;

import com.dev.shop.member.dto.MemberDetailsDto;
import com.dev.shop.member.dto.MemberDto;
import com.dev.shop.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Slf4j

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/login")
    public void memberLoginGet() {
        log.info("--- [GET] member/login ---");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("--- [/login] 토큰값 : {}", authentication);
    }

    @GetMapping("/logout")
    public String memberLogoutGet(HttpServletRequest request, HttpServletResponse response) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        return "redirect:/";
    }



    @GetMapping("/register")
    public String memberRegisterGet() {
        log.info("--- [GET] member/register");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof AnonymousAuthenticationToken) {
            return "member/register";
        }
        return "redirect:/";
    }

    @PostMapping("/register")
    public void memberRegisterPost(MemberDetailsDto memberDetailsDto) {
        log.info("memberDetailDto : {}", memberDetailsDto);

                // 값이 들어오는지 확인해야함

        memberService.memberRegister(memberDetailsDto);

    }

    @GetMapping("/mypage")
    public void mypageGet(){

    }

    @GetMapping("/mypage/update")
    public String updateGet( MemberDto memberDto, Model model, HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(authentication instanceof AnonymousAuthenticationToken) { // 비로그인 상태
            return "/member/unlogined";
        } else {
            MemberDto memberInfo = memberService.memberInfoByAuthId(authId);
            log.info("--- [mypage/update] --- {}", memberInfo);
            model.addAttribute("memberInfo", memberInfo);
            return "/member/update";

        }


    }


}
