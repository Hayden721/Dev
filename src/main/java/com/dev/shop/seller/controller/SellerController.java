package com.dev.shop.seller.controller;

import com.dev.shop.item.dto.FileResponse;
import com.dev.shop.seller.dto.*;
import com.dev.shop.seller.service.SellerService;
import com.dev.shop.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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

    FileUtils fileUtils = new FileUtils();
    String filePath = fileUtils.choosePath();

    // sellerSpot 메인 페이지
    @GetMapping("/main")
    public void sellerMain() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        log.info("--- [/seller/login] 권한 확인 : {}", authorities);
    }
    // 로그인 페이지
    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false ) String error, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        boolean hasUserAuthority = authorities.stream().anyMatch(grantedAuthority -> "USER".equals(grantedAuthority.getAuthority()));

        if(hasUserAuthority) {
            return "redirect:/seller/logout";
        }

        model.addAttribute("error", error);
        return "seller/login";
    }

    // 로그아웃
    @GetMapping("/logout")
    public String sellerLogoutGet(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        return "redirect:/seller/login";
    }

    // 회원 가입
    @GetMapping("/register")
    public String register() {
        return "/seller/register";
    }

    @PostMapping("/register")
    public void register(SellerRegisterRequest sellerRegister, @RequestParam("sellerPwConfirm") String sellerPwConfirm) {
        sellerService.sellerRegister(sellerRegister, sellerPwConfirm);
    }

    @GetMapping("/id-duplicate-check")
    @ResponseBody
    public ResponseEntity<?> idDuplicate(@RequestParam("idValue") String sellerId) {
        int duplicateValue = sellerService.checkIdDuplicateBySellerId(sellerId);

        return ResponseEntity.ok(duplicateValue);
    }

    @GetMapping("/create/room/validate")
    public String validateCreateRoom(Model model) {
        String authId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int checkRoomCount = sellerService.roomCountByAuthId(authId);

        model.addAttribute("checkRoomCount", checkRoomCount);

        return "/seller/room/create/validate";
    }

    // 방 만들기
    @GetMapping("/create/room")
    public String createRoom() {
        String authId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long sellerNo = sellerService.getSellerNoByAuthId(authId);

        return "/seller/room/create/create-room";
    }

    // 방 만들기
    @PostMapping("/create/room")
    public ResponseEntity<?> createRoom(@RequestBody RoomRequest roomInfo, HttpSession session) {
        // 로그인한 아이디
        String authId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // 방을 만들면 roomNo를 반환
        Long generatedRoomNo = sellerService.createRoom(roomInfo, authId);

        log.info("roomInfo {}", roomInfo);
        log.info("--------------11111111111---- {}", generatedRoomNo);

        session.setAttribute("generatedRoomNo", generatedRoomNo);
        return ResponseEntity.ok("/seller/create/room/image");
    }

    // 방 생성 후 이미지 페이지
    @GetMapping("/create/room/image")
    public String roomImage() {
        return "/seller/room/create/image";
    }

    @GetMapping("/create/option")
    public String createRoomOption(HttpSession session) {
        Long generatedRoomNo = (Long) session.getAttribute("generatedRoomNo");

        log.info("session roomNo {}", generatedRoomNo);
        // 세션값 없으면 접근 차단
        if (generatedRoomNo == null) {
            return "redirect:/seller/room/create-error";
        }
            return "/seller/room/create/create-option";
    }


    @PostMapping("/create/option")
    public ResponseEntity<?> roomOptionCreatePost(@RequestBody List<RoomOptionRequest> options, HttpSession session) {
        Long generatedRoomNo = (Long) session.getAttribute("generatedRoomNo");
        log.info("options {}", options);
        sellerService.createRoomOption(options, generatedRoomNo);

        return ResponseEntity.ok("/seller/create/option/image");
    }

    @GetMapping("/create/option/image")
    public String optionImage(HttpSession session, Model model) {
        Long roomNo = (Long) session.getAttribute("generatedRoomNo");

        // 데이터 조회
        List<RoomOptionResponse> optionInfo = sellerService.getRoomOptionInfo(roomNo);

        model.addAttribute("optionInfo", optionInfo);

        return "/seller/room/create/option-image";
    }

    @GetMapping("/room/list")
    public String roomListGet(Model model) {
        String authId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int checkRoomCount = sellerService.roomCountByAuthId(authId);

        if (checkRoomCount == 0) {
            return "/seller/room/empty";
        } else {
            List<RoomListDto> roomList = sellerService.getRoomListByAuthId(authId);

            model.addAttribute("roomList", roomList);

            return "/seller/room/list";
        }
    }

    // 방 상세페이지
    @GetMapping("/room/detail")
    public String roomListDetail(@RequestParam final Long roomNo, Model model) {
        RoomResponse roomInfo =  sellerService.getRoomInfo(roomNo); // 방 정보 O
        // 썸네일 이미지
        FileResponse thumbnailImg = sellerService.getThumbnailImage(roomNo);
        List<FileResponse> extraImg = sellerService.getExtraImage(roomNo);
        List<OptionAndImageResponse> optionAndImage = sellerService.getOptionInfoAndImage(roomNo);
        if((thumbnailImg == null || extraImg == null)) {
            return "/seller/room/error/detail-error";
        } else {
            model.addAttribute("roomInfo", roomInfo);
            model.addAttribute("extra", extraImg);
            model.addAttribute("thumbnail", thumbnailImg);
            model.addAttribute("optionAndImage", optionAndImage);
            model.addAttribute("filePath", filePath);

            return "/seller/room/detail";
        }
    }

    @GetMapping("/room/update")
    public String roomUpdateGet(@RequestParam("roomNo") Long roomNo, Model model) {
        log.info("/room/detail/update roomNo : {}", roomNo);
        RoomResponse roomInfo = sellerService.getRoomInfo(roomNo);// 방 정보
        FileResponse thumbnailImg = sellerService.getThumbnailImage(roomNo);
        List<FileResponse> extraImg = sellerService.getExtraImage(roomNo);

        model.addAttribute("roomInfo", roomInfo);
        model.addAttribute("filePath", filePath);
        model.addAttribute("extraImg", extraImg);
        model.addAttribute("thumbnailImg", thumbnailImg);

        return "/seller/room/update/update-room";
    }

    // Post /room/update
    @PostMapping("/room/update")
    public ResponseEntity<?> roomUpdatePost(@RequestBody RoomRequest roomInfo) {
        log.info("/room/update roomInfo {}", roomInfo);
        sellerService.updateRoom(roomInfo);
        return ResponseEntity.ok("ok");
    }


    @GetMapping("/room/option/update")
    public String updateOptionGet(@RequestParam("roomNo") Long roomNo,Model model) {
        int optionTotal = sellerService.getOptionCount(roomNo);
        List<OptionAndImageResponse> optionAndImg = sellerService.getOptionInfoAndImage(roomNo);

        model.addAttribute("optionTotal", optionTotal);
        model.addAttribute("filePath", filePath);
        model.addAttribute("optionAndImg", optionAndImg);

        return "/seller/room/update/update-option";
    }

    @PostMapping("/room/option/update")
    @ResponseBody
    public ResponseEntity<?> updateOptionPost(
            @RequestParam("roomNo") Long roomNo,
            @RequestBody List<RoomOptionRequest> optionData) {
        log.info("roomNo {}", roomNo);
        log.info("updateOption : {}", optionData);
        try {
            sellerService.updateOption(optionData);
            return ResponseEntity.ok().body("/seller/room/detail?roomNo=" + roomNo);

        } catch (Exception e) {
            log.error("옵션 수정 오류 : {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("수정 중 오류가 발생했습니다.");
        }
    }

    @PostMapping("/room/option/add")
    public ResponseEntity<?> addOption(@RequestParam("roomNo") Long roomNo,
                                       @RequestPart List<RoomOptionRequest> optionData,
                                       @RequestPart List<MultipartFile> optionImages
                                       ) {
        sellerService.addOption(optionData, roomNo, optionImages);
        log.info("optionImage : {}", optionImages);
        log.info("addOption : {}", optionData);

        return ResponseEntity.ok().body("/seller/room/detail?roomNo=" + roomNo);
    }

    // option function
    @PostMapping("/room/option/delete")
    @ResponseBody
    public ResponseEntity<?> roomDeleteOptionPost(@RequestParam("roptionNo") Long optionNo,
                                     @RequestParam("optionImageNo") Long optionImageNo,
                                     @RequestParam("roomNo") Long roomNo) {
        log.info("roomNo {}", roomNo);
        log.info("roptionNo : {}", optionNo);
        log.info("optionImageNo : {}", optionImageNo);

        // optionImageNo가 없을 때 데이터 처리 optionImageNo가 null, 0일 때 optionImageNo를 어떻게 해야 할까

        sellerService.deleteRoomOption(optionNo, optionImageNo);

        return ResponseEntity.ok().body("/seller/room/option/update?roomNo=" + roomNo);
    }


//    ------------------------------------ 수정 완료


    // 방 삭제
    @PostMapping("/room/delete")
    public void roomDelete(@RequestParam("roomNo") Long roomNo) {
        log.info("roomNo: {}", roomNo);
    }

    //
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


    //------------------------------------------ error 관련
    //로그인 오류 세션 없애기
    @GetMapping("/login/error-session-remove")
    public String loginErrorSessionRemove(HttpServletRequest request) {
        log.info("로그인 오류 세션 삭제전 확인 : {}", request.getSession().getAttribute("errorMsg"));
        request.getSession().removeAttribute("errorMsg");

        return "redirect:/seller/login";
    }

    @GetMapping("/create/error/max-room")
    public String errorMaxRoom(Model model) {
        return "/seller/room/error/max-room";
    }

    // 방 생성중 오류 발생 시 사용
    @PostMapping("/error/delete/room")
    public ResponseEntity<?> deleteRoom(HttpSession session) {
        Long roomNo = (Long) session.getAttribute("generatedRoomNo");

        sellerService.removeErrorRoom(roomNo);

        session.removeAttribute("generatedRoomNo");

        return ResponseEntity.ok().build();
    }

    @PostMapping("/error/create/room")
    public void errorCreateRoom(HttpSession session) {
        Long errorRoomNo = (Long) session.getAttribute("generatedRoomNo");
        sellerService.errorCreateRoom(errorRoomNo);
    }
}
