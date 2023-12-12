package com.dev.shop.seller.dto;

import lombok.*;

@Getter @Setter @AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// 로그인에 사용
public class SellerDto {
    private Long sellerNo;
    private String sellerId;
    private String sellerEmail;
    private String sellerPw;
    private String sellerPhone;
    private String sellerName;
    private String sellerAuth;
    private String appendDate;
    private String updateDate;

}
