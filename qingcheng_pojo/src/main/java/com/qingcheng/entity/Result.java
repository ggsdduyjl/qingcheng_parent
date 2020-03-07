package com.qingcheng.entity;

import java.io.Serializable;

public class Result implements Serializable {

    private Integer code; // 返回代码 0.成功 1.失败

    private String message; // 返回信息

    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result() {
        this.code = 0;
        this.message = "执行成功";
    }

    public static Result success() {
        return new Result();
    }

    public static Result error(Integer code, String message) {
        return new Result(code, message);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
