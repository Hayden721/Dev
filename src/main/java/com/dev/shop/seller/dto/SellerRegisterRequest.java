package com.dev.shop.seller.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class SellerRegisterRequest {
    private Long sellerNo;
    private String sellerId;
    private String sellerPw;
    private String sellerEmail;
    private String sellerPhone;
    private String sellerName;
    private String sellerCreationdate;
    private String sellerUpdatedate;
    private String sellerAuth;
}
