package com.example.bamboo.Util;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpUtils {
    public static void readabilityWithOkhttp(String address,String text,okhttp3.Callback callback){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("text",text);
        String postBody =jsonObject.toString();
        Log.e(TAG, "readabilityWithOkhttp: "+postBody );
        MediaType mediaType = MediaType.Companion.parse("application/json;charset=UTF-8");


        OkHttpClient client = new OkHttpClient();
        RequestBody body =  RequestBody.Companion.create(postBody,mediaType);
        Request request=new Request.Builder()
                .url(address)
                .post(body)
                .build();
        Log.e(TAG, "readabilityWithOkhttp: "+request);
        client.newCall(request).enqueue(callback);
//        OkHttpClient client = new OkHttpClient();
//        RequestBody body = new FormBody.Builder()
//                .add("text",text)
//                .build();
//        Request request=new Request.Builder()
//                .url(address)
//                .post(body)
//                .addHeader("Content-Type","application/json")
//                .build();
//        Log.e(TAG, "readabilityWithOkhttp: "+request);
//        client.newCall(request).enqueue(callback);
    }
}
