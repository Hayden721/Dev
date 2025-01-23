package com.dev.shop.member.controller;


import com.dev.shop.member.dto.*;
import com.dev.shop.member.service.MemberService;
import com.dev.shop.reserve.dto.ReserveRoomListDto;
import com.dev.shop.utils.FileUtils;
import com.dev.shop.utils.PagingResponse;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Collection;
import java.util.List;

@Slf4j

@Controller
@RequiredArgsConstructor
@RequestMapping("/sharespot/member")
public class MemberController {

    private final MemberService memberService;
    private final FileUtils fileUtils;

    @GetMapping("/main")
    public String mainGet(Model model, Principal principal) {
        String filePath = fileUtils.choosePath();
        log.info("--- mainGet ---");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        boolean hasUserAuthority = authorities.stream().anyMatch(grantedAuthority -> "SELLER".equals(grantedAuthority.getAuthority()));
        log.info("=================== {}", hasUserAuthority);

        if(hasUserAuthority) {
            return "redirect:/sharespot/member/logout";
        }

        log.info("------------------ authorities : {}",authorities);

        if(authentication instanceof AnonymousAuthenticationToken) {
            log.info("--- [/member/login] 토큰값 : {}", authentication);
        } else {
            String memberId = principal.getName();
            log.info("멤버 아이디 값  : {}", memberId);

            model.addAttribute("auth", authentication);
        }

//        List<RoomAndImageDto> recentCreateRoom = memberService.getMainInfoNewSpot();

//        List<RoomAndImageDto> memberService
        model.addAttribute("filePath", filePath);
//        model.addAttribute("recentCreateRoom", recentCreateRoom);

        return "/sharespot/member/main";

    }

    @GetMapping("/login")
    public String memberLoginGet(@RequestParam(value = "error", required = false) String error, Model model) {
        log.info("--- [GET] member/login ---");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("--- [/login] 토큰값 : {}", authentication);

        model.addAttribute("error", error);

        return "/sharespot/member/login";
    }

    @GetMapping("/error-session-remove")
    public String errorSessionRemove(HttpServletRequest request) {
        request.getSession().removeAttribute("errorMsg");

        return "/sharespot/member/login";
    }

    @PostMapping("/logout")
    public void memberLogoutGet(HttpServletRequest request, HttpServletResponse response) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String refererUrl = request.getHeader("Referer");
        log.info("refererUrl : {}", refererUrl);

        System.out.println("refererUrl : " + refererUrl);

        if(refererUrl != null && refererUrl.isEmpty()) {
            request.getSession().setAttribute("previousPageUrl", refererUrl);
        }
        if(authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

    }

    // 아이디 중복 체크
    @GetMapping("/id-duplicate-check")
    @ResponseBody
    public ResponseEntity<?> idDuplicateCheck(@RequestParam("idValue") String memberId ) {
        log.info("memberId duplicate: {}", memberId);

        int duplicateValue = memberService.checkIdDuplicateByMemberId(memberId);

        return ResponseEntity.ok(duplicateValue);
    }



    @GetMapping("/register")
    public String memberRegisterGet() {
        log.info("--- [GET] member/register");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication instanceof AnonymousAuthenticationToken) {
            return "/devroom/member/register";
        }

        return "/sharespot/member/register";
    }

    @PostMapping("/register")
    public void memberRegisterPost(MemberRequest memberRequest, @RequestParam("memberPwConfirm") String memberPwConfirm) {
        log.info("memberDetailDto : {}", memberRequest);
        log.info("memberPwConfirm: {}", memberPwConfirm);
        memberService.memberRegister(memberRequest, memberPwConfirm);
    }

    @GetMapping("/mypage/account")
    public void accountGet(Principal principal, Model model) {
        String memberId = principal.getName();

        model.addAttribute("memberId", memberId);
    }

    // ---------------------------------

    @GetMapping("/mypage/edit")
    public void mypageEditGet(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String authId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        MemberDto memberInfo = memberService.memberInfoByAuthId(authId);
        model.addAttribute("memberInfo", memberInfo);
    }

    @PostMapping("/mypage/edit")
    public String mypageEditPost(MemberDto memberDto, String memberNewPw, String memberNewPwChk) {

        memberService.updateMemberInfo(memberDto, memberNewPw, memberNewPwChk );

        return "redirect:/devroom/member/mypage";
    }

    @GetMapping("/mypage/payment")
    public void mypagePaymentGet(@ModelAttribute("params") final ReservationCriteriaDto params, Principal principal, Model model) {

        log.info("params : {}", params);
        String memberId = principal.getName();

//        memberid로 no를 찾아서 마이바티스에 memberNo바꿔야 함
        // 페이징
        PagingResponse<PaymentHistoryDto> pagingPaymentHistory = memberService.getMemberPaymentHistoryByMemberId(memberId, params);
        log.info("payment history : {}", pagingPaymentHistory);
        model.addAttribute("paymentHistory", pagingPaymentHistory);
    }

    @GetMapping("/mypage/reservation-info")
    public String reserveCheckGet(@ModelAttribute("params") final ReservationCriteriaDto params, Model model) {
        String authId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long memberNo = memberService.getMemberNoByAuthId(authId);

        PagingResponse<getReserveInfoDto> reservationInfo = memberService.getReservationInfoByMemberNo(memberNo, params);
        log.info("----------=-=-=--=- {}", reservationInfo);
        model.addAttribute("reservationInfo", reservationInfo);

        return "/devroom/member/mypage/reservation-info";
    }

    @PostMapping("/mypage/reservation/cancel")
    public String reservationCancelGet(@RequestParam(required = false) Long reservationNo) {

        memberService.cancelReservation(reservationNo);

        return "redirect:/devroom/member/mypage/reservation-info";
    }

    @GetMapping("/mypage/bookmark/list")
    public String mypageBookmarkList(Principal principal, Model model) {
        String filePath = fileUtils.choosePath();
        String memberId = principal.getName();

        // 북마크된 room 가지고 오기
        List<BookmarkedDto> bookmarkedRoomList = memberService.getBookmarkedRoomListByMemberId(memberId);


        model.addAttribute("roomList", bookmarkedRoomList);
        model.addAttribute("filePath", filePath);
        return "/devroom/member/mypage/bookmark-list";
    }

    @PostMapping("/bookmark")
    public ResponseEntity<?> roomBookMark(@RequestParam("roomNo") Long roomNo, Principal principal) {
        String memberId = principal.getName();
        log.info("memberId {}", memberId);
        log.info("roomNo {}", roomNo);

        // 북마크가 되어 있는가? true 북마크 되었음, false 북마크 안되어 있음
        boolean bookmarkVal = memberService.roomBookmark(memberId, roomNo);
        return ResponseEntity.ok(bookmarkVal);
    }



}
