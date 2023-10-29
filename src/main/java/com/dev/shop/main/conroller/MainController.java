package com.dev.shop.main.conroller;

import com.dev.shop.main.service.face.MainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MainController {
    private final MainService mainService;

    @GetMapping("/")
    public String mainGet() {

        return "/main";
    }

}
