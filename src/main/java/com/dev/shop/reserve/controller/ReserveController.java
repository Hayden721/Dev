package com.dev.shop.reserve.controller;

import com.dev.shop.reserve.dto.RoomDto;
import com.dev.shop.reserve.service.ReserveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/reserve")
@RequiredArgsConstructor
public class ReserveController {

    private final ReserveService reserveService;

    @GetMapping("/list")
    public void listGet(Model model) {
        List<RoomDto> roomList = reserveService.findAllRoom();

        model.addAttribute("roomList", roomList);

//        log.info("--- reserveController --- 값 : {}", roomList);

    }

    @GetMapping("/detail")
    public void detailGet(@RequestParam final Long roomNo, Model model) {
        // roomDTO select
        // roomOption select 내일 할 것
    }

}
