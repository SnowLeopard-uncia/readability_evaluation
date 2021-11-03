package com.example.bamboo.javaBean;

public class BaseResponse<T> {
    //与json返回的一致
    private int code;
    private String msg;
    private T results;
}
