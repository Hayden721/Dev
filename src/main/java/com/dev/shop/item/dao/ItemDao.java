package com.dev.shop.item.dao;

import com.dev.shop.seller.dto.ImageFileDto;
import com.dev.shop.item.dto.FileResponse;
import com.dev.shop.item.dto.OptionImageRequest;
import com.dev.shop.reserve.dto.RoomOptionDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.List;

@Mapper
public interface ItemDao {

    void insertImageFilesByRoomNo(List<ImageFileDto> files);

    List<FileResponse> selectFileInfoByRoomNo(Long roomNo);

    List<RoomOptionDto> selectRoomOptionByRoomNo(Long roomNo);

    void insertOptionImageSaveByRefindOptionImage(List<OptionImageRequest> refinedOptionImage);


    void updateRoomImageByImageNo(Long imageNo);

    void insertRoomImages(List<ImageFileDto> refinedImages);

    void UpdateRoomImage(@Param("refinedImage") ImageFileDto refinedImage, @Param("imageNo") Long imageNo);

    List<FileResponse> selectAdditionalImageByRoomNo(Long roomNo);

    FileResponse selectThumbnailByRoomNo(Long roomNo);



}
