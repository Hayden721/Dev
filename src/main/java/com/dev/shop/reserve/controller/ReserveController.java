package com.dev.shop.reserve.controller;

import com.dev.shop.reserve.dto.RoomDto;
import com.dev.shop.reserve.dto.RoomOptionDto;
import com.dev.shop.reserve.dto.CriteriaDto;
import com.dev.shop.reserve.service.ReserveService;
import com.dev.shop.utils.PagingResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/devroom/reserve")
@RequiredArgsConstructor
public class ReserveController {

    private final ReserveService reserveService;

    @GetMapping("/list")
    public String listGet(@ModelAttribute("params") final CriteriaDto params, Model model) {


        PagingResponse<RoomDto> roomList = reserveService.findAllRoom(params);

        model.addAttribute("roomList", roomList);

        log.info("--- reserveController --- 값 : {}", roomList);

        return "/devroom/reserve/list";


    }

    @GetMapping("/detail")
    public void detailGet(@RequestParam final Long roomNo, Model model) {
        // roomDTO select
        RoomDto roomInfo = reserveService.findRoomInfo(roomNo);
        // roomOption select 내일 할 것
        List<RoomOptionDto> roomOptionInfo = reserveService.findRoomOptionInfo(roomNo);

                log.info("--- reserveController --- 값 : {}", roomOptionInfo);

        model.addAttribute("roomInfo", roomInfo);
        model.addAttribute("roomOptionInfo", roomOptionInfo);
    }

}
