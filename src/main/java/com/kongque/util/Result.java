package com.kongque.util;

/**
 * 返回结果
 */
public class Result<T> {

    private String code =  "200";

    private String message = "操作成功";

    private T returnData;

    private Long total = 0l;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getReturnData() {
        return returnData;
    }

    public void setReturnData(T returnData) {
        this.returnData = returnData;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Result() {
    }

    public Result(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(T returnData) {
        this.returnData = returnData;
    }
}
