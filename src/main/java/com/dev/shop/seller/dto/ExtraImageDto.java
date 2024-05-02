package com.dev.shop.seller.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ExtraImageDto {

    private List<Long> extraImageNo;
    private List<MultipartFile> extraImage;

}


