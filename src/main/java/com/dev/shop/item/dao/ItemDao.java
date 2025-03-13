package com.dev.shop.item.dao;

import com.dev.shop.item.dto.FileRequest;
import com.dev.shop.item.dto.OptionFileReqeuest;
import com.dev.shop.item.dto.FileResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ItemDao {

    void insertRoomImages(List<FileRequest> refinedImages);

    List<FileResponse> selectAdditionalImageByRoomNo(Long roomNo);

    FileResponse selectThumbnailByRoomNo(Long roomNo);

    void insertOptionImage(List<OptionFileReqeuest> imagesList);

    void updateOptionImage(List<OptionFileReqeuest> imagesList);

    void updateRoomImage(List<FileRequest> imagesList);

    void deleteRoomImage(Long imageNo);
}
