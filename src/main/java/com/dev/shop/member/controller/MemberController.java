package com.dev.shop.member.controller;


import com.dev.shop.member.dto.*;
import com.dev.shop.member.service.MemberService;
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
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Collection;
import java.util.List;

@Slf4j

@Controller
@RequiredArgsConstructor
@RequestMapping("/sharespot")
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
        // 최근에 추가된 방
        List<RoomAndImageResponse> recentCreateRoom = memberService.getMainInfoNewSpot();


        model.addAttribute("filePath", filePath);
        model.addAttribute("recentCreateRoom", recentCreateRoom);

        return "/sharespot/member/main";
    }

    @GetMapping("/member/login")
    public String memberLoginGet(@RequestParam(value = "error", required = false) String error, Model model) {
        log.info("--- [GET] member/login ---");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("--- [/login] 토큰값 : {}", authentication);


        model.addAttribute("error", error);

        return "/sharespot/member/login";
    }

    @GetMapping("/member/error-session-remove")
    public String errorSessionRemove(HttpServletRequest request) {
        request.getSession().removeAttribute("errorMsg");

        return "/sharespot/member/login";
    }

    @PostMapping("/member/logout")
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
    @GetMapping("/member/id-duplicate-check")
    @ResponseBody
    public ResponseEntity<?> idDuplicateCheck(@RequestParam("idValue") String memberId ) {
        log.info("memberId duplicate: {}", memberId);

        int duplicateValue = memberService.checkIdDuplicateByMemberId(memberId);

        return ResponseEntity.ok(duplicateValue);
    }

    @GetMapping("/member/register")
    public String memberRegisterGet() {
        log.info("--- [GET] member/register");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication instanceof AnonymousAuthenticationToken) {
            return "/sharespot/member/register";
        }

        return "/sharespot/member/register";
    }

    @PostMapping("/member/register")
    public void memberRegisterPost(MemberRequest memberRequest, @RequestParam("memberPwConfirm") String memberPwConfirm) {
        log.info("memberDetailDto : {}", memberRequest);
        log.info("memberPwConfirm: {}", memberPwConfirm);
        memberService.memberRegister(memberRequest, memberPwConfirm);
    }

    @GetMapping("/mypage")
    public String mypageGet() {
        return "/sharespot/member/mypage/mypage";
    }

    // 마이페이지 수정
    @GetMapping("/mypage/edit")
    public String mypageEditGet(Model model, Principal principal) {
        String memberId = principal.getName();
        MemberResponse memberInfo = memberService.getMemberInfo(memberId);

        model.addAttribute("memberInfo", memberInfo);

        return "/sharespot/member/mypage/edit";
    }

    @PostMapping("/mypage/edit")
    public String mypageEditPost(MemberRequest memberRequest, Principal principal) {
        log.info("memberUpdateData : {}", memberRequest);
//        memberService.updateMemberInfo(memberResponse);

        return "redirect:/sharespot/member/mypage";
    }

    @GetMapping("/mypage/edit/password")
    public String mypageEditPasswordGet(Model model, Principal principal) {

        return "/sharespot/member/mypage/edit-password";
    }

    @PostMapping("/mypage/edit/password")
    public ResponseEntity<?> mypageEditPasswordPost(Principal principal,
                                                    @RequestParam String password,
                                                    @RequestParam String confirmPassword,
                                                    @RequestParam String newPassword) {
        String memberId = principal.getName();
        log.info("password: {}", password);
        log.info("confirmPassword: {}", confirmPassword);
        log.info("newPassword: {}", newPassword);

        // 1. DB에 저장되어 있는 비번과 일치하지 않을 때
        boolean isCurrentPasswordMatch = memberService.validPassword(password, memberId);
        if(!isCurrentPasswordMatch) { // false일 때
            return ResponseEntity.badRequest().body("기존 비밀번호와 일치하지 않습니다.");
        }

        // 2. 바꾸려는 비번이 일치하지 않을 때
        if(!newPassword.equals(confirmPassword)) {
            return ResponseEntity.badRequest().body("비밀번호가 일치하지 않습니다.");
        }

        boolean isSuccess = memberService.changeMemberPassword(memberId, password, confirmPassword, newPassword);


        return ResponseEntity.ok(isSuccess);
    }

    @GetMapping("/member/find/password")
    public String findPasswordGet() {


        return "/sharespot/member/find-password";
    }

    @PostMapping("/member/find/password")
    public ResponseEntity<?> findPasswordPost(@RequestParam String memberId, @RequestParam String memberName, HttpSession session) {
        log.info("memberId : {}, memberName : {}", memberId, memberName);

        if(memberId == null || memberId.isEmpty() || memberName == null || memberName.isEmpty()) {
            return ResponseEntity.badRequest().body("빈 칸이 존재합니다.");
        }

        boolean isExist =  memberService.checkAccountExist(memberId, memberName);
        Long memberNo = memberService.getMemberNo(memberId);
        log.info("isExist : {}", isExist);

        if(!isExist) {
            return ResponseEntity.badRequest().body("존재하지 않는 회원입니다.");
        }

        session.setAttribute("memberNo", memberNo);

        return ResponseEntity.ok(true);
    }

    @GetMapping("/member/change/password")
    public String changePasswordGet(HttpSession session) {
        Long memberNo = (Long) session.getAttribute("memberNo");

        if(memberNo == null) {
            return "/error/wrong-approach";
        }

        return "/sharespot/member/change-password";
    }

    @PostMapping("/member/change/password")
    public ResponseEntity<?> changePasswordPost(HttpSession session,
                                     @RequestParam String password,
                                     @RequestParam String confirmPassword) {

        Long memberNo = (Long) session.getAttribute("memberNo");
        boolean isExist = memberService.changeMemberPassword(memberNo, password, confirmPassword);
        log.info("memberNo : {}", memberNo);

        if(memberNo == null) {
            session.removeAttribute("memberNo");
            return ResponseEntity.badRequest().body("잘못된 접근입니다.");
        }
        if(password == null || confirmPassword == null) {
            return ResponseEntity.badRequest().body("빈 칸이 존재합니다.");
        }
        if(!password.equals(confirmPassword)) {
            return ResponseEntity.badRequest().body("비밀번호가 일치하지 않습니다.");
        }

        if(!isExist) {
            return ResponseEntity.badRequest().body("오류가 발생했습니다.");
        }

        session.removeAttribute("memberNo");

        return ResponseEntity.ok("비밀번호가 변경되었습니다.");
    }

    @GetMapping("/mypage/bookmark/list")
    public String mypageBookmarkList(Principal principal, Model model) {
        String memberId = principal.getName();
        String filePath = fileUtils.choosePath();

        // 북마크된 room 가지고 오기
        List<BookmarkResponse> bookmarkList = memberService.getBookmarkList(memberId);

        model.addAttribute("filePath", filePath);
        model.addAttribute("bookmarkList", bookmarkList);

        return "/sharespot/member/mypage/bookmark-list";
    }

    @GetMapping("/mypage/reservation/list")
    public String reserveCheckGet(@ModelAttribute("params") final ReservationCriteriaDto params, Model model) {
        String authId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PagingResponse<ReservationResponse> reservationInfo = memberService.getReservationInfo(authId, params);
        log.info("----------=-=-=--=- {}", reservationInfo);

        model.addAttribute("reservationInfo", reservationInfo);

        return "/sharespot/member/mypage/reservation-info";
    }

    @PostMapping("/mypage/reservation/cancel")
    public String reservationCancelGet(@RequestParam Long reservationNo) {

        memberService.cancelReservation(reservationNo);

        return "redirect:/sharespot/member/mypage/reservation-info";
    }

    @GetMapping("/mypage/reservation/end/list")
    public String reservationEndList(@ModelAttribute("params") final ReservationCriteriaDto params, Model model) {

        List<String> searchType = params.getSearchType();
        log.info("mypageSearchType : {}", searchType);
        String authId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("endList params : {}", params);
        PagingResponse<ReservationResponse> reservationInfo = memberService.getReservationEndInfo(authId, params);

        model.addAttribute("reservationInfo", reservationInfo);
        return "/sharespot/member/mypage/reservation-end-list";
    }


    // 결제 내역
    @GetMapping("/mypage/account")
    public String accountGet(Principal principal, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("authentication : {}", authentication);

        String memberId = null;
        if(principal != null) {
            memberId = principal.getName();
            model.addAttribute("memberId", memberId);
        }else {
            log.warn("로그인이 되어있지 않음");
        }

        return "/sharespot/member/mypage/account";
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






}
