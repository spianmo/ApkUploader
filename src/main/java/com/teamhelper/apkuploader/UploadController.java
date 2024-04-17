package com.teamhelper.apkuploader;


import com.teamhelper.apkuploader.base.BaseResponse;
import com.teamhelper.apkuploader.base.HttpStatus;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Copyright (c) 2022
 *
 * @Project: CollectionBackend
 * @Author: Finger
 * @FileName: UploadController.java
 * @LastModified: 2022/04/02 00:30:02
 */

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/upload")
public class UploadController {
    @Resource
    private OssService ossService;
    @PostMapping("file")
    public BaseResponse<String> uploadFile(@RequestParam("file") MultipartFile uploadFile) throws IOException {
        if (uploadFile.getSize() == 0) {
            return new BaseResponse<>(HttpStatus.FAILD.getCode(), "上传文件为空");
        }
        if (uploadFile.getSize() > 400 * 1024 * 1024) {
            return new BaseResponse<>(HttpStatus.FAILD.getCode(), "请上传400M以内的文件");
        }
        String url = ossService.uploadFile(uploadFile);
        return new BaseResponse<>(HttpStatus.SUCCESS, url);
    }
}