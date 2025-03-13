package com.dev.shop.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.Builder;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MemberRequest {
    private Long memberNo;
    private String memberId;
    private String memberPw;
    private String memberEmail;
    private String memberPhone;
    private String memberName;
    private String memberCreationDate;
    private String memberUpdatedate;
    private String memberAuth;

    @Builder
    public MemberRequest(Long memberNo, String memberId, String memberPw, String memberEmail, String memberPhone, String memberName, String memberCreationDate, String memberUpdatedate, String memberAuth) {
        this.memberNo = memberNo;
        this.memberId = memberId;
        this.memberPw = memberPw;
        this.memberEmail = memberEmail;
        this.memberPhone = memberPhone;
        this.memberName = memberName;
        this.memberCreationDate = memberCreationDate;
        this.memberUpdatedate = memberUpdatedate;
        this.memberAuth = memberAuth;
    }
}
