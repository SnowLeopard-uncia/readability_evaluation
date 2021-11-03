package com.example.bamboo.okhttp;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpUtils {
    // 图书主页
    public static void bookMainRequest(String address, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .post(okhttp3.internal.Util.EMPTY_REQUEST)
                .build();
        client.newCall(request).enqueue(callback);
    }

    // 获取图片
    public static void getPictureRequest(String address, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);
    }
}
