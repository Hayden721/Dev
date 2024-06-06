package com.dev.shop.utils;

import com.dev.shop.seller.dto.ImageFileDto;
import com.dev.shop.seller.dto.OptionImageDto;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Getter
public class FileUtils {


    private final String uploadPath = choosePath();

    public String choosePath() {
        String os = System.getProperty("os.name").toLowerCase();
        String comName = System.getProperty("user.name");

        if(os.contains("win")) {
            return Paths.get("C:", "develop", "upload-files").toString();
        } else if (os.contains("mac")) {
            return Paths.get("/Users", comName, "develop", "upload-files").toString();
        } else {
            throw new RuntimeException("지원되지 않는 운영체제");
        }

    }
    // main image upload
    public List<ImageFileDto> uploadFiles(final List<MultipartFile> multipartFiles) {
        List<ImageFileDto> files = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (multipartFile.isEmpty()) {
                continue;
            }
            files.add(uploadFile(multipartFile));
        }
        return files;
    }

    public ImageFileDto uploadFile(final MultipartFile multipartFile) {
        if(multipartFile.isEmpty()) {
            return null;
        }
        String saveName = generatedSaveFileName(multipartFile.getOriginalFilename());
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String uploadPath = getUploadPath(today) + File.separator + saveName;
        File uploadFile = new File(uploadPath);

        try {
            multipartFile.transferTo(uploadFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return ImageFileDto.builder()
                .originalName(multipartFile.getOriginalFilename())
                .saveName(saveName)
                .fileSize(multipartFile.getSize())
                .createdDate(LocalDate.parse(today))
                .build();
    }


    //OpitonImageUpload 옵션 이미지 업로드에 사용


    public OptionImageDto optionImageUpload(final MultipartFile multipartFile) {
        if(multipartFile.isEmpty()) {
            return null;
        }
        String saveName = generatedSaveFileName(multipartFile.getOriginalFilename());
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String uploadPath = getUploadPath(today) + File.separator + saveName;
        File uploadFile = new File(uploadPath);

        try {
            multipartFile.transferTo(uploadFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return OptionImageDto.builder()
                .originalName(multipartFile.getOriginalFilename())
                .saveName(saveName)
                .fileSize(multipartFile.getSize())
                .createdDate(LocalDate.parse(today))
                .build();
    }

    public List<OptionImageDto> optionImageUploads(final List<MultipartFile> multipartFiles) {
        List<OptionImageDto> files = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (multipartFile.isEmpty()) {
                continue;
            }
            files.add(optionImageUpload(multipartFile));
        }
        return files;
    }

    private String generatedSaveFileName(final String filename) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String extension = StringUtils.getFilenameExtension(filename);
        return uuid + "." + extension;
    }

    private String getUploadPath() {
        return makeDirectories(uploadPath);
    }

    private String getUploadPath(final String addPath) {
        return makeDirectories(uploadPath + File.separator + addPath);
    }

    private String makeDirectories(final String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir.getPath();
    }


}
