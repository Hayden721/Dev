package com.dev.shop.item.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ItemService{
    
    void uploadRoomImage(MultipartFile thumbnail, List<MultipartFile> extraImg, Long roomNo);
    void optionImageUpload(Map<String, MultipartFile> optionImg);

    void updateRoomImage(Map<String, MultipartFile> imageFiles);

    void updateOptionImage(Map<String, MultipartFile> optionImgFormData);
}
