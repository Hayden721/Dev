package com.dev.shop.item.dao;

import com.dev.shop.item.dto.FileRequest;
import com.dev.shop.item.dto.FileResponse;
import com.dev.shop.item.dto.OptionImageRequest;
import com.dev.shop.reserve.dto.RoomOptionDto;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

@Mapper
public interface ItemDao {

    void insertImageFilesByRoomNo(List<FileRequest> files);

    List<FileResponse> selectFileInfoByRoomNo(Long roomNo);

    List<RoomOptionDto> selectRoomOptionByRoomNo(Long roomNo);

    void insertOptionImageSaveByRefindOptionImage(List<OptionImageRequest> refinedOptionImage);


    void updateRoomImageByImageNo(Long imageNo);

    void insertRoomImages(List<FileRequest> refinedImages);
}
