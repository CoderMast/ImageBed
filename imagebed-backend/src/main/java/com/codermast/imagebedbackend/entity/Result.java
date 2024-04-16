package com.codermast.imagebedbackend.entity;

import com.alibaba.fastjson2.JSONObject;
import com.codermast.imagebedbackend.utils.ResultCode;
import lombok.Data;

@Data
public class Result<T> {

    private String code;
    private String message;
    private Object data;

    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.code = ResultCode.SUCCESS;
        return result;
    }

    public static <T> Result<T> success(String message) {
        Result<T> result = new Result<>();
        result.code = ResultCode.SUCCESS;
        result.message = message;
        result.data = null;
        return result;
    }

    public static <T> Result<T> success(Object data) {
        Result<T> result = new Result<>();
        result.code = ResultCode.SUCCESS;
        result.data = data;
        return result;
    }

    public static <T> Result<T> error(String message) {
        Result<T> result = new Result<>();
        result.code = ResultCode.ERROR;
        result.message = message;
        return result;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
