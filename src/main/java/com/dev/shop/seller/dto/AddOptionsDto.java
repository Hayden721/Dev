package com.dev.shop.seller.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AddOptionsDto {
    private String title;
    private String price;
    private String content;
    private MultipartFile image;
}




