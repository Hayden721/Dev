package com.dev.shop.member.controller;

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
import java.security.Principal;


@Slf4j

@Controller
@RequiredArgsConstructor
@RequestMapping("/devroom/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/main")
    public String mainGet(Model model, Principal principal) {
        log.info("--- mainGet ---");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("--- [/login] 토큰값 : {}", authentication);

        if(authentication instanceof AnonymousAuthenticationToken) {
            log.info("--- [/member/login] 토큰값 : {}", authentication);
        } else {
            String memberId = principal.getName();
            log.info("멤버 아이디 값  : {}", memberId);
            log.info("--- [/login2] 토큰값 : {}", authentication);


            model.addAttribute("auth", authentication);
        }
        return "/devroom/member/main";

    }

    @GetMapping("/login")
    public String memberLoginGet() {
        log.info("--- [GET] member/login ---");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("--- [/login] 토큰값 : {}", authentication);

        return "/devroom/member/login";
    }

    @GetMapping("/logout")
    public String memberLogoutGet(HttpServletRequest request, HttpServletResponse response) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        return "redirect:/devroom/member/main";
    }



    @GetMapping("/register")
    public String memberRegisterGet() {
        log.info("--- [GET] member/register");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof AnonymousAuthenticationToken) {
            return "/devroom/member/register";
        }
        return "redirect:/devroom/member/main";
    }

    @PostMapping("/register")
    public void memberRegisterPost(MemberDto memberDto) {
        log.info("memberDetailDto : {}", memberDto);

                // 값이 들어오는지 확인해야함

        memberService.memberRegister(memberDto);

    }

    @GetMapping("/mypage")
    public void mypageGet(){

    }

    @GetMapping("/mypage/update")
    public String updateGet(Model model, HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String authId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(authentication instanceof AnonymousAuthenticationToken) { // 비로그인 상태
            return "/devroom/member/unlogined";
        } else {
            MemberDto memberInfo = memberService.memberInfoByAuthId(authId);
            log.info("--- [mypage/update] --- {}", memberInfo);
            model.addAttribute("memberInfo", memberInfo);
            return "/devroom/member/update";

        }


    }


}
