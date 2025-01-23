package com.dev.shop.member.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class MemberDto {
    private Long memberNo;
    private String memberId;
    private String memberPw;
    private String memberEmail;
    private String memberPhone;
    private String memberName;
    private String memberCreationDate;
    private String memberUpdatedate;
}