package com.dev.shop.reserve.controller;

import com.dev.shop.item.dto.FileResponse;
import com.dev.shop.reserve.dto.*;
import com.dev.shop.reserve.service.ReserveService;
import com.dev.shop.utils.FileUtils;
import com.dev.shop.utils.PagingResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@Slf4j
@Controller
@RequestMapping("/sharespot/reserve")
@RequiredArgsConstructor
public class ReserveController {

    private final ReserveService reserveService;
    private final FileUtils fileUtils;

    @GetMapping("/list")
    public String listGet(@ModelAttribute("params") final CriteriaDto params, Model model) {
        List<String> searchDiv = params.getSearchDiv();

        log.info("두 개로 받아 오는가? = {}", searchDiv);

        List<String> searchLocation = params.getSearchLocation();
        log.info("두 개로 받아 오는가? = {}", searchLocation);

        String filePath = fileUtils.choosePath();

        PagingResponse<ReserveRoomListDto> roomList = reserveService.findAllRoom(params);

        model.addAttribute("roomList", roomList);
        model.addAttribute("filePath", filePath);
        model.addAttribute("searchDiv", searchDiv);
        model.addAttribute("searchLocation", searchLocation);

        return "/sharespot/reserve/list";
    }
// ------------------------------------------------- end
    @GetMapping("/detail")
    public void detailGet(@RequestParam final Long roomNo, Model model, Principal principal) {
        // 이미지 파일 패스
        String filePath = fileUtils.choosePath();
        log.info("filePath {}", filePath);
        // 방정보 가져오기
        RoomInfoRequest roomInfo = reserveService.findRoomInfo(roomNo);
        log.info("roomInfo = {}", roomInfo);

        // 썸네일 이미지
        FileResponse thumbnailImage = reserveService.getThumbnailImage(roomNo);
        log.info("thumbnailImage {}", thumbnailImage);
        // 추가 이미지
        List<FileResponse> extraImages = reserveService.getExtraImage(roomNo);

        // roomOptionImage 옵션과 옵션 이미지
//        List<RoomOptionDto> roomOptionInfo = reserveService.findRoomOptionInfo(roomNo);

//        List<OptionAndImageDto> optionData = reserveService.getOptionAndImageByRoomNo(roomNo);

//        log.info("--- reserveController --- 값 : {}", optionData);

//        if(memberId != null) {
//            // bookmarkCheck
//            boolean bookmarkValue = reserveService.getBookmarkValue(memberId, roomNo);
//            log.info("controller boomarkValue : {}", bookmarkValue);
//            model.addAttribute("bookmark", bookmarkValue);
//        }

        model.addAttribute("roomInfo", roomInfo);
//        model.addAttribute("roomOptionInfo", roomOptionInfo);
        // 이미지 관련 model
        model.addAttribute("filePath", filePath);
        model.addAttribute("thumbnailImage", thumbnailImage);
        model.addAttribute("extraImages", extraImages);
//        model.addAttribute("optionData", optionData);
    }

    @GetMapping("/detail/reserve-option-ajax")
    public String reserveOptionAjaxGet(Long optionNo, Long roomNo, Long sellerNo, Model model) {

        model.addAttribute("sellerNo", sellerNo);
        model.addAttribute("optionNo", optionNo);
        model.addAttribute("roomNo", roomNo);

        return "/devroom/reserve/reserve-option-ajax";
    }

    @GetMapping("/detail/reserve-time-ajax")
    public String reserveTimeAjaxGet(String selectDate, Long roomNo, Long optionNo, Long sellerNo ,Model model) {

        log.info("optionNo : {}", optionNo);
        Map<String, ArrayList<Integer>> availableReserveTimes = reserveService.getAvailableReservationTime(selectDate, roomNo, optionNo);

        String authId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        model.addAttribute("availableReserveTimes", availableReserveTimes);
        model.addAttribute("selectDate", selectDate);
        model.addAttribute("roomNo", roomNo);
        model.addAttribute("optionNo", optionNo);
        model.addAttribute("authId", authId);
        model.addAttribute("sellerNo", sellerNo);

        return "/devroom/reserve/reserve-time-ajax";
    }

    @PostMapping("/reserve-info")
    public void reserveInfoPost(@RequestParam(required = false) Integer reserveStartTime, @RequestParam(required = false) Integer reserveEndTime,
            String authId, String selectDate, Long roomNo, Long optionNo, Long sellerNo, Model model) {

        log.info("----------------------- startTime : {}, endTime : {} ", reserveStartTime, reserveEndTime);
        log.info("----------------------- selectDate : {}, roomNo : {}, optionNo : {}, authId : {}", selectDate, roomNo, optionNo, authId);

        Long memberNo = reserveService.getMemberNoByAuthId(authId);

        model.addAttribute("reserveStartTime", reserveStartTime);
        model.addAttribute("reserveEndTime", reserveEndTime);
        model.addAttribute("selectDate", selectDate);
        model.addAttribute("optionNo", optionNo); //옵션의 이름으로 바꿔야 함
        model.addAttribute("sellerNo", sellerNo);
        model.addAttribute("roomNo", roomNo);
        model.addAttribute("memberNo", memberNo);


    }

    @PostMapping("/reserve-pay")
    public void reservePayPost(Integer reserveStartTime, Integer reserveEndTime, Long optionNo, String selectDate, Long sellerNo, Long memberNo, Long roomNo) {

        log.info("------------ /reserve-pay ------------ selectDate : {}, startTime : {}, endTime : {}, sellerNo : {}, memberNo : {}, roomNo : {}, optionNo : {}", selectDate, reserveStartTime, reserveEndTime, sellerNo, memberNo, roomNo, optionNo );
        reserveService.insertReservation(selectDate, reserveStartTime, reserveEndTime, sellerNo, memberNo, roomNo, optionNo );

    }

}
