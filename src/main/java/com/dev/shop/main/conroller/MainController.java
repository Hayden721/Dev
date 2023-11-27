package com.dev.shop.main.conroller;

import com.dev.shop.main.service.MainService;
import com.dev.shop.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MainController {
    private final MainService mainService;
    @GetMapping("/")
    public String mainGet(Model model) {
        log.info("--- mainGet ---");

        String memberId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        log.info("멤버 아이디 값  : {}", memberId);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("--- [/login] 토큰값 : {}", authentication);


        model.addAttribute("auth", authentication);
        return "/main";

    }



}
