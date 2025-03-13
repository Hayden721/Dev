package com.dev.shop.item.service.impl;

import com.dev.shop.item.dao.ItemDao;
import com.dev.shop.item.dto.FileRequest;
import com.dev.shop.item.dto.OptionFileReqeuest;
import com.dev.shop.item.service.ItemService;
import com.dev.shop.utils.FileUtils;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemDao itemDao;
    private final FileUtils fileUtils;

    @Override
    public void uploadRoomImage(MultipartFile thumbnail, List<MultipartFile> extraImg, Long roomNo) {
        List<FileRequest> wholeImage = new ArrayList<>();
        FileRequest refinedThumbnail = fileUtils.uploadFile(thumbnail); // 썸네일 이미지
        List<FileRequest> refinedImages = (extraImg != null && !extraImg.isEmpty())
                ? fileUtils.uploadFiles(extraImg)
                : new ArrayList<>(); // extraImg가 null이 아니고 isEmpty가 아니면 데이터 정제, 반대라면 빈 리스트 반환

        // 둘 다 없는 경우 return
        if(CollectionUtils.isEmpty(refinedImages) && refinedThumbnail == null) {
            return;
        }
        // 정제된 썸네일이 null이 아닐 때
        if(refinedThumbnail != null) {
            FileRequest thumbnailImage = FileRequest.builder()
                .originalName(refinedThumbnail.getOriginalName())
                .saveName(refinedThumbnail.getSaveName())
                .fileSize(refinedThumbnail.getFileSize())
                .uploadDate(refinedThumbnail.getUploadDate())
                .thumbnail('Y')
                .roomNo(roomNo)
                .build();

            wholeImage.add(thumbnailImage);
        }

        if(refinedImages != null) {
            for (FileRequest file : refinedImages) {
                FileRequest extraImage = FileRequest.builder()
                        .originalName(file.getOriginalName())
                        .saveName(file.getSaveName())
                        .fileSize(file.getFileSize())
                        .uploadDate(file.getUploadDate())
                        .thumbnail('N')
                        .roomNo(roomNo)
                        .build();
                wholeImage.add(extraImage);
            }
        }

        itemDao.insertRoomImages(wholeImage);

    }

    @Override
    public void optionImageUpload(Map<String, MultipartFile> optionImg) {
        List<OptionFileReqeuest> imagesList = new ArrayList<>();

        for(Map.Entry<String, MultipartFile> entry : optionImg.entrySet()) {
            // 1. MultipartFile 분리 key = roptionNo, value = imgData
            Long roptionNo = Long.valueOf(entry.getKey()); // roptionNo
            MultipartFile img = entry.getValue(); // 이미지
            log.info("img : {}", img);
            OptionFileReqeuest imgMapping = fileUtils.optionImageUpload(img);
            log.info("imgMapping : {}", imgMapping);

            OptionFileReqeuest image = OptionFileReqeuest.builder()
                .originalName(imgMapping.getOriginalName())
                .saveName(imgMapping.getSaveName())
                .fileSize(imgMapping.getFileSize())
                .uploadDate(imgMapping.getUploadDate())
                .roptionNo(roptionNo)
                .build();

            imagesList.add(image);
            log.info("imagesList : {}", imagesList );
        }
        itemDao.insertOptionImage(imagesList);
    }

    @Override
    public void updateRoomImage(Map<String, MultipartFile> imageFiles) {
        if(imageFiles.isEmpty()) {
            return;
        }
        List<FileRequest> imagesList = new ArrayList<>();
        // room image
        for(Map.Entry<String, MultipartFile> entry : imageFiles.entrySet()) {
            Long roomimageNo = Long.valueOf(entry.getKey()); // 키값 추출
            MultipartFile img = entry.getValue(); // 벨류값 추출
            log.info("roomimageNo key : {}", roomimageNo);
            log.info("img value : {}", img);

            // multipart 데이터 변환
            FileRequest imgMapping = fileUtils.uploadFile(img);

            FileRequest image = FileRequest.builder()
                    .roomimageNo(roomimageNo)
                    .originalName(imgMapping.getOriginalName())
                    .saveName(imgMapping.getSaveName())
                    .fileSize(imgMapping.getFileSize())
                    .uploadDate(imgMapping.getUploadDate())
                    .build();

            imagesList.add(image);
        }

        itemDao.updateRoomImage(imagesList);

    }

    @Override
    public void updateOptionImage(Map<String, MultipartFile> optionImgFormData) {
        List<OptionFileReqeuest> imagesList = new ArrayList<>();
        for(Map.Entry<String, MultipartFile> entry : optionImgFormData.entrySet()) {
                Long optionImageNo = Long.valueOf(entry.getKey()); // 키값 추출 (optionImageNo)
                MultipartFile img = entry.getValue(); // 벨류값 추출 (Multipart Data)

                // multipart 데이터 변환
                OptionFileReqeuest optionImgMapping = fileUtils.optionImageUpload(img);
                log.info("optionImgMapping : {}", optionImgMapping);
                OptionFileReqeuest image = OptionFileReqeuest.builder()
                        .roptionimageNo(optionImageNo)
                        .originalName(optionImgMapping.getOriginalName())
                        .saveName(optionImgMapping.getSaveName())
                        .fileSize(optionImgMapping.getFileSize())
                        .uploadDate(optionImgMapping.getUploadDate())
                        .build();
                imagesList.add(image);
            }

            itemDao.updateOptionImage(imagesList);
    }

    @Override
    public void imageDelete(Long imageNo) {
        itemDao.deleteRoomImage(imageNo);
    }

    @Override
    public void addImage(List<MultipartFile> image, Long roomNo) {
        List<FileRequest> imgList = new ArrayList<>();
        // image를 반복해서 정제
        for(MultipartFile img : image) {
            FileRequest convertedImage = fileUtils.uploadFile(img);

            FileRequest buildImage = FileRequest.builder()
                    .originalName(convertedImage.getOriginalName())
                    .saveName(convertedImage.getSaveName())
                    .fileSize(convertedImage.getFileSize())
                    .uploadDate(convertedImage.getUploadDate())
                    .roomNo(roomNo)
                    .thumbnail('N')
                    .build();

            imgList.add(buildImage);
        }
        //
        itemDao.insertRoomImages(imgList);

    }


}
