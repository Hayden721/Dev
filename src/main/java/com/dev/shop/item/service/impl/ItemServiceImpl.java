package com.dev.shop.item.service.impl;

import com.dev.shop.item.dao.ItemDao;
import com.dev.shop.item.dto.FileRequest;
import com.dev.shop.item.dto.FileResponse;
import com.dev.shop.item.dto.OptionImageRequest;
import com.dev.shop.item.service.ItemService;
import com.dev.shop.reserve.dto.RoomOptionDto;
import com.dev.shop.utils.FileUtils;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemDao itemDao;
    private final FileUtils fileUtils;
    @Override
    public void saveImagesByRoomNo(Long roomNo, List<MultipartFile> files, MultipartFile thumbnail) {

        List<FileRequest> refinedImages = fileUtils.uploadFiles(files);
        FileRequest refinedThumbnail = fileUtils.uploadFile(thumbnail);


        log.info("ItemService --------------------- {}", refinedImages);
        log.info("ItemService ---------------------thumbnail {}", refinedThumbnail);

        if(CollectionUtils.isEmpty(refinedImages) && refinedThumbnail == null) {
            return;
        }

        for (FileRequest file : refinedImages) {
            file.setThumbnail('N');
            file.setRoomNo(roomNo);
        }

        // thumbnail 이미지 데이터 처리

        if(refinedThumbnail != null) {
            refinedThumbnail.setThumbnail('Y');
            refinedThumbnail.setRoomNo(roomNo);

            refinedImages.add(refinedThumbnail);
        }
        itemDao.insertImageFilesByRoomNo(refinedImages);

    }

    @Override
    public List<FileResponse> getFileInfoByRoomNo(Long roomNo) {
        return itemDao.selectFileInfoByRoomNo(roomNo);
    }

    @Override
    public List<RoomOptionDto> getRoomOptionByRoomNo(Long roomNo) {
        return itemDao.selectRoomOptionByRoomNo(roomNo);
    }

    @Override
    public void saveOptionImageByRoomOptionNo(List<MultipartFile> optionImage, List<Long> roptionNo) {
        List<OptionImageRequest> refinedOptionImage = fileUtils.optionImageUploads(optionImage);
        log.info("----------------- itemServiceImpl refinedOptionImage : {} ", refinedOptionImage);

        for(int i=0; i<refinedOptionImage.size(); i++) {
            refinedOptionImage.get(i).setRoptionNo(roptionNo.get(i));
        }

        log.info("----------------- itemServiceImpl refinedOptionImage2 : {} ", refinedOptionImage);

        if(!refinedOptionImage.isEmpty()) {
            itemDao.insertOptionImageSaveByRefindOptionImage(refinedOptionImage);
        }
    }
}
