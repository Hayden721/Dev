package com.dev.shop.utils;

import com.dev.shop.item.dto.FileRequest;
import com.dev.shop.item.dto.OptionImageRequest;
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
    public List<FileRequest> uploadFiles(final List<MultipartFile> multipartFiles) {
        List<FileRequest> files = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (multipartFile.isEmpty()) {
                continue;
            }
            files.add(uploadFile(multipartFile));
        }
        return files;
    }

    public FileRequest uploadFile(final MultipartFile multipartFile) {
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

        return FileRequest.builder()
                .originalName(multipartFile.getOriginalFilename())
                .saveName(saveName)
                .fileSize(multipartFile.getSize())
                .build();
    }


    //OpitonImageUpload
    public List<OptionImageRequest> optionImageUploads(final List<MultipartFile> multipartFiles) {
        List<OptionImageRequest> files = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (multipartFile.isEmpty()) {
                continue;
            }
            files.add(optionImageUpload(multipartFile));
        }
        return files;
    }

    public OptionImageRequest optionImageUpload(final MultipartFile multipartFile) {
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

        return OptionImageRequest.builder()
                .originalName(multipartFile.getOriginalFilename())
                .saveName(saveName)
                .fileSize(multipartFile.getSize())
                .build();
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
