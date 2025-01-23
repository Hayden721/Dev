package com.dev.shop.test.controller;

import com.dev.shop.test.service.TestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {

    private final TestService testService;

//    @GetMapping("/schedule")
//    public void scheduleTest(Model model) {
//
//        log.info("테스트");
//        Long memberNo = 1L;
////        List<LocalDateTime> endDateTime =testService.getEndDateTime(memberNo);
////        log.info("/schedule endDateTime : {}", endDateTime);
////        model.addAttribute("endDateTime", endDateTime);
//
//    }

    @GetMapping("/drag")
    public String drag() {
        return "/test/image";
    }

    @GetMapping("/layout")
    public String layout() {
        return "/test/layout-test";

    }


}
