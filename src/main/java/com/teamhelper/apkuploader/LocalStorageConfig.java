package com.teamhelper.apkuploader;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Copyright (c) 2021
 *
 * @Project: SpringbootDemo
 * @Author: Finger
 * @FileName: LocalStorageConfig.java
 * @LastModified: 2021/11/10 15:34:10
 */

@Data
@Configuration
@ConfigurationProperties(prefix = "storage.oss")
public class LocalStorageConfig {
    public String domain;
    /**
     * 本地存储文件存放地址
     */
    private String uploadFolder;


    /**
     * 本地存储文件访问路径
     */
    private String accessPathPattern;
}