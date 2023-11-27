package com.dev.shop.member.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// 로그인에 사용
public class MemberDto {
    private Long memberNo;
    private String memberId;
    private String memberEmail;
    private String memberPw;
    private String memberPhone;
    private String memberName;
    private String memberAuth;


}
