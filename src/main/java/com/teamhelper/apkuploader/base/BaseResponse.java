package com.teamhelper.apkuploader.base;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;

/**
 * Copyright (c) 2021
 *
 * @Project: SpringbootDemo
 * @Author: Finger
 * @FileName: BaseResponse.java
 * @LastModified: 2021/11/10 15:34:10
 */

@Data
@Builder
@AllArgsConstructor
public class BaseResponse<T> {
    private T data;
    private String message;
    private int code;

    public BaseResponse(HttpStatus httpStatus) {
        this.code = httpStatus.getCode();
        this.message = httpStatus.getMessage();
    }

    public BaseResponse(HttpStatus httpStatus, T data) {
        this.code = httpStatus.getCode();
        this.message = httpStatus.getMessage();
        this.data = data;
    }


    public BaseResponse(int code, String message) {
        this.message = message;
        this.code = code;
    }

    public BaseResponse(int code, T data) {
        this.data = data;
        this.code = code;
    }

    public BaseResponse(int code, String message, T data) {
        this.data = data;
        this.message = message;
        this.code = code;
    }

    public static BaseResponse<Object> simple(Function<?> function, String... strings) {
        Object data = null;
        try {
            data = function.execute();
            if (data instanceof Boolean && !((Boolean) data)) {
                if (strings == null || strings.length == 0)
                    return new BaseResponse<>(HttpStatus.FAILD.getCode(), HttpStatus.FAILD.getMessage(), data);
                return new BaseResponse<>(HttpStatus.FAILD.getCode(), StrUtil.isEmpty(strings[1]) ? HttpStatus.FAILD.getMessage() : strings[1], data);
            }
            if (strings == null || strings.length == 0)
                return new BaseResponse<>(HttpStatus.SUCCESS.getCode(), HttpStatus.SUCCESS.getMessage(), data);
            return new BaseResponse<>(HttpStatus.SUCCESS.getCode(), StrUtil.isEmpty(strings[0]) ? HttpStatus.FAILD.getMessage() : strings[0], data);
        } catch (Exception e) {
            if (strings == null || strings.length == 0)
                return new BaseResponse<>(HttpStatus.FAILD.getCode(), HttpStatus.FAILD.getMessage(), data);
            return new BaseResponse<>(HttpStatus.FAILD.getCode(), StrUtil.isEmpty(strings[1]) ? HttpStatus.FAILD.getMessage() : strings[1], data);
        }
    }

    public static BaseResponse<Object> success(Object data) {
        return new BaseResponse<>(HttpStatus.SUCCESS, data);
    }

    public static BaseResponse<Object> successWithMsg(String message) {
        return new BaseResponse<>(HttpStatus.SUCCESS.getCode(), message, null);
    }

    public static BaseResponse<Object> faild(Object... data) {
        return new BaseResponse<>(HttpStatus.FAILD, data);
    }

    public static BaseResponse<Object> faildWithMsg(String message) {
        return new BaseResponse<>(HttpStatus.FAILD.getCode(), message, null);
    }

    public static ResponseEntity<Object> badRequest(Object data) {
        return new ResponseEntity<>(data, org.springframework.http.HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<Object> notFound(Object data) {
        return new ResponseEntity<>(data, org.springframework.http.HttpStatus.NOT_FOUND);
    }

    public static ResponseEntity<Object> forbidden(Object data) {
        return new ResponseEntity<>(data, org.springframework.http.HttpStatus.FORBIDDEN);
    }

    public static ResponseEntity<Object> unauthorized(Object data) {
        return new ResponseEntity<>(data, org.springframework.http.HttpStatus.UNAUTHORIZED);
    }

    public interface Function<T> {
        T execute();
    }
}