package com.dev.shop.item.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString

public class OptionImageDto {
    private Long roptionNo;
    private MultipartFile optionImage;

}
