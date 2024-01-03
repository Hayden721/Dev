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
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
        // roomOption select
        List<RoomOptionDto> roomOptionInfo = reserveService.findRoomOptionInfo(roomNo);

                log.info("--- reserveController --- 값 : {}", roomOptionInfo);

        model.addAttribute("roomInfo", roomInfo);
        model.addAttribute("roomOptionInfo", roomOptionInfo);



    }

    @GetMapping("/detail/reserveAjax")
    public String reserveAjaxGet(String selectDate, long roomNo, Model model) {

        Map<String, ArrayList<Integer>> availableReserveTimes = reserveService.getAvailableReservationTime(selectDate, roomNo);

        model.addAttribute("availableReserveTimes", availableReserveTimes);
        model.addAttribute("selectDate", selectDate);

        return "/devroom/reserve/reserveAjax";
    }


}
