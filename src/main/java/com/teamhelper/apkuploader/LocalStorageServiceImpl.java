package com.teamhelper.apkuploader;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.io.FileUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author Administrator
 */
@Service("LocalStorage")
@Slf4j
@Configuration
public class LocalStorageServiceImpl implements OssService {

    @Resource
    LocalStorageConfig localStorageConfig;

    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        String datePath = new DateTime().toString("yyyy/MM/dd");
        String fileName = "/" + datePath + "/" + file.getOriginalFilename();
        String fileSavePath = localStorageConfig.getUploadFolder() + fileName;
        String fileSaveDir = localStorageConfig.getUploadFolder() + "/" + datePath + "/";
        if (!FileUtil.exist(fileSaveDir)) {
            FileUtil.mkdir(fileSaveDir);
        }
        FileUtil.writeFromStream(inputStream, new File(fileSavePath));
        return localStorageConfig.getDomain() + localStorageConfig.getAccessPathPattern() + fileName;
    }
}
