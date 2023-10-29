package com.dev.shop.user.controller;

import com.dev.shop.user.dto.User;
import com.dev.shop.user.service.face.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Slf4j

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public void userLoginGet(Model model) {

    }

    @PostMapping("/login")
    public String userLoginPost(Model model, User user, HttpSession session) {

       boolean infoChk = userService.userlogin(user);

       if(infoChk) {
            log.info("[userCtr] infoChk value : {}", infoChk);
            return "redirect:/";
       }

        log.info("[userCtr] infoChk value : {}", infoChk);
        return "redirect:/user/login";

    }

    @GetMapping("/register")
    public void userRegisterGet() {
    }

}
