package com.example.demo.base;

public class Response<T> {

    public final static int SUCCESS_CODE = 200;
    public final static int FAIl_CODE = 500;
    //校验失败
    public final static int CHECKFAIL_CODE = 400;
    //登录失败
    public final static int LOGINFAIL_CODE = 302;
    //没有登录
    public final static int NOT_LOGIN = 301;

    private int code;
    private T data;
    private String msg;

    public static <T> Response<T> success(T data, String msg) {
        return new Response<T>(data, msg);
    }

    public static <T> Response<T> success(T data) {
        return new Response<T>(data, "success");
    }

    public static Response fail(String msg) {
        return new Response(Response.FAIl_CODE, msg);
    }

    public Response() {

    }

    public Response(T data, String msg) {
        this.code = Response.SUCCESS_CODE;
        this.data = data;
        this.msg = msg;
    }

    public Response(int code, String message) {
        this.code = code;
        this.msg = message;
    }

    public void set(int code, String message) {
        this.code = code;
        this.msg = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}