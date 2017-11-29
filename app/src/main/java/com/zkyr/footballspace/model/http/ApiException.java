package com.zkyr.footballspace.model.http;

/**
 * Created by codeest on 2016/8/4.
 */
public class ApiException extends Exception{

    private int code;
    private String msg;

    public ApiException(String msg) {
        super(msg);
        this.msg=msg;
    }

    public ApiException(String msg, int code) {
        super(msg);
        this.code = code;
        this.msg=msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }
}
