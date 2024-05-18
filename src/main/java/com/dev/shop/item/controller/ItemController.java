package com.dev.shop.item.controller;

import com.dev.shop.item.dto.FileResponse;

import com.dev.shop.item.service.ItemService;
import com.dev.shop.reserve.dto.RoomOptionDto;
import com.dev.shop.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;


@Slf4j
@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final FileUtils fileUtils;

    @GetMapping("/seller/error/image-upload-error")
    public void imageUploadError() {

    }


    @GetMapping("/seller/room/image-upload")
    public String roomImageUpload(Model model, HttpSession session) {

        Long roomNo = (Long) session.getAttribute("generatedRoomNo");

        log.info("/seller/room/image-upload GET roomNo : {}", roomNo);
        // session에서 넘어온 roomNo가 null일 때 비정상적인 접근 오류 사이트로 redirect시키기


            String filePath = fileUtils.choosePath();
            log.info("/seller/room/image-upload filePath : {}", filePath);

            List<FileResponse> roomImage = itemService.getFileInfoByRoomNo(roomNo);


            //roomNo로 저장된 roption 리스트로 불러와서
            List<RoomOptionDto> roomOptionInfo = itemService.getRoomOptionByRoomNo(roomNo);
            int roomOptionCount = roomOptionInfo.size();

            session.removeAttribute("generatedRoomNo");

            model.addAttribute("roomNo", roomNo);
            model.addAttribute("filePath", filePath);
            model.addAttribute("roomImage", roomImage);
            model.addAttribute("roomOptionInfo", roomOptionInfo);
            model.addAttribute("roomOptionCount", roomOptionCount);

            return "/seller/room/image-upload";

    }

    @PostMapping("/seller/room/image-upload")
    public String sellerRoomCreateImagePost(
            @RequestParam("roomNo") Long roomNo,
            final @RequestParam("files") List<MultipartFile> images,
            final @RequestParam("thumbnailImage") MultipartFile thumbnailImage,
            @RequestParam("optionImage") List<MultipartFile> optionImage,
            @RequestParam(value = "roptionNo", required = false) List<Long> roptionNo

            ) {



        log.info("/seller/room/create/image POST roomNo : {}", roomNo);
        log.info("/seller/room/create/image POST files : {}", images);
        // thumbnailImage : 대표이미지
        log.info("/seller/room/create/image POST thumbnailImage : {}", thumbnailImage);

        log.info("/image-upload ------------ optionImage : {}", optionImage);
        log.info("/image-upload ------------ roptionNo : {}", roptionNo);

        itemService.saveImagesByRoomNo(roomNo, images, thumbnailImage);
        itemService.saveOptionImageByRoomOptionNo(optionImage, roptionNo);

        return "redirect:/seller/room/list";
    }


    @ResponseBody
    @PostMapping("/seller/room/detail/upload/image")
    public void sellerRoomImageUploadPost(@RequestPart("extraImages") List<MultipartFile> extraImages,
                                            @RequestParam("roomNo") Long roomNo) {

        log.info("/seller/room/detail/update/image roomNo : {}", roomNo);
        log.info("/seller/room/detail/update/image imageData : {}", extraImages);
        itemService.sellerSaveImagesByRoomNo(roomNo, extraImages);

    }

    // 이미지 불러오기
    @GetMapping("seller/room/update-images-ajax")
    public String sellerRoomImageGetAjax(@RequestParam("roomNo") Long roomNo, Model model) {

        String filePath = fileUtils.choosePath();
        FileResponse thumbnailImage = itemService.getThumbnailImageByRoomNo(roomNo);
        List<FileResponse> additionalImage = itemService.getAdditionalImageByRoomNo(roomNo);


        model.addAttribute("filePath", filePath);
        model.addAttribute("thumbnailImage", thumbnailImage);
        model.addAttribute("additionalImage", additionalImage);


        return "/seller/room/update-images-ajax";
    }

    // 이미지 업데이트
    @ResponseBody
    @PostMapping("/seller/room/detail/update/image")
    public void sellerRoomImageUpdatePost(@RequestPart("extraImage") MultipartFile extraImage,
                                          @RequestParam("imageNo") Long imageNo) {

        log.info("/seller/room/detail/update/image imageNo : {}", imageNo);
        log.info("/seller/room/detail/update/image imageData : {}", extraImage);
        itemService.sellerUpdateImageByImageNo(imageNo, extraImage);

    }

    // 이미지 삭제
    @ResponseBody
    @PostMapping("/room/image/delete")
    public void sellerRoomImageDelete(@RequestParam("imageNo") Long imageNo) {

        itemService.deleteRoomImageByImageNo(imageNo);
        log.info("imageNo : {}", imageNo);

    }




}
