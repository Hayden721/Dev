package com.dev.shop.seller.controller;

import com.dev.shop.item.dto.FileResponse;
import com.dev.shop.item.dto.OptionImageRequest;
import com.dev.shop.reserve.dto.RoomDto;
import com.dev.shop.reserve.dto.RoomOptionDto;
import com.dev.shop.seller.dto.*;
import com.dev.shop.seller.service.SellerService;
import com.dev.shop.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/seller")
@Slf4j
@RequiredArgsConstructor
public class SellerController {

    private final SellerService sellerService;
    private final FileUtils fileUtils;
    @GetMapping("/main")
    public void mainGet() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        log.info("--- [/seller/login] 권한 확인 : {}", authorities);
    }

    @GetMapping("/login")
    public String loginGet(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("--- [/seller/login] 토큰값 : {}", authentication);

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        boolean hasUserAuthority = authorities.stream().anyMatch(grantedAuthority -> "USER".equals(grantedAuthority.getAuthority()));
        log.info("=================== {}", hasUserAuthority);

        if(hasUserAuthority) {
            return "redirect:/seller/logout";
        }

        log.info("------------------ authorities : {}",authorities);

        return "seller/login";
    }

    @GetMapping("/logout")
    public String sellerLogoutGet(HttpServletRequest request, HttpServletResponse response) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        return "redirect:/seller/login";
    }

    @GetMapping("/register")
    public String registerGet() {
        log.info("--- [/seller/register] --- GET");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication instanceof AnonymousAuthenticationToken) {
            return "/seller/register";
        }
        return "redirect:/seller/login";
    }

    @PostMapping("/register")
    public void memberRegisterPost(SellerDto sellerDto) {
        log.info("memberDetailDto : {}", sellerDto);

        sellerService.sellerRegister(sellerDto);

    }

    @GetMapping("/room/create-error")
    public void roomCreateError() {

    }
    @GetMapping("/room/create-verification")
    public String createRoomVerification(Model model) {
        String authId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long sellerNo = sellerService.getSellerNoByAuthId(authId);

        int checkRoomCount = sellerService.getRoomCountBySellerNo(sellerNo);



        model.addAttribute("checkRoomCount", checkRoomCount);

        if (checkRoomCount == 12) {
            return "redirect:/seller/room/create-error";
        }else {
            return "/seller/room/create-verification";
        }

    }

    @GetMapping("/room/create")
    public String roomCreateGet(Model model) {
        String authId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long sellerNo = sellerService.getSellerNoByAuthId(authId);

        int checkRoomCount = sellerService.getRoomCountBySellerNo(sellerNo);

        model.addAttribute("sellerNo", sellerNo);

        if (checkRoomCount == 12) {
            return "/seller/room/create-error";
        } else {
            return "/seller/room/create";
        }
    }

    @PostMapping("/room/create")
    public void roomCreatePost(@RequestBody SellerRoomDto sellerRoomDto, HttpSession session) {
        log.info("------------sellerRoomDto = {}",sellerRoomDto);
        Long generatedRoomNo = sellerService.insertRoomInfoByPostRoomDto(sellerRoomDto);
        log.info("--------------11111111111---- {}", generatedRoomNo);

        session.setAttribute("generatedRoomNo", generatedRoomNo);

    }

    @GetMapping("/room/option-create")
    public String roomOptionCreateGet(HttpSession session) {

        Long generatedRoomNo = (Long) session.getAttribute("generatedRoomNo");
        log.info("--------1231212-- {}", generatedRoomNo);

        return "/seller/room/option-create";
    }
    @PostMapping("/room/option-create")
    public ResponseEntity<?> roomOptionCreatePost(@RequestBody List<PostRoomOptionDto> options, HttpSession session) {
        log.info("----------------- json test : {}", options);

        // 1. 방을 만든다. 2. 만든 방에 대한 roomNo 필요 3. roomNo를 찾아서 그에 대한 roomOption데이터를 insert해야한다.
        sellerService.insertRoomOptionInfoByOptions(options);

        PostRoomOptionDto findRoomNo = options.get(0);
        Long roomNo = Long.valueOf(findRoomNo.getRoomNo());
        log.info("/room/option-create --------- roomNo : {}", roomNo);



        return ResponseEntity.ok().build();
    }

    @GetMapping("/room/detail")
    public void roomListDetail(@RequestParam final Long roomNo,  Model model) {

        String filePath = fileUtils.choosePath(); // 파일 경로

        // 방 정보
        RoomDto roomInfo =  sellerService.getRoomDetailByRoomNo(roomNo); // 방 정보
        // 룸 옵션 카운트
        int roomOptionCount = sellerService.getRoomOptionCountByRoomNo(roomNo);

        // 썸네일 이미지
        FileResponse thumbnailImage = sellerService.getThumbnailImageByRoomNo(roomNo);

        List<FileResponse> additionalImage = sellerService.getAdditionalImageByRoomNo(roomNo);

        // 방 옵션 정보
        List<RequestRoomOptionDto> optionInfoAndImage = sellerService.getOptionInfoAndImage(roomNo);


        model.addAttribute("roomInfo", roomInfo);
        model.addAttribute("optionInfoAndImage", optionInfoAndImage);
        model.addAttribute("roomOptionCount", roomOptionCount);
        model.addAttribute("roomNo", roomNo);
        model.addAttribute("filePath", filePath);
        model.addAttribute("additionalImage", additionalImage);
        model.addAttribute("thumbnailImage", thumbnailImage);
    }

    @GetMapping("/room/detail/update")
    public String roomUpdateGet(@RequestParam("roomNo") Long roomNo, Model model) {
        log.info("/room/detail/update roomNo : {}", roomNo);

        RoomDto roomInfo =  sellerService.getRoomDetailByRoomNo(roomNo);
        List<RoomOptionDto> roomOptionInfo = sellerService.getRoomOptionInfoByRoomNo(roomNo);
        String filePath = fileUtils.choosePath(); // 파일 경로
        // 썸네일 이미지
        FileResponse thumbnailImage = sellerService.getThumbnailImageByRoomNo(roomNo);

        List<FileResponse> additionalImage = sellerService.getAdditionalImageByRoomNo(roomNo);

        // 방 옵션 정보
        List<RequestRoomOptionDto> optionInfoAndImage = sellerService.getOptionInfoAndImage(roomNo);


        model.addAttribute("roomInfo", roomInfo);
        model.addAttribute("roomOptionInfo", roomOptionInfo);
        model.addAttribute("roomNo", roomNo);

        model.addAttribute("filePath", filePath);
        model.addAttribute("additionalImage", additionalImage);
        model.addAttribute("thumbnailImage", thumbnailImage);
        model.addAttribute("optionInfoAndImage", optionInfoAndImage);

        return "/seller/room/update";
    }

    @PostMapping("/room/delete")
    public String roomDelete(@RequestParam("roomNo") Long roomNo) {

        sellerService.removeRoomByRoomNo(roomNo);

        return "redirect:/seller/room/list";
    }
    @GetMapping("/room/list")
    public String roomListGet(Model model) {
        String authId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long sellerNo = sellerService.getSellerNoByAuthId(authId);

        int checkRoomCount = sellerService.getRoomCountBySellerNo(sellerNo);

        if (checkRoomCount == 0) {
            return "/seller/room/empty";
        } else {
            List<RoomListDto> roomList = sellerService.getRoomListBySellerNo(sellerNo);

            model.addAttribute("roomList", roomList);

            return "/seller/room/list";
        }

    }

    @GetMapping("/reserve/manage")
    public void sellerReserveManage(Model model) {
        String authId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long sellerNo = sellerService.getSellerNoByAuthId(authId);

        List<ReserveManageDto> reserveManage= sellerService.getReserveManageInfoBySellerNo(sellerNo);

        model.addAttribute("reserveManage", reserveManage);

    }

    @GetMapping("/reserve/detail")
    public void sellerReserveDetail(@RequestParam("roomNo") Long roomNo, Model model) {
        log.info("/reserve/detail : {} ", roomNo);

        List<ReservationDto> reservationInfo = sellerService.getReservationInfoByRoomNo(roomNo);

        model.addAttribute("reservationInfo", reservationInfo);
    }


}
