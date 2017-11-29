package com.zkyr.footballspace.model.bean;

/**
 * Created by xiaohua on 2017/10/11.
 */

public class MyHttpResponse<T> {


    private String message;
    private String status;
    private T data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
