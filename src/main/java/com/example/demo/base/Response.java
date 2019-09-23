package com.example.demo.base;

public class Response<T> {

    private int code;
    private T data;
    private String msg;

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

    public Response(){

    }

    public Response(int code,String message){
        this.code = code;
        this.msg = message;
    }

    public void set(int code,String message){
        this.code = code;
        this.msg = message;
    }
}