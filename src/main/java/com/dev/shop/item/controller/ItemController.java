package com.dev.shop.item.controller;

import com.dev.shop.item.service.ItemService;
import com.dev.shop.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

import java.util.List;
import java.util.Map;


@Slf4j
@Controller
@RequiredArgsConstructor

public class ItemController {

    private final ItemService itemService;
    private final FileUtils fileUtils;


    // 방 이미지 업로드
    @PostMapping("/seller/create/room/image/upload")
    @ResponseBody
    public ResponseEntity<?> roomImageUpload(
            @RequestParam("thumbnail") MultipartFile thumbnail,
            @RequestParam(value = "extraImg", required = false) List<MultipartFile> extraImg,
            HttpSession session
    ) {
        log.info("thumbnail image {}",thumbnail);
        log.info("extra image {}",extraImg);

        Long roomNo = (Long) session.getAttribute("generatedRoomNo");
        log.info("roomNo {}", roomNo);

        if(roomNo == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("생성중인 방이 없습니다.");
        }

        itemService.uploadRoomImage(thumbnail, extraImg, roomNo);

        return ResponseEntity.ok("/seller/create/option");
    }

    @PostMapping("/seller/create/option/image/upload")
    public ResponseEntity<?> sellerOptionImageUpload(@RequestParam Map<String, MultipartFile> optionImg, HttpSession session) {

        log.info("optionImg {}", optionImg);

        itemService.optionImageUpload(optionImg);
        session.removeAttribute("generatedRoomNo");

        return ResponseEntity.ok("/seller/room/list");
    }

    @PostMapping("/seller/update/room/image")
    public ResponseEntity<?> updateOptionImage(
            @RequestParam MultipartFile thumbnail,
            @RequestParam Map<String, MultipartFile> optionImg) {


        return ResponseEntity.ok().build();
    }

    @PostMapping("/seller/update/option/image")
    public ResponseEntity<?> updateRoomImage(@RequestParam Map<String, MultipartFile> optionImgFormData) {

        if(optionImgFormData == null || optionImgFormData.isEmpty()) {
            return ResponseEntity.ok().body("수정된 이미지가 없습니다.");
        }
        log.info("imageFiles {}", optionImgFormData);


        try{
            itemService.updateOptionImage(optionImgFormData);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error update image: {}", e.getMessage(), e);
            return ResponseEntity.ok().body("업데이트된 이미지 없음");
        }
    }


    // error

    // 이미지 업로드 에러
    @GetMapping("/image/error/upload")
    public void imageUploadError() {
    }


}
