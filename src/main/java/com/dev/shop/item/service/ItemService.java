package com.dev.shop.item.service;


import com.dev.shop.item.dto.FileResponse;
import com.dev.shop.reserve.dto.RoomOptionDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ItemService{


    void saveImagesByRoomNo(Long roomNo, List<MultipartFile> files, MultipartFile thumbnail);

    List<FileResponse> getFileInfoByRoomNo(Long roomNo);


    List<RoomOptionDto> getRoomOptionByRoomNo(Long roomNo);

    void saveOptionImageByRoomOptionNo(List<MultipartFile> optionImage, List<Long> roptionNo);

    void deleteRoomImageByImageNo(Long imageNo);
}
